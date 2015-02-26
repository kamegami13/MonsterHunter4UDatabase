package com.daviancorp.android.data.classes;

import android.content.Context;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.database.ItemToSkillTreeCursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArmorSetBuilderSession {
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
    }

    public boolean isHeadSelected() {
        return armors[0] != none;
    }

    public boolean isBodySelected() {
        return armors[1] != none;
    }

    public boolean isArmsSelected() {
        return armors[2] != none;
    }

    public boolean isWaistSelected() {
        return armors[3] != none;
    }

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

    public Armor getHead() {
        return armors[0];
    }

    public Armor getBody() {
        return armors[1];
    }

    public Armor getArms() {
        return armors[2];
    }

    public Armor getWaist() {
        return armors[3];
    }

    public Armor getLegs() {
        return armors[4];
    }


    public List<ArmorSetSkillTreePoints> generateSkillTreePoints(Context context) {
        List<ArmorSetSkillTreePoints> skillTreePoints = new ArrayList<>();

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

    /**
     * @return A map of all the skills the armor piece provides along with the number of points in each.
     * @param piece The piece of armor to get the skills from.
     * <li>0: Head
     * <li>1: Body
     * <li>2: Arms
     * <li>3: Waist
     * <li>4: Legs</li>
     * @param context A context with which to access the database.
     */
    public Map<SkillTree, Integer> getSkillsFromArmorPiece(int piece, Context context) {
        Map<SkillTree, Integer> skills = new HashMap<>();

        for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(armors[piece].getId())) {
            skills.put(itemToSkillTree.getSkillTree(), itemToSkillTree.getPoints());
        }

        return skills;
    }


    public static class ArmorSetSkillTreePoints {
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
