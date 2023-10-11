package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Cliff extends Enemy {
    private float threshold;
    private short shots;

    public Cliff() {
        setName("\"Bridge Clip\" Cliff");
        setTrait("The legendary mercenary and founder of Blacksteel Worldwide.\nThe flames of war have both destroyed and reshaped him.");
        setSkill(
            PrintColor.BYellow("The Survivor, The Winner") + ": Greatly reduces physic and art damage taken. When struck by energy flow emitted from "
                    + PrintColor.BYellow("Heat Waves Spur") + ", this unit suffers " + PrintColor.Red("Burn") +".\n" +
            PrintColor.BRed("Rules of the Hunter") + ": Attack deals " + PrintColor.Red("physic damage") + " and " + PrintColor.Red("ignores a certain amount of the target's DEF") +
                    ". If an attack deals at least " + PrintColor.Red("a certain amount of damage on the target") + ", this unit will" + PrintColor.Red(" launch an additional attack on the same target") + ", each attack only triggers this effect once.\n" +
            PrintColor.BRed("Hand of Lightning") + ": Locks on a target and immediately launch 3 consecutive attack on them, each attack applies the effect from " + PrintColor.BRed("Rules of the Hunter")
                + ".\n\n" + PrintColor.BYellow("During the second phase:")
                + "\n - Attack now ignores more DEF."
                + "\n - The damage threshold to trigger bonus attack from " + PrintColor.BRed("Rules of the Hunter") + " decreases."
                + "\n - " + PrintColor.BRed("Hand of Lightning") + " is replaced with " + PrintColor.BRed("Hand of Thunder") + "."
        );
        setMaxHealth(30_000);
        setAtk((short) 1100);
        setDefIgn((short) 120);
        setReduction((short) 50);
        setDef((short) 1111);
        setRes((short) 1111);
        threshold = 0.7f;
        shots = 3;
        mana = 0;
        canRevive = true;
        isElite = true;
    }

    @Override
    public void normalAttack(Entity target) {
        target = getTaunt(target);
        if (mana >= 2) {
            castSkill(target);
            return;
        }

        int raw = getAtk() + getAp();
        int real_damage = this.damageOutput(getAtk(), getAp(), target);
        super.normalAttack(target, real_damage);

        // extra hit
        if (real_damage >= raw * threshold) {
            this.dealingDamage(target, damageOutput(getAtk(), getAp(), target), "Lucky Shot", PrintColor.red);
        }
        ++mana;
    }

    void castSkill(Entity target) {
        mana -= 2;
        String name = isCanRevive() ? "Hand of Lightning" : "Hand of Thunder";
        for (int i = 0; i < shots; ++i) {
            if (!target.isAlive()) break;
            int raw = getAtk() + getAp();
            int real_damage = this.damageOutput(getAtk(), getAp(), target);
            dealingDamage(target, real_damage, name, PrintColor.Bred);

            // extra hit
            if (real_damage >= raw * threshold) {
                this.dealingDamage(target, damageOutput(getAtk(), getAp(), target), "Lucky Shot", PrintColor.red);
                if (!isCanRevive()) mana++;
            }
        }
    }

    @Override
    public void revive() {
        super.revive();
        while (burn.inEffect()) burn.fadeout();
        setMaxHealth(getMaxHealth());
        addAtk((short) (getAtk() * 0.05f));
        setDefIgn((short) 240);
        setSkill(
                PrintColor.BYellow("The Survivor, The Winner") + ": Greatly reduces physic and art damage taken. When struck by energy flow emitted from "
                        + PrintColor.BYellow("Heat Waves Spur") + ", this unit suffers " + PrintColor.Red("Burn") +".\n" +
                        PrintColor.BRed("Rules of the Hunter") + ": Attack deals " + PrintColor.Red("physic damage") + " and " + PrintColor.Red("ignores a certain amount of the target's DEF") +
                        ". If an attack deals at least " + PrintColor.Red("a certain amount of damage on the target") + ", this unit will" + PrintColor.Red(" launch an additional attack on the same target") + ", each attack only triggers this effect once.\n" +
                        PrintColor.BRed("Hand of Thunder") + ": Locks on a target and immediately launch 5 consecutive attack on them, each attack applies the effect from " + PrintColor.BRed("Rules of the Hunter") + ". For every bonus attack made, this skill's cool-down is reduced"
                        + ".\n\n" + PrintColor.BYellow("During the second phase:")
                        + "\n - Attack now ignores more DEF."
                        + "\n - The damage threshold to trigger bonus attack from " + PrintColor.BRed("Rules of the Hunter") + " decreases."
                        + "\n - " + PrintColor.BRed("Hand of Lightning") + " is replaced with " + PrintColor.BRed("Hand of Thunder") + "."
        );
        threshold = 0.6f;
        shots = 5;
        mana = 1;
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "Max HP and damage reduction effect increases.";
        setMaxHealth((int) (getMaxHealth() * 1.5f));
        setReduction((short) 75);
    }
}
