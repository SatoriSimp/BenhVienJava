package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

import static com.company.EntitiesList.SoList;

public class Gravestone extends Enemy {
    private boolean cm = false;

    public Gravestone() {
        setName("Gravestone");
        setTrait("A shadow from the past, a warrior unbeknownst by all but the most intellected ones.\nHe is believed to have died a lifetime ago, yet here standing right in front of you, requesting a spar." +
                "\nHow come such aberration even happens doesn't matter.\nHe's here for you, and that's what counts.");
        setSkill(PrintColor.BRed("Insult to the Injuries") + ": Attack deals " + PrintColor.Red("additional physic damage")
                + " bases on the target's missing health. This bonus damage is significantly increased when the target's HP falls below a certain threshold.\n"
                + PrintColor.BBlue("Without a Trace") + ": Attack inflicts " + PrintColor.Blue("'Silence'") + " and "
                + PrintColor.Blue("'Weakening'") + " to the target. Additionally inflicts " +
                "them with " + PrintColor.Purple("'Grievous Wound'") + " when the target's HP falls below a certain threshold.\n"
                + PrintColor.BRed("Finishing Touch") + ": Launch an extra attack on all allies that are either " + PrintColor.Blue("'Weakened'")
                + " or " + PrintColor.Blue("'Silenced'") + ", dealing " + PrintColor.Red("physic damage") + ".");
        setMaxHealth(85000);
        setAtk((short) 500);
        setDefPen((short) 33);
        setDef((short) 300);
        setRes((short) 600);
        mana = 0;
    }

    @Override
    public void normalAttack(Entity target) {
        target = getTaunt(target);
        ++mana;
        final int pMissing = target.getMissingHealth() * 100 / target.getMaxHealth();
        int extra = (int) (getAtk() * 0.0295f * pMissing);
        if (pMissing > 50) {
            extra = (int) (extra * 1.35);
        }
        normalAttack(target, damageOutput(getAtk() + extra, getAp(), target));
        if (pMissing > 50) target.setHealingreduction((short) 50, (short) 2);
        target.silence.extend((short) 3);
        short ATK_debuff = (short) (cm ? 60 : 35);
        target.atkDebuff.initialize(ATK_debuff, (short) 2);

        if (mana >= 3) {
            System.out.println(PrintColor.BRed("Let's make this quick!"));
            SoList.forEach(so -> {
               if (so.isAlive() && (so.silence.inEffect() || so.atkDebuff.inEffect())) {
                   dealingDamage(so, damageOutput((int) (getAtk() * 0.5f + so.getMaxHealth() * 0.3f), 0, so), "Finishing Touch", PrintColor.red);
               }
            });
            mana -= 3;
        }
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        cm = true;
        skill += "The 'Weakening' effect is strengthened";
    }
}
