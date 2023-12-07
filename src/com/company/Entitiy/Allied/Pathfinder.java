package com.company.Entitiy.Allied;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Pathfinder extends Soldier {
    private int damageAdd;
    private Entity marked;

    private boolean defenseState = false;
    private short attackCount = 0;
    private int[] storeStat;

    public Pathfinder() {
        setName("Pathfinder");
        shortDes = "Adapt to different combat situation";
        tar_1 = true;
        tar_2 = false;
        cost_1 = 2;
        cost_2 = 3;
        hp = 4100;
        atk = 100;
        def = 600;
        res = 500;
        mana = 5;
        setMaxHealth(hp);
        setAtk(atk);
        setDef(def);
        setRes(res);
        setShield((short) 2);
        writeKit();
    }

    @Override
    public void writeKit() {
        damageAdd = (int) (getAtk() * 0.75f);

        talents = PrintColor.BGreen("Rules of Survival") + ": Starts off with "
                + PrintColor.Green("2 layers of 'Shield'") + ", and every 4th attack additionally generates " + PrintColor.Green("1 'Shield'")
                + ". When a 'Shield' breaks, restores " + PrintColor.Green((100 + getMaxHealth() / 5) + " (100 + 20% max health) HP") + ".\n" + gap_T
                + PrintColor.BRed("Ward of the Fertile Soil") + ": Attack and abilities deal an additional of " + PrintColor.Red(damageAdd + " (75% ATK) physic damage");
        s1_name = PrintColor.BRed("Crag Splitter");
        s1_des = "Attack a target, dealing " + PrintColor.Red((getAtk() * 195 / 100) + " (195% ATK) physic damage")
                + " and " + PrintColor.Blue("reduces their DEF by 15%")
                + " within the next turn. The next normal attack of self lands on that target deals "
                + PrintColor.Red("120% total damage")
                + " and further " + PrintColor.Red("ignore 57% of their DEF") + ".";
        s2_name = PrintColor.BBlue("All-out Formation");
        s2_des = PrintColor.White("(Can switch between this state and default state)\n") + gap_S2
                +
                (defenseState ?
                ("Switch back to normal stage, reverts all loss and gains stats.\n" + gap_S2) :
                ("Switch to fortress state, gaining "
                + PrintColor.Red((getMaxHealth() * 200 / 1000) + " (20% max HP) ATK") + ", "
                + PrintColor.BRed(((getBaseDef() * 225 / 10000 + getBaseRes() * 23 / 1000)) + " (2.25% base DEF + 2.3% base RES) life-steal")
                + " and attack turns into double strike. However, loses " + PrintColor.Green("45% max HP") + ", "
                + PrintColor.Yellow("85% DEF") + " and "
                + PrintColor.Cyan("85% RES") + ".\n" + gap_S2))
                + "Upon switching to from default to fortress state and vice versa, gains " + PrintColor.Green("1 'Shield'") + ".";
    }

    @Override
    public void normalAttack(Entity target) {
        short times = (short) ((defenseState) ? 2 : 1);
        boolean isMarked = (target == marked && target.defBuff.inEffect());

        for (short i = 1; i <= times; ++i) {
            int damage = damageOutput(getAtk() + damageAdd, getAp(), target);
            if (isMarked) {
                addDefPen((short) 57);
                damage = (int) (damageOutput(getAtk() + damageAdd, getAp(), target) * 1.2);
            }
            super.normalAttack(target, damage);
            if (isMarked) {
                addDefPen((short) -57);
                marked = null;
            }
            if (attackCount < 3) attackCount++;
            else {
                attackCount = 0;
                System.out.println(PrintColor.Green(getName() + " gains 1 'Shield'!"));
                setShield((short) (getShield() + 1));
            }
        }
    }

    @Override
    public boolean castSkill_1(Entity target) {
        if (!super.castSkill_1(target)) return false;
        marked = target;
        int skillDmg = damageOutput((int) (getAtk() * 1.95F + damageAdd), 0, target);
        target.defBuff.initialize((short) 0, (short) 0, (short) -15, (short) 0, (short) 2);
        dealingDamage(target, skillDmg, s1_name, PrintColor.red);
        return true;
    }

    @Override
    public boolean castSkill_2(Entity target) {
        if (!super.castSkill_2(target)) return false;
        defenseState = !defenseState;

        if (defenseState) {
            System.out.println(PrintColor.Yellow("Switched to offensive state!"));
            while(defBuff.inEffect()) defBuff.fadeout();
            storeStat = new int[]{getBaseAtk(), getBaseDef(), getBaseRes(), getMaxHealth()};
            setAtk((short) (getBaseAtk() + getMaxHealth() * 200 / 1000));
            setLifesteal((short) (getBaseDef() * 0.0225 + getBaseRes() * 0.023));
            setDef((short) (getBaseDef() * 0.15));
            setRes((short) (getBaseRes() * 0.15));
            setHealth((int) (getMaxHealth() * 0.55));
        }
        else {
            System.out.println("Switched to default state!");
            while(defBuff.inEffect()) defBuff.fadeout();
            setAtk((short) storeStat[0]);
            setDef((short) storeStat[1]);
            setRes((short) storeStat[2]);
            setHealth(storeStat[3]);
            setLifesteal((short) 0);
        }
        setShield((short) (getShield() + 1));
        return true;
    }
}
