package transitions;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
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

    public static void play(Pane pane) {
        ScaleTransition st = new ScaleTransition(Duration.millis(TRANSITION_DURATION + 200), pane);
        st.setFromX(1.0f);
        st.setFromY(1.0f);
        st.setToX(0.9f);
        st.setToY(0.9f);

        FadeTransition ft = new FadeTransition(Duration.millis(TRANSITION_DURATION), pane);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.play();
        st.play();
        Stage primaryStage = (Stage) pane.getScene().getWindow();
        ft.setOnFinished((event) -> {
            primaryStage.close();
        });
    }
}
