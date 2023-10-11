package com.company.Entitiy.Allied;

import com.company.PrintColor;

public class Flincher extends Soldier {
    private float shockwaveScale;
    private short shockwave;

    public Flincher() {
        setName("Flincher");
        shortDes = "";
        tar_1 = true;
        tar_2 = false;
        cost_1 = 3;
        cost_2 = 3;
        hp = 2600;
        atk = 600;
        defig = 160;
        def = 350;
        res = 100;
        mana = 3;
        shockwaveScale = 0.6f;
        shockwave = 1;
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
                + PrintColor.Red(getAtk() * 4 / 10 + " (40% ATK) physic damage")
                + " plus " + PrintColor.WHITE_BOLD + getAtk() * 4 / 10 + " (40% ATK) true damage" + PrintColor.def + " at the beginning of each turn."
                + '\n' + gap_T
                + PrintColor.BBlue("Stable Esthesia") + ": When this unit is present, all team members gain " + PrintColor.Red("+7% ATK")
                + ", this unit gains " + PrintColor.Red("+12% ATK") + " instead.";
        s1_name = PrintColor.BRed("Nociceptor Inhibition");
        s1_name = "Launch an attack that deals " + PrintColor.Red(getAtk() * 12 / 10 + " (120% ATK) physic damage")
                + " and follows with 2 additional shockwaves.";
    }
}
