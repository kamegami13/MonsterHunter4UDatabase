package com.daviancorp.android.data.classes;

import android.util.Log;

public class Talisman extends Equipment {

    private SkillTree[] skillTrees;

    private int[] skillPoints;

    private int typeIndex;

    /**
     * Used when creating a generic "dummy" talisman with no skills.
     */
    public Talisman() {
        skillTrees = new SkillTree[2];
        skillPoints = new int[2];
    }

    /**
     * Used when creating a talisman with one skill.
     */
    public Talisman(SkillTree skill1, int skill1Points, int typeIndex) {
        this(skill1, skill1Points, typeIndex, false);
    }

    /**
     * Used when creating a talisman with two skills.
     */
    public Talisman(SkillTree skill1, int skill1Points, SkillTree skill2, int skill2Points, int typeIndex) {
        this(skill1, skill1Points, typeIndex, false);

        skillTrees[1] = skill2;
        skillPoints[1] = skill2Points;
    }

    private Talisman(SkillTree skill1, int skill1Points, int typeIndex, boolean hasSkill2) {
        this();

        skillTrees[0] = skill1;
        skillPoints[0] = skill1Points;

        this.typeIndex = typeIndex;

        if (!hasSkill2) {
            skillTrees[1] = null;
            skillPoints[1] = 0;
        }
    }

    public void setSecondSkill(SkillTree newSkillTree, int newSkillPoints) {
        if (skillTrees[0] != null) {
            skillTrees[1] = newSkillTree;
            skillPoints[1] = newSkillPoints;
        }
        else {
            Log.e("SetBuilder", "Attempt to set the second skill on a talisman that doesn't yet have the first skill.");
        }
    }

    public SkillTree getSkill1() {
        return skillTrees[0];
    }

    public SkillTree getSkill2() {
        return skillTrees[1];
    }

    public int getSkill1Points() {
        return skillPoints[0];
    }

    public int getSkill2Points() {
        return skillPoints[1];
    }

    @Override
    public String getName() {
        return TypeName.values()[typeIndex].getName() + " Talisman";
    }

    /** @return True if the talisman has a second skill defined, false if not. */
    public boolean hasTwoSkills() {
        return skillTrees[1] != null;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    private enum TypeName {
        PAWN("Pawn"),
        BISHOP("Bishop"),
        KNIGHT("Knight"),
        ROOK("Rook"),
        QUEEN("Queen"),
        KING("King"),
        DRAGON("Dragon"),
        UNKNOWABLE("Unknowable"),
        MYSTIC("Mystic"),
        HERO("Hero"),
        LEGEND("Legend"),
        CREATOR("Creator"),
        SAGE("Sage"),
        MIRACLE("Miracle");

        private String name;

        private TypeName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
