package org.users.test.problem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
    }
    // consigo adicionar um item ao carrinho = Adicionar um item ao carrinho e tentar remover
    // mas não consigo tirar, pela pagina dos produtos

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

    @Test
    @DisplayName("Teste de carrinho")
    public void remCarrinho(){
        WebElement filtroTextOld = navegador.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/span"));

        WebElement filtro = navegador.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select"));
        Select select = new Select(filtro);
        select.selectByIndex(1);

        WebElement filtroTextNew = navegador.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/span"));
        Assertions.assertNotEquals(filtroTextOld, filtroTextNew);
    }
    // o filtro não funciona = Verificar se eles funcionam e comparar

    @Test
    @DisplayName("Teste de checkout")
    public void checkout(){
        navegador.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).click();
        navegador.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        navegador.findElement(By.xpath("//*[@id=\"checkout\"]")).click();

        WebElement priNome = navegador.findElement(By.xpath("//*[@id=\"first-name\"]"));
        priNome.sendKeys("Maria");
        WebElement segNome = navegador.findElement(By.xpath("//*[@id=\"last-name\"]"));
        segNome.sendKeys("Serra");
        WebElement codPostal = navegador.findElement(By.xpath("//*[@id=\"postal-code\"]"));
        codPostal.sendKeys("124567890");

        String priNomeText = priNome.getText();
        String segNomeText = segNome.getText();
        String codPostalText = codPostal.getText();

        Assertions.assertEquals("Maria",priNomeText);
        Assertions.assertEquals("Serra", segNomeText);
        Assertions.assertEquals("124567890",codPostalText);

        navegador.findElement(By.xpath("//*[@id=\"continue\"]")).click();
    }
    // checkout não me deixa digitar o LastName = Digitar e verificar se o valor do primeiro nome muda
}
