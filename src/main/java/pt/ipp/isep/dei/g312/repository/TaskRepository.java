package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.*;

import java.io.*;
import java.time.*;
import java.util.*;


/**
 * Repository class for managing tasks.
 */
public class TaskRepository implements Serializable {
    public List<Task> taskList = new ArrayList<>(); //isto passa a ser uma lista de tasks, passa a chamar-se taskList

    /**
     * Adds a task to the repository.
     *
     * @param task the task to add
     * @return an Optional containing the added task if it was successfully added, or empty otherwise
     */
    public Optional<Task> addTask(Task task) {
        Optional<Task> newTask = Optional.empty();
        boolean operationSuccess = false;

        if (isValidTask(task)) {
            newTask = Optional.of(task.clone());
            operationSuccess = taskList.add(newTask.get());
        }
        if (!operationSuccess) {
            newTask = Optional.empty();
        }
        return newTask;

    }


    /**
     * Retrieves the list of tasks from the repository.
     *
     * @return an Optional containing the list of tasks, or empty if the list is null
     */
    public Optional<List<Task>> getTaskList() {
        Optional<List<Task>> tasksList = Optional.empty();
        List<Task> listTask = new ArrayList<>(taskList);
        tasksList = Optional.of(listTask);
        return tasksList;
    }


    /**
     * Retrieves the list of tasks in the agenda.
     *
     * @return the list of tasks in the agenda
     */
    public List<Task> getAgenda() {
        ArrayList<Task> agenda = new ArrayList<>();
        for (Task t : taskList) {
            if (t.getTaskPosition().equals(TaskPosition.AGENDA)) {
                agenda.add(t);
            }
        }
        return agenda;
    }


    /**
     * Retrieves the list of tasks in the TO DO list.
     *
     * @return the list of tasks in the TO DO list
     */
    public List<Task> getToDoList() {
        ArrayList<Task> toDoList = new ArrayList<>();
        for (Task t : taskList) {
            if (t.getTaskPosition() == TaskPosition.TODOLIST) {
                toDoList.add(t);
            }
        }
        return toDoList;
    }


    /**
     * Updates a task in the agenda.
     *
     * @param task the task to update
     * @return an Optional containing the updated task if it was successfully updated, or empty otherwise
     */
    public Optional<Task> updateTask(Task task) {
        int index = 0;
        for (Task t : taskList) {
            if (t.getTaskID() == task.getTaskID()) {
                taskList.set(index, task);
                return Optional.of(task);
            }
            index++;
        }
        return Optional.empty();
    }


    /**
     * Checks the availability of a team for a given task.
     *
     * @param teamTasks the list of tasks assigned to the team
     * @param task      the task to check availability for
     * @return true if the team is available for the task, false otherwise
     */
    public boolean teamAvailability(List<Task> teamTasks, Task task) {
        if (teamTasks.isEmpty()) {
            return true;
        }

        for (Task t : teamTasks) {
            if (task.taskOverlap(t)) {
                return false;

            }
        }
        return true;

    }


