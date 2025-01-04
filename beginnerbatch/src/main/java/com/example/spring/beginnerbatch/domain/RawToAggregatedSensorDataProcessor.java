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
		Double min = item.temperatures().get(0);
		Double max = min;
		Double sum = 0d;
		
		for(Double temp : item.temperatures()) {
			min = Math.min(min, temp);
			max = Math.max(max, temp);
			sum += temp;
		}
		
		return new DailyAggregatedSensorData(item.date(),
				farenheitToCelsius(min),
				farenheitToCelsius(max),
				farenheitToCelsius(sum/item.temperatures().size()));
	}
	
	private static Double farenheitToCelsius(Double temp) {
		return (temp-32)*5/9;
	}

}
