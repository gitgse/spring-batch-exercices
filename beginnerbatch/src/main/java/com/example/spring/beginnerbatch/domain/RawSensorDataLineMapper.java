package com.example.spring.beginnerbatch.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.batch.item.file.LineMapper;

import com.example.spring.beginnerbatch.dto.DailySensorData;

/**
 * Classe implémentant le mapping entre une ligne de données de capteur
 * et une instance de la classe DailySensorData
 */
public class RawSensorDataLineMapper implements LineMapper<DailySensorData> {

	@Override
	public DailySensorData mapLine(String line, int lineNumber) throws Exception {
		var dateToTemperatureString = line.split(":");
		var date = LocalDate.parse(dateToTemperatureString[0],
				DateTimeFormatter.ofPattern("MM-dd-uuuu"));
		
		var temperaturesString = dateToTemperatureString[1].split(",");
		var temperatures = new ArrayList<Double>();
		for(var temperatureString : temperaturesString) {
			temperatures.add(Double.parseDouble(temperatureString));
		}
		
		return new DailySensorData(date, temperatures);
	}

}
