package imageviewapp;

import java.util.List;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author rado
 */
public class Slideshow implements Runnable {

    private final long DELAY = 1;
    private int index = 0;
    private ImageView imageView;
    private List<Image> images;

    public Slideshow(ImageView imageView, List<Image> images) {
        this.imageView = imageView;
        this.images = images;
    }

    @Override
    public void run() {
        if (!images.isEmpty()) {
            while (!Thread.currentThread().isInterrupted()) {
                Platform.runLater(() -> {
                    imageView.setImage(images.get(index));
                    index = (index + 1) % images.size();
                });
            }
        }
    }

}
