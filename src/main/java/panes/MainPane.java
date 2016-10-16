package panes;

import controllers.MainPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import utilities.Constants;

import java.io.IOException;

/**
 * Created by tahnik on 16/10/2016.
 */
public class MainPane {
    AnchorPane homePane = null;
    public Scene getMainPane() {
        FXMLLoader mainPaneFXML = new FXMLLoader(getClass().getResource(Constants.PATH_MAINPANEFXML));
        FXMLLoader homePaneFXML = new FXMLLoader(getClass().getResource(Constants.PATH_HOMEPANEFXML));

        //Get the singleton instance of the controller
        MainPaneController mainPaneController = MainPaneController.getInstance();

        //Set the controller of the main pane
        mainPaneFXML.setController(mainPaneController);

        VBox mainPane = null;
        homePane = null;
        try {
            mainPane = mainPaneFXML.load();
            homePane = homePaneFXML.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert mainPane != null;
        mainPane.getChildren().add(homePane);

        Scene mainPaneScene = new Scene(mainPane, Constants.WIDTH, Constants.HEIGHT);
        assert homePane != null;
        mainPaneScene.heightProperty().addListener((ov, t, t1) -> {
            homePane.setPrefHeight(t1.doubleValue() - 30);
        });
        mainPaneScene.setFill(null);

        return mainPaneScene;
    }
}
