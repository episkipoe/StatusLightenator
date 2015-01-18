package models.lol;

import constant.Region;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

@Entity
@DiscriminatorValue("SUMM")
public class Summoner extends Model {
	@Id
	public Long id;

	@Constraints.Required
	public String name;

	private dto.Summoner.Summoner summoner;

	private String getApiKey() {
		String apiKey = System.getenv("RIOT_API_KEY");
		if (apiKey == null) throw new IllegalStateException("RIOT_API_KEY is not set");
		return apiKey;
	}

	public void load() {
		RiotApi api = new RiotApi(getApiKey());
		api.setRegion(Region.NA);
		try {
			Map<String, dto.Summoner.Summoner> summonerMap = api.getSummonerByName(name);
			summoner = summonerMap.get(name);
			System.out.println(summoner.getName() + " is level " + summoner.getSummonerLevel());
		} catch (RiotApiException e) {
			e.printStackTrace();
		}
	}
}
