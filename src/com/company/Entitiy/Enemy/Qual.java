package com.company.Entitiy.Enemy;

import com.company.EntitiesList;
import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;
import com.company.PrintColor;
import com.company.Wait;

import java.util.*;

import static com.company.EntitiesList.SoList;

public class Qual extends Enemy {
    private boolean spell_not_charged = true;
    private final Stack<Integer> Spells = new Stack<>();
    private final String[] SpellName = {
            PrintColor.BRed("Bon Sirlcare"),
            PrintColor.BPurple("Vache Arhile"),
            PrintColor.BBlue("Exuschwl Maia"),
            PrintColor.BGreen("Gushnaria Arche"),
            PrintColor.black + "Todestrieb" + PrintColor.def,
            PrintColor.BYellow("Lune Adirecla"),
            PrintColor.BRed("Lune Escherichla"),
    };

    public Qual() {
        setName("Qual - Creator of Art");
        setTrait("Ancient life being that has existed since the primordial era. The almighty of art itself.\n" +
                "His knowledge has far surpassed that of everyone, His strength is flawless, and His art is beyond your imagination.\n" +
                "He was the first who discovered how to control the flow of energy within one's body, as well as take advantage of it. Driven with imagination, Art was hence created.\n" +
                SpellName[4] + ", His most simple and straightforward spell in concept, yet boasts terrifying destructive power, created with the sole intention is to kill. It is able to pierce through any line of defense, and even magic wouldn't help much either.\n" +
                "To his beknownst, every form of art must come with its own meaning, and those who don't respect that will soon face His wrath.");
        setSkill(PrintColor.BYellow("Eι Mαɠια") + ": " + PrintColor.Yellow("Reduces physic and art damage taken") + ". Instead of normal attack, this unit will cast one of their spell, spells' rotation follows a certain pattern.\n"
            + SpellName[0] + ": " + PrintColor.Red("Art casted from fire") + ", resembles that of volcano slug. Deals " + PrintColor.Purple("magic damage")
                + " to a unit, inflicts " + PrintColor.BRed("Burn") + " while " + PrintColor.Yellow("significantly increases their DEF") + " for 3 turns.\n"
            + SpellName[1] + ": " + PrintColor.Purple("Art casted from pure magic") + ", resembles the Knight of Victoria. "
                + "Summons 6 magic sphere to continuously bombard unit with " + PrintColor.Green("highest HP percentage")
                + ", deals " + PrintColor.Purple("accumulate magic damage") + " each time.\n"
            + SpellName[2] + ": " + PrintColor.Blue("Art casted from desires fueled with fears") + ", resembles the everlasting domination of absolute power. " + PrintColor.Red("Increases self ATK") + " and " +
                "at the same time, inflicting all allied units with " + PrintColor.Blue("30% 'Fragile'")
                + " and " + PrintColor.Purple("40% 'Grievous Wound'") + ", lasts for 4 turns.\n"
            + SpellName[3] + ": " + PrintColor.Red("Art casted from life-stealing magic") + ", resembles the nature instinct to seek for survival of beings. " +
                "Immediately deals " + PrintColor.Purple("magic damage") + " to all allied units while " + PrintColor.Green("heals self and creates 'Shields' bases on the number of targets hit") + ".\n"
            + PrintColor.WHITE_BOLD + "???" + PrintColor.def + ": it seems that he's holding back on you...");
        setMaxHealth(30000);
        setAtk((short) 200);
        setAp((short) 1000);
        setDef((short) 400);
        setRes((short) 1000);
        setReduction((short) 40);
        setResPen((short) 15);
        canCC = false;
        canRevive = true;
        isElite = true;
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 2, 3, 4);
        Collections.shuffle(list);

