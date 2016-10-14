package transitions;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by tahni on 14/10/2016.
 */
public class ExitTransition {

    private static final int TRANSITION_DURATION = 150;
    private static FadeTransition ft;
    private static Stage primaryStage;

    public static void play(Pane pane) {
        ft = new FadeTransition(Duration.millis(TRANSITION_DURATION), pane);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.play();
        primaryStage = (Stage) pane.getScene().getWindow();
        ft.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
    }
}
