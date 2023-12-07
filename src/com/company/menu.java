package com.company;

import com.company.Entitiy.Allied.*;
import com.company.Entitiy.Enemy.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.company.EntitiesList.*;

public class menu {
    static Clip clip;
    public static void playSound(String file) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (clip != null) clip.stop();
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File(file)));
        clip.start();
        clip.loop(100);
    }

    private static void getEntities(short mode) {
        ArrayList<Soldier> SoList = new ArrayList<>();
        ArrayList<Enemy> EnList = new ArrayList<>();
        int specialCombatEnv = 0;

        if (mode == 1) {
            ArrayList<String> disEnList = new ArrayList<>();
            Collections.addAll(disEnList,
                    "No enemy",
                    "Practice Dummy",
                    "Bewitched Dummy",
                    "Goblin Shaman / Senior Shaman",
                    "Zombified Orc / Prefect Zombified Orc",
                    "Vengeful Spirit / Hateful Spirit",
                    "Reaper",
                    "Enraged Possessed Bonethrower",
                    "Basin Infused Slug / Nourished Infused Slug",
                    "Venom Rock Spider",
                    "Gift of the Wild",
                    "Bloodthirsty Heir / Engorged Heir",
                    "Rotchaser / Lunatic Rotchaser",
                    "\"Redmark\" Eradicator",
                    "The Avenger",
                    "The Singer",
                    "Corrupted Bladeweaver / " + PrintColor.Red("Corrupted Worldcurser"),
                    PrintColor.red + "The Last Steam Knight",
                    PrintColor.red + "Emperor's Blade",
                    PrintColor.red + "The Forsaken One",
                    PrintColor.red + "Awakening",
                    PrintColor.Red("Ya"));
            AtomicInteger index = new AtomicInteger(0);
            disEnList.forEach(en -> {
                if (en == null) System.out.println(index.getAndIncrement() + " - No enemy");
                else
                    System.out.println(PrintColor.blue + index.getAndIncrement() + PrintColor.def + " - "
                            + PrintColor.yellow + en);
            });
            System.out.print(PrintColor.green);
            for (int i = 1; i <= 10; ++i) {
                Enemy pick;
                switch (Input.Shrt("enemy's ID", (short) 0, (short) (disEnList.size() - 1))) {
                    case 1:
                        pick = new Dummy();
                        break;
                    case 2:
                        pick = new BDummy();
                        break;
                    case 3:
                        pick = new GoblinShaman();
                        break;
                    case 4:
                        pick = new ZombifiedOrc();
                        break;
                    case 5:
                        pick = new Spirit();
                        break;
                    case 6:
                        pick = new Reaper();
                        break;
                    case 7:
                        pick = new EnThrow();
                        break;
                    case 8:
                        pick = new Slug();
                        break;
                    case 9:
                        pick = new VenomSpider();
                        break;
                    case 10:
                        pick = new Gift();
                        break;
                    case 11:
                        pick = new Heir();
                        break;
                    case 12:
                        pick = new Rotchaser();
                        break;
                    case 13:
                        pick = new Eradicator();
                        break;
                    case 14:
                        pick = new Avenger();
                        break;
                    case 15:
                        pick = new Singer();
                        break;
                    case 16:
                        pick = new PosSwrd();
                        break;
                    case 17:
                        pick = new StmKgt();
                        break;
                    case 18:
                        pick = new EB();
                        break;
                    case 19:
                        pick = new TFO();
                        break;
                    case 20:
                        pick = new Awakening();
                        break;
                    case 21:
                        pick = new Ya();
                        break;
                    default:
                        pick = null;
                }
                EnList.add(pick);

                if (pick != null) System.out.println(pick.getName() + " choosen!");
            }
        }
        else {
            System.out.println("Pick a package:");
            final String[] packages = {
                    "Nameless Shelter",
                    "Night of Ritual",
                    "Unhinged",
                    "No Time to Mourn",
                    "Slow yet Painful",
                    "\"Athanasios\"",
                    "Cover Your Wounds",
                    "After Dark",
                    "Ancient Formation",
                    "All Quite under the Thunder",
                    "A Feat of Patriotic",
                    "Survive on Cliff",
                    "Ei Magia"
            };
            final String[] des = {
                    "A shelter located inside the deep forest.\nEven though you have never been there before, the reminiscent feeling has already taken you over.",
                    "Their natures are meant to be decayed, yet they wish to chase for eternity.\nWe all know they can never have that.",
                    "Knowing who to prioritize makes a different between life and death.\nMisjudge the situation, then waste firepower on one target, whilst leaving the real threat unharmed, not wise at all...",
                    "In battle, every second counts.",
                    "",
                    "Well this is no good...",
                    "They overwhelm you and can sense you at your most disadvantages.\nBut even so, you have to fight back.",
                    "The path leads to home does not always feel the same. Especially when someone's lurking in that dark corner...",
                    "An ancient battlefield preserved in some arenas. Wishing to re-enact the competitive battles of the ancient knights, the opponents are monsters of desire and urbanity." +
                            "\nGlory, glory ceased to exist long ago.",
                    "The thunder is silent, the long night is dark, but our homeland's glory must stand eternal.",
                    "The will in these ruins is still not fallen - shall not be surmounted - cannot be exhausted - will do nothing but last forever.",
                    "To take down the target, you have to aim. Not there, but there.",
                    "If you lose, don't sweat it. You were never supposed to win this fight in the first place, after all."
            };

            int cnt = 1;
            for (String pName : packages) {
                System.out.println((cnt++) + ". " + pName);
            }

            int packageChoice = Input.Int("your choice", 1, packages.length);
            System.out.println('\n' + PrintColor.BRed(packages[packageChoice - 1]) + " chosen!");
            System.out.println(PrintColor.Blue(des[packageChoice - 1]) + '\n');
            Wait.sleep(3500);
            switch(packageChoice) {
                case 1:
                    System.out.println("Enemies selected: " + PrintColor.BYellow("The Avenger")
                        + PrintColor.Red("\nConditions: When \"The Avenger\" falls below 50% HP, max HP +200% and ATK gains on subsequence attacks increased by an additional 150%"));
                    Avenger av = new Avenger();
                    av.atkPerStrike_add += 300;
                    av.penPerStrike_add += 25;
                    av.healthAdd = true;
                    EnList.add(av);
                    break;
                case 2:
                    System.out.println("Enemies selected: " + PrintColor.BYellow("Enraged Possessed Bonethrower / Zombified Orc / Reaper")
                            + PrintColor.Red("\nConditions:\n- Enemy units have +40% max HP.\n- Zombified Orc additionally has +300% DEF and +500% RES.\n- Reaper has +600 RES and +40% DEF penetration.\n- Enraged Possessed Bonethrower's HP loss is greatly reduced."));
                    Collections.addAll(EnList, new EnThrow(), new ZombifiedOrc(), new Reaper());
                    EnList.forEach(en -> {
                        en.setMaxHealth(en.getMaxHealth() * 14 / 10);
                        if (en instanceof ZombifiedOrc) {
                            en.setDef((short) (en.getBaseDef() * 3));
                            en.setRes((short) (en.getBaseRes() * 5));
                        }
                        else if (en instanceof Reaper) {
                            en.setRes((short) (en.getBaseRes() + 600));
                            en.addDefPen((short) 40);
                        }
                        else if (en instanceof EnThrow) ((EnThrow) en).hpDecay = ((EnThrow) en).hpDecay * 3 / 10;
                    });
                    break;
                case 3:
                    System.out.println("Enemies selected: " + PrintColor.BYellow("Senior Goblin Shaman / Engorged Heir / Venom Rock Spider")
                            + PrintColor.Red("\nConditions: Goblin Shaman has greatly increased AP, and there will be an additional \"Twig of the Sanguinaria\""));
                    GoblinShaman gb = new GoblinShaman((short) 0);
                    gb.setAp((short) (gb.getBaseAp() * 2.1));
                    Collections.addAll(EnList, gb, new Heir((short) 0), new VenomSpider(), new Sanguinaria((byte) 2));
                    break;
                case 4:
                    System.out.println("Enemies selected: " + PrintColor.BYellow("Zombified Orc" + " * 3")
                            + PrintColor.Red("\nConditions: All Zombified Orc has greatly reduced HP, DEF and RES, and are unable to attack. A large number of \"Twigs of the Sanguinaria\" will appear."));
                    Collections.addAll(EnList, new ZombifiedOrc(), new ZombifiedOrc(), new ZombifiedOrc(), new Sanguinaria(), new Sanguinaria((byte) 2), new Sanguinaria());
                    EnList.forEach(en -> {
                        if (en instanceof ZombifiedOrc) {
                            en.isDisarmed = true;
                            en.setMaxHealth(100);
                            en.setDef((short) 0);
                            en.setRes((short) 0);
                        }
                    });

                    ((ZombifiedOrc)EnList.get(0)).hpDecay = 20;
                    ((ZombifiedOrc)EnList.get(1)).hpDecay = 17;
                    ((ZombifiedOrc)EnList.get(2)).hpDecay = 15;
                    break;
                case 5:
                    System.out.println("Enemy selected: " + PrintColor.BYellow("Nourished Infused Slug / Venom Rock Spider / Lunatic Rotchaser")
                        + PrintColor.Red("\nConditions: All enemies have increased DEF and RES, but decreased ATK."));
                    Collections.addAll(EnList, new Slug(0), new VenomSpider(), new Rotchaser(0));
                    EnList.forEach(e -> {
                        e.setDef((short) (e.getBaseDef() * 1.25));
                        e.setRes((short) (e.getBaseRes() * 1.25));
                        e.setAtk((short) (e.getBaseAtk() * 0.4));
                        e.setAp((short) (e.getBaseAp() * 0.7));
                    });
                    break;
                case 6:
                    System.out.println("Enemy selected: " + PrintColor.BYellow("\"The Singer\" / Engorged Heir")
                            + PrintColor.Red("\nConditions: Engorged Heir has increased AP and gains 'Shelter'."));
                    Heir h = new Heir((short) 0);
                    h.setAp((short) (h.getBaseAp() * 2));
                    h.shelter.setValue((short) 35, (short) 10);
                    Collections.addAll(EnList, new Singer(), h);
                    break;
                case 7:
                    System.out.println("Enemy selected: " + PrintColor.BYellow("\"Redmark\" Eradicator") + " * 5"
                            + PrintColor.Red("\nConditions: \"Redmark\" Eradicators have increased HP. Allied units lose a portion of HP at the start of battle and are affected with permanent \"Bleed\"."));
                    Collections.addAll(EnList, new Eradicator(), new Eradicator(), new Eradicator(), new Eradicator(), new Eradicator());
                    EnList.forEach(e -> {
                        e.setMaxHealth((int) (e.getMaxHealth() * 1.7f));
                    });
                    specialCombatEnv = 7;
                    break;
                case 8:
                    System.out.println("Enemy selected: " + PrintColor.BYellow("Gravestone"));
                    Gravestone gst = new Gravestone();
                    Collections.addAll(EnList, gst);
                    break;
                case 9:
                    System.out.println("Enemy selected: " + PrintColor.BYellow("Phalanx Spirit-clever / Phalanx Shadowiest Mage / Phalanx Renowned Frontliner"));
                    Collections.addAll(EnList, new PlxClever(), new PlxCaster(), new PlxPioneer(), new Mist());
                    break;
                case 10:
                    System.out.println("Enemy selected: " + PrintColor.BYellow("Shapeshifter")
                            + PrintColor.Red("\nConditions: More friendly units can be brought into this mission."));
                    EnList.add(new Shapeshifter());
                    specialCombatEnv = 9;
                    break;
                case 11:
                    System.out.println("Enemy selected: " + PrintColor.BYellow("The Last Steam Knight")
                            + PrintColor.Red("\nConditions: The Last Steam Knight has increased RES. \"Unto One's Death\" can launch an additional hit in both phases and each hit deals bonus true damage. " +
                            "\"Armament of Annihilation\" will be refreshed whenever \"Unto One's Death\" is used, but with reduced shields count."));
                    StmKgt stm = new StmKgt();
                    stm.setAtk((short) (stm.getBaseAtk() * 1.15));
                    stm.setRes((short) (stm.getBaseRes() + 200));
                    stm.special = true;
                    Collections.addAll(EnList, stm);
                    specialCombatEnv = 10;
                    break;
                case 12:
                    System.out.println("Enemy selected: " + PrintColor.BRed("\"Bridge Clip\" Cliff")
                            + PrintColor.Red("\nCondition: The \"Heat Waves Spout\" is present."));
                    EnList.add(new Cliff());
                    EnList.add(new Heatwave());
                    break;
                case 13:
                    System.out.println("Enemy selected: " + PrintColor.BRed("Qual")
                        + PrintColor.Red("\nConditions: More friendly units can be brought into this battle. All friendly units have increased RES and gain natural HP recovery."));
                    EnList.add(new Qual());
                    specialCombatEnv = 12;
                    break;
            }
            System.out.println();
        }
        ArrayList<Soldier> list = new ArrayList<>();
        //Champ select
        Collections.addAll(list, new Fighter(), new Defender(), new Marksman(), new Caster(), new Swordsman(),
                new Medic(), new Saigyouji(), new Artificer(), new Pathfinder(), new Hexer(), new Flincher());

        final int unitLim = (specialCombatEnv == 9 || specialCombatEnv == 12) ? 4 : 3;
        for (int i = 0; i < unitLim; ++i) {
            System.out.println(PrintColor.Byellow + "Choose unit's class:");
            AtomicReference<Short> order = new AtomicReference<>((short) 1);
            list.forEach(so -> {
                System.out.print(PrintColor.purple + order.get() + ". " + so.getName() + ": " + PrintColor.def + so.shortDes);
                System.out.println();
                order.set((short) (order.get() + 1));
            });

            System.out.print(PrintColor.WHITE_BOLD);
            short choice = Input.Shrt((short) 0, (short) (order.get() - 1));
            if (choice != 0) {
                Soldier s;
                switch (choice) {
                    case 1:
                        s = new Fighter();
                        break;
                    case 2:
                        s = new Defender();
                        break;
                    case 3:
                        s = new Marksman();
                        break;
                    case 4:
                        s = new Caster();
                        break;
                    case 5:
                        s = new Swordsman();
                        break;
                    case 6:
                        s = new Medic();
                        break;
                    case 7:
                        s = new Saigyouji();
                        break;
                    case 8:
                        s = new Artificer();
                        break;
                    case 9:
                        s = new Pathfinder();
                        break;
                    case 10:
                        s = new Hexer();
                        break;
                    case 11:
                        s = new Flincher();
                        break;
                    default:
                        s = null;
                        break;
                }
                SoList.add(s);
            }
        }

        new EntitiesList(SoList, EnList);

        switch (specialCombatEnv) {
            case 7:
                SoList.forEach(so -> {
                    so.burn.initialize((short) (so.getMaxHealth() * 0.3f), (short) 1, null);
                    so.bleed.initialize((short) (so.getMaxHealth() * 0.085f), (short) 100, null);
                });
                break;
            case 12:
                SoList.forEach(so -> {
                    so.setRes((short) (so.getBaseRes() + 300));
                    so.setRecovery((short) 180);
                });
                break;
        }

        System.out.println("Choose battle mode:" + "\n0. Experimental mode" + PrintColor.def + "\n1. Normal mode" + PrintColor.Yellow("\n2. Challenge Mode") + PrintColor.Red("\n3. Tribulation Mode"));
        switch (Input.Shrt((short) 0, (short) 3)) {
            case 0:
                System.out.println("Experimental mode selected!\nFriendlies have greatly increased base stats during this mode.");
                SoList.forEach(so -> {
                    if (so != null) {
                        so.setMaxHealth((int) (so.getMaxHealth() * 1.5f + 1000));
                        so.setAtk((short) (so.getBaseAtk() * 1.5f + 100));
                        so.setAp((short) (so.getBaseAp() * 1.5f + 100));
                        so.setDef((short) (so.getBaseDef() + 400));
                        so.setRes((short) (so.getBaseRes() + 400));
                        so.mana += 6;
                    }
                });
                break;
            case 2:
                System.out.println("Challenge Mode selected!");
                EnList.forEach(en -> {
                    if (en != null) en.setChallengeMode();
                });
                break;
            case 3:
                riskSelect();
        }
    }

    public static void riskSelect() {
        String[] riskName = {
                "Environment - Rusty Weapon I",
                "Environment - Rusty Weapon II",
                "Environment - Rusty Weapon III",
                "Tactic - Anti-armor I",
                "Tactic - Anti-armor II",
                "Environment - Exhaustion",
                "Environment - Erosion I",
                "Environment - Erosion II",
                "Environment - Erosion III",
                "Objective - Well-Equipped I",
                "Objective - Well-Equipped II",
                "Objective - Well-Equipped III",
                "Objective - High-value Targets I",
                "Objective - High-value Targets II",
                "Objective - High-value Targets III",
                "Deathmatch - Strengthened Will I",
                "Deathmatch - Strengthened Will II",
                "Deathmatch - Strengthened Will III",
                "Objective - Distinctive Darkness",
                "Hazard Environment - Toxicity Cloud I",
                "Hazard Environment - Toxicity Cloud II",
                "Hazard Environment - Toxicity Cloud III",
                "Objective - Reborn Anew I",
                "Objective - Reborn Anew II",
                "Objective - Reborn Anew III"
        };
        String[] effect = {
                "All friendly units have -15% ATK",
                "All friendly units have -30% ATK",
                "All friendly units have -50% ATK",
                "All friendly units have -30% DEF and RES",
                "All friendly units have -70% DEF and RES",
                "All friendly units have -3 initial mana",
                "All friendly units have -20% max HP",
                "All friendly units have -35% max HP",
                "All friendly units have -55% max HP",
                "All enemy units have +200 DEF",
                "All enemy units have +400 DEF and +200 RES",
                "All enemy units have +600 DEF and +400 RES",
                "All enemy units have +20% ATK",
                "All enemy units have +40% ATK",
                "All enemy units have +70% ATK",
                "All enemy units have +40% HP",
                "All enemy units have +70% HP",
                "All enemy units have +110% HP",
                "All friendly units require 1 more mana to activate their skills",
                "All friendly units are slightly 'Poisoned'",
                "All friendly units are 'Poisoned'",
                "All friendly units are heavily 'Poisoned'",
                "There will be a pre-spawned \"Twig of the Sanguinaria\"",
                "There will be two pre-spawned \"Twigs of the Sanguinaria\"",
                "There will be four pre-spawned \"Twigs of the Sanguinaria\""
        };
        short[] riskLvl = {1, 2, 3, 1, 2, 2, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 2, 1, 2, 3, 1, 2, 3};

        for (int i = 0; i < riskName.length; ++i) {
            final String msg = riskName[i] + ": " + effect[i];
            System.out.print(PrintColor.Cyan((i + 1) + " - "));
            switch (riskLvl[i]) {
                case 1:
                    System.out.println(PrintColor.Green( msg + " (▲)"));
                    break;
                case 2:
                    System.out.println(PrintColor.Yellow(msg + " (▲▼)"));
                    break;
                case 3:
                    System.out.println(PrintColor.Red(msg + " (▲▼▲)"));
            }
        }
        TreeSet<Integer> selectedRisk = new TreeSet<>();
        int select;
        int total = 0;
        while ((select = Input.Int("risk (enter 0 to complete)", 0, riskName.length)) != 0) {
            switch (select) {
                case 1: case 2: case 3:
                    selectedRisk.remove(1);
                    selectedRisk.remove(2);
                    selectedRisk.remove(3);
                    break;
                case 4: case 5:
                    selectedRisk.remove(4);
                    selectedRisk.remove(5);
                    break;
                case 7: case 8: case 9:
                    selectedRisk.remove(7);
                    selectedRisk.remove(8);
                    selectedRisk.remove(9);
                    break;
                case 10: case 11: case 12:
                    selectedRisk.remove(10);
                    selectedRisk.remove(11);
                    selectedRisk.remove(12);
                    break;
                case 13: case 14: case 15:
                    selectedRisk.remove(13);
                    selectedRisk.remove(14);
                    selectedRisk.remove(15);
                    break;
                case 16: case 17: case 18:
                    selectedRisk.remove(16);
                    selectedRisk.remove(17);
                    selectedRisk.remove(18);
                    break;
                case 20: case 21: case 22:
                    selectedRisk.remove(20);
                    selectedRisk.remove(21);
                    selectedRisk.remove(22);
                    break;
                case 23: case 24: case 25:
                    selectedRisk.remove(23);
                    selectedRisk.remove(24);
                    selectedRisk.remove(25);
            }
            selectedRisk.add(select);
        }

        for (int risk : selectedRisk) {
            total += riskLvl[risk - 1];
        }

        System.out.println("Selected risks:");
        selectedRisk.forEach(risk -> {
            final String msg = riskName[risk - 1] + ": " + effect[risk - 1];
            switch (riskLvl[risk - 1]) {
                case 1:
                    System.out.println(PrintColor.Green(msg));
                    break;
                case 2:
                    System.out.println(PrintColor.Yellow(msg));
                    break;
                case 3:
                    System.out.println(PrintColor.Red(msg));
            }
            switch (risk) {
                case 1: case 2: case 3:
                    short Scale = (short) ((risk == 1) ? 85 : (risk == 2) ? 70 : 50);
                    SoList.forEach(so -> {
                        so.setAtk((short) (so.getBaseAtk() * Scale / 100));
                        so.setAp((short) (so.getBaseAp() * Scale / 100));
                    });
                    break;
                case 4: case 5:
                    Scale = (short) ((risk == 4) ? 70 : 40);
                    SoList.forEach(so -> {
                        so.setDef((short) (so.getBaseDef() * Scale / 100));
                        so.setRes((short) (so.getBaseRes() * Scale / 100));
                    });
                    break;
                case 6:
                    SoList.forEach(so -> so.mana -= 3);
                    break;
                case 7: case 8: case 9:
                    Scale = (short) (risk == 7 ? 80 : risk == 8 ? 65 : 45);
                    SoList.forEach(so -> so.setMaxHealth(so.getMaxHealth() * Scale / 100));
                    break;
                case 10: case 11: case 12:
                    short DEF_b = (short) (risk == 10 ? 200 : risk == 11 ? 400 : 600);
                    short RES_b = (short) (DEF_b - 200);
                    EnList.forEach(en -> {
                        en.setDef((short) (en.getDef() + DEF_b));
                        en.setRes((short) (en.getRes() + RES_b));
                    });
                    break;
                case 13: case 14: case 15:
                    Scale = (short) (risk == 13 ? 120 : risk == 14 ? 140 : 170);
                    EnList.forEach(en -> {
                        en.setAtk((short) (en.getBaseAtk() * Scale / 100));
                        en.setAp((short) (en.getBaseAp() * Scale / 100));
                    });
                    break;
                case 16: case 17: case 18:
                    Scale = (short) (risk == 16 ? 140 : risk == 17 ? 170 : 210);
                    EnList.forEach(en -> en.setMaxHealth(en.getMaxHealth() * Scale / 100));
                    break;
                case 19:
                    SoList.forEach(so -> {
                        so.cost_1 += 1;
                        so.cost_2 += 1;
                    });
                    break;
                case 20: case 21: case 22:
                    final short dmg = (short) (100 + 125 * (risk - 20));
                    SoList.forEach(so ->
                        so.poison.initialize(dmg, (short) 1000, null)
                    );
                    break;
                case 23: case 24: case 25:
                    EnList.add(new Sanguinaria());
                    if (risk >= 24) EnList.add(new Sanguinaria((byte) 2));
                    if (risk >= 25) {
                        EnList.add(new Sanguinaria());
                        EnList.add(new Sanguinaria((byte) 2));
                    }
                    break;
            }
        });
        String announceContract = "Total contract level: " + total + " (";
        for (int i = 1; i <= total; ++i) {
            if (i % 2 == 1) announceContract += "▲";
            else announceContract += "▼";
        }
        announceContract += ")";
        if (total <= 4) System.out.println(PrintColor.Yellow(announceContract + "\nDifficulty rating: High"));
        else if (total <= 8) System.out.println(PrintColor.Red(announceContract + "\nDifficulty rating: Very high!"));
        else System.out.println(PrintColor.BRed(announceContract + "\nDifficulty rating: Extremely high, proceed with caution!"));
        EnList.forEach(EnemyBehaviors::setChallengeMode);
    }

    public static void mainMenu() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        try {
            playSound("ost\\boil.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Choose battle mode:"
                + PrintColor.Yellow("\n1. Free selection") + ": Choose up to 3 enemies at will, including additional add-ons."
                + PrintColor.Red("\n2. Packed selection") + ": Pick a pack with pre-selected enemies and add-ons.");
        getEntities(Input.Shrt("choice", (short) 1, (short) 2));
        // open the sound file as a Java input stream
        if (clip != null) clip.stop();

        if (!Enemies_hasElite()) {
            boolean fileExist = false;
            while (!fileExist) {
                fileExist = true;
                try {
                    String songName = Input.Str("song name (type '0' to play none)") + ".wav";
                    if (!songName.equals("0.wav")) playSound(songName);
                } catch (FileNotFoundException e) {
                    System.out.println("File doesn't exist, make sure the path is correct and try again!");
                    fileExist = false;
                }
            }
        }
        else {
            String theme = "";
            String quote = "";
            for (Enemy e : EnList) {
                if (!e.isElite()) continue;

                if (e instanceof Qual) {
                        theme = ".\\ost\\sanguinarch";
                        quote = e.challengeMode ? "Show me more, your Arts." : "Show me that, your Arts.";
                }
                else if (e instanceof Cliff) {
                    theme = ".\\ost\\cliff";
                    quote = "\"Yes?\"";
                }
                else if (e instanceof Ya) {
                    theme = ".\\ost\\ymmons";
                    quote = "It the one has seen it all!";
                }
                else if (e instanceof EB) {
                    theme = "D:\\C++ storage\\ABetterGems\\nicho5\\Debug\\ost\\emblade";
                    quote = e.challengeMode ? "Snowfalls, blackening the earth." : "The blizzard, approaching.";
                }
                else if (e instanceof StmKgt) {
                    theme = ".\\ost\\stmkgt1";
                    quote = "The Knight charges forward!";
                }
                else if (e instanceof Awakening) {
                    theme = ".\\ost\\bbrain";
                    quote = "Awaken!";
                }
                else if (e instanceof Shapeshifter) {
                    theme = ".\\ost\\shift";
                    quote = "The calming sky foresees no good...";
                }
                else if (e instanceof TFO) {
                    theme = ".\\ost\\sacrifice";
                    quote = "As for one shall cleanse their sins...";
                }
                else if (e instanceof PlxCaster) {
                    theme = e.challengeMode ? ".\\ost\\plxsqd_vo" : ".\\ost\\plxsqd";
                    quote = "The sky is dyed black!";
                }
                else {
                    theme = "D:\\C++ storage\\ABetterGems\\nicho5\\Debug\\ost\\lostmemory";
                    quote = "Huh?";
                }
            }
            playSound(theme + ".wav");
            System.out.println(PrintColor.Red(quote));
            Wait.sleep(2000);
        }
        battleStart();
        if (clip != null) clip.stop();
        SoList.clear();
        EnList.clear();
    }

    private static void battleStart() {
        gameStart();
        if (Enemies_isAlive() || !Players_isAlive()) System.out.println("YOU LOST! Better luck next time");
        else {
            System.out.println("U won!");
//          addNewEnemy();
//          battleStart();
        }
    }

    private static void gameStart() {
        EntitiesList.Entities_getPreBattleEffect();

        SoList.forEach(so -> {
            so.fadeoutEffects();
            so.printHealthBar(PrintColor.yellow);
        });
        EnList.forEach(en -> {
            if (!en.isAlive() && en.isCanRevive()) en.revive();
            en.fadeoutEffects();
            en.printHealthBar(PrintColor.red);
        });
        System.out.println();

        while (Players_isAlive() && Enemies_isAlive()) {
            EntitiesList.Entities_getAction();
        }
    }
}
