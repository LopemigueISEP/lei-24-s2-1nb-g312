package pt.ipp.isep.dei.g312.ui.console.menu;

import pt.ipp.isep.dei.g312.ui.console.*;
import pt.ipp.isep.dei.g312.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HRMUI implements Runnable {

    public HRMUI() {
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


        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n---------------- HRM MENU -----------------",true);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
