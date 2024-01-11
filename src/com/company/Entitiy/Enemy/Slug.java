package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Slug extends Enemy {
    private float def_up, res_up;
    private final int deathNote;
    private short preAR = 0, preMR = 0;
    private boolean exploded = false;
    private final short burnBaseDmg;
    private final float burnBaseBonus;

    public Slug() {
        if (Math.floor(Math.random() * (100 - 1 + 1) + 1) >= 20) {
            setMaxHealth(8000);
            setAtk((short) 400);
            deathNote = 1000;
            setName("Basin Infused Slug");
            setTrait("They're named this way bases on basic similarities with a normal slug, but don't look down on them because of this. " +
                    "\nHaving their abdomen filled with nothing but a tons of unstable dangerous chemicals,\nthey have developed to be more" +
                    " resilient and aggressive when injured in order to keep themselves alive, results in an extremely high survivability" +
                    ".\nEven if they fail to do so, their opponents and surroundings won't receive a good end either");
        }
        else {
            setMaxHealth(9000);
            setAtk((short) 420);
            deathNote = 1200;
            setName("Nourished Infused Slug");
            setTrait("They're named this way bases on basic similarities with a normal slug, but don't look down on them because of this. " +
                    "\nHaving their abdomen filled with nothing but a tons of unstable dangerous chemicals,\nthey have developed to be more" +
                    " resilient and aggressive when injured in order to keep themselves alive, results in an extremely high survivability" +
                    ".\nAnd since they have survived quite a long time in the wilderness, the chemicals within their body has gradually " +
                    "become more unstable, results in an even more glory firework show if they were defeated.");
        }
        setSkill(PrintColor.BRed("Heat-up") + ": Attack inflicts " + PrintColor.BRed("Burn") + " to the target, " +
                    "dealing " + PrintColor.WHITE_BOLD + "true damage" + PrintColor.def + " that scales with target's DEF. "
                    + PrintColor.BRed("Burn") + "'s base damage increases bases on self's missing HP." + '\n'
                + PrintColor.BYellow("Cracking Shell") + ": Upon falling below 50% HP, gains increased " + PrintColor.yellow + "DEF" + PrintColor.def + " and " + PrintColor.cyan + "RES" + PrintColor.def + ".\n"
                + PrintColor.BRed("Firework Show") + ": Upon death, explodes and deals " + PrintColor.WHITE_BOLD + deathNote + " true damage" + PrintColor.def + " to all friendly units.");
        def_up = 4.65f;
        res_up = 4.05f;
        burnBaseDmg = 150;
        burnBaseBonus = 7.25f;
        setDef((short) 500);
        setRes((short) 200);
    }

    public Slug(int seed) {
        if (seed >= 20) {
            setMaxHealth(8000);
            setAtk((short) 400);
            deathNote = 1000;
            setName("Basin Infused Slug");
            setTrait("They're named this way bases on basic similarities with a normal slug, but don't look down on them because of this. " +
                    "\nHaving their abdomen filled with nothing but a tons of unstable dangerous chemicals,\nthey have developed to be more" +
                    " resilient and aggressive when injured in order to keep themselves alive, results in an extremely high survivability" +
                    ".\nEven if they fail to do so, their opponents and surroundings won't receive a good end either");
        }
        else {
            setMaxHealth(9000);
            setAtk((short) 420);
            deathNote = 1200;
            setName("Nourished Infused Slug");
            setTrait("They're named this way bases on basic similarities with a normal slug, but don't look down on them because of this. " +
                    "\nHaving their abdomen filled with nothing but a tons of unstable dangerous chemicals,\nthey have developed to be more" +
                    " resilient and aggressive when injured in order to keep themselves alive, results in an extremely high survivability" +
                    ".\nAnd since they have survived quite a long time in the wilderness, the chemicals within their body has gradually " +
                    "become more unstable, results in an even more glory firework show if they were defeated.");
        }
        setSkill(PrintColor.BRed("Heat-up") + ": Attack inflicts " + PrintColor.BRed("Burn") + " to the target, " +
                "dealing " + PrintColor.WHITE_BOLD + "true damage" + PrintColor.def + " that scales with target's DEF. "
                + PrintColor.BRed("Burn") + "'s base damage increases bases on self's missing HP." + '\n'
                + PrintColor.BYellow("Cracking Shell") + ": Upon falling below 50% HP, gains increased " + PrintColor.yellow + "DEF" + PrintColor.def + " and " + PrintColor.cyan + "RES" + PrintColor.def + ".\n"
                + PrintColor.BRed("Firework Show") + ": Upon death, explodes and deals " + PrintColor.WHITE_BOLD + deathNote + " true damage" + PrintColor.def + " to all friendly units.");
        def_up = 4.65f;
        res_up = 4.05f;
        burnBaseDmg = 150;
        burnBaseBonus = 7.25f;
        setDef((short) 500);
        setRes((short) 200);
    }

    @Override
    public void normalAttack(Entity target) {
        target = getTaunt(target);
        super.normalAttack(target);
        int percentMissingHealth = getMissingHealth() * 100 / getMaxHealth();
        target.burn.initialize((short) (burnBaseBonus * percentMissingHealth + burnBaseDmg), (short) 3, this);
        System.out.println(PrintColor.Yellow(target.getName()) + " has been inflicted with " + PrintColor.Red("'Burn'") + "!");
    }

    @Override
    public void update() {
        addDef((short) (preAR * -1));
        addRes((short) (preMR * -1));
        int percentMissingHealth = getMissingHealth() * 100 / getMaxHealth();
        if (percentMissingHealth >= 50) {
            preAR = (short) (percentMissingHealth * def_up);
            preMR = (short) (percentMissingHealth * res_up);
        }
        addDef(preAR);
        addRes(preMR);
    }

    public void detonate() {
        if (exploded) return;
        System.out.println(PrintColor.yellow + getName() + " exploded! Dealing true damage to all allied units!");
        EntitiesList.SoList.forEach(so -> {
            if (so.isAlive()) {
                dealingDamage(so, damageOutput(0, 0, deathNote, so), PrintColor.BYellow("Firework Show"), PrintColor.red);
            }
        });
        exploded = true;
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "Bonus DEF and RES from \"Heat-up\" is increased";
        def_up = 7.5f;
        res_up = 6.25f;
    }
}
