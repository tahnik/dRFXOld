package controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import transitions.ExitTransition;
import utilities.Constants;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tahnik on 13/10/2016.
 *
 * Controller for the main stage. This will control drag, resize and OS control buttons (Minimize, Maximize and Close)
 * This controller here is a singleton as we wouldn't create a second instance of the main GUI
 *
 */
public class MainPaneController implements Initializable {

    //Singleton
    private static MainPaneController mainPaneControllerSingleton = null;

    public static MainPaneController getInstance() {
        if(mainPaneControllerSingleton == null) {
            mainPaneControllerSingleton = new MainPaneController();
        }
        return mainPaneControllerSingleton;
    }

    private MainPaneController() {};

    @FXML
    private ImageView icon_main_exit;
    @FXML
    private BorderPane mainGUI;
    @FXML
    private GridPane mainPaneTitleBar;

    // This offset will be used to calculate the movement when GUI is being dragged using the top title panel
    private double xOffset = 0;
    private double yOffset = 0;

    // Height and width of the main GUI
    private double height = 0;
    private double width = 0;

    // The amount of pixels from the edge where the GUI will respond to resize drag
    private final double xResizeOffset = 5;
    private final double yResizeOffset = 5;

    // Calculated offset using x/yResizeOffset to find the minimum offset from the edge where the GUI is resizable
    // TODO: Better explanation
    private double xMinResizeOffset = 0;
    private double yMinResizeOffset = 0;

    // Bool to check if the GUI is being resized
    private boolean widthBeingResized = false;
    private boolean heightBeingResized = false;

    private Stage primaryStage = null;

    public void initialize(URL location, ResourceBundle resources) {

        // Exit the GUI if the close button is pressed using a special transition
        icon_main_exit.setOnMouseClicked((event) -> {
            ExitTransition.play(mainGUI);
        });

        /*
            Used for dragging the GUI using the top title bar

            Logic:
            Get the position of the mouse in the screen when the mouse
            pressed on the top title bar.
            Move the GUI using that position as an offset.
        */
        mainPaneTitleBar.setOnMousePressed((event) -> {
            primaryStage = (Stage) mainPaneTitleBar.getScene().getWindow();
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });

        mainPaneTitleBar.setOnMouseDragged((event) -> {
            primaryStage = (Stage) mainPaneTitleBar.getScene().getWindow();
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });

        /*
            Shows the resize cursor when the mouse is moved on the right and bottom edge

            Logic:
            Get the width of the screen and calculate the offset. When the mouse is moved between max width and offset
            then change the cursor style
        */
        mainGUI.setOnMouseMoved((event) -> {
            primaryStage = (Stage) mainPaneTitleBar.getScene().getWindow();
            height = primaryStage.getHeight();
            width = primaryStage.getWidth();

            xMinResizeOffset = width - xResizeOffset;
            yMinResizeOffset = height - yResizeOffset;


            if((event.getX() > xMinResizeOffset && event.getX() < width)) {
                primaryStage.getScene().setCursor(Cursor.E_RESIZE);
            }else if((event.getY() > yMinResizeOffset && event.getY() < height)) {
                primaryStage.getScene().setCursor(Cursor.N_RESIZE);
            }else {
                primaryStage.getScene().setCursor(Cursor.DEFAULT);
            }

        });

        /*
            Resize the GUI

            Logic:
            When the mouse is pressed check if it's in the area where the GUI will respond to resize drag.
            If it is then set the BeingResized variable to true.

            When the mouse is dragged, it checks if the BeingResized variable is true. If it is, it allows resizing
            and sets the width according to the X/Y values of the mouse

            When the mouse is released, it sets BeingResized to false
        */
        mainGUI.setOnMousePressed((event) -> {
            if((event.getX() > xMinResizeOffset && event.getX() < width)) {
                widthBeingResized = true;
            }else if((event.getY() > yMinResizeOffset && event.getY() < height)) {
                heightBeingResized = true;
            }
        });

        mainGUI.setOnMouseDragged((event) -> {
            primaryStage = (Stage) mainPaneTitleBar.getScene().getWindow();
            height = primaryStage.getHeight();
            width = primaryStage.getWidth();

            xMinResizeOffset = width - xResizeOffset;
            yMinResizeOffset = height - yResizeOffset;

            if(widthBeingResized && event.getSceneX() >= Constants.WIDTH) {
                primaryStage.setWidth(event.getSceneX());
            }else if(heightBeingResized && event.getSceneY() >= Constants.HEIGHT) {
                primaryStage.setHeight(event.getSceneY());
            }
        });

        mainGUI.setOnMouseReleased((event -> {
            widthBeingResized = false;
            heightBeingResized = false;
        }));
    }
}
