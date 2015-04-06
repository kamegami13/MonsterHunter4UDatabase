package com.daviancorp.android.data.classes;

import android.content.Context;
import android.util.Log;
import com.daviancorp.android.data.database.DataManager;

import java.util.*;

/**
 * Represents a session of the user's interaction with the Armor Set Builder.
 */
public class ArmorSetBuilderSession {

    public static final int HEAD = 0;
    public static final int BODY = 1;
    public static final int ARMS = 2;
    public static final int WAIST = 3;
    public static final int LEGS = 4;
    public static final int TALISMAN = 5;

    private static Equipment noEquipment = new Equipment();
    private static Talisman noTalisman = new Talisman();

    private static Decoration noDecoration = new Decoration();
    public static Decoration dummyDecoration = new Decoration();

    private Equipment[] equipment;
    private Decoration[][] decorations;

    private List<SkillTreePointsSet> skillTreePointsSets;

    private List<OnArmorSetChangedListener> changedListeners;

    /**
     * Default constructor.
     */
    public ArmorSetBuilderSession() {

        equipment = new Equipment[6];
        for (int i = 0; i < equipment.length; i++) {
            if (i != TALISMAN) {
                equipment[i] = noEquipment;
            }
            else {
                equipment[i] = noTalisman;
            }
        }

        decorations = new Decoration[6][3];
        for (int i = 0; i < decorations.length; i++) {
            for (int j = 0; j < decorations[i].length; j++) {
                decorations[i][j] = noDecoration;
            }
        }

        skillTreePointsSets = new ArrayList<>();

        changedListeners = new ArrayList<>();
    }

    public Decoration getDecoration(int pieceIndex, int decorationIndex) {
        return decorations[pieceIndex][decorationIndex];
    }

    public boolean hasDecorations(int pieceIndex) {
        int decorationCount = 0;
        for (Decoration d : decorations[pieceIndex]) {
            if (d != noDecoration) {
                decorationCount++;
            }
        }

        return decorationCount > 0;
    }

    public int getAvailableSlots(int pieceIndex) {
        int decorationCount = 0;
        for (Decoration d : decorations[pieceIndex]) {
            if (d != noDecoration) {
                decorationCount++;
            }
        }

        return equipment[pieceIndex].getNumSlots() - decorationCount;
    }

    /** @return True if the designated slot is actually in use, false if it is empty. */
    public boolean decorationIsReal(int pieceIndex, int decorationIndex) {
        return decorations[pieceIndex][decorationIndex] != noDecoration && decorations[pieceIndex][decorationIndex] != dummyDecoration;
    }

    /** @return True if the designated slot is a "dummy" decoration - that is, the non-first slot in a decoration of size greater than 1 - and false if it is empty or an actual decoration. */
    public boolean decorationIsDummy(int pieceIndex, int decorationIndex) {
        return getDecoration(pieceIndex, decorationIndex) == dummyDecoration;
    }

    /** A utility method that finds the actual decoration causing a dummy to appear. */
    public Decoration findRealDecorationOfDummy(int pieceIndex, int decorationIndex) {
        if (getDecoration(pieceIndex, decorationIndex) != dummyDecoration) {
            throw new IllegalArgumentException("The specified decoration must be a dummy!");
        }

        int i = decorationIndex;
        while (getDecoration(pieceIndex, i) == dummyDecoration) {
            i--;
        }

        return getDecoration(pieceIndex, i);
    }

    /**
     * Attempts to add a decoration to the specified armor piece.
     * @param pieceIndex The index of a piece in the set to fetch, according to {@link com.daviancorp.android.data.classes.ArmorSetBuilderSession}.
     * @param decoration The decoration to add.
     * @return True if the piece was successfuly added, otherwise false.
     */
    public boolean addDecoration(int pieceIndex, Decoration decoration) {
        if (getAvailableSlots(pieceIndex) >= decoration.getNumSlots()) {
            int i = 0;
            while (decorations[pieceIndex][i] != noDecoration) {
                i++;
            }

            decorations[pieceIndex][i] = decoration;
            if (decoration.getNumSlots() == 2) {
                decorations[pieceIndex][i + 1] = dummyDecoration;
            }

            if (decoration.getNumSlots() == 3) {
                decorations[pieceIndex][i + 1] = dummyDecoration;
                decorations[pieceIndex][i + 2] = dummyDecoration;
            }

            notifyArmorSetChangedListeners();
            return true;
        } else {
            return false;
        }
    }

