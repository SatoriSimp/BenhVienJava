package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

public class PlxClever extends Enemy {
    private boolean phalanx = false;
    private float deduct;
    private short atkgain;

    public PlxClever() {
        setName("Phalanx Spirit-cleaver");
        setTrait("A swordsman that specializes in melee combat.\n" +
                "Even though their strength is somewhat run of the mill, the injuries they inflict are paranormal, making it nigh impossible for the victims to recover, thus leaves them wide open and become extremely vulnerable to attacks.\n" +
                "The name \"Phalanx\" of theirs seems heavily inspired from a well-known ancient combat formation. Marching alongside, their morale will peak, causing them to inflict even more destruction.");
        setSkill(PrintColor.BRed("Soul Cleaver") + ": Attack permanently deducts target's max health, proportional to a percentage of damage dealt.\n" +
                PrintColor.BBlue("Enōmotía") + ": " + PrintColor.BYellow("Phalanx") + ". When marching alongside with other " + PrintColor.BYellow("Phalanx") + " enemies, ATK significantly increases.");
        setMaxHealth(10000);
        setDef((short) 350);
        setRes((short) 200);
        setAtk((short) 500);
        deduct = 0.65f;
        atkgain = 400;
    }

    @Override
    public void normalAttack(Entity target) {
        target = getTaunt(target);
        int dmg = damageOutput(getAtk(), getAp(), target);
        super.normalAttack(target, (int) (dmg * (1 - deduct)));
        final int hp_deduct = (int) (dmg * deduct);
        System.out.println(PrintColor.Red(target.getName() + "'s health is deducted by " + hp_deduct + "!"));
        target.setHealth(Math.max(target.getMaxHealth() - hp_deduct, 500));
    }

    @Override
    public void preBattleSpecial() {
        if (preBattleEffectApplied) return;
        preBattleEffectApplied = true;
        int plxcnt = 0;
        for (Enemy e : EntitiesList.EnList) if (e.getName().contains("Phalanx")) plxcnt++;
        if (plxcnt >= 2) {
            phalanx = true;
            this.addAtk(atkgain);
        }
    }

    @Override
    public void update() {
        if (!phalanx) return;
        int plxcnt = 0;
        for (Enemy e : EntitiesList.EnList) if (e.getName().contains("Phalanx") && e.isAlive()) plxcnt++;
        if (phalanx && (plxcnt < 2 || !this.isAlive())) {
            System.out.println(PrintColor.Blue("Enōmotía deactivated! The bonus ATK on self has been removed!"));
            phalanx = false;
            this.addAtk((short) -atkgain);
        }
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "ATK gains when 'Enōmotía' is active and max health deduction ratio increases.";
        deduct += 0.35f;
        atkgain += 200;
    }
}
