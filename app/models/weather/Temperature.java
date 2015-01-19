package models.weather;

import com.google.gson.Gson;
import models.lights.GeneratesLights;
import models.lights.Light;
import org.apache.commons.io.FileUtils;
import play.Logger;
import play.data.validation.Constraints.Required;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Display the temperature as a string of lights. <br />
 * Positive temperatures are displayed in red, negative temperatures in blue <br />
 * Weather data is downloaded from openweathermap.org <br />
 * The environment variable OPEN_WEATHER_KEY must be set
 */
@Entity
@DiscriminatorValue("TEMP")
public class Temperature extends GeneratesLights {
	@Required
	public String city;
	public float temperature;

	private static final String ENV_VAR = "OPEN_WEATHER_KEY";

	public static boolean isAvailable() {
		return (System.getenv(ENV_VAR) != null);

	}

	private static String getApiKey() {
		String apiKey = System.getenv(ENV_VAR);
		if (apiKey == null) throw new IllegalStateException(ENV_VAR + " is not set");
		return apiKey;
	}

	@Override
	public void updateStatus() {
		temperature = 0.0f;
		try {
			updateFromOpenWeatherMap();
		} catch (IOException e) {
			System.out.println("Could not update temperature: " + e.getMessage());
		}
	}

	/**
	 * Class to get OpenWeatherMap data from JSON
	 */
	private static class WeatherData {
		private static class Main {
			/**
			 * In Kelvins
			 */
			public String temp;
		}

		Main main;
	}

	private void updateFromOpenWeatherMap() throws IOException {
		URL getWeather = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + getApiKey());
		Logger.debug("Updating weather from " + getWeather.toString());
		Path tempJSON = Files.createTempFile("weather", ".json");
		FileUtils.copyURLToFile(getWeather, tempJSON.toFile());
		String weatherJson = FileUtils.readFileToString(tempJSON.toFile());
		Files.delete(tempJSON);
		setWeatherFromJsonString(weatherJson);
		update();
	}

	public void setWeatherFromJsonString(String json) {
		WeatherData weatherData = new Gson().fromJson(json, WeatherData.class);
		if (weatherData == null || weatherData.main == null) {
			temperature = 0.0f;
			city = city + " (not found)";
		} else {
			String temperatureInKelvins = weatherData.main.temp;
			//noinspection MagicNumber
			temperature = (Float.valueOf(temperatureInKelvins) - 273.15f) * 1.8f + 32.0f;
		}
	}

	@Override
	public List<Light> getLights() {
		List<Light> lights = new ArrayList<>();
		if (temperature > 1) {
			for (int i = 0; i < Math.round(temperature); i++) {
				lights.add(Light.RED);
			}
		} else if (temperature < 1) {
			for (int i = 0; i < Math.round(Math.abs(temperature)); i++) {
				lights.add(Light.BLUE);
			}
		} else {
			lights.add(Light.WHITE);
		}
		return lights;
	}

}
