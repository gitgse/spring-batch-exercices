package com.example.spring.beginnerbatch.domain;

import org.springframework.batch.item.ItemProcessor;

import com.example.spring.beginnerbatch.dto.DailyAggregatedSensorData;
import com.example.spring.beginnerbatch.dto.DailySensorData;

/**
 * Classe permettant de passer du format brut des données de capteur au format
 * aggrégé
 */
public class RawToAggregatedSensorDataProcessor implements ItemProcessor<DailySensorData, DailyAggregatedSensorData> {

	@Override
	public DailyAggregatedSensorData process(DailySensorData item) throws Exception {
		return new DailyAggregatedSensorData(
				item.date(),
				item.temperatures().stream()
					.mapToDouble(d -> d)
					.min()
					.orElseThrow(),
				item.temperatures().stream()
					.mapToDouble(d -> d)
					.max()
					.orElseThrow(),
				item.temperatures().stream()
					.mapToDouble(d -> d)
					.average()
					.orElseThrow());
	}

}
