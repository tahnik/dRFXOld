package controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private double yScroll = -200;

    private TranslateTransition scrollTransition;
    private boolean scrollTransitionPlaying = false;

    private int scrollDownTransitionWaiting = 0;
    private double prevScrollTrans = 0;

    private Thread thread;
    boolean firstOnePlaying = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        thread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                if(scrollTransitionPlaying) {
                    synchronized (thread) {
                        try {
                            thread.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (scrollDownTransitionWaiting > 0 && !scrollTransitionPlaying) {
                    if(scrollDownTransitionWaiting == 1) {
                        System.out.println("Settings First one playing to true");
                        firstOnePlaying = true;
                    }
                    double pixelsToScroll = yScroll;
                    double durationToScroll = 150;
                    if(scrollDownTransitionWaiting != 0) {
                        durationToScroll = (150 / yScroll) * pixelsToScroll;
                        System.out.println(durationToScroll);
                    }
                    if(scrollDownTransitionWaiting > 1) {
                        pixelsToScroll = yScroll + scrollDownTransitionWaiting;
                    }
                    scrollTransition = new TranslateTransition(Duration.millis(Math.abs(durationToScroll)), mainScrollPane);
                    scrollTransition.setByY(pixelsToScroll);
                    prevScrollTrans = scrollDownTransitionWaiting;
                    scrollTransition.play();
                    if(firstOnePlaying && scrollDownTransitionWaiting > 1) {
                        System.out.println("First one playing. Stopping");
                        scrollTransition.stop();
                        firstOnePlaying = false;
                        scrollTransitionPlaying = false;
                    }else {
                        scrollTransitionPlaying = true;
                        System.out.println("Scroll Transition Waiting: " + scrollDownTransitionWaiting);
                        System.out.println(prevScrollTrans);
                        System.out.println("First one playing: " + firstOnePlaying);
                    }
                    scrollTransition.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            scrollTransitionPlaying = false;
                            scrollDownTransitionWaiting -= prevScrollTrans;
                        }
                    });
                }
            }
        });
        thread.start();
        mainScrollPane.setOnScroll((event) -> {
            if(event.getDeltaY() < 0) {
                scrollDownTransitionWaiting++;
                System.out.println(event.getDeltaY());
                synchronized (thread) {
                    thread.notify();
                }
            }
        });
    }
}
