package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

import java.util.ArrayList;

public class PosSwrd extends Enemy {
    private final ArrayList<Soldier> Marked = new ArrayList<>();

    public PosSwrd() {
        setName("Corrupted Bladeweaver");
        setMaxHealth(14000);
        setAtk((short) 800);
        setDef((short) 500);
        setRes((short) 500);
        setTrait("A shadow warrior formed by the will of soldiers that have fallen upon this cursed land. " +
                "Their battle has yet to be finished, their duties have yet to be fulfilled. Thus, they must continue, until the day they are all settled.");
        setSkill(
                PrintColor.BRed("Moment of Despair") + ": Attack deals a percentage of ATK as bonus " + PrintColor.WHITE_BOLD + "true damage"
                + PrintColor.def + ", this damage splashes amongst the allies, dealing "
                + PrintColor.Red("reduced damage") + " to all allies other than the primal target.");
    }

    @Override
    public void normalAttack(Entity target) {
        if (this.taunt.inEffect() && this.taunt.getTaunter().isAlive()) target = this.taunt.getTaunter();
        short bonus = (short) (getAtk() * 5 / 10);
        short hpBonus = 0;
        if (challengeMode) {
            bonus = (short) (getAtk() * 2 / 10 + getMaxHealth() * 3 / 100 + target.getMaxHealth() / 100);
            hpBonus = (short) (target.getMaxHealth() * 15 / 100);
        }

        dealingDamage(target, damageOutput(getAtk(), getAp(), bonus + hpBonus, target));

        for (Soldier so: EntitiesList.SoList) {
            if (so.isAlive() && so != target) {
                if (challengeMode) {
                    if (!Marked.contains(so)) Marked.add(so);
                    else {
                        int splash = bonus * 8 / 10 + hpBonus / 5;
                        dealingDamage(so, damageOutput(0, 0, splash, so), "Moment of Silence", PrintColor.WHITE_BOLD);
                    }
                }
                else {
                    int splash = bonus * 7 / 10 + hpBonus / 10;
                    dealingDamage(so, damageOutput(0, 0, splash, so));
                }
            }
        }
    }

    @Override
    public void setChallengeMode() {
        setName("Corrupted Worldcurser");
        setSkill(
                PrintColor.BRed("Moment of Despair") + ": Attack deals a percentage of ATK as bonus " + PrintColor.WHITE_BOLD + "true damage"
                + PrintColor.def + ", this damage splashes amongst the allies, dealing "
                + PrintColor.Red("reduced damage") + " to all allies other than the primal target.\n"
                + PrintColor.BRed("Moment of Silence") + ": Becomes immune to " + PrintColor.Blue("abnormal statuses")
                + " and possesses " + PrintColor.Red("40% Life-steal")
                + ". Additionally, the bonus damage from " + PrintColor.BYellow("Moment of Despair")
                + " is increased by a percentage of " + PrintColor.Red("self's max HP") + " plus " + "a percentage of "
                + PrintColor.Red("target's max HP ")
                + ". The splash damage no longer deal damage, but instead marks its targets, which detonates when they are hit" +
                " by this splash attack once again, dealing " + PrintColor.WHITE_BOLD + "true damage" + PrintColor.def + ".");
        isElite = true;
        ChallengeModeStatsUp();
        skill += "A second talent is granted!";
        setLifesteal((short) 40);
        canCC = false;
    }
}
