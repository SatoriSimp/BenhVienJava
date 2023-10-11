package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;
import com.company.Wait;

public class StmKgt extends Enemy {
    private short skillCost = 4;
    private short defReduce, skillScale;

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
                "dealing physic damage with each attack" + PrintColor.def + ".\nAfter the attack end, self are affected with " + PrintColor.blue + "40% 'Fragile'" + PrintColor.def + " within the next turn." +
                PrintColor.Byellow + "\n\nDuring the second phase:" + PrintColor.def +
                "\n- DEF is reduced, " + PrintColor.BGreen("\"Armament of Annihilation\"") + " is refreshed" +
                "\n- " + PrintColor.BBlue("\"Dauntless Charge\"") + " targets up to 2 units simultaneously." +
                "\n- " + PrintColor.BRed("\"Unto One's Death\"") + " can launch 2 additional hits.");
    }

    @Override
    public void normalAttack(Entity target) {
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
        addDefIgn((short) 50);
        int baseDmg = getAtk() * skillScale / 100;
        final byte hits = (byte) (isCanRevive() ? 4 : 6);
        for (byte i = 0; i < hits; ++i) {
            if (!EntitiesList.Players_isAlive()) {
                addDefIgn((short) -50);
                return;
            }
            Entity tar = targetSearch(HIGHEST_HP_PERCENTAGE);
            dealingDamage(tar, damageOutput(baseDmg, 0, tar), "Unto One's Death", PrintColor.red);
            Wait.sleep(1000);
        }
        addDefIgn((short)-50);
        if (!challengeMode) this.fragile.setValue((short)40, (short)2);
    }

    @Override
    public void revive() {
        super.revive();
        setMaxHealth(getMaxHealth());
        setDef((short) (getBaseDef() * 0.3f));
        addAtk((short) (getBaseAtk() * 0.15f));
        setShield((short)4);
        mana = 3;
        skillCost = 3;
        skillScale = 110;
    }

    @Override
    public void setChallengeMode() {
        defReduce = -80;
        ChallengeModeStatsUp();
        skill +=  "The DEF reduction effect from \"Dauntless Charge\" is strengthened, and \"Unto One's Death\" no longer " +
                "inflicts 'Fragile' on self upon finishing";
    }
}
