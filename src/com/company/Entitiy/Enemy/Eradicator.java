package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

import static com.company.EntitiesList.EnList;
import static com.company.EntitiesList.Enemies_isAlive;

public class Eradicator extends Enemy {
    private short atkcnt;
    private short atkcntMX;
    private boolean inviLost = false;
    private float skillScale;
    public float atkUp;

    public Eradicator() {
        setName("\"Redmark\" Eradicator");
        setTrait("A special mercenary sniper who excels at ambushing targets exposed in harsh environments.\n" +
                "They are good at hiding themselves and thus can never be spotted until they're already taken some moves, at which their job has already finished.\n" +
                "\"Redmark Contracts\" are tailor-made for elite and powerful mercenaries with a distinct lack of morals.\n" +
                "The initiators of such contracts are ruthless and definitely up to no good.");
        setSkill(
            PrintColor.BBlue("Hidden Killer") + ": Stays " + PrintColor.Yellow("Invisible") + " normally (can not be directly targeted). Effect is removed after this unit has attacked 3 times or all other enemies have been defeated.\n"
            + PrintColor.BRed("Radar Sweep") + ": When on the field, all enemies have increased ATK. While " + PrintColor.Yellow("Invisible") + ", attack will only target " + PrintColor.Blue("units with less than 50% HP") + ", inflicts " + PrintColor.Red("massive physic damage") + ".");
        setMaxHealth(6500);
        setAtk((short) 1200);
        setAp((short) 0);
        setDef((short) 350);
        setRes((short) 500);
        skillScale = 2.1f;
        atkUp = 1.07f;
        isInvisible = true;
        atkcnt = 0;
        atkcntMX = 3;
    }

    @Override
    public void normalAttack(Entity target) {
        if (isInvisible) {
            target = getTaunt(targetSearch(HIGHEST_MSSING_HLTH));
            if (target.getMissingHealth() < target.getMaxHealth() / 2) return;
            super.normalAttack(target, damageOutput((int) (getAtk() * skillScale), getAp(), target));
            atkcnt++;
            if (atkcnt == atkcntMX) {
                isInvisible = false;
                inviLost = true;
            }
        }
        else super.normalAttack(target);
    }

    @Override
    public void preBattleSpecial() {
        EnList.forEach(e -> {
            e.setAtk((short) (e.getBaseAtk() * atkUp));
            e.setAp((short) (e.getBaseAp() * atkUp));
        });
    }

    @Override
    public void update() {
        if (inviLost) return;
        boolean revStealth = true;
        for (Enemy e : EnList) {
            if (e.isAlive() && !e.isInvisible && e != this) revStealth = false;
        }
        if (revStealth) {
            inviLost = true;
            isInvisible = false;
        }
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "Requires 2 more attacks to remove stealth.";
        atkcntMX += 2;
        atkUp += 0.03f;
    }
}
