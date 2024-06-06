package pt.ipp.isep.dei.g312.ui.console.utils;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Task;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public interface EmailService {

    List<String> emailServices = new ArrayList<>();

    public default void sendEmailTeam (Task task) {
        for (Employee e: task.getAssignedTeam().getTeamEmployees()) {
            if (validEmail(e.getEmail())) {
                System.out.println("Email sent to: " + e.getEmail());
            }
        }
    }
    private List<String> loadValidEmailServices() {

        String filePath = "src/main/resources/config.properties";
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);

            String validEmail = properties.getProperty("ValidEmail");
            if (validEmail != null && !validEmail.isEmpty()) {
                String[] services = validEmail.split(",");
                for (String service : services) {
                    emailServices.add(service.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emailServices;
    }

    private boolean validEmail(String email){

        if(emailServices.contains(email.split("@")[1])){
            return true;
        }

        return false;
    }

}
