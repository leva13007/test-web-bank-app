package org.zloyleva.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest {
  WebDriver driver;

  @BeforeEach
  public void setup() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
  }

  @AfterEach
  public void teardown() throws InterruptedException {
    Thread.sleep(2000);
    driver.quit();
  }

  @Test
  public void testHomePageRenders () {
    String url = "http://localhost:5678/";
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    driver.get(url);

    String currentUrl = driver.getCurrentUrl();

    assertEquals(url, currentUrl);

    String title = driver.getTitle();
    assertEquals("Bank App | Home page", title);

    WebElement loginButton = driver.findElement(By.cssSelector("#navbarsExample07 > div > a.btn.btn-outline-light.me-2"));
    assertTrue(loginButton.isDisplayed());

    WebElement registrationButton = driver.findElement(By.cssSelector("#navbarsExample07 > div > a.btn.btn-warning"));
    assertTrue(registrationButton.isDisplayed());
  }
}
