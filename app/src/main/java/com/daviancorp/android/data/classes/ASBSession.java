package com.daviancorp.android.data.classes;

import android.content.Context;
import android.util.Log;
import com.daviancorp.android.data.database.DataManager;

import java.util.*;

/**
 * Contains all of the juicy stuff regarding ASB sets, like the armor inside and the skills it provides.
 */
public class ASBSession {
    public static final int HEAD = 0;
    public static final int BODY = 1;
    public static final int ARMS = 2;
    public static final int WAIST = 3;
    public static final int LEGS = 4;
    public static final int TALISMAN = 5;

    public static Decoration dummyDecoration = new Decoration();

    private Context context;

    private ASBSet asbSet;

    private Equipment[] equipment;
    private Decoration[][] decorations;

    private List<SkillTreeInSet> skillTreesInSet;

    private List<SessionChangeListener> sessionChangeListeners;

    public ASBSession(Context context) {

        this.context = context;

        equipment = new Equipment[6];

        decorations = new Decoration[6][3];

        skillTreesInSet = new ArrayList<>();
    }

    public long getId() {
        return asbSet.getId();
    }

    public int getRank() {
        return asbSet.getRank();
    }

    public int getHunterType() {
        return asbSet.getHunterType();
    }

    public void setASBSet(ASBSet set) {
        asbSet = set;
    }

    public Decoration getDecoration(int pieceIndex, int decorationIndex) {
        return decorations[pieceIndex][decorationIndex];
    }

    /**
     * @return True if the armor piece in question has any number of decorations, otherwise false.
     */
    public boolean hasDecorations(int pieceIndex) {
        int decorationCount = 0;
        for (Decoration d : decorations[pieceIndex]) {
            if (d != null) {
                decorationCount++;
            }
        }

        return decorationCount > 0;
    }

    public int getAvailableSlots(int pieceIndex) {
        if (isEquipmentSelected(pieceIndex)) {
            int decorationCount = 0;
            for (Decoration d : decorations[pieceIndex]) {
                if (d != null) {
                    decorationCount++;
                }
            }

            return equipment[pieceIndex].getNumSlots() - decorationCount;
        }
        else {
            return 0;
        }
    }

    /**
     * @return True if the slot is in use by an actual, user-selected decoration.
     */
    public boolean decorationIsReal(int pieceIndex, int decorationIndex) {
        return decorations[pieceIndex][decorationIndex] != null
                && decorations[pieceIndex][decorationIndex] != dummyDecoration;
    }

    /**
     * @return True if the designated slot is a "dummy" decoration - that is, the non-first slot in a decoration of
     * size greater than 1 - and false if it is empty or an actual decoration.
     */
    public boolean decorationIsDummy(int pieceIndex, int decorationIndex) {
        return getDecoration(pieceIndex, decorationIndex) == dummyDecoration;
    }

