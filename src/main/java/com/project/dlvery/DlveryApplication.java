package com.project.dlvery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.project.dlvery.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class DlveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DlveryApplication.class, args);
	}

}
