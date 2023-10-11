package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class EnThrow extends Enemy {
    public int hpDecay;

    public EnThrow() {
        setName("Enraged Possessed Bonethrower");
        setTrait("A former marksman that has completely given in to primal madness as their lifeforce is consumed." +
                "\nThey will even rip out bones inside their body to use as weapon because of their deadened sense of pain.");
        setSkill(PrintColor.BYellow("Final Warcry") + ": Has " + PrintColor.Red("extremely high ATK")
                + ". Gradually " + PrintColor.BBlue("loses HP overtime") + ".");
        setMaxHealth(10500);
        setDef((short) 500);
        setRes((short) 500);
        setAp((short) 0);
        setAtk((short) 1900);
        setDefPen((short) 35);
        setDefIgn((short) 200);
        hpDecay = getMaxHealth() * 15 / 100;
    }

    @Override
    public void normalAttack(Entity target){
        super.normalAttack(target);
        if (!challengeMode || this.getShield() <= 0) {
            this.rotting(hpDecay);
            System.out.println(PrintColor.yellow + getName() + PrintColor.def + " loses " + PrintColor.red + hpDecay + " HP!");
        }
    }

    public void setChallengeMode() {
        ChallengeModeStatsUp();
        setShield((short) 3);
        skill += "3 layers of 'Shield' is granted. While 'Shield's are persist, no longer lose HP overtime.";
    }
}
