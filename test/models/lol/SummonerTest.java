package models.lol;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SummonerTest {

    @Test
    public void testGet() {
        Summoner summoner = new Summoner();
        summoner.name = "episkipoe";
        summoner.updateStatus();
        System.out.println(summoner.name + " is level " + summoner.level);
        assertEquals(summoner.level, summoner.getLights().size());
    }

}