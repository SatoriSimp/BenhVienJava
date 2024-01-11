package com.company;

import com.company.Entitiy.Allied.*;
import com.company.Entitiy.Enemy.*;
import com.company.Entitiy.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class EntitiesList {
    public static ArrayList<Enemy> EnList = new ArrayList<Enemy>();
    public static ArrayList<ArrayList<Enemy>> WaveEnList = new ArrayList<>();
    public static ArrayList<Soldier> SoList = new ArrayList<Soldier>();
    public static ArrayList<Entity> SummonList = new ArrayList<>();

    public EntitiesList(ArrayList<Soldier> So, ArrayList<Enemy> En) {
        EnList = (ArrayList<Enemy>) En.clone();
        SoList = (ArrayList<Soldier>) So.clone();
        EnList.removeIf(Objects::isNull);
        SoList.removeIf(Objects::isNull);
    }

    public EntitiesList(Soldier s, Enemy e) {
        EnList.add(e);
        SoList.add(s);
    }

    public static boolean Players_isAlive() {
        for (Soldier sol : SoList) {
            if (sol.isAlive()) return true;
        }
        return false;
    }

    public static boolean Enemies_isAlive() {
        for (Enemy en : EnList) {
            if ((en.isAlive() || en.isCanRevive()) && !(en instanceof Heatwave)) return true;
        }
        return false;
    }

    public static boolean Enemies_hasElite() {
        for (Enemy en : EnList) {
            if (en.isElite()) return true;
        }

        for (ArrayList<Enemy> lst : WaveEnList) {
            for (Enemy en : lst) {
                if (en.isElite()) return true;
            }
        }
        return false;
    }

    public static Enemy getFirstElite() {
        for (Enemy en : EnList) {
            if (en.isElite()) return en;
        }

        for (ArrayList<Enemy> lst : WaveEnList) {
            for (Enemy en : lst) {
                if (en.isElite()) return en;
            }
        }

        return null;
    }

    public static void Entities_getPreBattleEffect() {
        EnList.forEach(Enemy::preBattleSpecial);
        SoList.forEach(Soldier::preBattleSpecial);
    }

    public static void Entities_getAction() {
        EnList.forEach(Enemy::preTurnPreparation);
        SoList.forEach(Soldier::preTurnPreparation);

        SoList.forEach(so -> {
            updateStatus();
            if (so.isAlive()) {
                so.action();
                Wait.sleep(500);
            }
            updateStatus();
        });
        EnList.forEach(en -> {
            updateStatus();
            if (en.isAlive() && en.targetSearch() != null) {
                en.normalAttack(en.targetSearch());
                Wait.sleep(1000);
            }
            updateStatus();
            if (!en.isAlive() && en instanceof Slug) ((Slug) en).detonate();
        });
        SoList.forEach(so -> {
            so.fadeoutEffects();
            if (so.isAlive()) so.naturalRecovery();
            if (so instanceof Marksman) so.setTauntLevel(((Marksman) so).defaultTaunt);
        });

        AtomicInteger summon_mirage_V1 = new AtomicInteger(0);
        AtomicBoolean summon_mirage_V2 = new AtomicBoolean();
        AtomicInteger summon_awaken = new AtomicInteger();
        AtomicReference<Shapeshifter> s = new AtomicReference<>();
        EnList.forEach(en -> {
            if ((!en.isAlive() || (en instanceof  Awakening && ((Awakening) en).hibernating > 0 && ((Awakening) en).hiber)) && en.isCanRevive()) {
                en.revive();
                if (en instanceof Shapeshifter) {
                    s.set((Shapeshifter) en);
                }
            }
            en.fadeoutEffects();
            en.defBuff.refresh();
            if (en.isAlive()) en.naturalRecovery();
            if (en instanceof Awakening && ((Awakening) en).summon >= 4) {
                System.out.println(PrintColor.BRed("\nSummoned new AWKs!"));
                summon_awaken.set(2);
                if (((Awakening) en).summon == 99) {
                    summon_awaken.set(5);
                    ((Awakening) en).summon -= 88888;
                }
                else if (!en.isCanRevive()) summon_awaken.set(3);
                ((Awakening) en).summon -= 4;
            }
            if (en instanceof Ya && ((Ya) en).manaClone >= ((Ya) en).cloneSpawn) {
                summon_mirage_V1.set(1);
                if (((Ya) en).delSpawn) {
                    summon_mirage_V1.set(666);
                    ((Ya) en).delSpawn = false;
                }
                summon_mirage_V2.set(en.challengeMode);
                ((Ya) en).manaClone -= ((Ya) en).cloneSpawn;
            }
        });
        EnList.removeIf(e -> !e.isAlive() && e.isSummon);

        if (s.get() != null) {
            EnList.add(s.get());
            s.get().setReduction((short) (s.get().getReduction() + 20));
        }
        if (summon_mirage_V1.get() != 0) {
            EnList.add(new Ya(summon_mirage_V1.shortValue(), summon_mirage_V2.get()));
        }
        while (summon_awaken.getAndDecrement() > 0) {
            EntitiesList.EnList.add(new Awakening.Awk());
        }

        if (!SummonList.isEmpty()) {
            SummonList.forEach(su -> {
                su.isSummon = true;
                if (su instanceof Enemy) EnList.add((Enemy) su);
                else if (su instanceof Soldier) SoList.add((Soldier) su);
            });
            SummonList = new ArrayList<>();
        }

        SoList.forEach(so -> so.printHealthBar(PrintColor.yellow));
        EnList.forEach(en -> en.printHealthBar(PrintColor.red));
        System.out.println();
        Wait.sleep();
    }

    public static boolean addEnemy() {
        final boolean cm = (EnList.get(0).challengeMode);
        EnList.removeIf(e -> !e.isAlive());
        if (WaveEnList.isEmpty()) return false;
        EnList.addAll(WaveEnList.get(0));
        if (cm) EnList.forEach(EnemyBehaviors::setChallengeMode);
        WaveEnList.remove(WaveEnList.get(0));
        return true;
    }

    public static void updateStatus() {
        EnList.forEach(Enemy::update);
        SoList.forEach(Soldier::update);
    }
}
