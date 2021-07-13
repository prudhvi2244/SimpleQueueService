package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(exclude = ContextStackAutoConfiguration.class)
@RestController
@Slf4j
public class SimpleQueueServiceApplication {
	
	@Autowired
	private QueueMessagingTemplate qtemplate;
	
	@Value("${cloud.aws.end-point.uri}")
	private String endpoint;
	
	/*
	 * http://localhost:8080/sendMessage/Welcome To AWS SQS
	 */
	@GetMapping(value = "/sendMessage/{message}")
	public String sendMessage(@PathVariable String message)
	{
		qtemplate.send(endpoint,MessageBuilder.withPayload(message).build());
		String result="<h1 style='color:green;text-align:center'>Message Send Successfuly</h1>";
		return result;
	}
	
	@SqsListener("IJ028-Queue2")
	public void readMessagesFromQueue(String message)
	{
		log.info("Message From Queue : IJ028-Queue2 is :{}",message);
	}
	
	

	public static void main(String[] args) {
		SpringApplication.run(SimpleQueueServiceApplication.class, args);
	}

}
