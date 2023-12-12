package com.company.Entitiy;

import com.company.EntitiesList;
import com.company.Entitiy.Allied.Defender;
import com.company.Entitiy.Allied.Pathfinder;
import com.company.Entitiy.Allied.Saigyouji;
import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Enemy.*;
import com.company.PrintColor;
import com.company.Status.CC.CC;
import com.company.Status.CC.Taunt;
import com.company.Status.Effect.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

import static com.company.EntitiesList.EnList;

abstract public class Entity implements Cloneable {
    public final Fragile fragile = new Fragile();
    public final Shelter shelter = new Shelter();
    public final Taunt taunt = new Taunt();
    public final GrievousWound grievouswound = new GrievousWound();
    public final DefBuff defBuff = new DefBuff(this);
    public final AtkBuff atkBuff = new AtkBuff(this);
    public final AtkDown atkDebuff = new AtkDown(this);
    public final CC silence = new CC();
    public final CC stun = new CC();
    public final DoT poison = new DoT();
    public final DoT bleed = new DoT();
    public final DoT burn = new DoT();
    public boolean canAttack = true, canCC = true, isSilenced = false, isDisarmed = false, isSummon = false, isInvisible = false;
    public String shortDes;
    private String name;
    private String id;
    private int health, maxHealth;
    private short atk, ap, def, res, baseAtk, baseAp, baseDef, baseRes, shield = 0, tauntLevel = 1;
    private short defPen = 0, defIgn = 0, resPen = 0, resIgn = 0, reduction = 0, lifesteal = 0;
    private float physAmp = 0, artAmp = 0;
    private short recovery = 0;

