package com.company.Entitiy.Allied;

import com.company.EntitiesList;
import com.company.Entitiy.Enemy.Enemy;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Marksman extends Soldier {
    public short defaultTaunt;
    private short baseCrit, critDmg;
    private float atkScale = 0;
    private boolean s2_active = false;

    public Marksman() {
        setName("Marksman");
        shortDes = "High consistent damage, tears through even the toughest opponent!";
        tar_1 = true;
        tar_2 = true;
        cost_1 = 0;
        cost_2 = 3;
        critDmg = 170;
        baseCrit = 50;
        hp = 2150;
        atk = 630;
        ap = 0;
        defpen = 10;
        def = 240;
        res = 50;
        atkScale = 6.5f;
        setMaxHealth(hp);
        setAtk(atk);
        setDefPen(defpen);
        setDef(def);
        setRes(res);
        setLifesteal((short) 10);
        defaultTaunt = 1;
        setTauntLevel(defaultTaunt);
        writeKit();
    }

    @Override
    public void writeKit() {
        int min_tal2_dmg = (int) (getAtk() * atkScale / 100);
        int max_tal2_dmg = (int) (getAtk() * atkScale * 10 / 100);

        talents = PrintColor.BRed("Bullseye") + ": Attack has " + PrintColor.BRed(baseCrit + "% chance to crit")
                + ", dealing " + PrintColor.BRed((getAtk() * critDmg / 100) + " (" + critDmg + "% ATK) physic damage instead") + ".\n"
                // talent 2
                + gap_T + PrintColor.WHITE_BOLD + "Armor penetration Arrow" + PrintColor.def + ": Critical attack deals extra "
                + PrintColor.WHITE_BOLD + min_tal2_dmg + " (" + atkScale + "% ATK) true damage" + PrintColor.def
                + " for every " + PrintColor.Green((getMaxHealth() * 3 / 10) + " (30% HP) greater max health") + " the target has, up to an additional "
                + PrintColor.WHITE_BOLD + max_tal2_dmg + " (" + atkScale * 10 + "% ATK) true damage" + PrintColor.def + " at "
                + PrintColor.Green((getMaxHealth() * 3) + " (300% HP) greater max health")
                + ". (Least HP required: " + PrintColor.Green((getMaxHealth() * 13 / 10) + " HP")
                + ", max HP required: " + PrintColor.Green((getMaxHealth() * 4) + " HP") + ").\n"
                // talent 3
                + gap_T + PrintColor.BRed("Hunter") + ": Gains " + PrintColor.Red(getLifesteal() + "% lifesteal") + " and " + PrintColor.Red("10% DEF penetration")
                + ". Upon scoring a critical hit, " + PrintColor.Red("ATK +" + (getBaseAtk() * 3 / 10) + " (30% base ATK)")
                + ", lasts 2 turns.";
        s1_name = PrintColor.BBlue("Traceless");
        s1_des = PrintColor.red + "ATK +" + getAtk() / 5 + " (20% ATK)" + PrintColor.def + ", " + PrintColor.red + "lifesteal +30%" + PrintColor.def + ", " + PrintColor.yellow
                + "lowers the likelihood of being targeted this turn" + PrintColor.def + ".\n"
                + gap_S1 + "Every time this skill is activated, " + PrintColor.Blue("mana cost +1") + ", "
                + PrintColor.Red("lifesteal +5%") + ", " + PrintColor.Red("Crit chance +10%") + " and " + PrintColor.Red("Crit damage +15%")
                + PrintColor.def + ", stacks up to 3 times.\n" + gap_S1
                + "Upon reaching max stacks, self are strengthened. Gains these following effects:\n"
                + gap_S1 + "- " + PrintColor.Red("Crit chance") + " and " + PrintColor.BRed("Crit damage")
                + " further increased by " + PrintColor.Red("20%") + " and " + PrintColor.BRed("35%") + ", respectively.\n" + gap_S1
                + "- Increases " + PrintColor.Red("DEF penetration") + " by an additional " + PrintColor.Red("15%") + ".\n" + gap_S1
                + "- Moderately enhances " + PrintColor.Red("the ATK scaling") + " of " + PrintColor.BYellow("Armor penetration Arrow") + ".";
        s2_name = PrintColor.BRed("Kaffeeklatsch");
        s2_des = "Immediately launch an attack that guarantees to be critical, dealing " + PrintColor.red + ((getAtk() * (100 + baseCrit * 3 / 10) / 100) * critDmg / 100) + " ("
                + ((100 + baseCrit * 3 / 10) * critDmg / 100) + "% ATK [= (100 + 30% Crit chance) x (1% Crit damage)]) physical damage" + PrintColor.def + " that ignores " + PrintColor.red + (50 + defpen) + " (50 + 100% DEF penetration)% DEF"
                + PrintColor.def + " and amplifies damage from " + PrintColor.yellow + "'Armor penetration Arrow'" + PrintColor.def + " by " + PrintColor.WHITE_BOLD + (baseCrit * 4 / 10 + critDmg * 145 / 1000) + "% (40% Crit chance + 14.5% Crit damage)" + PrintColor.def
                + " to become " + PrintColor.WHITE_BOLD + (min_tal2_dmg * (100 + (baseCrit * 4 / 10 + critDmg * 145 / 1000))) / 100 + " - " + (max_tal2_dmg * (100 + (baseCrit * 4 / 10 + critDmg * 145 / 1000))) / 100 + PrintColor.def + ".";
    }

    @Override
    public void normalAttack(Entity target) {
        target = getTaunt(target);
        int damage;
        if (isCrit()) {
            talentTriggers();
            System.out.print(PrintColor.red + "CRITICAL STRIKE! ");
            damage = damageMod(damageOutput(getAtk() * critDmg / 100, ap, target), target);
        }
        else damage = damageOutput(getAtk(), getAp(), target);
        normalAttack(target, damage);
    }

    @Override
    public boolean castSkill_1(Entity target) {
        if (!super.castSkill_1(target)) return false;
        this.setTauntLevel((short) (this.getTauntLevel() - 2));
        final short bonusAD = (short) (getAtk() * 2 / 10);
        this.setLifesteal((short) (this.getLifesteal() + 30));
        this.addAtk(bonusAD);
        mana--;
        normalAttack(target);
        this.addAtk((short) (bonusAD * -1));
        this.setLifesteal((short) (this.getLifesteal() - 30));
        if (cost_1 < 3) {
            baseCrit += 10;
            critDmg += 15;
            setLifesteal((short) (getLifesteal() + 5));
            cost_1++;
            if (cost_1 == 3) {
                System.out.println(PrintColor.red + "Stacks maxed out! This unit's strength is significantly enhanced!" + PrintColor.def);
                baseCrit += 20;
                critDmg += 35;
                defpen += 15;
                addDefPen((short)15);
                atkScale += 4.5f;
            }
        }
        return true;
    }

    @Override
    public boolean castSkill_2(Entity target) {
        if (!super.castSkill_2(target)) return false;
        talentTriggers();
        addDefPen((short) 50);
        s2_active = true;
        int damage = damageMod(damageOutput((getAtk() * (100 + (baseCrit * 3 / 10)) / 100) * critDmg / 100, ap, target), target);
        System.out.print(PrintColor.red + "CRITICAL STRIKE! ");
        dealingDamage(target, damage, s2_name, PrintColor.Bred);
        addDefPen((short) -50);
        return true;
    }

    private int damageMod(int damage, Entity target) {
        short hpDiff = (short) ((target.getMaxHealth() - this.getMaxHealth()) * 100 / this.getMaxHealth());
        short multiplier = (short) Math.max(Math.min(hpDiff / 30, 10), 0);
        short damagePlus = (short) (getAtk() * atkScale * multiplier / 100);
        if (s2_active) damagePlus = (short) (damagePlus * (100 + baseCrit * 0.4 + critDmg * 0.145) / 100);
        s2_active = false;
        return damage + damageOutput(0,0,damagePlus, target);
    }

    public void talentTriggers() {
        this.atkBuff.initialize((short) 0, (short) 0, (short) 30, (short) 0, (short) 2);
    }

    public boolean isCrit() {
        return Math.floor(Math.random() * 100 + 1) <= baseCrit;
    }
}
