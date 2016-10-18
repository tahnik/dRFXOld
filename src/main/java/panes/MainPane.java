package panes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import utilities.Constants;

import java.io.IOException;

/**
 * Created by tahnik on 16/10/2016.
 */
public class MainPane {
    AnchorPane mainPane = null;
    public Scene getMainPane() {
        FXMLLoader homePaneFXML = new FXMLLoader(getClass().getResource(Constants.PATH_MAINPANEFXML));

        mainPane = null;
        try {
            mainPane = homePaneFXML.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene mainPaneScene = new Scene(mainPane, Constants.WIDTH, Constants.HEIGHT);
        assert mainPane != null;
        mainPaneScene.setFill(null);

        return mainPaneScene;
    }
}
