package com.example.demo.Dl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.CabInfo;
import com.example.demo.model.DriverInfo;
import com.example.demo.repository.ManageCabsInfoRepository;
import com.example.demo.repository.ManageDriversInfoRepository;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

@Service
public class ManageCabsInfoDL {

	@Autowired
	ManageCabsInfoRepository cabRepo;
	
	@Autowired
	ManageDriversInfoRepository driverRepo;
	
	@Autowired
    MongoTemplate mongoTemplate;

//	public List<CabInfo> getAllCabInfo() {
//		
//		return this.cabRepo.findAll();
//	}

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
	
	//rohit --beg
	public Optional<CabInfo> findByDriverId(Long id) {
		//return this.driverRepo.findByDriverId(id);
		return this.cabRepo.findByDriverId(id);
	}
	//rohit -- end
	
/*	
    public List<String> findAllCabModel() {
    	
    	
        List<String> cabModelList = new ArrayList<>();
        
        MongoCollection mongoCollection = mongoTemplate.getCollection("cabInfo");
        
       // List<CabInfo> allCabModel=cabRepo.findByIsDeleted('0');
       DistinctIterable distinctIterable = mongoCollection.distinct("cabModel",String.class);
        
        //DistinctIterable distinctIterable = ((MongoCollection) allCabModel).distinct("cabModel",String.class);
        MongoCursor cursor = distinctIterable.iterator();
        
        while (cursor.hasNext()) {
            String cabModel = (String)cursor.next();
            cabModelList.add(cabModel);
        }
        return cabModelList;
    }
*/
	public List<CabInfo> findByIsDeleted(char c) {
		
		return this.cabRepo.findByIsDeleted(c);
	}

	public Optional<CabInfo> findByCabNumber(String cabNumber) {
		
		return this.cabRepo.findByCabNumber(cabNumber);
	}
	
	
}
