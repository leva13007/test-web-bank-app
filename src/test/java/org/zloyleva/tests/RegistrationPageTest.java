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

public class RegistrationPageTest {

    WebDriver driver;
    String url = AppURL.REGISTRATION.getDescription();

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

        // todo add check error message elements existence
        WebElement userErrorMessage = registrationForm.findElement(By.id("error-user"));
        assertEquals(false, userErrorMessage.isDisplayed());

        WebElement passwordErrorMessage = registrationForm.findElement(By.id("error-password"));
        assertEquals(false, passwordErrorMessage.isDisplayed());
    }

    @Test
    public void testRegistrationFlow() {
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

        // TODO: check the header it should has the logout form and Greeting message!
    }

    @Test
    public void testRegistrationWithExistingUser() {
        // Fill in registration form with existing user
        SecureRandom secureRandom = new SecureRandom();
        int randomInt = secureRandom.nextInt(10000);
        String existingUserName = "existinguser" + randomInt;

        WebElement userNameInput = driver.findElement(ByTestId.testId("user-name-input"));
        userNameInput.sendKeys(existingUserName);
        WebElement userPasswordInput = driver.findElement(ByTestId.testId("user-password-input"));
        userPasswordInput.sendKeys("Cucumber123!");
        WebElement submitButton = driver.findElement(ByTestId.testId("submit-button"));
        submitButton.click();
        // Check for error message
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.urlToBe(AppURL.HOME.getDescription()));

        // do the navigate to the registration page again
        driver.get(url);
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.urlToBe(AppURL.REGISTRATION.getDescription()));

        WebElement userNameInput1 = driver.findElement(ByTestId.testId("user-name-input"));
        userNameInput1.sendKeys(existingUserName);
        WebElement userPasswordInput1 = driver.findElement(ByTestId.testId("user-password-input"));
        userPasswordInput1.sendKeys("Cucumber123!");
        WebElement submitButton1 = driver.findElement(ByTestId.testId("submit-button"));
        submitButton1.click();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.visibilityOfElementLocated(ByTestId.testId("error-registration")));

        assertTrue(errorMessage.isDisplayed());
        assertEquals("Got an error during registration process, call the support team", errorMessage.getText());

        String currentUrl = driver.getCurrentUrl();
        assertEquals(AppURL.REGISTRATION.getDescription(), currentUrl);
    }

    @Test
    public void testValidationFailWhenFieldsAreEmpty() {
        WebElement registrationForm = driver.findElement(ByTestId.testId("registration-form"));
        WebElement submitButton = registrationForm.findElement(ByTestId.testId("submit-button"));
        submitButton.click();

        WebElement userErrorMessage = registrationForm.findElement(By.id("error-user"));
        assertTrue(userErrorMessage.isDisplayed());
        assertEquals("User name must be at least 3 characters long.", userErrorMessage.getText());

        WebElement passwordErrorMessage = registrationForm.findElement(By.id("error-password"));
        assertTrue(passwordErrorMessage.isDisplayed());
        assertEquals("Password must be at least 8 characters long and include uppercase, lowercase, number, and special character.", passwordErrorMessage.getText());

        WebElement userNameInput = registrationForm.findElement(By.id("user"));
        assertTrue(userNameInput.isDisplayed());
        assertTrue(userNameInput.getDomAttribute("class").contains("is-invalid"));

        WebElement userPasswordInput = registrationForm.findElement(By.id("password"));
        assertTrue(userPasswordInput.isDisplayed());
        assertTrue(userPasswordInput.getDomAttribute("class").contains("is-invalid"));
    }

    @Test
    public void testValidationFailWhenPasswordIsWeak() {
        WebElement registrationForm = driver.findElement(ByTestId.testId("registration-form"));
        WebElement userNameInput = registrationForm.findElement(By.id("user"));
        userNameInput.sendKeys("validusername");
        WebElement userPasswordInput = registrationForm.findElement(By.id("password"));
        userPasswordInput.sendKeys("weak"); // weak password
        WebElement submitButton = registrationForm.findElement(ByTestId.testId("submit-button"));
        submitButton.click();

        WebElement passwordErrorMessage = registrationForm.findElement(By.id("error-password"));
        assertTrue(passwordErrorMessage.isDisplayed());
        assertEquals("Password must be at least 8 characters long and include uppercase, lowercase, number, and special character.", passwordErrorMessage.getText());

        assertTrue(userPasswordInput.isDisplayed());
        assertTrue(userPasswordInput.getDomAttribute("class").contains("is-invalid"));
    }

    @Test
    public void testValidationFailWhenUserNameIsEmpty() {
        WebElement registrationForm = driver.findElement(ByTestId.testId("registration-form"));
        WebElement userPasswordInput = registrationForm.findElement(By.id("password"));
        userPasswordInput.sendKeys("StrongPass1!"); // valid password
        WebElement submitButton = registrationForm.findElement(ByTestId.testId("submit-button"));
        submitButton.click();

        WebElement userErrorMessage = registrationForm.findElement(By.id("error-user"));
        assertTrue(userErrorMessage.isDisplayed());
        assertEquals("User name must be at least 3 characters long.", userErrorMessage.getText());

        WebElement userNameInput = registrationForm.findElement(By.id("user"));
        assertTrue(userNameInput.isDisplayed());
        assertTrue(userNameInput.getDomAttribute("class").contains("is-invalid"));
    }

    @Test
    public void testValidationFailWhenPasswordIsEmpty() {
        WebElement registrationForm = driver.findElement(ByTestId.testId("registration-form"));
        WebElement userNameInput = registrationForm.findElement(By.id("user"));
        userNameInput.sendKeys("validusername"); // valid username
        WebElement submitButton = registrationForm.findElement(ByTestId.testId("submit-button"));
        submitButton.click();

        WebElement passwordErrorMessage = registrationForm.findElement(By.id("error-password"));
        assertTrue(passwordErrorMessage.isDisplayed());
        assertEquals("Password must be at least 8 characters long and include uppercase, lowercase, number, and special character.", passwordErrorMessage.getText());

        WebElement userPasswordInput = registrationForm.findElement(By.id("password"));
        assertTrue(userPasswordInput.isDisplayed());
        assertTrue(userPasswordInput.getDomAttribute("class").contains("is-invalid"));
    }
}