package com.company.Entitiy.Enemy;

public class Bishop extends Enemy {
    public Bishop() {
        setName("Intertwined Banshee");
        setTrait("");
        setSkill("");
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
    }
}
