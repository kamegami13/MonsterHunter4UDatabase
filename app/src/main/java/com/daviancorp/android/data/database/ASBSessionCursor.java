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
            session.setEquipment(ASBSession.HEAD, headArmor, false);
        }
        if (headDecoration1 != null) {
            session.addDecoration(ASBSession.HEAD, headDecoration1, false);
        }
        if (headDecoration2 != null) {
            session.addDecoration(ASBSession.HEAD, headDecoration2, false);
        }
        if (headDecoration3 != null) {
            session.addDecoration(ASBSession.HEAD, headDecoration3, false);
        }

        if (bodyArmor != null) {
            session.setEquipment(ASBSession.BODY, bodyArmor, false);
        }
        if (bodyDecoration1 != null) {
            session.addDecoration(ASBSession.BODY, bodyDecoration1, false);
        }
        if (bodyDecoration2 != null) {
            session.addDecoration(ASBSession.BODY, bodyDecoration2, false);
        }
        if (bodyDecoration3 != null) {
            session.addDecoration(ASBSession.BODY, bodyDecoration3, false);
        }

        if (armsArmor != null) {
            session.setEquipment(ASBSession.ARMS, armsArmor, false);
        }
        if (armsDecoration1 != null) {
            session.addDecoration(ASBSession.ARMS, armsDecoration1, false);
        }
        if (armsDecoration2 != null) {
            session.addDecoration(ASBSession.ARMS, armsDecoration2, false);
        }
        if (armsDecoration3 != null) {
            session.addDecoration(ASBSession.ARMS, armsDecoration3, false);
        }

        if (waistArmor != null) {
            session.setEquipment(ASBSession.WAIST, waistArmor, false);
        }
        if (waistDecoration1 != null) {
            session.addDecoration(ASBSession.WAIST, waistDecoration1, false);
        }
        if (waistDecoration2 != null) {
            session.addDecoration(ASBSession.WAIST, waistDecoration2, false);
        }
        if (waistDecoration3 != null) {
            session.addDecoration(ASBSession.WAIST, waistDecoration3, false);
        }

        if (legsArmor != null) {
            session.setEquipment(ASBSession.LEGS, legsArmor, false);
        }
        if (legsDecoration1 != null) {
            session.addDecoration(ASBSession.LEGS, legsDecoration1, false);
        }
        if (legsDecoration2 != null) {
            session.addDecoration(ASBSession.LEGS, legsDecoration2, false);
        }
        if (legsDecoration3 != null) {
            session.addDecoration(ASBSession.LEGS, legsDecoration3, false);
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
                session.addDecoration(ASBSession.TALISMAN, talismanDecoration1);
            }
            if (talismanDecoration2 != null) {
                session.addDecoration(ASBSession.TALISMAN, talismanDecoration2);
            }
            if (talismanDecoration3 != null) {
                session.addDecoration(ASBSession.TALISMAN, talismanDecoration2);
            }

            session.setEquipment(ASBSession.TALISMAN, talisman, false);
        }

        session.updateSkillTreePointsSets();

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
