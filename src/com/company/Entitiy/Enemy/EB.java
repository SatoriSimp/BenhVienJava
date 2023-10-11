package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class EB extends Enemy {
    private short stack = 1, atkAdd;
    private float hpscale, threshold;
    private boolean opener = true;

    public boolean special;

    public EB() {
        canRevive = true;
        isElite = true;
        special = false;
        setName("Emperor's Blade");
        setTrait("A manifestation of a certain will. The most terrifying military force, " +
                "they are widely-known throughout the world only as horrifying spectres of legend and yore.\n" +
                "A single one is enough to slaughter entire squads.\nDefeat has never been a possibility.");
        setSkill(PrintColor.BRed("Set the Stage") + ": First attack instead casts " + PrintColor.Red("\"Collapsing Fear\"") + ", can only happen once.\n" +
                PrintColor.BRed("Dominion") + ": Attack deals " + PrintColor.Red("ignores a certain amount of defense") + ", each attack permanently "
                + PrintColor.Red("increases this unit's ATK") + ", up to a total of 5 times.\n" +
                PrintColor.BYellow("\"Mercy\"") + ": Attacks against target with less than 40% HP applies "
                + PrintColor.Blue("1% 'Fragile'") + " on them, lasts for 10 turns.\n"
                + PrintColor.BRed("\"Collapsing Fear\":")
                + " Dealing " + PrintColor.Purple("magic damage") + " to all allied units once and " +
                "applies " + PrintColor.Purple("50% \"Grievous Wound\"")  + " on them in the next 3 turns. "
                + PrintColor.BRed("\"Collapsing Fear\"") + " immediately defeats any target that is " + PrintColor.Blue("'Fragile'") + ".\n"
                + "The first time HP drops to 0, immediately casts " + PrintColor.BRed("\"Collapsing Fear\"") + ", then revives with full HP and enters the second phase.\n" +
                PrintColor.BYellow("\nDuring the second phase:")
                + "\n- " + PrintColor.BRed("\"Set the Stage\"") + " is reset."
                + "\n- " + PrintColor.Red("\"Collapsing Fear\"") + " can be casted periodically.");
        setMaxHealth(15000);
        setAtk((short) 500);
        setDef((short) 500);
        setRes((short) 500);
        setAp((short) 0);
        setDefPen((short) 50);
        threshold = 0.4f;
        atkAdd = (short) (getAtk() * 0.2f);
    }

    @Override
    public void normalAttack(Entity target) {
        if (special) {
            EntitiesList.EnList.forEach(e -> {
                if (e instanceof EnThrow) dealingDamage(e, 9999, "Collapsing Fear", PrintColor.Bred, true);
            });
            special = false;
        }
        else {
            if ((this.mana >= 2 && !canRevive) || opener) {
                castSkill();
                return;
            }
            ++mana;
            target = getTaunt(target);
            int damage = damageOutput((int) (getAtk() + (target.getHealth() * hpscale)), getAp(), target);
            dealingDamage(target, damage);

            if (target.getHealth() <= target.getMaxHealth() * threshold)
                target.fragile.setValue((short) 1, (short) 10);

            if (this.mana == 2 && !canRevive)
                System.out.println(PrintColor.Bpurple + "His blade is oozing ominous vibes");

            if (stack < 5) {
                addAtk(atkAdd);
                ++stack;
            }
        }
    }

    public void castSkill() {
        float scale = challengeMode ? 2f : 1.5f;
        this.addResPen((short) 50);
        EntitiesList.SoList.forEach(so -> {
            if (so.isAlive()) {
                int spellDmg = damageOutput(0, (int) (getAtk() * scale), so);
                if (so.fragile.inEffect()) {
                    dealingDamage(so, 9999, "Collapsing Fear", PrintColor.Bred, true);
                }
                else {
                    dealingDamage(so, spellDmg, "Collapsing Fear", PrintColor.purple);
                }
                so.grievouswound.setValue((short) 50, (short) 3);
            }
        });
        if (challengeMode && !canRevive) this.shelter.setValue((short) 50, (short) 3);
        this.addResPen((short) -50);
        if (!opener) mana -= 2;
        opener = false;
    }

    @Override
    public void revive() {
        setMaxHealth((int) (getMaxHealth() * 1.0f));
        if (challengeMode) {
            hpscale = 0.35f;
            canCC = false;
        }
        addDef((short) 200);
        addRes((short) 200);
        super.revive();
        castSkill();
        mana = 0;
        System.out.println(PrintColor.Bred + "\nCollapse: " + PrintColor.red + getName() + " has increased stats and " +
                "can now cast \"Collapsing Fear\" periodically!");
    }

    @Override
    public void setChallengeMode() {
        challengeMode = true;
        setName("Emperor's Blade - The Pursuer");
        setTrait("He once ravaged the demons of the Northern Tundra, segregating the outsiders beyond the reach of civilization." +
                "\nHis blade shies not from royals nor nobles, safeguarding glory from the dusts of rebellion." +
                "\nEvery Royal Guard is as a dominion; the land beneath their feet is nothing but their territory.");
        setSkill(PrintColor.BRed("Set the Stage") + ": First attack instead casts " + PrintColor.Red("\"Collapsing Fear\"") + ", can only happen once.\n" +
                PrintColor.BRed("Dominion") + ": Attack deals " + PrintColor.Red("additional physic damage equals to a percentage of target's current HP") +
                " and " + PrintColor.Red("ignores a certain amount of defense") + ", each attack permanently "
                + PrintColor.Red("increases this unit's ATK") + ", up to a total of 5 times.\n" +
                PrintColor.BYellow("\"Mercy\"") + ": Attacks against target with less than 50% HP applies "
                + PrintColor.Blue("1% 'Fragile'") + " on them, lasts for 10 turns.\n"
                + PrintColor.BRed("\"Collapsing Fear\":")
                + " Dealing " + PrintColor.Purple("magic damage") + " to all allied units once and " +
                "applies " + PrintColor.Purple("50% \"Grievous Wound\"")  + " on them in the next 3 turns. "
                + PrintColor.BRed("\"Collapsing Fear\"") + " immediately defeats any target that is " + PrintColor.Blue("'Fragile'") + ".\n"
                + "The first time HP drops to 0, immediately casts " + PrintColor.BRed("\"Collapsing Fear\"") + ", then revives with full HP and enters the second phase.\n" +
                PrintColor.BYellow("\nDuring the second phase:")
                + "\n- Now becomes immune to " + PrintColor.Blue("crowd-control effects") + "."
                + "\n- " + PrintColor.BRed("\"Set the Stage\"") + " is reset."
                + "\n- " + PrintColor.Red("\"Collapsing Fear\"") + " can be casted periodically and " + PrintColor.Blue("renders self with 50% 'Shelter'") + ".");
        setMaxHealth(18000);
        setAtk((short) 300);
        setDef((short) 500);
        setRes((short) 500);
        setDefPen((short) 50);
        canCC = false;
        atkAdd = (short) (getAtk() * 0.2f);
        hpscale = 0.25f;
        threshold = 0.5f;
    }
}
