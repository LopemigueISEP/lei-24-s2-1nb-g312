package pt.ipp.isep.dei.g312.ui.console.menu;


import pt.ipp.isep.dei.g312.ui.console.*;
import pt.ipp.isep.dei.g312.ui.console.utils.Utils;
import pt.ipp.isep.dei.g312.ui.gui.*;

import java.util.ArrayList;
import java.util.List;

public class AdminUI implements Runnable {
    public AdminUI() {
    }


    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register Skill", new RegisterSkillUI()));
        options.add(new MenuItem("Show registered skills", new ShowListOfSkillsUI()));
        options.add(new MenuItem("Register Job", new RegisterJobUI()));
        options.add(new MenuItem("Show registered jobs", new ShowListOfJobsUI()));
        options.add(new MenuItem("Register Collaborator", new RegisterCollaboratorUI()));
        options.add(new MenuItem("Show registered collaborators",new ShowListOfCollaboratorsUI()));
        options.add(new MenuItem("Assign skills to Collaborator", new AssignSkillsCollaboratorUI()));
        options.add(new MenuItem("Show collaborators and his skills",new ShowListOfCollaboratorsWithSkillsUI()));
        options.add(new MenuItem("Generate team", new GenerateTeamUI()));
        options.add(new MenuItem("Show teams", new ShowListOfTeamsUI()));
        options.add(new MenuItem("Register Vehicle", new CreateVehicleUI()));
        options.add(new MenuItem("Register Check-up", new RegisterCheckUpUI()));
        options.add(new MenuItem("List Vehicles due to Check-up", new ListVehiclesDueToCheckUpUI()));
        options.add(new MenuItem("Import CSV for Planning and Building Irrigation System", new ImportCSVUI()));
        options.add(new MenuItem("Planning and Building Irrigation System", new KruskalAlgorithmUI()));
        options.add(new MenuItem("Import Group Files and Run-Time Testing Kruskal Algorithm", new RunTimeTestingKruskalAlgorithmUI()));
        options.add(new MenuItem("Register a Green Space", new RegisterGreenSpaceUI()));
        options.add(new MenuItem("Show list of green Spaces", new ShowListOfGreenSpacesUI()));
        options.add(new MenuItem("Add entry to the To-Do List", new AddEntryToDoListUI()));
        options.add(new MenuItem("Show To-Do List", new ShowToDoListUI()));
        options.add(new MenuItem("Add entry to the Agenda ", new AddEntryToAgendaUI()));
        options.add(new MenuItem("Show Agenda", new ShowListOfAgendaUI()));
        options.add(new MenuItem("Assign Team to an entry in Agenda", new AssignTeamEntryAgendaUI()));
        options.add(new MenuItem("Postpone an entry in the Agenda to a specific future date", new PostponeTaskInTheAgendaUI()));
        options.add(new MenuItem("Cancel an entry in the Agenda", new CancelEntryAgendaUI()));
        options.add(new MenuItem("Assign vehicles to an entry in the Agenda", new AssignVehicleToAgendaEntryGUI()));
        options.add(new MenuItem("List all green spaces managed by Me", new ListGreenSpacesManagedByMeGUI()));
        options.add(new MenuItem("List tasks assigned to me between two dates", new TasksAssignedToMeBetweenToDatesGUI()));
        options.add(new MenuItem("Record completion of a task", new TasksAssignedToMeBetweenToDatesGUI()));
        options.add(new MenuItem("Calculate routes to Assembly Point", new ImportTwoCSVUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n---------------- ADMIN MENU -----------------",true);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}