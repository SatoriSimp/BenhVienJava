package com.company.Status.CC;

public class CC {
    private short duration;

    public CC() {
        this.duration = 0;
    }

    public void extend(short amount) {
        duration += amount;
    }

    public void fadeout() {
        if (duration > 0) duration--;
    }

    public void cleanse() {
        duration = 0;
    }

    public boolean inEffect() {
        return duration > 0;
    }

    public boolean compareTo(CC other) {
        return this.duration >= other.duration;
    }
}
