package com.nishanneupane.whatsapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/")
	public ResponseEntity<String> homeController(){
		return new ResponseEntity<String>("welcome to pur whatsapp api using springboot ",HttpStatus.OK);
	}

}
