package com.company.Entitiy.Allied;

import com.company.EntitiesList;
import com.company.Entitiy.Enemy.Enemy;
import com.company.Entitiy.Entity;
import com.company.Input;
import com.company.PrintColor;

public class Artificer extends Soldier {
    private short blades;
    private float def_scale, res_scale, atk_scale;
    private short preAR = 0, preMR = 0, preAD = 0;
    private final short blades_max = 80;

    public Artificer() {
        setName("Artificer");
        tar_1 = false;
        tar_2 = true;
        cost_1 = 2;
        cost_2 = 0;
        hp = 2760;
        atk = 550;
        ap = 0;
        def = 300;
        res = 100;
        blades = 80;
        def_scale = 3.35F;
        res_scale = 1.8F;
        atk_scale = 4.05F;
        setMaxHealth(hp);
        setAtk(atk);
        setAp(ap);
        setDef(def);
        setRes(res);
        writeKit();
    }

    @Override
    public void writeKit() {
        talents = PrintColor.BYellow("Art of Blades")
                + ": Controls up to " + PrintColor.BYellow( blades_max + " \"Spirit Blades\"") + " to assist in combat, every existing "
                + PrintColor.BYellow("\"Divine Blade\"") + " increases " + PrintColor.Yellow("DEF by " + def_scale)
                + " and " + PrintColor.Cyan("RES by " + res_scale) + ".\n" + gap_T
                + PrintColor.White("(Currently controlling " + PrintColor.BYellow(blades + " \"Spirit Blades\"") + PrintColor.white
                + ", gain a total of " + PrintColor.Yellow("+" + (blades * def_scale) + " DEF")
                + PrintColor.White(" and ") + PrintColor.Cyan("+" + (blades * res_scale) + " RES")) + PrintColor.White(")") + ".\n" + gap_T
                + PrintColor.BRed("Vanguard Edges") + ": " + PrintColor.Red("ATK +" + (getBaseAtk() * 40 / 100) + " (40% base ATK)")
                + " while at least " + PrintColor.BYellow("1 \"Spirit Blade\"") + " is under control. Attacking an enemy commands " + PrintColor.Yellow("\"Spirit Blades\"")
                + " to damage them, deals a total of "
                + PrintColor.Red((getAtk() + getAtk() * (atk_scale * 10) * blades / 1000)
                + " (" + (100 + atk_scale * blades) + "% ATK = [100% + " + atk_scale + "% for every existing blade]) physic damage")
                + ". Every attack generates an additional " + PrintColor.BYellow("15 \"Spirit Blades\"") + " (can not surpass 80).";
        s1_name = PrintColor.BGreen("Blessing Heroism");
        s1_des = "Consumes a customized number of "+ PrintColor.Yellow("\"Spirit Blades\"") + " (no greater than 40) to restore HP, heals self for "
                + PrintColor.Green("100 plus " + (8.5 + getMaxHealth() * 25 / 1000 + getAtk() * 39 / 1000) + " (8.5 + 0.25% HP + 3.9% ATK) HP per blade consumed")
                + ". Gains " + PrintColor.Blue("5% 'Shelter'") + " for 1 turn after this ability is used, increases by an additional 1.25% for every 1 blade consumed"
                + ". A maximum of " + PrintColor.Green((100 + (8.5 + getMaxHealth() * 0.0025 + getAtk() * 39 / 1000) * 40) + " HP") + " can be restored this way.";

        String[] cusName = {
                "Strike of Victory",
                "Whip of Conviction",
                "Birth of a Heroism"
        };
        String[] cusEff = {
                "Dealing physic damage to a single target and additional physic damage to all other enemies.",
                "Dealing huge physic damage to a single target.",
                "Dealing successive minor physic damage to a single target and generates \"Spirit Blades\" for each attack hit."
        };
        byte BladeSeed;
        if (blades > 60) {
            cusEff[0] = "The blades rush forwards, dealing "
                    + PrintColor.Red((700 + getAtk() * 410 / 100) + " (700 + 410% ATK) physic damage")
                    + " then spreading around the primal target, dealing another " + PrintColor.Red((525 + getAtk() * 300 / 100) + " (525 + 300% ATK) physic damage")
                    + " to all other enemies. " + PrintColor.Blue("Loses 50 Blades when finished") + ".";
            BladeSeed = 0;
        }
        else if (blades > 40) {
            cusEff[1] = "The blades form a whip and thrust forwards, dealing "
                    + PrintColor.Red((1250 + getAtk() * 600 / 100) + " (1250 + 600% ATK) physic damage") + ", ignores "
                    + PrintColor.Red("30% of the target's DEF") + ". " + PrintColor.Blue("Loses 35 Blades when finished") + ".";
            BladeSeed = 1;
        }
        else {
            cusEff[2] = "The blades form a swirl, repeatedly damage the target, dealing 5 instances of "
                    + PrintColor.Red((50 + getAtk() * 40 / 100) + " (50 + 40% ATK) physic damage")
                    + ". For every instance that hits the target, " + PrintColor.BYellow("5 \"Divine Blades\"") + " are generated.";
            BladeSeed = 2;
        }
        s2_name = PrintColor.BRed("Bladeworks");
        s2_des = "Attack an enemy, dealing " + PrintColor.Red((getAtk() * 215 / 100) + " (215% ATK) physic damage")
                + ".\n" + gap_S2 + "According to the number of \"Divine Blades\" currently have, commands them to perform one of these following attack:";
        for (byte i = 0; i < 3; ++i) {
            if (i == BladeSeed) s2_des += '\n' + gap_S2 + "- " + PrintColor.BRed(cusName[i]) + ": " + cusEff[i];
            else s2_des += '\n' + gap_S2 + "- " + PrintColor.White(cusName[i] + ": " + cusEff[i]);
        }
    }

