package pt.ipp.isep.dei.g312.ui.console.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Utils {

    static public String readLineFromConsole(String prompt) {
        try {
            System.out.print("\n" + prompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static public int readIntegerFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                int value = Integer.parseInt(input);

                return value;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public double readDoubleFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                double value = Double.parseDouble(input);

                return value;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public Date readDateFromConsole(String prompt) {
        do {
            try {
                String strDate = readLineFromConsole(prompt);

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                Date date = df.parse(strDate);

                return date;
            } catch (ParseException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, "Incorrect date Format", ex);
            }
        } while (true);
    }

    static public boolean confirm(String message) {
        String input;
        do {
            input = Utils.readLineFromConsole("\n" + message + "\n");
        } while (!input.equalsIgnoreCase("s") && !input.equalsIgnoreCase("n"));

        return input.equalsIgnoreCase("s");
    }

    static public Object showAndSelectOne(List list, String header,boolean showLastOption) {
        showList(list, header, showLastOption);
        return selectsObject(list);
    }

    static public int showAndSelectIndex(List list, String header,boolean showLastOption) {
        showList(list, header, showLastOption);
        return selectsIndex(list);
    }

    static public int showAndSelectIndexDone(List list, String header) {
        showListDone(list, header);
        return selectsIndex(list);
    }

    static public void showList(List list, String header, boolean showLastOption) {
        System.out.println(header);

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println("  " + index + " - " + o.toString());
        }
        //System.out.println();
        if (showLastOption) {
            System.out.println("  0 - Cancel");
        }
    }

    static public void showOnlyList(List list, String header) {
        System.out.print(header);

        int index = 0;
        for (Object o : list) {
            if (index > 0) {
                System.out.print("; " + o.toString());

            }
            else {
                System.out.print(o.toString());
            }
            index++;
        }
        System.out.println();

    }

    static public void showListDone(List list, String header) {
        System.out.println(header);

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println("  " + index + " - " + o.toString());
        }
        //System.out.println();
        System.out.println("  0 - Done");
    }

    static public Object selectsObject(List list) {
        String input;
        int value;
        do {
            input = Utils.readLineFromConsole("Type your option: ");
            value = Integer.valueOf(input);
        } while (value < 0 || value > list.size());

        if (value == 0) {
            return null;
        } else {
            return list.get(value - 1);
        }
    }

    static public int selectsIndex(List list) {
        String input;
        int value;
        do {

            input = Utils.readLineFromConsole("Type your option: ");
            try {
                value = Integer.valueOf(input);
            } catch (NumberFormatException ex) {
                value = -1;
            }
            if (value > list.size()){
                raiseInvalidInput();
            }
        } while (value < 0 || value > list.size());

        return value - 1;
    }

    static public void raiseInvalidInput() {
        System.out.println("Invalid Input!");
    }

    static public void raiseAlertMessage(String message){
        System.out.printf("\nAlert: %s !\n",message);
    }
}