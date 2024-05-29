package pt.ipp.isep.dei.g312.ui.console.menu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author Paulo Maio pam@isep.ipp.pt
 */

public class MenuItem {
    private final String description;
    private final Runnable ui;
    private final Application gui;

    public MenuItem(String description, Runnable ui) {
        if (StringUtils.isBlank(description)) {
            throw new IllegalArgumentException("MenuItem description cannot be null or empty.");
        }
        if (Objects.isNull(ui)) {
            throw new IllegalArgumentException("MenuItem does not support a null UI.");
        }

        this.description = description;
        this.ui = ui;
        this.gui=null;
    }

    public MenuItem(String description, Application gui) {
        if (StringUtils.isBlank(description)) {
            throw new IllegalArgumentException("MenuItem description cannot be null or empty.");
        }
        if (Objects.isNull(gui)) {
            throw new IllegalArgumentException("MenuItem does not support a null UI.");
        }

        this.description = description;
        this.gui = gui;
        this.ui=null;
    }

    public void run() {
        if (this.ui==null){
            Platform.runLater(() -> {
                try {
                    this.gui.start(new Stage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else {
            this.ui.run();
        }
    }

    public boolean hasDescription(String description) {
        return this.description.equals(description);
    }

    public String toString() {
        return this.description;
    }
}