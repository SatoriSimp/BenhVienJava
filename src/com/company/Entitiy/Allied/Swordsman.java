package com.company.Entitiy.Allied;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Swordsman extends Soldier {
    private int recovery;
    private short atkBuff = 0, atkAdd = -1;

    public Swordsman() {
        setName("Swordsman");
        shortDes = "Self-sustain, becomes stronger as they lose more HP.";
        tar_1 = true;
        tar_2 = true;
        cost_1 = 2;
        cost_2 = 2;
        hp = 4000;
        atk = 750;
        ap = 0;
        def = 0;
        res = 0;
        recovery = 225;
        setMaxHealth(hp);
        setAtk(atk);
        setDef(def);
        setRes(res);
        writeKit();
    }

    @Override
    public void writeKit() {
        atkBuff = (short) (getBaseAtk() * 150 / 10000);
        String bonus = String.format("%.1f", recovery * 0.02f);
        talents = PrintColor.BGreen("Sabreur") + ": Attack recovers " + PrintColor.green + (getMissingHealth() * 100 / getMaxHealth() * recovery / 50 + recovery) + " HP" + PrintColor.def +
                ", with a base recovery of " + PrintColor.green + recovery + " HP" + PrintColor.def + " that is increased by " + PrintColor.green
                + bonus + " (2% amount)" + PrintColor.def + " for every " + PrintColor.green + getMaxHealth() / 100 + " (1% HP) missing health" + PrintColor.def
                + ", up to the max of " + PrintColor.green + recovery * 3 + " HP" + PrintColor.def + ".\n" + gap_T
                + PrintColor.BRed("Yataghan") + ": Gains " + PrintColor.Red("+" + atkBuff + " (1.5% base ATK) ATK") + " for every " + PrintColor.green
                + getMaxHealth() / 100 + " (1% HP) missing health" + PrintColor.def
                + ", up to the maximum of " + PrintColor.red + "+" + getBaseAtk() + " (100% base ATK) ATK" + PrintColor.def + " when having below "
                + PrintColor.green + getMaxHealth() / 3 + " (33% HP) HP" + PrintColor.def + ".";
        s1_name = PrintColor.BBlue("Coup de Grace");
        s1_des = "Immediately loses " + PrintColor.red + getHealth() * 65 / 100 + " (65% current HP) HP" + PrintColor.def + ", then strikes the target, dealing "
                + PrintColor.red  + getAtk() * 185 / 100 + " (185% ATK) physic damage" + PrintColor.def + " plus "
                + PrintColor.purple + getAtk() * 185 / 100 + " (185% ATK) magic damage" + PrintColor.def + " to them. When the skill ends, obtains " + PrintColor.green + "1 layer of 'Shield'" + PrintColor.def + ".";
        s2_name = PrintColor.BRed("Five Signs of a Dying Deva");
        s2_des = PrintColor.WHITE_BOLD + "This skill can only be used when HP falls below 40%\n" + PrintColor.def + gap_S2
                + "Lock-on a target then rapidly strikes them 4 times, dealing "
                + PrintColor.red + getAtk() * 60 / 100 + " (60% ATK) physic damage"
                + PrintColor.def + " each strike.\n" + gap_S2 + "Afterwards, perform an special attack that deals " + PrintColor.red
                + getAtk() * 1.40f + " (140% ATK)" + PrintColor.def + " plus "
                + PrintColor.red + (getAtk() * 5 / 100) + " (5% ATK) physic damage " + PrintColor.def + "for every " + PrintColor.red
                + "1% of target's missing health" + PrintColor.def + ".\n" + gap_S2
                + "Maximum damage this attack can achieve is " + PrintColor.red
                + (getAtk() * 140 / 100 + getAtk() * 50 * 70 / 1000) + " (" + (140 + 5 * 70) + "% ATK) physic damage"
                + PrintColor.def + " when the target has below "
                + PrintColor.red + "30% HP"  + PrintColor.def + ".\n"
                + gap_S2 + "During the skill duration, disables "
                + PrintColor.Red("the ATK gains/loss") + " from " + PrintColor.BYellow("\"Yataghan\"") + ".";
    }

    @Override
    public void normalAttack(Entity target) {
        if (isDisarmed) return;
        target = getTaunt(target);
        super.normalAttack(target);
        triggersTalent();
    }

    @Override
    public boolean castSkill_1(Entity target) {
        if (!super.castSkill_1(target)) return false;
        int healthLoss = (int) (getHealth() * 0.65f);
        System.out.println(PrintColor.red + getName() + " loses " + healthLoss + " HP!" + PrintColor.def);
        this.rotting(healthLoss);
        addAtk((short) (atkAdd * -1));
        atkAdd = (short) (atkBuff * Math.min(getMissingHealth() * 100 / getMaxHealth(), 50));
        addAtk(atkAdd);

        int scale = getAtk() * 185 / 100;
        int damage = damageOutput(scale, scale, target);
        dealingDamage(target, damage);
        this.setShield((short) 1);
        triggersTalent();
        return true;
    }

    @Override
    public boolean castSkill_2(Entity target) {
        if (!super.castSkill_2(target)) {
            return false;
        }
        else if (s2_locked()) {
            System.out.println(PrintColor.red + "Spell is being locked! Unable to cast." + PrintColor.def);
            mana += cost_2;
            return false;
        }
        final byte max_times = 5;
        short scale = (short) (getAtk() * 60 / 100);
        for (byte i = 1; i <= max_times; i++) {
            target.update();
            if (i == max_times) {
                short pMissing = (short) Math.min(target.getMissingHealth() * 100 / target.getMaxHealth(), 70);
                scale = (short) (getAtk() * 1.4 + getAtk() * 5 * pMissing / 100);
            }
            dealingDamage(target, damageOutput(scale, 0, target));
            triggersTalent();
        }
        return true;
    }

    @Override
    public void update() {
        addAtk((short) (atkAdd * -1));
        atkAdd = (short) (atkBuff * Math.min(getMissingHealth() * 100 / getMaxHealth(), 67));
        addAtk(atkAdd);
    }

    private void triggersTalent() {
        int healing = getMissingHealth() * 100 / getMaxHealth() * recovery / 50 + recovery;
        this.healing(healing);
    }

    public boolean s2_locked() {
        return !(getHealth() <= getMaxHealth() * 0.4f);
    }
}
