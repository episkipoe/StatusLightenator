package models.weather;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TemperatureTest {

	@Test
	public void testParsingJson() {
		String testJson = "{\"coord\":{\"lon\":-90.2,\"lat\":38.63},\"sys\":{\"message\":0.0195,\"country\":\"United States of America\",\"sunrise\":1421586936,\"sunset\":1421622426},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"Sky is Clear\",\"icon\":\"01d\"}],\"base\":\"cmc stations\",\"main\":{\"temp\":284.985,\"temp_min\":284.985,\"temp_max\":284.985,\"pressure\":1006.49,\"sea_level\":1028.61,\"grnd_level\":1006.49,\"humidity\":42},\"wind\":{\"speed\":2.91,\"deg\":258.004},\"clouds\":{\"all\":0},\"dt\":1421616292,\"id\":4407084,\"name\":\"St Louis\",\"cod\":200}\n";
		Temperature temperature = new Temperature();
		temperature.setWeatherFromJsonString(testJson);
		assertEquals(53.303, temperature.temperature, 0.01);
	}

	@Test
	public void testDownload() throws IOException {
		Temperature temperature = new Temperature();
		temperature.city = "St. Louis, MO";
		temperature.updateStatus();
		System.out.println("The current temperature in " + temperature.city + " is " + temperature.temperature);

	}
}