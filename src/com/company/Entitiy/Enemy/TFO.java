package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class TFO extends Enemy {
    public static Entity lowDEF, highHP;
    private short manaCost;
    private final short atkAdd;
    private final short penAdd;

    public TFO() {
        setName("The Forsaken One");
        setTrait("Rescuers recently found an unknown person who turns out to be a former member of adventure guild.\n" +
                "His skin and flesh were heavily corroded, abnormally aggressive behaviours, no longer able to speak human language, already turned into a terrifying monster.\n" +
                "Carnage everywhere... countless casualties... whereabouts unknown...");
        setSkill(PrintColor.BGreen("Thirst for Survival") + ": Gradually " + PrintColor.Green("recovers HP")
                + ", healing increases bases upon HP loss. While possessing " + PrintColor.Green("'Shield'") + ", significantly "
                + PrintColor.Green("ramps up the healing") + "!\n"
                + PrintColor.BRed("Overflowing Hatred") + ": " + PrintColor.Red("ATK") + " and " + PrintColor.Red("DEF penetration")
                + " are continuously increased after each attack, " + PrintColor.Yellow("but quickly wears off") + " if the attack is "
                + PrintColor.Blue("'Blocked'") + " or " + PrintColor.Blue("unable to attack") + ".\n"
                + PrintColor.BRed("Despite the Weaks") + ": Locks up to 2 friendly units simultaneously, one with "
                + PrintColor.Yellow("lowest DEF") + " and the other with " + PrintColor.Green("highest HP")
                + ".\nPeriodically, releases a special ability to attack both:\n" + "- "
                + PrintColor.Red("\"Singing Sand\"") + " on the " + PrintColor.Yellow("former") + ", dealing " + PrintColor.Red("high physic damage") + "."
                + "\n- " + PrintColor.Blue("\"Blizzard Tomb\"") + " on the " + PrintColor.Green("latter") + ", inflicts "
                + PrintColor.Purple("magic damage") + " bases on their " + PrintColor.Green("max health")
                + ".\nAfter the skill ends, this unit then drains vitality from the targets, grants to them " + PrintColor.Green("'Shield'") + " equals to target count.");
        setMaxHealth(15000);
        setAtk((short) 800);
        setDef((short) 500);
        setRes((short) 300);
        atkAdd = 200;
        penAdd = 15;
        manaCost = 2;
        isElite = true;
    }

    public void normalAttack(Entity target) {
        if (this.mana >= manaCost) {
            castSkill();
            return;
        }
        ++mana;
        boolean atkLanded = false;
        if (this.taunt.inEffect() && this.taunt.getTaunter().isAlive()) target = this.taunt.getTaunter();
        if (target.getShield() <= 0 && !this.isDisarmed) atkLanded = true;

        int damage = damageOutput(getAtk(), getAp(), target);
        dealingDamage(target, damage);

        if (atkLanded) {
            addAtk(atkAdd);
            addDefPen(penAdd);
            addResPen(penAdd);
        }
        else {
            if (this.getAtk() - atkAdd * 2 < this.getBaseAtk()) setAtk(getBaseAtk());
            else addAtk((short) (atkAdd * -2));

            if (this.getDefPen() - penAdd * 2 < 0) setDefPen((short) 0);
            else addDefPen((short) (penAdd * - 2));

            if (this.getResPen() - penAdd * 2 < 0) setResPen((short) 0);
            else addResPen((short) (penAdd * - 2));
        }

        if (this.mana >= manaCost) System.out.println(PrintColor.Red("The wind is howling!"));
    }

    public void castSkill() {
        this.mana -= manaCost;
        dealingDamage(lowDEF, damageOutput(getAtk() * 18 / 10, 0, lowDEF), "Singing Sand" , PrintColor.red);
        dealingDamage(highHP, damageOutput(0, getAtk() / 2 + (highHP.getHealth() * 6 / 10), highHP), "Blizzard Tomb", PrintColor.blue);
        setShield((short) 1);
        if (lowDEF != highHP) setShield((short) 2);
    }

    @Override
    public void preTurnPreparation() {
        super.preTurnPreparation();
        highHP = targetSearch(5);
        lowDEF = targetSearch(2);
    }

    @Override
    public void naturalRecovery() {
        int healing = !challengeMode ? (int) (getMaxHealth() * 0.025f + getMissingHealth() * 0.055f)
                                    : (int) (getMaxHealth() * 0.045f + getMissingHealth() * 0.066f);
        if (this.getShield() > 0) healing *= (!challengeMode) ? 3 : 4;
        System.out.println();
        healing(healing, true);
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "The healing effect from \"Thirst for Survival\" is strengthened";
    }
}
