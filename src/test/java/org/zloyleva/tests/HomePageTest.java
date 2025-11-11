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

  @Test
  public void testPageHeaderRenders () {
    // logo link
    WebElement logoLink = driver.findElement(ByTestId.testId("header-logo-link"));
    assertTrue(logoLink.isDisplayed());
    assertEquals("Bank App", logoLink.getText());

    // burger link doesn't display on desktop
    WebElement burgerButton = driver.findElement(ByTestId.testId("header-burger-button"));
    assertFalse(burgerButton.isDisplayed());

    // home link
    WebElement homeLink = driver.findElement(ByTestId.testId("header-home-link"));
    assertTrue(homeLink.isDisplayed());
    assertEquals("Home", homeLink.getText());

    // productsLink link
    WebElement productsLink = driver.findElement(ByTestId.testId("header-products-link"));
    assertTrue(productsLink.isDisplayed());
    assertEquals("Products", productsLink.getText());

    // login button
    WebElement loginButton = driver.findElement(ByTestId.testId("header-login-button"));
    assertTrue(loginButton.isDisplayed());
    assertEquals("Login", loginButton.getText());

    // registration button
    WebElement registrationButton = driver.findElement(ByTestId.testId("header-registration-button"));
    assertTrue(registrationButton.isDisplayed());
    assertEquals("Registration", registrationButton.getText());
  }
}
