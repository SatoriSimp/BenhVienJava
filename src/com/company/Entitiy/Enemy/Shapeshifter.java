package com.company.Entitiy.Enemy;

import com.company.Entitiy.Entity;
import com.company.PrintColor;

import static com.company.EntitiesList.EnList;

public class Shapeshifter extends Enemy {
    private int baseHealth;
    private short baseATK, baseDEF, baseRES, baseAP, bonusAP;
    private float multiplier;

    private String skill, trait;
    public short shapeSeed;
    private short seedMax = 6;
    boolean cm = false;

    public Shapeshifter() {
        setName("Shapeshiter");
        baseHealth = 10000;
        baseATK = 500;
        baseAP = 0;
        baseDEF = 200;
        baseRES = 500;
        multiplier = 1.0f;
        bonusAP = 15;
        setMaxHealth((int) (baseHealth * multiplier));
        setDef((short) (baseDEF * multiplier));
        setRes((short) (baseRES * multiplier));
        setAtk((short) (baseATK * multiplier));
        setAp((short) (baseAP * multiplier));
        trait = "Fragment of the past. A mystery that has been left unspoken throughout the history. " +
                "This creature can take on the appearance of another individual which is indistinguishable to the original, in addition to be able of cloning themselves infinitely, " +
                "from head to toe and down to their attire, allowing them to seamlessly infiltrate enemies and feeding vital information without ever being compromised.";
        skill = PrintColor.BYellow("Fragment of Silence") + ": Initially disguise as an enemy and inherits their stats, however can not use their abilities. Self is revealed once transformed target is defeated.\n"
                + PrintColor.BYellow("'We are More'") + ": Each transformation additionally creates a clone of self and permanently increases this unit and clones' original stats."
                + PrintColor.BPurple("\nRising Tides") + ": Each attack " + PrintColor.Purple("increases AP") + ", stacks additively. This bonus resets upon shifting.";
        setTrait(trait);
        setSkill(skill);
        isElite = true;
        canRevive = true;
        shapeSeed = 1;
        shape(shapeSeed);
    }

    public Shapeshifter(short initialSeed) {
        setName("Shapeshiter");
        baseHealth = 10000;
        baseATK = 500;
        baseAP = 0;
        baseDEF = 200;
        baseRES = 500;
        multiplier = 1.0f;
        bonusAP = 15;
        setMaxHealth((int) (baseHealth * multiplier));
        setDef((short) (baseDEF * multiplier));
        setRes((short) (baseRES * multiplier));
        setAtk((short) (baseATK * multiplier));
        setAp((short) (baseAP * multiplier));
        trait = "Fragment of the past. A mystery that has been left unspoken throughout the history. " +
                "This creature can take on the appearance of another individual which is indistinguishable to the original, in addition to be able of cloning themselves infinitely, " +
                "from head to toe and down to their attire, allowing them to seamlessly infiltrate enemies and feeding vital information without ever being compromised.";
        skill = PrintColor.BYellow("Fragment of Silence") + ": Initially disguise as an enemy and inherits their stats, however can not use their abilities. Self is revealed once transformed target is defeated.\n"
                + PrintColor.BYellow("'We are More'") + ": After each transformation, creates a clone of self and permanently increases this unit and clones' original stats."
                + PrintColor.BPurple("\nRising Tides") + ": Each attack " + PrintColor.Purple("increases AP") + ", stacks additively. Loses when shifting.";
        setTrait(trait);
        setSkill(skill);
        isElite = true;
        canRevive = true;
        shapeSeed = initialSeed;
        shape(shapeSeed);
    }

    @Override
    public void revive() {
        if (shapeSeed == seedMax) canRevive = false;
        shapeSeed++;
        shape(shapeSeed);
    }

    @Override
    public void normalAttack(Entity target) {
        super.normalAttack(target);
        addAp(bonusAP);
    }

