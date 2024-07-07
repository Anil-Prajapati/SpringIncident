package com.incident.controller;

import java.security.Principal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import com.incident.constants.Priority;
import com.incident.constants.Status;
import com.incident.model.Incident;
import com.incident.service.IncidentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Incident Controller", description = "Here Maitain The Incident Realated Resposbility")
public class IncidentController {

	@Autowired
	private IncidentService incidentService;

	@GetMapping("/incident/all")
	@Operation(summary = "Get All The Details For Incident", description = "This Details Can Incident Only Access Other One is Not able to acess")
	@ApiResponse(responseCode = "200", description = "All Data Getting Successfully")
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('User')")
	public Iterable<Incident> getAll() {
		return incidentService.getAll();

	}

	@GetMapping("/incident")
	@Operation(summary = "Get All The Details For Incident", description = "This Details Can Incident Only Access Other One is Not able to acess")
	@ApiResponse(responseCode = "200", description = "All Data Getting Successfully")
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('User')")
	public List<Incident> getAll(Principal principal) {
		return incidentService.getIncidentsByUser(principal);
	}

	@Operation(summary = "Get Single Incident Details Using Id", description = "This Details Can Incident Only Access Other One is Not able to acess")
	@ApiResponse(responseCode = "200", description = "All Data Getting Successfully")
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('User')")
	@GetMapping("/incident/{id}")
	public Incident getSingleData(@PathVariable("id") long id) {
		return incidentService.getSingle(id);
	}

//	@Operation(summary = "Save The Incident Details", description = "This Details Can Incident Only Access Other One is Not able to acess")
//	@ApiResponse(responseCode = "200", description = "All Data Getting Successfully")
//	@SecurityRequirement(name = "Bearer Authentication")
//	@PreAuthorize("hasAnyRole('User')")
//	@PostMapping("/incident/create")
//	public Incident create(@RequestBody Incident incident) {
//		return incidentService.create(incident);
//	}

	@Operation(summary = "Save The Incident Details", description = "This Details Can Incident Only Access Other One is Not able to acess")
	@ApiResponse(responseCode = "200", description = "All Data Getting Successfully")
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('User')")
	@PostMapping("/incident/create")
    public ResponseEntity<Incident> createIncident(@RequestBody Incident incident, Principal principal) {
        Incident savedIncident = incidentService.saveIncident(incident, principal);
        return ResponseEntity.ok(savedIncident);
    }
	
	@Operation(summary = "Get Details Using IncidentId", description = "This Details Can Incident Only Access Other One is Not able to acess")
	@ApiResponse(responseCode = "200", description = "All Data Getting Successfully")
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('User')")
	@GetMapping("/api/incident/{incidentId}")
	public Incident getIncidentId(@PathVariable("incidentId") String incidentId) {
		return incidentService.getByIncidentId(incidentId);
	}

	@Operation(summary = "Update The Details For Incident", description = "This Details Can Incident Only Access Other One is Not able to acess")
	@ApiResponse(responseCode = "200", description = "All Data Getting Successfully")
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('User')")
	@PutMapping("/update/incident/{incidentId}/{incidentDetails}/{reportedDateTime}/{priority}/{status}")
	public Incident updateIncident(@PathVariable("incidentId") String incidentId,
			@PathVariable("incidentDetails") String incidentDetails,
			@PathVariable("reportedDateTime") LocalDate reportedDateTime, @PathVariable("priority") Priority priority,
			@PathVariable("status") Status status) {
		return incidentService.updateIncident(incidentId, incidentDetails, reportedDateTime, priority, status);
	}

	@Operation(summary = "delete the incident details", description = " if you wan't to delete the incident details")
	@ApiResponse(responseCode = "200", description = "All Data Getting Successfully")
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('User')")
	@DeleteMapping("/delete/{id}")
	public void getIncidentId(@PathVariable("id") long id) {
		incidentService.deleteIncident(id);
	}

}
