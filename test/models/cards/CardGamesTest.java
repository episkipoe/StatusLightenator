package models.cards;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CardGamesTest {
	@Test
	public void testServer() {
		CardGames game = new CardGames();
		game.baseURL = "http://localhost:9090";
		assertNull(game.gameState);
		game.updateStatus();
		assertNotNull(game.gameState);
		assertEquals(53, game.getLights().size());
	}

}