package com.company.Entitiy.Enemy;

import com.company.Entitiy.Allied.Defender;

public class HeavyDefender extends Enemy {
    public HeavyDefender() {
        setName("Skirmishing Oracle");
    }

    @Override
    public void setChallengeMode() {

        ChallengeModeStatsUp();
        skill += "";
    }
}
