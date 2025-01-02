package com.example.spring.beginnerbatch.dto;

import java.time.LocalDate;

/**
 * Record contenant les données aggrégées d'un capteur pour une journée donnée
 * 
 * Paramètres:
 * 	date: la date de capture des données
 * 	minT: la température minimale de la journée
 * 	maxT: la température maximale de la journée
 *  avgT: la température moyenne de la journée
 */
public record DailyAggregatedSensorData(LocalDate date, Double minT, Double maxT, Double avgT) {
}
