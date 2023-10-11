package com.company.Status.Effect;

import com.company.Entitiy.Entity;

public class DefBuff extends Effect {
    private final Entity effectHolder;
    private short def_flat, res_flat;
    private short def_percentage, res_percentage;
    private short defAdd, resAdd;

    public DefBuff(Entity helder) {
        super();
        this.effectHolder = helder;
    }

    public void initialize(short def, short res, short defi, short resi, short dur) {
        endEffect();
        setDuration(dur);
        def_flat = def;
        res_flat = res;
        def_percentage = defi;
        res_percentage = resi;
        defAdd = (short) (def_flat + effectHolder.getBaseDef() * def_percentage / 100);
        resAdd = (short) (res_flat + effectHolder.getBaseRes() * res_percentage / 100);
        effectHolder.addDef(defAdd);
        effectHolder.addRes(resAdd);
    }

    public void initialize(short defi, short resi, short dur) {
        endEffect();
        setDuration(dur);
        def_flat = defi;
        res_flat = resi;
        defAdd = (short) (def_flat + effectHolder.getBaseDef() * def_percentage / 100);
        resAdd = (short) (res_flat + effectHolder.getBaseRes() * res_percentage / 100);
        effectHolder.addDef(defAdd);
        effectHolder.addRes(resAdd);
    }

    public boolean isBuff() {
        return (def_flat > 0 && defAdd > 0) || (res_flat > 0 && resAdd > 0);
    }

    @Override
    public void fadeout() {
        super.fadeout();
        if (!this.inEffect()) {
            effectHolder.addDef((short) (defAdd * -1));
            effectHolder.addRes((short) (resAdd * -1));
            defAdd = 0;
            resAdd = 0;
            def_flat = 0;
            res_flat = 0;
            def_percentage = 0;
            res_percentage = 0;
        }
    }

    public void endEffect() {
        effectHolder.addDef((short) (defAdd * -1));
        effectHolder.addRes((short) (resAdd * -1));
        defAdd = 0;
        resAdd = 0;
        def_flat = 0;
        res_flat = 0;
        def_percentage = 0;
        res_percentage = 0;
    }

    public void refresh() {
        if (getDuration() <= 0) return;
        effectHolder.addDef((short) (defAdd * -1));
        effectHolder.addRes((short) (resAdd * -1));
        defAdd = (short) (def_flat + effectHolder.getBaseDef() * def_percentage / 100);
        resAdd = (short) (res_flat + effectHolder.getBaseRes() * res_percentage / 100);
        effectHolder.addDef(defAdd);
        effectHolder.addRes(resAdd);
    }
}
