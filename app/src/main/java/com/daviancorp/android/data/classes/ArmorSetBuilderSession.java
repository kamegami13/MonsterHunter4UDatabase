package com.daviancorp.android.data.classes;

import android.content.Context;
import android.util.Log;
import com.daviancorp.android.data.database.DataManager;

import java.util.*;

/**
 * Represents a session of the user's interaction with the Armor Set Builder.
 */
public class ArmorSetBuilderSession {
    
    /** A singleton {@code Armor} that represents the absence of armor. */
    private static Armor none = new Armor();

    /**
     * The array of armor pieces in the set.
     * <li>0: Head
     * <li>1: Body
     * <li>2: Arms
     * <li>3: Waist
     * <li>4: Legs
     */
    private Armor[] armors;
    
    private List<SkillTreePointsSet> skillTreePointsSets;

    /**
     * Default constructor.
     */
    public ArmorSetBuilderSession() {
        Armor head = none;
        Armor body = none;
        Armor arms = none;
        Armor waist = none;
        Armor legs = none;

        armors = new Armor[5];
        armors[0] = head;
        armors[1] = body;
        armors[2] = arms;
        armors[3] = waist;
        armors[4] = legs;
        
        skillTreePointsSets = new ArrayList<>();
    }

    /** @return Whether the user has selected a head piece or not. */
    public boolean isHeadSelected() {
        return armors[0] != none;
    }

    /** @return Whether the user has selected a body piece or not. */
    public boolean isBodySelected() {
        return armors[1] != none;
    }

    /** @return Whether the user has selected a arms piece or not. */
    public boolean isArmsSelected() {
        return armors[2] != none;
    }

    /** @return Whether the user has selected a waist piece or not. */
    public boolean isWaistSelected() {
        return armors[3] != none;
    }

    /** @return Whether the user has selected a legs piece or not. */
    public boolean isLegsSelected() {
        return armors[4] != none;
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

    /** @return The armor set's head piece. */
    public Armor getHead() {
        return armors[0];
    }

    /** @return The armor set's body piece. */
    public Armor getBody() {
        return armors[1];
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
