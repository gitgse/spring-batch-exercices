package com.example.spring.beginnerbatch.dto;

import java.time.LocalDate;

/**
 * Record portant les données relatives à une anomalie
 * dans les relevés d'un capteur
 * 
 * Paramètres:
 * 	date: la date à laquelle s'est produite l'anomalie
 * 	type: le type d'anomalie, c'est-à-dire si l'anomalie concerne le minimum ou le maximum
 * 	anomalyValue: la valeur du relevé en anomalie, c'est-à-dire la valeur du minimum
 * 		ou du maximum selon le cas
 */
public record AnomalyData(LocalDate date, AnomalyType type, Double value) {

	public enum AnomalyType {
		MINIMUM,
		MAXIMUM
	}
	
}
