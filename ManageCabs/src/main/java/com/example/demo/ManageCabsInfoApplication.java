package com.example.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.CabInfo;
import com.example.demo.model.DriverInfo;
import com.example.demo.repository.ManageCabsInfoRepository;
import com.example.demo.repository.ManageDriversInfoRepository;


@SpringBootApplication
public class ManageCabsInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageCabsInfoApplication.class, args);
	}

	   @Bean
	    public CommandLineRunner runner() {
	       
	        return new CommandLineRunner() { 
	        	
	            @Autowired
	            ManageCabsInfoRepository cabRepo;
          
	            @Autowired
	            ManageDriversInfoRepository driverRepo;
            
	            @SuppressWarnings("deprecation")
	            @Override
	            public void run(String... args) throws Exception{
	           
//	                Date date1 = new Date (11,28,2024, 0, 0);
//	                DriverInfo driver1= new DriverInfo(108,"Abu",589672660,"LCNO7550",date1);
//	                this.driverRepo.save(driver1);
//	               
//	                Date date2 = new Date (2022, 12, 21, 0, 0);
//	                CabInfo cab1= new CabInfo("TN12K1111",driver1,"Tavera",10,123456987,date2,"ragu");
//	                this.cabRepo.save(cab1);
            }
        };
    }
}
	