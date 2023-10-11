package com.company;

public class Wait extends Thread {
    public Wait() {
    }

    public static void sleep() {
        sleep(2000);
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
