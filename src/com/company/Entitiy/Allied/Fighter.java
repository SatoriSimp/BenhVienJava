package com.company.Entitiy.Allied;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Fighter extends Soldier {
    private final short rate = 45, leth = 15;
    private short stored = 0;
    private short bonusStored = 0;

    public Fighter() {
        setName("Fighter");
        tar_1 = true;
        tar_2 = false;
        cost_1 = 3;
        cost_2 = 1;
        hp = 2950;
        atk = 300;
        ap = 0;
        def = 450;
        res = 150;
        setMaxHealth(hp);
        setAtk(atk);
        setDef(def);
        setRes(res);
        writeKit();
        shortDes = "Gradually dominates the battlefield as time passes by";
    }

    @Override
    public void writeKit() {
        talents = PrintColor.BRed("Sharpen Your Blade")
                + ": After every turn, " + PrintColor.red + "ATK and lethality" + PrintColor.def + " are increased at a fixed rate "
                + PrintColor.yellow + "(currently gaining " + PrintColor.red + "+" + (rate * stored) + " ATK" + PrintColor.yellow + " and "
                + PrintColor.Bred + "+" + (leth * stored) + " DEF ignorance" + PrintColor.yellow + ").\n" + gap_T
                + PrintColor.BCyan("Preaching of Mind") + ": After 6 stacks of " + PrintColor.BYellow("Sharpen your Blade")
                + " have been granted, additionally recovers " + PrintColor.Cyan("1 mana") + ".";
        s1_name = PrintColor.BRed("Juggernaut");
        s1_des = "Swing the sword to attack a target, temporary " + PrintColor.Blue("doubles the bonus amount from talent")
                + ", then deals " + PrintColor.Red(((getAtk() + stored * rate) * 30 / 10) + " (300% ATK) plus ")
                + PrintColor.Red((1 + (getAtk() + stored * rate) * 615 / 100000) + "% (1 + 0.615% ATK) target's max health as physic damage") + ".";
        s2_name = PrintColor.BBlue("Unshakable Will");
        s2_des = "Within this turn, " + PrintColor.Blue("stop attacking and doubles the gain from passive") + ", " +
                "renders self with " + PrintColor.cyan + "90% 'Shelter'" + PrintColor.def +".";
    }

    @Override
    public boolean castSkill_1(Entity target) {
        if (!super.castSkill_1(target)) return false;
        addDefIgn((short) (leth * stored));
        int scaling = ((getAtk() + stored * rate) * 27 / 10) + (target.getMaxHealth() * (1 + (getAtk() + stored * rate) * 485 / 100000) / 100);
        int damage = damageOutput(scaling, 0, target);
        this.dealingDamage(target, damage, s1_name, PrintColor.Bred);
        addDefIgn((short) (leth * stored * -1));
        talentTriggers();
        return true;
    }

    @Override
    public boolean castSkill_2(Entity target) {
        if (!super.castSkill_2(target)) return false;
        addAtk((short) (rate * 2));
        addDefIgn((short) (leth * 2));
        stored += 2;
        bonusStored += 2;
        if (bonusStored >= 6) {
            mana++;
            bonusStored -= 6;
            System.out.println(PrintColor.Cyan("Fighter recovers 1 mana!"));
        }
        this.shelter.setValue((short) 90);
        System.out.println("Fighter gains " + PrintColor.red + rate * 2 + " atk" + PrintColor.def
                + ", " + PrintColor.Bred + leth * 2 + " lethality" + PrintColor.def + " and " + PrintColor.blue
                + "90% 'Shelter'" + PrintColor.def + " this turn!");
        this.printHealthBar(PrintColor.green);
        System.out.println();
        return true;
    }

    @Override
    public void normalAttack(Entity target) {
        super.normalAttack(target);
        talentTriggers();
    }

    public void talentTriggers() {
        System.out.println("Fighter gains " + PrintColor.red + rate + " atk " + PrintColor.def
                + "and " + PrintColor.Bred + leth + " lethality!" + PrintColor.def);
        addAtk(rate);
        addDefIgn(leth);
        stored++;
        bonusStored++;
        if (bonusStored >= 6) {
            mana++;
            bonusStored -= 6;
            System.out.println(PrintColor.Cyan("Fighter recovers 1 mana!"));
        }
    }
}
