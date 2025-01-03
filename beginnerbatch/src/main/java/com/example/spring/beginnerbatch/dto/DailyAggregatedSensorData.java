package com.example.spring.beginnerbatch.dto;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.oxm.xstream.XStreamMarshaller;

import com.thoughtworks.xstream.security.ExplicitTypePermission;

/**
 * Record contenant les données aggrégées d'un capteur pour une journée donnée
 * 
 * Paramètres:
 * 	date: la date de capture des données
 * 	min: la température minimale de la journée
 * 	max: la température maximale de la journée
 *  avg: la température moyenne de la journée
 */
public record DailyAggregatedSensorData(LocalDate date, Double min, Double max, Double avg) {

	public static final String ITEM_ROOTELEMENT_NAME = "daily-data";
	
	public static XStreamMarshaller getMarshaller() {
		XStreamMarshaller marshaller = new XStreamMarshaller();
		
		Map<String, Class<?>> aliases =  Map.of(
				ITEM_ROOTELEMENT_NAME, DailyAggregatedSensorData.class,
				"date", LocalDate.class,
				"min", Double.class,
				"max", Double.class,
				"avg", Double.class
				);
		
		ExplicitTypePermission typePermission = new ExplicitTypePermission(
				new Class[] { DailyAggregatedSensorData.class }
				);
		
		marshaller.setAliases(aliases);
		marshaller.setTypePermissions(typePermission);
		
		return marshaller;
	}
	
}
