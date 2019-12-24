package com.yu.house.web.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import comt.yu.house.autoconfig.EnableHttpClient;

@SpringBootApplication
@EnableHttpClient
public class HouseApplication {
	public static void main(String[] args) {
		SpringApplication.run(HouseApplication.class, args);
	}

}
