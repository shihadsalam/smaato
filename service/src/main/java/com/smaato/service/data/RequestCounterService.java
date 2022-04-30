package com.smaato.service.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestCounterService {

	@Autowired
	private RequestCounterRepository requestCounterRepository;
	
	public RequestCounter getRequestCounter(int requestId) {
		return requestCounterRepository.findByRequestId(requestId);
	}
	
	public void save(RequestCounter reqCounter) {
		requestCounterRepository.save(reqCounter);
	}
	
	public int getDistinctRequestCount() {
		int result = 0;
		List<RequestCounter> listRequest = requestCounterRepository.findAll();
		if (null != listRequest) {
			result = listRequest.size();
		}
		return result; 
	}
}
