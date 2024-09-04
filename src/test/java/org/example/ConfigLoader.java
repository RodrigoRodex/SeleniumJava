package org.example;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigLoader {
    private JsonObject jsonObject;

    public ConfigLoader(String filePath){
        try (JsonReader jsonReader = Json.createReader(new FileReader(filePath))){
            jsonObject = jsonReader.readObject();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getUsername(){
        return jsonObject.getString("username");
    }

    public String getPassword(){
        return jsonObject.getString("password");
    }
}
