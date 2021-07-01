package com.example.demo.model;


import java.math.BigInteger;
import java.time.LocalDate;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;



@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Document(collection = "cabInfo")

public class CabInfo {
	
	@Id
	String  cabNumber;
	
	long driverId;
	String  cabModel;
	int availableSeats;
	BigInteger insuranceNumber;
	LocalDate expiryDate;
	String  driverName;
	char isDeleted;
	String createdBy;
	String modifiedBy;
	LocalDate createdDate;
	LocalDate modifiedDate;
	//String status;
	
	public CabInfo(String cabModel, int availableSeats, BigInteger insuranceNumber,
			LocalDate expiryDate, String driverName, char isDeleted, String createdBy, String modifiedBy,
			LocalDate createdDate, LocalDate modifiedDate) {
		super();
		this.cabModel = cabModel;
		this.availableSeats = availableSeats;
		this.insuranceNumber = insuranceNumber;
		this.expiryDate = expiryDate;
		this.driverName = driverName;
		this.isDeleted = isDeleted;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		//this.status = status;
	}
	
	
	
}
