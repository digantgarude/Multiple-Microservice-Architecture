package com.microservices.bookingservice;

import com.microservices.bookingservice.controller.BookingController;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingServiceApplication.class, args);
	}

	@Bean
	public List<ToolCallback> bookingTools(BookingController bookingController){
		return List.of(ToolCallbacks.from(bookingController));
	}
}
