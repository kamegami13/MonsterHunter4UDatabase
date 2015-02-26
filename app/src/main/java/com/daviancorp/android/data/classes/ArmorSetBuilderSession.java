package com.daviancorp.android.data.classes;

import android.content.Context;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.database.ItemToSkillTreeCursor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArmorSetBuilderSession {
    private static Armor none = new Armor();

    /**
     * The array of armor pieces in the set.
     * <li>
     * 0: Head
     * </li>
     * <li>
     * 1: Body
     * </li>
     * <li>
     * 2: Arms
     * </li>
     * <li>
     * 3: Waist
     * </li>
     * <li>
     * 4: Legs
     * </li>
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

    /**
     * @return A map of all the skills the armor piece provides along with the number of points in each.
     * @param piece The piece of armor to get the skills from.
     * <li>0: Head</li>
     * <li>1: Body</li>
     * <li>2: Arms</li>
     * <li>3: Waist</li>
     * <li>4: Legs</li>
     * @param context A context with which to access the database.
     */
    public Map<SkillTree, Integer> getSkillsFromArmorPiece(int piece, Context context) {
        Map<SkillTree, Integer> skills = new HashMap<>();

        for (ItemToSkillTree itemToSkillTree : DataManager.get(context).queryItemToSkillTreeArrayItem(armors[piece].getId())) {
            // TODO
        }

        return skills;
    }
}
