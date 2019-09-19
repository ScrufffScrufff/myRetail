package com.casestudy.myRetailProduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;


@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class MyRetailProductApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(MyRetailProductApplication.class, args);
		}catch (Exception e){
			e.printStackTrace();
		}

	}
}
