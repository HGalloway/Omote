package com.OctoApp.Octo;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class JSONHandler {
    public final Hashtable<String, Integer> PictureList = new Hashtable<>();

    /**
     * @param FileName
     * @param Values
     * @param Context  <p>Takes the dictionary given and puts it into json format. Then the json object is put into a file.</p>
     */
    public void WritingToJSONFile(String FileName, Hashtable<?, ?> Values, Context Context) {
        JSONObject JSONObject = new JSONObject();
        try {
            for (int i = 0; i < Values.size(); ++i) {
                JSONObject.put(String.valueOf(Values.keySet().toArray()[i]), Values.values().toArray()[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String JSONString = JSONObject.toString();
        try {
            File file = new File(Context.getFilesDir(), FileName + ".json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(JSONString);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param Context
     * @param FILE_NAME
     * @return JSONObject
     * <p>Reads a json file and returns a JSONObject</p>
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject ReadJSONFile(Context Context, String FILE_NAME) throws IOException, JSONException {
        File file = new File(Context.getFilesDir(), FILE_NAME + ".json");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        String responce = stringBuilder.toString();
        JSONObject JSONObject = new JSONObject(responce);

        return JSONObject;

    }
}
