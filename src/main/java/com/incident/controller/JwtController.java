package com.incident.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.incident.model.JwtRequest;
import com.incident.model.JwtResponse;
import com.incident.service.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="Authentication Model",description = "Here Login Every One What Are the Person We Do The Registration")
public class JwtController {

	@Autowired
	private JwtService jwtService;
	
		
	@PostMapping("/authentication")
	@Operation(summary = "Login Every One", description = "This Is The Login Model Here")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",description = "Login Successfully"),
			@ApiResponse(responseCode = "401",description = "Something Wan't Wrong Plsease Trying Again"),
			@ApiResponse(responseCode = "402", description = "Data not Found")
	})
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) {	
		return jwtService.createJwtToken(jwtRequest);
	}
}
