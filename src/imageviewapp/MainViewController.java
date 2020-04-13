package imageviewapp;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rados
 */
public class MainViewController implements Initializable {

    @FXML
    private Parent root;
    @FXML
    private Button btnLoad;
    @FXML
    private Button btnPrevious;
    @FXML
    private Button btnNext;
    @FXML
    private ImageView imageView;
    @FXML
    private Button btnNext1;
    @FXML
    private Button btnNext2;
    @FXML
    private Label lblFilename;

    private final List<Image> images = new ArrayList<>();
    private final List<String> filenames = new ArrayList<>();
    private int currentImageIndex = 0;
    private ScheduledExecutorService executor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void displayImage() {
        if (!images.isEmpty() || filenames.isEmpty()) {
            lblFilename.setText(filenames.get(currentImageIndex));
            imageView.setImage(images.get(currentImageIndex));
        }
    }

    @FXML
    private void handleBtnStartSlideshow(ActionEvent event) {

        Runnable slideshow = new Slideshow(imageView, images, lblFilename, filenames);
        executor = Executors.newSingleThreadScheduledExecutor();
        try {
            executor.scheduleAtFixedRate(slideshow, 1, 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void handleBtnStopSlideshow(ActionEvent event) {
        executor.shutdown();
    }

    @FXML
    private void handleBtnLoadAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty()) {
            files.forEach((File f) -> {
                filenames.add(f.getName());
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();
        }
    }

    @FXML
    private void handleBtnPreviousAction(ActionEvent event) {
        if (!images.isEmpty()) {
            currentImageIndex = (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }

    }

    @FXML
    private void handleBtnNextAction(ActionEvent event) {
        if (!images.isEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
        }

    }
}
