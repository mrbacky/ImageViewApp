package imageviewapp;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author annem
 */
public class Slideshow implements Runnable {

    private final long DELAY = 1;
    private int index = 0;
    private ImageView imageView;
    private List<Image> images;
    private Label lblFilename;
    private List<String> filenames;

    public Slideshow(ImageView imageView, List<Image> images, Label label, List<String> filenames) {
        this.imageView = imageView;
        this.images = images;
        this.lblFilename = label;
        this.filenames = filenames;
    }

    @Override
    public void run() {
        if (!images.isEmpty()) {
            try {
                while (true) {
                    Platform.runLater(() -> {
                        imageView.setImage(images.get(index));
                        lblFilename.setText(filenames.get(index));
                    });
                    index = (index + 1) % images.size();
                    //Thread.sleep(1000);
                    TimeUnit.SECONDS.sleep(DELAY);
                    //When sleeping, the thread goes to the blocking stage.
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Slideshow.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("The slide show was stopped.");
            }
        }
    }

//    @Override
//    public void run() {
//        if (!images.isEmpty()) {
//            while (!Thread.currentThread().isInterrupted()) {
//                Platform.runLater(() -> {
//                    imageView.setImage(images.get(index));
//                    index = (index + 1) % images.size();
//                });
//
//            }
//        }
//    }
}
