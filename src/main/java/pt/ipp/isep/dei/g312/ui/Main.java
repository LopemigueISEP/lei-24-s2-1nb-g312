package pt.ipp.isep.dei.g312.ui;

import pt.ipp.isep.dei.g312.ui.Bootstrap;
import pt.ipp.isep.dei.g312.ui.console.menu.MainMenuUI;
import pt.ipp.isep.dei.g312.ui.console.menu.MenuItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Main {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();

        try {
            MainMenuUI menu = new MainMenuUI();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}