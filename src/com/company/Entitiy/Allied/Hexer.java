package com.company.Entitiy.Allied;

import com.company.Entitiy.Entity;
import com.company.PrintColor;


public class Hexer extends Soldier {

    private Entity MarkedEn = null;
    private int MarkedHP = 0;

    public Hexer() {
        setName("Hexxer");
        tar_1 = true;
        tar_2 = true;
        cost_1 = 2;
        cost_2 = 2;
        hp = 2270;
        atk = 50;
        ap = 700;
        def = 200;
        res = 300;
        setMaxHealth(hp);
        setAtk(atk);
        setAp(ap);
        setDef(def);
        setRes(res);
        setResPen(respen);
        writeKit();
    }

    @Override
    public void writeKit() {
        talents = PrintColor.BPurple("Thresher") + ": Attacks deal " + PrintColor.Purple("additional magic damage equals to 15% of the target's current HP")
                    + ", up to a maximum of " + PrintColor.Purple((getAp() * 19 / 10) + " (190% AP) magic damage") + ".\n" + gap_T
                + PrintColor.BBlue("Rhythm of Victory") + ": Dealing damage to an enemy with less than " + PrintColor.Green("40% max HP")
                    + " inflicts " + PrintColor.Blue("20% 'Fragile'") + " on them, lasts 2 turns.";
        s1_name = PrintColor.BBlue("Harsh Deterrence");
        s1_des = "Strikes an enemy, dealing " + PrintColor.Purple((getAp() * 19 / 10) + " (190% AP) magic damage")
                + " and reduces their " + PrintColor.Yellow("DEF") + " and "+ PrintColor.Cyan("RES")
                + " by " + PrintColor.Yellow("20%") + " and " + PrintColor.Cyan("45%") + ", respectively, lasts until the end of this turn.";
        s2_name = PrintColor.BRed("Exorcise Evil");
        s2_des = "Casts a special spell on an enemy, which marks them then deals " + PrintColor.Purple(getAp() * 17 / 10 + " (170% AP) magic damage")
                + ".\n" + gap_S2 + "The mark remains on the target until they are hit by this spell again, during which it explodes, dealing " + PrintColor.Purple(getAp() * 13 / 10 + " (130% AP) magic damage")
                + " for every " + PrintColor.Green("1500 missing HP") + " the target has from when the mark begins.";
    }

    @Override
    public void normalAttack(Entity target) {
        int bonus = (int) Math.min(target.getHealth() * 0.15f, getAp() * 1.9f);
        super.normalAttack(target, damageOutput(getAtk(), getAp() + bonus, target));
        applyTalent(target);
    }

    @Override
    public boolean castSkill_1(Entity target) {
        if (!super.castSkill_1(target)) return false;
        int bonus = (int) Math.min(target.getHealth() * 0.15f, getAp() * 1.9f);
        target.defBuff.initialize((short) -5, (short) -10, (short) -20, (short) -45, (short) 2);
        applyTalent(target);
        dealingDamage(target, damageOutput(0, (int) (getAp() * 1.9 + bonus), target), s1_name, PrintColor.blue);
        return true;
    }

    @Override
    public boolean castSkill_2(Entity target) {
        if (!super.castSkill_2(target)) return false;
        int bonus = (int) Math.min(target.getHealth() * 0.15f, getAp() * 1.9f);
        if (MarkedEn != target) {
            MarkedEn = target;
            MarkedHP = target.getHealth();
            dealingDamage(target, damageOutput(0, (int) (getAp() * 1.7 + bonus), target), s2_name, PrintColor.purple);
            applyTalent(target);
        }
        else {
            dealingDamage(target, damageOutput(0, (int) (getAp() * 1.7 + bonus), target), s2_name, PrintColor.purple);
            applyTalent(target);
            int markDmg = (int) (getAp() * 1.3 * Math.max((MarkedHP - target.getHealth()) / 1500, 1));
            dealingDamage(MarkedEn, damageOutput(0, markDmg + bonus, target), PrintColor.BRed("Cleansing Flare"), PrintColor.Bpurple);
            MarkedEn = null;
        }
        return true;
    }

    public void applyTalent(Entity target) {
        if (target.getHealth() <= target.getMaxHealth() * 0.4) target.fragile.setValue((short) 20, (short) 3);
    }
}
