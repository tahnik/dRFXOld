package transitions;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Created by tahni on 16/10/2016.
 */
public class StartupTransition {
    private static final int TRANSITION_DURATION = 300;

    public static void play(Pane pane) {
        ScaleTransition st = new ScaleTransition(Duration.millis(TRANSITION_DURATION + 200), pane);
        st.setFromX(0.9f);
        st.setFromY(0.9f);
        st.setToX(1.0f);
        st.setToY(1.0f);

        FadeTransition ft = new FadeTransition(Duration.millis(TRANSITION_DURATION), pane);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        st.play();
    }
}
