package com.company.Status.Effect;

public class GrievousWound extends Effect {
    public GrievousWound() {
        super();
    }

    public int reduce(int amount) {
        if (!this.inEffect()) return amount;
        return amount * (100 - getValue()) / 100;
    }
}
