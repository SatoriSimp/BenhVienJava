package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Rotchaser extends Enemy {
    private int bleedDmg_base;
    private int bleedDmg_MxHealth;

    public Rotchaser() {
        if (Math.floor(Math.random() * (100 - 1 + 1) + 1) > 25) {
            setName("Rotchaser");
            setTrait("Creature of the Sanguinaria, fast-movement and surprisingly resilient despite their look.\n" +
                    "They don't understand the concept of living and such, and it's not like they need to.\n" +
                    "Thoughts are incompatible with death, and they never stop.");
            setMaxHealth(8700);
            setAtk((short) 550);
            setDef((short) 600);
        }
        else {
            setName("Lunatic Rotchaser");
            setTrait("Creature of the Sanguinaria, more nimble and resilient, even among their kins.\n" +
                    "They don't understand the concept of living and such, and it's not like they need to.\n" +
                    "Thoughts are incompatible with death, and they never stop.");
            setMaxHealth(10000);
            setAtk((short) 600);
            setDef((short) 700);
        }
        setRes((short) 400);
        bleedDmg_base = 400;
        bleedDmg_MxHealth = 10;
        setSkill(PrintColor.BRed("Death Cavern") + ": Attack causes " + PrintColor.Red("'Bleeding'") + " to the target, " +
                "continuously inflicts physic and true damage");
    }

    public Rotchaser(int seed) {
        if (seed > 25) {
            setName("Rotchaser");
            setTrait("Creature of the Sanguinaria, fast-movement and surprisingly resilient despite their look.\n" +
                    "They don't understand the concept of living and such, and it's not like they need to.\n" +
                    "Thoughts are incompatible with death, and they never stop.");
            setMaxHealth(8700);
            setAtk((short) 550);
            setDef((short) 600);
        }
        else {
            setName("Lunatic Rotchaser");
            setTrait("Creature of the Sanguinaria, more nimble and resilient, even among their kins.\n" +
                    "They don't understand the concept of living and such, and it's not like they need to.\n" +
                    "Thoughts are incompatible with death, and they never stop.");
            setMaxHealth(10000);
            setAtk((short) 600);
            setDef((short) 700);
        }
        setRes((short) 400);
        bleedDmg_base = 400;
        bleedDmg_MxHealth = 10;
        setSkill(PrintColor.BRed("Death Cavern") + ": Attack causes " + PrintColor.Red("'Bleeding'") + " to the target, " +
                "continuously inflicts physic and true damage that scales with target's max HP");
    }

    @Override
    public void normalAttack(Entity target) {
        target = getTaunt(target);
        target.bleed.initialize((short) (bleedDmg_base + bleedDmg_MxHealth * target.getMaxHealth() / 100), (short) 3, this);
        System.out.println(PrintColor.Yellow(target.getName()) + " has been inflicted with " + PrintColor.Red("'Bleeding'") + "!");
        super.normalAttack(target);
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        bleedDmg_MxHealth += 10;
        bleedDmg_base += 200;
        skill += "'Bleeding' damage increased!";
    }
}
