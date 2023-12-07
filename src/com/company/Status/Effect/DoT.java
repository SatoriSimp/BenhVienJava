package com.company.Status.Effect;

import com.company.Entitiy.Entity;

public class DoT extends Effect {
    private Entity Inflicter;

    public DoT() { super(); }

    public void initialize(short amount, short duration, Entity infl) {
        Inflicter = infl != null ? infl : new Entity() {
            @Override
            public void setDefPen(short defPen) {
                super.setDefPen((short) 0);
            }

            @Override
            public void setDefIgn(short defIgn) {
                super.setDefIgn((short) 0);
            }

            @Override
            public void setResPen(short resPen) {
                super.setResPen((short) 0);
            }

            @Override
            public void setResIgn(short resIgn) {
                super.setResIgn((short) 0);
            }
        };
        this.setValue(amount, duration);
    }

    public Entity getInflicter() { return Inflicter; }
}
