package com.smaato.service.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.smaato.service.data.RequestCounter;
import com.smaato.service.data.RequestCounterService;


@RestController
@RequestMapping("/v1/api")
public class RESTController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RequestCounterService requestCounterService;
	
	//@Value("{kafka.topic.publish}")
	//private String kafkaTopic;
	
	//@Autowired
	//private KafkaTemplate<String, String> kafkaTemplate;
	
	@GetMapping("/request/count")
	public ResponseEntity<String> getRequestCount(@RequestParam int id, @RequestParam(required = false) String endpoint) {
		logger.info("Invoked request count api with id {}, and endpoint {}", id, endpoint);
		try {
			logger.info("Saving new request with id {}, and endpoint {}", id, endpoint);
			requestCounterService.save(new RequestCounter(id, endpoint));
			
			if (StringUtils.isNotEmpty(endpoint)) {
				try {
					logger.info("Invoking the endpoint {}", endpoint);
					RestTemplate restTemplate = new RestTemplate();
					ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);
					logger.info("Executed the endpoint {} with response {}", endpoint, response.getBody());
				}
				catch(Exception ex) {
					logger.info("Exception occured while invoking the endpoint", ex);
					return ResponseEntity.ok("FAILED");
				}
			}

			return ResponseEntity.ok("OK");	
		}
		catch(Exception e) {
			logger.info("Exception occured while while saving the request record", e);
			return ResponseEntity.ok("FAILED");
		}
	}
	
	@Scheduled(cron = "0 * * * * *")
	public void triggerEvent() {
		int uniqueRecordize = requestCounterService.getDistinctRequestCount();
		
		Date currentTime = new Date(System.currentTimeMillis());
		DateFormat dateFormat = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
		logger.info("Unique request count at timestamp {} - {}", dateFormat.format(currentTime), uniqueRecordize);
		
		// Sending the unique request count to kafka topic
		// kafkaTemplate.send(kafkaTopic, String.valueOf(uniqueRecordize));
	}

}
