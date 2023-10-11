package com.company.Entitiy.Allied;

import com.company.EntitiesList;
import com.company.Entitiy.Enemy.Enemy;
import com.company.Entitiy.Entity;
import com.company.Input;
import com.company.PrintColor;

import java.util.ArrayList;
import java.util.EmptyStackException;

import static com.company.EntitiesList.EnList;
import static com.company.EntitiesList.Enemies_isAlive;

public class Saigyouji extends Soldier {
    private short Spring = 0;
    private ArrayList<Entity> Marks = new ArrayList<>();
    public boolean followUp = false;

    public Saigyouji() {
        setName("Saigyouji");
        tar_1 = true;
        tar_2 = false;
        cost_1 = 2;
        cost_2 = 3;
        hp = 3650;
        atk = 100;
        ap = 400;
        def = 450;
        res = 200;
        setMaxHealth(hp);
        setAtk(atk);
        setAp(ap);
        setDef(def);
        setRes(res);
        writeKit();
    }

    @Override
    public void writeKit() {
        talents = PrintColor.BPurple("Perfect Cherry Blossom") +  ": Casting skill gathers "
                + PrintColor.BPurple("Spring") + " from target to self. " + PrintColor.BPurple("Spring")
                + " can be consumed instead of mana to cast an enhanced version of unit's abilities.\n" + gap_T
                + PrintColor.BPurple("Graves of Beings") + ": Targets hit by this unit's attack and abilities are marked. "
                + "The next attack or ability triggers the mark, dealing "
                + PrintColor.Purple((getMaxHealth() * 15 / 100 + getAp()) + " (100% AP + 15% HP) magic damage") + " and gathers "
                + PrintColor.BPurple("15 Spring") + ".\n" + gap_T
                + PrintColor.BRed("Synchronized Patterns") + ": Normal attack deals "
                + PrintColor.Purple((getAtk() + getAp() + getMaxHealth() * 9 / 100) + " (100% ATK + 100% AP + 9% HP) magic damage")
                + ". Whenever an ally uses their normal attack, this unit follows-up with an attack, dealing "
                + PrintColor.Purple((getAtk() * 5 / 10 + getAp() * 5 / 10 + getMaxHealth() * 45 / 1000) + " (50% ATK + 50% AP + 4.5% HP) magic damage")
                + ". The attack cast this way will not grant mana to self, but instead heals self for "
                + PrintColor.Green((30 + getMaxHealth() * 15 / 1000) + " (30 + 1.5% HP) HP") + ".";
        s1_name = (Spring < 60)
                ? PrintColor.BPurple("Law of Mortality - Dead Butterflies")
                : (PrintColor.PURPLE_BOLD_BRIGHT + "Law of Mortality - Poisonous Moths" + PrintColor.def);
        s1_des =  (Spring < 60)
                ? ("Summon a spirit ring underneath an enemy, dealing "
                + PrintColor.Purple((500 + getAp() * 144 / 100) + " (500 + 144% AP) magic damage")
                + " and gathers " + PrintColor.BPurple("20 Spring") + ".\n" + gap_S1
                + PrintColor.Yellow("At 60 Spring or more, this ability will be replaced by ")
                + PrintColor.BPurple(" \"Law of Mortality - Poisonous Moths\"") + ".")

                : ("Summon a stronger spirit ring underneath an enemy, dealing "
                + PrintColor.Purple((825 + getAp() * 200 / 100 + getMaxHealth() * 20 / 100) + " (825 + 200% AP + 20% HP) magic damage")
                + " and gathers " + PrintColor.BPurple("20 Spring") + ". The target then becomes "
                + PrintColor.BPurple("Poisoned") + " in the next 3 turns, continuously taking " + PrintColor.Purple((220 + getAp() * 35 / 100 + getMaxHealth() * 7 / 100) + " (220 + 35% AP + 7% HP) magic damage")
                + " each turn.");

        s2_name = (Spring < 50)
                ? PrintColor.BPurple("Flowery Soul - Swallowtail Butterfly")
                : (PrintColor.PURPLE_BOLD_BRIGHT + "Flowery Soul - Butterfly Delusion" + PrintColor.def);
        s2_des = (Spring < 50)
                ? ("Charges for a spell that hits all enemies, dealing " + PrintColor.Purple((150 + getAp() * 8 / 10) + " (150 + 80% AP) magic damage")
                + ".\n" + gap_S2 + "Each enemy hit heals self for " + PrintColor.Green((35 + getMaxHealth() * 2 / 100) + " (35 + 2% HP) plus 30% damage dealt") + ".\n" + gap_S2
                + PrintColor.Yellow("At 50 Spring or more, this ability will be replaced by ")
                + PrintColor.BPurple("Flowery Soul - Butterfly Delusion") + ".")
                : ("Charges for a stronger spell that hits all enemies, dealing " + PrintColor.Purple((270 + getAp() * 12 / 10) + " (270 + 120% AP) magic damage")
                + ".\n" + gap_S2 + "Each enemy hit heals self for " + PrintColor.Green((70 + getMaxHealth() * 5 / 100) + " (70 + 5% HP) plus 60% damage dealt") + ".");
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
        Enemy tar;
        System.out.println(PrintColor.yellow + getName() + "'s turn!" + PrintColor.def);
        String options = "Choose an action " +
                "\n1. Normal attack " + PrintColor.cyan + "(+1 mana)" +
                (Spring >= 60 ? PrintColor.BPurple("\n2. Use upgraded skill 1 ") + PrintColor.Purple("(-60 Spring)")  :
                PrintColor.def + "\n2. Use skill 1 " + PrintColor.cyan + "(-" + cost_1 + " mana)")
                + PrintColor.def +
                (Spring >= 50 ? PrintColor.BPurple("\n3. Use upgraded skill 2 ") + PrintColor.Purple("(-50 Spring)")  :
                PrintColor.def + "\n3. Use skill 2 " + PrintColor.cyan + "(-" + cost_2 + " mana)")  + PrintColor.WHITE_BOLD +
                "\n4. Check this unit stats" +
                "\n5. Check an enemy stats" + PrintColor.def +
                "\n" + PrintColor.blue + "Current mana: " + mana + '\n' +
                PrintColor.BPurple("Current \"Spring\": " + Spring);
        System.out.println(options);
        short cnt = 1, enChoice;
        short choice = Input.Shrt("choice", (short) 1, (short) 5);
        switch (choice) {
            case 1:
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
                }
                this.normalAttack(tar);
                break;
            case 2:
                if (this.silence.inEffect()) {
                    System.out.println(PrintColor.Red("Silenced, can not cast skill!"));
                    action();
                    return 0;
                }
                else if (tar_1) {
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
                    } else if (!this.castSkill_1(tar)) {
                        action();
                        return 0;
                    }
                }
                else {
                    if (!this.castSkill_1(null)) {
                        action();
                        return 0;
                    }
                }
                break;
            case 3:
                if (this.silence.inEffect()) {
                    System.out.println(PrintColor.Red("Silenced, can not cast skill!"));
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
        if (this.taunt.inEffect() && this.taunt.getTaunter().isAlive()) target = taunt.getTaunter();
        int damage = followUp
                ? (getAp() * 5 / 10 + getAtk() * 5 / 10 + getMaxHealth() * 45 / 1000)
                : (getAp() + getAtk() + getMaxHealth() * 90 / 1000);
        if (!followUp) mana++;
        else healing(30 + getMaxHealth() * 15 / 1000);
        followUp = false;
        dealingDamage(target, damageOutput(0, damage, target));
        checkMark(target);
    }

