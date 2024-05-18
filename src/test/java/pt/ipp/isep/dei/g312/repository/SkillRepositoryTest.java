package pt.ipp.isep.dei.g312.repository;

import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.domain.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SkillRepositoryTest {

    @Test
    void addSkillRep() {
        SkillRepository skillRepository = new SkillRepository();
        Skill skl = new Skill("Bananas","Boas");
        boolean result = skillRepository.addSkillRep(skl);
        assertTrue(result);

        boolean result_EmptyList = false;
        if(skillRepository.getSkills().isEmpty()){result_EmptyList = true;}
        assertFalse(result_EmptyList);

        boolean result2 = skillRepository.addSkillRep(skl);
        assertFalse(result2);
    }

    @Test
    void validateSkill() {
        SkillRepository skillRepository = new SkillRepository();
        Skill skl = new Skill("Bananas","Boas");
        boolean result = skillRepository.validateSkill(skl);
        assertTrue(result);

        skillRepository.addSkillRep(skl);
        boolean validateSkillExists = skillRepository.validateSkill(skl);
        assertFalse(validateSkillExists);

    }

    @Test
    void getSkills() {
        SkillRepository skillRepository = new SkillRepository();
        Skill skl = new Skill("Bananas","Boas");
        skillRepository.addSkillRep(skl);
        Optional<List<Skill>> optResult = skillRepository.getSkills();
        List<Skill> result = optResult.get();

        List<Skill> exp_Result = new ArrayList<>();
        exp_Result.add(skl);

        assertEquals(exp_Result.get(0).getSkillName(), result.get(0).getSkillName());

        boolean vazio = false;
        if(skillRepository.getSkills().get().getFirst() ==null){vazio = true;}
        assertFalse(vazio);



    }
}