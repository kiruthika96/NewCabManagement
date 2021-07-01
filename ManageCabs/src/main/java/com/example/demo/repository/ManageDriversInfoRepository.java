package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.DriverInfo;

public interface ManageDriversInfoRepository extends MongoRepository<DriverInfo, Integer> {

	public Optional<DriverInfo> findByDriverId(Integer id); //rohit
}
