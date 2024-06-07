package pt.ipp.isep.dei.g312.ui.console.menu;

import pt.ipp.isep.dei.g312.ui.console.*;
import pt.ipp.isep.dei.g312.ui.console.utils.Utils;
import pt.ipp.isep.dei.g312.ui.gui.*;

import java.util.ArrayList;
import java.util.List;

public class GSMUI implements Runnable {

    public GSMUI() {
    }

    // TODO falta alterar para as respetivas UI
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register a Green Space", new RegisterGreenSpaceUI()));
        options.add(new MenuItem("Show list of green Spaces", new ShowListOfGreenSpacesUI()));
        options.add(new MenuItem("Add entry to the To-Do List", new AddEntryToDoListUI()));
        options.add(new MenuItem("Show To-Do List", new RunTimeTestingKruskalAlgorithmUI()));
        options.add(new MenuItem("Add entry to the Agenda ", new AddEntryToAgendaUI()));
        options.add(new MenuItem("Show Agenda", new ShowListOfAgendaUI()));
        options.add(new MenuItem("Assign Team to an entry in Agenda", new AssignTeamEntryAgendaUI()));
        options.add(new MenuItem("Postpone an entry in the Agenda to a specific future date", new PostponeTaskInTheAgendaUI()));
        options.add(new MenuItem("Cancel an entry in the Agenda", new CancelEntryAgendaUI()));
        options.add(new MenuItem("Assign vehicles to an entry in the Agenda", new AssignVehicleToAgendaEntryGUI()));
        options.add(new MenuItem("List all green spaces managed by Me", new ListGreenSpacesManagedByMeGUI()));


        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n---------------- GSM MENU -----------------",true);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
