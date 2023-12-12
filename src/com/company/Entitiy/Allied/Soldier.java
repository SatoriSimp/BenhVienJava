package com.company.Entitiy.Allied;

import com.company.EntitiesList;
import com.company.Entitiy.Enemy.Enemy;
import com.company.Entitiy.Entity;
import com.company.Input;
import com.company.PrintColor;

import static com.company.EntitiesList.EnList;
import static com.company.EntitiesList.Enemies_isAlive;

abstract public class Soldier extends Entity implements PlayerBehaviors {
    final String gap_T = "          ", gap_S1 = "               ", gap_S2 = "                ";
    public short mana = 3;
    public short cost_1, cost_2;
    boolean tar_1 = true, tar_2 = true;
    String talents, s1_name, s1_des, s2_name, s2_des;
    int hp;
    short atk, ap, def, res, defpen = 0, respen = 0, defig = 0, resig = 0, lifesteal = 0, reduc = 0;

    public void addMana(short amount) {
        this.mana += amount;
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

        if (this.stun.inEffect()) {
            this.stun.fadeout();
            return 0;
        }

        Enemy tar;
        String lockedSpell = "", notReached = "";
        if (this.silence.inEffect()) {
            lockedSpell = PrintColor.red + " (Locked)" + PrintColor.def;
        }
        else if (this instanceof Swordsman && this.getMissingHealth() < this.getMaxHealth() / 2) {
            notReached = PrintColor.Red(" (Not available)");
        }
        System.out.println(PrintColor.yellow + getName() + "'s turn!" + PrintColor.def);
        String options = "Choose an action " +
                "\n1. Normal attack " + PrintColor.cyan + "(+1 mana)" + PrintColor.def +
                "\n2. Use skill 1 " + PrintColor.cyan + "(-" + cost_1 + " mana)" + lockedSpell + PrintColor.def +
                "\n3. Use skill 2 " + PrintColor.cyan + "(-" + cost_2 + " mana)" + lockedSpell + notReached + PrintColor.WHITE_BOLD +
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
                    if (t.isAlive()) System.out.println(cnt + ". " + t.getName() + PrintColor.Blue(t.isInvisible ? "    [Invisible]" : ""));
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
                this.normalAttack(tar);
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
                        if (t.isAlive()) System.out.println(cnt + ". " + t.getName() + PrintColor.Blue(t.isInvisible ? "    [Invisible]" : ""));
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
                        if (t.isAlive()) System.out.println(cnt + ". " + t.getName() + PrintColor.Blue(t.isInvisible ? "    [Invisible]" : ""));
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
                    else if (!this.castSkill_2(tar)) {
                        action();
                        return 0;
                    }
                }
                else if (!this.castSkill_2(null)) {
                    action();
                    return 0;
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
    public void printInfo() {
        writeKit();
        System.out.println("Role: " + PrintColor.BYellow(getName()));
        super.printInfo();
        System.out.println(PrintColor.YELLOW_BOLD_BRIGHT + "\nTalent >> " + PrintColor.def + talents
                + PrintColor.YELLOW_BOLD_BRIGHT + "\nFirst skill >> " + PrintColor.def + s1_name + PrintColor.CYAN_BRIGHT + " (" + cost_1 + " mana)" + PrintColor.def + ": " + s1_des
                + PrintColor.YELLOW_BOLD_BRIGHT + "\nSecond skill >> " + PrintColor.def + s2_name + PrintColor.CYAN_BRIGHT + " (" + cost_2 + " mana)" + PrintColor.def + ": " + s2_des + '\n');
    }

    @Override
    public void normalAttack(Entity target) {
        normalAttack(target, damageOutput(getAtk(), getAp(), target));
    }

    @Override
    public void normalAttack(Entity target, int damage) {
        ++mana;
        super.normalAttack(target, damage);
        if (target instanceof Enemy) {
            EntitiesList.SoList.forEach(so -> {
                if (so instanceof Saigyouji && so != this && so.isAlive()) {
                    ((Saigyouji) so).followUp = true;
                    so.normalAttack(target);
                }
            });
        }
    }

    @Override
    public boolean castSkill_1(Entity target) {
        if (this.mana < cost_1) {
            System.out.println("Insufficient mana! Unable to cast!");
            return false;
        }
        this.mana -= cost_1;
        System.out.println(getName() + " used their first skill!");
        return true;
    }

    @Override
    public boolean castSkill_2(Entity target) {
        if (this.mana < cost_2) {
            System.out.println("Insufficient mana! Unable to cast!");
            return false;
        }
        this.mana -= cost_2;
        System.out.println(getName() + " used their second skill!");
        return true;
    }
}
