package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tahni on 16/10/2016.
 */
public class MainPaneController implements Initializable {

    @FXML
    AnchorPane homeAnchorPane;
    @FXML
    ScrollPane mainScrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainScrollPane.setOnScroll((event) -> {
            System.out.println(event.getDeltaY());
        });
    }
}
