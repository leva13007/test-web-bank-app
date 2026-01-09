package org.zloyleva.tests;

import java.security.SecureRandom;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.zloyleva.utils.AppURL;
import org.zloyleva.utils.ByTestId;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginPageTest {

    WebDriver driver;
    String url = AppURL.LOGIN.getDescription();

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
        assertEquals("Bank App | Login page", title);
    }

    @Test
    public void testLoginForm() {
        WebElement loginForm = driver.findElement(ByTestId.testId("login-form"));
        assertTrue(loginForm.isDisplayed());

        assertEquals("form", loginForm.getTagName());
        assertEquals("/login", loginForm.getDomAttribute("action"));
        assertEquals("post", loginForm.getDomAttribute("method"));

        WebElement formTitle = loginForm.findElement(By.tagName("h1"));
        assertEquals("Login form", formTitle.getText());

        // Login form has user name label
        WebElement userNameLabel = loginForm.findElement(By.cssSelector("label[for='user']"));
        assertTrue(userNameLabel.isDisplayed());
        assertEquals("User name", userNameLabel.getText());

        // Login form has user name input
        WebElement userNameInput = loginForm.findElement(ByTestId.testId("user-name-input"));
        assertTrue(userNameInput.isDisplayed());
        assertEquals("text", userNameInput.getDomAttribute("type"));
        assertEquals("user", userNameInput.getDomAttribute("id"));

        // Login form has user password label
        WebElement userPasswordLabel = loginForm.findElement(By.cssSelector("label[for='password']"));
        assertTrue(userPasswordLabel.isDisplayed());
        assertEquals("Password", userPasswordLabel.getText());

        // Login form has user password input
        WebElement userPasswordInput = loginForm.findElement(ByTestId.testId("user-password-input"));
        assertTrue(userPasswordInput.isDisplayed());
        assertEquals("password", userPasswordInput.getDomAttribute("type"));
        assertEquals("password", userPasswordInput.getDomAttribute("id"));

        // Login form has submit button
        WebElement submitButton = loginForm.findElement(ByTestId.testId("submit-button"));
        assertTrue(submitButton.isDisplayed());

        // todo add check error message elements existence
        WebElement userErrorMessage = loginForm.findElement(By.id("error-user"));
        assertEquals(false, userErrorMessage.isDisplayed());

        WebElement passwordErrorMessage = loginForm.findElement(By.id("error-password"));
        assertEquals(false, passwordErrorMessage.isDisplayed());
    }

    @Test
    public void testLoginFailIfUserDoesntExist(){
         // Fill in login form
        SecureRandom secureRandom = new SecureRandom();
        int randomInt = secureRandom.nextInt(10000);
        WebElement userNameInput = driver.findElement(ByTestId.testId("user-name-input"));
        userNameInput.sendKeys("testuser" + randomInt);

        WebElement userPasswordInput = driver.findElement(ByTestId.testId("user-password-input"));
        userPasswordInput.sendKeys("Cucumber123!");

        WebElement submitButton = driver.findElement(ByTestId.testId("submit-button"));
        submitButton.click();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.visibilityOfElementLocated(ByTestId.testId("error-login")));

        assertTrue(errorMessage.isDisplayed());
        assertEquals("Got an error during login process, call the support team", errorMessage.getText());

        String currentUrl = driver.getCurrentUrl();
        assertEquals(AppURL.LOGIN.getDescription(), currentUrl);
    }

    @Test
    public void testLoginPageDoesntOpenForUser(){
        String urlRegistration = AppURL.REGISTRATION.getDescription();
        driver.get(urlRegistration);

        // Fill in registration form
        SecureRandom secureRandom = new SecureRandom();
        int randomInt = secureRandom.nextInt(10000);
        WebElement userNameInput = driver.findElement(ByTestId.testId("user-name-input"));
        userNameInput.sendKeys("testuser" + randomInt);

        WebElement userPasswordInput = driver.findElement(ByTestId.testId("user-password-input"));
        userPasswordInput.sendKeys("Cucumber123!");

        WebElement submitButton = driver.findElement(ByTestId.testId("submit-button"));
        submitButton.click();

        // Wait for redirect to home page after successful registration
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.urlToBe(AppURL.HOME.getDescription()));

        String currentUrl = driver.getCurrentUrl();
        assertEquals(AppURL.HOME.getDescription(), currentUrl);

        driver.get(url);
        String currentUrl1 = driver.getCurrentUrl();
        assertEquals(AppURL.HOME.getDescription(), currentUrl1);
    }


    // Guest can create user and after they can login with this cred
    // TC: registration -> (redirect: Home) -> logout -> (redirect: Home) -> login page -> login -> (redirect: Home)


    // Guest can create user and after they cannot login with wrong cred: 1. wrong user_name 2. wrong password
    // TC1: registration -> (redirect: Home) -> logout -> (redirect: Home) -> login page -> login
    // TC2: registration -> (redirect: Home) -> logout -> (redirect: Home) -> login page -> login


    // Validate form fields(2)
}