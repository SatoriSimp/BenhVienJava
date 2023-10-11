package com.company.Status.Effect;

import com.company.Entitiy.Entity;

public class AtkBuff extends Effect {
    private final Entity effectHolder;
    private short ad_flat, ap_flat;
    private short ad_percentage, ap_percentage;
    private short adAdd, apAdd;

    public AtkBuff(Entity helder) {
        super();
        ad_flat = 0;
        ap_flat = 0;
        ad_percentage = 0;
        ap_percentage = 0;
        this.effectHolder = helder;
    }

    public void initialize(short ad, short ap, short duration) {
        if (compareTo(ad, ap, duration) == 0) return;
        else if (compareTo(ad, ap, duration) == 2) {
            setDuration(duration);
            return;
        }
        ad_flat = ad;
        ap_flat = ap;
        setDuration(duration);
        ad_percentage = 0;
        ap_percentage = 0;
        adAdd = ad_flat;
        apAdd = ap_flat;
        effectHolder.addAtk(adAdd);
        effectHolder.addAp(apAdd);
    }

    public void initialize(short ad, short ap, short pc, short duration) {
        if (compareTo(ad, ap, pc, pc, duration) == 0) return;
        else if (compareTo(ad, ap, pc, pc, duration) == 2) {
            setDuration(duration);
            return;
        }
        ad_flat = ad;
        ap_flat = ap;
        ad_percentage = pc;
        ap_percentage = pc;
        setDuration(duration);
        short isDebf_ad = 1, isDebf_ap = 1;
        if (ad_percentage < 0) {
            ad_percentage *= -1;
            isDebf_ad = -1;
        }
        if (ap_percentage < 0) {
            ap_percentage *= -1;
            isDebf_ap = -1;
        }
        adAdd = (short) (ad_flat + effectHolder.getBaseAtk() * ad_percentage / 100 * isDebf_ad);
        apAdd = (short) (ap_flat + effectHolder.getBaseAp() * ap_percentage / 100 * isDebf_ap);
        effectHolder.addAtk(adAdd);
        effectHolder.addAp(apAdd);
    }

    public void initialize(short ad, short ap, short defp, short resp, short duration) {
        if (compareTo(ad, ap, defp, resp, duration) == 0) return;
        else if (compareTo(ad, ap, defp, resp, duration) == 2) {
            setDuration(duration);
            return;
        }
        else endEffect();
        ad_flat = ad;
        ap_flat = ap;
        ad_percentage = defp;
        ap_percentage = resp;
        setDuration(duration);
        short isDebf_ad = 1, isDebf_ap = 1;
        if (ad_percentage < 0) {
            ad_percentage *= -1;
            isDebf_ad = -1;
        }
        if (ap_percentage < 0) {
            ap_percentage *= -1;
            isDebf_ap = -1;
        }
        adAdd = (short) (ad_flat + (effectHolder.getBaseAtk() * ad_percentage / 100) * isDebf_ad);
        apAdd = (short) (ap_flat + (effectHolder.getBaseAp() * ap_percentage / 100) * isDebf_ap);
        effectHolder.addAtk(adAdd);
        effectHolder.addAp(apAdd);
    }

    @Override
    public void fadeout() {
        super.fadeout();
        if (!inEffect()) endEffect();
    }

    private void endEffect() {
        effectHolder.addAtk((short) (adAdd * -1));
        effectHolder.addAp((short) (apAdd * -1));
        adAdd = 0;
        apAdd = 0;
        ad_flat = 0;
        ap_flat = 0;
        ad_percentage = 0;
        ap_percentage = 0;
    }

    public boolean isBuff() {
        return (adAdd > 0 && effectHolder.getBaseAtk() > effectHolder.getBaseAp())
                || (apAdd > 0 && effectHolder.getBaseAtk() < effectHolder.getBaseAp());
    }

    private short compareTo(short fad, short fap, short pad, short pap, short dur) {
        if ((ad_flat == 0 && ad_percentage == 0 && effectHolder.getBaseAtk() > effectHolder.getBaseAp())
                || (ap_flat == 0 && ap_percentage == 0 && effectHolder.getBaseAtk() < effectHolder.getBaseAp()))
            return 1;

        if ((fad + (pad * effectHolder.getBaseAtk() / 100) > ad_flat + (ad_percentage * effectHolder.getBaseAtk() / 100))
                && effectHolder.getBaseAtk() > effectHolder.getBaseAp()) return 1;
        else if ((fap + (pap * effectHolder.getBaseAp() / 100) > ap_flat + (ap_percentage * effectHolder.getBaseAp() / 100))
                && effectHolder.getBaseAtk() < effectHolder.getBaseAp()) return 1;

        if (dur >= getDuration()) return 2;
        return 0;
    }

    private short compareTo(short fad, short fap, short dur) {
        if ((ad_flat == 0 && effectHolder.getBaseAtk() > effectHolder.getBaseAp()) || (ap_flat == 0 && effectHolder.getBaseAtk() < effectHolder.getBaseAp()))
            return 1;

        if (fad > ad_flat && effectHolder.getBaseAtk() > effectHolder.getBaseAp()) return 1;
        else if (fap > ap_flat && effectHolder.getBaseAtk() < effectHolder.getBaseAp()) return 1;

        if (dur >= getDuration()) return 2;
        return 0;
    }
}
