package transitions;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by tahni on 14/10/2016.
 */
public class MinimiseTransition {

    private static final int TRANSITION_DURATION = 150;

    public static void play(Pane pane) {
        FadeTransition ft = new FadeTransition(Duration.millis(TRANSITION_DURATION), pane);
        ft.setFromValue(1.0);
        ft.setToValue(0);

        ft.play();

        Stage primaryStage = (Stage) pane.getScene().getWindow();
        ft.setOnFinished((event) -> {
            primaryStage.setIconified(true);
        });
    }
}
