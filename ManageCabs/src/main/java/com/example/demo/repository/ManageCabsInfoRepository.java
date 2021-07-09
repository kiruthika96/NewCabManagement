package com.example.demo.repository;


import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.demo.model.CabInfo;



public interface ManageCabsInfoRepository extends MongoRepository<CabInfo, Integer> {

	
	Optional<CabInfo> findByCabNumber(String cabNumber);

	List<CabInfo> findByIsDeleted(char isDeleted);

	Optional<CabInfo> findByDriverId(Long id);

	Optional<CabInfo> findByCabNumberAndIsDeleted(String cabNumber, char c);

	Optional<CabInfo> findByInsuranceNumberAndIsDeleted(String insNum, char c);
	

}
