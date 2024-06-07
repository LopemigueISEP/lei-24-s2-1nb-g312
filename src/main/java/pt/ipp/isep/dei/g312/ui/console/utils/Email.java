package pt.ipp.isep.dei.g312.ui.console.utils;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Task;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Email implements EmailService {

    private List<String> emailServices = new ArrayList<>();

    @Override
    public boolean assignTaskToTeamEmail(Task task) {
        return sendEmailTeam(task);
    }

    private boolean sendEmailTeam(Task task) {
        loadValidEmailServices();

        boolean emailSent = false;

        for (Employee e : task.getAssignedTeam().getTeamEmployees()) {
            if (validEmail(e.getEmail())) {
                createEmailFile(e, task);
                emailSent = true;
            }
        }

        return emailSent;
    }

    private void loadValidEmailServices() {
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
    }

    private boolean validEmail(String email) {
        String domain = email.split("@")[1];
        return emailServices.contains(domain);
    }

    private void createEmailFile(Employee employee, Task task) {
        String fileName = "email_" + employee.getName() + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("To: " + employee.getEmail() + "\n");
            writer.write("Subject: Task Assignment\n\n");
            writer.write("Dear " + employee.getName() + ",\n\n");
            writer.write("You have been assigned a new task.\n\n");
            writer.write("Task Details:\n");
            writer.write("Title: " + task.getTitle() + "\n");
            writer.write("Description: " + task.getDescription() + "\n");
            writer.write("Greenspace: " + task.getGreenSpace() + "\n");
            writer.write("Assigned Team: " + task.getAssignedTeam() + "\n");
            writer.write("Start Date: " + task.getStartDate() + "\n");
            writer.write("End Date: " + task.getEndDate() + "\n");
            writer.write("\nBest regards,\nMusgoSublime\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
