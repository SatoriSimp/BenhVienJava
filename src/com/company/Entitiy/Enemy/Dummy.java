package com.company.Entitiy.Enemy;

public class Dummy extends Enemy {
    public Dummy() {
        setName("Practice Dummy");
        setTrait("A dummy made of simple straws, for practicing purpose.\n" +
                "Despite their material, they are extremely durable and can soak heavy punishment, making them an excellent choice to test your fangs.\n" +
                "But, oh well, try not to straight up burn or explode them to pieces. It's not too hard to find a new one, but please don't do that.");
        setMaxHealth(30000);
        setAtk((short) 0);
        setDef((short) 0);
        setRes((short) 0);
        canCC = false;
        canAttack = false;
    }

    @Override
    public void setChallengeMode() {
    }
}
