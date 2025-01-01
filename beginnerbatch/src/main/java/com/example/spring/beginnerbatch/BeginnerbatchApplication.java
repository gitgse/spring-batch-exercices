package com.example.spring.beginnerbatch;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeginnerbatchApplication {

	public static void main(String[] args) throws Exception {
		CommandLineJobRunner.main(args);
	}

}
