package com.example.comp4004f22a3101077008;

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

    @LocalServerPort
    int port;

    ArrayList<WebDriver> drivers;
    ChromeOptions chromeOptions;
    @BeforeEach
    public void loadWebDriver(){
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe");
        chromeOptions = new ChromeOptions();
        drivers = new ArrayList<>();
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(10));
        chromeOptions.setPageLoadTimeout(Duration.ofSeconds(10));
        WebDriver driver1 = new ChromeDriver(chromeOptions);
        drivers.add(driver1);
        /*WebDriver driver2 = new ChromeDriver(chromeOptions);
        WebDriver driver3 = new ChromeDriver(chromeOptions);
        WebDriver driver4 = new ChromeDriver(chromeOptions);

        drivers.add(driver2);
        drivers.add(driver3);
        drivers.add(driver4);*/
    }
    @AfterEach
    public void close(){
        for(WebDriver d : drivers){
            if(d!=null){
                d.close();
            }
        }
    }
    @Test
    public void testRow67(){

        drivers.get(0).get("http://localhost:"+port);
        String text = drivers.get(0).findElement(By.id("title")).getText();
        assertEquals("Crazy Eights",text);
    }
}
