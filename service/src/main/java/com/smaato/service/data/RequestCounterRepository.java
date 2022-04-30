package com.smaato.service.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestCounterRepository extends JpaRepository<RequestCounter, Integer> {
	
	public RequestCounter findByRequestId(int requestId);
	
}
