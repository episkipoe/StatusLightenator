package models.lol;

import org.junit.Test;

import static org.junit.Assert.*;

public class SummonerTest {

    @Test
    public void testGet() {
        Summoner summoner = new Summoner();
        summoner.name = "episkipoe";
        summoner.load();
    }

}