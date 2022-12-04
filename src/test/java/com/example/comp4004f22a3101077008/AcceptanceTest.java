package com.example.comp4004f22a3101077008;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;


import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;

@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class)
public class AcceptanceTest {
    @Autowired
    GameData gd;
    @Autowired
    GameLogic game;
    @LocalServerPort
    int port;

    ArrayList<WebDriver> drivers;
    ChromeOptions chromeOptions;
    @BeforeEach
    public void loadWebDriver(){
        //System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        chromeOptions = new ChromeOptions();
        drivers = new ArrayList<>();
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(20));
        chromeOptions.setPageLoadTimeout(Duration.ofSeconds(20));
        WebDriver driver1 = new ChromeDriver(chromeOptions);
        drivers.add(driver1);
        WebDriver driver2 = new ChromeDriver(chromeOptions);
        WebDriver driver3 = new ChromeDriver(chromeOptions);
        WebDriver driver4 = new ChromeDriver(chromeOptions);

        drivers.add(driver2);
        drivers.add(driver3);
        drivers.add(driver4);
    }
    @AfterEach
    public void close(){
        for(WebDriver d : drivers){

            d.quit();
        }
    }
    @Test
    @DirtiesContext
    public void testRow41(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        d1.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        d2.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        d3.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        d4.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow41();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        d1.findElement(By.id("3C")).click();
        String topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("3C",topID);

        topID = d2.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("3C",topID);
        topID = d3.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("3C",topID);
        topID = d4.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("3C",topID);

        assertEquals("Turn: 2",d1.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 2",d2.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 2",d3.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 2",d4.findElement(By.id("turnID")).getText());
        assertTrue(d2.findElement(By.id("draw")).isEnabled());
    }

    @Test
    @DirtiesContext
    public void testRow42(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        d1.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        d2.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        d3.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        d4.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow42();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        d1.findElement(By.id("AH")).click();
        String topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("AH",topID);

        topID = d2.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("AH",topID);
        topID = d3.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("AH",topID);
        topID = d4.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("AH",topID);

        String direction = d1.findElement(By.id("direction")).getText();
        assertEquals("right",direction);
        direction = d2.findElement(By.id("direction")).getText();
        assertEquals("right",direction);
        direction = d3.findElement(By.id("direction")).getText();
        assertEquals("right",direction);
        direction = d3.findElement(By.id("direction")).getText();
        assertEquals("right",direction);

        assertEquals("Turn: 4",d1.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 4",d2.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 4",d3.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 4",d4.findElement(By.id("turnID")).getText());
        assertTrue(d4.findElement(By.id("draw")).isEnabled());
        d4.findElement(By.id("7H")).click();
        assertEquals("Turn: 3",d1.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 3",d2.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 3",d3.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 3",d4.findElement(By.id("turnID")).getText());
        assertTrue(d3.findElement(By.id("draw")).isEnabled());

    }

    @Test
    @DirtiesContext
    public void testRow44(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        d1.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        d2.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        d3.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        d4.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow44();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        d1.findElement(By.id("QC")).click();
        String topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("QC",topID);

        topID = d2.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("QC",topID);
        topID = d3.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("QC",topID);
        topID = d4.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("QC",topID);

        assertEquals("Turn: 3",d1.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 3",d2.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 3",d3.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 3",d4.findElement(By.id("turnID")).getText());
        assertFalse(d2.findElement(By.id("draw")).isEnabled());
        assertFalse(d1.findElement(By.id("draw")).isEnabled());
        assertTrue(d3.findElement(By.id("draw")).isEnabled());
    }

    @Test
    @DirtiesContext
    public void testRow45(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        d1.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        d2.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        d3.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        d4.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow45();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        d1.findElement(By.id("QC")).click();
        String topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("QC",topID);

        topID = d2.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("QC",topID);
        topID = d3.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("QC",topID);
        topID = d4.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("QC",topID);

        assertFalse(d2.findElement(By.id("draw")).isEnabled());
        assertTrue(d3.findElement(By.id("draw")).isEnabled());

        d3.findElement(By.id("4C")).click();
        topID = d3.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("4C",topID);

        d4.findElement(By.id("3C")).click();
        topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("3C",topID);
        assertEquals("Turn: 1",d1.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 1",d2.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 1",d3.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 1",d4.findElement(By.id("turnID")).getText());
        assertTrue(d1.findElement(By.id("draw")).isEnabled());
    }

    @Test
    @DirtiesContext
    public void testRow46(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        d1.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        d2.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        d3.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        d4.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow46();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        d1.findElement(By.id("QH")).click();
        String topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("QH",topID);

        d3.findElement(By.id("JH")).click();
        topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("JH",topID);

        d4.findElement(By.id("AH")).click();
        topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("AH",topID);

        assertEquals("Turn: 3",d1.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 3",d2.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 3",d3.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 3",d4.findElement(By.id("turnID")).getText());

        assertEquals("right",d1.findElement(By.id("direction")).getText());
        assertEquals("right",d2.findElement(By.id("direction")).getText());
        assertEquals("right",d3.findElement(By.id("direction")).getText());
        assertEquals("right",d4.findElement(By.id("direction")).getText());

        d3.findElement(By.id("7H")).click();
        topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("7H",topID);

        assertEquals("Turn: 2",d1.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 2",d2.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 2",d3.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 2",d4.findElement(By.id("turnID")).getText());
    }
    @Test
    @DirtiesContext
    public void testRow48(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        d1.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        d2.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        d3.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        d4.findElement(By.id("usernameBtn")).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow48();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        d1.findElement(By.id("3H")).click();
        String topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("3H",topID);

        d2.findElement(By.id("3C")).click();
        topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("3C",topID);

        d3.findElement(By.id("4C")).click();
        topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("4C",topID);

        d4.findElement(By.id("QC")).click();
        topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("QC",topID);

        assertEquals("Turn: 2",d1.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 2",d2.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 2",d3.findElement(By.id("turnID")).getText());
        assertEquals("Turn: 2",d4.findElement(By.id("turnID")).getText());
    }

    public void rigTestRow41(){
        String rigC = "7C AH 9H 3C 2C 5H 4C JS 9D TH KS TS TD 8C 9C 4S 7H AS TC 9S 2D";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
    }

    public void rigTestRow42(){
        String rigC = "4H AH 9H 3C 2C 5H 4C JS 9D TH KS TS TD 8C 9C 4S 7H AS TC 9S 2D";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
    }

    public void rigTestRow44(){
        String rigC = "7C 5C QC AH 9D 6D 6C 4H 7H 9D JS 4C 9H 2H TD JH KS 3C 4S 6S 5S";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
    }
    public void rigTestRow45(){
        String rigC = "7C 5C QC AH 9D 6D 6C 4H 7H 9D JS 4C 9H 2H TD JH KS 3C 4S 6S 5S";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
    }

    public void rigTestRow46(){
        String rigC = "QC 5C QH 8H 9D 6D 6C 4H 7H 9D JS 4C 9H 7H TD JH KS 3C 4S AH 5S";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
    }
    public void rigTestRow48(){
        String rigC = "7H 5C 3H 8H 9D 6D 3C 4H 7H 9D JS 4C 9H 7H TD JH KS QC 4S AH 5S";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
    }
    public ArrayList<Card> stringToArray(String cards){
        ArrayList<Card> rig = new ArrayList<>();
        for(String car:cards.split(" ")){
            String suit = car.split("")[1];
            String rank = car.split("")[0];
            Card tmpC = new Card(suit,rank);
            rig.add(tmpC);
        }
        return rig;
    }
}
