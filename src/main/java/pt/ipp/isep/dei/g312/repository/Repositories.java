package pt.ipp.isep.dei.g312.repository;


/**
 * Represents a collection of repositories and provides access to them.
 */
public class Repositories {

    private static Repositories instance;
    private final OrganizationRepository organizationRepository;
    //    private final TaskCategoryRepository taskCategoryRepository;
    private final AuthenticationRepository authenticationRepository;
    private final VehicleRepository vehicleRepository;
    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;
    private final TeamRepository teamRepository;

    private final JobRepository jobRepository;
    private final CSVFileRepository csvFileRepository;
    private final TaskRepository taskRepository;
    private final GreenSpaceRepository greenSpaceRepository;




    /**
     * Constructs a Repositories object and initializes all repository instances.
     */
    private Repositories() {
        organizationRepository = new OrganizationRepository();
//        taskCategoryRepository = new TaskCategoryRepository();
        authenticationRepository = new AuthenticationRepository();
        vehicleRepository = new VehicleRepository();
        employeeRepository = new EmployeeRepository();
        jobRepository = new JobRepository();
        skillRepository = new SkillRepository();
        teamRepository = new TeamRepository();
        csvFileRepository = new CSVFileRepository();
        taskRepository = new TaskRepository();
        greenSpaceRepository = new GreenSpaceRepository();
    }

    /**
     * Retrieves the singleton instance of Repositories.
     *
     * @return The Repositories instance.
     */
    public static Repositories getInstance() {
        if (instance == null) {
            synchronized (Repositories.class) {
                instance = new Repositories();
            }
        }
        return instance;
    }

    /**
     * Gets the organization repository instance.
     *
     * @return The OrganizationRepository instance.
     */
    public OrganizationRepository getOrganizationRepository() {
        return organizationRepository;
    }

//    public TaskCategoryRepository getTaskCategoryRepository() {
//        return taskCategoryRepository;
//    }

    /**
     * Gets the authentication repository instance.
     *
     * @return The AuthenticationRepository instance.
     */
    public AuthenticationRepository getAuthenticationRepository() {
        return authenticationRepository;
    }

    /**
     * Gets the vehicle repository instance.
     *
     * @return The VehicleRepository instance.
     */
    public VehicleRepository getVehicleRepository() {
        return vehicleRepository;
    }

    /**
     * Gets the employee repository instance.
     *
     * @return The EmployeeRepository instance.
     */
    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    /**
     * Gets the job repository instance.
     *
     * @return The JobRepository instance.
     */
    public JobRepository getJobRepository() {
        return jobRepository;
    }

    /**
     * Gets the skill repository instance.
     *
     * @return The SkillRepository instance.
     */
    public SkillRepository getSkillRepository() {
        return skillRepository;
    }

    /**
     * Gets the team repository instance.
     *
     * @return The TeamRepository instance.
     */
    public TeamRepository getTeamRepository() {
        return teamRepository;
    }

    /**
     * Gets the CSV file repository instance.
     *
     * @return The CSVFileRepository instance.
     */
    public CSVFileRepository getcsvFileRepository() {
        return csvFileRepository;
    }
    /**
     * Retrieves the TaskRepository object associated with this controller.
     *
     * @return The TaskRepository object.
     */
    public TaskRepository getTaskRepository() {return taskRepository;
    }
    /**
     * Retrieves the GreenSpaceRepository object associated with this controller.
     *
     * @return The GreenSpaceRepository object.
     */
    public GreenSpaceRepository getGreenSpaceRepository() {
        return greenSpaceRepository;
    }

}
