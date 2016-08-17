package farhad.rddha;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainApp extends Application {

    public static String inputQuery;
    public static String[] Result = new String[500];
    public static String[] Thumbnail_Link = new String[500];
    public static String[] Video_Link = new String[500];
    public static String[] Direct_Link = new String[500];
    public static int total_item;
    public static String current;
    public static String current_title;
    public static String keys;
    
    public static String dest_location = System.getProperty("user.home") + "/Desktop";

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Design.fxml"));

        Scene scene = new Scene(root);
        LOAD_SNIPPET_AND_CONTENT_DETAILS_FILE();
        stage.setTitle("RDDHA 1.0");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("/pics/youtube1.png").toString()));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
});
    }

    void LOAD_SNIPPET_AND_CONTENT_DETAILS_FILE() throws IOException {

        BufferedReader br = null;
        URL inputUrl = getClass().getResource("/userfile/youtube.properties");
        File dest1 = new File("upload/rddh_log.txt");
        FileUtils.copyURLToFile(inputUrl, dest1);

        String line;
        br = new BufferedReader(new FileReader(dest1.toString()));
        line = br.readLine();
        System.out.println("MY KEYS: " + line);
        keys = line;
    }

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
            Alert alert = new Alert(AlertType.WARNING);
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
//    public void SEARCH_IT(String aa) {
//
//        Properties properties = new Properties();
//        try {
//            InputStream in = MainApp.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
//            properties.load(in);
//
//        } catch (IOException e) {
//            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
//                    + " : " + e.getMessage());
//            System.exit(1);
//        }
//
//        try {
//            /*
//       * The YouTube object is used to make all API requests. The last argument is required, but
//       * because we don't need anything initialized when the HttpRequest is initialized, we override
//       * the interface and provide a no-op function.
//             */
//            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
//                public void initialize(HttpRequest request) throws IOException {
//                }
//            }).setApplicationName("youtube-cmdline-search-sample").build();
//
//            // Get query term from user.
//            String queryTerm = aa;//getInputQuery();
//            //System.out.println("=================== " + aa + " =============================");
//
//            YouTube.Search.List search = youtube.search().list("id,snippet");
//
//            /*
//       * It is important to set your developer key from the Google Developer Console for
//       * non-authenticated requests (found under the API Access tab at this link:
//       * code.google.com/apis/). This is good practice and increased your quota.
//             */
//            String apiKey = properties.getProperty("youtube.apikey");
//            search.setKey(apiKey);
//            search.setQ(queryTerm);
//            /*
//       * We are only searching for videos (not playlists or channels). If we were searching for
//       * more, we would add them as a string like this: "video,playlist,channel".
//             */
//            search.setType("video, playlist");
//            /*
//       * This method reduces the info returned to only the fields we need and makes calls more
//       * efficient.
//             */
//            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
//            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
//            SearchListResponse searchResponse = search.execute();
//
//            List<SearchResult> searchResultList = searchResponse.getItems();
//
//            if (searchResultList != null) {
//                prettyPrint(searchResultList.iterator(), queryTerm);
//            }
//        } catch (GoogleJsonResponseException e) {
//            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
//                    + e.getDetails().getMessage());
//        } catch (IOException e) {
//            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//
//    }

    /*
   * Returns a query term (String) from user via the terminal.
     */
//    private String getInputQuery() throws IOException {
//
//        //String inputQuery = "";
//        //System.out.print("Please enter a search term: ");
//        //BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
//        //inputQuery = bReader.readLine();
//        inputQuery = "Obosh Onuvutir Deyal";
//
//        if (inputQuery.length() < 1) {
//            // If nothing is entered, defaults to "YouTube Developers Live."
//            inputQuery = "YouTube Developers Live";
//        }
//
//        return inputQuery;
//    }

    /*
   * Prints out all SearchResults in the Iterator. Each printed line includes title, id, and
   * thumbnail.
   *
   * @param iteratorSearchResults Iterator of SearchResults to print
   *
   * @param query Search query (String)
     */
//    private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {
//
//        /*System.out.println("\n=============================================================");
//        System.out.println(
//                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
//        System.out.println("=============================================================\n");*/
//        
//                
//        if (!iteratorSearchResults.hasNext()) {
//            System.out.println(" There aren't any results for your query.");
//        }
//        int i = 0;
//
//        while (iteratorSearchResults.hasNext()) {
//            
//            //System.out.println("Has reached");
//
//            SearchResult singleVideo = iteratorSearchResults.next();
//            ResourceId rId = singleVideo.getId();
//
//            // Double checks the kind is video.
//            if (rId.getKind().equals("youtube#video")) {
//                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().get("default");
//                Result[i] = singleVideo.getSnippet().getTitle();
//                Video_Link[i] = rId.getVideoId();
//                Thumbnail_Link[i] = thumbnail.getUrl();
//                i++;
//                /*System.out.println(" Video Id" + rId.getVideoId());
//                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
//                System.out.println(" Thumbnail: " + thumbnail.getUrl());
//                System.out.println("\n-------------------------------------------------------------\n");*/
//            }
//        }
//    }
    private static MainApp instance;

    public MainApp() {
        instance = this;
    }
// static method to get instance of view

    public static MainApp getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
