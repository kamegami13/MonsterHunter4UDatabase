package com.daviancorp.android.data.classes;

public class Talisman extends Equipment {

    private static SkillTree noSkill = new SkillTree();

    private SkillTree[] skillTrees;

    private int[] skillPoints;

    private Talisman(SkillTree skill1, int skill1Points, boolean hasSkill2) {
        skillTrees = new SkillTree[2];
        skillPoints = new int[2];

        skillTrees[0] = skill1;
        skillPoints[0] = skill1Points;

        if (!hasSkill2) {
            skillTrees[1] = noSkill;
            skillPoints[1] = 0;
        }
    }

    /**
     * The constructor to be used when created a talisman with one skill.
     */
    public Talisman(SkillTree skill1, int skill1Points) {
        this(skill1, skill1Points, false);
    }

    /**
     * The constructor to be used when creating a talisman with two skills.
     */
    public Talisman(SkillTree skill1, int skill1Points, SkillTree skill2, int skill2Points) {
        this(skill1, skill1Points, true);

        skillTrees[1] = skill2;
        skillPoints[1] = skill2Points;
    }

    /** @return True if the talisman has a second skill defined, false if not. */
    public boolean hasTwoSkills() {
        return skillTrees[1] != noSkill;
    }
}
