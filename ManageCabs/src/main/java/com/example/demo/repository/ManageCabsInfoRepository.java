package com.example.demo.repository;


import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.demo.model.CabInfo;



public interface ManageCabsInfoRepository extends MongoRepository<CabInfo, Integer> {

	// find cab details by cabNumber
	Optional<CabInfo> findByCabNumber(String cabNumber);

	// find cab details where isDeleted field='0'
	List<CabInfo> findByIsDeleted(char isDeleted);

	// find cab details by driverId
	Optional<CabInfo> findByDriverId(Long id);

	// find a cab details where given cab number and isDelete field are same
	Optional<CabInfo> findByCabNumberAndIsDeleted(String cabNumber, char c);

	// find a cab details where given insurance number and isDelete field are same
	Optional<CabInfo> findByInsuranceNumberAndIsDeleted(String insNum, char c);
	

}
