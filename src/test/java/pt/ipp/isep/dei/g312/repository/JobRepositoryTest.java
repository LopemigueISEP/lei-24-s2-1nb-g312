package pt.ipp.isep.dei.g312.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.domain.Job;
import pt.ipp.isep.dei.g312.domain.Skill;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JobRepositoryTest {

    @Test
    void getJobTitle() {
        JobRepository jobRepository = new JobRepository();
        Job jb = new Job("BATEDOR","Laranjas");
        jobRepository.addJobRep(jb);
        Optional<Job> result = jobRepository.getJobTitle("BATEDOR");

        assertEquals(jb.getJobName(),result.get().getJobName());

        Optional<Job> result2 = jobRepository.getJobTitle("COMEDOR");
        assertTrue(result2.isEmpty());
    }

    @Test
    void addJobRep() {
        JobRepository jobRepository = new JobRepository();
        Job jb = new Job("BATEDOR","Laranjas");
        boolean result = jobRepository.addJobRep(jb);
        assertTrue(result);

        boolean result_EmptyList = false;
        if(jobRepository.jobsList.isEmpty()){result_EmptyList = true;}
        assertFalse(result_EmptyList);

        Job jb1 = new Job("","");
        boolean result1 = jobRepository.addJobRep(jb);
        assertFalse(result1);

    }

    @Test
    void validateJob() {
        JobRepository jobRepository = new JobRepository();
        Job jb = new Job("BATEDOR","Laranjas");

        boolean validateNotExist = jobRepository.validateJob(jb);
        assertTrue(validateNotExist);


        jobRepository.addJobRep(jb);
        boolean validateJobExists = jobRepository.validateJob(jb);
        assertFalse(validateJobExists);

    }

    @Test
    void getJobsList() {
        JobRepository jobRepository = new JobRepository();
        Job jb = new Job("BATEDOR","Laranjas");
        jobRepository.addJobRep(jb);
        final String EXP_RESULT = "BATEDOR";
        String result = jobRepository.jobsList.get(0).getJobName();
        assertEquals(EXP_RESULT,result);

        boolean vazio = false;
        if(jobRepository.jobsList.getFirst()==null){vazio = true;}
        assertFalse(vazio);
    }

    @Test
    void testGetJobsList() {
        JobRepository jobRep = new JobRepository();
        Job jb = new Job("BATEDOR","Laranjas");
        Job jb1 = new Job("COMEDOR", "Pessegos");
        jobRep.addJobRep(jb);
        jobRep.addJobRep(jb1);

        List<Job> listaClone = jobRep.getJobsList();

        assertEquals(jobRep.getJobsList(), listaClone);
    }
}