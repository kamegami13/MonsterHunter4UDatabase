package com.daviancorp.android.data.classes;

import android.content.Context;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.database.ItemToSkillTreeCursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    private List<ArmorSetSkillTreePoints> skillTreePoints;

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
        
        skillTreePoints = new ArrayList<ArmorSetSkillTreePoints>();
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

    @Deprecated
    public List<ArmorSetSkillTreePoints> generateSkillTreePoints(Context context) {
        List<ArmorSetSkillTreePoints> skillTreePoints = new ArrayList<>();
        
        // TODO: Make this method not be awful

        for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(getHead().getId())) {
            ArmorSetSkillTreePoints s = new ArmorSetSkillTreePoints();
            s.setSkillTree(itemToSkillTree.getSkillTree());
            s.setHeadPoints(itemToSkillTree.getPoints());
        }

        for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(getBody().getId())) {

            ArmorSetSkillTreePoints s = new ArmorSetSkillTreePoints();
            s.setSkillTree(itemToSkillTree.getSkillTree());
            s.setBodyPoints(itemToSkillTree.getPoints());
            skillTreePoints.add(s);

            for (ArmorSetSkillTreePoints armorSkillTree : skillTreePoints) { // Since we could have some repeated skills, we have to flush them.
                if (itemToSkillTree.getSkillTree() == armorSkillTree.getSkillTree()) {
                    armorSkillTree.setBodyPoints(itemToSkillTree.getPoints());
                    skillTreePoints.remove(s);
                }
            }
        }

        for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(getArms().getId())) {

            ArmorSetSkillTreePoints s = new ArmorSetSkillTreePoints();
            s.setSkillTree(itemToSkillTree.getSkillTree());
            s.setArmsPoints(itemToSkillTree.getPoints());
            skillTreePoints.add(s);

            for (ArmorSetSkillTreePoints armorSkillTree : skillTreePoints) {
                if (itemToSkillTree.getSkillTree() == armorSkillTree.getSkillTree()) {
                    armorSkillTree.setArmsPoints(itemToSkillTree.getPoints());
                    skillTreePoints.remove(s);
                }
            }
        }

        for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(getWaist().getId())) {

            ArmorSetSkillTreePoints s = new ArmorSetSkillTreePoints();
            s.setSkillTree(itemToSkillTree.getSkillTree());
            s.setWaistPoints(itemToSkillTree.getPoints());
            skillTreePoints.add(s);

            for (ArmorSetSkillTreePoints armorSkillTree : skillTreePoints) {
                if (itemToSkillTree.getSkillTree() == armorSkillTree.getSkillTree()) {
                    armorSkillTree.setWaistPoints(itemToSkillTree.getPoints());
                    skillTreePoints.remove(s);
                }
            }
        }

        for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(getLegs().getId())) {

            ArmorSetSkillTreePoints s = new ArmorSetSkillTreePoints();
            s.setSkillTree(itemToSkillTree.getSkillTree());
            s.setLegsPoints(itemToSkillTree.getPoints());
            skillTreePoints.add(s);

            for (ArmorSetSkillTreePoints armorSkillTree : skillTreePoints) {
                if (itemToSkillTree.getSkillTree() == armorSkillTree.getSkillTree()) {
                    armorSkillTree.setLegsPoints(itemToSkillTree.getPoints());
                    skillTreePoints.remove(s);
                }
            }
        }

        return skillTreePoints;
    }
    
    /** Adds any skills to the armor set's skill trees that were not there before, and removes those no longer there. */
    public void updateArmorSetSkillTreePoints(Context context) {
        
        List<SkillTree> skillTrees = new ArrayList<>(); // We keep an array list of all the skills in the set to help when we go to remove skills
        
        for (int i = 0; i < armors.length; i++) {
            
            for (SkillTree skillTree : getSkillsFromArmorPiece(armors[i], context).keySet()) {
                
                skillTrees.add(skillTree);
                
                ArmorSetSkillTreePoints s;
                
                if (findArmorSetSkillTreePointsBySkillTree(skillTreePoints, skillTree) == null) { // If the armor set does not yet have this skill tree registered...
                    
                    s = new ArmorSetSkillTreePoints(); // We add it...
                    s.setSkillTree(skillTree);
                    skillTreePoints.add(s);
                }
                else {
                    s = findArmorSetSkillTreePointsBySkillTree(skillTreePoints, skillTree); // Otherwise, we just find the skill tree set that's already there
                }
                
                switch (i) {
                    case 0:
                        s.setHeadPoints(getSkillsFromArmorPiece(armors[i], context).get(skillTree));
                        break;
                    case 1:
                        s.setBodyPoints(getSkillsFromArmorPiece(armors[i], context).get(skillTree));
                        break;
                    case 2:
                        s.setArmsPoints(getSkillsFromArmorPiece(armors[i], context).get(skillTree));
                        break;
                    case 3:
                        s.setWaistPoints(getSkillsFromArmorPiece(armors[i], context).get(skillTree));
                        break;
                    case 4:
                        s.setLegsPoints(getSkillsFromArmorPiece(armors[i], context).get(skillTree));
                        break;
                }
                
            }
        }
        
        for (ArmorSetSkillTreePoints s : skillTreePoints) { // Finally, we remove any skill tree points sets that aren't actually present in the set anymore
            if (!skillTrees.contains(s.getSkillTree())) {
                skillTreePoints.remove(s);
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

        for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(armors[piece].getId())) {
            skills.put(itemToSkillTree.getSkillTree(), itemToSkillTree.getPoints());
        }

        return skills;
    }
    
    /** Utility method for finding a specific {@code ArmorSetSkillTreePoints} from a list given a specific {@code SkillTree}. */
    private ArmorSetSkillTreePoints findArmorSetSkillTreePointsBySkillTree(List<ArmorSetSkillTreePoints> skillTreePoints, SkillTree skillTree) {
        for (ArmorSetSkillTreePoints s : skillTreePoints) {
            if (s.getSkillTree() == skillTree) {
                return s;
            }
        }
        
        return null;
    }


    /**
     * A container class that represents a skill tree as well as a specific number of points provided by each armor piece in a set.
     */
    public static class ArmorSetSkillTreePoints { // TODO: Rename this to SkillTreePointsSet
        
        private SkillTree skillTree;
        private int headPoints;
        private int bodyPoints;
        private int armsPoints;
        private int waistPoints;
        private int legsPoints;
        
        public ArmorSetSkillTreePoints(SkillTree skillTree) {
            this.skillTree = skillTree;
        }

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
