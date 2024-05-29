package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.*;

import java.text.SimpleDateFormat;
import java.util.*;


// Agenda is a repository of tasks, not a conceptual class.
public class AgendaRepository {

    public List<Task> agenda = new ArrayList<>();
    public List<Agenda> agendaList = new ArrayList<>();





    // TODO - Further implement this method to validate team availability

    // method to assign a team to a task in the agenda
    public boolean assignTeamToTask(Team team, Task task) {
        task.assignTeam(team);
        return updateTask(task);
    }

    //method to update task in the agenda
    public boolean updateTask(Task task){
        int index = 0;
        for(Task t : agenda){
            if(t.getTaskID() == task.getTaskID()){
                agenda.set(index, task);
                return true;
            }
            index++;
        }
        return false;
    }

    // method to test team availability, checks start and end date and also the period of the task, morning or afternoon
    public boolean teamAvailability(List<Task> teamTasks, Task task) {
        Date startDate = task.getDate();
        int taskDuration = task.getDuration();
        TaskPeriod startingTaskPeriod = task.getTaskStartPeriod();
        Date endDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        if (teamTasks.isEmpty()) {
            return true;
        }

        endDate = findEndDate(startDate, startingTaskPeriod, taskDuration); // find the end date of the task
        TaskPeriod endPeriod = findEndPeriod(startDate, startingTaskPeriod, taskDuration); // find the end period of the task (morning or afternoon)

        for (Task teamTask : teamTasks) {
            Date teamTaskStartDate = teamTask.getDate();
            TaskPeriod teamTaskStartingTaskPeriod = teamTask.getTaskStartPeriod();
            int teamTaskDuration = teamTask.getDuration();
            TaskPeriod teamTaskEndPeriod = findEndPeriod(teamTaskStartDate, teamTaskStartingTaskPeriod, teamTaskDuration);

            Date teamTaskEndDate = findEndDate(teamTaskStartDate, teamTaskStartingTaskPeriod, teamTaskDuration);

            if (startDate.after(teamTaskEndDate) || endDate.before(teamTaskStartDate)) {
                return true;
            }
            if (startDate.equals(teamTaskEndDate) && startingTaskPeriod.equals(TaskPeriod.AFTERNOON) && teamTaskEndPeriod.equals(TaskPeriod.MORNING)) {
                return true;
            }
            if (endDate.equals(teamTaskStartDate) && endPeriod.equals(TaskPeriod.MORNING) && teamTaskStartingTaskPeriod.equals(TaskPeriod.AFTERNOON)) {
                return true;
            }

        }
        return false;
    }

    private Date findEndDate(Date startDate, TaskPeriod startingTaskPeriod, int taskDuration) {
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        if (taskDuration == 1) { // if it occupies only half a day (morning or afternoon)
            endDate = startDate;
        }
        if (taskDuration == 2) { // if it occupies a whole day ("2 half days")
            if (startingTaskPeriod.equals(TaskPeriod.MORNING)) {
                endDate = startDate;
            } else {
                calendar.add(Calendar.DAY_OF_YEAR, 1); // afternoon
                endDate = calendar.getTime();
            }
        }

        if (taskDuration >= 3) {
            if (startingTaskPeriod.equals(TaskPeriod.MORNING)) {
                if (taskDuration % 2 == 0) {
                    calendar.add(Calendar.DAY_OF_YEAR, ((taskDuration / 2) - 1)); // if starts in the morning - if pair number of task duration (number of half days), it takes 3 days, counting with the starting day (adds only 2 days to the calendar if takes for example 6 days)
                    endDate = calendar.getTime();
                } else {
                    calendar.add(Calendar.DAY_OF_YEAR, ((taskDuration - 1) / 2)); // if odd number takes 3.5 days = 4 days
                    endDate = calendar.getTime();
                }

            } else { //afternoon

                if (taskDuration % 2 == 0) {
                    calendar.add(Calendar.DAY_OF_YEAR, ((taskDuration / 2))); // if pair number takes 3 days but counting as the starting day (adds only 2 days to the calendar if takes for example 6 days)
                    endDate = calendar.getTime();
                } else {
                    calendar.add(Calendar.DAY_OF_YEAR, ((taskDuration - 1) / 2)); // if odd number takes 3.5 days = 4 days
                    endDate = calendar.getTime();
                }
            }
        }
        return endDate;
    }

    private TaskPeriod findEndPeriod(Date startDate, TaskPeriod startingTaskPeriod, int taskDuration) {
        TaskPeriod endPeriod = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);


        if (taskDuration == 1) { // if it occupies only half a day (morning or afternoon)
            endPeriod = startingTaskPeriod;
        }
        if (taskDuration == 2) { // if it occupies a whole day ("2 half days")
            if (startingTaskPeriod.equals(TaskPeriod.MORNING)) {
                endPeriod = TaskPeriod.AFTERNOON;
            } else {
                endPeriod = TaskPeriod.MORNING;
            }
        }

        if (taskDuration >= 3) {
            if (startingTaskPeriod.equals(TaskPeriod.MORNING)) {
                if (taskDuration % 2 == 0) {
                    endPeriod = TaskPeriod.AFTERNOON;
                } else {
                    endPeriod = TaskPeriod.MORNING;
                }

            } else { //afternoon

                if (taskDuration % 2 == 0) {
                    endPeriod = TaskPeriod.MORNING;
                } else {
                    endPeriod = TaskPeriod.AFTERNOON;
                }
            }
        }
        return endPeriod;
    }

    // method to get all tasks assigned to a team (ignoring tasks with status canceled or done)
    public List<Task> getAllTeamTasks(Team team) {
        List<Task> teamTasks = new ArrayList<>();
        for (Task task : agenda) {
            if (task.getAssignedTeam().equals(team)) {
                if (!task.getStatus().equals(TaskStatus.Canceled) || !task.getStatus().equals(TaskStatus.Done)) {
                    teamTasks.add(task);

                }
            }
        }
        return teamTasks;
    }

    public List<Task> getAgenda(){
        return this.agenda;
    }



    public List<Task> getPlannedTasks() {
        List<Task> plannedTasks = new ArrayList<>();
        for (Task task : agenda) {
            if (task.getStatus() == TaskStatus.Planned) {
                plannedTasks.add(task);
            }
        }
        return plannedTasks;
    }



}