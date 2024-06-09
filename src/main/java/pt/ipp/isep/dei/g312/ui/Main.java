package pt.ipp.isep.dei.g312.ui;

import pt.ipp.isep.dei.g312.ui.console.menu.MainMenuUI;
import pt.ipp.isep.dei.g312.ui.gui.JavaFXInitializer;


public class Main {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        System.out.println("Getting data from serialization...");
        bootstrap.run();


        try {
            JavaFXInitializer.initialize();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nMusgoSublime is being shut down....");
                System.out.println("\nSaving Data...");
                bootstrap.saveSeralization();
            }));
            MainMenuUI menu = new MainMenuUI();
            menu.run();

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}