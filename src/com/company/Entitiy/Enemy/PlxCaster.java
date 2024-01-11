package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlxCaster extends Enemy {
    private boolean phalanx = false;
    private short resAdd;
    private float recovery_ratio, healing_ratio;

    public PlxCaster() {
        setName("Phalanx Shadowiest Mage");
        setTrait("A caster that is specialized in ranged combat and is the leader of the formation.\n" +
                "They are in charge of both assaulting weakened target from ranged and healing their allies due to the lack of proper medic, and they has never once let their guard down. " +
                "They carefully inspect their opponents to always make sure they pick the right target, whilst providing necessary cares to the team.\n" +
                "The name \"Phalanx\" of theirs seems heavily inspired from a well-known ancient combat formation. Marching alongside, they enforce their comrades, making them more resilient.");
        setSkill(PrintColor.BPurple("Dark Ages") + ": Controls a " + PrintColor.BYellow("\"Black Mist\"") + " to assist in combat. " + PrintColor.BYellow("\"Black Mist\"") + " is invisible and will persist as long as this unit still presents.\n" +
                PrintColor.BGreen("Incantation") + ": When there is \"Phalanx\" enemy with less than half health except self, focuses on healing them instead of attacking. Otherwise, attack deals a percentage of ATK as art damage.\n" +
                PrintColor.BBlue("Enōmotía") + ": " + PrintColor.BYellow("Phalanx") + ". When marching alongside with other " + PrintColor.BYellow("Phalanx") + " enemies, activates a field that " + PrintColor.Cyan("significantly increases their RES")
                + " and " + PrintColor.Green("gradually recovers their HP") + ".");
        setMaxHealth(8_500);
        setAtk((short) 100);
        setAp((short) 1000);
        setDef((short) 350);
        setRes((short) 400);
        resAdd = 400;
        recovery_ratio = 0.03f;
        healing_ratio = 1.0f;
        isElite = true;
    }

    @Override
    public void normalAttack(Entity target) {
        if (!this.taunt.inEffect()) {
            AtomicBoolean healed = new AtomicBoolean(false);
            EntitiesList.EnList.forEach(plx -> {
                if (!healed.get() && plx.isAlive() && plx.getName().contains("Phalanx") && plx.getHealth() < plx.getMaxHealth() / 2) {
                    plx.healing((int) (this.getAp() * healing_ratio));
                    healed.set(true);
                }
            });
            if (!healed.get()) {
                super.normalAttack(getTaunt(target), damageOutput((int) (getAtk() * 0.6f), (int) (getAp() * 0.6f), target));
            }
        }
        else super.normalAttack(getTaunt(target));
    }

    @Override
    public void preBattleSpecial() {
        if (preBattleEffectApplied) return;
        preBattleEffectApplied = true;
        EntitiesList.SummonList.add(new Mist());
        int plxcnt = 0;
        for (Enemy e : EntitiesList.EnList) if (e.getName().contains("Phalanx")) plxcnt++;
        if (plxcnt >= 2) {
            phalanx = true;
            EntitiesList.EnList.forEach(plx -> {
                if (plx.getName().contains("Phalanx")) {
                    plx.addRes(resAdd);
                    plx.setRecovery((short) (100 + plx.getMaxHealth() * recovery_ratio));
                }
            });
        }
    }

    @Override
    public void update() {
        if (!phalanx) return;
        int plxcnt = 0;
        for (Enemy e : EntitiesList.EnList) if (e.getName().contains("Phalanx") && e.isAlive()) plxcnt++;
        if (phalanx && (plxcnt < 2 || !this.isAlive())) {
            System.out.println(PrintColor.Blue("Enōmotía deactivated! The bonus RES and HP recovery on all other enemies have been removed!"));
            phalanx = false;
            EntitiesList.EnList.forEach(plx -> {
                if (plx.getName().contains("Phalanx")) {
                    plx.addRes((short) -resAdd);
                    plx.setRecovery((short) 0);
                }
            });
        }
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "Bonus RES, HP recovery from 'Enōmotía' and healing ratio from 'Incantation' are increased.";
        resAdd += 200;
        recovery_ratio += 0.02;
        healing_ratio += 0.7f;
    }
}
