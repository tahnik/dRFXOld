package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import transitions.ExitTransition;
import transitions.MinimiseTransition;
import transitions.StartupTransition;
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
    private Pane mainGUI;
    @FXML
    private GridPane mainPaneTitleBar;
    @FXML
    private ImageView icon_main_minimise;
    @FXML
    private ImageView icon_main_maximise;

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

    private boolean maximised = false;
    private double mainGUIWidthBeforeMaximising = 0;
    private double mainGUIHeightBeforeMaximising = 0;
    private double xMainGUIPositionBeforeMaximising = 0;
    private double yMainGUIPositionBeforeMaximising = 0;

    private Stage primaryStage = null;

    public void initialize(URL location, ResourceBundle resources) {

        StartupTransition.play(mainGUI);

        // Exit the GUI if the close button is pressed using a special transition
        icon_main_exit.setOnMouseClicked((event) -> {
            ExitTransition.play(mainGUI);
        });

        // Listener for resize events for the main title bar.
        // The main title bar is a GridPane. GridPane doesn't automatically resize.
        mainGUI.widthProperty().addListener((ov, t, t1) -> {
            mainPaneTitleBar.setPrefWidth(t1.doubleValue());
        });

        // Minimises the main gui.
        icon_main_minimise.setOnMouseClicked((event) -> {
            primaryStage = (Stage) icon_main_minimise.getScene().getWindow();
            // This listener checks if the GUI has been clicked from taskbar.
            // If it has been clicked from taskbar it sets the opacity back to 1
            primaryStage.iconifiedProperty().addListener((ov, t, t1) -> {
                if(!t1) {
                    mainGUI.setOpacity(1.0);
                }
            });

            // Minimises the GUI using a special transition
            MinimiseTransition.play(mainGUI);
        });

        icon_main_minimise.setOnMouseEntered((event) -> {

        });

        icon_main_maximise.setOnMouseClicked((event) -> {
            primaryStage = (Stage) icon_main_maximise.getScene().getWindow();
            if(maximised) {
                primaryStage.setX(xMainGUIPositionBeforeMaximising);
                primaryStage.setY(yMainGUIPositionBeforeMaximising);

                primaryStage.setWidth(mainGUIWidthBeforeMaximising);
                primaryStage.setHeight(mainGUIHeightBeforeMaximising);
            }else {
                Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

                xMainGUIPositionBeforeMaximising = primaryStage.getX();
                yMainGUIPositionBeforeMaximising = primaryStage.getY();

                mainGUIWidthBeforeMaximising = primaryStage.getWidth();
                mainGUIHeightBeforeMaximising = primaryStage.getHeight();

                primaryStage.setX(primaryScreenBounds.getMinX());
                primaryStage.setY(primaryScreenBounds.getMinY());

                primaryStage.setWidth(primaryScreenBounds.getWidth());
                primaryStage.setHeight(primaryScreenBounds.getHeight());
            }

            maximised = !maximised;
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

            if(widthBeingResized && event.getSceneX() >= Constants.WIDTH) {
                primaryStage.setWidth(event.getSceneX());
            }else if(heightBeingResized && event.getSceneY() >= Constants.HEIGHT) {
                primaryStage.setHeight(event.getSceneY());
            }
        });

        mainGUI.setOnMouseReleased((event -> {
            widthBeingResized = false;
            heightBeingResized = false;
            System.gc();
        }));
    }
}
