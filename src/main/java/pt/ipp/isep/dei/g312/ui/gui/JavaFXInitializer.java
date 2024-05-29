package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


public class JavaFXInitializer extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Não faça nada aqui, isso é apenas para inicializar a plataforma
    }

    public static void initialize() {
        // Use uma variável estática para garantir que a plataforma seja inicializada apenas uma vez
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }
}
