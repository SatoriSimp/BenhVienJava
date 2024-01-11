package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Heatwave extends Enemy {
    public int baseDmg;
    public float defScale;

    public Heatwave() {
        setName("Heat Waves Spout");
        setTrait("");
        setSkill(PrintColor.BBlue("Charges") + ": Cannot attack " +
                "and takes fixed damage when being attacked, the amount increases based on the attacker's DEF.\n"
            + PrintColor.BYellow("Energy Flow")
                + ": When HP drops to 0, overloads and emit a shockwave that deals 3000 true damage to all enemies. Then fully restores HP.");
        setMaxHealth(12000);
        setAtk((short) 0);
        setDef((short) 0);
        setRes((short) 0);
        canAttack = false;
        canRevive = true;
        baseDmg = 1000;
        defScale = 1.85f;
    }

    @Override
    public void normalAttack(Entity target) {
    }

    @Override
    public void revive() {
        EntitiesList.EnList.forEach(en -> {
            if (en.isAlive()) {
                this.dealingDamage(en, damageOutput(0, 0, 3000, en), "Energy Flow", PrintColor.Byellow);
                if (en instanceof Cliff) en.burn.initialize((short) (en.getMaxHealth() * 0.035f), (short) 2, this);
            }
        });
        setMaxHealth(getMaxHealth());
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        setMaxHealth((int) (getMaxHealth() * 1.15f));
        skill += "Requires more energy to fully charges, base charge amount decreases";
        baseDmg -= 400;
        defScale += 0.25f;
    }
}
