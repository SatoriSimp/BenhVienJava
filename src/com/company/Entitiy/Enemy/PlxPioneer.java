package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.PrintColor;

public class PlxPioneer extends Enemy {
    private boolean phalanx = false;

    public PlxPioneer() {
        setName("Phalanx Renowned Frontliner");
        setTrait("A pioneer that, as their name suggests, specializes in covering the allies and protecting them from the firepower of enemy.\n" +
                "Equipped with high protected gears combined with years of trainings. They stand forth amidst of battle, seemingly unyielding. With proper support from their allies, they won't go down easily.\n" +
                "The name \"Phalanx\" of theirs seems heavily inspired from a well-known ancient combat formation. Marching alongside, they are insurmountable, a loosely packed formation may not be able to halt their advance at all!");
        setSkill(PrintColor.BBlue("Distorted Deconstruction") + ": When being attacked, the source of damage is affected with " + PrintColor.BBlue("\"Weakening\"") + " that lasts 3 turns.\n" +
                PrintColor.BBlue("Enōmotía") + ": " + PrintColor.BYellow("Phalanx") + ". When marching alongside with other " + PrintColor.BYellow("Phalanx") + " enemies, activates a field that renders them to be " + PrintColor.BYellow("\"Invisible\"") + ".");
        setMaxHealth(15000);
        setDef((short) 800);
        setRes((short) 200);
        setAtk((short) 500);
    }

    @Override
    public void preBattleSpecial() {
        int plxcnt = 0;
        for (Enemy e : EntitiesList.EnList) if (e.getName().contains("Phalanx")) plxcnt++;
        if (plxcnt >= 2) {
            phalanx = true;
            for (Enemy e : EntitiesList.EnList) {
                if (e.getName().contains("Phalanx") && e != this) {
                    e.isInvisible = true;
                    if (challengeMode) e.setReduction((short) 70);
                }
            }
        }
    }

    @Override
    public void update() {
        int plxcnt = 0;
        for (Enemy e : EntitiesList.EnList) if (e.getName().contains("Phalanx")) plxcnt++;
        if ((plxcnt < 2 && phalanx) || !this.isAlive()) {
            phalanx = false;
            for (Enemy e : EntitiesList.EnList)
                if (e.getName().contains("Phalanx")) {
                    e.isInvisible = false;
                    if (challengeMode) e.setReduction((short) 0);
                }
        }
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "Enōmotía now also grants to Phalanx enemies (except self) damage reduction.";
    }
}
