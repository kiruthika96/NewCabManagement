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
	
		//call a business layer method to get List of cabModels and return unique set of cab models
		@GetMapping(path="/all/cabModel")
	    public ResponseEntity<Set<String>> getAllCabModels()
	    {
	    	List<CabInfo> cabInfo =this.cabInfoBl.getAllCabModels('0');
	 
	    	Set<String> cabModel=new HashSet<>();
	    	
	    	for(CabInfo eachCab:cabInfo) {
	    		
	    		String eachCabModel=eachCab.getCabModel();
	    		
	    			cabModel.add(eachCabModel);
	    	}
	    	
	    	return ResponseEntity.status(HttpStatus.OK).body(cabModel);
	    }
	
		
		
		// call a business layer method to get & return list of driver details
		@GetMapping(path="/all/driverInfo")
	    
	    public ResponseEntity<List<DriverInfo>> getAllDrivers(){
	        	
	        List<DriverInfo> driverInfo =this.cabInfoBl.getAllDrivers();
	        
	        return ResponseEntity.status(HttpStatus.OK).body(driverInfo);
	    }
	
		
		
		//call a BL method to get & return list of cab details which is not deleted by the user
	    @GetMapping(path="/all/cabInfo")
	    
	    public ResponseEntity<List<CabInfo>> getAllCabDetails(){
	    	
	    	List<CabInfo> cabInfo =this.cabInfoBl.getAllCabDetails();
	    	
	        return ResponseEntity.status(HttpStatus.OK).body(cabInfo);
	    }
	        
	    
	    
	    
	    //call a BL method to  add new cab into the database
	    @PostMapping(path="/addCabInfo")
	    public ResponseEntity<CabInfo> addCabDetails(@RequestBody CabInfo cabInfo){
	    	
	    	CabInfo reqCab = cabInfo;
	    	reqCab.setIsDeleted('0');
	    	
	    	CabInfo createdBy=cabInfo;
	    	createdBy.setCreatedBy("Admin");
	    	
	    	CabInfo createdDate=cabInfo;
	    	createdDate.setCreatedDate(LocalDate.now());
	    	
	      	
	    	//call a BL method to get cab details if cab number already have cab details
	    	String cabNumber = cabInfo.getCabNumber();
	    	Optional<CabInfo> entityCabNum=cabInfoBl.getCabNumber(cabNumber);

	    	//call a BL method to get cab details if cab insurance number already have cab details
	    	String insNum=cabInfo.getInsuranceNumber();
	    	Optional<CabInfo> entityInsNum =cabInfoBl.getInsuranceNumber(insNum); 	
	    	
	    	
	    	CabInfo saveCabInfo = null;
			
	    	//call a business layer method to check if the driver has already been assigned a cab
	    	boolean isDriverAvailable = cabInfoBl.isDriverAvailable(cabInfo);
	    	
	    	
			if(entityCabNum.isPresent()) {
				
				//"CabNumber already exist"
				return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
				
			}
			else if(entityInsNum.isPresent()) {
				//"Insurance Number already exist"
				return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
			}
			
			else {
			
				if(isDriverAvailable)
					saveCabInfo=this.cabInfoDl.addCabInfo(cabInfo);
					
				else {
					
					//"Driver already assigned a cab";
					return new ResponseEntity<>(null, HttpStatus.FOUND);
				}
				return ResponseEntity.status(HttpStatus.CREATED).body(saveCabInfo);
			}
	    	 	
	    }
	    
	    //call a BL method to update and return the cab detail
		@PutMapping(path="/updateCabInfo")
		public ResponseEntity<CabInfo> editCabDetails(@RequestBody CabInfo updateCabInfo)
		{
	    	CabInfo isDeleted = updateCabInfo;
	    	isDeleted.setIsDeleted('0');
	    	
	    	CabInfo modifiedBy=updateCabInfo;
	    	modifiedBy.setModifiedBy("Admin");
	    	
	    	CabInfo modifiedDate=updateCabInfo;
	    	modifiedDate.setModifiedDate(LocalDate.now());
	    	
	    	// call a BL method to get cab detail if insurance number have a cab detail
	    	String updateInsNum=updateCabInfo.getInsuranceNumber();
	    	Optional<CabInfo> updateEntityInsNum =cabInfoBl.getInsuranceNumber(updateInsNum);
	    	
	    	
	    	CabInfo saveCabInfo = null;
	    	
	    	//call a business layer method to check if the driver has already been assigned a cab
	    	boolean isDriverAvailable = cabInfoBl.isDriverAvailable(updateCabInfo);
	    	
	    	if(updateEntityInsNum.isPresent() && !(updateEntityInsNum.get().getCabNumber().equals(updateCabInfo.getCabNumber()))) {
	    		// insurance number already exist
	    		return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);	
	    	}
	    	else 
	    	{
				if(isDriverAvailable) {
					saveCabInfo=this.cabInfoDl.updateCabDetails(updateCabInfo);
				}else {
					// driver already assign to a cab
					return new ResponseEntity<>(null, HttpStatus.FOUND);	
				}
				
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveCabInfo);
		}
	}    	
	    
	   
	    // call a BL method to delete the cab detail
	    @PutMapping(path="/deleteCabInfo/{cabNumber}")
	    public ResponseEntity<CabInfo> deleteCab(@PathVariable("cabNumber") String cabNumber)
	    {   	
	    		
	    	CabInfo cabInfo=this.cabInfoBl.deleteCab(cabNumber);
	    	
	    	return ResponseEntity.status(HttpStatus.OK).body(cabInfo);
	    }
	    	       
	    
}
