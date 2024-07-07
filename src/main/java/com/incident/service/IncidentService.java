package com.incident.service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incident.constants.Priority;
import com.incident.constants.Status;
import com.incident.model.Incident;
import com.incident.model.User;
import com.incident.repository.IncidentRepository;

@Service
public class IncidentService {

	@Autowired
	private IncidentRepository incidentRepository;

	@Autowired
	private UserService userService;

	public Iterable<Incident> getAll() {
		return incidentRepository.findAll();
	}

	public List<Incident> getIncidentsByUser(Principal principal) {
		User currentUser = userService.getCurrentUser(principal);
		return incidentRepository.findByUser(currentUser);
	}

//	public List<Incident> getIncidentsByUser(Principal principal) {
//        User currentUser = userRepository.findByUserName(principal.getName());
//        return incidentRepository.findByUser(currentUser);
//    }

	public Incident saveIncident(Incident incident, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        incident.setIncidentId(generateIncidentId());
        incident.setUser(currentUser);
        return incidentRepository.save(incident);
    }

	public Incident getSingle(long id) {
		return incidentRepository.findById(id).orElse(new Incident());
	}

	public Incident create(Incident incident) {
		incident.setIncidentId(generateIncidentId());
		return incidentRepository.save(incident);
	}

	private String generateIncidentId() {
		String prefix = "RMG";
		int randomNum = new Random().nextInt(90000) + 10000; // Generate random 5-digit number
		int currentYear = Year.now().getValue(); // Get current year
		return prefix + randomNum + currentYear;
	}

	public Incident getByIncidentId(String incidentId) {
		return incidentRepository.findByIncidentId(incidentId);
	}

	public Incident updateIncident(String incidentId, String incidentDetails, LocalDate reportedDateTime,
			Priority priority, Status status) {
		Incident incident = incidentRepository.findByIncidentId(incidentId);
		if(incident.getStatus()==Status.CLOSED) {
			throw new IllegalArgumentException("Cannot update a closed incident.");
		}
		incident.setIncidentDetails(incidentDetails);
		incident.setReportedDateTime(reportedDateTime);
		incident.setPriority(priority);
		incident.setStatus(status);

		return incidentRepository.save(incident);
	}

	public void deleteIncident(long id) {
		incidentRepository.deleteById(id);
	}

}
