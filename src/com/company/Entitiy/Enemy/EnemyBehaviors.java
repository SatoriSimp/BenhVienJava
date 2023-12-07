package com.company.Entitiy.Enemy;

import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;

public interface EnemyBehaviors {
    void normalAttack(Entity target);
    void setChallengeMode();
    Soldier targetSearch();
    Soldier targetSearch(int searchType);
}
