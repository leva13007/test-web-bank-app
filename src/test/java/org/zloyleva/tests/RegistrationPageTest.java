package org.zloyleva.tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.zloyleva.utils.ByTestId;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RegistrationPageTest {

    WebDriver driver;
    String url = "http://localhost:5678/registration";

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
    public void testPageURL() {
        String currentUrl = driver.getCurrentUrl();
        assertEquals(url, currentUrl);
    }

    @Test
    public void testPageConfig() {
        String title = driver.getTitle();
        assertEquals("Bank App | Registration page", title);
    }

    @Test
    public void testRegistrationForm() {
        // Registration form exist
        WebElement registrationForm = driver.findElement(ByTestId.testId("registration-form"));
        assertTrue(registrationForm.isDisplayed());
        assertEquals("form", registrationForm.getTagName());
        assertEquals("/registration", registrationForm.getDomAttribute("action"));
        assertEquals("post", registrationForm.getDomAttribute("method"));

        WebElement formTitle = registrationForm.findElement(By.tagName("h1"));
        assertEquals("Registration form", formTitle.getText());

        // Registration form has user name label
        WebElement userNameLabel = registrationForm.findElement(By.cssSelector("label[for='user']"));
        assertTrue(userNameLabel.isDisplayed());
        assertEquals("User name", userNameLabel.getText());

        // Registration form has user name input
        WebElement userNameInput = registrationForm.findElement(ByTestId.testId("user-name-input"));
        assertTrue(userNameInput.isDisplayed());
        assertEquals("text", userNameInput.getDomAttribute("type"));
        assertEquals("user", userNameInput.getDomAttribute("id"));

        // Registration form has user password label
        WebElement userPasswordLabel = registrationForm.findElement(By.cssSelector("label[for='password']"));
        assertTrue(userPasswordLabel.isDisplayed());
        assertEquals("Password", userPasswordLabel.getText());

        // Registration form has user password input
        WebElement userPasswordInput = registrationForm.findElement(ByTestId.testId("user-password-input"));
        assertTrue(userPasswordInput.isDisplayed());
        assertEquals("password", userPasswordInput.getDomAttribute("type"));
        assertEquals("password", userPasswordInput.getDomAttribute("id"));

        // Registration form has submit button
        WebElement submitButton = registrationForm.findElement(ByTestId.testId("submit-button"));
        assertTrue(submitButton.isDisplayed());
    }
}
