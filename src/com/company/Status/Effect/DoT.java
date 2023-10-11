package com.company.Status.Effect;

import com.company.Entitiy.Entity;

public class DoT extends Effect {
    private Entity Inflicter;

    public DoT() { super(); }

    public void initialize(short amount, short duration, Entity infl) {
        Inflicter = infl;
        this.setValue(amount, duration);
    }
}
