package com.daviancorp.android.data.classes;

import android.content.Context;
import android.util.Log;
import com.daviancorp.android.data.database.DataManager;

import java.util.*;

/**
 * Represents a session of the user's interaction with the Armor Set Builder.
 * <p>
 * For using armor arrays:
 * <li>0: Head
 * <li>1: Body
 * <li>2: Arms
 * <li>3: Waist
 * <li>4: Legs
 */
public class ArmorSetBuilderSession {

    public static final int HEAD = 0;
    public static final int BODY = 1;
    public static final int ARMS = 2;
    public static final int WAIST = 3;
    public static final int LEGS = 4;
    
    /** A singleton {@code Armor} that represents the absence of armor. */
    private static Armor noArmor = new Armor();

    /** A singleton {@code Decoration} that represents the absence of a decoration. */
    private static Decoration noDecoration = new Decoration();

    public static Decoration dummy = new Decoration();

    /**
     * The array of armor pieces in the set.
     * @see com.daviancorp.android.data.classes.ArmorSetBuilderSession
     */
    private Armor[] armors;

    /**
     * The array of socketed decorations for each armor piece.
     * @see com.daviancorp.android.data.classes.ArmorSetBuilderSession
     */
    private Decoration[][] decorations;
    
    private List<SkillTreePointsSet> skillTreePointsSets;

    /**
     * Default constructor.
     */
    public ArmorSetBuilderSession() {

        armors = new Armor[5];
        for (int i = 0; i < armors.length; i++) {
            armors[i] = noArmor;
        }

        decorations = new Decoration[5][3];
        for (int i = 0; i < decorations.length; i++) {
            for (int j = 0; j < decorations[i].length; j++) {
                decorations[i][j] = noDecoration;
            }
        }

        skillTreePointsSets = new ArrayList<>();
    }

    /**
     * Attempts to add an decoration to the specified armor piece.
     * @param pieceIndex The index of a piece in the set to fetch, according to {@link com.daviancorp.android.data.classes.ArmorSetBuilderSession}.
     * @param decoration The decoration to add.
     * @return True if the piece was successfuly added, otherwise false.
     */
    public boolean addDecoration(int pieceIndex, Decoration decoration) {
        if (getAvailableSlots(pieceIndex) >= decoration.getNumSlots()) { // TODO
            int i = 0;
            while (decorations[pieceIndex][i] != noDecoration) {
                i++;
            }

            decorations[pieceIndex][i] = decoration;
            if (decoration.getNumSlots() == 2) {
                decorations[pieceIndex][i + 1] = dummy;
            }

            if (decoration.getNumSlots() == 3) {
                decorations[pieceIndex][i + 2] = dummy;
            }

            return true;
        }
        else {
            return false;
        }
    }

    public int getAvailableSlots(int pieceIndex) {
        int decorationCount = 0;
        for (Decoration d : decorations[pieceIndex]) {
            if (d != noDecoration) {
                decorationCount++;
            }
        }

        return armors[pieceIndex].getNumSlots() - decorationCount;
    }

    /** @return Whether the user has selected a head piece or not. */
    public boolean isHeadSelected() {
        return armors[0] != noArmor;
    }

    /** @return Whether the user has selected a body piece or not. */
    public boolean isBodySelected() {
        return armors[1] != noArmor;
    }

    /** @return Whether the user has selected a arms piece or not. */
    public boolean isArmsSelected() {
        return armors[2] != noArmor;
    }

    /** @return Whether the user has selected a waist piece or not. */
    public boolean isWaistSelected() {
        return armors[3] != noArmor;
    }

    /** @return Whether the user has selected a legs piece or not. */
    public boolean isLegsSelected() {
        return armors[4] != noArmor;
    }

    public void setHead(Armor head) {
        armors[0] = head;
    }

    public void setBody(Armor body) {
        armors[1] = body;
    }

    public void setArms(Armor arms) {
        armors[2] = arms;
    }

    public void setWaist(Armor waist) {
        armors[3] = waist;
    }

    public void setLegs(Armor legs) {
        armors[4] = legs;
    }

    public Decoration getDecoration(int pieceIndex, int decorationIndex) {
        return decorations[pieceIndex][decorationIndex];
    }

    /** @return True if the designated slot is actually in use, false if it is empty. */
    public boolean decorationIsReal(int pieceIndex, int decorationIndex) {
        return decorations[pieceIndex][decorationIndex] == noDecoration;
    }

    public boolean decorationIsDummy(int pieceIndex, int decorationIndex) {
        return getDecoration(pieceIndex, decorationIndex) == dummy;
    }

    /**
     * @return A set of the armor set based on the provided piece index.
     * @see com.daviancorp.android.data.classes.ArmorSetBuilderSession
     */
    public Armor getArmor(int pieceIndex) {
        return armors[pieceIndex];
    }

