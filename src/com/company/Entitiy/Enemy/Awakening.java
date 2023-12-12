package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class Awakening extends Enemy {
    public short summon = 3;
    static boolean replace = false;
    private String summonName = PrintColor.BYellow("AWK-2 Replica");

    public boolean hiber = false;
    public short hibernating = 6;

    public Awakening() {
        setName("Awakening");
        setTrait("In a world where the strong seizes higher control, the weak can do nothing but to be preyed upon.\n" +
                "Hence, to them, watching their kinds being torn apart has become their regular routine.\n" +
                "Everytime they get to witness so, the fear and feud inside is gradually built up without they knowing. " +
                "Overtime, it became strong enough to make up its own mind, forming into a visible entity, namely \"Awakening\".\n" +
                "This self-proclaimed \"Dreamland\" that was personally created solely for that reason.\n" +
                "In here, no one will ever have to suffer the pain of being the prey, every kind will be the same.\n" +
                "Love and hope shall achieve that which this current cruel world cannot.");
        setSkill(PrintColor.BYellow("Resonance") + ": Periodically summons "
                + summonName + " to assist in combat. Upon receiving damage, "
                + PrintColor.Blue("50% of the damage will be transferred to all presenting ") + summonName + ".\n"
                + PrintColor.BPurple("Journey") + ": Normal attack " + PrintColor.Blue("depletes 1 mana")
                + " from the target. If mana is insufficient, that attack " + PrintColor.Purple("deals bonus magic damage") + " instead.\n"
                + PrintColor.BRed("Shattered Vision") + ": Charges up for an attack that deals "
                + PrintColor.Red("high physic damage") + " and hits all allied units, but is split equally amongst the team.\n"
                + PrintColor.BGreen("The Joy of Creation") + ": When HP drops to 0 for the first time, immediately destroys all presenting " + summonName + " then enters "
                + PrintColor.BBlue("Hibernating State")
                + " that lasts for 5 turns, during which this unit becomes "
                + PrintColor.BBlue("invulnerable") + " but cannot act. Upon entering, a large numbers of "
                + summonName + " are immediately summoned. After the "
                + PrintColor.BBlue("Hibernating State") + " ends or all summoned " + summonName + " are defeated, " +
                "whichever comes first, this unit will revive with " + PrintColor.Green("the amount of restored HP is based on the number of ")
                + summonName + PrintColor.Green(" that is still alive") + ". Then enters their second phase."
                + "\n\n" + PrintColor.BYellow("During the second phase:\n")
                + "- The damage penalty from " + PrintColor.BPurple("\"Journey\"") + " are increased.\n"
                + "- More " + summonName + " will be summoned overtime.\n"
                + "- " + PrintColor.BRed("Shattered Vision") + " now " + PrintColor.Red("ignores a percentage of target's DEF") + "." );
        isElite = true;
        setCanRevive(true);
        setMaxHealth(25000);
        setAtk((short) 100);
        setAp((short) 600);
        setDef((short) 500);
        setRes((short) 500);
    }

    public static class Awk extends Enemy {
        public Awk() {
            setName("AWK-2 Replica");
            setTrait("A failed attempts of the progress to form the fully generated \"Awakening\". " +
                    "Before perfection can be achieved, countless failures must be made. In the end, things have proven to be all worth it.");
            setSkill(PrintColor.BYellow("Resonance") + ": A percentage of damage taken by " + PrintColor.BYellow("Awakening")
                    + " is directed to self. This damage is distributed equally if more than one " + PrintColor.BYellow(getName()) + " are present." );
            setDef((short) 500);
            setRes((short) 650);
            setMaxHealth(3500);
            setAtk((short) 500);
            setAp((short) 0);
            isSummon = true;
            if (replace) setChallengeMode();
        }

        @Override
        public void normalAttack(Entity target) {
            target = getTaunt(target);
            normalAttack(target, damageOutput(getAtk(), getAp(), target));
        }

        @Override
        public void setChallengeMode() {
            setName("AWK-3 Bionic");
            setTrait("A failed attempts of the progress to form the fully generated \"Awakening\", was half-way to achieve succession. " +
                    "They have formed a barrier to ignore any outside interruptions that prevent them from achieving their goal. " +
                    "In the end, things have proven to be all worth it.");
            setSkill(PrintColor.BYellow("Resonance") + ": A percentage of damage taken by " + PrintColor.BYellow("Awakening")
                    + " is directed to self. This damage is distributed equally if more than one "
                    + PrintColor.BYellow(getName()) + " are present.\n"
                    + PrintColor.BYellow("Ignore Interfere") + ": Reduces damage taken from other sources, " +
                    "outside of damage transferred via " + PrintColor.BYellow("\"Resonance\"") + ".");
            setDef((short) 500);
            setRes((short) 650);
            setMaxHealth(4500);
            setAtk((short) 600);
            setAp((short) 100);
            setReduction((short) 50);
        }
    }

    @Override
    public void normalAttack(Entity target) {
        if (hibernating >= 1 && hiber) {
            if (hibernating == 1) hiber = false;
            return;
        }

        if (mana < 4) mana++;
        else {
            castSkill();
            mana -= 4;
            return;
        }
        if (summon < 4) summon++;

        target = getTaunt(target);
        int damage = damageOutput(getAtk(), getAp(), target);
        if (((Soldier) target).mana > 0) ((Soldier) target).mana--;
        else {
            short penl = (short) (isCanRevive() ? 7 : 11);
            damage += damageOutput(0, getAp() * penl / 10, target);
        }
        super.normalAttack(target, damage);
    }

    public void castSkill() {
        int baseDamage = (int) ((getAtk() + getAp()) * 6.5f);
        short cnt = 0;
        for (Soldier s : EntitiesList.SoList) if (s.isAlive()) cnt++;
        baseDamage /= cnt;
        final int baseDmg = baseDamage;
        if (!this.isCanRevive()) addDefPen((short) 50);
        EntitiesList.SoList.forEach(soldier -> {
            if (soldier.isAlive()) {
                dealingDamage(soldier, damageOutput(baseDmg, 0, soldier), "Shattered Vision", PrintColor.Bred);
            }
        });
        if (!this.isCanRevive()) addDefPen((short) -50);
    }

    @Override
    public void revive() {
        if (hiber) {
            boolean allDorGone = true;
            for (Enemy e : EntitiesList.EnList) {
                if (e instanceof Awk && e.isAlive())
                    allDorGone = false;
            }
            if (allDorGone) hibernating = 0;
            else hibernating--;
        }

        if (hibernating > 1) {
            System.out.println(PrintColor.BBlue('\n' + getName() + "'s hibernating remaining duration: " + (hibernating - 1)));
            if (hibernating == 6) {
                for (Enemy e : EntitiesList.EnList) {
                    if (e instanceof Awk && e.isAlive())
                        this.dealingDamage(e, 9999, "Deconstruction", PrintColor.Bred);
                }
                System.out.println(PrintColor.BBlue("Hibernation starts!"));
                while (this.grievouswound.inEffect()) this.grievouswound.fadeout();
                healing(1);
                this.shelter.setValue((short) 100, (short) 10);
                summon = 99;
                hiber = true;
            }
        }
        else {
            System.out.println(PrintColor.BRed("\nThis is the final experiment!"));
            short cnt = (short) 0;
            for (Enemy e : EntitiesList.EnList) {
                if (e instanceof Awk && e.isAlive()) {
                    this.dealingDamage(e, 9999, "Absorption", PrintColor.Bgreen);
                    cnt++;
                }
            }
            while (this.grievouswound.inEffect()) this.grievouswound.fadeout();
            while (this.shelter.inEffect()) this.shelter.fadeout();
            this.healing((int) (getMaxHealth() * 0.65f + getMaxHealth() * 0.07f * cnt), true);
            addAp((short) (getBaseAp() * 2 / 10));
            summon = 3;
            canRevive = false;
            hiber = false;
        }
    }

    @Override
    public void setChallengeMode() {
        summonName = PrintColor.BYellow("AWK-3 Bionic");
        setTrait(getTrait().replace("AWK-2 Replica", summonName));
        ChallengeModeStatsUp();
        skill += "All summoning AWK-2 Replica will be replaced by AWK-3 Bionic";
        replace = true;
    }
}