    /**
     * Retrieves all tasks assigned to a team, excluding those with status CANCELED or DONE.
     *
     * @param team the team for which to retrieve tasks
     * @return the list of tasks assigned to the team
     */
    public List<Task> getAllTeamTasks(Team team) {
        List<Task> teamTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            Team assignedTeam = task.getAssignedTeam();
            if (assignedTeam != null && assignedTeam.equals(team)) {
                if (!task.getStatus().equals(TaskStatus.CANCELED) && !task.getStatus().equals(TaskStatus.DONE)) {
                    teamTasks.add(task);
                }
            }
        }
        return teamTasks;
    }


    /**
     * Retrieves a list of all agenda tasks except those that are marked as Done or Canceled.
     *
     * @return A list of agenda tasks that are not marked as Done or Canceled.
     */
    public List<Task> getAllAgendaTasksExceptDoneCanceled() {
        List<Task> tasksExcept = new ArrayList<>();
        for (Task task : getAgenda()) {
            if (task.getStatus() != TaskStatus.DONE && task.getStatus() != TaskStatus.CANCELED) {
                tasksExcept.add(task);
            }
        }
        return tasksExcept;
    }


    // method to get a list of planned status tasks

    /**
     * Retrieves a list of tasks with status PLANNED.
     *
     * @return a list of planned tasks
     */
    public List<Task> getPlannedTasks() {
        List<Task> plannedTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            if (task.getStatus() == TaskStatus.PLANNED) {
                plannedTasks.add(task);
            }
        }
        return plannedTasks;
    }

    /**
     * Retrieves a list of tasks with status PLANNED or POSTPONED.
     *
     * @return a list of planned and postponed tasks
     */
    public List<Task> getPlannedAndPostponedTasks() {
        List<Task> plannedTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            if (task.getStatus() == TaskStatus.PLANNED || task.getStatus() == TaskStatus.POSTPONED) {
                plannedTasks.add(task);
            }
        }
        return plannedTasks;
    }

    // method to get a list of pending status tasks

    /**
     * Retrieves a list of tasks with status PENDING.
     *
     * @return a list of pending tasks
     */
    public List<Task> getPendingTasks() {
        List<Task> pendingTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            if (task.getStatus() == TaskStatus.PENDING) {
                pendingTasks.add(task);
            }
        }
        return pendingTasks;
    }

    /**
     * Retrieves a list of tasks associated with a specific green space.
     *
     * @param greenSpace the GreenSpace for which to retrieve tasks
     * @return a List of Task objects associated with the specified green space
     */
    public List<Task> getTasksByGreenSpace(GreenSpace greenSpace) {
        List<Task> tasksOfGreenSpace = new ArrayList<>();
        for (Task t :
                taskList) {
            if (t.getGreenSpace().equals(greenSpace)) {
                tasksOfGreenSpace.add(t);
            }
        }
        return tasksOfGreenSpace;
    }

    /**
     * Checks if a task is valid, ensuring it is not already present in the list of tasks.
     *
     * @param task the task to validate
     * @return true if the task is valid (i.e., not already present in the list),
     * false otherwise
     */
    private boolean isValidTask(Task task) {

        for (Task t :
                taskList) {
            if (t.getTaskID() == task.getTaskID()) {
                return false;
            }
        }

        return true;
    }


    /**
     * Adds a new task to the repository.
     *
     * @param task the task to add
     * @return an Optional containing the added task if it was successfully added, or empty otherwise
     */
    public Optional<Task> add(Task task) {
        Optional<Task> newTask = Optional.empty();
        boolean operationSuccess = false;

        if (isValidTask(task)) {
            newTask = Optional.of(task.clone());
            operationSuccess = taskList.add(newTask.get());
        }
        if (!operationSuccess) {
            newTask = Optional.empty();
        }
        return newTask;
    }


    /**
     * Retrieves the list of available vehicles for a given task.
     *
     * @param taskSelecionada The task for which vehicle availability is checked.
     * @param vehicles        The list of vehicles to be considered for availability.
     * @return A list of vehicles that are available for the given task.
     */
    public List<Vehicle> getVehicleAvaiability(Task taskSelecionada, List<Vehicle> vehicles) {
        Boolean checkAvailable;
        List<Vehicle> listaVeiculosDisponiveis = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            if (vehicle != null) {
                checkAvailable = true;
                for (Task task : getAllAgendaTasksExceptDoneCanceled()) {
                    if (task != null) {
                        for (Vehicle taskVehicle : task.getAssignedVehicles()) {
                            if (taskVehicle != null && taskVehicle.getRegistrationPlate().equals(vehicle.getRegistrationPlate())) {
                                if (!(taskSelecionada.getStartDate().compareTo(task.getEndDate()) >= 0 || taskSelecionada.getEndDate().compareTo(task.getStartDate()) <= 0)) {
                                    checkAvailable = false;
                                    break;
                                }
                            }

                        }

                    }
                    if (!checkAvailable) {
                        break;
                    }

                }
                if (checkAvailable) {
                    listaVeiculosDisponiveis.add(vehicle);

                }
            }


        }
        return listaVeiculosDisponiveis;
    }

    /**
     * Retrieves a list of tasks that can be canceled.
     *
     * @return a list of cancelable tasks
     */
    public List<Task> getTasksCancelable() {
        List<Task> cancelableTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            if (task.getStatus() != TaskStatus.CANCELED) {
                cancelableTasks.add(task);
            }
        }
        return cancelableTasks;
    }

    /**
     * Retrieves a list of tasks that can be completed.
     *
     * @return a list of completable tasks
     */
    public List<Task> getTasksCompletable(Employee colab) {
        List<Task> completableTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            if (task.getStatus() != TaskStatus.CANCELED && task.getStatus() != TaskStatus.DONE) {
                if (task.getAssignedTeam() != null) {
                    for (Employee e :
                            task.getAssignedTeam().getTeamEmployees()) {
                        if (e.getEmail().equals(colab.getEmail())) {
                            completableTasks.add(task);
                        }
                    }
                }


            }
        }
        return completableTasks;
    }


    /**
     * Cancels a given task.
     *
     * @param taskToCancel the task to cancel
     */

    public void cancelTask(Task taskToCancel) {
        taskToCancel.cancel();
    }

    /**
     * Completes a given task.
     *
     * @param taskToComplete the task to cancel
     * @param observation    optional observation
     * @param endDate        date of the finished task
     */
    public void completeTask(Task taskToComplete, String observation, Date endDate) {
        taskToComplete.complete(observation, endDate);
    }

    /**
     * Adds the specified task to the agenda with the provided start date and time.
     *
     * @param task      the task to add to the agenda
     * @param startDate the date on which the task is to be scheduled
     * @param startTime the time at which the task is to start
     */
    public void addTaskAgenda(Task task, LocalDate startDate, LocalTime startTime) {
        for (Task t :
                taskList) {
            if (t.getTaskID() == task.getTaskID()) {
                t.addTaskAgenda(startDate, startTime);
            }
        }
    }


    /**
     * Retrieves the next available task ID.
     *
     * @return the next available task ID
     */
    public int getNextTaskId() {
        int maxId = 0;
        for (Task task :
                taskList) {
            if (maxId < task.getTaskID()) {
                maxId = task.getTaskID();
            }

        }
        return maxId + 1;
    }


    /**
     * Retrieves a list of tasks associated with a specific green space.
     *
     * @param greenSpace the GreenSpace for which to retrieve tasks
     * @return a list of tasks associated with the specified green space
     */
    public List<Task> getTasksByGreenSpaceForTeam(GreenSpace greenSpace) {
        List<Task> tasksOfGreenSpace = new ArrayList<>();
        for (Task t :
                taskList) {
            if (t.getStatus() != null) {
                if (t.getGreenSpace().equals(greenSpace) && (t.getStatus().equals(TaskStatus.PENDING) || t.getStatus().equals(TaskStatus.POSTPONED))) {
                    tasksOfGreenSpace.add(t);
                }
            }
        }
        return tasksOfGreenSpace;
    }


    /**
     * Serializes the TaskRepository object to a file.
     */
    public void serializateData() {

        String filename = this.getClass().getSimpleName() + ".bin";

        // Serialization
        try {

            // Saving of object in a file
            FileOutputStream file = new FileOutputStream
                    (filename);
            ObjectOutputStream out = new ObjectOutputStream
                    (file);

            // Method for serialization of object
            out.writeObject(this);


            out.close();
            file.close();

            System.out.println(this.getClass().getSimpleName() + " Has Been Serialized successfully! ");
        } catch (FileNotFoundException ex) {
            System.out.println("IOException is caught");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes the TaskRepository object from a file and adds the skills to the current repository.
     */
    public void getSeralizatedData() {
        String filename = this.getClass().getSimpleName() + ".bin";

        try {

            // Reading the object from a file
            FileInputStream file = new FileInputStream
                    (filename);
            ObjectInputStream in = new ObjectInputStream
                    (file);

            // Method for deserialization of object
            TaskRepository taskRepo = (TaskRepository) in.readObject();

            for (Task t :
                    taskRepo.getTaskList().get()) {
                this.addTask(t);
            }

            in.close();
            file.close();

        } catch (IOException ex) {
            System.out.printf("\n%s not found!", filename);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" +
                    " is caught");
        }
    }


    /**
     * Retrieves the tasks assigned to the specified user within a given date range.
     *
     * @param userEmail the email of the user whose tasks are to be retrieved
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @return a list of tasks assigned to the specified user within the given date range
     */
    public List<Task> getTasksAssignedToMeBetweenToDates(String userEmail, LocalDate startDate, LocalDate endDate) {

        // Converte LocalDate para ZonedDateTime usando o fuso horário do sistema
        ZonedDateTime zonedStartDate = startDate.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime zonedEndDate = endDate.atStartOfDay(ZoneId.systemDefault());

        // Converte ZonedDateTime para Date
        Date startDateDate = Date.from(zonedStartDate.toInstant());
        Date endDateDate = Date.from(zonedEndDate.toInstant());

        List<Task> employeeTasksBetweenDates = new ArrayList<>();
        List<Employee> taskEmployees = new ArrayList<>();

        for (Task task : getAgenda()) {
            try {
                if (task != null) {
                    if ((startDateDate.before(task.getEndDate()) || startDateDate.equals(task.getEndDate())) && (endDateDate.after(task.getStartDate()) || endDateDate.equals(task.getStartDate()))) {
                        if (task.getAssignedTeam() != null && task.getAssignedTeam().getTeamEmployees() != null) {
                            taskEmployees = task.getAssignedTeam().getTeamEmployees();
                            for (Employee emp : taskEmployees) {
                                if (emp != null && emp.getEmail().equals(userEmail)) {
                                    employeeTasksBetweenDates.add(task);
                                }
                            }
                        }
                    }
                }
            } catch (NullPointerException e) {
                System.err.println("Error: Null value encountered - " + e.getMessage());
                throw new RuntimeException("Null value encountered during task processing", e);
            } catch (Exception e) {
                System.err.println("An unexpected error occurred - " + e.getMessage());
                throw new RuntimeException("An unexpected error occurred during task processing", e);
            }
        }

        return employeeTasksBetweenDates;
    }


}