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

		

	public List<CabInfo> getAllCabDetails() {
		
		return this.cabInfoDl.findByIsDeleted('0');
	}
	

	public boolean isDriverAvailable(CabInfo info) {
		
		Long id = info.getDriverId();
		Optional<CabInfo> entity = cabInfoDl.findByDriverId(id); 
		
		if(entity.isPresent() && !(entity.get().getCabNumber().equals(info.getCabNumber()))  && entity.get().getIsDeleted()=='0')
			return false;
		
		
		return true;
	} 
	

	public CabInfo deleteCab(String cabNumber) {		
		return this.cabInfoDl.deleteCabByCabNumber(cabNumber);
	}

	public List<DriverInfo> getAllDrivers() {
		return this.cabInfoDl.findAllDrivers();
	}

	public List<CabInfo> findByIsDeleted(char c) {
		return this.cabInfoDl.findByIsDeleted(c);
	}


	public Optional<CabInfo> getCabNumber(String cabNumber) {
		return this.cabRepo.findByCabNumberAndIsDeleted(cabNumber,'0');
	}


	public Optional<CabInfo> getInsuranceNumber(String insNum) {
		return this.cabRepo.findByInsuranceNumberAndIsDeleted(insNum, '0');
	}
	
}
