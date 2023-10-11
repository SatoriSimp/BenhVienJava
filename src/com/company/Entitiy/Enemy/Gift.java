package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Gift extends Enemy {
    public short blades;
    private short baseReduction = 15;

    public Gift() {
        setName("Gift of the Wild");
        setMaxHealth(5000);
        setAtk((short) 400);
        setAp((short) 400);
        blades = 5;
        setSkill(
                PrintColor.BYellow("Soul Rings") + ": Carries " + PrintColor.Yellow(blades + " 'Soul Rings'") + " that give a stacking damage reduction, each attack consumes "
                + PrintColor.Yellow("1 'Soul Ring'") + " to deals " + PrintColor.Red("bonus damage")
                + ", increased by the number of " + PrintColor.Yellow("'Soul Ring'") + " self currently holds.");
        setReduction((short) (baseReduction * blades));
    }

    @Override
    public void normalAttack(Entity target) {
        target = getTaunt(target);
        int damage = damageOutput(getAtk(), getAp(), target);
        if (blades > 0) {
            damage = damage * 15 / 10 + (damage * 35 / 100) * (blades - 1);
            --blades;
            setReduction((short) (baseReduction * blades));
        }
        dealingDamage(target, damage);
    }

    @Override
    public void setChallengeMode() {
        blades += 3;
        setReduction((short) (baseReduction * blades));
        setSkill(
                PrintColor.BYellow("Soul Rings") + ": Carries " + PrintColor.Yellow(blades + " 'Soul Rings'") + " that give a stacking damage reduction, each attack consumes "
                        + PrintColor.Yellow("1 'Soul Ring'") + " to deals " + PrintColor.Red("bonus damage")
                        + ", increased by the number of " + PrintColor.Yellow("'Soul Ring'") + " self currently holds.");
        ChallengeModeStatsUp();
        skill += "More 'Soul Rings' are granted!";
    }
}
