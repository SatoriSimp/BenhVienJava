package com.company.Status.Effect;

/**
 * <p>Reduces the damage taken by <span style="color:#00ECFF">the exact % of effect</span></p>
 * <p><b>Can also decreases <span style="color:white">true damage</b></span></p>
 */
public class Shelter extends Effect {
    public Shelter() {
        super();
    }

    public int reduce(int damage) {
        if (!this.inEffect()) return damage;
        return damage * (100 - this.getValue()) / 100;
    }
}
