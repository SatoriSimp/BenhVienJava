package com.company.Status.Effect;

import com.company.Entitiy.Entity;

public class AtkDown extends Effect {
    private final Entity effectHolder;
    private short ad_flat, ap_flat;
    private short ad_percentage, ap_percentage;
    private short adAdd, apAdd;

    public AtkDown(Entity helder) {
        super();
        ad_flat = 0;
        ap_flat = 0;
        ad_percentage = 0;
        ap_percentage = 0;
        this.effectHolder = helder;
    }

    public void initialize(short pc, short duration) {
        if (compareTo(pc, pc, duration) == 0) return;
        else if (compareTo(pc, pc, duration) == 2) {
            setDuration(duration);
            return;
        }
        ad_percentage = pc;
        ap_percentage = pc;
        setDuration(duration);

        adAdd = (short) (ad_flat + effectHolder.getBaseAtk() * ad_percentage / 100);
        apAdd = (short) (ap_flat + effectHolder.getBaseAp() * ap_percentage / 100);
        effectHolder.addAtk((short) (adAdd * -1));
        effectHolder.addAp((short) (apAdd * -1));
    }

    public void initialize(short defp, short resp, short duration) {
        if (compareTo(defp, resp, duration) == 0) return;
        else if (compareTo(defp, resp, duration) == 2) {
            setDuration(duration);
            return;
        }
        else endEffect();
        ad_percentage = defp;
        ap_percentage = resp;
        setDuration(duration);
        adAdd = (short) (ad_flat + (effectHolder.getBaseAtk() * ad_percentage / 100));
        apAdd = (short) (ap_flat + (effectHolder.getBaseAp() * ap_percentage / 100));
        effectHolder.addAtk((short) (adAdd * -1));
        effectHolder.addAp((short) (apAdd * -1));
    }

    @Override
    public void fadeout() {
        super.fadeout();
        if (!inEffect()) endEffect();
    }

    private void endEffect() {
        effectHolder.addAtk(adAdd);
        effectHolder.addAp(apAdd);
        adAdd = 0;
        apAdd = 0;
        ad_flat = 0;
        ap_flat = 0;
        ad_percentage = 0;
        ap_percentage = 0;
    }

    /**
     *
     * @param pad
     * @param pap
     * @param dur
     * @return
     * 1 if the new debuff is stronger than the current<br>
     * 2 if the new debuff has longer duration and isn't weaker than current one<br>
     * 0 if the new debuff is weaker
     */
    private short compareTo(short pad, short pap, short dur) {
        if ((ad_flat == 0 && ad_percentage == 0 && effectHolder.getBaseAtk() > effectHolder.getBaseAp())
                || (ap_flat == 0 && ap_percentage == 0 && effectHolder.getBaseAtk() < effectHolder.getBaseAp()))
            return 1;

        if (pad > ad_percentage
                && effectHolder.getBaseAtk() >= effectHolder.getBaseAp()) return 1;
        else if (pap > ap_percentage
                && effectHolder.getBaseAtk() <= effectHolder.getBaseAp()) return 1;

        if (pad == ad_percentage && effectHolder.getBaseAtk() > effectHolder.getBaseAp() && dur >= getDuration()) return 2;
        return 0;
    }
}
