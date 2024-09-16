package org.users.test.problem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.users.test.ConfigLoader;

public class problemUserTest {
    private WebDriver navegador;
    private final String[] imgsEspe = {"/static/media/sauce-backpack-1200x1500.0a0b85a3.jpg",
    "/static/media/bike-light-1200x1500.37c843b0.jpg", "/static/media/bolt-shirt-1200x1500.c2599ac5.jpg",
    "/static/media/sauce-pullover-1200x1500.51d7ffaf.jpg", "/static/media/red-onesie-1200x1500.2ec615b2.jpg",
    "/static/media/red-tatt-1200x1500.30dadef4.jpg"};

    @BeforeEach
    public void setup(){
        ConfigLoader config = new ConfigLoader("src/test/resources/credentials");
        navegador = ConfigLoader.inicializador("https://www.saucedemo.com/inventory.html");

        WebElement userNamePath = navegador.findElement(By.xpath("//*[@id=\"user-name\"]"));
        userNamePath.sendKeys(config.getUsername("problem"));

        WebElement passwordPath = navegador.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordPath.sendKeys(config.getPassword());

        WebElement loginBtn = navegador.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginBtn.click();

    }

    @Test
    @DisplayName("Testa o carrinho")
    public void carrinho(){
            WebElement addBtn = navegador.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]"));
            addBtn.click();
            navegador.findElement(By.xpath("//*[@id=\"remove-sauce-labs-backpack\"]")).click();
            Assertions.assertFalse(addBtn.isDisplayed());
        // navegador.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
    }
    @Test
    @DisplayName("Teste de imagem")
    public void imagens(){
        for (int i = 0; i < imgsEspe.length; i++) {
            String cardImgXpath = String.format("//*[@id=\"item_%d_img_link\"]/img", i);
            WebElement imagens = navegador.findElement(By.xpath(cardImgXpath));

            String imgAtt = imagens.getAttribute("src");
            String imgEspe = imgsEspe[i];

            if (!imgAtt.equals(imgEspe)){
                ConfigLoader.addError("Card " + i + " com imagem esperada de: " + imgEspe + " teve a imagem de: " + imgAtt);
            }
        }
        ConfigLoader.reportErrors();
    }
    // imagens erradas = Testar por src, verificar se é o mesmo das outras paginas
    // consigo adicionar um item ao carrinho = Adicionar um item ao carrinho e tentar remover
    // mas não consigo tirar, pela pagina dos produtos
    // o filtro não funciona = Verificar se eles funcionam e comparar
    // checkout não me deixa digitar o LastName = Digitar e verificar se o valor do primeiro nome muda
}
