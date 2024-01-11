package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

import static com.company.EntitiesList.SummonList;

public class Root extends Enemy {
    private short spawnCnt = 0;

    public Root() {
        setName("Branch of the Sanguinaria");
        setTrait("");
        setSkill(PrintColor.BYellow("Reshaping Burden") +
                ": Does not attack. Periodically spawns an \"Sanguinary Royal Court Soldier\" or \"Rotchaser\".");
        setMaxHealth(25_000);
        setDef((short) 800);
        setRes((short) 800);
        isElite = true;
        mana = 3;
        canRevive = true;
    }

    @Override
    public void normalAttack(Entity target) {
        if (mana >= 3) {
            System.out.println(PrintColor.Red("New enemy is ready to join the fray!"));
            if (spawnCnt <= 2) {
                if (!challengeMode) SummonList.add(new RoyalCourt());
                else SummonList.add(new RoyalCourt((short) 1));
                spawnCnt++;
            }
            else {
                if (!challengeMode) SummonList.add(new Rotchaser());
                else SummonList.add(new Rotchaser((short) 1));
                spawnCnt = 0;
            }
            mana -= 3;
            return;
        }
        mana++;
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "Spawn enemy changes!";
    }
}