    public void removeDecoration(int pieceIndex, int decorationIndex) {

        if (decorations[pieceIndex][decorationIndex] != dummyDecoration) {
            decorations[pieceIndex][decorationIndex] = noDecoration;

            for (int j = decorationIndex + 1; j < decorations[pieceIndex].length; j++) {
                if (decorations[pieceIndex][j] == dummyDecoration) {
                    decorations[pieceIndex][j] = noDecoration;
                } else {
                    break;
                }

            }

        }

        int i = 0;
        Decoration[] newDecorations = new Decoration[3]; // We move all of the decorations to a new array so that they are all at the beginning

        for (Decoration d : decorations[pieceIndex]) {
            if (d != noDecoration) {
                newDecorations[i++] = d;
            }
        }

        while (i < newDecorations.length) {
            newDecorations[i++] = noDecoration;
        }

        decorations[pieceIndex] = newDecorations;

        notifyArmorSetChangedListeners();
    }

    /** @return True if the user has chosen a piece at the specified index or has created a talisman, false otherwise. */
    public boolean isEquipmentSelected(int pieceIndex) {
        if (pieceIndex == TALISMAN) {
            return equipment[pieceIndex] != noTalisman;
        }
        return equipment[pieceIndex] != noEquipment;
    }

    public void setEquipment(int pieceIndex, Equipment equip) {
        equipment[pieceIndex] = equip;

        notifyArmorSetChangedListeners();
    }

    /** @return A piece of the armor set based on the provided piece index. */
    public Equipment getEquipment(int pieceIndex) {
        return equipment[pieceIndex];
    }

    public Talisman getTalisman() {
        return (Talisman)equipment[TALISMAN];
    }

    public void removeEquipment(int pieceIndex) {
        if (pieceIndex == TALISMAN) {
            equipment[pieceIndex] = noTalisman;
        }
        else {
            equipment[pieceIndex] = noEquipment;
        }

        for (int i = 0; i < decorations[pieceIndex].length; i++) {
            decorations[pieceIndex][i] = noDecoration;
        }

        notifyArmorSetChangedListeners();
    }

    public List<SkillTreePointsSet> getSkillTreePointsSets() {
        return skillTreePointsSets;
    }

    /**
     * Adds any skills to the armor set's skill trees that were not there before, and removes those no longer there.
     */
    public void updateSkillTreePointsSets(Context context) {

        skillTreePointsSets.clear();

        Map<Long, SkillTreePointsSet> skillTreeToSkillTreePointsSet = new HashMap<>(); // A map of the skill trees in the set and their associated SkillTreePointsSets

        for (SkillTreePointsSet pointsSet : skillTreePointsSets) {
            skillTreeToSkillTreePointsSet.put(pointsSet.getSkillTree().getId(), pointsSet);
        }

        for (int i = 0; i < equipment.length; i++) {

            Log.v("SetBuilder", "Reading skills from armor piece " + i);

            Map<SkillTree, Integer> armorSkillTreePoints = getSkillsFromArmorPiece(i, context); // A map of the current piece of armor's skills, localized so we don't have to keep calling it

            for (SkillTree skillTree : armorSkillTreePoints.keySet()) {

                SkillTreePointsSet s; // The actual points set that we are working with that will be shown to the user

                if (!skillTreeToSkillTreePointsSet.containsKey(skillTree.getId())) { // If the armor set does not yet have this skill tree registered...
                    Log.d("SetBuilder", "Adding skill tree " + skillTree.getName() + " to the list of Skill Trees in the armor set.");

                    s = new SkillTreePointsSet(); // We add it...
                    s.setSkillTree(skillTree);
                    skillTreePointsSets.add(s);

                    skillTreeToSkillTreePointsSet.put(skillTree.getId(), s);

                } else {
                    Log.d("SetBuilder", "Skill tree " + skillTree.getName() + " already registered!");
                    s = skillTreeToSkillTreePointsSet.get(skillTree.getId()); // Otherwise, we just find the skill tree set that's already there
                }

                s.setPoints(i, armorSkillTreePoints.get(skillTree));
            }
        }
    }

