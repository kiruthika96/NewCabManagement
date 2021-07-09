package com.example.demo.controller;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;


import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dl.ManageCabsInfoDL;
import com.example.demo.bl.ManageCabsInfoBL;
import com.example.demo.model.CabInfo;
import com.example.demo.model.DriverInfo;


@RestController

public class ManageCabsInfoController {
	 
	@Autowired
	ManageCabsInfoBL cabInfoBl;

	@Autowired
	ManageCabsInfoDL cabInfoDl;
	

		@GetMapping(path="/all/cabModel")
	    public ResponseEntity<Set<String>> getAllCabModels()
	    {
	    	List<CabInfo> cabInfo =this.cabInfoBl.findByIsDeleted('0');
	 
	    	Set<String> cabModel=new HashSet<>();
	    	
	    	for(CabInfo eachCab:cabInfo) {
	    		
	    		String oneCab=eachCab.getCabModel();
	    		
	    			cabModel.add(oneCab);
	    	}
	    	
	    	return ResponseEntity.status(HttpStatus.OK).body(cabModel);
	    }
	
		
		
		@GetMapping(path="/all/driverInfo")
	    
	    public ResponseEntity<List<DriverInfo>> getAllDrivers(){
	        	
	        List<DriverInfo> driverInfo =this.cabInfoBl.getAllDrivers();
	        
	        return ResponseEntity.status(HttpStatus.OK).body(driverInfo);
	    }
	
	
	    @GetMapping(path="/all/cabInfo")
	    
	    public ResponseEntity<List<CabInfo>> getAllCabDetails(){
	    	
	    	List<CabInfo> cabInfo =this.cabInfoBl.getAllCabDetails();
	    	
	        return ResponseEntity.status(HttpStatus.OK).body(cabInfo);
	    }
	        
	    

	    @PostMapping(path="/addCabInfo")
	    public ResponseEntity<CabInfo> addCabDetails(@RequestBody CabInfo cabInfo){
	    	
	    	CabInfo reqCab = cabInfo;
	    	reqCab.setIsDeleted('0');
	    	
	    	CabInfo createdBy=cabInfo;
	    	createdBy.setCreatedBy("Admin");
	    	
	    	CabInfo createdDate=cabInfo;
	    	createdDate.setCreatedDate(LocalDate.now());
	    	
	      	
	    	
	    	String cabNumber = cabInfo.getCabNumber();
	    	Optional<CabInfo> entityCabNum=cabInfoBl.getCabNumber(cabNumber);

	    	String insNum=cabInfo.getInsuranceNumber();
	    	Optional<CabInfo> entityInsNum =cabInfoBl.getInsuranceNumber(insNum); 	
	    	
	    	
	    	CabInfo saveCabInfo = null;
			
	    	boolean isDriverAvailable = cabInfoBl.isDriverAvailable(cabInfo);
	    	
	    	
			if(entityCabNum.isPresent()) {
				
				//throw new NoSuchElementException("CabNumber already exist");
				return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
				
			}
			else if(entityInsNum.isPresent()) {
				//throw new NoSuchElementException("Insurance Number already exist");
				return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
			}
			
			else {
				
				//call a business layer method to check if the driver has already been assigned a cab
			
				if(isDriverAvailable)
					saveCabInfo=this.cabInfoDl.addCabInfo(cabInfo);
					
				else {
					//throw new NoSuchElementException("Driver already assigned a cab");
					return new ResponseEntity<>(null, HttpStatus.FOUND);
				}
				return ResponseEntity.status(HttpStatus.CREATED).body(saveCabInfo);
			}
	    	 	
	    }
	    
	    
		@PutMapping(path="/updateCabInfo")
		public ResponseEntity<CabInfo> editCabDetails(@RequestBody CabInfo updateCabInfo)
		{
	    	CabInfo isDeleted = updateCabInfo;
	    	isDeleted.setIsDeleted('0');
	    	
	    	CabInfo modifiedBy=updateCabInfo;
	    	modifiedBy.setModifiedBy("Admin");
	    	
	    	CabInfo modifiedDate=updateCabInfo;
	    	modifiedDate.setModifiedDate(LocalDate.now());
	    	
	    	CabInfo saveCabInfo = null;
	    	
	    	boolean isDriverAvailable = cabInfoBl.isDriverAvailable(updateCabInfo);
	    	
			if(isDriverAvailable) {
				saveCabInfo=this.cabInfoDl.updateCabDetails(updateCabInfo);
			}else {
				
				return new ResponseEntity<>(null, HttpStatus.FOUND);	
			}
				
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveCabInfo);
		}
	    
	   
	    
	    @PutMapping(path="/deleteCabInfo/{cabNumber}")
	    public ResponseEntity<CabInfo> deleteCab(@PathVariable("cabNumber") String cabNumber)
	    {   	
	    		
	    	CabInfo cabInfo=this.cabInfoBl.deleteCab(cabNumber);
	    	
	    	return ResponseEntity.status(HttpStatus.OK).body(cabInfo);
	    }
	    	       
	    
}