    /**
     * A utility method that finds the actual decoration causing a dummy to appear.
     */
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
     * Same as {@link #addDecoration(int, Decoration, boolean)}, but always updates skills.
     * @see #addDecoration(int, Decoration, boolean)
     */
    public int addDecoration(int pieceIndex, Decoration decoration) {
        return addDecoration(pieceIndex, decoration, true);
    }

    /**
     * Attempts to add a decoration to the specified armor piece.
     * @param pieceIndex The index of a piece in the set to fetch, according to {@link ASBSession}.
     * @param decoration The decoration to add.
     * @param updateSkills Whether or not to call {@link #updateSkillTreePointsSets()} upon completion.
     * @return The 0-based index of the slot that the decoration was added to.
     */
    public int addDecoration(int pieceIndex, Decoration decoration, boolean updateSkills) {
        Log.v("ASB", "Adding decoration at piece index " + pieceIndex);
        if (getAvailableSlots(pieceIndex) >= decoration.getNumSlots()) {
            int i = 0;
            while (decorations[pieceIndex][i] != null) {
                i++;
            }

            decorations[pieceIndex][i] = decoration;

            if (decoration.getNumSlots() == 2) {
                decorations[pieceIndex][i + 1] = dummyDecoration;
            }
            else if (decoration.getNumSlots() == 3) {
                decorations[pieceIndex][i + 1] = dummyDecoration;
                decorations[pieceIndex][i + 2] = dummyDecoration;
            }

            if (updateSkills) {
                updateSkillTreePointsSets();
            }

            return i;
        }
        else {
            Log.e("ASB", "Cannot add that decoration!");
            return -1;
        }
    }

    /**
     * Same as {@link #removeDecoration(int, int, boolean)}, but always updates skills.
     * @see #removeDecoration(int, int, boolean)
     */
    public void removeDecoration(int pieceIndex, int decorationIndex) {
        removeDecoration(pieceIndex, decorationIndex, true);
    }

    /**
     * Removes the decoration at the specified location from the specified armor piece. Will fail if the decoration in question is non-existent or a dummy.
     * @param updateSkills Whether or not to call {@link #updateSkillTreePointsSets()} upon completion.
     */
    public void removeDecoration(int pieceIndex, int decorationIndex, boolean updateSkills) {
        if (decorations[pieceIndex][decorationIndex] != dummyDecoration && decorations[pieceIndex][decorationIndex] != null) {
            decorations[pieceIndex][decorationIndex] = null;

            int i = 0;
            Decoration[] newDecorations = new Decoration[3]; // We move all of the decorations to a new array so that they are all at the beginning

            for (Decoration d : decorations[pieceIndex]) {
                if (d != null && d != dummyDecoration) {
                    newDecorations[i++] = d;
                }
            }

            while (i < newDecorations.length) {
                newDecorations[i++] = null;
            }

            decorations[pieceIndex] = newDecorations;

            if (updateSkills) {
                updateSkillTreePointsSets();
            }
        }
    }

    /**
     * @return True if the user has chosen or created a piece at the specified index, false otherwise.
     */
    public boolean isEquipmentSelected(int pieceIndex) {
        return equipment[pieceIndex] != null;
    }

    /**
     * @return A piece of the armor set based on the provided piece index.
     */
    public Equipment getEquipment(int pieceIndex) {
        return equipment[pieceIndex];
    }

    /**
     * @return The set's talisman.
     */
    public ASBTalisman getTalisman() {
        return (ASBTalisman) equipment[TALISMAN];
    }

    /**
     *  Changes the equipment at the specified location.
     */
    public void setEquipment(int pieceIndex, Equipment equip) {
        setEquipment(pieceIndex, equip, true);
    }

    /**
     * Changes the equipment at the specified location.
     * @param updateSkills Whether or not to call {@link #updateSkillTreePointsSets()} upon completion.
     */
    public void setEquipment(int pieceIndex, Equipment equip, boolean updateSkills) {
        equipment[pieceIndex] = equip;

        if (updateSkills) {
            updateSkillTreePointsSets();
        }
    }

    /**
     * Removes the equipment at the specified location.
     */
    public void removeEquipment(int pieceIndex) {
        removeEquipment(pieceIndex, true);
    }

    /**
     * Removes the equipment at the specified location.
     * @param updateSkills Whether or not to call {@link #updateSkillTreePointsSets()} upon completion.
     */
    public void removeEquipment(int pieceIndex, boolean updateSkills) {
        if (pieceIndex == TALISMAN) {
            equipment[pieceIndex] = null;
        }
        else {
            equipment[pieceIndex] = null;
        }

        for (int i = 0; i < decorations[pieceIndex].length; i++) {
            decorations[pieceIndex][i] = null;
        }

        if (updateSkills) {
            updateSkillTreePointsSets();
        }
    }

    public List<SkillTreeInSet> getSkillTreesInSet() {
        return skillTreesInSet;
    }

    /**
     * Adds any skills to the armor set's skill trees that were not there before, and removes those no longer there.
     */
    public void updateSkillTreePointsSets() {
        skillTreesInSet.clear();

        Map<Long, SkillTreeInSet> skillTreeToSkillTreeInSet = new HashMap<>(); // A map of the skill trees in the set and their associated SkillTreePointsSets

        for (SkillTreeInSet pointsSet : skillTreesInSet) {
            skillTreeToSkillTreeInSet.put(pointsSet.getSkillTree().getId(), pointsSet);
        }

        for (int i = 0; i < equipment.length; i++) {

            Log.v("ASB", "Reading skills from armor piece " + i);

            Map<SkillTree, Integer> armorSkillTreePoints = getSkillsFromArmorPiece(i); // A map of the current piece of armor's skills, localized so we don't have to keep calling it

            for (SkillTree skillTree : armorSkillTreePoints.keySet()) {

                SkillTreeInSet s; // The actual points set that we are working with that will be shown to the user

                if (!skillTreeToSkillTreeInSet.containsKey(skillTree.getId())) { // If the armor set does not yet have this skill tree registered...
                    Log.v("ASB", "Adding skill tree " + skillTree.getName() + " to the list of Skill Trees in the armor set.");

                    s = new SkillTreeInSet(); // We add it...
                    s.setSkillTree(skillTree);
                    skillTreesInSet.add(s);

                    skillTreeToSkillTreeInSet.put(skillTree.getId(), s);

                }
                else {
                    Log.v("ASB", "Skill tree " + skillTree.getName() + " already registered!");
                    s = skillTreeToSkillTreeInSet.get(skillTree.getId()); // Otherwise, we just find the skill tree set that's already there
                }

                s.setPoints(i, armorSkillTreePoints.get(skillTree));
            }
        }
    }

    /**
     * A helper method that converts an armor piece present in the current session into a map of the skills it provides and the respective points in each.
     * @param pieceIndex The piece of armor to get the skills from.
     * @return A map of all the skills the armor piece provides along with the number of points in each.
     */
    private Map<SkillTree, Integer> getSkillsFromArmorPiece(int pieceIndex) {
        Map<SkillTree, Integer> skills = new HashMap<>();

        if (equipment[pieceIndex] != null) {
            if (pieceIndex != TALISMAN) {
                for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(equipment[pieceIndex].getId())) { // We add skills for armor
                    skills.put(itemToSkillTree.getSkillTree(), itemToSkillTree.getPoints());
                }
            }
            else {
                skills.put(getTalisman().getSkill1(), getTalisman().getSkill1Points());

                if (getTalisman().getSkill2() != null) {
                    skills.put(getTalisman().getSkill2(), getTalisman().getSkill2Points());
                }
            }

            for (Decoration d : decorations[pieceIndex]) {
                if (d != null) {
                    for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(d.getId())) {
                        SkillTree skillTreeToAddTo = null;

                        for (SkillTree skillTree : skills.keySet()) {
                            if (skillTree.getId() == itemToSkillTree.getSkillTree().getId()) {
                                skillTreeToAddTo = skillTree;
                                break;
                            }
                        }

                        if (skillTreeToAddTo != null) {
                            int points = skills.get(skillTreeToAddTo) + itemToSkillTree.getPoints();
                            skills.remove(skillTreeToAddTo);
                            skills.put(skillTreeToAddTo, points);
                        }
                        else {
                            skills.put(itemToSkillTree.getSkillTree(), itemToSkillTree.getPoints());
                        }
                    }
                }
            }
        }

