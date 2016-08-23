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
 *
 * @author rafikfarhad
 */
public class DATA extends Observable implements Runnable {

    public ProgressBar bar;
    public Button stop, restart;
    public Label HeadLine;
    public int on;
    public String format, load, title;

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
    // Max size of download buffer.
    private static final int MAX_BUFFER_SIZE = 8192;
     
    // These are the status names.
    public static final String STATUSES[] = {"Downloading",
    "Paused", "Complete", "Cancelled", "Error"};
     
    // These are the status codes.
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
    
     
    // Get this download's URL.
    public String getUrl() {
        return url.toString();
    }
     
    // Get this download's size.
    public int getSize() {
        return size;
    }
     
    // Get this download's progress.
    public float getProgress() {
        return ((float) downloaded / size) * 100;
    }
     
    // Get this download's status.
    public int getStatus() {
        return status;
    }
     
    // Pause this download.
    public void pause() {
        status = PAUSED;
        stateChanged();
    }
     
    // Resume this download.
    public void resume() {
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
     
    // Download file.
    public void run() {
        file = null;
        InputStream stream = null;
         
        try {
            // Open connection to URL.
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
             
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
                } catch (Exception e) {}
            }
        }
    }
     
    // Notify observers that this download's status has changed.
    private void stateChanged() {
//        setChanged();
//        notifyObservers();
        System.out.println("stateChanged() -> " + downloaded  + " -> " + size);
    }

//    // Max size of download buffer.
//    private static final int MAX_BUFFER_SIZE = 4096;
//
//    // These are the status codes.
//    public final int DOWNLOADING = 0;
//    public final int PAUSED = 1;
//    public final int COMPLETE = 2;
//    public final int CANCELLED = 3;
//    public final int ERROR = 4;
//
//    private URL url; // download URL
//    public int size; // size of download in bytes
//    public int downloaded; // number of bytes downloaded
//    private int status; // current status of download
//
//    // Get this download's size.
//    public int getSize() {
//        return size;
//    }
//
//    // Get this download's status.
//    public int getStatus() {
//        return status;
//    }
//
//    // Pause this download.
//    public void go_to_pause() {
//        status = PAUSED;
//        stateChanged();
//    }
//
//    // Resume this download.
//    public void go_to_resume() {
//        status = DOWNLOADING;
//        stateChanged();
//        download();
//    }
//
//    // Cancel this download.
//    public void cancel() {
//        status = CANCELLED;
//        stateChanged();
//    }
//
//    // Mark this download as having an error.
//    private void error() {
//        status = ERROR;
//        stateChanged();
//    }
//
//    // Start or resume downloading.
//    private void download() {
//        Thread thread = new Thread(this);
//        thread.start();
//    }
//
//    // Get file name portion of URL.
//    private String getFileName(URL url) {
//        String fileName = url.getFile();
//        return fileName.substring(fileName.lastIndexOf('=') + 1);
//    }
//    RandomAccessFile file = null;
////    public file getFile(){
////        return file;
////    }
//    // Download file.
//
//    public void run() {
//        file = null;
//        InputStream stream = null;
//
//        try {
//            // Open connection to URL.
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            // Specify what portion of file to download.
//            connection.setRequestProperty("Range", "bytes=" + downloaded + "-");
//
//            // Connect to server.
//            connection.connect();
//
//            // Make sure response code is in the 200 range.
//            if (connection.getResponseCode() / 100 != 2) {
//                error();
//            }
//
//            // Check for valid content length.
//            int contentLength = connection.getContentLength();
//            if (contentLength < 1) {
//                error();
//            }
//
//            /* Set the size for this download if it hasn't been already set. */
//            if (size == -1) {
//                size = contentLength;
//                stateChanged();
//            }
//
//            // Open file and seek to the end of it.
//            file = new RandomAccessFile(title, "rw");
//            file.seek(downloaded);
//
//            stream = connection.getInputStream();
//
//            while (status == DOWNLOADING) {
//                /* Size buffer according to how much of the file is left to download. */
//                byte buffer[];
//                if (size - downloaded > MAX_BUFFER_SIZE) {
//                    buffer = new byte[MAX_BUFFER_SIZE];
//                } else {
//
//                    buffer = new byte[size - downloaded];
//
//                }
//                // Read from server into buffer.
//                int read = stream.read(buffer);
//                if (read < 1) {
//                    break;
//                }
//                // Write buffer to file.
//                file.write(buffer, 0, read);
//                downloaded += read;
//                stateChanged();
//            }
//
//            /* Change status to complete if this point was reached because downloading has finished. */
//            if (status == DOWNLOADING) {
//                status = COMPLETE;
//                stateChanged();
//            }
//        } catch (Exception e) {
//            System.out.println("Here is Exception in download func() + " + e);
//            error();
//        } finally {
//            // Close file.
//            if (file != null) {
//                try {
//                    file.close();
//                } catch (Exception e) {
//                }
//            }
//            // Close connection to server.
//            if (stream != null) {
//                try {
//                    //stream.close();
//                } catch (Exception e) {
//                }
//            }
//        }
//    }
//
//    // Notify observers that this download's status has changed.
//    private void stateChanged() {
//        //setChanged();
//        //notifyObservers();
//        System.out.println("stateChanged() " + getStatus());
//    }
//
////    @Override
////    public void update(Observable o, Object arg) {
////
////        System.out.println("Updating... + " + downloaded + " -> " + size + " -> " + getFileName(url));
////        //http://www.java-tips.org/java-se-tips-100019/15-javax-swing/1391-how-to-create-a-download-manager-in-java.html
////
////    }
}