    public void addMaxHealth(int amount) {
        this.maxHealth += amount;
        this.health += amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAtk(short atk) {
        this.atk += atk;
    }

    public void addAp(short ap) {
        this.ap += ap;
    }

    public void addDef(short amount) {
        this.def += amount;
    }

    public void addRes(short amount) {
        this.res += amount;
    }

    public short getShield() { return shield; }

    public void setShield(short shield) {
        this.shield = shield;
    }

    public void setDefPen(short defPen) {
        this.defPen = defPen;
    }

    public void addDefPen(short defPen) {
        this.defPen += defPen;
    }

    public void setDefIgn(short defIgn) {
        this.defIgn = defIgn;
    }

    public void addDefIgn(short defIgn) {
        this.defIgn += defIgn;
    }

    public void setResPen(short resPen) {
        this.resPen = resPen;
    }

    public void setResIgn(short resIgn) {
        this.resIgn = resIgn;
    }

    public void addResPen(short resPen) {
        this.resPen += resPen;
    }

    public void addResIgn(short resIgn) {
        this.resIgn += resIgn;
    }

    public void setReduction(short reduction) {
        this.reduction = reduction;
    }

    public void setHealingreduction(short healingreduction, short dur) {
        this.grievouswound.setValue(healingreduction, dur);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        int missing = this.health * 100 / this.maxHealth;
        this.maxHealth = health;
        this.health = maxHealth * missing / 100;
    }

    public short getRecovery() {
        return recovery;
    }

    public void setRecovery(short recovery) {
        this.recovery = recovery;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = this.health = maxHealth;
    }

    public short getDefPen() {
        return defPen;
    }

    public short getReduction() { return reduction; }

    public short getResPen() {
        return resPen;
    }

    public int getMissingHealth() {
        return maxHealth - health;
    }

    public short getAtk() {
        return atk;
    }

    public void setAtk(short atk) {
        this.atk = this.baseAtk = atk;
    }

    public short getBaseAtk() {
        return baseAtk;
    }

    public short getAp() {
        return ap;
    }

    public void setAp(short ap) {
        this.ap = this.baseAp = ap;
    }

    public short getBaseAp() {
        return baseAp;
    }

    public short getTauntLevel() {
        return tauntLevel;
    }

    public void setTauntLevel(short level) {
        this.tauntLevel = level;
    }

    public short getDef() {
        return def;
    }

    public void setDef(short def) {
        this.def = this.baseDef = def;
    }

    public short getBaseDef() {
        return baseDef;
    }

    public short getRes() {
        return res;
    }

    public void setRes(short res) {
        this.res = this.baseRes = res;
    }

    public short getBaseRes() {
        return baseRes;
    }

    public short getLifesteal() {
        return lifesteal;
    }

    public void setLifesteal(short lifesteal) {
        this.lifesteal = lifesteal;
    }

    public boolean canTarget() {
        return isAlive();
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void fixHealth() {
        if (this.health < 0) this.health = 0;
        else if (this.health > this.maxHealth) this.health = this.maxHealth;
    }

    public void lifesteal(int damage) {
        if (lifesteal <= 0) return;
        int healing = this.grievouswound.reduce(damage * lifesteal / 100);
        this.health += healing;
        this.fixHealth();
        System.out.println(PrintColor.green + name + " heals for " + healing + " HP!");
    }

    public int damageOutput(int pd, int mg, @NotNull Entity target) {
        return damageOutput(pd, mg, 0, target);
    }

    public int damageOutput(int pd, int mg, int td, @NotNull Entity target) {
        if (target.shield > 0) return 0;
        return damageOutput(pd, mg, td, target, true);
    }

    public int damageOutput(int pd, int mg, @NotNull Entity target, boolean ignoreShield) {
        return damageOutput(pd, mg, 0, target, ignoreShield);
    }

    public int damageOutput(int pd, int mg, int td, @NotNull Entity target, boolean ignoreShield) {
        int phyDmg = pd * (1000 - ((target.def - this.defIgn) * (100 - this.defPen) / 100)) / 1000;
        if (phyDmg < pd / 20) phyDmg = pd / 20;
        else if (phyDmg > pd) phyDmg = pd;
        int magDmg = mg * (1000 - ((target.res - this.resIgn) * (100 - this.resPen) / 100)) / 1000;
        if (magDmg < mg / 10) magDmg = mg / 10;
        else if (magDmg > mg) magDmg = mg;

        int total = (phyDmg + magDmg) * Math.max(100 - target.reduction, 0) / 100 + td;
        total = target.fragile.amplify(target.shelter.reduce(total));
        return total;
    }

    public void printHealthBar(String color) {
        if (health < 0) health = 0;
        int per_missing = (maxHealth - health) * 100 / maxHealth;
        System.out.println("\n" + color + name + "'s health: " + health + "/" + maxHealth);
        System.out.print('[');
        if (health <= 0) {
            for (short i = 1; i <= 50; ++i) System.out.print(" ");
        } else {
            short left = (short) (50 - (per_missing / 2));
            for (short i = 1; i <= 50; ++i) {
                if (i <= left) {
                    System.out.print("=");
                } else {
                    System.out.print(" ");
                }
            }
        }
        System.out.print("]" + "\t" + (100 - per_missing) + '%' + "\u001B[0m");

        if (this.isInvisible) System.out.print(PrintColor.Yellow("    Invisible"));
        if (this.shield > 0) System.out.print(PrintColor.green + "    Layers of Shield: " + this.shield);
        if (this == TFO.lowDEF) System.out.print(PrintColor.BRed("    Singing Sand"));
        if (this == TFO.highHP) System.out.print(PrintColor.BBlue("    Blizzard Tomb"));
        if (this.poison.inEffect()) System.out.print(PrintColor.purple + "    Poisoned");
        if (this.bleed.inEffect()) System.out.print(PrintColor.Red("    Bleeding"));
        if (this.burn.inEffect()) System.out.print(PrintColor.Red("    Burnt"));
        if (this instanceof Soldier && Spirit.marked == this) System.out.print(PrintColor.purple + "    Marked: " + Spirit.timer + " remaining turns");
        if (this.silence.inEffect()) System.out.print(PrintColor.Blue("    Silenced"));
        if (this.taunt.inEffect()) System.out.print(PrintColor.yellow + "    Taunted");
        if (this.fragile.inEffect()) System.out.print(PrintColor.cyan + "    Fragile");
        if (this.shelter.inEffect()) System.out.print(PrintColor.BBlue + "    Shelter");
        if (this.grievouswound.inEffect()) System.out.print(PrintColor.purple + "    Wounded");
        if (this.atkBuff.inEffect()) System.out.print(PrintColor.red + "    ATK-up");
        if (this.atkDebuff.inEffect()) System.out.print(PrintColor.blue + "    ATK-down");
        if (this.defBuff.inEffect()) {
            if (this.defBuff.isBuff()) System.out.print(PrintColor.yellow + "    DEF-up");
            else System.out.print("    " + PrintColor.YELLOW_UNDERLINED + "DEF-down");
        }
        System.out.println(PrintColor.def);
    }

    public void normalAttack(Entity target) {
        if (this.taunt.inEffect() && this.taunt.getTaunter().isAlive()) target = this.taunt.getTaunter();
        normalAttack(target, damageOutput(getAtk(), getAp(), target));
    }

    public void normalAttack(Entity target, int damage) {
        dealingDamage(target, damage);
    }

    public void dealingDamage(Entity target, int damage) {
        boolean shieldBlock = false;
        if (target.shield > 0) {
            shieldBlock = true;
            damage = 0;
            target.shield--;
            System.out.print(PrintColor.Bgreen + "BLOCKED! " + PrintColor.def);
        }
        if (this.poison.inEffect() && target instanceof VenomSpider) {
            damage /= 2;
        }

        if (target instanceof Awakening) {
            short DorCnt = (short) EnList.stream().filter(e -> e instanceof Awakening.Awk && e.isAlive()).count();
            if (DorCnt > 0) {
                damage /= 2;
                final short SplitDmg = (short) (damage / DorCnt);
                EnList.forEach(dor -> {
                    if (dor instanceof Awakening.Awk && dor.isAlive())
                        target.dealingDamage(dor, SplitDmg, "Resonance", PrintColor.Byellow);
                });
            }
        }
        else if (target instanceof Heatwave) {
            damage = (int) (((Heatwave) target).baseDmg + this.getDef() * ((Heatwave) target).defScale);
        }

        target.health -= damage;
        String adaptive_damage = (this.atk > this.ap) ? PrintColor.red : PrintColor.purple;
        System.out.println(PrintColor.yellow + name + PrintColor.def + " attacked "
                + PrintColor.yellow + target.name + PrintColor.def + ", dealt " + adaptive_damage + damage + " damage!" + PrintColor.def);
        this.lifesteal(damage);
        target.fixHealth();

        if (shieldBlock && target instanceof Pathfinder) {
            target.healing((int) (100 + target.getMaxHealth() * 0.2));
        }

        if (this instanceof Soldier) {
            target.printHealthBar(PrintColor.red);
            System.out.println();
        }

        if (target instanceof Defender) {
            int reflect = ((Defender) target).reflectDmg(this);
            if (this instanceof VenomSpider && target.poison.inEffect()) reflect /= 2;
            this.health -= reflect;
            this.fixHealth();
            this.atkDebuff.initialize((short) 10, (short) 2);
            if (this.shield > 0) {
                this.shield--;
                System.out.print(PrintColor.Bgreen + "BLOCKED! " + PrintColor.def);
            }
            System.out.println(PrintColor.yellow + target.getName() + PrintColor.def + " reflects to " +
                    PrintColor.yellow + this.getName() + " " + PrintColor.red + reflect + " damage!");
        }
        else if (target instanceof PlxPioneer) {
            this.atkDebuff.initialize((short) 25, (short) 3);
        }
        else if (target instanceof BDummy) {
            ((BDummy) target).reflect(this, damage);
        }

        if (target instanceof Avenger && !(this instanceof Enemy)) {
            ((Avenger) target).DamageTaken.put(this.toString(), ((Avenger) target).DamageTaken.getOrDefault(this.toString(), 0) + damage);
        }

        if (this instanceof Enemy && target.fragile.inEffect()) {
            int hl = 0;
            for (Enemy e : EnList) {
                if (e instanceof Singer && e.isAlive())
                    hl = ((Singer) e).getHeal();
            }
            if (hl > 0) this.healing(hl);
        }
    }

    public void dealingDamage(Entity target, int damage, String skillName, String color) {
        dealingDamage(target, damage, skillName, color, false);
    }

    public void dealingDamage(Entity target, int damage, String skillName, String color, boolean ignoreShield) {
        boolean shieldBlock = false;
        if (!ignoreShield) {
            if (target.shield > 0) {
                shieldBlock = true;
                damage = 0;
                target.shield--;
                System.out.print(PrintColor.Bgreen + "BLOCKED! " + PrintColor.def);
            }
        }
        if (this.poison.inEffect() && target instanceof VenomSpider) {
            damage /= 2;
        }

        if (target instanceof Awakening) {
            short DorCnt = (short) EnList.stream().filter(e -> e instanceof Awakening.Awk && e.isAlive()).count();
            if (DorCnt > 0) {
                damage /= 2;
                final short SplitDmg = (short) (damage / 2 / DorCnt);
                EnList.forEach(dor -> {
                    if (dor instanceof Awakening.Awk && dor.isAlive())
                        target.dealingDamage(dor, SplitDmg, "Resonance", PrintColor.Byellow);
                });
            }
        }
        else if (target instanceof Heatwave) {
            damage = (int) (((Heatwave) target).baseDmg + this.getDef() * ((Heatwave) target).defScale);
        }

        target.health -= damage;
        System.out.println(PrintColor.yellow + this.name + PrintColor.def + " casted "
                + color + skillName + PrintColor.def + " on "
                + PrintColor.yellow + target.name + PrintColor.def
                + ", dealt " + color + damage + " damage!" + PrintColor.def);
        this.lifesteal(damage);
        target.fixHealth();

        if (shieldBlock && target instanceof Pathfinder) {
            target.healing((int) (100 + target.getMaxHealth() * 0.2));
        }

        if (this instanceof Soldier) {
            target.printHealthBar(PrintColor.red);
            System.out.println();
        }

        if (target instanceof Defender) {
            int reflect = ((Defender) target).reflectDmg(this);
            if (this instanceof VenomSpider && target.poison.inEffect()) reflect /= 2;
            this.health -= reflect;
            this.fixHealth();
            this.atkDebuff.initialize((short) 10, (short) 2);
            if (this.shield > 0) {
                this.shield--;
                System.out.print(PrintColor.Bgreen + "BLOCKED! " + PrintColor.def);
            }
            System.out.println(PrintColor.yellow + target.getName() + PrintColor.def + " reflects to " +
                    PrintColor.yellow + this.getName() + " " + PrintColor.red + reflect + " damage!");
        }
        else if (target instanceof PlxPioneer) {
            this.atkDebuff.initialize((short) 25, (short) 3);
        }
        else if (target instanceof BDummy) {
            ((BDummy) target).reflect(this, damage);
        }

        if (target instanceof Avenger && !(this instanceof Enemy)) {
            if (!((Avenger) target).DamageTaken.containsKey(this.toString())) {
                ((Avenger) target).DamageTaken.put(this.toString(), damage);
            }
            else {
                int finalDamage = damage;
                ((Avenger) target).DamageTaken.compute(this.toString(), (k, v) -> v += finalDamage);
            }
        }

        if (this instanceof Enemy && target.fragile.inEffect()) {
            int hl = 0;
            for (Enemy e : EnList) {
                if (e instanceof Singer && e.isAlive())
                    hl = ((Singer) e).getHeal();
            }
            if (hl > 0) this.healing(hl);
        }
    }

    public void naturalRecovery() {
        if (recovery <= 0) return;
        this.healing(recovery, false);
    }

    public void healing(int amount) {
        amount = this.grievouswound.reduce(amount);
        System.out.println(PrintColor.green + getName() + " is healed for " + amount + " HP!" + PrintColor.def);
        this.health += amount;
        fixHealth();
        this.printHealthBar(PrintColor.green);
        System.out.println();
    }

    public void healing(int amount, boolean printMessage) {
        amount = this.grievouswound.reduce(amount);
        if (printMessage) System.out.println(PrintColor.green + getName() + " is healed for " + amount + " HP!" + PrintColor.def);
        this.health += amount;
        fixHealth();
    }

    public void rotting(int amount) {
        this.health -= amount;
        fixHealth();
    }

    public void preTurnPreparation() {
        if (this.poison.inEffect() && this.isAlive()) {
            this.setHealingreduction((short) 35, (short) 1);
            int poisonDmg = this.poison.getInflicter().damageOutput(0, poison.getValue(), this, true);
            System.out.println(PrintColor.BPurple("Poison triggers! ") + PrintColor.Yellow(this.name)
                    + " takes " + PrintColor.Purple(poisonDmg + " damage") + "!");
            this.health -= poisonDmg;
            this.poison.fadeout();
        }
        if (this.bleed.inEffect() && this.isAlive()) {
            int bleedDmg = this.bleed.getValue() / 2;
            int total = bleed.getInflicter().damageOutput(bleedDmg, 0, bleedDmg, this, true);
            this.health -= total;
            System.out.println(PrintColor.BRed("Bleeding! ") + PrintColor.Yellow(this.name)
                    + " takes " + PrintColor.Red(total + " damage") + "!");
            this.bleed.fadeout();
        }
        if (this.burn.inEffect() && this.isAlive()) {
            int burnDmg = (int) (this.burn.getValue() * (1.0 + this.getDef() * 0.0012f));
            int output = burn.getInflicter().damageOutput(0, 0, burnDmg, this, true);
            this.health -= output;
            System.out.println(PrintColor.BRed("Burns! ") + PrintColor.Yellow(this.name)
                    + " takes " + PrintColor.Red(output + " damage") + "!");
            this.burn.fadeout();
        }
    }

    public void preBattleSpecial() {}

    public void update() {}

    public void printInfo() {
        String color = null;
        if (this instanceof Soldier) color = PrintColor.green;
        else if (this instanceof Enemy) color = PrintColor.red;
        this.printHealthBar(color);
        System.out.println("\n" + PrintColor.green + "HP: " + health + " / " + maxHealth + " (" + (health * 100 / maxHealth) + "%)" + PrintColor.red +
                "\nATK: " + atk + " (" + baseAtk + " + " + (atk - baseAtk) + ")"
                + "\nDEF penetration: " + defIgn + " / " + defPen + "%"
                + PrintColor.purple + "\nAP: " + ap + " (" + baseAp + " + " + (ap - baseAp) + ")"
                + "\nRES penetration: " + resIgn + " / " + resPen + "%"
                + PrintColor.Bred + "\nLife-steal: " + lifesteal + "%"
                + PrintColor.yellow + "\nDEF: " + def + PrintColor.cyan + "\nRES: " + res + PrintColor.def);
    }

    public void fadeoutEffects() {
        this.fragile.fadeout();
        this.shelter.fadeout();
        this.taunt.fadeout();
        this.defBuff.fadeout();
        this.atkBuff.fadeout();
        this.atkDebuff.fadeout();
        this.grievouswound.fadeout();
        this.silence.fadeout();
        this.stun.fadeout();
    }

    public Entity getTaunt(Entity target) {
        if (this.taunt.inEffect() && this.taunt.getTaunter().isAlive()) return this.taunt.getTaunter();
        return target;
    }

    public Entity clone() {
        Entity another = null;
        try {
            another = (Entity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (Entity) another;
    }
}
