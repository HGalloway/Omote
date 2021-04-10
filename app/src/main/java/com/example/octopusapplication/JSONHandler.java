package com.example.octopusapplication;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONHandler {
    public void WritingToJSONFile(){

    }
    public JSONObject ReadJSONFile(Context Context, String FILE_NAME) throws IOException, JSONException {
        File file = new File(Context.getFilesDir(),FILE_NAME);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        String responce = stringBuilder.toString();
        JSONObject JSONObject  = new JSONObject(responce);

        return JSONObject;

    }
}
