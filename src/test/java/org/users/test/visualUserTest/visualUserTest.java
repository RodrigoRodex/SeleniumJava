package org.users.test.visualUserTest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.opentest4j.AssertionFailedError;
import org.users.test.ConfigLoader;

import java.util.Arrays;

public class visualUserTest {
    private WebDriver navegador;
    private final String[] priceEspe = {"$29.99","$9.99", "$15,99","$49,99","$7,99","$15,99"};
    private final String[] Card = {"-sauce-labs-backpack\"]",
            "-sauce-labs-bike-light\"]",
            "-sauce-labs-bolt-t-shirt\"]",
            "-sauce-labs-fleece-jacket\"]",
            "-sauce-labs-onesie\"]",
            "-test.allthethings()-t-shirt-(red)\"]"};

    @BeforeEach
    public void setup(){
        navegador = ConfigLoader.inicializador("https://www.saucedemo.com/inventory.html");
        ConfigLoader config = new ConfigLoader("src/test/resources/credentials");

        WebElement userNamePath = navegador.findElement(By.xpath("//*[@id=\"user-name\"]"));
        userNamePath.sendKeys(config.getUsername("visual"));

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
    @DisplayName("verificando se a primeira imagem está correta")
    public void firstImage(){
        // imagem do primeiro card errado
        navegador.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div/div/div[1]/div[1]/a/img"));
        for (int i=0;i<=3;i++){
            WebElement filtro = navegador.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select"));
            Select select = new Select(filtro);
            select.selectByIndex(i);
            WebElement imagens = navegador.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div/div/div[1]/div[1]/a/img"));
            String imgAtt = imagens.getAttribute("src");

            try{
                Assertions.assertNotEquals("https://www.saucedemo.com/static/media/sl-404.168b1cce.jpg", imgAtt);
            }catch (AssertionFailedError e){
                ConfigLoader.addError("Primeira imagem no filtro "+i+" está errada");
            }

        }
        ConfigLoader.reportErrors();

    }


    @Test
    @DisplayName("Verificando o preço e se o filtro está alterando o preço")
    public void price(){
        // valor dos produtos errado


        for(int i=1;i<=6;i++){
            String cardPriceXpath = String.format("//*[@id=\"inventory_container\"]/div/div[%d]/div[2]/div[2]/div",i);
            WebElement cardPrice = navegador.findElement(By.xpath(cardPriceXpath));
            String price = cardPrice.getText();
            try{
                Assertions.assertEquals(priceEspe[i-1], price);
            }catch (AssertionFailedError e) {
                ConfigLoader.addError("Card: " + i + " preço esperado: " + priceEspe[i-1] + " preço encontrado: " + price);
            }
        }
        ConfigLoader.reportErrors();
    }

    @Test
    @DisplayName("Verificando se o preço se altera ao trocar o filtro")
    public void priceFil(){
        // filtro faz os preços mudarem
        for (int i=0;i<=3;i++) {
            WebElement filtro = navegador.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select"));
            Select select = new Select(filtro);
            select.selectByIndex(i);

            String cardPriceXpathNew = "//*[@id=\"inventory_container\"]/div/div[1]/div[2]/div[2]/div";
            WebElement cardPriceNew = navegador.findElement(By.xpath(cardPriceXpathNew));
            String priceNew = cardPriceNew.getText();

            try{
                Assertions.assertEquals(priceNew,priceEspe[0]);
            }catch (AssertionFailedError e){
                ConfigLoader.addError("Valor esperado: "+priceEspe[0]+" valor encontrado: "+priceNew);
            }
        }
        ConfigLoader.reportErrors();
    }

    @Test
    @DisplayName("Verificando o lugar dos elementos")
    public void loc(){
        int[] carrOld = {852};
        int[] btnOld = {702};
        int[] checkbtn = {694};

        try{
            WebElement carrNew = navegador.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a"));
            carrNew.getLocation();
            Assertions.assertEquals(carrOld[0],carrNew);
        }catch (AssertionFailedError e){
            ConfigLoader.addError("Localização do carrinho está errada");
        }

        for (String s : Card){
            try{
                String addBtnXPath =  String.format("%s","//*[@id=\"add-to-cart"+s);
                WebElement addBtn = navegador.findElement(By.xpath(addBtnXPath));
                int btnX = addBtn.getLocation().getX();
                Assertions.assertEquals(btnOld[0], btnX);
            }catch (AssertionFailedError e){
                ConfigLoader.addError("Botão de index: "+s+" está no lugar errado");
            }
        }
        try {
            WebElement carrinho = navegador.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a"));
            carrinho.click();
            WebElement carrinhoXPath = navegador.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a"));
            int carX = carrinhoXPath.getLocation().getX();
            Assertions.assertEquals(checkbtn[0],carX);
        }catch (AssertionFailedError e){
            ConfigLoader.addError("Botão de Checkout no lugar errado");
        }
        ConfigLoader.reportErrors();

        // lugar do carrinho errado
        // último card com adicionar, no lugar errado
        // checkout no lugar errado


    }
}