    /**
     * A helper method that converts an armor piece present in the current session into a map of the skills it provides and the respective points in each.
     * @param pieceIndex The piece of armor to get the skills from.
     * <li/>0: Head
     * <li/>1: Body
     * <li/>2: Arms
     * <li/>3: Waist
     * <li/>4: Legs
     * @return A map of all the skills the armor piece provides along with the number of points in each.
     */
    private Map<SkillTree, Integer> getSkillsFromArmorPiece(int pieceIndex, Context context) {
        Map<SkillTree, Integer> skills = new HashMap<>();

        if (pieceIndex != TALISMAN) {
            for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(equipment[pieceIndex].getId())) { // We add skills for armor
                skills.put(itemToSkillTree.getSkillTree(), itemToSkillTree.getPoints());
                Log.d("SetBuilder", "Skill tree added to map: " + itemToSkillTree.getSkillTree().getName());
            }
        }
        else if (getTalisman() != noTalisman) {
            skills.put(getTalisman().getSkill1(), getTalisman().getSkill1Points());

            if (getTalisman().hasTwoSkills()) {
                Log.d("SetBuilder", "Talisman has two skills.");
                skills.put(getTalisman().getSkill2(), getTalisman().getSkill2Points());
            }
        }

        for (Decoration d : decorations[pieceIndex]) { // Now we work on decorations
            for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(d.getId())) {
                if (skills.containsKey(itemToSkillTree.getSkillTree())) {
                    int points = skills.get(itemToSkillTree.getSkillTree()) + itemToSkillTree.getPoints();
                    skills.remove(itemToSkillTree.getSkillTree());
                    skills.put(itemToSkillTree.getSkillTree(), points);
                } else {
                    skills.put(itemToSkillTree.getSkillTree(), itemToSkillTree.getPoints());
                }
            }
        }
        return skills;
    }

    public void addOnArmorSetChangedListener(OnArmorSetChangedListener l) {
        changedListeners.add(l);
    }

    public void detachOnArmorSetChangedListener(OnArmorSetChangedListener l) {
        changedListeners.remove(l);
    }

    private void notifyArmorSetChangedListeners() {
        for (OnArmorSetChangedListener l : changedListeners) {
            l.onArmorSetChanged();
        }
    }

    /** Allows an object to be notified when the {@code ArmorSetBuilderSession} is changed in some way. */
    public static interface OnArmorSetChangedListener {
        public void onArmorSetChanged();
    }

    /**
     * A container class that represents a skill tree as well as a specific number of points provided by each armor piece in a set.
     */
    public static class SkillTreePointsSet {

        private SkillTree skillTree;
        private int[] points;

        public SkillTreePointsSet() {
            points = new int[6];
        }

        public SkillTree getSkillTree() {
            return skillTree;
        }

        public int getHeadPoints() {
            return points[HEAD];
        }

        public int getBodyPoints() {
            return points[BODY];
        }

        public int getArmsPoints() {
            return points[ARMS];
        }

        public int getWaistPoints() {
            return points[WAIST];
        }

        public int getLegsPoints() {
            return points[LEGS];
        }

        public int getTalismanPoints() {
            return points[TALISMAN];
        }

        /**
         * @return The total number of skill points provided to the skill by all pieces in the set.
         */
        public int getTotal() {
            int total = 0;
            for (int piecePoints : points) {
                total += piecePoints;
            }
            return total;
        }

        public void setSkillTree(SkillTree skillTree) {
            this.skillTree = skillTree;
        }

        public void setPoints(int pieceIndex, int piecePoints) {
            points[pieceIndex] = piecePoints;
        }
    }
}
