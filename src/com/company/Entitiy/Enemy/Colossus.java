package com.company.Entitiy.Enemy;

import com.company.PrintColor;

public class Colossus extends Enemy {
    public Colossus() {
        setName("Broken Colossus");
        setTrait("A war machine that once spread terror throughout the lands.\n" +
                "Though boasts significant destructive power, they are extremely difficult to operate properly.");
        setSkill(PrintColor.BRed("Lamentation of a Bygone Glorious Era") + ": Attack deals reduced damage and greatly increases damage taken. " +
                "This effect is removed once this unit are " + PrintColor.BYellow("Operated") + ".");
        setMaxHealth(100000);
        setAtk((short) 2000);
        setDefPen((short) 50);
        setDef((short) 1000);
        setRes((short) 500);
    }

    @Override
    public void setChallengeMode() {

    }
}
