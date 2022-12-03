package com.example.comp4004f22a3101077008;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.testng.annotations.BeforeClass;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;

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
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(10));
        chromeOptions.setPageLoadTimeout(Duration.ofSeconds(10));
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

        assertTrue(d2.findElement(By.id("draw")).isEnabled());
    }

    public void rigTestRow41(){
        Card top = new Card("C","7");
        Card p1c1 = new Card("C","3");
        Card p1c2 = new Card("H","9");
        Card p1c3 = new Card("H","A");
        Card p1c4 = new Card("D","9");
        Card p1c5 = new Card("S","J");
        Card p2c1 = new Card("C","4");
        Card p2c2 = new Card("H","9");
        Card p2c3 = new Card("H","7");
        Card p2c4 = new Card("D","9");
        Card p2c5 = new Card("S","J");
        Card p3c1 = new Card("C","3");
        Card p3c2 = new Card("H","9");
        Card p3c3 = new Card("H","A");
        Card p3c4 = new Card("D","9");
        Card p3c5 = new Card("S","J");
        Card p4c1 = new Card("C","3");
        Card p4c2 = new Card("H","9");
        Card p4c3 = new Card("H","A");
        Card p4c4 = new Card("D","9");
        Card p4c5 = new Card("S","J");
        ArrayList<Card> rigCard= new ArrayList<>();
        rigCard.add(top);
        rigCard.add(p1c1);
        rigCard.add(p1c2);
        rigCard.add(p1c3);
        rigCard.add(p1c4);
        rigCard.add(p1c5);

        rigCard.add(p2c1);
        rigCard.add(p2c2);
        rigCard.add(p2c3);
        rigCard.add(p2c4);
        rigCard.add(p2c5);

        rigCard.add(p2c1);
        rigCard.add(p3c2);
        rigCard.add(p3c3);
        rigCard.add(p3c4);
        rigCard.add(p3c5);

        rigCard.add(p4c1);
        rigCard.add(p4c2);
        rigCard.add(p4c3);
        rigCard.add(p4c4);
        rigCard.add(p4c5);

        gd.setCards(rigCard);
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
    }
}
