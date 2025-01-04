package com.example.spring.beginnerbatch.dto;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import org.springframework.oxm.xstream.XStreamMarshaller;

import com.thoughtworks.xstream.security.ExplicitTypePermission;

/**
 * Classe contenant les données aggrégées d'un capteur pour une journée donnée
 * 
 * Paramètres:
 * 	date: la date de capture des données
 * 	min: la température minimale de la journée
 * 	max: la température maximale de la journée
 *  avg: la température moyenne de la journée
 */
public class DailyAggregatedSensorData {
	public static final String ITEM_ROOTELEMENT_NAME = "daily-data";
	
	private final LocalDate date;
	private final Double min;
	private final Double max;
	private final Double avg;

	public DailyAggregatedSensorData(LocalDate date, Double min, Double max, Double avg) {
		this.date = date;
		this.min = min;
		this.max = max;
		this.avg = avg;
	}
	
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

	public LocalDate getDate() {
		return date;
	}

	public Double getMin() {
		return min;
	}

	public Double getMax() {
		return max;
	}

	public Double getAvg() {
		return avg;
	}

	@Override
	public String toString() {
		return "DailyAggregatedSensorData [date=" + date + ", min=" + min + ", max=" + max + ", avg=" + avg + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(avg, date, max, min);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DailyAggregatedSensorData other = (DailyAggregatedSensorData) obj;
		return Objects.equals(avg, other.avg) && Objects.equals(date, other.date) && Objects.equals(max, other.max)
				&& Objects.equals(min, other.min);
	}
	
}
