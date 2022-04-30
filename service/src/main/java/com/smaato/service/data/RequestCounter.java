package com.smaato.service.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RequestCounter {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="request_id", unique=true)
	private int requestId;
	
	@Column(name="request_endpoint")
	private String requestEndpoint;
	
	protected RequestCounter() {
		
	}
	
	public RequestCounter(int requestId, String requestEndpoint) {
		this.requestId = requestId;
		this.requestEndpoint = requestEndpoint;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getRequestEndpoint() {
		return requestEndpoint;
	}

	public void setRequestEndpoint(String requestEndpoint) {
		this.requestEndpoint = requestEndpoint;
	}
	
}
