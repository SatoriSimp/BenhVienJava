package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;
import com.company.Wait;

public class StmKgt extends Enemy {
    private short skillCost = 4;
    private short defReduce, skillScale;
    private short defLoss = 0, resLoss = 0;
    public boolean special = false, selfDebuff = false;

    public StmKgt() {
        setMaxHealth(18000);
        setAtk((short) 700);
        setDef((short) 2000);
        setRes((short) 600);
        setShield((short) 4);
        mana = 4;
        setCanRevive(true);
        isElite = true;
        canCC = false;
        defReduce = -60;
        skillScale = 125;
        setName("The Last Steam Knight");
        setTrait("The glorious Victorian Steam Knights represent the strongest of Victoria's martial prowess and " +
                "the highest honor awarded to a soldier.\nThe last of the Steam Knights stands guard in the dusty ruins, " +
                "and charges forth to protect the Victoria in his heart.");
        setSkill(
                PrintColor.BGreen("Armament of Annihilation") + ": Gains " + PrintColor.green + "4 layers of 'Shields'" + PrintColor.def + " at the start of the battle.\n" +
                PrintColor.BBlue("Dauntless Charge") + ": Prioritize attacks the unit with " + PrintColor.UYellow("highest DEF") + " and " + PrintColor.Blue("reduce their armor by " + Math.abs(defReduce) + "% in 3 turns\n") +
                PrintColor.BRed("Unto One's Death") + ": Constantly searches and locks-on target with " + PrintColor.UGreen("highest HP percentage") + " and launch 4 attacks on them consecutively, " + PrintColor.red +
                "dealing physic damage with each attack" + PrintColor.def + ".\nAfter the attack end, " + PrintColor.Blue("DEF and RES are significantly reduced") + " within the next turn." +
                PrintColor.Byellow + "\n\nDuring the second phase:" + PrintColor.def +
                "\n- " + PrintColor.Blue("DEF is reduced") + ", " + PrintColor.BGreen("\"Armament of Annihilation\"") + " is refreshed" +
                "\n- " + PrintColor.BBlue("\"Dauntless Charge\"") + " targets up to 2 units simultaneously." +
                "\n- " + PrintColor.BRed("\"Unto One's Death\"") + " can launch 2 additional hits.");
    }

    @Override
    public void normalAttack(Entity target) {
        if (selfDebuff) {
            addDef(defLoss);
            addRes(resLoss);
            defLoss = 0;
            resLoss = 0;
            selfDebuff = false;
        }
        if (this.mana >= skillCost) {
            castSkill();
            return;
        }
        target = targetSearch(HIGHEST_DEF);
        Entity target2 = null;
        if (!isCanRevive()) target2 = targetSearch(6);
        target.defBuff.initialize((short) 0, (short) 0, defReduce, (short) 0, (short) 4);
        super.normalAttack(target);
        if (target2 != null) {
            if (target2.defBuff.inEffect() && !target2.defBuff.isBuff()) target2.defBuff.setDuration((short) 4);
            else target2.defBuff.initialize((short) 0, (short) 0, defReduce, (short) 0, (short) 4);
            super.normalAttack(target2);
            --mana;
        }
    }

    public void castSkill() {
        this.mana -= skillCost;
        int baseDmg = getAtk() * skillScale / 100;
        byte hits = (byte) (isCanRevive() ? 4 : 6);
        if (special) hits++;
        for (byte i = 0; i < hits; ++i) {
            int mbonus = special ? (int) (getAtk() * 0.25) : 0;
            if (!EntitiesList.Players_isAlive()) {
                addDefIgn((short) -50);
                return;
            }
            Entity tar = targetSearch(HIGHEST_HP_PERCENTAGE);
            dealingDamage(tar, damageOutput(baseDmg, 0, mbonus, tar), "Unto One's Death", PrintColor.red);
            Wait.sleep(1000);
        }

        if (!challengeMode) {
            defLoss = (short) (getBaseDef() * 0.6);
            resLoss = (short) (getBaseRes() * 0.6);
            selfDebuff = true;
            addDef((short) (defLoss * -1));
            addRes((short) (resLoss * -1));
        }
        if (special) setShield((short) Math.max(getShield(), 3));
    }

    @Override
    public void revive() {
        if (selfDebuff) {
            addDef(defLoss);
            addRes(resLoss);
            defLoss = 0;
            resLoss = 0;
            selfDebuff = false;
        }
        super.revive();
        System.out.println(PrintColor.BRed("Keep marching on!"));
        setMaxHealth(getMaxHealth());
        setDef((short) (getBaseDef() * 0.35f));
        addAtk((short) (getBaseAtk() * 0.15f));
        setShield((short) 4);
        mana = 3;
        skillCost = 3;
        skillScale = 110;
    }

    @Override
    public void setChallengeMode() {
        defReduce = -80;
        ChallengeModeStatsUp();
        skill +=  "The DEF reduction effect from \"Dauntless Charge\" is strengthened, and \"Unto One's Death\" no longer " +
                "reduces self resistances upon finishing";
    }
}
