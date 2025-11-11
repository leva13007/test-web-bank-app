package org.zloyleva.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.zloyleva.utils.ByTestId;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest {
  WebDriver driver;
  String url = "http://localhost:5678/";

  @BeforeEach
  public void setup() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();

    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    driver.get(url);
  }

  @AfterEach
  public void teardown() throws InterruptedException {
//    Thread.sleep(1000);
//    Thread.sleep(0);
    driver.quit();
  }

  @Test
  public void testPageURL () {
    String currentUrl = driver.getCurrentUrl();
    assertEquals(url, currentUrl);
  }

  @Test
  public void testPageConfig () {
    String title = driver.getTitle();
    assertEquals("Bank App | Home page", title);
  }

  /**
   * Home page header tests for Guests
   */
  @Test
  public void testPageHeaderRenders () {
    // logo link
    WebElement logoLink = driver.findElement(ByTestId.testId("header-logo-link"));
    assertTrue(logoLink.isDisplayed());
    assertEquals("Bank App", logoLink.getText());
    assertEquals("a", logoLink.getTagName());

    // burger link doesn't display on desktop
    WebElement burgerButton = driver.findElement(ByTestId.testId("header-burger-button"));
    assertFalse(burgerButton.isDisplayed());
    assertEquals("button", burgerButton.getTagName());

    // home link
    WebElement homeLink = driver.findElement(ByTestId.testId("header-home-link"));
    assertTrue(homeLink.isDisplayed());
    assertEquals("Home", homeLink.getText());
    assertEquals("a", homeLink.getTagName());

    // productsLink link
    WebElement productsLink = driver.findElement(ByTestId.testId("header-products-link"));
    assertTrue(productsLink.isDisplayed());
    assertEquals("Products", productsLink.getText());
    assertEquals("a", productsLink.getTagName());

    // login button
    WebElement loginButton = driver.findElement(ByTestId.testId("header-login-button"));
    assertTrue(loginButton.isDisplayed());
    assertEquals("Login", loginButton.getText());
    assertEquals("a", loginButton.getTagName());

    // registration button
    WebElement registrationButton = driver.findElement(ByTestId.testId("header-registration-button"));
    assertTrue(registrationButton.isDisplayed());
    assertEquals("Registration", registrationButton.getText());
    assertEquals("a", registrationButton.getTagName());
  }

  @Test
  public void testPageBodyRenders () {
    // Home Page Title H1 exist and contain correct text
    WebElement title = driver.findElement(ByTestId.testId("home-page-title"));
    assertTrue(title.isDisplayed());
    assertEquals("Welcome to the Web Bank Application", title.getText());
    assertEquals("h1", title.getTagName());

    // Home Page SubTitle p exist and contain correct text
    WebElement subTitle = driver.findElement(ByTestId.testId("home-page-subtitle"));
    assertTrue(subTitle.isDisplayed());
    assertEquals("Something short and leading about the Bank App.", subTitle.getText());
    assertEquals("p", subTitle.getTagName());

    // Home Page contain primary CTA button and it has correct text
    WebElement primaryCTA = driver.findElement(ByTestId.testId("home-page-primary-cta"));
    assertTrue(primaryCTA.isDisplayed());
    assertEquals("Main call to action", primaryCTA.getText());
    assertEquals("button", primaryCTA.getTagName());

    // Home Page contain secondary CTA button and it has correct text
    WebElement secondaryCTA = driver.findElement(ByTestId.testId("home-page-secondary-cta"));
    assertTrue(secondaryCTA.isDisplayed());
    assertEquals("Secondary action", secondaryCTA.getText());
    assertEquals("button", secondaryCTA.getTagName());
  }
}
