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


    public class PostponeTaskInTheAgendaController {
    //TODO acrescentar filtro para os greenSpaces antes de escolher task na combobox
        private final TaskRepository taskRepository;
        private final GreenSpaceRepository greenSpaceRepository;

        public PostponeTaskInTheAgendaController() {
            this.taskRepository = getTaskRepository();
            this.greenSpaceRepository = getGreenSpaceRepository();
        }

        private TaskRepository getTaskRepository() { return Repositories.getInstance().getTaskRepository(); }
        private GreenSpaceRepository getGreenSpaceRepository() { return Repositories.getInstance().getGreenSpaceRepository(); }

        public List<Task> listPlannedTasks() { return taskRepository.getPlannedTasks(); }

        public List<Task> getTeamTasks(Team team) { return taskRepository.getAllTeamTasks(team); }

        /**public Optional<Task> postponeTask(Task task, Date newDate) {
            return Employee.postponedTask(task, newDate);
        } */

        public Optional<Task> postponeTask(Task task, LocalDate newDate, LocalTime newTime) {
            LocalDateTime newStartDateTime = LocalDateTime.of(newDate, newTime);
            Date startDate = Date.from(newStartDateTime.atZone(ZoneId.systemDefault()).toInstant());
            return Employee.postponedTask(task, startDate);
        }

        public List<GreenSpace> getGreenSpaces() {
            return greenSpaceRepository.getGreenSpaceList();

        }

        public List<Task> getTasksByGreenSpace(GreenSpace greenSpace) {
            return taskRepository.getPlannedTasks().stream()
                    .filter(task -> task.getGreenSpace().equals(greenSpace))
                    .collect(Collectors.toList());
        }

    }
