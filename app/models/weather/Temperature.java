package models.weather;

import models.lights.GeneratesLights;
import models.lights.Light;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("TEMP")
public class Temperature extends GeneratesLights {


	private float temperature;

	public Temperature() {
		if (System.getenv("OPEN_WEATHER_KEY") != null) {
			//get temperature from openweathermap.org
		}
		temperature = 32;
	}

	@Override
	public List<Light> getLights() {
		List<Light> lights = new ArrayList<>();
		if (temperature > 0) {
			for (int i = 0; i < Math.round(temperature); i++) {
				lights.add(Light.RED);
			}
		} else {
			for (int i = 0; i < Math.round(Math.abs(temperature)); i++) {
				lights.add(Light.BLUE);
			}
		}
		return lights;
	}

}