    @Override
    public void normalAttack(Entity target) {
        if (isDisarmed) return;
        if (this.taunt.inEffect() && this.taunt.getTaunter().isAlive()) target = this.taunt.getTaunter();
        int damage = damageOutput((int) (getAtk() + getAtk() * atk_scale * 10 * blades / 1000), getAp(), target);
        normalAttack(target, damage);
        System.out.println(PrintColor.Yellow( getName() + " gains 12 Blades!"));
        blades = (short) Math.min(blades_max, blades + 15);
    }

    @Override
    public boolean castSkill_1(Entity target) {
        if (!super.castSkill_1(target) || this.blades < 1) return false;
        short consump = Input.Shrt("number of Blades to consume", (short) 1, (short) (Math.min((blades_max) / 2, blades)));
        int healing = (int) (100 + (8.5 + getMaxHealth() * 0.0025 + getAtk() * consump / 1000) * consump);
        this.shelter.setValue((short) (5 + consump * 1.25F), (short) 1);
        this.healing(healing);
        this.blades -= consump;
        return true;
    }

    @Override
    public boolean castSkill_2(Entity target) {
        if (!super.castSkill_2(target)) return false;
        if (this.blades <= 0) {
            System.out.println(PrintColor.Red("At least 5 existing Blades are required to cast this ability!"));
            return false;
        }
        dealingDamage(target, damageOutput(getAtk() * 225 / 100, 0, target), "Bladeworks", PrintColor.Bred);
        if (blades > 60) {
            int DirectScale = 700 + getAtk() * 410 / 100,
                SplashScale = 525 + getAtk() * 300 / 100;
            EntitiesList.EnList.forEach(en -> {
                if (en == target) dealingDamage(en, damageOutput(DirectScale, 0, en), "Strike of Victory - Primal", PrintColor.Bred);
                else dealingDamage(en, damageOutput(SplashScale, 0, en), "Strike of Victory - Splash", PrintColor.red);
            });
            this.blades -= 50;
        }
        else if (blades > 40) {
            int DirectScale = 1250 + getAtk() * 600 / 100;
            addDefPen((short) 30);
            dealingDamage(target, damageOutput(DirectScale, 0, target), "Whip of Conviction", PrintColor.Bred);
            addDefPen((short) -30);
            this.blades -= 35;
        }
        else {
            byte times = 5;
            int PerHit = 50 + getAtk() * 40 / 100;
            for (byte i = 1; i <= times; ++i) {
                boolean hit = (target.getShield() <= 0);
                dealingDamage(target, damageOutput(PerHit, 0, target), "Birth of a Hero", PrintColor.Bred);
                if (hit) {
                    short bladeGain = 5;
                    this.blades += bladeGain;
                    System.out.println(PrintColor.Yellow( getName() + " gains " + bladeGain + " Blades!"));
                }
            }
        }
        return true;
    }

    @Override
    public void update() {
        addAtk((short) (preAD * -1));
        if (this.blades > 0) preAD = (short) (getBaseAtk() * 40 / 100);
        else preAD = 0;
        addAtk(preAD);

        addDef((short) (preAR * -1));
        addRes((short) (preMR * -1));
        preAR = (short) (blades * def_scale);
        preMR = (short) (blades * res_scale);
        addDef(preAR);
        addRes(preMR);
    }
}
