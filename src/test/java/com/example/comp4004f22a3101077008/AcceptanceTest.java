package com.example.comp4004f22a3101077008;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
        System.setProperty("webdriver.chrome.driver","C:\\Users\\abhai\\Downloads\\chromedriver-win64\\chromedriver.exe");

        chromeOptions = new ChromeOptions();
        drivers = new ArrayList<>();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(600));
        chromeOptions.setPageLoadTimeout(Duration.ofSeconds(1000));

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

        //Before clicking the register button make the selenium wait till register button is clickable this is because it needs to wait till client is connected to server
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
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
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
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
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
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
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
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
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
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
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
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

    @Test
    @DirtiesContext
    public void testRow51(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow51();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("KC",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("KH")).click();
        String topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("KH",topID);
    }
    @Test
    @DirtiesContext
    public void testRow52(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow52();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("KC",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("7C")).click();
        String topID = d1.findElement(By.className("topCard")).getAttribute("id");
        assertEquals("7C",topID);
    }
    @Test
    @DirtiesContext
    public void testRow53(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow53();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("KC",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("8H")).click();
        assertTrue(d1.findElement(By.id("spade")).isDisplayed());
        assertTrue(d1.findElement(By.id("heart")).isDisplayed());
        assertTrue(d1.findElement(By.id("club")).isDisplayed());
        assertTrue(d1.findElement(By.id("diamond")).isDisplayed());
    }
    @Test
    @DirtiesContext
    public void testRow54() throws InterruptedException {
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow54();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("KC",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("5S")).click();
        Thread.sleep(2000);
        System.out.println("Alert: "+d1.switchTo().alert().getText());
        assertEquals("Invalid Selection",d1.switchTo().alert().getText());
    }

    @Test
    @DirtiesContext
    public void testRow58(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow58();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("7C",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("6C")).size()>0);
        d1.findElement(By.id("6C")).click();
        assertEquals("6C",d1.findElement(By.className("topCard")).getAttribute("id"));
    }
    @Test
    @DirtiesContext
    public void testRow59(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow59();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("7C",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("6D")).size()>0);
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("5C")).size()>0);
        d1.findElement(By.id("5C")).click();
        assertEquals("5C",d1.findElement(By.className("topCard")).getAttribute("id"));
    }
    @Test
    @DirtiesContext
    public void testRow60(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow60();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("7C",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("6D")).size()>0);
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("5S")).size()>0);
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("7H")).size()>0);
        d1.findElement(By.id("7H")).click();
        assertEquals("7H",d1.findElement(By.className("topCard")).getAttribute("id"));
    }
    @Test
    @DirtiesContext
    public void testRow61(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow61();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("7C",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("6D")).size()>0);
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("5S")).size()>0);
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("4H")).size()>0);
        assertFalse(d1.findElement(By.id("draw")).isEnabled());
        assertEquals("Turn: 2", d1.findElement(By.id("turnID")).getText());
    }
    @Test
    @DirtiesContext
    public void testRow62(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow62();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("7C",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("6D")).size()>0);
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("8H")).size()>0);
        d1.findElement(By.id("8H")).click();
        d1.findElement(By.id("spade")).click();
        assertEquals("S",d2.findElement(By.className("topCard")).getAttribute("id"));

    }
    @Test
    @DirtiesContext
    public void testRow63(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow63();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("7C",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("6C")).size()>0);
        d1.findElement(By.id("6C")).click();
        assertEquals("6C",d2.findElement(By.className("topCard")).getAttribute("id"));

    }
    @Test
    @DirtiesContext
    public void testRow67(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow67();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("7C",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("2C")).click();

        assertTrue(d2.findElements(By.id("6C")).size()>0);
        assertTrue(d2.findElements(By.id("9D")).size()>0);
        assertEquals("Turn: 2", d1.findElement(By.id("turnID")).getText());
        assertEquals("2C",d2.findElement(By.className("topCard")).getAttribute("id"));
        d2.findElement(By.id("6C")).click();
        assertEquals("6C",d2.findElement(By.className("topCard")).getAttribute("id"));
    }
    @Test
    @DirtiesContext
    public void testRow68(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow68();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("7C",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("2C")).click();

        assertEquals("Turn: 2", d1.findElement(By.id("turnID")).getText());
        assertEquals("2C",d2.findElement(By.className("topCard")).getAttribute("id"));
        assertTrue(d2.findElements(By.id("6S")).size()>0);
        assertTrue(d2.findElements(By.id("9D")).size()>0);
        d2.findElement(By.id("draw")).click();
        assertTrue(d2.findElements(By.id("6C")).size()>0);
        d2.findElement(By.id("draw")).click();
        assertTrue(d2.findElements(By.id("9D")).size()>0);
        d2.findElement(By.id("6C")).click();
        assertEquals("6C",d2.findElement(By.className("topCard")).getAttribute("id"));
    }
    @Test
    @DirtiesContext
    public void testRow69(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow69();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("7C",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("2C")).click();

        assertEquals("Turn: 2", d1.findElement(By.id("turnID")).getText());
        assertEquals("2C",d2.findElement(By.className("topCard")).getAttribute("id"));
        assertTrue(d2.findElements(By.id("6S")).size()>0);
        assertTrue(d2.findElements(By.id("9D")).size()>0);

        d2.findElement(By.id("draw")).click();
        assertTrue(d2.findElements(By.id("9D")).size()>0);
        d2.findElement(By.id("draw")).click();
        assertTrue(d2.findElements(By.id("7S")).size()>0);
        d2.findElement(By.id("draw")).click();
        assertTrue(d2.findElements(By.id("5H")).size()>0);

        assertFalse(d2.findElement(By.id("draw")).isEnabled());
        assertEquals("Turn: 3",d1.findElement(By.id("turnID")).getText());
    }
    @Test
    @DirtiesContext
    public void testRow70(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow70();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("AC",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("2C")).click();

        assertEquals("Turn: 2", d1.findElement(By.id("turnID")).getText());
        assertEquals("2C",d2.findElement(By.className("topCard")).getAttribute("id"));
        assertTrue(d2.findElements(By.id("2H")).size()>0);
        assertTrue(d2.findElements(By.id("9D")).size()>0);
        d2.findElement(By.id("2H")).click();

        assertEquals("2H",d3.findElement(By.className("topCard")).getAttribute("id"));
        assertEquals("Turn: 3",d3.findElement(By.id("turnID")).getText());
        assertTrue(d3.findElements(By.id("5S")).size()>0);
        assertTrue(d3.findElements(By.id("6D")).size()>0);
        assertTrue(d3.findElements(By.id("6H")).size()>0);
        assertTrue(d3.findElements(By.id("7C")).size()>0);
        d3.findElement(By.id("6H")).click();

        assertEquals("6H",d4.findElement(By.className("topCard")).getAttribute("id"));
        assertEquals("Turn: 4",d4.findElement(By.id("turnID")).getText());
    }
    @Test
    @DirtiesContext
    public void testRow72(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow72();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("7C",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("2C")).click();


        assertEquals("Turn: 2", d2.findElement(By.id("turnID")).getText());
        assertEquals("2C",d2.findElement(By.className("topCard")).getAttribute("id"));
        d2.findElement(By.id("4C")).click();
        assertEquals("4C",d2.findElement(By.className("topCard")).getAttribute("id"));
        assertEquals("Turn: 2", d2.findElement(By.id("turnID")).getText());
        d2.findElement(By.id("6C")).click();
        assertEquals("6C",d2.findElement(By.className("topCard")).getAttribute("id"));
        assertEquals("Turn: 3", d2.findElement(By.id("turnID")).getText());
    }
    @Test
    @DirtiesContext
    public void testRow73(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow73();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("7C",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("2C")).click();


        assertEquals("Turn: 2", d2.findElement(By.id("turnID")).getText());
        assertEquals("2C",d2.findElement(By.className("topCard")).getAttribute("id"));
        d2.findElement(By.id("4C")).click();
        assertEquals("4C",d2.findElement(By.className("topCard")).getAttribute("id"));
        assertEquals("Turn: 2", d2.findElement(By.id("turnID")).getText());
        d2.findElement(By.id("6C")).click();

        assertEquals("Player 2: 0",d2.findElement(By.id("p2")).getText());
        assertTrue(d2.findElement(By.id("startBtn")).isDisplayed());
    }
    @Test
    @DirtiesContext
    public void testRow77(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        //rig game
        rigTestRow77();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("7C",d1.findElement(By.className("topCard")).getAttribute("id"));
        d1.findElement(By.id("3C")).click();


        assertEquals("Turn: 2", d2.findElement(By.id("turnID")).getText());
        assertEquals("3C",d2.findElement(By.className("topCard")).getAttribute("id"));
        d2.findElement(By.id("4C")).click();

        assertEquals("Player 1: 1",d2.findElement(By.id("p1")).getText());
        assertEquals("Player 2: 0",d2.findElement(By.id("p2")).getText());
        assertEquals("Player 3: 86",d2.findElement(By.id("p3")).getText());
        assertEquals("Player 4: 102",d2.findElement(By.id("p4")).getText());

        assertFalse(d1.findElement(By.id("startBtn")).isDisplayed());
        assertFalse(d2.findElement(By.id("startBtn")).isDisplayed());
        assertFalse(d3.findElement(By.id("startBtn")).isDisplayed());
        assertFalse(d4.findElement(By.id("startBtn")).isDisplayed());

        assertEquals("WINNER IS PLAYER 2",d1.findElement(By.id("winMSG")).getAttribute("innerHTML"));
        assertEquals("WINNER IS PLAYER 2",d2.findElement(By.id("winMSG")).getAttribute("innerHTML"));
        assertEquals("WINNER IS PLAYER 2",d3.findElement(By.id("winMSG")).getAttribute("innerHTML"));
        assertEquals("WINNER IS PLAYER 2",d4.findElement(By.id("winMSG")).getAttribute("innerHTML"));
    }

    @Test
    @DirtiesContext
    public void testRow80(){
        WebDriver d1 = drivers.get(0);
        WebDriver d2 = drivers.get(1);
        WebDriver d3 = drivers.get(2);
        WebDriver d4 = drivers.get(3);
        d1.get("http://localhost:"+port);
        String text = d1.findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
        WebDriverWait wait = new WebDriverWait(d1, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 1",d1.findElement(By.id("playerID")).getText());

        d2.get("http://localhost:"+port);
        WebDriverWait wait2 = new WebDriverWait(d2, Duration.ofSeconds(100));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 2",d2.findElement(By.id("playerID")).getText());

        d3.get("http://localhost:"+port);
        WebDriverWait wait3 = new WebDriverWait(d3, Duration.ofSeconds(100));
        wait3.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 3",d3.findElement(By.id("playerID")).getText());

        d4.get("http://localhost:"+port);
        WebDriverWait wait4 = new WebDriverWait(d4, Duration.ofSeconds(100));
        wait4.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        assertEquals("Player: 4",d4.findElement(By.id("playerID")).getText());

        rigTestRow80R1();

        assertTrue(d1.findElement(By.id("startBtn")).isDisplayed());
        d1.findElement(By.id("startBtn")).click();
        text = d1.findElement(By.id("direction")).getText();
        assertEquals("left",text);

        assertEquals("4D",d1.findElement(By.className("topCard")).getAttribute("id"));

        d1.findElement(By.id("4H")).click();
        d2.findElement(By.id("4S")).click();
        d3.findElement(By.id("9S")).click();

        d4.findElement(By.id("draw")).click();
        assertTrue(d4.findElements(By.id("2C")).size()>0);
        d4.findElement(By.id("draw")).click();
        assertTrue(d4.findElements(By.id("3C")).size()>0);
        d4.findElement(By.id("draw")).click();
        assertTrue(d4.findElements(By.id("4C")).size()>0);

        d1.findElement(By.id("7S")).click();
        d2.findElement(By.id("6S")).click();
        d3.findElement(By.id("6C")).click();
        d4.findElement(By.id("2C")).click();
        assertTrue(d1.findElements(By.id("TC")).size()>0);
        assertTrue(d1.findElements(By.id("JC")).size()>0);

        d1.findElement(By.id("JC")).click();
        d2.findElement(By.id("KC")).click();
        d3.findElement(By.id("9C")).click();
        d4.findElement(By.id("3C")).click();

        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("7C")).size()>0);

        d1.findElement(By.id("7C")).click();
        d2.findElement(By.id("8H")).click();
        d2.findElement(By.id("diamond")).click();
        assertEquals("D",d3.findElement(By.className("topCard")).getAttribute("id"));
        d3.findElement(By.id("JD")).click();
        d4.findElement(By.id("7D")).click();

        d1.findElement(By.id("9D")).click();
        d2.findElement(By.id("TD")).click();

        assertEquals("Player 1: 21",d1.findElement(By.id("p1")).getText());
        assertEquals("Player 2: 0",d2.findElement(By.id("p2")).getText());
        assertEquals("Player 3: 3",d3.findElement(By.id("p3")).getText());
        assertEquals("Player 4: 39",d4.findElement(By.id("p4")).getText());
        assertTrue(d2.findElement(By.id("startBtn")).isDisplayed());

        rigTestRow80R2();

        d2.findElement(By.id("startBtn")).click();

        assertEquals("TD",d1.findElement(By.className("topCard")).getAttribute("id"));
        assertEquals("Turn: 2",d2.findElement(By.id("turnID")).getText());

        d2.findElement(By.id("9D")).click();
        d3.findElement(By.id("3D")).click();
        d4.findElement(By.id("4D")).click();

        d1.findElement(By.id("4S")).click();
        d2.findElement(By.id("3S")).click();
        d3.findElement(By.id("9S")).click();
        d4.findElement(By.id("7S")).click();

        d1.findElement(By.id("7C")).click();
        d2.findElement(By.id("9C")).click();
        d3.findElement(By.id("3C")).click();
        d4.findElement(By.id("4C")).click();

        d1.findElement(By.id("4H")).click();
        d2.findElement(By.id("3H")).click();
        d3.findElement(By.id("9H")).click();

        d4.findElement(By.id("draw")).click();
        assertTrue(d4.findElements(By.id("KS")).size()>0);
        d4.findElement(By.id("draw")).click();
        assertTrue(d4.findElements(By.id("QS")).size()>0);
        d4.findElement(By.id("draw")).click();
        assertTrue(d4.findElements(By.id("KH")).size()>0);
        d4.findElement(By.id("KH")).click();

        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("6D")).size()>0);
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("QD")).size()>0);
        d1.findElement(By.id("draw")).click();
        assertTrue(d1.findElements(By.id("JD")).size()>0);
        assertEquals("Turn: 2",d2.findElement(By.id("turnID")).getText());

        d2.findElement(By.id("draw")).click();
        assertTrue(d2.findElements(By.id("6S")).size()>0);
        d2.findElement(By.id("draw")).click();
        assertTrue(d2.findElements(By.id("JS")).size()>0);
        d2.findElement(By.id("draw")).click();
        assertTrue(d2.findElements(By.id("TS")).size()>0);
        assertEquals("Turn: 3",d3.findElement(By.id("turnID")).getText());

        d3.findElement(By.id("5H")).click();

        assertEquals("Player 1: 59",d1.findElement(By.id("p1")).getText());
        assertEquals("Player 2: 36",d2.findElement(By.id("p2")).getText());
        assertEquals("Player 3: 3",d3.findElement(By.id("p3")).getText());
        assertEquals("Player 4: 114",d4.findElement(By.id("p4")).getText());

        assertFalse(d1.findElement(By.id("startBtn")).isDisplayed());
        assertFalse(d2.findElement(By.id("startBtn")).isDisplayed());
        assertFalse(d3.findElement(By.id("startBtn")).isDisplayed());
        assertFalse(d4.findElement(By.id("startBtn")).isDisplayed());

        assertEquals("WINNER IS PLAYER 3",d1.findElement(By.id("winMSG")).getAttribute("innerHTML"));
        assertEquals("WINNER IS PLAYER 3",d2.findElement(By.id("winMSG")).getAttribute("innerHTML"));
        assertEquals("WINNER IS PLAYER 3",d3.findElement(By.id("winMSG")).getAttribute("innerHTML"));
        assertEquals("WINNER IS PLAYER 3",d4.findElement(By.id("winMSG")).getAttribute("innerHTML"));
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

    public void rigTestRow51(){
        String rigC = "KC 5C KH 8H 9D 6D 3C 4H 7H 9D JS 4C 9H 6C TD JH KS QC 4S AH 5S";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
    }
    public void rigTestRow52(){
        String rigC = "KC 7C KH 8H 9D 6D 3C 4H 7H 9D JS 4C 9H 5C TD JH KS QC 4S AH 5S";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
    }
    public void rigTestRow53(){
        String rigC = "KC 7C KH 8H 9D 6D 3C 4H 7H 9D JS 4C 9H 5C TD JH KS QC 4S AH 5S";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
    }
    public void rigTestRow54(){
        String rigC = "KC 5S KH 8H 9D 6D 3C 4H 7H 9D JS 4C 9H 5C TD JH KS QC 4S AH 7C";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
    }
    public void rigTestRow58(){
        String rigC = "7C 5S KH 8H 9D 6D 3C 4H 7H 9D JS 4C 9H 5C TD JH KS QC 4S AH KC 6C";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> p1rig = new ArrayList<>();
        Card tmpC = new Card("H","3");
        p1rig.add(tmpC);
        gd.getPlayers().get(0).setCards(p1rig);
    }
    public void rigTestRow59(){
        String rigC = "7C 5S KH 8H 9D 6C 3C 4H 7H 9D JS 4C 9H 5D TD JH KS QC 4S AH KC 6D 5C";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> p1rig = new ArrayList<>();
        Card tmpC = new Card("H","3");
        p1rig.add(tmpC);
        gd.getPlayers().get(0).setCards(p1rig);
    }
    public void rigTestRow60(){
        String rigC = "7C 5C KH 8H 9D 6C 3C 4H 7D 9D JS 4C 9H 5D TD JH KS QC 4S AH KC 6D 5S 7H";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> p1rig = new ArrayList<>();
        Card tmpC = new Card("H","3");
        p1rig.add(tmpC);
        gd.getPlayers().get(0).setCards(p1rig);
    }
    public void rigTestRow61(){
        String rigC = "7C 5C KH 8H 9D 6C 3C 7H 7D 9D JS 4C 9H 5D TD JH KS QC 4S AH KC 6D 5S 4H";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> p1rig = new ArrayList<>();
        Card tmpC = new Card("H","3");
        p1rig.add(tmpC);
        gd.getPlayers().get(0).setCards(p1rig);
    }
    public void rigTestRow62(){
        String rigC = "7C 5C KH 8S 9D 6C 3C 7H 7D 9D JS 4C 9H 5D TD JH KS QC 4S AH KC 6D 8H";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> p1rig = new ArrayList<>();
        Card tmpC = new Card("H","3");
        p1rig.add(tmpC);
        gd.getPlayers().get(0).setCards(p1rig);
    }
    public void rigTestRow63(){
        String rigC = "7C 5C KH 8S 9D 6D 3C 7H 7D 9D JS 4C 9H 5D TD JH KS QC 4S AH KC 6C 8H";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> p1rig = new ArrayList<>();
        Card tmpC = new Card("S","K");
        p1rig.add(tmpC);
        tmpC = new Card("C","3");
        p1rig.add(tmpC);
        gd.getPlayers().get(0).setCards(p1rig);
    }
    public void rigTestRow67(){
        String rigC = "7C 2C KH 8S 9S 6D 3C 7H 7D 9C JS 4C 9H 5D TD JH KS QC 4S AH KC 6C 9D";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> p2rig = new ArrayList<>();
        Card tmpC = new Card("H","4");
        p2rig.add(tmpC);
        gd.getPlayers().get(1).setCards(p2rig);
    }
    public void rigTestRow68(){
        String rigC = "7C 2C KH 8S 9S 6D 3C 7H 7D 9C JS 4C 9C 5D TD JH KS QC 4S AH KC 6S 9D 6C 9H";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> p2rig = new ArrayList<>();
        Card tmpC = new Card("H","4");
        p2rig.add(tmpC);
        gd.getPlayers().get(1).setCards(p2rig);
    }
    public void rigTestRow69(){
        String rigC = "7C 2C KH 8S 9S 6D 3C 7H 7D 9C JS 4C 9C 5D TD JH KS QC 4S AH KC 6S 9D 9H 7S 5H";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> p2rig = new ArrayList<>();
        Card tmpC = new Card("H","4");
        p2rig.add(tmpC);
        gd.getPlayers().get(1).setCards(p2rig);
    }
    public void rigTestRow70(){
        String rigC = "AC 2C KH 8S 9S AD 3C 7H 7D 9C JS 4C 9C 5D TD JH KS QC 4S AH KC 2H 9D 5S 6D 6H 7C";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> p2rig = new ArrayList<>();
        Card tmpC = new Card("H","4");
        p2rig.add(tmpC);
        gd.getPlayers().get(1).setCards(p2rig);
        ArrayList<Card> p3rig = new ArrayList<>();
        tmpC = new Card("D","7");
        p3rig.add(tmpC);
        gd.getPlayers().get(2).setCards(p3rig);
    }
    public void rigTestRow72(){
        String rigC = "7C 2C KH 8S 9S 6D 3C 7H 7D 9C JS 4C 9C 5D TD JH KS QC 4S AH KC";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> p2rig = new ArrayList<>();
        Card tmpC = new Card("C","4");
        p2rig.add(tmpC);
        tmpC = new Card("C","6");
        p2rig.add(tmpC);
        tmpC = new Card("D","9");
        p2rig.add(tmpC);
        gd.getPlayers().get(1).setCards(p2rig);
    }
    public void rigTestRow73(){
        String rigC = "7C 2C KH 8S 9S 6D 3C 7H 7D 9C JS 4C 9C 5D TD JH AS QC 4S AH AC";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> p2rig = new ArrayList<>();
        Card tmpC = new Card("C","4");
        p2rig.add(tmpC);
        tmpC = new Card("C","6");
        p2rig.add(tmpC);
        gd.getPlayers().get(1).setCards(p2rig);
    }

    public void rigTestRow77(){
        String rigC = "7C 2C KH 8S 9S 6D 3C 7H 7D 9C JS 4C 9C 5D TD JH KS QC 4S AH KC";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
        ArrayList<Card> rig = new ArrayList<>();

        Card tmpc = new Card("S","A");
        rig.add(tmpc);
        tmpc = new Card("C","3");
        rig.add(tmpc);
        gd.getPlayers().get(0).setCards(rig);

        rig = new ArrayList<>();
        tmpc = new Card("C","4");
        rig.add(tmpc);
        gd.getPlayers().get(1).setCards(rig);

        rig = new ArrayList<>();
        tmpc = new Card("H","8");
        rig.add(tmpc);
        tmpc = new Card("H","J");
        rig.add(tmpc);
        tmpc = new Card("H","6");
        rig.add(tmpc);
        tmpc = new Card("H","K");
        rig.add(tmpc);
        tmpc = new Card("S","K");
        rig.add(tmpc);
        gd.getPlayers().get(2).setCards(rig);

        rig = new ArrayList<>();
        tmpc = new Card("C","8");
        rig.add(tmpc);
        tmpc = new Card("D","8");
        rig.add(tmpc);
        tmpc = new Card("D","2");
        rig.add(tmpc);
        gd.getPlayers().get(3).setCards(rig);
    }

    public void rigTestRow80R1(){
        String rigC = "4D 4H 7S 5D 6D 9D 4S 6S KC 8H TD 9S 6C 9C JD 3H 7D JH QH KH 5C 2C 3C 4C TC JC 7C 8C KD";
        gd.setCards(stringToArray(rigC));
        gd.setTopCard(game.startSetTopCard(gd.getCards()));
        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(),gd.getPlayers(),p.getID()-1);
        }
    }
    public void rigTestRow80R2(){
        String rigC = "TD 7D 4S 7C 4H 5D 9D 3S 9C 3H JC 3D 9S 3C 9H 5H 4D 7S 4C 5S 8D KS QS KH 6D QD JD 6S JS TS";
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
