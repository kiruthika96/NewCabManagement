package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.demo.model.DriverInfo;

public interface ManageDriversInfoRepository extends MongoRepository<DriverInfo, Integer> {

	
List<DriverInfo> findByIsDeleted(int i);
}