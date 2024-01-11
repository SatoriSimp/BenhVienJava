package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

import java.util.ArrayList;

public class ColWarrior extends Enemy {
    private final ArrayList<Entity> TouchedCheck = new ArrayList<>();
    private short atkSteal;

    public ColWarrior() {
        setName("Collapsal Warrior");
        setTrait("The Sanguinaria's existence means its surrounding area is no longer safe to enter.\n" +
                "Once a zone is corrupted by some of the Sanguinaria's creatures, any being that steps upon them will slowly go insane.\n" +
                "Staying inside long enough, and one will certainly lose all of their minds. The term \"Collapsal\" is then used to describe those.");
        setSkill(PrintColor.BRed("Ever-reverberation") + ": The first attack made against a unit " + PrintColor.Red("deals bonus damage and steals a certain amount of their ATK") + ".\n"
                + PrintColor.BGreen("Motion") + ": Attack heals self for a percentage of damage dealt. " + PrintColor.Blue("Revives with reduced stats") + " when defeated and refreshes " + PrintColor.BRed("Ever-reverberation") + ".");
        setMaxHealth(9000);
        setAtk((short) 700);
        setAp((short) 0);
        setDef((short) 450);
        setRes((short) 300);
        setLifesteal((short) 50);
        atkSteal = 100;
        canRevive = true;
    }

    @Override
    public void normalAttack(Entity target) {
        target = getTaunt(target);
        int dmg = damageOutput(getAtk(), getAp(), target);
        if (!TouchedCheck.contains(target)) {
            dmg += damageOutput((int) (getAtk() * 0.33f), 0, (int) (target.getMaxHealth() * 0.25f), target);
            target.addAtk((short) -atkSteal);
            target.addAp((short) -atkSteal);
            this.addAtk(atkSteal);
            TouchedCheck.add(target);
        }
        super.normalAttack(target, dmg);
    }

    @Override
    public void revive() {
        super.revive();
        TouchedCheck.clear();
        final float ratio = challengeMode ? 1.25f : 0.75f;
        setMaxHealth((int) (getMaxHealth() * ratio));
        setAtk((short) (getBaseAtk() * ratio + TouchedCheck.size() * atkSteal));
        setDef((short) (getBaseDef() * ratio));
        setRes((short) (getBaseRes() * ratio));
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "ATK steal on first attack increases. Now revives with increased stats instead.";
        atkSteal += 50;
    }
}
