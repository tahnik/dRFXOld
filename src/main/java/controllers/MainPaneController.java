package controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tahni on 13/10/2016.
 */
public class MainPaneController implements Initializable {

    private static MainPaneController mainPaneControllerSingleton = null;

    public static MainPaneController getInstance() {
        if(mainPaneControllerSingleton == null) {
            mainPaneControllerSingleton = new MainPaneController();
        }
        return mainPaneControllerSingleton;
    }

    private MainPaneController() {};

    @FXML
    private ImageView icon_main_exit;
    @FXML
    private BorderPane mainGUI;

    public void initialize(URL location, ResourceBundle resources) {

        icon_main_exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                FadeTransition ft = new FadeTransition(Duration.millis(150), mainGUI);
                ft.setFromValue(1.0);
                ft.setToValue(0);
                ft.play();
                ft.setOnFinished(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        Stage primaryStage = (Stage) icon_main_exit.getScene().getWindow();
                        primaryStage.close();
                        System.exit(0);
                    }
                });
            }
        });
    }
}
