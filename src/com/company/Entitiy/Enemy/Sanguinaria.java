package com.company.Entitiy.Enemy;

import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static com.company.EntitiesList.EnList;
import static com.company.EntitiesList.SoList;

public class Sanguinaria extends Enemy {
    private short seed;

    public Sanguinaria() {
        setName("Twig of the Sanguinaria");
        setTrait("An enormous plant that grows underground, exposing its roots and branches to absorb the sunlight.\n" +
                "Thanks to their massive size and incredibly tough outershell, " +
                "their branches have long been treated as an convenient shelter for all creatures to hide or lays their eggs inside it, " +
                "making what hiding underneath that big twig remains a mystery.");
        setSkill(PrintColor.BYellow("Ominous Apparition") + ": Doesn't attack, self destruct when all other enemies have been defeated. " +
                "Upon destruction, spawns an " + PrintColor.Yellow("Sanguinary Descendant Malicebearer") + ".");
        setMaxHealth(12500);
        setDef((short) 700);
        setRes((short) 500);
        setAtk((short) 0);
        seed = 1;
        canRevive = true;
    }

    public Sanguinaria(short seed) {
        if (seed == 342) {
            byte StatsMul = 2;
            setName("Sanguinary Descendant Chaincaster");
            setTrait("An apparition that would normally never appear, unless they have to.\n" +
                    "Nourished by hatred and blood, they show no hesitation in seeking for revenge for their kins.\n" +
                    "Once the task is done, their body will soon decay and return to the wild, waiting for the next time they get to rise once again.");
            setSkill(PrintColor.BRed("Wrathgorger") + ": Upon being spawned, gains increased "
                    + PrintColor.Green("max HP") + ", " + PrintColor.BPurple("AP") + ", "
                    + PrintColor.Yellow("DEF") + " and " + PrintColor.Cyan("RES") + " for every enemy that has been defeated, stacks additively. " +
                    "Attack jumps between targets that haven't gotten struck by it, deals reduced damage each time.");
            setMaxHealth(8000 + (4000 * StatsMul));
            setAp((short) (700 + (200 * StatsMul)));
            setDef((short) (300 + (100 * StatsMul)));
            setRes((short) (300 + (250 * StatsMul)));
            canRevive = false;
        }
        else if (seed == 667) {
            byte StatsMul = 3;
            setName("Sanguinary Descendant Malicebearer");
            setTrait("An apparition that would normally never appear, unless they have to.\n" +
                    "Nourished by hatred and blood, they show no hesitation in seeking for revenge for their kins.\n" +
                    "Once the task is done, their body will soon decay and return to the wild, waiting for the next time they get to rise once again.");
            setSkill(PrintColor.BRed("Hatedrinker") + ": Upon being spawned, gains increased "
                    + PrintColor.Green("max HP") + ", " + PrintColor.Red("ATK") + ", " + PrintColor.Red("DEF penetration")
                    + ", " + PrintColor.Yellow("DEF") + " and " + PrintColor.Cyan("RES") + " for every enemy that has been defeated, stacks additively.");
            setMaxHealth(8000 + (4000 * StatsMul));
            setAtk((short) (750 + (250 * StatsMul)));
            setDefPen((short) (15 + (5 * StatsMul)));
            setDef((short) (300 + (250 * StatsMul)));
            setRes((short) (300 + (100 * StatsMul)));
            canRevive = false;
        }
        else {
            setName("Twig of the Sanguinaria");
            setTrait("An enormous plant that grows underground, exposing its roots and branches to absorb the sunlight.\n" +
                    "Thanks to their massive size and incredibly tough outer shell, " +
                    "their branches have long been treated as an convenient shelter for all creatures to hide or lays their eggs inside it, " +
                    "making what hiding underneath that big twig remains a mystery.");
            if (seed == 1) {
                setSkill(PrintColor.BYellow("Ominous Apparition") + ": Doesn't attack, self destruct when all other enemies have been defeated. " +
                        "Upon destruction, spawns an " + PrintColor.Yellow("Sanguinary Descendant Malicebearer") + ".");
                setMaxHealth(12500);
                setDef((short) 700);
                setRes((short) 500);
            } else {
                setSkill(PrintColor.BYellow("Ominous Apparition") + ": Doesn't attack, self destruct when all other enemies have been defeated. " +
                        "Upon destruction, spawns an " + PrintColor.Yellow("Sanguinary Descendant Chaincaster") + ".");
                setMaxHealth(12500);
                setDef((short) 500);
                setRes((short) 800);
            }
            setAtk((short) 0);
            this.seed = seed;
            canRevive = true;
        }
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
        else if (this.getName().contains("Malicebearer")) {
            super.normalAttack(targetSearch());
        }
        else {
            ArrayList<Soldier> SList = (ArrayList<Soldier>) SoList.clone();
            int scale = 100;
            Collections.shuffle(SList);
            for (Soldier s : SList) {
                if (!s.isAlive()) continue;
                dealingDamage(s, damageOutput(0, getAp() * scale / 100, s));
                scale = Math.max(scale - 25, 50);
            }
        }
    }

    @Override
    public void revive() {
        super.revive();
        short StatsMul = 0;
        for (Enemy e : EnList) {
            if (!e.isAlive() && !(e instanceof Sanguinaria)) StatsMul++;
        }
        StatsMul = (short) Math.min(3, StatsMul);

        if (seed == 1) {
            setName("Sanguinary Descendant Malicebearer");
            setTrait("An apparition that would normally never appear, unless they have to.\n" +
                    "Nourished by hatred and blood, they show no hesitation in seeking for revenge for their kins.\n" +
                    "Once the task is done, their body will soon decay and return to the wild, waiting for the next time they get to rise once again.");
            setSkill(PrintColor.BRed("Hatedrinker") + ": Upon being spawned, gains increased "
                    + PrintColor.Green("max HP") + ", " + PrintColor.Red("ATK") + ", " + PrintColor.Red("DEF penetration")
                    + ", " + PrintColor.Yellow("DEF") + " and " + PrintColor.Cyan("RES") + " for every enemy that has been defeated, stacks additively.");
            setMaxHealth(8000 + (4000 * StatsMul));
            setAtk((short) (750 + (250 * StatsMul)));
            setDefPen((short) (15 + (5 * StatsMul)));
            setDef((short) (300 + (250 * StatsMul)));
            setRes((short) (300 + (100 * StatsMul)));
        }
        else {
            setName("Sanguinary Descendant Chaincaster");
            setTrait("An apparition that would normally never appear, unless they have to.\n" +
                    "Nourished by hatred and blood, they show no hesitation in seeking for revenge for their kins.\n" +
                    "Once the task is done, their body will soon decay and return to the wild, waiting for the next time they get to rise once again.");
            setSkill(PrintColor.BRed("Wrathgorger") + ": Upon being spawned, gains increased "
                    + PrintColor.Green("max HP") + ", " + PrintColor.BPurple("AP") + ", "
                    + PrintColor.Yellow("DEF") + " and " + PrintColor.Cyan("RES") + " for every enemy that has been defeated, stacks additively. " +
                    "Attack jumps between targets that haven't gotten struck by it, deals reduced damage each time.");
            setMaxHealth(8000 + (4000 * StatsMul));
            setAp((short) (700 + (200 * StatsMul)));
            setDef((short) (300 + (100 * StatsMul)));
            setRes((short) (300 + (250 * StatsMul)));
        }
    }

    @Override
    public void setChallengeMode() {
    }
}