    @Override
    public boolean castSkill_1(Entity target) {
        if (this.mana < cost_1 && Spring < 60) {
            System.out.println("Insufficient mana! Unable to cast!");
            return false;
        }
        else if (Spring < 60) this.mana -= cost_1;

        if (this.taunt.inEffect() && this.taunt.getTaunter().isAlive()) target = taunt.getTaunter();
        int damage = damageOutput(0, 500 + getAp() * 144 / 100, target);
        if (this.Spring >= 60) {
            Spring -= 60;
            damage = damageOutput(0, 825 + getAp() * 2 + getMaxHealth() * 20 / 100, target);
            target.poison.initialize((short) (220 + getAp() * 35 / 100 + getMaxHealth() * 7 / 100), (short) 3, this);
        }
        dealingDamage(target, damage, s1_name, PrintColor.Bpurple);
        checkMark(target);
        this.Spring += 20;
        return true;
    }

    @Override
    public boolean castSkill_2(Entity target) {
        if (this.mana < cost_2 && Spring < 50) {
            System.out.println("Insufficient mana! Unable to cast!");
            return false;
        }
        else if (Spring < 50) this.mana -= cost_2;

        if (this.taunt.inEffect() && this.taunt.getTaunter().isAlive()) target = taunt.getTaunter();

        int totalHealing = 0;
        int baseHealing = 35 + getMaxHealth() * 2 / 100;
        short ratio = 30;
        int baseDmg = 150 + getAp() * 8 / 10;
        if (this.Spring >= 50) {
            baseHealing = 70 + getMaxHealth() * 5 / 100;
            ratio = 60;
            baseDmg = 270 + getAp() * 12 / 10;
            Spring -= 50;
        }
        for (Enemy en : EntitiesList.EnList) {
            if (!en.isAlive()) continue;

            int damage = damageOutput(0, baseDmg, en);
            dealingDamage(en, damage, s2_name, PrintColor.purple);
            totalHealing += baseHealing + damage * ratio / 100;
            checkMark(en);
        }
        this.healing(totalHealing);
        return true;
    }

    private void checkMark(Entity target) {
        if (Marks.isEmpty() || !Marks.contains(target)) {
            System.out.println(PrintColor.BPurple(target.getName() + " marked!"));
            Marks.add(target);
        }
        else {
            System.out.print(PrintColor.Purple("Mark activates! "));
            dealingDamage(target, damageOutput(0, getAp() + getMaxHealth() * 15 / 100, target));
            this.Spring += 15;
            Marks.remove(target);
        }
    }
}
