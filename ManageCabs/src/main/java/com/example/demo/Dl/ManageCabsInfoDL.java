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


	public CabInfo addCabInfo(CabInfo cabInfo) {
		
		return this.cabRepo.save(cabInfo);
	}

	public CabInfo updateCabDetails(CabInfo updateCabInfo) {
		
		return this.cabRepo.save(updateCabInfo);
	}

	public CabInfo deleteCabByCabNumber(String cabNumber) {
		
		Optional<CabInfo> cab = this.cabRepo.findByCabNumber(cabNumber);
		CabInfo deletedCab = cab.get();
		deletedCab.setIsDeleted('1');
		deletedCab.setModifiedBy("Admin");

		deletedCab.setModifiedDate(LocalDate.now());
		return this.cabRepo.save(deletedCab);
	}

	
	public List<DriverInfo> findAllDrivers() {
		return this.driverRepo.findAll();
	}
	
	
	public Optional<CabInfo> findByDriverId(Long id) {
		
		Query driverQuery=new Query();
		
		Criteria driverIdCriteria1=Criteria.where("driverId").is(id);
		Criteria driverIdCriteria2=Criteria.where("isDeleted").nin('1');
		
		Criteria driverIdCriteria=new Criteria();
		driverIdCriteria.andOperator(driverIdCriteria1,driverIdCriteria2);
		
		driverQuery.addCriteria(driverIdCriteria);
				
		List<CabInfo> cabDriver =  mongoTemplate.find(driverQuery, CabInfo.class, "cabInfo");
		
		if(cabDriver.isEmpty()) {
			
			Query cabDriverQuery=new Query();
			
			Criteria cabDriverIdCriteria=Criteria.where("driverId").is(id);
			
			cabDriverQuery.addCriteria(cabDriverIdCriteria);
			
			List<CabInfo> cabDriverId =  mongoTemplate.find(cabDriverQuery, CabInfo.class, "cabInfo");
			
			if(cabDriverId.isEmpty()) {
				return this.cabRepo.findByDriverId(id);
			}
			
		return Optional.of(cabDriverId.get(0));
		}
		
		return Optional.of(cabDriver.get(0));
	}
	

	
	public List<CabInfo> findByIsDeleted(char c) {
		
		return this.cabRepo.findByIsDeleted(c);
	}

	
	
}
