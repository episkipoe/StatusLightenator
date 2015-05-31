package models.cards;

import com.episkipoe.deck.Card;
import com.episkipoe.games.war.War;
import com.google.gson.Gson;
import models.lights.GeneratesLights;
import models.lights.Light;
import org.apache.commons.io.FileUtils;
import play.Logger;
import play.data.validation.Constraints.Required;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CARDS")
public class CardGames extends GeneratesLights {
	@Required
	public String baseURL;

	@Column(columnDefinition = "TEXT")
	public String gameState;

	private static final String GAME = "com.episkipoe.games.war.War";

	private int intensity(Card card) {
		return (int)Math.floor(card.getValue().getValueAcesHigh() / 14.0 * 255);
	}

	@Override
	public List<Light> getLights() {
		List<Light> lights = new ArrayList<>();
		if (gameState != null) {
			War game = new Gson().fromJson(gameState, War.class);
			for (Card card : game.getPlayerOne().getCards()) {
				lights.add(new Light(0, intensity(card), 0));
			}
			lights.add(Light.WHITE);
			for (Card card : game.getPlayerTwo().getCards()) {
				lights.add(new Light(intensity(card), 0, 0));
			}
		}
		return lights;
	}

	@Override
	public void updateStatus() {
		if (gameState == null) {
			startNewGame();
		} else {
			nextRound();
		}
		update();
	}

	private void startNewGame() {
		try {
			URL startGame = new URL(baseURL + "/card-games-server/games/" + GAME + "?data=new");
			Logger.debug("Starting game " + startGame.toString());
			Path tempJSON = Files.createTempFile("gameState", ".json");
			FileUtils.copyURLToFile(startGame, tempJSON.toFile());
			gameState = FileUtils.readFileToString(tempJSON.toFile());
			Files.delete(tempJSON);
		} catch (IOException e) {
			Logger.error("Could not start card game", e);
		}
	}

	private void nextRound() {
		try {
			URL startGame = new URL(baseURL + "/card-games-server/games/" + GAME);
			URLConnection connection = startGame.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
				out.write("data=" + gameState);
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					gameState = reader.readLine();
				}
			}
		} catch (IOException e) {
			Logger.error("Could not start card game", e);
		}
	}
}
