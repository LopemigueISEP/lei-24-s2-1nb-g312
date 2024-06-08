    package pt.ipp.isep.dei.g312.application.controller;

    import pt.ipp.isep.dei.g312.domain.Employee;
    import pt.ipp.isep.dei.g312.domain.GreenSpace;
    import pt.ipp.isep.dei.g312.domain.Task;
    import pt.ipp.isep.dei.g312.domain.Team;
    import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
    import pt.ipp.isep.dei.g312.repository.Repositories;
    import pt.ipp.isep.dei.g312.repository.TaskRepository;

    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.LocalTime;
    import java.time.ZoneId;
    import java.util.Date;
    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    /**
     * Controller class responsible for managing the postponement of tasks in the agenda.
     */
    public class PostponeTaskInTheAgendaController {

        private final TaskRepository taskRepository;
        private final GreenSpaceRepository greenSpaceRepository;

        /**
         * Constructor for PostponeTaskInTheAgendaController.
         * Initializes the repositories.
         */
        public PostponeTaskInTheAgendaController() {
            this.taskRepository = getTaskRepository();
            this.greenSpaceRepository = getGreenSpaceRepository();
        }

        /**
         * Gets the task repository instance.
         * @return the task repository instance
         */
        private TaskRepository getTaskRepository() { return Repositories.getInstance().getTaskRepository(); }

        /**
         * Gets the green space repository instance.
         * @return the green space repository instance
         */
        private GreenSpaceRepository getGreenSpaceRepository() { return Repositories.getInstance().getGreenSpaceRepository(); }

        /**
         * Lists the tasks that are planned.
         * @return a list of planned tasks
         */
        public List<Task> listPlannedTasks() { return taskRepository.getPlannedTasks(); }

        /**
         * Gets the tasks assigned to a specific team.
         * @param team the team to get tasks for
         * @return a list of tasks assigned to the specified team
         */
        public List<Task> getTeamTasks(Team team) { return taskRepository.getAllTeamTasks(team); }

        /**
         * Postpones a task to a new date and time.
         * @param task the task to postpone
         * @param newDate the new date to postpone the task to
         * @param newTime the new hour to postpone the task to
         * @return an Optional containing the postponed task if successful, or an empty Optional if not
         */
        public Optional<Task> postponeTask(Task task, LocalDate newDate, LocalTime newTime) {
            LocalDateTime newStartDateTime = LocalDateTime.of(newDate, newTime);
            Date startDate = Date.from(newStartDateTime.atZone(ZoneId.systemDefault()).toInstant());
            return Employee.postponedTask(task, startDate);
        }

        /**
         * Gets the list of all green spaces.
         * @return a list of green spaces
         */
        public List<GreenSpace> getGreenSpaces() {
            return greenSpaceRepository.getGreenSpaceList();

        }

        /**
         * Gets the tasks assigned to a specific green space.
         * @param greenSpace the green space to get tasks for
         * @return a list of tasks for the specified green space
         */
        public List<Task> getTasksByGreenSpace(GreenSpace greenSpace) {
            return taskRepository.getPlannedTasks().stream()
                    .filter(task -> task.getGreenSpace().equals(greenSpace))
                    .collect(Collectors.toList());
        }

    }
