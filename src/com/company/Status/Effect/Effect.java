package com.company.Status.Effect;

abstract class Effect {
    private short duration, value;

    public Effect() {
        this.duration = 0;
        this.value = 0;
    }

    public short getDuration() {
        return duration;
    }

    public void setDuration(short duration) {
        this.duration = duration;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short amount) {
        value = amount;
        duration = 1;
    }

    public void setValue(short amount, short duration) {
        if (!isBetter(amount, duration)) return;
        value = amount;
        this.duration = duration;
    }

    public void addVal(short amount) {
        value += amount;
    }

    public void extend(short dur) {
        duration += dur;
    }

    public void fadeout() {
        if (duration > 0) --duration;
        if (duration <= 0) value = 0;
    }

    public boolean inEffect() {
        return duration > 0;
    }

    public boolean isBetter(short newV, short newD) {
        return (newV > value) || (newV == value && newD > duration);
    }
}
