package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Devourer extends Enemy {
    public boolean devoured = false;
    private short DEF_add;
    private float DevourThreshold, ATK_steal, HP_steal;

    public Devourer() {
        if (Math.floor(Math.random() * (100 - 1 + 1) + 1) > 20) {
            setName("Sanguinary Wither Devourer");
            setTrait("Creature of the Sanguinaria. Devours any obstacle in its way, including the living. Anything and everything is food to it.");
            setMaxHealth(22000);
            setAtk((short) 900);
            DEF_add = 800;
            ATK_steal = 0.8f;
            HP_steal = 1.5f;
        }
        else {
            setName("Sanguinary Wither Maw");
            setTrait("Higher form creature of the Sanguinaria. Devours any obstacle in its way, including the living. Anything and everything is food to it. Only death can make it surrender what it devoured.");
            setMaxHealth(25000);
            setAtk((short) 1050);
            DEF_add = 900;
            ATK_steal = 0.9f;
            HP_steal = 2f;
        }
        DevourThreshold = 0.5f;
        setSkill(PrintColor.BRed("Feast Upon Thee") + ": When an allied unit falls below 50% HP, devour them to permanently increase self DEF and steal a percentage of their base stats. " +
                "Once devoured, the target is immediately defeated and this ability can no longer be used afterwards.");
        setDef((short) 0);
        setRes((short) 600);
    }

    @Override
    public void normalAttack(Entity target) {
        if (!devour()) super.normalAttack(target);
    }

    @Override
    public void update() {
        if (!challengeMode) return;
        devour();
    }

    public boolean devour() {
        if (devoured) return false;
        for (Soldier s : EntitiesList.SoList) {
            if (!s.isAlive()) continue;
            if (s.getHealth() <= s.getMaxHealth() / 2) {
                dealingDamage(s, 9999, "Devour", PrintColor.Bred, true);
                if (!s.isAlive()) {
                    this.addDef(DEF_add);
                    this.addAtk((short) (s.getBaseAtk() * ATK_steal + s.getBaseAp() * (ATK_steal - 0.2f)));

                    if (challengeMode) this.setMaxHealth((int) (this.getMaxHealth() + s.getMaxHealth() * HP_steal));
                    else this.setHealth((int) (this.getMaxHealth() + s.getMaxHealth() * HP_steal));

                    this.setShield(s.getShield());
                    s.setShield((short) 0);
                    devoured = true;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "'Feast Upon Thee' can now be used as soon as there is a valid target. When successfully devoured the target, fully restores HP.";
    }
}
