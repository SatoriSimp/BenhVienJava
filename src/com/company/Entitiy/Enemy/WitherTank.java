package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

import static com.company.EntitiesList.SoList;

public class WitherTank extends Enemy {
    public WitherTank() {
        setName("Sanguinary Wither Tank");
        setTrait("Creature of the Sanguinaria, shaped like a standard transportation vehicle, with sharp spikes scattered all over its body.\n" +
                "Rushing straightforwardly to clear up the obstacles within its path, they function well as a frontliner, but their main role is to spread corruption.");
        setSkill(PrintColor.BPurple("Corrupting Path") + ": After several attacks, the next attack deals " + PrintColor.Purple("art damage") + " to all friendly units and " + PrintColor.Purple("poisons") + " them.");
        setMaxHealth(14000);
        setAtk((short) 1050);
        setAp((short) 0);
        setDef((short) 900);
        setRes((short) 400);
        mana = 2;
    }

    @Override
    public void normalAttack(Entity target) {
        if (mana >= 3) {
            castSkill();
            return;
        }
        super.normalAttack(target);
    }

    void castSkill() {
        mana -= 3;
        SoList.forEach(s -> {
            if (s.isAlive()) {
                dealingDamage(s, damageOutput(0, (int) (getAtk() * 0.37f), s), "Corruption", PrintColor.purple);
                s.poison.initialize((short) 200, (short) 3, this);
            }
        });
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "DEF is greatly increased.";
    }

    @Override
    public void ChallengeModeStatsUp() {
        challengeMode = true;
        skill += '\n' + cmAdd;
        addMaxHealth((int) (getMaxHealth() * 0.2f));
        setAtk((short) (getBaseAtk() * 12 / 10));
        setAp((short) (getBaseAp() * 11 / 10));
        setDef((short) (getBaseDef() + 2000));
    }
}
