package pt.ipp.isep.dei.g312.repository;


import pt.ipp.isep.dei.g312.domain.Job;
import pt.ipp.isep.dei.g312.domain.Skill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The JobRepository class manages a collection of jobs.
 */
public class JobRepository {

    public final List<Job> jobsList = new ArrayList<>();


    /**
     * Retrieves a job matching the String provided.
     *
     * @param jobTitle The job name to retrieve.
     * @return An Optional containing the job if found, otherwise empty.
     */
    public Optional<Job> getJobTitle(String jobTitle) {

        for (Job job : jobsList) {
            if (job.getTitle().equalsIgnoreCase(jobTitle)) {
                return Optional.of(job);
            }
        }
        return Optional.empty();
    }



    /**
     * Adds a job to the repository and sorts the jobs list.
     *
     * @param job The job to add.
     * @return true if the job was successfully added, false otherwise.
     */
    public boolean addJobRep(Job job){

        boolean operationSuccess = false;

        if (validateJob(job)) {
            operationSuccess = jobsList.add(job.clone());
            Collections.sort(jobsList);
        }

        if (!operationSuccess) {
            return false;
        }


        return true;
    }



    /**
     * Validates a job before adding it to the repository.
     *
     * @param job The job to validate.
     * @return true if the job is valid and can be added, false otherwise.
     */
    public boolean validateJob(Job job) {
        for (Job jb: jobsList){
            if(jb.getJobName().equals(job.getJobName())){
                System.out.print("\nAlready existing job");
                return false;
            }
        }
        return true;
    }


    /**
     * Prints all jobs in the repository.
     */
    public void printAllJobs(){

        for(Job jb:jobsList){
            if(jb!=null){
                System.out.printf("%12s - Description: %s\n",jb,jb.getJobDescription());
            }
        }
        System.out.println("------------------------------------------------");
    }

    /**
     * Retrieves a clone of the list of jobs.
     *
     * @return A cloned list of jobs.
     */
    public List<Job> getJobsList() {
        return new ArrayList<>(jobsList);
    }
}