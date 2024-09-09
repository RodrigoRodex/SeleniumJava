package org.users.test.standard;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.users.test.ConfigLoader;

public class cards {
    private WebDriver navegador;
    private final String[] nomesEspe = {
            "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Onesie", "Sauce Labs Red T-Shirt",
            "Sauce Labs Backpack"
    };

    private final String[] DescsEspe = {
            "Carry all the things with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.",
            "A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included.",
            "Get your testing superhero on with the Sauce Labs bolt T-shirt. From American Apparel, 100% ringspun combed cotton, heather gray with red bolt.",
            "It's not every day that you come across a midweight quarter-zip fleece jacket capable of handling everything from a relaxing day outdoors to a busy day at the office.",
            "Rib snap infant onesie for the junior automation engineer in development. Reinforced 3-snap bottom closure, two-needle hemmed sleeved and bottom won't unravel.",
            "This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton"
    };

    @BeforeEach
    public void setup(){
        navegador = ConfigLoader.inicializador("https://www.saucedemo.com/inventory.html");
        ConfigLoader config = new ConfigLoader("src/test/resources/credentials");

        WebElement userNamePath = navegador.findElement(By.xpath("//*[@id=\"user-name\"]"));
        userNamePath.sendKeys(config.getUsername("standard"));

        WebElement passwordPath = navegador.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordPath.sendKeys(config.getPassword());

        WebElement loginBtn = navegador.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginBtn.click();
    }

    @Test
    @DisplayName("Testa se os cards estão com nome e texto corretos")
    public void cardName(){
        for (int i= 0; i<=4;i++){
            String cardNameXPath = String.format("//*[@id=\"item_%d_title_link\"]/div",i);
            WebElement card = navegador.findElement(By.xpath(cardNameXPath));

            String nomeEspe = nomesEspe[i];
            String nome = card.getText();
            // Assertions.assertEquals(nome, nomeEspe);

            if (!nome.equals(nomeEspe)){
                ConfigLoader.addError("Card " + i + ": Esperado \"" + nomeEspe + "\", mas encontradao \"" + nome + "\"");
            }
            // verifica se todas os nomes estão corretos, se um estiver errado, da erro
        }
        ConfigLoader.reportErrors();
    }

    @Test
    @DisplayName("Testa se a descrição dos cards estão corretos")
    public void cardDesc(){
        for (int i = 1; i<=5;i++){
            String cardDescXPath = String.format("//*[@id=\"inventory_container\"]/div/div[%d]/div[2]/div[1]/div", i);
            WebElement card = navegador.findElement(By.xpath(cardDescXPath));

            String DescEspe = DescsEspe[i-1];
            String Desc = card.getText();

            if (!Desc.equals(DescEspe)){
                ConfigLoader.addError("Card " + i + ": Esperado \"" + DescEspe +"\", mas encontrado \"" + Desc + "\"");
            }
        }
        ConfigLoader.reportErrors();
    }
}
