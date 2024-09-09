package org.users.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {
    private static final List<String> errors = new ArrayList<>();
    private JsonObject jsonObject;

    public ConfigLoader(String filePath){
        try (JsonReader jsonReader = Json.createReader(new FileReader(filePath))){
            jsonObject = jsonReader.readObject();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getUsername(String user){
        return jsonObject.getString(user);
    }

    public String getPassword(){
        return jsonObject.getString("password");
    }

    public static WebDriver inicializador(String path){
        WebDriver navegador = new ChromeDriver();
        navegador.get(path);
        return navegador;
    }

    public static void addError(String error){
        errors.add(error);
    }

    public static void reportErrors(){
        if (!errors.isEmpty()){
            StringBuilder sb = new StringBuilder();
            sb.append("Encontradas ").append(errors.size()).append(" falhas:\n");
            for (String error : errors){
                sb.append(error).append("\n");
            }
            throw new AssertionError(sb.toString());
        }
    }

}
