/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farhad.rddha;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author rafikfarhad
 */
public class DATA extends Observable implements Runnable, Observer {

    public ProgressBar bar;
    public Button play, stop, proxy;
    public Label HeadLine;
    public int on;
    public String format, load;

    public DATA(String format, String load) throws MalformedURLException {
        bar = new ProgressBar();
        play = new Button("▮▮");
        stop = new Button("■");
        proxy = new Button();
        HeadLine = new Label("");
        bar.setPrefSize(660, 20);
        play.setPrefSize(40, 40);
        play.setAlignment(Pos.CENTER);
        stop.setPrefSize(40, 40);
        stop.setAlignment(Pos.CENTER);
        HeadLine.setPrefSize(660, 20);
        HeadLine.getStyleClass().add("HeadLine");
        play.getStyleClass().add("play-pause-button");
        stop.getStyleClass().add("stop-button");
        
        on = 1;
        this.format = format;
        this.load = load;

        ///
        this.url = new URL(load);
        size = -1;
        downloaded = 0;
        status = DOWNLOADING;
        addObserver(this);
        // Begin the download.
        download();

    }

// Max size of download buffer.
    private static final int MAX_BUFFER_SIZE = 2048;

    // These are the status codes.
    public final int DOWNLOADING = 0;
    public final int PAUSED = 1;
    public final int COMPLETE = 2;
    public final int CANCELLED = 3;
    public final int ERROR = 4;

    private URL url; // download URL
    public int size; // size of download in bytes
    public int downloaded; // number of bytes downloaded
    private int status; // current status of download

    // Get this download's size.
    public int getSize() {
        return size;
    }

    // Get this download's status.
    public int getStatus() {
        return status;
    }

    // Pause this download.
    public void go_to_pause() {
        status = PAUSED;
        stateChanged();
    }

    // Resume this download.
    public void go_to_resume() {
        status = DOWNLOADING;
        stateChanged();
        download();
    }

    // Cancel this download.
    public void cancel() {
        status = CANCELLED;
        stateChanged();
    }

    // Mark this download as having an error.
    private void error() {
        status = ERROR;
        stateChanged();
    }

    // Start or resume downloading.
    private void download() {
        Thread thread = new Thread(this);
        thread.start();
    }

    // Get file name portion of URL.
    private String getFileName(URL url) {
        String fileName = url.getFile();
        return fileName.substring(fileName.lastIndexOf('/') + 1);
    }
    RandomAccessFile file = null;
//    public file getFile(){
//        return file;
//    }
    // Download file.

    public void run() {
        file = null;
        InputStream stream = null;

        try {
            // Open connection to URL.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Specify what portion of file to download.
            connection.setRequestProperty("Range", "bytes=" + downloaded + "-");

            // Connect to server.
            connection.connect();

            // Make sure response code is in the 200 range.
            if (connection.getResponseCode() / 100 != 2) {
                error();
            }

            // Check for valid content length.
            int contentLength = connection.getContentLength();
            if (contentLength < 1) {
                error();
            }

            /* Set the size for this download if it hasn't been already set. */
            if (size == -1) {
                size = contentLength;
                stateChanged();
            }

            // Open file and seek to the end of it.
            String space = MainApp.dest_location;
            String fileName = MainApp.current_title + "." + format;
            file = new RandomAccessFile(space + "/" + fileName, "rw");
            file.seek(downloaded);

            stream = connection.getInputStream();
            while (status == DOWNLOADING) {
                /* Size buffer according to how much of the file is left to download. */
                byte buffer[];
                if (size - downloaded > MAX_BUFFER_SIZE) {
                    buffer = new byte[MAX_BUFFER_SIZE];
                } else {
                    buffer = new byte[size - downloaded];
                }
                // Read from server into buffer.
                int read = stream.read(buffer);
                if (read == -1) {
                    break;
                }
                // Write buffer to file.
                file.write(buffer, 0, read);
                downloaded += read;
                stateChanged();
            }

            /* Change status to complete if this point was reached because downloading has finished. */
            if (status == DOWNLOADING) {
                status = COMPLETE;
                stateChanged();
            }
        } catch (Exception e) {
            error();
        } finally {
            // Close file.
            if (file != null) {
                try {
                    file.close();
                } catch (Exception e) {
                }
            }
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
        setChanged();
        notifyObservers();
        bar.setProgress((double)downloaded/size);
        System.out.println("stateChanged() " + getStatus());
    }

    @Override
    public void update(Observable o, Object arg) {
        
        System.out.println("Updating... + " + downloaded);
        //http://www.java-tips.org/java-se-tips-100019/15-javax-swing/1391-how-to-create-a-download-manager-in-java.html

    }

}
