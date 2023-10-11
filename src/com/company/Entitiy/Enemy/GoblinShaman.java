package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class GoblinShaman extends Enemy {
    public GoblinShaman() {
        if (Math.floor(Math.random() * (100 - 1 + 1) + 1) > 20) {
            setName("Goblin Shaman");
            setTrait("A goblin that has learnt to use Art, some individuals are even confirmed to capable of performing" +
                    " their Art splendidly. Fortunately, this one is not the case here.");
            setSkill(PrintColor.BPurple("Art Overflow") + ": After few attacks, next attack charges up to "
                    + PrintColor.Purple("deals increased damage bases on target missing HP") +
                    " (prioritize friendly unit with " + PrintColor.Red("lowest %HP") + ").");
            setMaxHealth(5000);
            setDef((short) 200);
            setRes((short) 600);
            setAp((short) 550);
            setAtk((short) 0);
        } else {
            setName("Senior Goblin Shaman");
            setTrait("A special goblin that has fully master their Art, greatly adds up to their destructive power. " +
                    "Perhaps thing called 'talent' does exist in such low-intelligence creatures");
            setSkill(
                    PrintColor.BPurple("Art Overflow") + ": Gains " + PrintColor.Purple("50 RES penetration") + ". After few attacks, next attack charges up to "
                    + PrintColor.Purple("deals increased damage bases on target missing HP") +
                    " (prioritize friendly unit with " + PrintColor.Red("lowest %HP") + ").");
            setMaxHealth(6000);
            setDef((short) 200);
            setRes((short) 650);
            setAp((short) 700);
            setAtk((short) 0);
            setResIgn((short) 50);
        }
    }

    public GoblinShaman(short seed) {
        if (seed > 20) {
            setName("Goblin Shaman");
            setTrait("A goblin that has learnt to use Art, some individuals are even confirmed to capable of performing" +
                    " their Art splendidly. Fortunately, this one is not the case here.");
            setSkill(PrintColor.BPurple("Art Overflow") + ": After few attacks, next attack charges up to "
                    + PrintColor.Purple("deals increased damage bases on target missing HP") +
                    " (prioritize friendly unit with " + PrintColor.Red("lowest %HP") + ").");
            setMaxHealth(5000);
            setDef((short) 200);
            setRes((short) 600);
            setAp((short) 550);
            setAtk((short) 0);
        } else {
            setName("Senior Goblin Shaman");
            setTrait("A special goblin that has fully master their Art, greatly adds up to their destructive power. " +
                    "Perhaps thing called 'talent' does exist in such low-intelligence creatures");
            setSkill(
                    PrintColor.BPurple("Art Overflow") + ": Gains " + PrintColor.Purple("50 RES penetration") + ". After few attacks, next attack charges up to "
                            + PrintColor.Purple("deals increased damage bases on target missing HP") +
                            " (prioritize friendly unit with " + PrintColor.Red("lowest %HP") + ").");
            setMaxHealth(6000);
            setDef((short) 200);
            setRes((short) 650);
            setAp((short) 700);
            setAtk((short) 0);
            setResIgn((short) 50);
        }
    }

    @Override
    public void normalAttack(Entity target) {
        if (mana < 3 || this.isSilenced) {
            super.normalAttack(target);
            ++mana;
            return;
        }
        if (this.taunt.inEffect()) {
            target = this.taunt.getTaunter();
        }
        else target = this.targetSearch(HIGHEST_MSSING_HLTH);
        int skillDmg;
        if (challengeMode) {
            addResPen((short) 30);
            skillDmg = (getAp() * (100 + (target.getMissingHealth() * 250 / target.getMaxHealth())) / 100) * 13 / 10;
            addResPen((short) -30);
        }
        else skillDmg = getAp() * (100 + (target.getMissingHealth() * 250 / target.getMaxHealth())) / 100;
        System.out.println(PrintColor.red + this.getName() + " used their skill!" + PrintColor.def);
        dealingDamage(target, damageOutput(getAtk(), skillDmg, target));
        mana -= 3;
    }

    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "Charged attack deals increased damage.";
    }
}
