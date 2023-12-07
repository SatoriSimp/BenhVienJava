package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

import static com.company.EntitiesList.EnList;

public class Shapeshifter extends Enemy {
    private final int baseHealth;
    private final short baseATK;
    private final short baseDEF;
    private final short baseRES;
    private final short baseAP;
    private final short bonusAP;

    private String skill;
    private final String trait;
    public short shapeSeed;
    private short seedMax = 3;

    public Shapeshifter() {
        setName("Shapeshiter");
        baseHealth = 15_000;
        baseATK = 500;
        baseAP = 0;
        baseDEF = 300;
        baseRES = 500;
        bonusAP = 5;
        setMaxHealth((baseHealth));
        setDef(baseDEF);
        setRes(baseRES);
        setAtk(baseATK);
        setAp(baseAP);
        trait = "Fragment of the past. A mystery that has been left unspoken throughout the history. " +
                "This creature can take on the appearance of another individual which is indistinguishable to the original, in addition to be able of cloning themselves infinitely, " +
                "from head to toe and down to their attire, allowing them to seamlessly infiltrate enemies and feeding vital information without ever being compromised.";
        skill = PrintColor.BYellow("Fragment of Silence") + ": When defeated, revives and summons an " + PrintColor.BYellow("Isomorphic Fragment") + ", this unit and " + PrintColor.BYellow("Isomorphic Fragments") + " share HP along with status effects. " +
                "For every time this unit revives, they and " + PrintColor.BYellow("Isomorphic Fragment") + " have increased max HP and receive new ability.\n"
                + PrintColor.BYellow("Second Iteration") + ": Every time an " + PrintColor.BYellow("Isomorphic Fragment") + " appears, this unit and " + PrintColor.BYellow("Isomorphic Fragments") + " gain a certain percentage of Physical and Arts damage reduction."
                + PrintColor.BPurple("\nRising Tides") + ": Each attack " + PrintColor.Purple("increases AP") + ", this bonus stacks without a limit.\n"
                + PrintColor.BPurple("Lifeline Burst") + ": After a certain number of attack, releases a burst that dealing " + PrintColor.Purple("magic damage") + " to all allies unit. Attacks from " + PrintColor.BYellow("Isomorphic Fragments") + " also count towards this progress.";
        setTrait(trait);
        setSkill(skill);
        isElite = true;
        canRevive = true;
        shapeSeed = 1;
    }

    @Override
    public void revive() {
        if (shapeSeed == seedMax) canRevive = false;
        shapeSeed++;
        this.setMaxHealth(this.getMaxHealth() + 6500);
    }

    @Override
    public void normalAttack(Entity target) {
        super.normalAttack(target);
        addAp(bonusAP);
        if (mana >= 6) castSkill();
    }

    public void castSkill() {
        EntitiesList.SoList.forEach(s -> {
            if (s.isAlive()) {
                this.dealingDamage(s, damageOutput(0, (int) (100 + getAp() * 1.5f), s), "Lifeline Burst", PrintColor.purple);
            }
        });
        mana -= 6;
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "Number of \"Isomorphic Fragment\" +1";
        seedMax++;
    }
}
