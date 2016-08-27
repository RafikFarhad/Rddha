/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farhad.rddha;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Observable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * This class is for download a video in a dedicated thread. It also keep a
 * progress-bar for for keep tracking how much a file being downloaded.
 *
 * @author rafikfarhad
 */
public class DATA extends Observable implements Runnable {

    public ProgressBar bar;
    public Button stop, restart;
    public Label HeadLine;
    public int on;
    public String format, load, title;

    /**
     * this constructors initilize the value, create a blank file in the local
     * memory & starts the download
     *
     * @param format the format of the video
     * @param load the downloading link
     * @throws MalformedURLException If there is a Internet Connection Error
     */
    public DATA(String format, String load) throws MalformedURLException {
        bar = new ProgressBar();
        restart = new Button();
        stop = new Button();
        HeadLine = new Label("");
        bar.setPrefSize(660, 20);
        restart.setPrefSize(40, 40);
        stop.setPrefSize(40, 40);
        stop.setAlignment(Pos.CENTER);
        HeadLine.setPrefSize(660, 20);
        HeadLine.getStyleClass().add("HeadLine");
        restart.getStyleClass().add("restart-button");
        stop.getStyleClass().add("stop-button");
        String space = MainApp.dest_location;
        title = space + "/" + MainApp.current_title + "." + format;
        on = 1;
        this.format = format;
        this.load = load;

        ///
        this.url = new URL(load);
        size = -1;
        downloaded = 0;
        status = DOWNLOADING;
        //addObserver(this);
        // Begin the download.
        download();

    }
    //States Of Downloads
    public static final int DOWNLOADING = 0;
    public static final int PAUSED = 1;
    public static final int COMPLETE = 2;
    public static final int CANCELLED = 3;
    public static final int ERROR = 4;
    private URL url; // download URL
    public int size; // size of download in bytes
    private int downloaded; // number of bytes downloaded
    public int status; // current status of download
    public File file;

    /**
     *
     * @return the url as string
     */
    public String getUrl() {
        return url.toString();
    }

    /**
     * Get this download's size.
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * Get this download's progress.
     *
     * @return
     */
    public float getProgress() {
        return ((float) downloaded / size) * 100;
    }

    /**
     * Get this download's status.
     *
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * .
     * Mark this download as having an error
     */
    private void error() {
        status = ERROR;
        stateChanged();
    }

    /**
     * Start or resume downloading.
     */
    private void download() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * To resume download
     */
    public void resume() {
        status = DOWNLOADING;
        stateChanged();
        download();
    }

    /**
     * Cancel this download.
     */
    public void cancel() {
        status = CANCELLED;
        stateChanged();
    }

    /**
     * Get file name portion of URL.
     */
    private String getFileName(URL url) {
        String fileName = url.getFile();
        return fileName.substring(fileName.lastIndexOf('/') + 1);
    }

    /**
     * Download file.
     */
    public void run() {
        file = null;
        InputStream stream = null;

        try {
            // Open connection to URL.
            HttpURLConnection connection
                    = (HttpURLConnection) url.openConnection();

            // Specify what portion of file to download.
            connection.setRequestProperty("Range",
                    "bytes=" + downloaded + "-");

            // Connect to server.
            connection.connect();

            // Check for valid content length.
            int contentLength = connection.getContentLength();
            if (contentLength < 1) {
                error();
            }

            /* Set the size for this download if it
         hasn't been already set. */
            if (size == -1) {
                size = contentLength;
                stateChanged();
            }

            file = new File(title);
            stream = connection.getInputStream();
            Files.copy(stream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

            /* Change status to complete if this point was
         reached because downloading has finished. */
            if (status == DOWNLOADING) {
                status = COMPLETE;
                stateChanged();
            }
        } catch (Exception e) {
            error();
        } finally {

            // Close connection to server.
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {
                }
            }
        }
    }

    // Notify observers that this download's status has changed.
    private void stateChanged() {

    }

}
