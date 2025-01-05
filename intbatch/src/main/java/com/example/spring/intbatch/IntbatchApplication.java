package com.example.spring.intbatch;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntbatchApplication {

	public static void main(String[] args) throws Exception {
		CommandLineJobRunner.main(args);
	}

}
