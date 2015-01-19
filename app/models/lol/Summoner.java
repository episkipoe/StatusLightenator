package models.lol;

import constant.Region;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;
import models.lights.GeneratesLights;
import models.lights.Light;
import play.data.validation.Constraints.Required;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Display the level of the League of Legend summoner as a string of green lights <br />
 * The environment variable RIOT_API_KEY must be set
 */
@Entity
@DiscriminatorValue("SUMMONER")
public class Summoner extends GeneratesLights {
	@Id
	public Long id;

	@Required
	public String name;
	public long level;
	private static final String API_KEY = "RIOT_API_KEY";

	public static boolean isAvailable() {
		return (System.getenv(API_KEY ) != null);
	}

	private static String getApiKey() {
		String apiKey = System.getenv(API_KEY);
		if (apiKey == null) throw new IllegalStateException(API_KEY + " is not set");
		return apiKey;
	}

	@Override
	public List<Light> getLights() {
		List<Light> lights = new ArrayList<>();
		for (long i = 0; i < level; i++) {
			lights.add(Light.GREEN);
		}
		return lights;
	}

	@Override
	public void updateStatus() {
		RiotApi api = new RiotApi(getApiKey());
		api.setRegion(Region.NA);
		try {
			Map<String, dto.Summoner.Summoner> summonerMap = api.getSummonerByName(name);
			dto.Summoner.Summoner summoner = summonerMap.get(name);
			level = summoner.getSummonerLevel();
			update();
		} catch (RiotApiException e) {
			e.printStackTrace();
		}
	}
}
