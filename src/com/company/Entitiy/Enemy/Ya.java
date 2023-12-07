package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;
import com.company.PrintColor;
import com.company.Wait;

public class Ya extends Enemy {
    public short manaClone = 2;
    public final short cloneSpawn = 3;
    public boolean delSpawn = false;
    private boolean highSummon = false, canSummon = true;
    public boolean skillCycle = false;

    public Ya() {
        setName("Ya");
        setTrait("The executor of the Feranmut, \"Ya\"." +
                "\nAccording to the Sui Regulators' ancient scrolls, one of the Feranmuts hunted by the Yan a thousand years ago, with the mystical ability to \"cut away the seasons and hold them at Its blossom\"." +
                "\nIts wounds still refuse to close, and It laps at them in fury and bewilderment... It returns once more to familiar Yan soil.");
        setSkill(PrintColor.BYellow("Spring's Birth") + ": Periodically summons a " + PrintColor.BYellow("\"Mirage\"") + " that can use some of Ya's abilities. When HP falls below a certain threshold, sacrifices a " + PrintColor.BYellow("Mirage")
                + " to restore HP and reduces the cool-down for the summon.\n"
            + PrintColor.BPurple("Wordly Self") + ": Ya blasts a shock burst of energy," + PrintColor.Purple(" dealing magic damage") + " to all allied units and reduces their DEF in 3 turns.\n"
            + PrintColor.BRed("Slices the Seasons") + ": Perform a powerful slash, "
                + PrintColor.Red("dealing physic damage") + " to an allied unit. "
                + "When " + PrintColor.BRed("Slices the Seasons") + " is used by Ya or their Mirage to attack a target, "
                + PrintColor.BYellow("all Ya and Ya's Mirage") + " will also perform an additional slash to that target.\n"
            + PrintColor.BYellow("\nDuring the second phase:\n")
            + PrintColor.BYellow("Autumn's Bounty") + ": Destroys all " + PrintColor.BYellow("Mirages") + " and permanently disables " + PrintColor.BYellow("Spring's Birth")
                + ". Immediately summons a " + PrintColor.BYellow("Delusion") + " that can use most of Ya's abilities. Gains greatly increased "
                + PrintColor.Yellow("DEF") + " and " + PrintColor.Cyan("RES") + " while Delusion is present.");
        setMaxHealth(25000);
        setDef((short) 500);
        setRes((short) 500);
        setAtk((short) 800);
        setAp((short) 0);
        mana = 0;
        canCC = false;
        canRevive = true;
        isElite = true;
        isSummon = false;
    }

    public Ya(short cloneSeed, boolean cyl) {
        if (cloneSeed == 1) {
            setName("Mirage");
            setTrait("A mirage, a delusion, a nonexistent form. It has no thought, nor it has its own mind, but it has purposes.");
            setMaxHealth(10000);
            setDef((short) 200);
            setRes((short) 200);
            setAtk((short) 700);
            setSkill(PrintColor.BRed("Slices the Seasons") + ": Perform a powerful slash, "
                    + PrintColor.Red("dealing physic damage") + " to an allied unit. "
                    + "When " + PrintColor.BRed("Slices the Seasons") + " is used by Ya or their Mirage to attack a target, "
                    + PrintColor.BYellow("all Ya and Ya's Mirages") + " will also perform an additional slash to that target.");
        }
        else {
            setName("Delusion");
            setTrait("Old wounds fester in suspicion and anger. Infinite seasons, finite lifespan, and It has returned home.");
            setMaxHealth(22000);
            setDef((short) 500);
            setRes((short) 500);
            setAtk((short) 900);
            highSummon = true;
            setSkill(
                    PrintColor.BPurple("Wordly Self") + ": \"Delusion\" blasts a shock burst of energy," + PrintColor.Purple(" dealing magic damage") + " to all allied units and reduces their DEF in 3 turns.\n"
                    + PrintColor.BRed("Slices the Seasons") + ": Perform a powerful slash, "
                    + PrintColor.Red("dealing physic damage") + " to an allied unit. "
                    + "When " + PrintColor.BRed("Slices the Seasons") + " is used by Ya or their Mirage to attack a target, "
                    + PrintColor.BYellow("all Ya and Ya's Mirage") + " will also perform an additional slash to that target.");
        }
        setAp((short) 0);
        this.challengeMode = cyl;
        if (challengeMode) {
            setMaxHealth((int) (getMaxHealth() * 1.5f));
            setAtk((short) (getBaseAtk() * 1.25f));
        }
        this.skillCycle = false;
        mana = 0;
        isElite = true;
        isSummon = true;
    }

