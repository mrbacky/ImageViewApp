package imageviewapp;

import java.util.List;
import java.util.concurrent.TimeUnit;
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
            try {
                while (true) {
                    imageView.setImage(images.get(index));
                    index = (index + 1) % images.size();
                    TimeUnit.SECONDS.sleep(DELAY);
                    //When sleeping, the thread goes to the blocking stage.
                }
            } catch (InterruptedException ex) {
                System.out.println("The slide show was stopped.");
            }
        }
    }
}
