package controllers;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tahni on 16/10/2016.
 */
public class MainPaneController implements Initializable {

    @FXML
    AnchorPane homeAnchorPane;
    @FXML
    Pane mainScrollPane;

    double yScroll = -100;

    private TranslateTransition scrollTransition;
    private boolean scrollTransitionPlaying = false;

    private int scrollTransitionWaiting = 0;
    double prevScrollTrans = 0;

    Thread thread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        thread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                synchronized (thread) {
                    try {
                        thread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (scrollTransitionWaiting > 0 && !scrollTransitionPlaying) {
                    scrollTransition = new TranslateTransition(Duration.millis(100), mainScrollPane);
                    scrollTransition.setByY(yScroll * scrollTransitionWaiting);
                    prevScrollTrans = scrollTransitionWaiting;
                    scrollTransitionPlaying = true;
                    scrollTransition.play();
                    System.out.println(scrollTransitionWaiting);
                    System.out.println(prevScrollTrans);
                    scrollTransition.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            scrollTransitionPlaying = false;
                            scrollTransitionWaiting -= prevScrollTrans;
                        }
                    });
                }
            }
        });
        thread.start();
        mainScrollPane.setOnScroll((event) -> {
//            if(event.getDeltaY() < 0 && !scrollTransitionPlaying) {
//                scrollTransition = new TranslateTransition(Duration.millis(100), mainScrollPane);
//                scrollTransition.setByY(yScroll);
//                scrollTransitionPlaying = true;
//                scrollTransition.play();
//                scrollTransition.setOnFinished(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        scrollTransitionPlaying = false;
//                    }
//                });
//            }else {
//                scrollTransitionWaiting++;
//            }

            scrollTransitionWaiting++;
            synchronized (thread) {
                thread.notify();
            }
        });

        mainScrollPane.setOnScrollFinished((event) -> {
            if(scrollTransitionWaiting != 0) {
                while(scrollTransitionWaiting > 0) {
                    scrollTransition = new TranslateTransition(Duration.millis(100), mainScrollPane);
                    scrollTransition.setByY(yScroll);
                    scrollTransition.play();
                    scrollTransition.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            scrollTransitionWaiting--;
                        }
                    });
                }
            }
        });
    }
}
