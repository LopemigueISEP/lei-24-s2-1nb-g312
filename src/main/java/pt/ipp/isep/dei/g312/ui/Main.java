package pt.ipp.isep.dei.g312.ui;

import pt.ipp.isep.dei.g312.ui.console.menu.MainMenuUI;
import pt.ipp.isep.dei.g312.ui.gui.JavaFXInitializer;


public class Main {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();

        try {
            JavaFXInitializer.initialize();
            MainMenuUI menu = new MainMenuUI();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}