package com.company.Entitiy.Enemy;

import com.company.PrintColor;

public class EoE extends Enemy {
    public EoE() {
        setName("Essence of Evolution - Newborn");
        setTrait("A madman who should not have stepped upon this world. An oddity that should not have existed in the other world." +
                "\nThe union of the two gave birth to this node of \"evolution\" that far surpasses the limits of biology." +
                "\nIt's existence naturally contradicts all of the world. It should not live. It must not live.");
        setSkill(PrintColor.BYellow("Glory of Evolution"));
        setMaxHealth(10000);
        setAtk((short) 0);
        setAp((short) 0);
    }

    @Override
    public void setChallengeMode() {

    }
}
