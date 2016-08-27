package farhad.rddha;

import farhad.rddha.Controller.MainController;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * If all files selected from a playlist then this class is used for grabbing
 * all download link of playlist.
 *
 * @author rafikfarhad
 */
public final class Find_ALL {

    public static int loaded = 0;
    public static int err = 0;

    /**
     * Constructor It starts to grabbing data automatically from
     * www.keepvid.com;
     */
    public Find_ALL() {
        String my_format = MainController.all_load_format;
        String[] link = MainApp.Video_Link;
        int tot = MainApp.total_item;
        MainController.pree = new String[tot];

        String got = null;
        for (int i = 0; i < tot; i++) {
            try {
                if (null != my_format) {
                    switch (my_format) {
                        case "MP4 480pixel":
                            got = CALL_480(link[i]);
                            break;
                        case "WEBM 360pixel":
                            got = CALL_webm(link[i]);
                            break;
                        case "M4A":
                            got = CALL_m4a(link[i]);
                            break;
                        default:
                            break;
                    }
                }
                MainApp.current = MainApp.Video_Link[i];
                MainApp.current_title = MainApp.Result[i];
                System.out.println("got " + i + 1 + " -> " + got);
                if (got == null) {
                    throw new Exception();
                }
                System.out.println("after exception");
                MainController.pree[i] = got;
                loaded++;
            } catch (Exception e) {
                err = 1;
                System.out.println("In the exception + " + e.getClass());
            }
        }

    }

    /**
     *
     * @param link the video link of the video
     * @return
     * @throws MalformedURLException If there is a network error
     * @throws IOException If there is a error in open/closing error in http
     * request
     */
    String CALL_480(String link) throws MalformedURLException, IOException {
        URL yahoo = new URL("http://keepvid.com/?url=https://www.youtube.com/watch?v=" + link);
        Document document = Jsoup.connect(yahoo.toString()).timeout(20 * 1000).get();
        Elements links = document.getElementsContainingOwnText("» Download MP4 «");
        return links.first().attr("abs:href");

    }

    /**
     *
     * @param link the video link of the video
     * @return
     * @throws MalformedURLException If there is a network error
     * @throws IOException If there is a error in open/closing error in http
     * request
     */
    String CALL_webm(String link) throws MalformedURLException, IOException {
        URL yahoo = new URL("http://keepvid.com/?url=https://www.youtube.com/watch?v=" + link);
        Document document = Jsoup.connect(yahoo.toString()).timeout(20 * 1000).get();
        Elements links = document.getElementsContainingOwnText("» Download WEBM «");
        return links.first().attr("abs:href");
    }

    /**
     *
     * @param link the video link of the video
     * @return
     * @throws MalformedURLException If there is a network error
     * @throws IOException If there is a error in open/closing error in http
     * request
     */
    String CALL_m4a(String link) throws MalformedURLException, IOException {
        URL yahoo = new URL("http://keepvid.com/?url=https://www.youtube.com/watch?v=" + link);
        Document document = Jsoup.connect(yahoo.toString()).timeout(20 * 1000).get();
        Elements links = document.getElementsContainingOwnText("» Download M4A «");
        return links.first().attr("abs:href");
    }

}
