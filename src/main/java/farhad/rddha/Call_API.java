/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farhad.rddha;

import static farhad.rddha.MainApp.Result;
import static farhad.rddha.MainApp.Thumbnail_Link;
import static farhad.rddha.MainApp.Video_Link;
import static farhad.rddha.MainApp.keys;
import static farhad.rddha.MainApp.total_item;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author rafikfarhad
 */
public class Call_API {
    
    public void GET_ALL_DATA_FROM_PLAYLIST(String query) throws Exception {

        query = "=" + query;
        query = query.substring(query.lastIndexOf('=') + 1);
        //System.out.println("QUERY: " + query);
 
        String jsonData = "";
        String jsonData2 = "";

        {
            URL yahoo = new URL("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId="
                    + query + "&key=" + keys);
            URLConnection yc = yahoo.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonData2 += inputLine;
                //System.out.println(inputLine);
            }
            in.close();

        }
        {
            URL yahoo = new URL("https://www.googleapis.com/youtube/v3/playlistItems?part=contentDetails&maxResults=50&playlistId="
                    + query + "&key=" + keys);
            URLConnection yc = yahoo.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonData += inputLine;
                //System.out.println(inputLine);
            }
            in.close();
        }
        
        JSONObject main_obj = new JSONObject(jsonData);

        JSONObject main_obj2 = new JSONObject(jsonData2);

        //System.out.println("blogURL: " + pageInfo.getString("totalResults"));
        total_item = 0;

        JSONObject pageInfo = main_obj.getJSONObject("pageInfo");
        total_item = pageInfo.getInt("totalResults");
        JSONArray jinishpati = main_obj.getJSONArray("items");
        JSONArray jinishpati2 = main_obj2.getJSONArray("items");
        //System.out.println("blogURL: " + pageInfo.getInt("totalResults"));
        if (total_item > 48) {
            total_item = 48;
        }
        for (int i = 0; i < total_item; i++) {

            Result[i] = jinishpati2.getJSONObject(i).getJSONObject("snippet").getString("title");
            Video_Link[i] = jinishpati.getJSONObject(i).getJSONObject("contentDetails").getString("videoId");
            Thumbnail_Link[i] = jinishpati2.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url");
            //System.out.println(Result[i]);
            //System.out.println(Video_Link[i]);
            //System.out.println(Thumbnail_Link[i]);

        }
    }

    public void GET_ALL_DATA_FROM_VIDEO(String query) throws IOException {

        query = "=" + query;
        query = query.substring(query.lastIndexOf('=') + 1);
        System.out.println(query + " -> \n" + keys + "\n");
        String jsonData = "";
        String jsonData2 = "";
        try {
            {
                URL yahoo = new URL("https://www.googleapis.com/youtube/v3/videos?id="
                        + query + "&part=snippet&key=" + keys);

                System.out.println(yahoo.toString() + "\n");
                URLConnection yc = yahoo.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    jsonData2 += inputLine;
                    //System.out.println(inputLine);
                }
                in.close();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Invalid Playlist Link");
            alert.setContentText("Please check your playlist link, it may either be broken \n"
                    + "or may be this playlist is not public");
            alert.showAndWait();
            return;
        }

        JSONObject main_obj2 = new JSONObject(jsonData2);

        //System.out.println("blogURL: " + pageInfo.getString("totalResults"));
        JSONArray jinishpati2 = main_obj2.getJSONArray("items");
        total_item = 0;
        Result[0] = jinishpati2.getJSONObject(0).getJSONObject("snippet").getString("title");
        Video_Link[0] = query;
        Thumbnail_Link[0] = jinishpati2.getJSONObject(0).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url");
        total_item = 1;

    }
    
}
