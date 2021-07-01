package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.model.CabInfo;
import com.example.demo.model.DriverInfo;


public interface ManageCabsInfoRepository extends MongoRepository<CabInfo, Integer> {

	
	Optional<CabInfo> findByCabNumber(String cabNumber);
	
	
	//String findByCabNumberPut(String cabNumber);
	
	//to find cabs where the isDeleted flag is 0
	
	List<CabInfo> findByIsDeleted(char isDeleted);
	
	//boolean findByIsDeleted(char isDeleted);
	
//	@Query(value = "{}",fields="{cabNumber:1}")
//    public Optional<CabInfo> findOnlyCabNumber();

	
	

	Optional<CabInfo> findByDriverId(Long id);

	Optional<CabInfo> findByCabNumberAndIsDeleted(String cabNumber, char c);
	

	

}
