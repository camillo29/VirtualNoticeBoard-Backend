package com.VirtualNoticeBoardBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class VirtualNoticeBoardBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualNoticeBoardBackendApplication.class, args);
	}

}
