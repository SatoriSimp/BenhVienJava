package com.company.Entitiy.Allied;

import com.company.EntitiesList;
import com.company.Entitiy.Enemy.Enemy;
import com.company.Entitiy.Entity;
import com.company.PrintColor;
import com.company.Wait;

public class Defender extends Soldier {
    private short armorAdd;
    private short resAdd;
    private short damageAdd;

    public Defender() {
        setName("Defender");
        shortDes = "Sacrificing damage in exchange for high durability";
        tar_1 = false;
        tar_2 = false;
        cost_1 = 2;
        cost_2 = 3;
        hp = 3800;
        atk = 220;
        ap = 0;
        def = 680;
        res = 300;
        setMaxHealth(hp);
        setAtk(atk);
        setDef(def);
        setRes(res);
        writeKit();
    }

    @Override
    public void writeKit() {
        armorAdd = (short) (getBaseDef() * 60 / 100);
        resAdd = (short) (getBaseRes() * 12 / 10);
        damageAdd = (short) (getMaxHealth() * 15 / 100);
        talents = PrintColor.BRed("Unyielding") + ": Attack deals " + PrintColor.Red((getAtk() + getDef() * 6 / 10 + getRes() * 12 / 10) + " (100% ATK + 60% DEF + 120% RES) physical damage")+ ".\n" + gap_T
                + PrintColor.BPurple("Unflinching") + ": Damaging targets that are being " + PrintColor.Yellow("'Taunted'") + " deals an additional "
                + PrintColor.Red(damageAdd + " (15% HP) physic damage") + ".\n" + gap_T
                + PrintColor.BBlue("Counterattack") + ": When being attacked, dealing " + PrintColor.Red((getAtk() + getDef() * 5 / 10 + getRes()) + " (100% ATK + 50% DEF + 100% RES) physic damage")
                + " to the enemy that attacks this unit and " + PrintColor.Blue("reduces their ATK by 10%") + " in 2 turns.";
        s1_name = PrintColor.BGreen("Reinforcement");
        s1_des = "Immediately heals self for " + PrintColor.Green((100 + getMaxHealth() / 10 + getMissingHealth() / 5) + " (100 + 10% HP + 20% missing HP) HP");
        s2_name = PrintColor.BYellow("Form-up");
        s2_des = "Stop attacking, temporary gains " + PrintColor.Yellow(armorAdd + " (60% base DEF) DEF")
                + " and " + PrintColor.Cyan(resAdd + " (120% base RES) RES")
                + ". Then " + PrintColor.Blue("'Taunt'") + " all enemies within this turn.";
    }

    public int reflectDmg(Entity target) {
        int dmg = damageOutput(getAtk() + getDef() * 5 / 10 + getRes(), 0, target);
        if (target.taunt.inEffect()) dmg += damageOutput(damageAdd, 0, target);
        return dmg;
    }

    @Override
    public void normalAttack(Entity target) {
        if (this.taunt.inEffect() && this.taunt.getTaunter().isAlive()) target = this.taunt.getTaunter();
        int damage = damageOutput(getAtk() + getDef() * 6 / 10 + getRes() * 12 / 10, getAp(), target);
        if (target.taunt.inEffect()) damage += damageOutput(0, damageAdd, target);
        normalAttack(target, damage);
    }

    @Override
    public boolean castSkill_1(Entity target) {
        if (!super.castSkill_1(target)) return false;
        int healing = 100 + getMaxHealth() / 10 + this.getMissingHealth() / 5;
        this.healing(healing);
        Wait.sleep();
        return true;
    }

    @Override
    public boolean castSkill_2(Entity target) {
        if (!super.castSkill_2(target)) return false;
        this.defBuff.initialize(armorAdd, resAdd, (short) 1);
        System.out.println(PrintColor.yellow + getName() + PrintColor.def + " taunts their enemies!");
        EntitiesList.EnList.forEach(enemy -> {
            if (enemy.canCC) {
                enemy.taunt.setTaunter(this);
                enemy.taunt.extend((short) 1);
                enemy.printHealthBar(PrintColor.purple);
                System.out.println();
            }
        });
        return true;
    }
}
