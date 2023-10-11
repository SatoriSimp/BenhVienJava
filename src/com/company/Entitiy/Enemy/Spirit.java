package com.company.Entitiy.Enemy;

import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Spirit extends Enemy {
    public static Soldier marked;
    private short manaDrain, skillCost;
    public static short timer;
    private int healing;

    public Spirit() {
        if (Math.floor(Math.random() * (100 - 1 + 1) + 1) >= 30) {
            setName("Vengeful Spirit");
            setMaxHealth(8000);
            setAp((short) 400);
            setDef((short) 800);
            setRes((short) 200);
            healing = 400;
            setTrait("Its body has decayed and returned to the wild, what's left now is a chained will, a bare essence that " +
                    "desperately searching for its new host.\nIt attacks any living form as soon as they're caught in its sight, weakens the " +
                    "spirit within then seizes control of their body.\nDue to incompatibility, none of its attempt ended in success, but" +
                    " its lacks of thought prevents it from understanding what happens, so it will never stop trying.");
        } else {
            setName("Hateful Spirit");
            setMaxHealth(9000);
            setAp((short) 500);
            setDef((short) 850);
            setRes((short) 250);
            healing = 525;
            setTrait("Its body has decayed and returned to the wild, what's left now is a chained will, a bare essence that " +
                    "desperately searching for its new host.\nIt attacks any living form as soon as they're caught in its sight, weakens the " +
                    "spirit within then seizes control of their body.\nDespite already knowing it can never success, something within it feels the distorted joy from" +
                    " killing others amusing, and for that has become its only purpose in life, it will never stop.");
        }
        manaDrain = 6;
        mana = 4;
        skillCost = 5;
        setSkill(PrintColor.BCyan("The Second Death") + ": Marks a target, prioritize one with " + PrintColor.blue + "lowest mana" + PrintColor.def
                + ". After a few turns, the mark activates, steals up to " + PrintColor.cyan + manaDrain + " mana" + PrintColor.def
                + " of the target in addition to deal extra " + PrintColor.Purple("magic damage") + " that increases bases on how low the target's mana is"
                + ", and heals self for " + PrintColor.green + healing + " - " + healing * manaDrain + " HP" + PrintColor.def
                + ", bases on mana stole. If the target has no mana, " + PrintColor.Bred + "immediately executes them!");
    }

    public Spirit(int seed) {
        if (seed >= 30) {
            setName("Vengeful Spirit");
            setMaxHealth(8000);
            setAp((short) 400);
            setDef((short) 800);
            setRes((short) 200);
            healing = 400;
            setTrait("Its body has decayed and returned to the wild, what's left now is a chained will, a bare essence that " +
                    "desperately searching for its new host.\nIt attacks any living form as soon as they're caught in its sight, weakens the " +
                    "spirit within then seizes control of their body.\nDue to incompatibility, none of its attempt ended in success, but" +
                    " its lacks of thought prevents it from understanding what happens, so it will never stop trying.");
        } else {
            setName("Hateful Spirit");
            setMaxHealth(9000);
            setAp((short) 470);
            setDef((short) 850);
            setRes((short) 200);
            healing = 525;
            setTrait("Its body has decayed and returned to the wild, what's left now is a chained will, a bare essence that " +
                    "desperately searching for its new host.\nIt attacks any living form as soon as they're caught in its sight, weakens the " +
                    "spirit within then seizes control of their body.\nDespite already knowing it can never success, something within it feels the distorted joy from" +
                    " killing others amusing, and for that has become its only purpose in life, it will never stop.");
        }
        manaDrain = 6;
        mana = 4;
        skillCost = 5;
        setSkill(PrintColor.BCyan("The Second Death") + ": Marks a target, prioritize one with " + PrintColor.blue + "lowest mana" + PrintColor.def
                + ". After a few turns, the mark activates, steals up to " + PrintColor.cyan + manaDrain + " mana" + PrintColor.def
                + " of the target in addition to deal extra " + PrintColor.Purple("magic damage") + " that increases bases on how low the target's mana is"
                + ", and heals self for " + PrintColor.green + healing + " - " + healing * manaDrain + " HP" + PrintColor.def
                + ", bases on mana stole. If the target has no mana, " + PrintColor.Bred + "immediately executes them!");
    }

    @Override
    public void normalAttack(Entity target) {
        if (mana >= skillCost && !isSilenced) {
            this.castSkill();
        }
        if (this.taunt.inEffect()) target = this.taunt.getTaunter();
        super.normalAttack(target);
    }

    public void castSkill() {
        Soldier target = targetSearch(7);
        System.out.println(PrintColor.purple + getName() + " marked " + target.getName() + "!");
        marked = target;
        timer = (short) (Math.min(skillCost - 1, 3));
        mana -= skillCost;
    }

    @Override
    public void preTurnPreparation() {
        if (!this.isAlive()) marked = null;
        if (marked == null) return;
        if (--timer <= 0) {
            if (marked.mana == 0) {
                System.out.println(PrintColor.red + "Mark triggers! " + marked.getName() + " doesn't have enough mana!" + PrintColor.def);
                dealingDamage(marked, 9999, "Execution", PrintColor.red);
                this.healing(healing * manaDrain);
            }
            else {
                int steal = Math.min(marked.mana, manaDrain);
                System.out.println("Mark triggers! " + PrintColor.yellow + getName() + PrintColor.def + " stole away "
                        + PrintColor.cyan + steal + " mana " + PrintColor.def + "from " + PrintColor.yellow + marked.getName() + PrintColor.def);
                marked.mana -= steal;
                steal = manaDrain - steal + 1;
                dealingDamage(marked, damageOutput(0, (int)((getAp() * 0.2f + marked.getMaxHealth() * 0.1f) * steal), marked)
                        , "Lifeless Surgeon", PrintColor.purple);
                this.healing(healing * steal);
            }
            marked = null;
        }
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "'The Second Death' has reduced cooldown";
        skillCost = 3;
    }
}
