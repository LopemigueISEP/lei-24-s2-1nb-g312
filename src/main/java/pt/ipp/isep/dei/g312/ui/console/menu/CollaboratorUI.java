package pt.ipp.isep.dei.g312.ui.console.menu;

import pt.ipp.isep.dei.g312.ui.console.*;
import pt.ipp.isep.dei.g312.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorUI implements Runnable {
    public CollaboratorUI(){
    }

    // TODO: Update action the collaborator can use
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("List Vehicles due to CheckUp", new ListVehiclesDueToCheckUpUI()));



        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n---------------- Collaborator MENU -----------------",true);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
