package com.company.Entitiy.Enemy;

import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.company.EntitiesList.SoList;

public class Avenger extends Enemy {
    public final HashMap<String, Integer> DamageTaken = new HashMap<>();
    private Entity recentlyAttacked = null;

    public short atkIncRatio = 180;
    public short atkPerStrike_base, penPerStrike_base;
    public short atkPerStrike_add, penPerStrike_add;
    public short shelterGains;
    public boolean healthAdd = false;

    private short total_AtkAdd = 0, total_PenAdd = 0;
    private boolean below = false;

    public Avenger() {
        canRevive = true;
        setName("The Avenger");
        setTrait("A combatant you have encountered in the past who attacks with long, burning blades.\n" +
                "The formidable enemy you have defeated in the former battlefield now returned in front of you, drawing out the flames of vengeance.");
        setSkill(PrintColor.BRed("Flame of Vengeance") + ": Attack prioritizes targeting unit that "
                + PrintColor.Red("has dealt the most damage") + " to self.\n"
                + PrintColor.BRed("Boiling Blood") + ": Continuously attacking the same unit will " + PrintColor.Red("increases self ATK and DEF penetration")
                + ", this bonus can stack additively but will be reset when switch to different unit.\n"
                + PrintColor.BRed("Fire and Steel") + ": When HP falls below half, "
                + PrintColor.Red("ATK increases") + " and gains permanent " + PrintColor.Blue("'Shelter'")
                + ". Upon being defeated for the first time, immediately revives with " + PrintColor.Green("50% max HP") + ".");
        setMaxHealth(13000);
        setAtk((short) 550);
        setDef((short) 300);
        setRes((short) 400);
        setDefPen((short) 25);
        atkPerStrike_base = 200;
        penPerStrike_base = 10;
        atkPerStrike_add = 100;
        penPerStrike_add = 5;
        shelterGains = 50;
    }

    @Override
    public void normalAttack(Entity target, int damage) {
        if (this.taunt.inEffect() && this.taunt.getTaunter().isAlive()) target = this.taunt.getTaunter();

        SoList.forEach(so -> {
            if (!so.isAlive()) DamageTaken.remove(so.toString());
        });

        if (DamageTaken.isEmpty()) {
            if (target == recentlyAttacked) {
                short Atk = atkPerStrike_base, Pen = penPerStrike_base;
                this.addAtk(Atk);
                this.addDefPen(Pen);
                total_AtkAdd += Atk;
                total_PenAdd += Pen;
            }
            else {
                this.addAtk((short) (total_AtkAdd * -1));
                this.addDefPen((short) (total_PenAdd * -1));
                total_AtkAdd = 0;
                total_PenAdd = 0;
                recentlyAttacked = target;
            }
            super.normalAttack(target, damageOutput(getAtk(), getAp(), target));
        }
        else {
            String highestDmg = DamageTaken.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
            SoList.forEach(so -> {
                if (so.toString().equals(highestDmg)) {
                    if (this.taunt.inEffect() && this.taunt.getTaunter().isAlive()) so = (Soldier) this.taunt.getTaunter();

                    if (so == recentlyAttacked) {
                        short Atk = atkPerStrike_base, Pen = penPerStrike_base;
                        this.addAtk(Atk);
                        this.addDefPen(Pen);
                        total_AtkAdd += Atk;
                        total_PenAdd += Pen;
                    }
                    else {
                        this.addAtk((short) (total_AtkAdd * -1));
                        this.addDefPen((short) (total_PenAdd * -1));
                        total_AtkAdd = 0;
                        total_PenAdd = 0;
                        recentlyAttacked = so;
                    }
                    super.normalAttack(so, damageOutput(getAtk(), getAp(), so));
                }
            });
        }
    }

    @Override
    public void revive() {
        super.revive();
        while (this.grievouswound.inEffect()) grievouswound.fadeout();
        this.healing(getMaxHealth() / 2);
    }

    @Override
    public void update() {
        if (this.getMissingHealth() >= this.getMaxHealth() / 2 && !below) {
            below = true;
            if (healthAdd) setHealth(getMaxHealth() * 3);
            setAtk((short) (getBaseAtk() * atkIncRatio / 100));
            this.shelter.setValue(shelterGains, (short) 200);
            atkPerStrike_base += atkPerStrike_add;
            penPerStrike_base += penPerStrike_add;
        }
    }

    @Override
    public void setChallengeMode() {
        canCC = false;
        addMaxHealth(8000);
        ChallengeModeStatsUp();
        skill += "Gains increased HP and removes susceptibility against crowd-control effects.";
    }
}
