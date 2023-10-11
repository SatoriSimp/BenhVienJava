package com.company.Entitiy.Enemy;

import com.company.Entitiy.Allied.Soldier;
import com.company.Entitiy.Entity;
import com.company.PrintColor;

import java.util.ArrayList;
import java.util.Collections;

import static com.company.EntitiesList.SoList;

abstract public class Enemy extends Entity implements EnemyBehaviors {
    public boolean challengeMode = false;
    String trait, skill, shortDes, cmAdd = PrintColor.RED_BOLD_BRIGHT + "Challenge Mode's add-on: " + PrintColor.red;
    boolean bleeding = false, canRevive = false;
    public boolean isElite = false;
    short mana = 0;
    final int HIGHEST_MSSING_HLTH = 1,
                LOWEST_DEF = 2,
                LOWEST_CRNT_HLTH = 3,
                HIGHEST_CRNT_HLTH = 4,
                HIGHEST_DEF = 5,
                LOWEST_ENRGY = 7,
                LOWEST_PSON_DUR = 8,
                HIGHEST_HP_PERCENTAGE = 9;

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public boolean isBleeding() {
        return bleeding;
    }

    public void setBleeding(boolean bleeding) {
        this.bleeding = bleeding;
    }

    public boolean isCanRevive() {
        return canRevive;
    }

    public boolean isElite() { return isElite; }

    public void setCanRevive(boolean canRevive) {
        this.canRevive = canRevive;
    }

    @Override
    public void printHealthBar(String color) {
        if (this.isElite) color = PrintColor.Bred;
        super.printHealthBar(color);
    }

    public void ChallengeModeStatsUp() {
        challengeMode = true;
        skill += '\n' + cmAdd;
        addMaxHealth(getMaxHealth() / 10);
        setAtk((short) (getBaseAtk() * 11 / 10));
        setAp((short) (getBaseAp() * 11 / 10));
        setDef((short) (getBaseDef() * 11 / 10));
    }

    public void revive() {
        canRevive = false;
    }

    /**
     * Scan thru a list of friendly units, <span style="color:#00F3FF"><i>pick those with highest taunt level</i></span>
     *
     * @return <span style="color:yellow">A friendly unit with highest priority</span>
     */
    public Soldier targetSearch() {
        ArrayList<Soldier> tempList = new ArrayList<>(SoList);
        tempList.removeIf(curr -> !curr.isAlive());
        if (tempList.isEmpty()) return null;
        tempList.sort((o1, o2) -> o2.getTauntLevel() - o1.getTauntLevel());
        final short highestPriority = tempList.get(0).getTauntLevel();
        final ArrayList<Soldier> prioList = new ArrayList<Soldier>();
        for (Soldier sol : tempList) {
            if (sol.getTauntLevel() == highestPriority) prioList.add(sol);
        }
        Collections.shuffle(prioList);
        return prioList.get(0);
    }

    /**
     * Scan thru a list of current alive friendly units to find the one that is:<br>
     * <ol><li><span style="color:red"><i>Most heavily injured</i></span></li>
     * <li><span style="color:red"><i>Most vulnerable to physic damage</i></span></li>
     * <li><span style="color:red">With lowest health</span></li>
     * <li><span style="color:red">Least vulnerable to physic damage</span></li>
     * <li><span style="color:red">Highest current health</span></li>
     * <li><span style="color:red">Second least vulnerable to physic damage</span></li>
     * <li><span style="color:red">Lowest current mana</span></li>
     * <li><span style="color:red">Least remaining duration of 'poison'</span></li>
     * </ol>
     * @param searchType
     * @return <span style="color:yellow">A Soldier-type entity satisfies said condition</span>
     */
    public Soldier targetSearch(int searchType) {
        ArrayList<Soldier> tempList = new ArrayList<>(SoList);
        tempList.removeIf(curr -> !curr.isAlive());
        if (tempList.isEmpty()) return null;
        tempList.sort((o1, o2) -> {
            int res = 0;
            switch (searchType) {
                case HIGHEST_MSSING_HLTH:
                    res = o2.getMissingHealth() * 100 / o2.getMaxHealth() - o1.getMissingHealth() * 100 / o1.getMaxHealth();
                    break;
                case LOWEST_DEF:
                    res = o1.getDef() - o2.getDef();
                    break;
                case LOWEST_CRNT_HLTH:
                    res = o1.getHealth() - o2.getHealth();
                    break;
                case HIGHEST_DEF: case 6:
                    res = o2.getDef() - o1.getDef();
                    break;
                case HIGHEST_CRNT_HLTH:
                    res = o2.getHealth() - o1.getHealth();
                    break;
                case LOWEST_ENRGY:
                    res = o1.mana - o2.mana;
                    break;
                case LOWEST_PSON_DUR:
                    res = o1.poison.getDuration() - o2.poison.getDuration();
                    break;
                case HIGHEST_HP_PERCENTAGE:
                    res = (o2.getHealth() * 100 / o2.getMaxHealth()) - (o1.getHealth() * 100 / o1.getMaxHealth());
                    break;
            }
            return res;
        });
        return searchType != 6 ? tempList.get(0) : tempList.size() <= 1 ? null : tempList.get(1);
    }

    @Override
    public void printInfo() {
        System.out.println("Known as: " + PrintColor.BYellow(getName()));
        super.printInfo();
        System.out.println('\n' + trait + PrintColor.Byellow + "\n\nAbilities:\n" + PrintColor.def + skill + '\n');
    }

    @Override
    public void normalAttack(Entity target) {
        super.normalAttack(target);
        mana++;
    }
}