        for (int i : list) Spells.push(i);
        for (int i : list) Spells.push(i);
    }

    @Override
    public void normalAttack(Entity target) {
        if (spell_not_charged) {
            System.out.println(PrintColor.Yellow(getName()) + " starts casting his next spell...");
            spell_not_charged = false;
        }
        else {
            target = getTaunt(target);
            int spellID = Spells.pop();
            switch (spellID) {
                case 1:
                    dealingDamage(target, damageOutput(0, (int) (getAp() * 0.85f), target), SpellName[spellID - 1], PrintColor.Bred);
                    target.defBuff.initialize((short) 500, (short) 0, (short) 0, (short) 0, (short) (challengeMode ? 5 : 3));
                    target.burn.initialize((short) 300, (short) 3, this);
                    break;
                case 2:
                    short times = 6;
                    short baseDmg = (short) (getAp() * 0.4f);
                    for (short i = 1; i <= times; ++i) {
                        if (!EntitiesList.Players_isAlive()) return;
                        Soldier t = targetSearch(HIGHEST_HP_PERCENTAGE);
                        dealingDamage(t, damageOutput(0, baseDmg, t), SpellName[spellID - 1], PrintColor.Bpurple);
                        baseDmg += getAp() * 0.15f;
                        Wait.sleep(1000);
                    }
                    break;
                case 3:
                    System.out.println(PrintColor.Yellow(getName()) + " casted " + SpellName[spellID - 1] + "! Gains increased ATK and debuffs all friendly units.");
                    short dur = (short) (challengeMode ? 6 : 4);
                    this.atkBuff.initialize((short) 0, (short) 0, (short) 0, (short) 30, dur);
                    SoList.forEach(so -> {
                        if (so.isAlive()) {
                            so.fragile.setValue((short) 30, dur);
                            so.grievouswound.setValue((short) 40, dur);
                        }
                    });
                    break;
                case 4:
                    final int healthGen = (int) (challengeMode ? getMaxHealth() * 0.08 : getMaxHealth() * 0.05);
                    SoList.forEach(so -> {
                        if (so.isAlive()) {
                            dealingDamage(so, damageOutput(0, (int) (getAp() * 0.6f), so), SpellName[spellID - 1], PrintColor.purple);
                            this.healing(healthGen, false);
                            this.setShield((short) (this.getShield() + 1));
                        }
                    });
                    break;
                case 5:
                    this.rotting((int) (getMaxHealth() * 0.1f));
                    dealingDamage(target, 6000, SpellName[spellID - 1], PrintColor.WHITE_BOLD, true);
                    spell_not_charged = true;
                    break;
                case 6:
                    ArrayList<Soldier> SList = (ArrayList<Soldier>) SoList.clone();
                    addResPen((short) 35);
                    int scale = 150;
                    SList.sort((s1, s2) -> s2.getDef() - s1.getDef());
                    for (Soldier s : SList) {
                        if (!s.isAlive()) continue;
                        dealingDamage(s, damageOutput(0, getAp() * scale / 100, s), SpellName[spellID - 1], PrintColor.yellow);
                        scale = Math.max(scale - 40, 50);
                    }
                    addResPen((short) -35);
                    break;
            }
            if (spellID != 2) Wait.sleep();
            Spells.add(0, spellID);
        }
        System.out.println(PrintColor.Yellow("Upcoming spell: ") + SpellName[Spells.peek() - 1]);
    }

    @Override
    public void setChallengeMode() {
        setSkill(PrintColor.BYellow("Eι Mαɠια") + ": " + PrintColor.Yellow("Reduces physic and art damage taken") + ". Instead of normal attack, this unit will cast one of their spell, spells' rotation follows a certain pattern.\n"
                + SpellName[0] + ": " + PrintColor.Red("Art casted from fire") + ", resembles that of volcano slug. Deals " + PrintColor.Purple("magic damage")
                + " to a unit, inflicts " + PrintColor.BRed("Burn") + " for 3 turns while " + PrintColor.Yellow("significantly increases their DEF") + " for 5 turns.\n"
                + SpellName[1] + ": " + PrintColor.Purple("Art casted from pure magic") + ", resembles the Knight of Victoria. "
                + "Summons 6 magic sphere to continuously bombard unit with " + PrintColor.Green("highest HP")
                + ", deals " + PrintColor.Purple("increasing magic damage") + " each time.\n"
                + SpellName[2] + ": " + PrintColor.Blue("Art casted from desires fueled with fears") + ", resembles the everlasting domination of absolute power. Increases self ATK and " +
                "at the same time, inflicting all allied units with " + PrintColor.Blue("30% 'Fragile'")
                + " and " + PrintColor.Purple("40% 'Grievous Wound'") + ", lasts for 6 turns.\n"
                + SpellName[3] + ": " + PrintColor.Red("Art casted from life-stealing magic") + ", resembles the nature instinct to seek for survival of beings. " +
                "Immediately deals " + PrintColor.Purple("magic damage") + " to all allied units while " + PrintColor.Green("heals self and creates 'Shields' bases on the number of targets hit") + ".\n"
                + SpellName[5] + ": " + PrintColor.Yellow("Art casted from electricity") + ", resembles the swiftness of lightning and the strength of thunder. Strikes an allied unit " + PrintColor.Yellow("with highest DEF") + ", " +
                PrintColor.Purple("deals high magic damage") + ", the attack continues to jump between allies that have yet to be attacked by it, deals reduced damage each time.\n"
                + SpellName[4] + ": " + PrintColor.WHITE_BOLD + "Art casted from deathly magic" + PrintColor.def + ", resembles those who are willing to risk their life in exchange for a brief moment of unilluminated glory. " +
                "Immediately sacrifices a certain amount of self's HP to " + PrintColor.WHITE_BOLD + "deal massive pure damage" + " to a target, " + PrintColor.Red("completely ignores their defense, 'Shields', 'Shelter' and damage reduction effects") + ". Can not attack for a short time after this spell is used.");
        ChallengeModeStatsUp();
        skill += "More spells are available, some spells additionally are enhanced.";
        Spells.clear();
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 2, 3, 4, 6);
        Collections.shuffle(list);
        for (int i : list) Spells.push(i);
        for (int i : list) Spells.push(i);
        Spells.add(0, 5);
    }
}
