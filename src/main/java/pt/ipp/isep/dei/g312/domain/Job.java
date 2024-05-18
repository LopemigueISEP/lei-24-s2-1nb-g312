package pt.ipp.isep.dei.g312.domain;

/**
 * Represents a job that has a job name and description.
 * Provides methods to access and modify the job attributes, and implements Comparable to enable sorting by job name.
 */
public class Job implements Comparable<Job>{

    private String jobName;
    private String jobDescription;


    /**
     * Constructs a Job object with the specified name and description.
     *
     * @param jobName        The name of the job.
     * @param jobDescription The description of the job.
     */
    public Job(String jobName, String jobDescription){
        this.jobName = jobName;
        this.jobDescription = jobDescription;

    }

    /**
     * Creates a clone of the current job object.
     *
     * @return A new Job object that is a copy of the current instance.
     */
    @Override
    public Job clone(){
        return new Job(this.jobName, this.jobDescription);
    }


    @Override
    public String toString() {
        return this.jobName;
    }

    /**
     * Gets the name of the job.
     *
     * @return The job name.
     */
    public String getTitle() {
        return jobName;
    }

    /**
     * Gets the name of the job.
     *
     * @return The job name.
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Sets the name of the job.
     *
     * @param jobName The new name for the job.
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * Gets the description of the job.
     *
     * @return The job description.
     */
    public String getJobDescription() {
        return jobDescription;
    }

    /**
     * Sets the description of the job.
     *
     * @param jobDescription The new description for the job.
     */
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    /**
     * Compares this job with another job based on their names.
     *
     * @param o The job to be compared.
     * @return A negative integer, zero, or a positive integer as this job is less than, equal to, or greater than the specified job.
     */
    @Override
    public int compareTo(Job o) {
        return this.jobName.compareTo(o.getJobName());
    }
}
