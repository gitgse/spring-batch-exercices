package com.example.spring.beginnerbatch.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe contenant les données brutes relevées par un capteur pour une journée donnée
 *
 * Paramètres:
 * 	date: la date à laquelle ont été effectués les relevés
 *  temperatures: la liste des températures, en degrés Farenheit, relevées par le capteur
 */
public record DailySensorData(LocalDate date, List<Double> temperatures) {
}
