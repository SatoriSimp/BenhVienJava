package com.company.Entitiy.Allied;

import com.company.EntitiesList;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

import java.util.HashMap;

public class Flincher extends Soldier {
    private float shockwaveScale;
    private static final HashMap<Entity, Integer> BleedingTrack = new HashMap<>();

    public Flincher() {
        setName("Flincher");
        shortDes = "";
        tar_1 = true;
        tar_2 = false;
        cost_1 = 2;
        cost_2 = 3;
        hp = 2600;
        atk = 700;
        defig = 160;
        def = 350;
        res = 100;
        mana = 3;
        shockwaveScale = 0.6f;
        setMaxHealth(hp);
        setAtk(atk);
        setDefIgn(defig);
        setDef(def);
        setRes(res);
        writeKit();
    }

    @Override
    public void writeKit() {
        talents = PrintColor.BRed("Expanded Cognition") + ": Attack ignores " + PrintColor.Red("160 DEF")
                + " and follows with an shockwave that deals " + PrintColor.Red(getAtk() * 6 / 10 + " (60% ATK) physic damage") + " to the target."
                + '\n' + gap_T
                + PrintColor.BRed("Accumulating Injuries") + ": Enemy hit by shockwave suffers 1 stack of " + PrintColor.BRed("Injury")
                + ". At 3 stacks, they become " + PrintColor.BRed("Bleeding") + " in the next 3 turns, continuously taking "
                + PrintColor.Red(getAtk() * 75 / 100 + " (75% ATK) + 1% target's max health as physic damage")
                + " and " + PrintColor.WHITE_BOLD + getAtk() * 75 / 100 + " (75% ATK) + 1% target's max health as true damage" + PrintColor.def + " at the beginning of each turn."
                + '\n' + gap_T
                + PrintColor.BBlue("Stable Esthesia") + ": When this unit is present, all team members gain " + PrintColor.Red("+6% ATK")
                + ", this unit gains " + PrintColor.Red("+10% ATK") + " instead.";
        s1_name = PrintColor.BRed("Nociceptor Inhibition");
        s1_des = "Launches an attack that deals " + PrintColor.Red((300 + getAtk() * 2) + " (300 + 200% ATK) physic damage")
                + " and follows with 2 additional shockwaves.";
        s2_name = PrintColor.BRed("Changeability of Perceptive Information");
        s2_des = "Fires an explosive grenade that hits all enemies, dealing " + PrintColor.Red(getAtk() * 12 / 10 + " (120% ATK) physic damage")
                + " and follows with a shockwave. The grenade additionally inflicts all target it hits with " + PrintColor.Cyan("25% 'Fragile'")
                + " before causing another explosion that deals " + PrintColor.Red(getAtk() * 8 / 10 + " (80% ATK) physic damage")
                + ", increased to " + PrintColor.Red(getAtk() * 32 / 10 + " (320% ATK) physic damage") + " if the target is " + PrintColor.BRed("Bleeding") + ".";
    }

    @Override
    public void preBattleSpecial() {
        if (preBattleEffectApplied) return;
        preBattleEffectApplied = true;
        EntitiesList.SoList.forEach(s -> {
            if (s == this) {
                s.setAtk((short) (s.getBaseAtk() * 1.1));
                s.setAp((short) (s.getBaseAp() * 1.1));
            }
            else {
                s.setAtk((short) (s.getBaseAtk() * 1.06));
                s.setAp((short) (s.getBaseAp() * 1.06));
            }
        });
    }

    @Override
    public void normalAttack(Entity target) {
        super.normalAttack(target);
        applyShockwave(target);
    }

    @Override
    public boolean castSkill_1(Entity target) {
        if (!super.castSkill_1(target)) return false;
        dealingDamage(target, damageOutput((int) (300 + getAtk() * 2.0f), 0, target), s1_name, PrintColor.red);
        applyShockwave(target);
        applyShockwave(target);
        applyShockwave(target);
        return true;
    }

    @Override
    public boolean castSkill_2(Entity target) {
        if (!super.castSkill_2(target)) return false;
        EntitiesList.EnList.forEach(en -> {
            if (en.isAlive()) {
                dealingDamage(en, damageOutput((short) (getAtk() * 1.2), 0, en), "Explosion", PrintColor.red);
                applyShockwave(en);
                en.fragile.setValue((short) 25, (short) 1);
            }
        });
        EntitiesList.EnList.forEach(en -> {
            if (en.isAlive()) {
                final float scl = (float) (en.bleed.inEffect() ? 3.2 : 0.8);
                dealingDamage(en, damageOutput((int) (getAtk() * scl), 0, en), "Second Iteration", PrintColor.Bred);
            }
        });
        return true;
    }

    public void applyShockwave(Entity target) {
        dealingDamage(target, damageOutput((int) (getAtk() * shockwaveScale), 0, target));
        if (BleedingTrack.containsKey(target)) {
            if (BleedingTrack.get(target) >= 2) {
                System.out.println(PrintColor.Yellow(target.getName()) + " has been inflicted with " + PrintColor.Red("Bleeding!"));
                target.bleed.initialize((short) (this.getAtk() * 1.5f + target.getMaxHealth() * 0.02f), (short) 3, this);
                BleedingTrack.remove(target);
            }
            else BleedingTrack.replace(target, BleedingTrack.get(target) + 1);
        }
        else BleedingTrack.put(target, 1);
    }
}