        return skills;
    }

    public void addSessionChangeListener(SessionChangeListener l) {
        if (sessionChangeListeners == null) {
            sessionChangeListeners = new ArrayList<>();
        }

        sessionChangeListeners.add(l);
    }

    public void removeSessionChangeListener(SessionChangeListener l) {
        if (sessionChangeListeners == null) {
            sessionChangeListeners = new ArrayList<>();
        }

        sessionChangeListeners.remove(l);
    }

    public void notifySessionChangeListeners() {
        if (sessionChangeListeners != null) {
            for (SessionChangeListener l : sessionChangeListeners) {
                l.onSessionChange();
            }
        }
    }

    public interface SessionChangeListener {
        void onSessionChange();
    }

    /**
     * A container class that represents a skill tree as well as a specific number of points provided by each armor piece in a set.
     */
    public static class SkillTreeInSet {

        private SkillTree skillTree;
        private int[] points;

        public SkillTreeInSet() {
            points = new int[6];
        }

        public SkillTree getSkillTree() {
            return skillTree;
        }

        public int getPoints(int pieceIndex) {
            if (pieceIndex == BODY) {
                throw new IllegalArgumentException("Use the getPoints(int, List<SkillTreeInSet>) when dealing with the chest piece!");
            }
            return getPoints(pieceIndex, null);
        }

        public int getPoints(int pieceIndex, List<SkillTreeInSet> trees) {
            if (pieceIndex == BODY) {
                int torsoUpPieces = 0;
                for (SkillTreeInSet s : trees) {
                    if (s.getSkillTree().getId() == 1) { // 1 is the ID of the Torso Up skill.
                        torsoUpPieces++;
                    }
                }
                return points[pieceIndex] * (torsoUpPieces + 1);
            }
            else {
                return points[pieceIndex];
            }
        }

        /**
         * @return The total number of skill points provided to the skill by all pieces in the set.
         */
        public int getTotal(List<SkillTreeInSet> trees) {
            int total = 0;
            for (int i = 0; i < points.length; i++) {
                total += getPoints(i, trees);
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
