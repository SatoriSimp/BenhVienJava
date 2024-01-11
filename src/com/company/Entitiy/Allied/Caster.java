package com.company.Entitiy.Allied;

import com.company.EntitiesList;
import com.company.Entitiy.Enemy.Enemy;
import com.company.Entitiy.Entity;
import com.company.Input;
import com.company.PrintColor;

import static com.company.EntitiesList.EnList;
import static com.company.EntitiesList.Enemies_isAlive;

public class Caster extends Soldier {
    private final short charge_max = 3;
    private short charged = 0;
    private short resPenAdd = 0;
    public static Entity Vulnerable = null;

    public Caster() {
        setName("Caster");
        shortDes = "Delivers a burst of art damage, either ST or AoE";
        tar_1 = true;
        tar_2 = false;
        cost_1 = 2;
        cost_2 = 4;
        hp = 2400;
        atk = 70;
        ap = 1050;
        respen = 15;
        def = 250;
        res = 200;
        mana += 3;
        charged += 2;
        setMaxHealth(hp);
        setAtk(atk);
        setAp(ap);
        setDef(def);
        setRes(res);
        setResPen(respen);
        writeKit();
    }

    public void writeKit() {
        addResIgn((short) (resPenAdd * -1));
        resPenAdd = (short) (charged * 70);
        addResIgn(resPenAdd);
        talents = PrintColor.BPurple("Sealed Treasure") + ": Attack can be stored to fire up in a burst (up to " + charge_max + "), " +
                "a fully charged attack amplifies the damage by 45%, dealing " + PrintColor.red + (getAtk() * (1 + charge_max)) * 145 / 100  + " (580% ATK) physic damage" + PrintColor.def + " plus " + PrintColor.purple + getAp() * (1 + charge_max) * 145 / 100 + " (580% AP) magic damage" + PrintColor.def + ".\n" + gap_T +
                PrintColor.BPurple("Dynamic") + ": For every charged attack in possession, " + PrintColor.Purple("RES ignore +70") +
                ". When attacking " + PrintColor.Red("Elite enemies") + " with fully charged attack, " + PrintColor.Purple("number of charged attack +1") + ".\n" + gap_T +
                PrintColor.BCyan("Quick Charge") + ": At the start of the battle, " + PrintColor.Blue("initial mana +3") + " and obtains 2 charged attacks.";
        s1_name = PrintColor.BYellow("Fire Beacon");
        s1_des = "Strikes a target, dealing " + PrintColor.purple + (325 + getAp() * 5 / 3) + " (325 + 167% AP) magic damage" + PrintColor.def + " and immediately obtains" +
                " 1 charged attack. If the target has " + PrintColor.green + "below 20% HP" + PrintColor.def + ", the damage becomes " + PrintColor.purple +
                (568 + getAp() * 291 / 100) + " (568 + 291% AP) magic damage" + PrintColor.def + ". Otherwise, " +
                "they will then be marked as " + PrintColor.yellow + "'Vulnerable'" + PrintColor.def + ", causing" +
                " the next source of damage they have to receive from this unit being enhanced by " + PrintColor.purple + ((200 + getAp() * 4 / 5) * (1 + charged))
                + " (" + 200 * (1 + charged) + " + " + 80 * (1 + charged) + "% AP [= 200 + 80% AP per 1 charged attack]) magic damage"
                + PrintColor.def + ", up to " + PrintColor.purple + (200 + getAp() * 4 / 5) * (1 + charge_max) + " (800 + 320% AP) magic damage" + PrintColor.def + " and forfeits the mark";
        s2_name = PrintColor.BPurple("Meteorite");
        s2_des = "Casts a spell that consumes all charged attacks to attack all enemies, " +
                "dealing " + PrintColor.purple + (300 + getAp() * 12 / 10) + " (300 + 120% AP) magic damage" + PrintColor.def + ", each charged attack " +
                "enhances the spell damage by " + PrintColor.purple + (80 + getAp() * 6 / 10) + " (80 + 60% AP) magic damage" + PrintColor.def + ", spell " +
                "applies talent's effect to deal up to " + PrintColor.purple + ((300 + getAp() * 12 / 10 + getAp() * 6 / 10 * charge_max) * 145 / 100)
                + " (" + ((300 + 80 * charge_max) * 145 / 100) + " + " + ((120 + 60 * charge_max) * 145 / 100) + "% AP) magic damage" + PrintColor.def + ".";
    }

