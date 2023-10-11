package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Heir extends Enemy {
    private short ap_up;
    private float healing, convert;

    public Heir() {
        if (Math.floor(Math.random() * (100 - 1 + 1) + 1) >= 30) {
            setName("Bloodthirsty Heir");
            setTrait("A creature was born from the Sanguinaria, adapting bloods as their only source of food to grow. " +
                    "They always thirst for more bloods, and won't stop searching to steal the blood from others.");
            setMaxHealth(2000);
            setDef((short) 0);
            setRes((short) 400);
            setAp((short) 180);
            setAtk((short) 0);
            setReduction((short) 75);
        }
        else {
            setName("Engorged Heir");
            setTrait("A creature was born from the Sanguinaria, adapting bloods as their only source of food to grow. " +
                    "Though their bodies are already engorged with the blood of others, their thirst never ceases.");
            setMaxHealth(2300);
            setDef((short) 0);
            setRes((short) 400);
            setAp((short) 240);
            setAtk((short) 0);
            setReduction((short) 80);
        }
        ap_up = 20;
        healing = 1.9f;
        convert = 0.55f;
        setSkill(PrintColor.BRed("Bloodbath")
                + ": Reduces damage taken, attack hits all allies and heals self for a percentage of damage dealt. " +
                "A portion of exceeded healing " + PrintColor.Green("is converted to max health") + ".\n"
                + PrintColor.BRed("Nourishing Blood") + ": Whenever max health is gained from " + PrintColor.BYellow("\"Bloodbath\"")
                + ", " + PrintColor.Purple("increases self AP") + ".");
    }

    public Heir(short seed) {
        if (seed >= 30) {
            setName("Bloodthirsty Heir");
            setTrait("A creature was born from the Sanguinaria, adapting bloods as their only source of food to grow. " +
                    "They always thirst for more bloods, and won't stop searching to steal the blood from others.");
            setMaxHealth(2000);
            setDef((short) 0);
            setRes((short) 400);
            setAp((short) 200);
            setAtk((short) 0);
            setReduction((short) 75);
        }
        else {
            setName("Engorged Heir");
            setTrait("A creature was born from the Sanguinaria, adapting bloods as their only source of food to grow. " +
                    "Though their bodies are already engorged with the blood of others, their thirst never ceases.");
            setMaxHealth(2300);
            setDef((short) 0);
            setRes((short) 400);
            setAp((short) 250);
            setAtk((short) 0);
            setReduction((short) 80);
        }
        ap_up = 20;
        healing = 1.9f;
        convert = 0.55f;
        setSkill(PrintColor.BRed("Bloodbath")
                + ": Reduces damage taken, attack hits all allies and heals self for a percentage of damage dealt. " +
                "A portion of exceeded healing " + PrintColor.Green("is converted to max health") + ".\n"
                + PrintColor.BRed("Nourishing Blood") + ": Whenever max health is gained from " + PrintColor.BYellow("\"Bloodbath\"")
                + ", " + PrintColor.Purple("increases self AP") + ".");
    }

    public void normalAttack(Entity target) {
        if (this.isDisarmed) return;

        EntitiesList.SoList.forEach(so -> {
            if (so.isAlive()) {
                int damage = damageOutput(getAtk(), getAp(), so);
                dealingDamage(so, damage);
                int restore = (int) ((damage * healing) * (100 - this.grievouswound.getValue()) / 100);
                if (this.getMissingHealth() < restore) {
                    setMaxHealth((int) ((restore - getMissingHealth()) * convert + getMaxHealth()));
                    addAp(ap_up);
                } else healing(restore);
            }
        });
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "The max-health convert ratio and ap gain are increased.";
        convert += 0.15f;
        ap_up += 15;
    }
}
