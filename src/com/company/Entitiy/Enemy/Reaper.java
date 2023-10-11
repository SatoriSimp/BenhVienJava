package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Reaper extends Enemy {
    private short skillMana;

    public Reaper() {
        setName("Reaper");
        setTrait("An ancient creature that has existed for eternity.\nExtremely aggressive, especially towards human " +
                "being and will attack them as soon as one is in their sight." +
                "\nThus it is advised to avoid encountering them at all cost");
        setSkill(PrintColor.BYellow("Scythe") + ": Prioritize attack unit with " + PrintColor.red + "lowest HP first\n" + PrintColor.def +
                PrintColor.BRed("Reaper") + ": Performs an AoE attack that " + PrintColor.red + "hits all friendly units once" + PrintColor.def + ", " +
                "dealing " + PrintColor.Red("physic damage") + " that is increased bases on " + PrintColor.Red("their missing health") +
                ".\nIf this attack takes down at least 1 target, " + PrintColor.red + "it can be recast immediately!" + PrintColor.def);
        setMaxHealth(7000);
        setDef((short) 400);
        setRes((short) 150);
        setAtk((short) 600);
        skillMana = 3;
    }

    @Override
    public void normalAttack(Entity target) {
        if (this.mana >= skillMana && !this.isSilenced) {
            mana -= skillMana;
            castSkill();
            return;
        }
        target = targetSearch(LOWEST_CRNT_HLTH);
        super.normalAttack(target);
    }

    public void castSkill() {
        if (!this.isAlive()) return;
        boolean took = false;
        System.out.println(PrintColor.red + "HARVEST! " + getName() + " performs a powerful AoE attack!");
        for (Soldier so : EntitiesList.SoList) {
            if (!so.isAlive()) continue;
            int spellDmg = damageOutput(Math.min((this.getAtk() + so.getMissingHealth()), this.getAtk() * 3), 0, so);
            dealingDamage(so, spellDmg);
            if (!so.isAlive()) took = true;
        }
        if (took) castSkill();
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "Has significantly increased HP, and \"Reap\" has reduced cooldown";
        skillMana = 2;
        addMaxHealth(getMaxHealth() * 12 / 10);
        ++mana;
    }
}
