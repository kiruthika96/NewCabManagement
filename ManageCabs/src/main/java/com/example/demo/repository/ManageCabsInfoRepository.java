package com.example.demo.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.model.CabInfo;
import com.example.demo.model.DriverInfo;


public interface ManageCabsInfoRepository extends MongoRepository<CabInfo, Integer> {

	
	Optional<CabInfo> findByCabNumber(String cabNumber);

	
	
	List<CabInfo> findByIsDeleted(char isDeleted);

	
	//@Query(value="{driverId:?0,isDeleted:{$nin:[1]}}")
	Optional<CabInfo> findByDriverId(Long id);

	Optional<CabInfo> findByCabNumberAndIsDeleted(String cabNumber, char c);


	Optional<CabInfo> findByInsuranceNumberAndIsDeleted(String insNum, char c);
	

	

}
