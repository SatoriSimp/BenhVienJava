package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

import java.util.concurrent.atomic.AtomicBoolean;

public class Mist extends Enemy {

    public Mist() {
        setName("Black Mist");
        setMaxHealth(100_000);
        setAp((short) 700);
        setResPen((short) 60);
        setDef((short) 9999);
        setRes((short) 9999);
        setReduction((short) 100);
        setTrait("");
        setSkill(PrintColor.BPurple("Thunder Roar") + ": " + PrintColor.BYellow("Terrain") + ". Attack deals " + PrintColor.Purple("art damage") + " and prioritizes friendly unit with " + PrintColor.Purple("lowest current HP") + ".");
        isInvisible = true;
        canCC = false;
    }

    @Override
    public void normalAttack(Entity target) {
        target = getTaunt(targetSearch(LOWEST_CRNT_HLTH));
        super.normalAttack(target);
    }

    @Override
    public void update() {
        if (!this.isAlive()) return;
        AtomicBoolean check = new AtomicBoolean(false);
        EntitiesList.EnList.forEach(plx -> {
            if (plx instanceof PlxCaster && plx.isAlive()) {
                check.set(true);
            }
        });
        if (!check.get()) this.dealingDamage(this, this.getMaxHealth());
    }

    @Override
    public void setChallengeMode() {

    }
}
