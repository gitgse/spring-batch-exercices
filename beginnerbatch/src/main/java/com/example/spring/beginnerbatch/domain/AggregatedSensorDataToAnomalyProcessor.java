package com.example.spring.beginnerbatch.domain;

import org.springframework.batch.item.ItemProcessor;

import com.example.spring.beginnerbatch.dto.AnomalyData;
import com.example.spring.beginnerbatch.dto.AnomalyData.AnomalyType;
import com.example.spring.beginnerbatch.dto.DailyAggregatedSensorData;

/**
 * Classe de conversion d'une donnée aggrégée d'un capteur vars une anomalie, le
 * cas échéant. Filtre les éléments qui ne sont pas en anomalie (en renvoyant
 * null)
 */
public class AggregatedSensorDataToAnomalyProcessor implements ItemProcessor<DailyAggregatedSensorData, AnomalyData> {

	private static final Double THRESHOLD = 0.9;

	@Override
	public AnomalyData process(DailyAggregatedSensorData item) throws Exception {
		if ((item.getMin() / item.getAvg()) < THRESHOLD) {
			return new AnomalyData(item.getDate(), AnomalyType.MINIMUM, item.getMin());
		} else if ((item.getAvg() / item.getMax()) < THRESHOLD) {
			return new AnomalyData(item.getDate(), AnomalyType.MAXIMUM, item.getMax());
		} else {
			// En retournant null on filtre l'item (ie pas d'anomalie à cette date)s
			return null;
		}
	}

}
