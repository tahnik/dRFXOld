package stages;

import controllers.MainPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.Constants;

import java.io.IOException;
import java.net.URL;

/**
 * Created by tahnik on 13/10/2016.
 *
 * This is the main pane. It will decides whether the user is already authenticated and will redirect to appropriate pages
 */
public class MainPaneStage extends Stage {

    private Scene mainPane;

    public MainPaneStage() {
        //Load the main pane fxml file
        FXMLLoader mainPaneFXML = new FXMLLoader(getClass().getResource(Constants.PATH_MAINPANEFXML));

        //Get the singleton instance of the controller
        MainPaneController mainPaneController = MainPaneController.getInstance();

        //Set the controller of the main pane
        mainPaneFXML.setController(mainPaneController);

        try {
            mainPane = new Scene(mainPaneFXML.load(), Constants.WIDTH, Constants.HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Set the style to undecorated so it doesn't use the default title bar of the operating system
        this.initStyle(StageStyle.UNDECORATED);

        //Set the stage background to transparent and fill to null so we can use our own fade out animations
        //Without this when fading out a scene it will show white background
        this.initStyle(StageStyle.TRANSPARENT);
        mainPane.setFill(null);

        this.setScene(mainPane);
        this.show();
    }
}
