package com.incident.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incident.model.Incident;
import com.incident.model.User;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long>{

	Incident findByIncidentId(String incidentId);

	List<Incident> findByUser(User currentUser);
}
