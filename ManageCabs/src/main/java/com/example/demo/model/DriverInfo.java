package com.example.demo.model;

import java.math.BigInteger;
import java.util.Date;

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

@Document(collection = "driverInfo")

public class DriverInfo {

	@Id
	int driverId;
	
	String driverName;
	long driverNumber;
	String licenseNumber;
	Date expiryDate;
	
}
