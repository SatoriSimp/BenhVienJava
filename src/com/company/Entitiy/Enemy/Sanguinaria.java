package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

import static com.company.EntitiesList.EnList;

public class Sanguinaria extends Enemy {
    public Sanguinaria() {
        setName("Twig of the Sanguinaria");
        setTrait("An enormous plant that grows underground, exposing its roots and branches to absorb the sunlight.\n" +
                "Thanks to their massive size and incredibly tough outershell, " +
                "their branches have long been treated as an convenient shelter for all creatures to hide or lays their eggs inside it, " +
                "making what hiding underneath that big twig remains a mystery.");
        setSkill(PrintColor.BYellow("Ominous Apparition") + ": Doesn't attack, self destruct when all other enemies have been defeated. " +
                "Upon destruction, spawns an " + PrintColor.Yellow("Sanguinary Descendant Malicebearer") + ".");
        setMaxHealth(10000);
        setDef((short) 900);
        setRes((short) 900);
        setAtk((short) 0);
        canRevive = true;
    }

    @Override
    public void normalAttack(Entity target) {
        if (this.canRevive) {
            boolean allGone = true;
            for (Enemy e : EnList) {
                if (e.isAlive() && !(e instanceof Sanguinaria)) allGone = false;
            }
            if (allGone) this.revive();
        }
        else super.normalAttack(targetSearch());
    }

    @Override
    public void revive() {
        super.revive();
        short StatsMul = 1;
        for (Enemy e : EnList) {
            if (!e.isAlive() && !(e instanceof Sanguinaria)) StatsMul++;
        }
        StatsMul = (short) Math.min(4, StatsMul);
        setName("Sanguinary Descendant Malicebearer");
        setTrait(" An apparition that would normally never appear, unless they have to.\n" +
                "Nourished by hatred and blood, they show no hesitation in seeking for revenge for their kins.\n" +
                "Once the task is done, their body will soon decay and return to the wild, waiting for the next time they get to rise once again.");
        setSkill(PrintColor.BRed("Hatedrinker") + ": Upon being spawned, gains increased "
                + PrintColor.Green("max HP") + ", " + PrintColor.Red("ATK") + ", " + PrintColor.Red("DEF penetration")
                + ", " + PrintColor.Yellow("DEF") + " and " + PrintColor.Cyan("RES") + " for every enemy that has been defeated, stacks additively.");
        setMaxHealth(5000 * StatsMul);
        setAtk((short) (500 * StatsMul));
        setDefPen((short) (10 * (StatsMul - 1)));
        setDef((short) (250 * StatsMul));
        setRes((short) (200 * StatsMul));
    }

    @Override
    public void setChallengeMode() {
    }
}
