package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * The JavaFXInitializer class is responsible for initializing the JavaFX platform.
 * This class ensures that the JavaFX platform is initialized only once.
 */
public class JavaFXInitializer extends Application {
    /**
     * This method is called when the application is started.
     * It is overridden to do nothing because the purpose of this class is only to initialize the JavaFX platform.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
    }

    /**
     * Initializes the JavaFX platform.
     * This method ensures that the platform is initialized only once by checking if the current thread is the JavaFX application thread.
     */
    public static void initialize() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {
            });
        }
    }
}
