import javafx.application.Application;
import javafx.stage.Stage;
import stages.MainStage;

/**
 * Created by tahnik on 13/10/2016.
 *
 * Entry point for the application. Opens up the main pane
 */
public class devRant extends Application {
    public void start(Stage primaryStage) throws Exception {
        new MainStage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
