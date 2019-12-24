package com.yu.house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import comt.yu.house.autoconfig.EnableHttpClient;
//(exclude= {DataSourceAutoConfiguration.class})

@SpringBootApplication
@EnableHttpClient
@EnableAsync
public class HouseApplication {
	public static void main(String[] args) {
		SpringApplication.run(HouseApplication.class, args);
	}
}
