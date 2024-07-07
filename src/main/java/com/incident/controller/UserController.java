package com.incident.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.incident.model.User;
import com.incident.service.UserService;
import com.incident.util.ApiComponent;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User Controller", description = "Here User Can Perform The Action What Is Required")
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	private final RestTemplate restTemplate;

	@GetMapping(ApiComponent.GET_API)
	@Operation(summary = "Get All The Details For User", description = "This Details Can User Only Here Is Not Add The Any Role")
	@ApiResponse(responseCode = "200", description = "Geting All Register Data Successfully")
	@PreAuthorize("hasAnyRole('User')")
	@SecurityRequirement(name = "Bearer Authentication")
	public Iterable<User> getAll() {
		return userService.getAll();
	}

	@GetMapping("/names/{userName}")
	@Operation(summary = "Get Single Data For Admin And User And Doctor", description = "Get The Details Using Id And This Details Can Only Admin And User And Doctor Access Other One is Not able to acess")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Geting Data Successfully"),
			@ApiResponse(responseCode = "401", description = "Something Wan't Wrong Plsease Trying Again"),
			@ApiResponse(responseCode = "402", description = "Data not Found") })
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('User')")
	public User getSingle(@PathVariable("userName") String userName) {
		return userService.getSingleData(userName);
	}

	@PostMapping(ApiComponent.CREATE_API)
	@Operation(summary = "Here We Do The Registration Here Is Not Add Any Security", description = "Here We Do The Registration. This Api Is Every One We can Access And Do The Registration After That Login")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Your Registration Sucessfully Done"),
			@ApiResponse(responseCode = "401", description = "Something Wan't Wrong Plsease Trying Again"),
			@ApiResponse(responseCode = "402", description = "Data not Found") })
	public User create(@RequestBody User user) {
		return userService.create(user);
	}

	@PutMapping("/forget/{email}/{password}")
	@Operation(summary = "Forget Password without any Security", description = "Forget your password without any security. You can change the password.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Password Updated Successfully"),
			@ApiResponse(responseCode = "401", description = "Something Went Wrong. Please Try Again."),
			@ApiResponse(responseCode = "404", description = "Data not Found") })
//	@SecurityRequirement(name = "Bearer Authentication")
//	@PreAuthorize("hasAnyRole('User')")
	public User forgetPassword(@PathVariable("email") String email, @PathVariable("password") String password) {

		User updatedUser = userService.forgetPassword(email, password);

		if (updatedUser != null) {
			return userService.forgetPassword(email, password);
		} else {
			return null;
		}
	}

	public UserController() {
		this.restTemplate = new RestTemplate();
	}

	@GetMapping("/pincode/{pincode}")
	public ResponseEntity<String> getPincodeInfo(@PathVariable String pincode) {
		String url = "http://www.postalpincode.in/api/pincode/" + pincode;
		try {
			String response = restTemplate.getForObject(url, String.class);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error fetching data");
		}
	}

}
