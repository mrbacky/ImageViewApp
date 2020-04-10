/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageviewapp;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
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

    private final List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;
    @FXML
    private Button btnNext1;
    @FXML
    private Button btnNext2;
    private ExecutorService executor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        btnLoad.setOnAction((ActionEvent event) -> {
//            handleBtnLoadAction(event);
//        });
//        btnPrevious.setOnAction((ActionEvent event) -> {
//            handleBtnPreviousAction(event);
//        });
//        btnNext.setOnAction((ActionEvent event) -> {
//            handleBtnNextAction(event);
//        });
    }

    private void displayImage() {
        if (!images.isEmpty()) {
            imageView.setImage(images.get(currentImageIndex));
        }
    }

    @FXML
    private void handleBtnStartSlideshow(ActionEvent event) {
        Runnable slideshow = new Slideshow(imageView, images);
        executor = Executors.newSingleThreadExecutor();
        executor.submit(slideshow);
    }

    @FXML
    private void handleBtnStopSlideshow(ActionEvent event) {
        executor.shutdown();

    }

    @FXML
    private void handleBtnLoadAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty()) {
            files.forEach((File f)
                    -> {
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();
        }
    }

    @FXML
    private void handleBtnPreviousAction(ActionEvent event) {
        {
            if (!images.isEmpty()) {
                currentImageIndex = (currentImageIndex - 1 + images.size()) % images.size();
                displayImage();
            }
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
