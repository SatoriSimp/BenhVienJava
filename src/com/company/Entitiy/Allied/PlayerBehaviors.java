package com.company.Entitiy.Allied;

import com.company.Entitiy.Entity;

public interface PlayerBehaviors {
    short action();
    void writeKit();
    boolean castSkill_1(Entity target);
    boolean castSkill_2(Entity target);
}
