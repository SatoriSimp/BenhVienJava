package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class BDummy extends Enemy {
    public BDummy() {
        setName("Bewitched Dummy");
        setTrait("A dummy made of straws and topped it up with some magic, for practicing purpose.\n" +
                "People complaint that the original dummy was too boring, even for a practice target, " +
                "hence, some elite casters went ahead and blessed these with a spell that reflects damage to the attacker.\n" +
                "Yet, they didn't plan to make a safety switch or anything for it. So even dummies can be deadly now.");
        setSkill(PrintColor.BYellow("Reflection") + ": When attacked, part of damage taken is redirect to the attacker as " + PrintColor.Yellow("true damage") + ".");
        setMaxHealth(40000);
        setAtk((short) 0);
        setDef((short) 0);
        setRes((short) 100);
        canCC = false;
        canAttack = false;
    }

    @Override
    public void setChallengeMode() {

    }

    public void reflect(Entity attacker, int dmg) {
        this.dealingDamage(attacker,
                damageOutput(0, 0, (int) (dmg * 0.33f), attacker),
                PrintColor.BYellow("Reflection"), PrintColor.yellow);
    }
}
