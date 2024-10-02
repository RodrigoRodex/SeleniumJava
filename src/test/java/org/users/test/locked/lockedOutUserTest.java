package org.users.test.locked;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.users.test.ConfigLoader;

public class lockedOutUserTest {

    private WebDriver navegador;

    @BeforeEach
    public void setup(){
        navegador = ConfigLoader.inicializador("https://www.saucedemo.com/inventory.html");
        ConfigLoader config = new ConfigLoader("src/test/resources/credentials");

        WebElement userNamePath = navegador.findElement(By.xpath("//*[@id=\"user-name\"]"));
        userNamePath.sendKeys(config.getUsername("locked"));

        WebElement passwordPath = navegador.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordPath.sendKeys(config.getPassword());

        WebElement loginBtn = navegador.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginBtn.click();
    }

    @AfterEach
    public void end(){
        navegador.close();
    }

    @Test
    @DisplayName("Testar os itens no locked user")
    public void locked(){
        WebElement errorContainer = navegador.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3"));
        String errorMessage = errorContainer.getText();
        Assertions.assertEquals(errorMessage, "Epic sadface: Sorry, this user has been locked out.");
    }
}
