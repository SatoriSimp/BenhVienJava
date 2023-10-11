package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class VenomSpider extends Enemy {
    private short poisonDmg;

    public VenomSpider() {
        setName("Venom Rock Spider");
        setAtk((short) 300);
        setMaxHealth((short) 7000);
        setDef((short) 500);
        setRes((short) 300);
        poisonDmg = 650;
        setTrait("Creature that mostly found in caves, usually disguise themselves as a rock that is extremely hard to tell. " +
                "Their body contains deadly venom, causing a gradual but painful death for any unlucky creature that got caught in their sight.");
        setSkill(PrintColor.Bpurple + "Neurotoxin" + PrintColor.def + ": Attack renders the target to be " + PrintColor.Purple("'Poisoned'")
            + ", continuously inflicts " + PrintColor.Purple(poisonDmg + " magic damage") + " and " + PrintColor.purple + "35% 'Grievous Wound'" + PrintColor.def
            + " each turn in the next 3 turns. Self prioritize attacking target that has yet to be "
            + PrintColor.purple + "'Poisoned'" + PrintColor.def + " or target with the least remaining duration.\n"
            + PrintColor.BBlue + "Antibody" + PrintColor.def + ": Takes " + PrintColor.blue + "significantly reduced damage "
            + PrintColor.def + "from attacks of " + PrintColor.purple + "'Poisoned'"
            + PrintColor.def + " units.");
    }

    public void normalAttack(Entity target) {
        if (this.taunt.inEffect()) target = this.taunt.getTaunter();
        else target = targetSearch(8);
        super.normalAttack(target);
        target.poison.initialize(poisonDmg, (short)3, this);
        System.out.println(PrintColor.purple + target.getName() + " is poisoned!");
        if (challengeMode) {
            if (!EntitiesList.Players_isAlive()) return;
            Entity target2 = targetSearch(8);
            if (target2 == target) return;
            int damage = damageOutput(getAtk(), getAp(), target2);
            dealingDamage(target2, damage);
            target2.poison.initialize(poisonDmg, (short) 3, this);
            System.out.println(PrintColor.purple + target2.getName() + " is poisoned!");
        }
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "Attack 2 targets simultaneously";
    }
}
