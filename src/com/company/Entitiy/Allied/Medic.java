package com.company.Entitiy.Allied;

import com.company.EntitiesList;
import com.company.Entitiy.Enemy.Enemy;
import com.company.Entitiy.Entity;
import com.company.Input;
import com.company.PrintColor;

import static com.company.EntitiesList.*;

public class Medic extends Soldier {
    private short clarity = 0;

    public Medic() {
        setName("Medic");
        shortDes = "Healing and providing other necessary stuffs";
        tar_1 = true;
        tar_2 = true;
        cost_1 = 2;
        cost_2 = 5;
        hp = 1700;
        atk = 125;
        ap = 450;
        def = 150;
        res = 150;
        setTauntLevel((short) 0);
        setMaxHealth(hp);
        setAtk(atk);
        setAp(ap);
        setDef(def);
        setRes(res);
        writeKit();
    }

    @Override
    public void writeKit() {
        talents = PrintColor.BGreen("Battlefield Medic") + ": Normal attack can only target allies, and instead of attacking, heals the target for "
                + PrintColor.Green(getAp() + " (100% AP) HP") + ", increased to " + PrintColor.green + getAp() * 15 / 10 + " (150% AP) HP"
                + PrintColor.def + " if the target has below 50% HP.\n"
                + gap_T + PrintColor.BCyan("Stilled Breath") + ": Healing an ally grant to self 1 stack of " + PrintColor.BYellow("\"Clarity\"")
                + ". At 3 stacks, the next healing that does not target self consumes all of it to increases the healing by " + PrintColor.Green("25%")
                + " and grants to the target " + PrintColor.Cyan("2 mana") + ".\n"
                + gap_T + PrintColor.BBlue("Environment Adaption") + ": Reduces " + PrintColor.Blue("the likelihood of being targeted") + " of self " + PrintColor.BBlue("slightly")
                + ". When this unit is present, all allies " + PrintColor.Green("max HP +8%") + ".";
        s1_name = PrintColor.BBlue("Weakening");
        s1_des = "Casts a special spell on an enemy, dealing " + PrintColor.Purple((200 + getAp() * 4 / 3) + " (200 + 133% AP) magic damage")
                + " and " + PrintColor.Blue("reduces their ATK by 35%") + " in 2 turns.";
        s2_name = PrintColor.BGreen("Enkaphelin");
        s2_des = "Gather the quintessence of healing art, " + PrintColor.BGreen("cleanses")
                + " the effect of " + PrintColor.Purple("'Grievous Wound'") + " on all friendly units, then heals each of them for "
                + PrintColor.Green((150 + getAp() * 5 / 4) + " (150 + 125% AP) HP") + ". An ally can be targeted to be healed " +
                "for " + PrintColor.Green((300 + getAp() * 6 / 4) + " (300 + 150% AP) HP")
                + " instead, and is prioritized to gain the bonus effect from " + PrintColor.BCyan("Stilled Breath") + ".";
    }

    @Override
    public short action() {
        if (!Enemies_isAlive()) return 0;
        Entity tar;
        System.out.println(PrintColor.yellow + getName() + "'s turn!" + PrintColor.def);
        String options = "Choose an action " +
                "\n1. Heal an ally " + PrintColor.cyan + "(+1 mana)" + PrintColor.Yellow("    Clarity: " + clarity) +
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
                for (Soldier t : SoList) {
                    if (t.isAlive()) System.out.println(cnt + ". " + t.getName());
                    ++cnt;
                }
                enChoice = Input.Shrt("choice", (short) 1, (short) (cnt - 1));
                tar = SoList.get(enChoice - 1);
                if (!tar.isAlive()) {
                    System.out.println("This target is already gone! Try choosing another one instead!");
                    action();
                    return 0;
                }
                this.normalAttack(tar);
                break;
            case 2:
                if (tar_1) {
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
                } else {
                    if (!this.castSkill_1(null)) {
                        action();
                        return 0;
                    }
                }
                break;
            case 3:
                if (tar_2) {
                    System.out.println("Pick a target:");
                    for (Soldier t : SoList) {
                        if (t.isAlive()) System.out.println(cnt + ". " + t.getName());
                        ++cnt;
                    }
                    enChoice = Input.Shrt("choice", (short) 1, (short) (cnt - 1));
                    tar = SoList.get(enChoice - 1);
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
        if (target instanceof Soldier) {
            ++mana;
            int healing = (target.getHealth() < target.getMaxHealth() / 2) ? (getAp() * 15 / 10) : getAp();
            if (clarity < 3 || target == this) {
                clarity++;
                target.healing(healing);
            }
            else {
                clarity -= 3;
                healing = healing * 125 / 100;
                target.healing(healing);
                ((Soldier) target).addMana((short) 2);
                System.out.println(PrintColor.Yellow(getName()) + PrintColor.Cyan(" grants to ")
                        + PrintColor.Yellow(target.getName()) + PrintColor.Cyan(" 2 mana!\n"));
            }
        }
        else super.normalAttack(target);

        if (target instanceof Enemy) {
            EntitiesList.SoList.forEach(so -> {
                if (so instanceof Saigyouji) {
                    ((Saigyouji) so).followUp = true;
                    so.normalAttack(target);
                }
            });
        }
    }

    @Override
    public void preBattleSpecial() {
        SoList.forEach(s -> s.setMaxHealth((int) (s.getMaxHealth() * 1.08f)));
    }

    @Override
    public boolean castSkill_1(Entity target) {
        if (!super.castSkill_1(target)) return false;
        dealingDamage(target, damageOutput(0, 200 + getAp() * 4 / 3, target), s1_name, PrintColor.blue);
        target.atkDebuff.initialize((short) 35, (short) 2);
        return true;
    }

    @Override
    public boolean castSkill_2(Entity target) {
        if (!super.castSkill_2(target)) return false;

        int aliveCount = 0;
        for (Soldier so : SoList) if (so.isAlive()) aliveCount++;
        clarity += aliveCount;

        SoList.forEach(so -> {
            if (so.isAlive()) {
                while (so.grievouswound.inEffect()) so.grievouswound.fadeout();
                int heal = 150 + getAp() * 5 / 4;
                if (so == target) heal = 300 + getAp() * 6 / 4;

                if (so != target || clarity < 3) {
                    so.healing(heal);
                }
                else {
                    clarity -= 3;
                    heal = heal * 125 / 100;
                    so.healing(heal);
                    so.addMana((short) 2);
                    System.out.println(PrintColor.Yellow(getName()) + PrintColor.Cyan(" grants to ")
                            + PrintColor.Yellow(so.getName()) + PrintColor.Cyan(" 2 mana!\n"));
                }
            }
        });
        return true;
    }
}
