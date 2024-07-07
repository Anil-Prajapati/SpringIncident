package com.incident.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.incident.constants.Priority;
import com.incident.constants.Status;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "incident")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Incident {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Hidden
	private Long id;

	@Column(name = "incident_id")
	@Hidden
	private String incidentId; // RMG + Random 5-digit number + Current year

	@Column(name = "enterprise_Government")
	private String enterpriseOrGovernment;

	@Column(name = "reporter_name")
	private String reporterName;

	@Column(name = "incident_details")
	private String incidentDetails;

	@Column(name = "reporter_date")
	private LocalDate reportedDateTime;

	@Column(name = "incident_priority")
	@Enumerated(EnumType.STRING)
	private Priority priority;

	@Column(name = "incident_status")
	@Enumerated(EnumType.STRING)
	private Status status;

	

	public Incident(Long id, String incidentId, String enterpriseOrGovernment, String reporterName,
			String incidentDetails, LocalDate reportedDateTime, Priority priority, Status status, User user) {
		super();
		this.id = id;
		this.incidentId = incidentId;
		this.enterpriseOrGovernment = enterpriseOrGovernment;
		this.reporterName = reporterName;
		this.incidentDetails = incidentDetails;
		this.reportedDateTime = reportedDateTime;
		this.priority = priority;
		this.status = status;
		this.user = user;
	}



	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

}
