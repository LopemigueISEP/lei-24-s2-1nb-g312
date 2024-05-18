package pt.ipp.isep.dei.g312.ui.console.menu;

import pt.ipp.isep.dei.g312.ui.console.*;
import pt.ipp.isep.dei.g312.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class VFMUI implements Runnable {
    public VFMUI(){
    }


    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register Vehicle", new CreateVehicleUI()));
        options.add(new MenuItem("Register Check-up", new RegisterCheckUpUI()));
        options.add(new MenuItem("List Vehicles due to CheckUp", new ListVehiclesDueToCheckUpUI()));



        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n---------------- VFM MENU -----------------",true);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
