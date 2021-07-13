package com.example.demo.bl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Dl.ManageCabsInfoDL;
import com.example.demo.model.CabInfo;
import com.example.demo.model.DriverInfo;
import com.example.demo.repository.ManageCabsInfoRepository;


@Component
public class ManageCabsInfoBL {

	@Autowired
	ManageCabsInfoDL cabInfoDl;
	
	@Autowired
	ManageCabsInfoRepository cabRepo;

		
	// call a DL method to find and return List of cab details which isDeleted field is '0'
	public List<CabInfo> getAllCabDetails() {
		
		return this.cabInfoDl.findByIsDeleted('0');
	}
	
	//call a DL method to check driver availability
	public boolean isDriverAvailable(CabInfo info) {
		
		Long id = info.getDriverId();
		Optional<CabInfo> entity = cabInfoDl.findByDriverId(id);
		
		//driver already present and he is assigned to a cab return false(driver is not available)
		if(entity.isPresent() && !(entity.get().getCabNumber().equals(info.getCabNumber()))  && entity.get().getIsDeleted()=='0')
			return false;
		
		
		return true;
	} 
	
	// call a DL method to find a cab for deletion
	public CabInfo deleteCab(String cabNumber) {		
		return this.cabInfoDl.deleteCabByCabNumber(cabNumber);
	}
	
	// call a DL method to find and return List of drivers
	public List<DriverInfo> getAllDrivers() {
		return this.cabInfoDl.findAllDrivers();
	}
	
	//call a DL method to find and return List of cabModel which isDeleted field is '0' 
	public List<CabInfo> getAllCabModels(char c) {
		return this.cabInfoDl.findByIsDeleted(c);
	}

	//call a DL method to find and return the cab detail that matched a
	//to the cab number & isDeleted field '0'
	public Optional<CabInfo> getCabNumber(String cabNumber) {
		return this.cabRepo.findByCabNumberAndIsDeleted(cabNumber,'0');
	}

	//call a DL method to find and return the cab detail that matched 
	//to the insurance number & isDeleted field '0'
	public Optional<CabInfo> getInsuranceNumber(String insNum) {
		return this.cabRepo.findByInsuranceNumberAndIsDeleted(insNum, '0');
	}
	
}