    @Override
    public void normalAttack(Entity target) {
        if (!isSummon && canSummon) manaClone++;
        mana++;
        target = getTaunt(target);
        if (mana >= 2) {
            castSkill(target);
            return;
        }
        super.normalAttack(target);
    }

    public void castSkill(Entity target) {
        if (skillCycle) {
            skillCycle = false;
            mana = 1;
            for (Enemy e : EntitiesList.EnList) {
                if (!(e instanceof Ya) || !e.isAlive()) continue;

                if (!e.isSummon) {
                    e.dealingDamage(target, e.damageOutput(e.getAtk(), 0, target), "Slices the Seasons", PrintColor.Bred);
                }
                else {
                    e.dealingDamage(target, e.damageOutput((int) (e.getAtk() * 1.25f), 0, target), "Slices the Seasons", PrintColor.red);
                }
                Wait.sleep(1000);
            }
        }
        else {
            skillCycle = true;
            mana = 0;
            if (isSummon && !highSummon) {
                ((Entity)this).normalAttack(target);
                return;
            }

            for (Soldier s : EntitiesList.SoList) {
                if (!s.isAlive()) continue;
                if (!isSummon) dealingDamage(s, damageOutput(0, (int) (getAtk() * 0.7f), s), "Wordly Self", PrintColor.Bpurple);
                else dealingDamage(s, damageOutput(0, (int) (getAtk() * 0.85f), s), "Wordly Self", PrintColor.purple);
                s.defBuff.initialize((short) 0, (short) 0, (short) -30, (short) 0, (short) 3);
            }
        }
    }

    @Override
    public void revive() {
        EntitiesList.EnList.forEach(e -> {
            if (e instanceof Ya && e.isSummon)
                e.setHealth(0);
        });
        manaClone = cloneSpawn;
        delSpawn = true;
        setMaxHealth(getMaxHealth());
        setAtk((short) (getAtk() * 1.15f));
        super.revive();
        canSummon = false;
        System.out.println(PrintColor.BRed("Witness the seasons change once more!"));
    }

    @Override
    public void naturalRecovery() {
        if (!isSummon) {
            short cloneCNT = 0;
            Ya mirage = null;
            for (Enemy e : EntitiesList.EnList) {
                if (e instanceof Ya && e.isSummon && e.isAlive()) {
                    if (!e.getName().equals("Delusion")) mirage = (Ya) e;
                    if (((Ya) e).highSummon) cloneCNT += 3;
                }
            }
            if (this.getHealth() < (this.getMaxHealth() * 0.3) && mirage != null) {
                dealingDamage(mirage, 99999, "Ritual", PrintColor.Bgreen);
                final float healingScale = challengeMode ? 0.6f : 0.4f;
                this.healing((int) (getMaxHealth() * healingScale));
                manaClone += 2;
            }
            if (cloneCNT > 0) this.defBuff.initialize((short) 50, (short) 50, (short) (20 * cloneCNT), (short) (20 * cloneCNT), (short) 2);
        }
        else super.naturalRecovery();
    }

    @Override
    public void setChallengeMode() {
        challengeMode = true;
        ChallengeModeStatsUp();
        skill += "Mirages and Delusion have increased HP and ATK. And HP restore from sacrificing Mirages is increased.";
    }
}
