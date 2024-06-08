package pt.ipp.isep.dei.g312.ui.console.utils;

import pt.ipp.isep.dei.g312.domain.Task;

/**
 * Interface for email services related to tasks.
 */
public interface EmailService {

    /**
     * Sends an email to notify the assignment of a task to a team.
     * @param task The task that has been assigned to a team.
     * @return true if the email was sent successfully, false otherwise.
     */
    boolean assignTaskToTeamEmail(Task task);
}
