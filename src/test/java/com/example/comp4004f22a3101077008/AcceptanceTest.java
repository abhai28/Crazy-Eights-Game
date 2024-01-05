/*
 * Author: Abhai Roop Singh Gill
 * The AcceptanceTest class is responsible for executing acceptance test cases.
 * The @SpringBootTest annotation is used to specify that the application context should be loaded for this test.
 * The @LocalServerPort annotation is used to inject the actual port used into the field.
 * The @DirtiesContext annotation indicates that the Spring ApplicationContext has been modified by this test and should be closed.
 * The @ContextConfiguration annotation is used to specify which ApplicationContext should be used to load the context for this test.
 * The @BeforeEach annotation is used to signal that the annotated method should be executed before each @Test method in the current test class.
 * The @AfterEach annotation is used to signal that the annotated method should be executed after each @Test method in the current test class.
 * The @BeforeAll annotation is used to signal that the annotated method should be executed before all @Test methods in the current class.
 */
package com.example.comp4004f22a3101077008;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    static ChromeOptions options;

    final int numPlayers = 4;

    ArrayList<WebDriver> drivers;
    ArrayList<WebDriverWait> waits;

    // The initChromeOptions() method is used to initialize the ChromeOptions object. It is executed before all @Test methods.
    @BeforeAll
    public static void initChromeOptions() {
        System.setProperty("webdriver.chrome.driver", ".\\chromedriver-win64\\chromedriver.exe");
        options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
    }

    // The setUp() method is used to initialize the WebDriver and WebDriverWait objects. It is executed before each @Test method.
    @BeforeEach
    public void setUp() {
        drivers = new ArrayList<>();
        waits = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            drivers.add(new ChromeDriver(options));
            waits.add(new WebDriverWait(drivers.get(i), Duration.ofSeconds(10)));
            drivers.get(i).get("http://localhost:"+port);
            waits.get(i).until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn"))).click();
        sleep(1000);
        }

        Dimension screenSize = drivers.get(0).manage().window().getSize();
        int windowWidth = screenSize.getWidth()/2;
        int windowHeight = screenSize.getHeight()/2;
        for (WebDriver driver : drivers) {
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(windowWidth, windowHeight));
        }
        drivers.get(0).manage().window().setPosition(new org.openqa.selenium.Point(0, 0));
        drivers.get(1).manage().window().setPosition(new org.openqa.selenium.Point(0, windowHeight));
        drivers.get(2).manage().window().setPosition(new org.openqa.selenium.Point(windowWidth, windowHeight));
        drivers.get(3).manage().window().setPosition(new org.openqa.selenium.Point(windowWidth, 0));
    }

    // The tearDown() method is used to close the WebDriver objects. It is executed after each @Test method.
    @AfterEach
    public void tearDown(){
        for (WebDriver driver : drivers) {
            driver.quit();
        }
    }

    /*
     * The sleep() method is used to pause the execution of the program for a specified number of milliseconds.
     * @param milliseconds The number of milliseconds to pause the execution of the program.
     */
    private void sleep(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (Exception e){

        }
    }

    /*
     * The rigTable() method is used to rig the game with a specified set of cards.
     * @param riggedCards The set of cards to rig the game with.
     */
    private void rigTable(String riggedCards) {
        ArrayList<Card> riggedDeck = new ArrayList<>();
        String[] cardStrings = riggedCards.split(" ");
        for (String cardString : cardStrings) {
            riggedDeck.add(new Card(cardString.substring(1), cardString.substring(0,1)));
        }

        gd.setCards(riggedDeck);
        gd.setTopCard(game.startSetTopCard(riggedDeck));
        for (int i = 0; i < gd.getPlayers().size(); i++) {
            game.startDealCards(riggedDeck, gd.getPlayers(), i);
        }
    }

    /*
     * The assertTopCardForAll() method is used to assert that the top card is the same for all players. 
     * It retrieves the top card for each player by locating the element with the class name "topCard" 
     * and asserting that the id of the element is equal to the specified card string.
     * @param cardStr The string representation of the card to assert.
     */
    private void assertTopCardForAll(String cardStr) {
        assertEquals(cardStr, waits.get(0).until(ExpectedConditions.presenceOfElementLocated(By.className("topCard"))).getAttribute("id"));
        assertEquals(cardStr, waits.get(1).until(ExpectedConditions.presenceOfElementLocated(By.className("topCard"))).getAttribute("id"));
        assertEquals(cardStr, waits.get(2).until(ExpectedConditions.presenceOfElementLocated(By.className("topCard"))).getAttribute("id"));
        assertEquals(cardStr, waits.get(3).until(ExpectedConditions.presenceOfElementLocated(By.className("topCard"))).getAttribute("id"));
    }

    /*
     * The assertPlayerTurnForAll() method is used to assert that it is the specified player's turn. 
     * It retrieves the turn for each player by locating the element with the id "turnID" and asserting that the text of the element is equal to the specified turn string.
     * It also asserts that the draw button is enabled for the specified player.
     * @param turn The string representation of the turn to assert.
     */
    private void assertPlayerTurnForAll(int turn) {
        assertEquals("Turn: "+(turn+1), waits.get(0).until(ExpectedConditions.presenceOfElementLocated(By.id("turnID"))).getText());
        assertEquals("Turn: "+(turn+1), waits.get(1).until(ExpectedConditions.presenceOfElementLocated(By.id("turnID"))).getText());
        assertEquals("Turn: "+(turn+1), waits.get(2).until(ExpectedConditions.presenceOfElementLocated(By.id("turnID"))).getText());
        assertEquals("Turn: "+(turn+1), waits.get(3).until(ExpectedConditions.presenceOfElementLocated(By.id("turnID"))).getText());
        assertTrue(waits.get(turn).until(ExpectedConditions.presenceOfElementLocated(By.id("draw"))).isEnabled());
    }

    /*
     * The assertDirectionForAll() method is used to assert that the direction is the same for all players. 
     * It retrieves the direction for each player by locating the element with the id "direction" 
     * and asserting that the text of the element is equal to the specified direction string.
     * @param direction The string representation of the direction to assert.
     */
    private void assertDirectionForAll(String direction) {
        assertEquals(direction, waits.get(0).until(ExpectedConditions.presenceOfElementLocated(By.id("direction"))).getText());
        assertEquals(direction, waits.get(1).until(ExpectedConditions.presenceOfElementLocated(By.id("direction"))).getText());
        assertEquals(direction, waits.get(2).until(ExpectedConditions.presenceOfElementLocated(By.id("direction"))).getText());
        assertEquals(direction, waits.get(3).until(ExpectedConditions.presenceOfElementLocated(By.id("direction"))).getText());
    }

    /*
     * The assertPlayerHand() method is used to assert that the specified player's hand is the same as the specified hand string. 
     * It retrieves the hand for the specified player by locating the elements with the class name "card" 
     * and asserting that the id of each element is contained in the specified hand string.
     * @param player The player to assert.
     * @param handStr The string representation of the hand to assert.
     */
    private void assertPlayerHand(int player, String handStr){
        Set<String> expectedHand = Stream.of(handStr.split(" ")).collect(Collectors.toSet());
        Set<String> hand = drivers.get(player).findElements(By.className("card")).stream()
                .map(elem -> elem.getAttribute("id")).collect(Collectors.toSet());
        assertIterableEquals(expectedHand, hand);
    }

    /*
     * The assertCardNotInPlayerHand() method is used to assert that the specified card is not in the specified player's hand. 
     * It retrieves the hand for the specified player by locating the elements with the class name "card" 
     * and asserting that the id of each element is not equal to the specified card string.
     * @param player The player to assert.
     * @param cardStr The string representation of the card to assert.
     */
    private void assertCardNotInPlayerHand(int player, String cardStr) {
        assertTrue(drivers.get(player).findElements(By.className("card")).stream()
                .noneMatch(elem -> elem.getAttribute("id").equals(cardStr)));
    }

    /*
     * The assertCardsInPlayerHandDisabled() method is used to assert that the specified cards are disabled in the specified player's hand. 
     * It retrieves the hand for the specified player by locating the elements with the class name "card" 
     * and asserting that the id of each element is contained in the specified cards string.
     * It also asserts that each element is disabled.
     * @param player The player to assert.
     * @param cardsStr The string representation of the cards to assert.
     */
    private void assertCardsInPlayerHandDisabled(int player, String cardsStr) {
        List<String> cards = Arrays.stream(cardsStr.split(" ")).toList();
        for (String card : cards) {
            assertFalse(waits.get(player).until(ExpectedConditions.presenceOfElementLocated(By.id(card))).isEnabled());
        }
    }

    /*
     * The assertCardsInPlayerHandUnplayable() method is used to assert that the specified cards are unplayable in the specified player's hand. 
     * It retrieves the hand for the specified player by locating the elements with the class name "card" 
     * and asserting that the id of each element is contained in the specified cards string.
     * It also asserts that each element is unplayable by clicking on it and asserting that an alert with the text "Invalid Selection" is present.
     * @param player The player to assert.
     * @param cardsStr The string representation of the cards to assert.
     */
    private void assertCardsInPlayerHandUnplayable(int player, String cardsStr) {
        List<String> cards = Arrays.stream(cardsStr.split(" ")).toList();
        for (String card : cards) {
            waits.get(player).until(ExpectedConditions.elementToBeClickable(By.id(card))).click();
            sleep(1000);
            Alert alert = waits.get(player).until(ExpectedConditions.alertIsPresent());
            assertEquals("Invalid Selection", alert.getText());
            alert.accept();
        }
    }

    /*
     * The assertPromptForSuit() method is used to assert that the specified player is prompted to select a suit. 
     * It retrieves the turn for each player by locating the element with the id "turnID" 
     * and asserting that the text of the element is equal to the specified turn string.
     * It also asserts that the suit buttons are displayed for the specified player.
     * @param player The player to assert.
     */
    private void assertPromptForSuit(int player) {
        assertEquals("Turn: " + (player+1), waits.get(0).until(ExpectedConditions.presenceOfElementLocated(By.id("turnID"))).getText());
        assertEquals("Turn: " + (player+1), waits.get(1).until(ExpectedConditions.presenceOfElementLocated(By.id("turnID"))).getText());
        assertEquals("Turn: " + (player+1), waits.get(2).until(ExpectedConditions.presenceOfElementLocated(By.id("turnID"))).getText());
        assertEquals("Turn: " + (player+1), waits.get(3).until(ExpectedConditions.presenceOfElementLocated(By.id("turnID"))).getText());
        assertTrue(waits.get(player).until(ExpectedConditions.elementToBeClickable(By.id("spade"))).isDisplayed());
        assertTrue(waits.get(player).until(ExpectedConditions.elementToBeClickable(By.id("heart"))).isDisplayed());
        assertTrue(waits.get(player).until(ExpectedConditions.elementToBeClickable(By.id("club"))).isDisplayed());
        assertTrue(waits.get(player).until(ExpectedConditions.elementToBeClickable(By.id("diamond"))).isDisplayed());
    }

    /*
     * The assertScore() method is used to assert that the scores are the same for all players. 
     * It retrieves the score for each player by locating the element with the id "p1", "p2", "p3", or "p4" 
     * and asserting that the text of the element is equal to the specified score string.
     * @param p1Score The string representation of the score for player 1.
     * @param p2Score The string representation of the score for player 2.
     * @param p3Score The string representation of the score for player 3.
     * @param p4Score The string representation of the score for player 4.
     */
    private void assertScore(int p1Score, int p2Score, int p3Score, int p4Score) {
        for (WebDriverWait wait : waits) {
            assertEquals("Player 1: "+p1Score, wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p1"))).getText());
            assertEquals("Player 2: "+p2Score, wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p2"))).getText());
            assertEquals("Player 3: "+p3Score, wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p3"))).getText());
            assertEquals("Player 4: "+p4Score, wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p4"))).getText());
        }
    }

    /*
     * The assertPlayerHandEmpty() method is used to assert that the specified player's hand is empty. 
     * It retrieves the hand for the specified player by locating the elements with the class name "card" 
     * and asserting that the number of elements is equal to 0.
     * @param player The player to assert.
     */
    private void assertRoundEnded() {
        assertFalse(waits.get(0).until(ExpectedConditions.presenceOfElementLocated(By.id("draw"))).isDisplayed());
        assertFalse(waits.get(1).until(ExpectedConditions.presenceOfElementLocated(By.id("draw"))).isDisplayed());
        assertFalse(waits.get(2).until(ExpectedConditions.presenceOfElementLocated(By.id("draw"))).isDisplayed());
        assertFalse(waits.get(3).until(ExpectedConditions.presenceOfElementLocated(By.id("draw"))).isDisplayed());
    }

    /*
     * The assertPlayerHandEmpty() method is used to assert that the specified player's hand is empty. 
     * It retrieves the hand for the specified player by locating the elements with the class name "card" 
     * and asserting that the number of elements is equal to 0.
     * @param player The player to assert.
     */
    private void assertGameOver() {
        assertFalse(waits.get(0).until(ExpectedConditions.presenceOfElementLocated(By.id("startBtn"))).isDisplayed());
        assertFalse(waits.get(1).until(ExpectedConditions.presenceOfElementLocated(By.id("startBtn"))).isDisplayed());
        assertFalse(waits.get(2).until(ExpectedConditions.presenceOfElementLocated(By.id("startBtn"))).isDisplayed());
        assertFalse(waits.get(3).until(ExpectedConditions.presenceOfElementLocated(By.id("startBtn"))).isDisplayed());
    }

    /*
     * The assertPlayerHandEmpty() method is used to assert that the specified player's hand is empty. 
     * It retrieves the hand for the specified player by locating the elements with the class name "card" 
     * and asserting that the number of elements is equal to 0.
     * @param player The player to assert.
     */
    private void assertWinner(int winner) {
        assertEquals("WINNER IS PLAYER "+(winner+1), waits.get(0).until(ExpectedConditions.visibilityOfElementLocated(By.id("winMSG"))).getText());
        assertEquals("WINNER IS PLAYER "+(winner+1), waits.get(1).until(ExpectedConditions.visibilityOfElementLocated(By.id("winMSG"))).getText());
        assertEquals("WINNER IS PLAYER "+(winner+1), waits.get(2).until(ExpectedConditions.visibilityOfElementLocated(By.id("winMSG"))).getText());
        assertEquals("WINNER IS PLAYER "+(winner+1), waits.get(3).until(ExpectedConditions.visibilityOfElementLocated(By.id("winMSG"))).getText());
    }

    /*
     * The testRow25() executes the following scenario: p1 plays 3C: assert next player is player 2
     */
    @Test
    @DirtiesContext
    public void testRow25() {
        rigTestRow25();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        assertTopCardForAll("AC");
        assertPlayerTurnForAll(0);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        assertTopCardForAll("3C");
        assertPlayerTurnForAll(1);
    }

    public void rigTestRow25() {
        String riggedCards = "AC "+"2C 3C 4C 5C 6C "+"7C 8C 9C TC JC "+"QC KC AD 2D 3D "+"4D 5D 6D 7D 8D "+"9D TD JD QD KD AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    /*
     * testRow27() executes the following scenario: p1 plays 1H assert next player is player 4 AND interface must show now playing in opposite direction (i.e., going right) player4 plays 7H and next player is player 3
     */
    @Test
    @DirtiesContext
    public void testRow27() {
        rigTestRow27();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        assertTopCardForAll("AC");
        assertPlayerTurnForAll(0);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("AH"))).click();
        sleep(1000);
        assertTopCardForAll("AH");
        assertPlayerTurnForAll(3);
        assertDirectionForAll("right");
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("7H"))).click();
        sleep(1000);
        assertTopCardForAll("7H");
        assertPlayerTurnForAll(2);
    }

    public void rigTestRow27() {
        String riggedCards = "AC "+"AH 3C 4C 5C 6C "+"7C 8C 9C TC JC "+"QC KC AD 2D 3D "+"7H 5D 6D 7D 8D "+"9D TD JD QD KD 2C 2H 3H 4H 5H 6H 4D 8H 9H TH JH QH KH AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow28() executes the following scenario: p1 plays QC assert next player is player 3  (because player 2 is notified and loses their turn)
    @Test
    @DirtiesContext
    public void testRow28() {
        rigTestRow28();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        assertTopCardForAll("AC");
        assertPlayerTurnForAll(0);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        assertTopCardForAll("QC");
        assertPlayerTurnForAll(2);
        assertFalse(waits.get(1).until(ExpectedConditions.presenceOfElementLocated(By.id("draw"))).isEnabled());
    }

    public void rigTestRow28() {
        String riggedCards = "AC "+"QC 3C 4C 5C 6C "+"7C 8C 9C TC JC "+"AH KC AD 2D 3D "+"7H 5D 6D 7D 8D "+"9D TD JD QD KD 2C 2H 3H 4H 5H 6H 4D 8H 9H TH JH QH KH AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow29() executes the following scenario: p4 plays 3C: assert next player is player 1
    @Test
    @DirtiesContext
    public void testRow29() {
        rigTestRow29();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        assertTopCardForAll("AC");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("KC"))).click();
        sleep(1000);
        assertPlayerTurnForAll(3);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        assertTopCardForAll("3C");
        assertPlayerTurnForAll(0);
    }

    public void rigTestRow29() {
        String riggedCards = "AC "+"QC 7H 4C 5C 6C "+"7C 8C 9C TC JC "+"AH KC AD 2D 3D "+"3C 5D 6D 7D 8D "+"9D TD JD QD KD 2C 2H 3H 4H 5H 6H 4D 8H 9H TH JH QH KH AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow31() executes the following scenario: p4 plays 1H: assert next player is player 3 AND interface must show now playing in opposite direction (i.e., right) player3 plays 7H and next player is player 2
    @Test
    @DirtiesContext
    public void testRow31() {
        rigTestRow31();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        assertTopCardForAll("AC");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("4C"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("3H"))).click();
        sleep(1000);
        assertPlayerTurnForAll(3);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("AH"))).click();
        sleep(1000);
        assertTopCardForAll("AH");
        assertPlayerTurnForAll(2);
        assertDirectionForAll("right");
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("7H"))).click();
        sleep(1000);
        assertTopCardForAll("7H");
        assertPlayerTurnForAll(1);
    }

    public void rigTestRow31() {
        String riggedCards = "AC "+"QC 7C 4C 5C 6C "+"3C 8C 9C TC JC "+"7H 3H AD 2D 3D "+"AH 5D 6D 7D 8D "+"9D TD JD QD KD 2C 2H KC 4H 5H 6H 4D 8H 9H TH JH QH KH AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow32() executes the following scenario: p4 plays QC assert next player is player 2  (because player 1 loses their turn)
    @Test
    @DirtiesContext
    public void testRow32() {
        rigTestRow32();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        assertTopCardForAll("AC");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("4C"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);
        assertPlayerTurnForAll(3);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        assertTopCardForAll("QC");
        assertPlayerTurnForAll(1);
        assertFalse(waits.get(0).until(ExpectedConditions.presenceOfElementLocated(By.id("draw"))).isEnabled());
    }

    public void rigTestRow32() {
        String riggedCards = "AC "+"AH 7H 4C 5C 6C "+"3C 8C 9C TC JC "+"7C 3H AD 2D 3D "+"QC 5D 6D 7D 8D "+"9D TD JD QD KD 2C 2H KC 4H 5H 6H 4D 8H 9H TH JH QH KH AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow35() executes the following scenario: top card is KC and player1 plays KH
    @Test
    @DirtiesContext
    public void testRow35() {
        rigTestRow35();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        assertTopCardForAll("KC");
        assertPlayerTurnForAll(0);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KH"))).click();
        sleep(1000);
        assertTopCardForAll("KH");
        assertPlayerTurnForAll(1);
    }

    public void rigTestRow35() {
        String riggedCards = "KC "+"AH 7H KH 5C 6C "+"3C 8C 9C TC JC "+"7C 3H AD 2D 3D "+"QC 5D 6D 7D 8D "+"9D TD JD QD KD 2C 2H AC 4H 5H 6H 4D 8H 9H TH JH QH 4C AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow36() executes the following scenario: top card is KC and player1 plays 7C
    @Test
    @DirtiesContext
    public void testRow36() {
        rigTestRow36();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        assertTopCardForAll("KC");
        assertPlayerTurnForAll(0);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);
        assertTopCardForAll("7C");
        assertPlayerTurnForAll(1);
    }

    public void rigTestRow36() {
        String riggedCards = "KC "+"AH 7C KH 5C 6C "+"3C 8C 9C TC JC "+"7H 3H AD 2D 3D "+"QC 5D 6D 7D 8D "+"9D TD JD QD KD 2C 2H AC 4H 5H 6H 4D 8H 9H TH JH QH 4C AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow37() executes the following scenario: top card is KC and player1 plays 8H  and interface prompts for new suit
    @Test
    @DirtiesContext
    public void testRow37() {
        rigTestRow37();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        assertTopCardForAll("KC");
        assertPlayerTurnForAll(0);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("8H"))).click();
        sleep(1000);
        assertTopCardForAll("8H");
        assertPromptForSuit(0);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("spade"))).click();
        sleep(1000);
        assertTopCardForAll("S");
        assertPlayerTurnForAll(1);
    }

    public void rigTestRow37() {
        String riggedCards = "KC "+"AH 7C 8H 5C 6C "+"3C 8C 9C TC JC "+"7H 3H AD 2D 3D "+"QC 5D 6D 7D 8D "+"9D TD JD QD KD 2C 2H AC 4H 5H 6H 4D KH 9H TH JH QH 4C AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow38() executes the following scenario: top card is KC and player1 tries to play 5S and interface prohibits this card being played (disabled or message)
    @Test
    @DirtiesContext
    public void testRow38() {
        rigTestRow38();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        assertTopCardForAll("KC");
        assertPlayerTurnForAll(0);
        assertCardsInPlayerHandUnplayable(0,"5S");
        assertTopCardForAll("KC");
    }

    public void rigTestRow38() {
        String riggedCards = "KC "+"AH 7C 8H 5C 5S "+"3C 8C 9C TC JC "+"7H 3H AD 2D 3D "+"QC 5D 6D 7D 8D "+"9D TD JD QD KD 2C 2H AC 4H 5H 6H 4D KH 9H TH JH QH 4C AS 2S 3S 4S 6C 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow42() executes the following scenario: top card is 7C and p1 has only {3H} as hand: must draw, draws 6C and must play it
    @Test
    @DirtiesContext
    public void testRow42() {
        rigTestRow42();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("7D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("4D"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("4H"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QH"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("5H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("5C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KC"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("JC"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);

        assertTopCardForAll("7C");
        assertPlayerTurnForAll(0);
        assertPlayerHand(0, "3H");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "3H 6C");
        assertFalse(waits.get(0).until(ExpectedConditions.visibilityOfElementLocated(By.id("draw"))).isEnabled());
        assertCardsInPlayerHandDisabled(0, "3H");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("6C"))).click();
        sleep(1000);
        assertTopCardForAll("6C");
        assertCardNotInPlayerHand(0, "6C");
        assertPlayerTurnForAll(1);
    }

    public void rigTestRow42() {
        String riggedCards = "AD "+"KD 4D 5H KC 3H "+"7D 8D 5C 4H JC "+"QD QC QH 2C 3C "+"4C 5D 6D 7C 8C "+"6C 9C TD JD 2D AH 2H 3D TC 9H 6H 7H 8H 9D TH JH AC KH AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow43() executes the following scenario: top card is 7C and p1 has {3H} as hand: must draw, draws 6D then 5C and must play it
    @Test
    @DirtiesContext
    public void testRow43() {
        rigTestRow43();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("7D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("4D"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("4H"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QH"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("6H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("6C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KC"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("JC"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);

        assertTopCardForAll("7C");
        assertPlayerTurnForAll(0);
        assertPlayerHand(0, "3H");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "3H 6D");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "3H 6D 5C");
        assertFalse(waits.get(0).until(ExpectedConditions.visibilityOfElementLocated(By.id("draw"))).isEnabled());
        assertCardsInPlayerHandDisabled(0, "3H 6D");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("5C"))).click();
        sleep(1000);
        assertTopCardForAll("5C");
        assertCardNotInPlayerHand(0, "5C");
        assertPlayerTurnForAll(1);
    }

    public void rigTestRow43() {
        String riggedCards = "AD "+"KD 4D 6H KC 3H "+"7D 8D 6C 4H JC "+"QD QC QH 2C 3C "+"4C 5D 9C 7C 8C "+"6D 5C TD JD 2D AH 2H 3D TC 9H 5H 7H 8H 9D TH JH AC KH AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow44() executes the following scenario: top card is 7C and p1 has {3H} as hand: must draw, draws 6D, 5S then 7H and must play it
    @Test
    @DirtiesContext
    public void testRow44() {
        rigTestRow44();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("7D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("4D"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("4H"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QH"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("6H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("6C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KC"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("JC"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);

        assertTopCardForAll("7C");
        assertPlayerTurnForAll(0);
        assertPlayerHand(0, "3H");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "3H 6D");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "3H 6D 5S");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "3H 6D 5S 7H");
        assertFalse(waits.get(0).until(ExpectedConditions.visibilityOfElementLocated(By.id("draw"))).isEnabled());
        assertCardsInPlayerHandDisabled(0, "3H 6D 5S");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("7H"))).click();
        sleep(1000);
        assertTopCardForAll("7H");
        assertCardNotInPlayerHand(0, "7H");
        assertPlayerTurnForAll(1);
    }

    public void rigTestRow44() {
        String riggedCards = "AD "+"KD 4D 6H KC 3H "+"7D 8D 6C 4H JC "+"QD QC QH 2C 3C "+"4C 5D 9C 7C 8C "+"6D 5S 7H TD JD 2D AH 2H 3D TC 9H 5H 8H 9D TH JH AC KH AS 2S 3S 4S 5C 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow45() executes the following scenario: top card is 7C and p1 has {3H} as hand: must draw, draws 6D, 5S, 4H; still can't play: turn ends (ie max 3 cards drawn)
    @Test
    @DirtiesContext
    public void testRow45() {
        rigTestRow45();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("7D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("9D"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("9H"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QH"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("6H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("6C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KC"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("JC"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);

        assertTopCardForAll("7C");
        assertPlayerTurnForAll(0);
        assertPlayerHand(0, "3H");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "3H 6D");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "3H 6D 5S");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "3H 6D 5S 4H");
        assertFalse(waits.get(0).until(ExpectedConditions.visibilityOfElementLocated(By.id("draw"))).isEnabled());
        assertCardsInPlayerHandDisabled(0, "3H 6D 5S 4H");
        assertPlayerTurnForAll(1);
        assertTopCardForAll("7C");
    }

    public void rigTestRow45() {
        String riggedCards = "AD "+"KD 9D 6H KC 3H "+"7D 8D 6C 9H JC "+"QD QC QH 2C 3C "+"4C 5D 9C 7C 8C "+"6D 5S 4H 7H TD JD 2D AH 2H 3D TC 5H 8H 4D TH JH AC KH AS 2S 3S 4S 5C 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow46() executes the following scenario: top card is 7C and p1 has {3H} as hand: must draw, draws 6D then 8H; must play 8H and declare new suit
    @Test
    @DirtiesContext
    public void testRow46() {
        rigTestRow46();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("7D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("9D"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("9H"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QH"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("6H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("6C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KC"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("JC"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);

        assertTopCardForAll("7C");
        assertPlayerTurnForAll(0);
        assertPlayerHand(0, "3H");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "3H 6D");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "3H 6D 8H");
        assertFalse(waits.get(0).until(ExpectedConditions.visibilityOfElementLocated(By.id("draw"))).isEnabled());
        assertCardsInPlayerHandDisabled(0, "3H 6D");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("8H"))).click();
        sleep(1000);
        assertTopCardForAll("8H");
        assertCardNotInPlayerHand(0, "8H");
        assertPromptForSuit(0);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("spade"))).click();
        sleep(1000);
        assertTopCardForAll("S");
        assertPlayerTurnForAll(1);
    }

    public void rigTestRow46() {
        String riggedCards = "AD "+"KD 9D 6H KC 3H "+"7D 8D 6C 9H JC "+"QD QC QH 2C 3C "+"4C 5D 9C 7C 8C "+"6D 8H 5S 4H 7H TD JD 2D AH 2H 3D TC 5H 4D TH JH AC KH AS 2S 3S 4S 5C 6S 7S 8S 9S TS JS QS KS";
        rigTable(riggedCards);
    }

    // testRow47() executes the following scenario: top card is 7C and p1 has {KS, 3C} as hand: chooses to draw, draws 6C and must play 6C
    @Test
    @DirtiesContext
    public void testRow47() {
        rigTestRow47();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("7D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("9D"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("9H"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QH"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("5H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("5C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("JC"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);

        assertTopCardForAll("7C");
        assertPlayerTurnForAll(0);
        assertPlayerHand(0, "KS 3C");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "KS 3C 6C");
        assertFalse(waits.get(0).until(ExpectedConditions.visibilityOfElementLocated(By.id("draw"))).isEnabled());
        assertCardsInPlayerHandDisabled(0, "KS 3C");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("6C"))).click();
        sleep(1000);
        assertTopCardForAll("6C");
        assertCardNotInPlayerHand(0, "6C");
        assertPlayerTurnForAll(1);
    }

    public void rigTestRow47() {
        String riggedCards = "AD "+"KD 9D 5H KS 3C "+"7D 8D 5C 9H QC "+"QD JC QH 2C 3H "+"4C 5D 9C 7C 8C "+"6C 6D 8H 5S 4H 7H TD JD 2D AH 2H 3D TC 6H 4D TH JH AC KH AS 2S 3S 4S 6S 7S 8S 9S TS JS QS KC";
        rigTable(riggedCards);
    }

    // testRow51() executes the following scenario: p1 plays 2C, p2 has only  {4H} thus must draw 2 cards {6C and 9D} then plays 6C
    @Test
    @DirtiesContext
    public void testRow51() {
        rigTestRow51();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("7D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("TD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("TH"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QH"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("5H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("5C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("JC"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("TC"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);

        assertTopCardForAll("7C");
        assertPlayerTurnForAll(0);
        assertPlayerHand(1, "4H");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("2C"))).click();
        sleep(1000);
        assertTopCardForAll("2C");
        assertPlayerTurnForAll(1);
        assertPlayerHand(1, "4H 6C 9D");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("6C"))).click();
        sleep(1000);
        assertTopCardForAll("6C");
        assertPlayerTurnForAll(2);
    }

    public void rigTestRow51() {
        String riggedCards = "AD "+"KD TD 5H KS 2C "+"7D 3C 5C TH 4H "+"QD JC QH 8D TC "+"4C QC 9C 7C 8C "+"6C 9D 6D 8H 5S 5D 7H JD 2D AH 2H 3D 3H 6H 4D 9H JH AC KH AS 2S 3S 4S 6S 7S 8S 9S TS JS QS KC";
        rigTable(riggedCards);
    }

    // testRow52() executes the following scenario: p1 plays 2C, p2 has only {4H}, draws {6S and 9D}, still can't play, then draws 9H then 6C and then must play 6C
    @Test
    @DirtiesContext
    public void testRow52() {
        rigTestRow52();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("7D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("TD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("TH"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QH"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("5H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("5C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("JC"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("TC"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);

        assertTopCardForAll("7C");
        assertPlayerTurnForAll(0);
        assertPlayerHand(1, "4H");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("2C"))).click();
        sleep(1000);
        assertTopCardForAll("2C");
        assertPlayerTurnForAll(1);
        assertPlayerHand(1, "4H 6S 9D");
        assertCardsInPlayerHandUnplayable(1,"4H 6S 9D");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(1, "4H 6S 9D 9H");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(1, "4H 6S 9D 9H 6C");
        assertFalse(waits.get(1).until(ExpectedConditions.visibilityOfElementLocated(By.id("draw"))).isEnabled());
        assertCardsInPlayerHandDisabled(1, "4H 6S 9D 9H");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("6C"))).click();
        sleep(1000);
        assertTopCardForAll("6C");
        assertCardNotInPlayerHand(1, "6C");
        assertPlayerTurnForAll(2);
    }

    public void rigTestRow52() {
        String riggedCards = "AD "+"KD TD 5H KS 2C "+"7D 3C 5C TH 4H "+"QD JC QH 8D TC "+"4C QC 9C 7C 8C "+"6S 9D 9H 6C 6D 8H 5S 5D 7H JD 2D AH 2H 3D 3H 6H 4D JH AC KH AS 2S 3S 4S 7S 8S 9S TS JS QS KC";
        rigTable(riggedCards);
    }

    // testRow53() executes the following scenario: p1 plays 2C, p2 has only {4H} draws {6S and 9D} then draws 9H, 7S, 5H and then  turns end (without playing a card)
    @Test
    @DirtiesContext
    public void testRow53() {
        rigTestRow53();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("7D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("TD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("TH"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QH"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("3H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("JC"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("5C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("TC"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);

        assertTopCardForAll("7C");
        assertPlayerTurnForAll(0);
        assertPlayerHand(1, "4H");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("2C"))).click();
        sleep(1000);
        assertTopCardForAll("2C");
        assertPlayerTurnForAll(1);
        assertPlayerHand(1, "4H 6S 9D");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(1, "4H 6S 9D 9H");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(1, "4H 6S 9D 9H 7S");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(1, "4H 6S 9D 9H 7S 5H");
        assertFalse(waits.get(1).until(ExpectedConditions.visibilityOfElementLocated(By.id("draw"))).isEnabled());
        assertCardsInPlayerHandDisabled(1, "4H 6S 9D 9H 7S 5H");
        assertPlayerTurnForAll(2);
    }

    public void rigTestRow53() {
        String riggedCards = "AD "+"KD TD 3H KS 2C "+"7D 3C 5C TH 4H "+"QD JC QH 8D TC "+"4C QC 9C 7C 8C "+"6S 9D 9H 7S 5H 6C 6D 8H 5S 5D 7H JD 2D AH 2H 3D 6H 4D JH AC KH AS 2S 3S 4S 8S 9S TS JS QS KC";
        rigTable(riggedCards);
    }

    // testRow55() executes the following scenario: p1 plays 2C, p2 has {4H} draws {2H and 9D} then plays 2H (forcing next player to immediately play or draw 4 cards) then p3 having only {7D} p3 draws {5S, 6D, 6H and 7C} and then  plays 6H
    @Test
    @DirtiesContext
    public void testRow55() {
        rigTestRow55();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("5D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("TD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("TH"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QH"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("3H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("JC"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("5C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("TC"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("9C"))).click();
        sleep(1000);

        assertTopCardForAll("9C");
        assertPlayerTurnForAll(0);
        assertPlayerHand(1, "4H");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("2C"))).click();
        sleep(1000);
        assertTopCardForAll("2C");
        assertPlayerTurnForAll(1);
        assertPlayerHand(1, "4H 2H 9D");
        assertPlayerHand(2, "7D");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("2H"))).click();
        sleep(1000);
        assertTopCardForAll("2H");
        assertPlayerTurnForAll(2);
        assertPlayerHand(2, "7D 5S 6D 6H 7C");
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("6H"))).click();
        sleep(1000);
        assertTopCardForAll("6H");
        assertPlayerTurnForAll(3);
    }

    public void rigTestRow55() {
        String riggedCards = "AD "+"KD TD 3H KS 2C "+"5D 3C 5C TH 4H "+"QD JC QH 7D TC "+"4C QC 9C 6S 8C "+"2H 9D 5S 6D 6H 7C 9H 7S 5H 6C 8H 8D 7H JD 2D AH 3D 4D JH AC KH AS 2S 3S 4S 8S 9S TS JS QS KC";
        rigTable(riggedCards);
    }

    // testRow56() executes the following scenario: p1 plays 2C, p2 has {4C, 6C, 9D} then p2 plays 4C and 6C (ie 2 legal cards) and ends their turn
    @Test
    @DirtiesContext
    public void testRow56() {
        rigTestRow56();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("5D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("TD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("TC"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);

        assertTopCardForAll("QC");
        assertPlayerTurnForAll(0);
        assertPlayerHand(1, "4C 6C 9D");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("2C"))).click();
        sleep(1000);
        assertTopCardForAll("2C");
        assertPlayerTurnForAll(1);
        assertPlayerHand(1, "4C 6C 9D");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("4C"))).click();
        sleep(1000);
        assertTopCardForAll("4C");
        assertPlayerTurnForAll(1);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("6C"))).click();
        sleep(1000);
        assertTopCardForAll("6C");
        assertPlayerTurnForAll(2);
    }

    public void rigTestRow56() {
        String riggedCards = "AD "+"KD TD 3H KS 2C "+"5D TC 4C 6C 9D "+"QD JC QC 7D TH "+"3C QH 9C 6S 8C "+"2H 4H 5S 6D 6H 7C 9H 7S 5H 5C 8H 8D 7H JD 2D AH 3D 4D JH AC KH AS 2S 3S 4S 8S 9S TS JS QS KC";
        rigTable(riggedCards);
    }

    // testRow57() executes the following scenario: p1 plays 2C, p2 has {4C, 4S} then p2 plays 4C and 4S and ends round (because p2 played all their cards)
    @Test
    @DirtiesContext
    public void testRow57() {
        rigTestRow57();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("5D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("TD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("TC"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("6C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("JC"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("9C"))).click();
        sleep(1000);

        assertTopCardForAll("9C");
        assertPlayerTurnForAll(0);
        assertPlayerHand(1, "4C 4S");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("2C"))).click();
        sleep(1000);
        assertTopCardForAll("2C");
        assertPlayerTurnForAll(1);
        assertPlayerHand(1, "4C 4S");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("4C"))).click();
        sleep(1000);
        assertTopCardForAll("4C");
        assertPlayerTurnForAll(1);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("4S"))).click();
        sleep(1000);
        assertRoundEnded();
    }

    public void rigTestRow57() {
        String riggedCards = "AD "+"KD TD 3H 7C 2C "+"5D TC 4C 6C 4S "+"QD JC QC 7D TH "+"3C QH 9C 6S 8C "+"2H 9D 4H 5S 6D 6H KS 9H 7S 5H 5C 8H 8D 7H JD 2D AH 3D 4D JH AC KH AS 2S 3S 8S 9S TS JS QS KC";
        rigTable(riggedCards);
    }

    // testRow62() testing scoring of a single round 
    @Test
    @DirtiesContext
    public void testRow62() {
        rigTestRow62();
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("KD"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("QD"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("5D"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("5C"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("QC"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("3H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("QH"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("8S"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("club"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("2C"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("4C"))).click();
        sleep(1000);

        // NOTE: Must check hands before last card played since UI does not show hand when round is over
        assertPlayerHand(0, "AS");
        assertPlayerHand(1, "4S");
        assertPlayerHand(2, "8H JH 6H KH KS");
        assertPlayerHand(3, "8C 8D 2D");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("4S"))).click();
        sleep(5000);
        assertRoundEnded();
        assertScore(1, 0, 86, 102);
        assertGameOver();
    }

    public void rigTestRow62() {
        String riggedCards = "AD "+"AS KD 5C 3H 2C "+"QD QC QH 4C 4S "+"8H JH 6H KH KS "+"8C 8D 2D 5D 3C "+"8S TD 6C 7C 9C 6S TC JC 7D TH 2H 9D 4H 5S 6D 9H 7S 5H 7H JD AH 3D 4D AC 2S 3S 8S 9S TS JS QS KC";
        rigTable(riggedCards);
    }

    // testRow64() testing a complete game
    @Test
    @DirtiesContext
    public void testRow64() {
        rigTestRow64(1);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);

        assertPlayerHand(0, "4H 7S 5D 6D 9D");
        assertPlayerHand(1, "4S 6S KC 8H TD");
        assertPlayerHand(2, "9S 6C 9C JD 3H");
        assertPlayerHand(3, "7D JH QH KH 5C");
        assertTopCardForAll("4D");
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("4H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("4S"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("9S"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("7S"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("6S"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("6C"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("2C"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("JC"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("KC"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("9C"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("8H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("diamond"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("JD"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("7D"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("9D"))).click();
        sleep(1000);
        assertPlayerHand(0, "5D 6D TC");
        assertPlayerHand(1, "TD");
        assertPlayerHand(2, "3H");
        assertPlayerHand(3, "JH QH KH 5C 4C");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("TD"))).click();
        sleep(1000);
        assertRoundEnded();
        assertScore(21, 0, 3, 39);
        rigTestRow64(2);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("startBtn"))).click();
        sleep(1000);
        assertTopCardForAll("TD");
        assertPlayerHand(0, "7D 4S 7C 4H 5D");
        assertPlayerHand(1, "9D 3S 9C 3H JC");
        assertPlayerHand(2, "3D 9S 3C 9H 5H");
        assertPlayerHand(3, "4D 7S 4C 5S 8D");
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("9D"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("3D"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("4D"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("4S"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("3S"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("9S"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("7S"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("7C"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("9C"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("3C"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("4C"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("4H"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("3H"))).click();
        sleep(1000);
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("9H"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(3).until(ExpectedConditions.elementToBeClickable(By.id("KH"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(0).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        waits.get(1).until(ExpectedConditions.elementToBeClickable(By.id("draw"))).click();
        sleep(1000);
        assertPlayerHand(0, "5D 6D 7D JD QD");
        assertPlayerHand(1, "JC 6S TS JS");
        assertPlayerHand(2, "5H");
        assertPlayerHand(3, "5S 8D KS QS");
        waits.get(2).until(ExpectedConditions.elementToBeClickable(By.id("5H"))).click();
        sleep(5000);
        assertRoundEnded();
        assertScore(59, 36, 3, 114);
        assertGameOver();
        assertWinner(2);
    }

    public void rigTestRow64(int round) {
        String riggedCards;
        if (round == 1)
            riggedCards = "4D "+"4H 7S 5D 6D 9D "+"4S 6S KC 8H TD "+"9S 6C 9C JD 3H "+"7D JH QH KH 5C "+"2C 3C 4C TC JC 7C 8C 8D 2D QD QC 8S AS KD 6H KS TH 2H 5S 9H 5H 7H AH 3D AD AC 2S 3S 8S TS JS QS";
        else
            riggedCards = "TD "+"7D 4S 7C 4H 5D "+"9D 3S 9C 3H JC "+"3D 9S 3C 9H 5H "+"4D 7S 4C 5S 8D "+"KS QS KH 6D QD JD 6S JS TS QC QH 8C 2D AS KD 5C 8H JH 6H 2C 8S AD 6C TC TH 2H 7H AH AC 2S 8S KC";
        rigTable(riggedCards);
    }
}