    @Override
    public short action() {
        boolean allLive = false;
        for (Enemy e : EnList) {
            if (e.getHealth() > 0) {
                allLive = true;
                break;
            }
        }
        if (!allLive) return 0;
        String spec = PrintColor.yellow + "    Charged attack: " + charged + (charged == charge_max ? " (MAXED OUT)" : "");
        Enemy tar;
        System.out.println(PrintColor.yellow + getName() + "'s turn!" + PrintColor.def);
        String options = "Choose an action " +
                "\n1. Normal attack " + PrintColor.cyan + "(+1 mana)" + spec + PrintColor.def +
                "\n2. Use skill 1 " + PrintColor.cyan + "(-" + cost_1 + " mana)" + PrintColor.def +
                "\n3. Use skill 2 " + PrintColor.cyan + "(-" + cost_2 + " mana)" + PrintColor.WHITE_BOLD +
                "\n4. Check this unit stats" +
                "\n5. Check an enemy stats" + PrintColor.def +
                "\n" + PrintColor.blue + "Current mana: " + mana + PrintColor.def;
        System.out.println(options);
        short cnt = 1, enChoice;
        short choice = Input.Shrt("choice", (short) 1, (short) 5);
        switch (choice) {
            case 1:
                System.out.println("Pick a target:");
                for (Enemy t : EnList) {
                    if (t.isAlive()) System.out.println(cnt + ". " + t.getName() + PrintColor.Red(" [" + t.getHealth() * 100 / t.getMaxHealth() + "%]") + PrintColor.Blue(t.isInvisible ? "    [Invisible]" : ""));
                    ++cnt;
                }
                if (charged < charge_max) System.out.println(PrintColor.yellow + cnt + ". Charges this attack");
                enChoice = Input.Shrt("choice", (short) 1, charged < charge_max ? cnt : (short) (cnt - 1));
                if (enChoice == cnt) {
                    ++mana;
                    chargesAttack();
                }
                else {
                    tar = EnList.get(enChoice - 1);
                    if (!tar.isAlive()) {
                        System.out.println(PrintColor.Red("This target is already gone! Try choosing another one instead!"));
                        action();
                        return 0;
                    }
                    else if (tar.isInvisible) {
                        System.out.println(PrintColor.Yellow("This target can not be targeted! Try choosing another one instead!"));
                        action();
                        return 0;
                    }
                    this.normalAttack(tar);
                }
                break;
            case 2:
                if (this.silence.inEffect()) {
                    System.out.println(PrintColor.Red("Silenced! Can not use skill!"));
                    action();
                    return 0;
                }
                else if (tar_1) {
                    System.out.println("Pick a target:");
                    for (Enemy t : EnList) {
                        if (t.isAlive()) System.out.println(cnt + ". " + t.getName() + PrintColor.Red(" [" + t.getHealth() * 100 / t.getMaxHealth() + "%]") + PrintColor.Blue(t.isInvisible ? "    [Invisible]" : ""));
                        ++cnt;
                    }
                    enChoice = Input.Shrt("choice", (short) 1, (short) (cnt - 1));
                    tar = EnList.get(enChoice - 1);
                    if (!tar.isAlive()) {
                        System.out.println(PrintColor.Red("This target is already gone! Try choosing another one instead!"));
                        action();
                        return 0;
                    }
                    else if (tar.isInvisible) {
                        System.out.println(PrintColor.Yellow("This target can not be targeted! Try choosing another one instead!"));
                        action();
                        return 0;
                    }
                    else if (!this.castSkill_1(tar)) {
                        action();
                        return 0;
                    }
                }
                else if (!castSkill_1(null)) {
                    action();
                    return 0;
                }
                break;
            case 3:
                if (this.silence.inEffect()) {
                    System.out.println(PrintColor.Red("Silenced! Can not use skill!"));
                    action();
                    return 0;
                }
                else if (tar_2) {
                    System.out.println("Pick a target:");
                    for (Enemy t : EnList) {
                        if (t.isAlive()) System.out.println(cnt + ". " + t.getName());
                        ++cnt;
                    }
                    enChoice = Input.Shrt("choice", (short) 1, (short) (cnt - 1));
                    tar = EnList.get(enChoice - 1);
                    if (!tar.isAlive()) {
                        System.out.println("This target is already gone! Try choosing another one instead!");
                        action();
                        return 0;
                    } else if (!this.castSkill_2(tar)) {
                        action();
                        return 0;
                    }
                } else {
                    if (!this.castSkill_2(null)) {
                        action();
                        return 0;
                    }
                }
                break;
            case 4:
                this.printInfo();
                action();
                break;
            case 5:
                System.out.println("Pick a target:");
                for (Enemy t : EnList) {
                    System.out.println(cnt + ". " + t.getName());
                    ++cnt;
                }
                enChoice = Input.Shrt("choice", (short) 1, (short) (cnt - 1));
                EnList.get(enChoice - 1).printInfo();
                action();
                break;
        }
        return choice;
    }

