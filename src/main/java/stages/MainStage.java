package stages;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import panes.MainPane;

/**
 * Created by tahnik on 13/10/2016.
 *
 * This is the main pane. It will decides whether the user is already authenticated and will redirect to appropriate pages
 */
public class MainStage extends Stage {

    private Scene mainPane;

    public MainStage() {
        //Set the style to undecorated so it doesn't use the default title bar of the operating system

        //Set the stage background to transparent and fill to null so we can use our own fade out animations
        //Without this when fading out a scene it will show white background

        MainPane mainPane = new MainPane();

        this.setMinWidth(600);
        this.setMinHeight(600);
        this.sizeToScene();

        this.setTitle("devRant Unofficial");
        this.getIcons().add(new Image("/resources/main/icons/devRant_small_icon.png"));


        this.setScene(mainPane.getMainPane());
        this.show();
    }
}
