package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class ZombifiedOrc extends Enemy {
    public int hpDecay;
    private short defDecay, resDecay;

    public ZombifiedOrc() {
        shortDes = "Deals high physic dmg with some abilities, recommended for newcomers";
        if (Math.floor(Math.random() * (100 - 1 + 1) + 1) > 20) {
            setName("Zombified Orc");
            setTrait("A rotten kind of lifeform. After getting their mind reshaped by a parasite living inside their brain," +
                    " they now boast superior strength as their lifeforce is consumed.");
            setMaxHealth(8000);
            setDef((short) 600);
            setAtk((short) 750);
        } else {
            setName("Prefect Zombified Orc");
            setTrait("An evenly rotten kind of lifeform. After getting their mind and body reshaped by a parasite living inside their brain," +
                    " they now boast superior strength as their lifeforce is consumed.");
            setMaxHealth(9000);
            setDef((short) 650);
            setAtk((short) 900);
        }
        setSkill(PrintColor.BBlue("Organic Deconstruction") + ": "
                + PrintColor.Red("Gradually loses HP and resistances overtime") + ".\n"
                + PrintColor.BGreen("Gorges") + ": Attack "
                + PrintColor.Green("heals self for a percentage of damage dealt") + ".\n"
                + PrintColor.BRed("Despite the Weak")
                + ": Charges up for a strong attack that deals " + PrintColor.Red("more damage") + ", "
                + PrintColor.Green("ramps up the healing") + " and "
                + "prioritizes targeting unit with " + PrintColor.Yellow("lowest DEF") + ".");
        setLifesteal((short) 60);
        setRes((short) 400);
        hpDecay = 5;
    }

    @Override
    public void normalAttack(Entity target) {
        if (this.isDisarmed) {
            triggerTalent();
            return;
        }

        if (mana < 3 || this.isSilenced) {
            super.normalAttack(target);
            triggerTalent();
            ++mana;
            return;
        }

        if (this.taunt.inEffect()) {
            target = this.taunt.getTaunter();
        } else target = this.targetSearch(LOWEST_DEF);
        int skillDmg = getAtk() * 2;
        setLifesteal((short) 200);
        System.out.println(PrintColor.red + this.getName() + " used their skill!" + PrintColor.def);
        dealingDamage(target, damageOutput(skillDmg, 0, target));
        setLifesteal((short) 60);
        triggerTalent();
        mana -= 3;
    }

    public void triggerTalent() {
        if (challengeMode) {
            defDecay = (short) (getDef() * 8 / 100 * -1);
            resDecay = (short) (getRes() * 8 / 100 * -1);
        } else {
            defDecay = (short) (getDef() / 5 * -1);
            resDecay = (short) (getRes() / 5 * -1);
        }
        int healthLoss = getMaxHealth() * hpDecay / 100;
        this.rotting(healthLoss);
        this.addDef(defDecay);
        this.addRes(resDecay);
        System.out.println(PrintColor.blue + getName() + PrintColor.def + " loses " + PrintColor.red +  healthLoss + " HP" + PrintColor.def + ", "
                + PrintColor.yellow + (defDecay * -1) + " DEF" + PrintColor.def + " and " + PrintColor.cyan + (resDecay * -1) + " RES" + PrintColor.def);
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "ATK increases, DEF and RES loss overtime is significantly decreased.";
        setAtk((short) (getBaseAtk() + 200));
    }
}