    @Override
    public void normalAttack(Entity target) {
        int damage = damageOutput(getAtk(), getAp(), target) * (1 + charged);
        boolean addCharge = ((Enemy)target).isElite && charged == charge_max;
        if (charged == charge_max) {
            if (addCharge) {
                addResIgn((short) 70);
                charged++;
            }
            damage = damageOutput(getAtk(), getAp(), target) * (1 + charged) * 145 / 100;
        }
        normalAttack(target, damage);
        if (Vulnerable == target) triggersMark();
        if (addCharge) addResIgn((short) -70);
        charged = 0;
    }

    @Override
    public boolean castSkill_1(Entity target) {
        if (!super.castSkill_1(target)) return false;
        chargesAttack();
        int spellDmg = damageOutput(0, 325 + getAp() * 167 / 100, target);
        if (target.getHealth() < target.getMaxHealth() / 5) spellDmg = spellDmg * 175 / 100;
        else {
            System.out.println(PrintColor.yellow + target.getName() + " has been marked!" + PrintColor.def);
            Vulnerable = target;
        }
        dealingDamage(target, spellDmg, s1_name, PrintColor.Bpurple);
        return true;
    }

    @Override
    public boolean castSkill_2(Entity target) {
        if (!super.castSkill_2(target)) return false;
        int rawDmg = 300 + getAp() * 12 / 10 + (80 + getAp() * 6 / 10) * charged;
        if (charged == charge_max) {
            rawDmg = rawDmg * 145 / 100;
        }

        for (Enemy en : EnList) {
            if (en.isAlive()) {
                boolean isElite = (charged == charge_max && en.isElite);
                int spellDmg = rawDmg;
                if (isElite) {
                    addResIgn((short) 70);
                    spellDmg += (80 + getAp() * 6 / 10) * 145 / 100;
                }
                dealingDamage(en, damageOutput(0, spellDmg, en), s2_name, PrintColor.purple);
                if (en == Vulnerable) triggersMark();
                if (isElite) addResIgn((short) -70);
            }
        }

        charged = 0;
        return true;
    }

    public void chargesAttack() {
        if (charged < charge_max) {
            System.out.println(PrintColor.yellow + getName() + " charges this attack!");
            charged++;
        }
    }

    public void triggersMark() {
        int damage = (200 + getAp() * 4 / 5) * (1 + charged);
        System.out.print(PrintColor.purple + "Mark triggers! " + PrintColor.def);
        dealingDamage(Vulnerable, damageOutput(0, damage, Vulnerable), "Vulnerable", PrintColor.purple);
        Vulnerable = null;
    }
}
