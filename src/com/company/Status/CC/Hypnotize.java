package com.company.Status.CC;

import com.company.Entitiy.Entity;

public class Hypnotize extends CC {
    private Entity Inflicter;

    public Hypnotize() {}

    public Entity getInflicter() { return Inflicter; }

    @Override
    public void fadeout() {
        super.fadeout();
        if (!inEffect()) Inflicter = null;
    }
}
