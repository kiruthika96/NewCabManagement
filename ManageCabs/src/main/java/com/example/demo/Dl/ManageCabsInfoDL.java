package com.example.demo.Dl;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Service;

import com.example.demo.model.CabInfo;
import com.example.demo.model.DriverInfo;
import com.example.demo.repository.ManageCabsInfoRepository;
import com.example.demo.repository.ManageDriversInfoRepository;


@Service
public class ManageCabsInfoDL {

	@Autowired
	ManageCabsInfoRepository cabRepo;
	
	@Autowired
	ManageDriversInfoRepository driverRepo;
	
	@Autowired
    MongoTemplate mongoTemplate;

	
	//call save method to save cab details into database
	public CabInfo addCabInfo(CabInfo cabInfo) {
		
		return this.cabRepo.save(cabInfo);
	}

	
	//call save method to replace cab details into database
	public CabInfo updateCabDetails(CabInfo updateCabInfo) {
		
		return this.cabRepo.save(updateCabInfo);
	}

	
	//call find method to get the cab detail of the cab number
	// and update isDelete field in '0' to '1'
	public CabInfo deleteCabByCabNumber(String cabNumber) {
		
		Optional<CabInfo> cab = this.cabRepo.findByCabNumber(cabNumber);
		CabInfo deletedCab = cab.get();
		deletedCab.setIsDeleted('1');
		deletedCab.setModifiedBy("Admin");

		deletedCab.setModifiedDate(LocalDate.now());
		return this.cabRepo.save(deletedCab);
	}

	// call a find all method to find and get all drivers
	public List<DriverInfo> findAllDrivers() {
		return this.driverRepo.findAll();
	}
	
	// find the driver availability
	public Optional<CabInfo> findByDriverId(Long id) {
		
		Query driverQuery=new Query();
		
		Criteria driverIdCriteria1=Criteria.where("driverId").is(id);       //   where the driverId matched
		Criteria driverIdCriteria2=Criteria.where("isDeleted").nin('1');	//  where isDeleted field not in '1'
		
		Criteria driverIdCriteria=new Criteria();
		driverIdCriteria.andOperator(driverIdCriteria1,driverIdCriteria2);	// make one criteria by combining criteria 1 & 2 
		
		driverQuery.addCriteria(driverIdCriteria);							// add criteria to query
		
		
		// find cab details which match for the given criteria in cabInfo collection
		List<CabInfo> cabDriver =  mongoTemplate.find(driverQuery, CabInfo.class, "cabInfo");     
		
		
		if(cabDriver.isEmpty()) {
			
			Query cabDriverQuery=new Query();
			
			Criteria cabDriverIdCriteria=Criteria.where("driverId").is(id);		// where the driverId matched
			
			cabDriverQuery.addCriteria(cabDriverIdCriteria);					// add criteria to query
			
			
			// find cab details which match for the given criteria in cabInfo collection
			List<CabInfo> cabDriverId =  mongoTemplate.find(cabDriverQuery, CabInfo.class, "cabInfo");
			
			if(cabDriverId.isEmpty()) {									// if driverId is not present in the collection
				
				return this.cabRepo.findByDriverId(id);
			}
			
		return Optional.of(cabDriverId.get(0));			  
		}
		
		return Optional.of(cabDriver.get(0));           
	}
	

	// call a find method to find all cab details where isDelete field='0'
	public List<CabInfo> findByIsDeleted(char c) {
		
		return this.cabRepo.findByIsDeleted(c);
	}

	// call a find method to find cab details where cab number and isDeleted are matched
	public Optional<CabInfo> findByCabNumberAndIsDeleted(String cabNumber, char c) {
		
		return this.cabRepo.findByCabNumberAndIsDeleted(cabNumber, c);
	}

	// call a find method to find cab details where insurance number and isDeleted are matched
	public Optional<CabInfo> findByInsuranceNumberAndIsDeleted(String insNum, char c) {
		
		return this.cabRepo.findByInsuranceNumberAndIsDeleted(insNum, c);
	}

	
	
}
