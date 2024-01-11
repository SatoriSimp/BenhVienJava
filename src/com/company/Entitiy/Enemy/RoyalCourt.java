package com.company.Entitiy.Enemy;

import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class RoyalCourt extends Enemy {
    private float witherScale;

    public RoyalCourt() {
        setName("Sanguinary Witherer");
        setTrait("Creature of the Sanguinaria, a natural-born warrior.\n" +
                "From the juncture they see the sunlight, their fate is intertwined with protecting this place, and they have never once thought otherwise.");
        setSkill(PrintColor.BRed("Withering Light") + ": Attack inflicts a percentage of AP as build-up 'Wither' damage on the target. After reaching a certain threshold, the affected unit takes true damage and loses some mana.");
        setMaxHealth(8000);
        setAtk((short) 60);
        setAp((short) 350);
        setDef((short) 370);
        setRes((short) 500);
        witherScale = 0.75f;
    }

    public RoyalCourt(short seed) {
        setName("Sanguinary Royal Witherer");
        setTrait("Creature of the Sanguinaria, a natural-born warrior.\n" +
                "From the juncture they see the sunlight, their fate is intertwined with protecting this place, and they have never once thought otherwise.\n" +
                "Do not push too far in, and you may yet avoid their wrath.");
        setSkill(PrintColor.BRed("Withering Light") + ": Attack inflicts a percentage of AP as build-up 'Wither' damage on the target. After reaching a certain threshold, the affected unit takes true damage and loses some mana.");
        setMaxHealth(10000);
        setAtk((short) 60);
        setAp((short) 500);
        setDef((short) 370);
        setRes((short) 600);
        witherScale = 0.6f;
    }

    @Override
    public void normalAttack(Entity target) {
        target = getTaunt(target);
        super.normalAttack(target);
        if (!WitherAccumulate.containsKey(target)) WitherAccumulate.put(target, (int) (getAp() * witherScale));
        else WitherAccumulate.replace(target, (int) (WitherAccumulate.get(target) + getAp() * witherScale));
        if (WitherAccumulate.get(target) >= 1000) {
            this.dealingDamage(target, damageOutput(0, 0, 1000, target), "Wither", PrintColor.WHITE_BOLD);
            ((Soldier) target).mana = (short) Math.max(((Soldier) target).mana - 2, 0);
            WitherAccumulate.remove(target);
        }
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "'Wither' build-up damage on attack greatly increased.";
        witherScale += 0.75f;
    }
}
