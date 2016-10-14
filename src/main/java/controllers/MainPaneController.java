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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import transitions.ExitTransition;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tahnik on 13/10/2016.
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
    @FXML
    private GridPane mainPaneTitleBar;

    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize(URL location, ResourceBundle resources) {

        icon_main_exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                ExitTransition.play(mainGUI);
            }
        });

        mainPaneTitleBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Stage primaryStage = (Stage) mainPaneTitleBar.getScene().getWindow();
                xOffset = primaryStage.getX() - event.getScreenX();
                yOffset = primaryStage.getY() - event.getScreenY();
            }
        });

        mainPaneTitleBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Stage primaryStage = (Stage) mainPaneTitleBar.getScene().getWindow();
                primaryStage.setX(event.getScreenX() + xOffset);
                primaryStage.setY(event.getScreenY() + yOffset);
            }
        });
    }
}
