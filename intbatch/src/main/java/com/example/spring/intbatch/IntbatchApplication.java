package com.example.spring.intbatch;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.spring.intbatch.dbtool.GenerateSourceDatabase;

@SpringBootApplication
public class IntbatchApplication {

	public static void main(String[] args) throws Exception {
		if ((args.length > 0) && ("initdb".compareTo(args[0]) == 0)) {
			System.out.println("Initialisation de la base de données...");
			GenerateSourceDatabase.main(args);
			System.out.println("...terminé.");
		} else {
			CommandLineJobRunner.main(args);
		}
	}

}
