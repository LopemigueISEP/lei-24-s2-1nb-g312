package pt.ipp.isep.dei.g312.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a skill that has a skill name and description.
 * Provides methods to access and modify the skill attributes, and implements Comparable to enable sorting by skill name.
 */
public class Skill implements Comparable<Skill>{

    private String skillName;
    private String skillDescription;



    /**
     * Constructs a Skill object with the specified name and description.
     *
     * @param skillName        The name of the skill.
     * @param skillDescription The description of the skill.
     */
    public Skill(String skillName, String skillDescription){
        this.skillName = skillName;
        this.skillDescription = skillDescription;
    }

    /**
     * Gets the name of the skill.
     *
     * @return The skill name.
     */
    public String getSkillName() {
        return skillName;
    }

    /**
     * Sets the name of the skill.
     *
     * @param skillName The new name for the skill.
     */
    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    /**
     * Gets the description of the skill.
     *
     * @return The skill description.
     */
    public String getSkillDescription() {
        return skillDescription;
    }

    /**
     * Sets the description of the skill.
     *
     * @param skillDescription The new description for the skill.
     */
    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }


    /**
     * Creates a clone of the current skill object.
     *
     * @return A new Skill object that is a copy of the current instance.
     */
    @Override
    public Skill clone(){
        return new Skill(this.skillName,this.skillDescription);
    }


    /**
     * Compares this skill with another skill based on their names.
     *
     * @param o The skill to be compared.
     * @return A negative integer, zero, or a positive integer as this skill is less than, equal to, or greater than the specified skill.
     */
    @Override
    public int compareTo(Skill o) {
        return this.skillName.compareTo(o.getSkillName());
    }
}
