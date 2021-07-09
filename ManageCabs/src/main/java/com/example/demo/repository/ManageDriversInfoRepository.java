package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.DriverInfo;

public interface ManageDriversInfoRepository extends MongoRepository<DriverInfo, Integer> {

	
}
