package com.company.Status.Effect;

/**
 * <p>Amplifies the damage taken by <span style="color:#00ECFF">the exact % of effect</span></p>
 * <p><b>Can also enhances <span style="color:white">true damage</b></span></p>
 */
public class Fragile extends Effect {
    public Fragile() {
        super();
    }

    public int amplify(int damage) {
        if (!this.inEffect()) return damage;
        return damage * (100 + getValue()) / 100;
    }
}