    /** @return The armor set's head piece. */
    public Armor getHead() {
        return armors[HEAD];
    }

    /** @return The armor set's body piece. */
    public Armor getBody() {
        return armors[BODY];
    }

    /** @return The armor set's arms piece. */
    public Armor getArms() {
        return armors[2];
    }

    /** @return The armor set's waist piece. */
    public Armor getWaist() {
        return armors[3];
    }

    /** @return The armor set's legs piece. */
    public Armor getLegs() {
        return armors[4];
    }

    public List<SkillTreePointsSet> getSkillTreePointsSets() {
        return skillTreePointsSets;
    }
    
    /** Adds any skills to the armor set's skill trees that were not there before, and removes those no longer there. */
    public void updateSkillTreePointsSets(Context context) {

        skillTreePointsSets.clear();
        
        Map<Long, SkillTreePointsSet> skillTreeToSkillTreePointsSet = new HashMap<>(); // A map of the current skill trees' ID's in the set and their associated SkillTreePointsSets

        for (SkillTreePointsSet pointsSet : skillTreePointsSets) {
            skillTreeToSkillTreePointsSet.put(pointsSet.getSkillTree().getId(), pointsSet);
        }

        for (int i = 0; i < armors.length; i++) {

            Map<SkillTree, Integer> armorSkillTreePoints = getSkillsFromArmorPiece(i, context); // A map of the current piece of armor's skills

            for (SkillTree skillTree : armorSkillTreePoints.keySet()) {

                SkillTreePointsSet s; // The actual points set that we are working with that will be shown to the user

                if (!skillTreeToSkillTreePointsSet.containsKey(skillTree.getId())) { // If the armor set does not yet have this skill tree registered...
                    Log.d("SetBuilder", "Registering skill tree...");

                    s = new SkillTreePointsSet(); // We add it...
                    s.setSkillTree(skillTree);
                    skillTreePointsSets.add(s);

                    skillTreeToSkillTreePointsSet.put(skillTree.getId(), s);
                }
                else {
                    Log.d("SetBuilder", "Skill tree already registered!");
                    s = skillTreeToSkillTreePointsSet.get(skillTree.getId()); // Otherwise, we just find the skill tree set that's already there
                }

                switch (i) {
                    case 0:
                        s.setHeadPoints(armorSkillTreePoints.get(skillTree));
                        break;
                    case 1:
                        s.setBodyPoints(armorSkillTreePoints.get(skillTree));
                        break;
                    case 2:
                        s.setArmsPoints(armorSkillTreePoints.get(skillTree));
                        break;
                    case 3:
                        s.setWaistPoints(armorSkillTreePoints.get(skillTree));
                        break;
                    case 4:
                        s.setLegsPoints(armorSkillTreePoints.get(skillTree));
                        break;
                }
                
            }
        }

    }

    /**
     * @return A map of all the skills the armor piece provides along with the number of points in each.
     * @param pieceIndex The piece of armor to get the skills from.
     * <li>0: Head
     * <li>1: Body
     * <li>2: Arms
     * <li>3: Waist
     * <li>4: Legs</li>
     * @param context A context with which to access the app's database.
     */
    public Map<SkillTree, Integer> getSkillsFromArmorPiece(int pieceIndex, Context context) {
        Map<SkillTree, Integer> skills = new HashMap<>();

        for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(armors[pieceIndex].getId())) {
            skills.put(itemToSkillTree.getSkillTree(), itemToSkillTree.getPoints());
        }

        return skills;
    }

    /** A container class that represents a skill tree as well as a specific number of points provided by each armor piece in a set. */
    public static class SkillTreePointsSet {
        
        private SkillTree skillTree;
        private int headPoints;
        private int bodyPoints;
        private int armsPoints;
        private int waistPoints;
        private int legsPoints;

        public SkillTree getSkillTree() {
            return skillTree;
        }

        public int getHeadPoints() {
            return headPoints;
        }

        public int getBodyPoints() {
            return bodyPoints;
        }

        public int getArmsPoints() {
            return armsPoints;
        }

        public int getWaistPoints() {
            return waistPoints;
        }

        public int getLegsPoints() {
            return legsPoints;
        }

        public int getTotal() {
            return getHeadPoints() + getBodyPoints() + getArmsPoints() + getWaistPoints() + getLegsPoints();
        }

        public void setSkillTree(SkillTree skillTree) {
            this.skillTree = skillTree;
        }

        public void setHeadPoints(int headPoints) {
            this.headPoints = headPoints;
        }

        public void setBodyPoints(int bodyPoints) {
            this.bodyPoints = bodyPoints;
        }

        public void setArmsPoints(int armsPoints) {
            this.armsPoints = armsPoints;
        }

        public void setWaistPoints(int waistPoints) {
            this.waistPoints = waistPoints;
        }

        public void setLegsPoints(int legsPoints) {
            this.legsPoints = legsPoints;
        }

    }
}
