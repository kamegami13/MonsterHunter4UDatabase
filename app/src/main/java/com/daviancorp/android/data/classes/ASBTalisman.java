package com.daviancorp.android.data.classes;

public class ASBTalisman extends Equipment {

    private SkillTree[] skillTrees;

    private int[] skillPoints;

    private int typeIndex;

    /**
     * Used when creating a generic "dummy" talisman with no skills.
     */
    public ASBTalisman() {
        skillTrees = new SkillTree[2];
        skillPoints = new int[2];
    }

    /**
     * Used when creating a talisman with one skill.
     */
    public ASBTalisman(SkillTree skill1, int skill1Points, int typeIndex) {
        this(skill1, skill1Points, typeIndex, false);
    }

    /**
     * Used when creating a talisman with two skills.
     */
    public ASBTalisman(SkillTree skill1, int skill1Points, SkillTree skill2, int skill2Points, int typeIndex) {
        this(skill1, skill1Points, typeIndex, false);

        skillTrees[1] = skill2;
        skillPoints[1] = skill2Points;
    }

    private ASBTalisman(SkillTree skill1, int skill1Points, int typeIndex, boolean hasSkill2) {
        this();

        skillTrees[0] = skill1;
        skillPoints[0] = skill1Points;

        this.typeIndex = typeIndex;

        if (!hasSkill2) {
            skillTrees[1] = null;
            skillPoints[1] = 0;
        }
    }

    public SkillTree getSkill1() {
        return skillTrees[0];
    }

    public void setSkill1(SkillTree skillTree) {
        skillTrees[0] = skillTree;
    }

    public SkillTree getSkill2() {
        return skillTrees[1];
    }

    public void setSkill2(SkillTree skillTree) {
        skillTrees[1] = skillTree;
    }

    public int getSkill1Points() {
        return skillPoints[0];
    }

    public void setSkill1Points(int skillPoints) {
        this.skillPoints[0] = skillPoints;
    }

    public int getSkill2Points() {
        return skillPoints[1];
    }

    public void setSkill2Points(int skillPoints) {
        this.skillPoints[1] = skillPoints;
    }

    @Override
    public String getName() {
        return TypeName.values()[typeIndex].getName() + " Talisman";
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
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

        TypeName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
