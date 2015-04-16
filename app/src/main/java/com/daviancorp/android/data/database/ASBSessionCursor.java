package com.daviancorp.android.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import com.daviancorp.android.data.classes.*;

public class ASBSessionCursor extends CursorWrapper {

    public ASBSessionCursor(Cursor c) {
        super(c);
    }

    public ASBSession getASBSession(Context context) {
        if (isBeforeFirst() || isAfterLast()) {
            return null;
        }

        ASBSession session = new ASBSession(context);

        long id = getLong(getColumnIndex(S.COLUMN_ARMOR_SET_ID));

        ASBSet set = DataManager.get(context).getASBSet(id);
        session.setASBSet(set);

        long headId = getLong(getColumnIndex(S.COLUMN_HEAD_ARMOR_ID));
        long headDecoration1Id = getLong(getColumnIndex(S.COLUMN_HEAD_DECORATION_1_ID));
        long headDecoration2Id = getLong(getColumnIndex(S.COLUMN_HEAD_DECORATION_2_ID));
        long headDecoration3Id = getLong(getColumnIndex(S.COLUMN_HEAD_DECORATION_3_ID));
        Armor headArmor = getArmorById(context, headId);
        Decoration headDecoration1 = getDecorationById(context, headDecoration1Id);
        Decoration headDecoration2 = getDecorationById(context, headDecoration2Id);
        Decoration headDecoration3 = getDecorationById(context, headDecoration3Id);

        long bodyId = getLong(getColumnIndex(S.COLUMN_BODY_ARMOR_ID));
        long bodyDecoration1Id = getLong(getColumnIndex(S.COLUMN_BODY_DECORATION_1_ID));
        long bodyDecoration2Id = getLong(getColumnIndex(S.COLUMN_BODY_DECORATION_2_ID));
        long bodyDecoration3Id = getLong(getColumnIndex(S.COLUMN_BODY_DECORATION_3_ID));
        Armor bodyArmor = getArmorById(context, bodyId);
        Decoration bodyDecoration1 = getDecorationById(context, bodyDecoration1Id);
        Decoration bodyDecoration2 = getDecorationById(context, bodyDecoration2Id);
        Decoration bodyDecoration3 = getDecorationById(context, bodyDecoration3Id);

        long armsId = getLong(getColumnIndex(S.COLUMN_ARMS_ARMOR_ID));
        long armsDecoration1Id = getLong(getColumnIndex(S.COLUMN_ARMS_DECORATION_1_ID));
        long armsDecoration2Id = getLong(getColumnIndex(S.COLUMN_ARMS_DECORATION_2_ID));
        long armsDecoration3Id = getLong(getColumnIndex(S.COLUMN_ARMS_DECORATION_3_ID));
        Armor armsArmor = getArmorById(context, armsId);
        Decoration armsDecoration1 = getDecorationById(context, armsDecoration1Id);
        Decoration armsDecoration2 = getDecorationById(context, armsDecoration2Id);
        Decoration armsDecoration3 = getDecorationById(context, armsDecoration3Id);

        long waistId = getLong(getColumnIndex(S.COLUMN_WAIST_ARMOR_ID));
        long waistDecoration1Id = getLong(getColumnIndex(S.COLUMN_WAIST_DECORATION_1_ID));
        long waistDecoration2Id = getLong(getColumnIndex(S.COLUMN_WAIST_DECORATION_2_ID));
        long waistDecoration3Id = getLong(getColumnIndex(S.COLUMN_WAIST_DECORATION_3_ID));
        Armor waistArmor = getArmorById(context, waistId);
        Decoration waistDecoration1 = getDecorationById(context, waistDecoration1Id);
        Decoration waistDecoration2 = getDecorationById(context, waistDecoration2Id);
        Decoration waistDecoration3 = getDecorationById(context, waistDecoration3Id);

        long legsId = getLong(getColumnIndex(S.COLUMN_LEGS_ARMOR_ID));
        long legsDecoration1Id = getLong(getColumnIndex(S.COLUMN_LEGS_DECORATION_1_ID));
        long legsDecoration2Id = getLong(getColumnIndex(S.COLUMN_LEGS_DECORATION_2_ID));
        long legsDecoration3Id = getLong(getColumnIndex(S.COLUMN_LEGS_DECORATION_3_ID));
        Armor legsArmor = getArmorById(context, legsId);
        Decoration legsDecoration1 = getDecorationById(context, legsDecoration1Id);
        Decoration legsDecoration2 = getDecorationById(context, legsDecoration2Id);
        Decoration legsDecoration3 = getDecorationById(context, legsDecoration3Id);

        int talismanExists = getInt(getColumnIndex(S.COLUMN_TALISMAN_EXISTS));
        long talismanSkill1Id = getLong(getColumnIndex(S.COLUMN_TALISMAN_SKILL_1_ID));
        int talismanSkill1Points = getInt(getColumnIndex(S.COLUMN_TALISMAN_SKILL_1_POINTS));
        long talismanSkill2Id = getLong(getColumnIndex(S.COLUMN_TALISMAN_SKILL_2_ID));
        int talismanSkill2Points = getInt(getColumnIndex(S.COLUMN_TALISMAN_SKILL_2_POINTS));
        int talismanType = getInt(getColumnIndex(S.COLUMN_TALISMAN_TYPE));
        int talismanSlots = getInt(getColumnIndex(S.COLUMN_TALISMAN_SLOTS));
        long talismanDecoration1Id = getLong(getColumnIndex(S.COLUMN_TALISMAN_DECORATION_1_ID));
        long talismanDecoration2Id = getLong(getColumnIndex(S.COLUMN_TALISMAN_DECORATION_2_ID));
        long talismanDecoration3Id = getLong(getColumnIndex(S.COLUMN_TALISMAN_DECORATION_3_ID));
        Decoration talismanDecoration1 = getDecorationById(context, talismanDecoration1Id);
        Decoration talismanDecoration2 = getDecorationById(context, talismanDecoration2Id);
        Decoration talismanDecoration3 = getDecorationById(context, talismanDecoration3Id);

        if (headArmor != null) {
            session.setEquipment(ASBSession.HEAD, getArmorById(context, headId));
        }
        if (headDecoration1 != null) {
            session.addDecoration(ASBSession.HEAD, getDecorationById(context, headDecoration1Id));
        }
        if (headDecoration2 != null) {
            session.addDecoration(ASBSession.HEAD, getDecorationById(context, headDecoration2Id));
        }
        if (headDecoration3 != null) {
            session.addDecoration(ASBSession.HEAD, getDecorationById(context, headDecoration3Id));
        }

        if (bodyArmor != null) {
            session.setEquipment(ASBSession.BODY, getArmorById(context, bodyId));
        }
        if (bodyDecoration1 != null) {
            session.addDecoration(ASBSession.BODY, getDecorationById(context, bodyDecoration1Id));
        }
        if (bodyDecoration2 != null) {
            session.addDecoration(ASBSession.BODY, getDecorationById(context, bodyDecoration2Id));
        }
        if (bodyDecoration3 != null) {
            session.addDecoration(ASBSession.BODY, getDecorationById(context, bodyDecoration3Id));
        }

        if (armsArmor != null) {
            session.setEquipment(ASBSession.ARMS, getArmorById(context, armsId));
        }
        if (armsDecoration1 != null) {
            session.addDecoration(ASBSession.ARMS, getDecorationById(context, armsDecoration1Id));
        }
        if (armsDecoration2 != null) {
            session.addDecoration(ASBSession.ARMS, getDecorationById(context, armsDecoration2Id));
        }
        if (armsDecoration3 != null) {
            session.addDecoration(ASBSession.ARMS, getDecorationById(context, armsDecoration3Id));
        }

        if (waistArmor != null) {
            session.setEquipment(ASBSession.WAIST, getArmorById(context, waistId));
        }
        if (waistDecoration1 != null) {
            session.addDecoration(ASBSession.WAIST, getDecorationById(context, waistDecoration1Id));
        }
        if (waistDecoration2 != null) {
            session.addDecoration(ASBSession.WAIST, getDecorationById(context, waistDecoration2Id));
        }
        if (waistDecoration3 != null) {
            session.addDecoration(ASBSession.WAIST, getDecorationById(context, waistDecoration3Id));
        }

        if (legsArmor != null) {
            session.setEquipment(ASBSession.LEGS, getArmorById(context, legsId));
        }
        if (legsDecoration1 != null) {
            session.addDecoration(ASBSession.LEGS, getDecorationById(context, legsDecoration1Id));
        }
        if (legsDecoration2 != null) {
            session.addDecoration(ASBSession.LEGS, getDecorationById(context, legsDecoration2Id));
        }
        if (legsDecoration3 != null) {
            session.addDecoration(ASBSession.LEGS, getDecorationById(context, legsDecoration3Id));
        }

        if (talismanExists == 1) {
            ASBTalisman talisman = new ASBTalisman();
            talisman.setTypeIndex(talismanType);
            talisman.setNumSlots(talismanSlots);
            talisman.setSkill1(getSkillTreeById(context, talismanSkill1Id));
            talisman.setSkill1Points(talismanSkill1Points);

            if (talismanSkill2Id != -1) {
                talisman.setSkill2(getSkillTreeById(context, talismanSkill2Id));
                talisman.setSkill2Points(talismanSkill2Points);
            }

            if (talismanDecoration1 != null) {
                session.addDecoration(ASBSession.TALISMAN, getDecorationById(context, talismanDecoration1Id));
            }
            if (talismanDecoration2 != null) {
                session.addDecoration(ASBSession.TALISMAN, getDecorationById(context, talismanDecoration2Id));
            }
            if (talismanDecoration3 != null) {
                session.addDecoration(ASBSession.TALISMAN, getDecorationById(context, talismanDecoration3Id));
            }

            session.setEquipment(ASBSession.TALISMAN, talisman);
        }

        return session;
    }

    private static Armor getArmorById(Context context, long id) {
        if (id != 0 && id != -1) {
            return DataManager.get(context).getArmor(id);
        }
        else return null;
    }

    private static Decoration getDecorationById(Context context, long id) {
        if (id != 0 && id != -1) {
            return DataManager.get(context).getDecoration(id);
        }
        else return null;
    }

    private static SkillTree getSkillTreeById(Context context, long id) {
        if (id != 0 && id != -1) {
            return DataManager.get(context).getSkillTree(id);
        }
        else return null;
    }
}
