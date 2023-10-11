package com.company.Status.CC;

import com.company.Entitiy.Entity;

public class Taunt extends CC {
    private Entity Taunter;

    public Taunt() {
        super();
    }

    public Entity getTaunter() {
        return Taunter;
    }

    public void setTaunter(Entity taunter) {
        Taunter = taunter;
    }

    @Override
    public void fadeout() {
        super.fadeout();
        if (!inEffect()) setTaunter(null);
    }
}
