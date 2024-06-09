package pt.ipp.isep.dei.g312.ui.console.menu;

import pt.ipp.isep.dei.g312.ui.console.*;
import pt.ipp.isep.dei.g312.ui.console.utils.Utils;
import pt.ipp.isep.dei.g312.ui.gui.CompleteTaskUI;
import pt.ipp.isep.dei.g312.ui.gui.TasksAssignedToMeBetweenToDatesGUI;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorUI implements Runnable {
    public CollaboratorUI(){
    }

    // TODO: Update action the collaborator can use
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("List tasks assigned to me between to dates", new TasksAssignedToMeBetweenToDatesGUI()));
        options.add(new MenuItem("Record completion of a task", new CompleteTaskUI()));



        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n---------------- Collaborator MENU -----------------",true);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