    public void shape(short seed) {
        switch (seed) {
            case 1:
                setMaxHealth(9000);
                setAtk((short) 420);
                setName("Nourished Infused Slug");
                setTrait("They're named this way bases on basic similarities with a normal slug, but don't look down on them because of this. " +
                        "\nHaving their abdomen filled with nothing but a tons of unstable dangerous chemicals,\nthey have developed to be more" +
                        " resilient and aggressive when injured in order to keep themselves alive, results in an extremely high survivability" +
                        ".\nAnd since they have survived quite a long time in the wilderness, the chemicals within their body has gradually " +
                        "become more unstable, results in an even more glory firework show if they were defeated.");
                setSkill(PrintColor.BRed("Heat-up") + ": Gains " + PrintColor.Red("bonus ATK bases upon HP loss")
                        + ". When below 50% HP, also provides an increment in " + PrintColor.yellow + "DEF" + PrintColor.def + " and " + PrintColor.cyan + "RES" + PrintColor.def + ".\n"
                        + PrintColor.BRed("Firework Show") + ": Upon death, explodes and deals " + PrintColor.WHITE_BOLD + "1200 true damage" + PrintColor.def + " to all friendly units.");
                setDef((short) 500);
                setRes((short) 200);
                break;
            case 2:
                setName("Shapeshiter");
                setMaxHealth((int) (baseHealth * multiplier));
                setDef((short) (baseDEF * multiplier));
                setRes((short) (baseRES * multiplier));
                setAtk((short) (baseATK * multiplier));
                setAp((short) (baseAP * multiplier));
                if (cm) setReduction((short) 6);
                setTrait(trait);
                setSkill(skill);
                break;
            case 3:
                if (Math.floor(Math.random() * (5 - 1 + 1) + 1) % 2 == 0) {
                    setName("Prefect Zombified Orc");
                    setTrait("An evenly rotten kind of lifeform. After getting their mind and body reshaped by a parasite living inside their brain," +
                            " they now boast superior strength as their lifeforce is consumed.");
                    setMaxHealth(9000);
                    setDef((short) 650);
                    setAtk((short) 900);
                    setAp((short) 0);
                    setSkill(PrintColor.BBlue("Organic Deconstruction") + ": "
                            + PrintColor.Red("Gradually loses HP and resistances overtime") + ".\n"
                            + PrintColor.BGreen("Gorges") + ": Attack "
                            + PrintColor.Green("heals self for a percentage of damage dealt") + ".\n"
                            + PrintColor.BRed("Despite the Weak")
                            + ": Charges up for a strong attack that deals " + PrintColor.Red("more damage") + ", "
                            + PrintColor.Green("ramps up the healing") + " and "
                            + "prioritizes targeting unit with " + PrintColor.Yellow("lowest DEF") + ".");
                    setLifesteal((short) 40);
                    setRes((short) 400);
                }
                else {
                    setName("Hateful Spirit");
                    setMaxHealth(9000);
                    setAp((short) 600);
                    setDef((short) 850);
                    setRes((short) 200);
                    baseAP += 100;
                    setTrait("Its body has decayed and returned to the wild, what's left now is a chained will, a bare essence that " +
                            "desperately searching for its new host.\nIt attacks any living form as soon as they're caught in its sight, weakens the " +
                            "spirit within then seizes control of their body.\nDespite already knowing it can never success, something within it feels the distorted joy from" +
                            " killing others amusing, and for that has become its only purpose in life, it will never stop.");
                }
                if (cm) setReduction((short) 12);
                break;
            case 4:
                setName("Shapeshiter");
                multiplier = 1.5f;
                setMaxHealth((int) (baseHealth * multiplier));
                setDef((short) (baseDEF * multiplier));
                setRes((short) (baseRES * (multiplier * 0.85f)));
                setAtk((short) (baseATK * (multiplier * 0.8f)));
                setAp((short) (baseAP * multiplier));
                setTrait(trait);
                setSkill(skill);
                bonusAP += 5;
                if (cm) setReduction((short) 18);
                break;
            case 5:
                if (Math.floor(Math.random() * (100 - 1 + 1) + 1) % 2 == 0) {
                    setName("Sanguinary Descendant Malicebearer");
                    setTrait(" An apparition that would normally never appear, unless they have to.\n" +
                            "Nourished by hatred and blood, they show no hesitation to seek for revenge for their kins.\n" +
                            "Once the task is done, their body will soon decay and return to the wild, waiting for the next time they get to rise once again.");
                    setSkill(PrintColor.BRed("Hatedrinker") + ": Upon being spawned, gains increased "
                            + PrintColor.Green("max HP") + ", " + PrintColor.Red("ATK") + ", " + PrintColor.Red("DEF penetration")
                            + ", " + PrintColor.Yellow("DEF") + " and " + PrintColor.Cyan("RES") + " for every enemy that has been defeated, stacks additively.");
                    setMaxHealth(20000);
                    setAtk((short) 1000);
                    setDef((short) 900);
                    setRes((short) 600);
                    setAp((short) 0);
                    baseDEF += 100;
                }
                else {
                    setName("Enraged Possessed Bonethrower");
                    setTrait("A former marksman that has completely given in to primal madness as their lifeforce is consumed." +
                            "\nThey will even rip out bones inside their body to use as weapon because of their deadened sense of pain.");
                    setSkill(PrintColor.BYellow("Final Warcry") + ": Has " + PrintColor.Red("extremely high ATK")
                            + ". Gradually " + PrintColor.BBlue("loses HP overtime") + ".");
                    setMaxHealth(10000);
                    setDef((short) 500);
                    setRes((short) 500);
                    setAp((short) 0);
                    setAtk((short) 1800);
                    setDefPen((short) 30);
                    setDefIgn((short) 100);
                }
                if (cm) setReduction((short) 24);
                break;
            case 6:
                setName("Shapeshiter");
                multiplier = 2.25f;
                setMaxHealth((int) (baseHealth * multiplier));
                setDef((short) (baseDEF * (multiplier * 1.05f)));
                setRes((short) (baseRES * (multiplier * 0.75f)));
                setAtk((short) (baseATK * (multiplier * 0.65f)));
                setAp((short) (baseAP * multiplier));
                setTrait(trait);
                setSkill(skill);
                bonusAP += 5;
                if (cm) setReduction((short) 30);
                break;
        }
    }

    @Override
    public void setChallengeMode() {
        cm = true;
        baseHealth *= 1.1f;
        baseATK *= 1.1f;
        baseDEF *= 1.1f;
        baseRES *= 1.1f;
        skill += '\n' + cmAdd + "After each transformation, gains a stacking damage reduction effect";
    }
}
