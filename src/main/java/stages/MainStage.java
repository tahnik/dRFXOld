package stages;

import controllers.MainPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import panes.MainPane;
import utilities.Constants;

import java.io.IOException;

/**
 * Created by tahnik on 13/10/2016.
 *
 * This is the main pane. It will decides whether the user is already authenticated and will redirect to appropriate pages
 */
public class MainStage extends Stage {

    private Scene mainPane;

    public MainStage() {
        //Set the style to undecorated so it doesn't use the default title bar of the operating system
        this.initStyle(StageStyle.UNDECORATED);

        //Set the stage background to transparent and fill to null so we can use our own fade out animations
        //Without this when fading out a scene it will show white background
        this.initStyle(StageStyle.TRANSPARENT);

        MainPane mainPane = new MainPane();

        this.setScene(mainPane.getMainPane());
        this.show();
    }
}
