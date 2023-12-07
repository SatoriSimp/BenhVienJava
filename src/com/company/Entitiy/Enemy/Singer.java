package com.company.Entitiy.Enemy;

import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;
import com.company.PrintColor;
import com.company.menu;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.company.EntitiesList.EnList;
import static com.company.EntitiesList.SoList;

public class Singer extends Enemy {
    private boolean soloist = false;
    private float healScale;
    private short fragileStrg;

    public Singer() {
        setName("\"The Singer\"");
        setTrait("There was once a traveler who appears in a black coat, humming an unknown yet pleasing melody. They travel from place to place, bringing their songs to anywhere they came\n" +
                "Everyone in the country fell in love with their songs, they sang them at every party, celebration or even around the campfire.\n" +
                "Time passed over, and after that traveler left the town for a really long time, war occurred: a conflict between two nations, a common story.\n" +
                "Yet, the strange thing is, all soldiers in that country - for some reason - lost all the will to fight, to defend their own hometown. Their bodies no longer responded, they could barely pick up their weapon.\n" +
                "What happened next is just the whole stage of tragedy: vanguards couldn't swing their swords, archers couldn't string their bows. They were dead standing there, looking at the battlefield, staring at each other, whispering some weird words, before getting crushed by enemies' firepower.\n" +
                "Everyone knew that they were dead meat, yet they didn't run, resist, or even felt scared.\n" +
                "Instead, they started singing...\n" +
                "\"At the end of it all, consciousness began to escape from my mind\n" +
                "I saw the town I once hated riddled with holes, but this brought me no joy\n" +
                "There, I see a wandering traveler\n" +
                "I hear a strange song, yet somewhat familiar\n" +
                "I see our mountains\"");
        setSkill(
                PrintColor.BBlue("Appoggiatura") + ": Greatly reduces damage taken. Attack additionally inflicts " + PrintColor.Blue("Fragile") + ", lasts for 2 turns.\n" +
                PrintColor.BBlue("Encore") + ": If an attack initiates " + PrintColor.Blue("Fragile") + " on its target, that attack deals bonus damage. Otherwise, the duration of " + PrintColor.Blue("Fragile") + " is refresh and its effect is enhanced (stacks).\n" +
                PrintColor.BBlue("Duetto") + ": Each attack heals all enemy by a percentage of damage dealt. When an enemy attacks a " + PrintColor.Blue("Fragile") + " target, it is also healed for a percentage of this unit's ATK.\n" +
                PrintColor.BPurple("Soloist") + ": When all other enemies are defeated, enters Soloist form. During this state, ATK decreases and loses damage reduction, but attack now " + PrintColor.Purple("deals AoE damage and ignores a flat amount of target's RES") + "."
        );
        setMaxHealth(12500);
        setDef((short) 400);
        setRes((short) 600);
        setAtk((short) 100);
        setAp((short) 400);
        setReduction((short) 80);
        healScale = 2.8f;
        fragileStrg = 20;
    }

    @Override
    public void normalAttack(Entity target) {
        if (!soloist) {
            target = getTaunt(target);
            int dmg = getAp();
            if (!target.fragile.inEffect()) {
                target.fragile.setValue(fragileStrg, (short) 3);
                dmg += getAp() * 0.6f;
            } else {
                target.fragile.setValue((short) (Math.max(target.fragile.getValue() + fragileStrg * 0.5f, fragileStrg * 1.5f)), (short) 3);
            }
            int OutputDmg = damageOutput(getAtk(), dmg, target);
            super.normalAttack(target, OutputDmg);
            if (OutputDmg > 0) {
                EnList.forEach(e -> {
                    if (e.isAlive()) e.healing((int) (OutputDmg * healScale * 0.6f));
                });
            }
        }
        else {
            for (Soldier tr : SoList) {
                if (!tr.isAlive()) continue;
                int dmg = getAp();
                if (!tr.fragile.inEffect()) {
                    tr.fragile.setValue(fragileStrg, (short) 3);
                    dmg += getAp() * 1.0f;
                } else {
                    tr.fragile.setValue((short) (Math.max(tr.fragile.getValue() + fragileStrg * 0.5f, fragileStrg * 1.5f)), (short) 3);
                }
                int OutputDmg = damageOutput(getAtk(), dmg, tr);
                super.normalAttack(tr, OutputDmg);
            }
        }
    }

    @Override
    public void preTurnPreparation() {
        if (soloist) return;
        AtomicBoolean allDed = new AtomicBoolean(true);
        EnList.forEach(en -> {
            if (en.isAlive() && !(en instanceof Singer) && !(en instanceof Sanguinaria)) allDed.set(false);
        });
        if (allDed.get()) {
            System.out.println(PrintColor.BRed("Now, lend me your ears!"));
            try {
                menu.playSound("ost\\dawnseeker.wav");
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
            soloist = true;
            setReduction((short) 0);
            setAp((short) (getBaseAp() * 0.6f));
            setResPen((short) 60);
            healScale *= 0.8f;
        }
        super.preTurnPreparation();
    }

    public int getHeal() {
        return (int) (getAp() * healScale);
    }

    @Override
    public void setChallengeMode() {
        ChallengeModeStatsUp();
        skill += "'Fragile' and healing strength increased!";
        fragileStrg += 15;
        healScale += 1.8f;
    }
}
