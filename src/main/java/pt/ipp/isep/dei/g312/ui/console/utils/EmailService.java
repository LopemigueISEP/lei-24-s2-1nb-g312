package pt.ipp.isep.dei.g312.ui.console.utils;

import pt.ipp.isep.dei.g312.domain.Task;

public interface EmailService {
    boolean assignTaskToTeamEmail(Task task);
}
