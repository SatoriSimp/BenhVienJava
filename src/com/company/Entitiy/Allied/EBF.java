package com.company.Entitiy.Allied;

import com.company.Entitiy.Enemy.Enemy;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class EBF extends Soldier {
    public EBF() {
        setName("Emperor's Blade");
        setMaxHealth(15000);
        setAtk((short) 500);
        setDef((short) 500);
        setRes((short) 500);
        setAp((short) 0);
        setDefPen((short) 50);
        mana = 1;
        cost_1 = 1;
        cost_2 = 3;
        writeKit();
    }

    @Override
    public void writeKit() {
        talents = PrintColor.BRed("Dominion") + ": After every attack, " + PrintColor.Red("permanently increases self ATK by 100") + ", stacking up to 5 times. If the attack brings the target's HP down belows 40%, they will be inflicted with " + PrintColor.Blue("1% 'Fragile'") + " within the next 10 turns.";
    }

    @Override
    public boolean castSkill_1(Entity target) {
        return super.castSkill_1(target);
    }

    @Override
    public boolean castSkill_2(Entity target) {
        return super.castSkill_2(target);
    }
}
