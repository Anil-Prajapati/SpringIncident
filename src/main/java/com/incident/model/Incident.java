package com.incident.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private String priority;

	@Column(name = "incident_status")
	private String status;

	public Incident(String incidentId, String enterpriseOrGovernment, String reporterName, String incidentDetails,
			LocalDate reportedDateTime, String priority, String status) {
		super();
		this.incidentId = incidentId;
		this.enterpriseOrGovernment = enterpriseOrGovernment;
		this.reporterName = reporterName;
		this.incidentDetails = incidentDetails;
		this.reportedDateTime = reportedDateTime;
		this.priority = priority;
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

}
