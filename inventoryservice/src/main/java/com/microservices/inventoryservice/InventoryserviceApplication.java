package com.microservices.inventoryservice;

import com.microservices.inventoryservice.controller.InventoryController;
import com.microservices.inventoryservice.service.InventoryService;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class InventoryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryserviceApplication.class, args);
	}

	@Bean
	public List<ToolCallback> inventoryTools(InventoryController inventoryController){
		return List.of(ToolCallbacks.from(inventoryController));
	}

}
