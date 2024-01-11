package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

import java.util.HashMap;

public class Shapeshifter extends Enemy {
    private final float corrosionScale;

    public short shapeSeed;
    private short seedMax = 3;

    public Shapeshifter() {
        setName("Shapeshifter");
        setMaxHealth(15_000);
        setDef((short) 300);
        setRes((short) 500);
        setAtk((short) 500);
        setAp((short) 0);
        setTrait("Fragment of the past. A mystery that has been left unspoken throughout the history.\n" +
                "This creature can take on the appearance of another individual which is indistinguishable to the original, in addition to be able of cloning themselves infinitely, " +
                "from head to toe and down to their attire, allowing them to seamlessly infiltrate enemies and feeding vital information without ever being compromised.");
        setSkill(PrintColor.BYellow("Fragment of Silence") + ": When defeated, revives and summons an " + PrintColor.BYellow("Isomorphic Fragment") + ", this unit and " + PrintColor.BYellow("Isomorphic Fragments") + " share HP along with status effects. " +
                "For every time this unit revives, they and " + PrintColor.BYellow("Isomorphic Fragment") + " have increased max HP. Up to 3 "  + PrintColor.BYellow("Isomorphic Fragments") + " can be summoned.\n"
                + PrintColor.BYellow("Second Iteration") + ": Every time an " + PrintColor.BYellow("Isomorphic Fragment") + " appears, this unit and " + PrintColor.BYellow("Isomorphic Fragments") + " gain a certain percentage of Physical and Arts damage reduction."
                + PrintColor.BCyan("\nRising Tides") + ": Attack and ability inflict build-up " + PrintColor.BCyan("\"Corrosion\"") + " damage. After reaching a certain threshold, the affected unit takes " + PrintColor.Red("physic damage") + " and " + PrintColor.Blue("permanently loses some of their DEF") + ".\n"
                + PrintColor.BPurple("Lifeline Burst") + ": After a certain number of attack, releases a burst that deal " + PrintColor.Purple("magic damage") + " to all allied units. Attacks from " + PrintColor.BYellow("Isomorphic Fragments") + " also count towards this progress.");
        corrosionScale = 0.4f;
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
        target = getTaunt(target);
        super.normalAttack(target);
        if (!CorrosionAccumulate.containsKey(target)) CorrosionAccumulate.put(target, (int) (getAtk() * corrosionScale));
        else CorrosionAccumulate.replace(target, (int) (CorrosionAccumulate.get(target) + getAtk() * corrosionScale));
        if (CorrosionAccumulate.get(target) >= 1000) {
            this.dealingDamage(target, damageOutput(1200, 0, 0, target), "Corrosion", PrintColor.cyan);
            target.addDef((short) -100);
            CorrosionAccumulate.remove(target);
        }
        if (mana >= 6) castSkill();
    }

    public void castSkill() {
        EntitiesList.SoList.forEach(s -> {
            if (s.isAlive()) {
                this.dealingDamage(s, damageOutput(0, 250, s), "Lifeline Burst", PrintColor.purple);
                if (!CorrosionAccumulate.containsKey(s)) CorrosionAccumulate.put(s, 200);
                else CorrosionAccumulate.replace(s, CorrosionAccumulate.get(s) + 200);
                if (CorrosionAccumulate.get(s) >= 1000) {
                    this.dealingDamage(s, damageOutput(1200, 0, 0, s), "Corrosion", PrintColor.cyan);
                    s.addDef((short) -100);
                    CorrosionAccumulate.remove(s);
                }
            }
        });
        mana -= 6;
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "Number of \"Isomorphic Fragment\" +1.";
        seedMax++;
    }
}
