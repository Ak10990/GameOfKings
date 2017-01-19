package com.android.thoughtworks.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by akanksha on 8/1/17.
 */
public class KingDb extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private int battleNum;
    private double rating;
    private int attackWinTotal;
    private int attackLostTotal;
    private int attackDrawTotal;
    private int defenseWinTotal;
    private int defenseLostTotal;
    private int defenseDrawTotal;
    private String strength;
    private String battleType;
    private int color;

    public KingDb() {
    }

    public KingDb(long id, String name, int battleNum, double rating,
                  int attackWinTotal, int attackLostTotal, int attackDrawTotal,
                  int defenseWinTotal, int defenseLostTotal, int defenseDrawTotal,
                  String strength, String battleType, int color) {
        this.id = id;
        this.name = name;
        this.battleNum = battleNum;
        this.rating = rating;
        this.attackWinTotal = attackWinTotal;
        this.attackLostTotal = attackLostTotal;
        this.attackDrawTotal = attackDrawTotal;
        this.defenseWinTotal = defenseWinTotal;
        this.defenseLostTotal = defenseLostTotal;
        this.defenseDrawTotal = defenseDrawTotal;
        this.strength = strength;
        this.battleType = battleType;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBattleNum() {
        return battleNum;
    }

    public double getRating() {
        return rating;
    }

    public int getAttackWinTotal() {
        return attackWinTotal;
    }

    public int getAttackLostTotal() {
        return attackLostTotal;
    }

    public int getAttackDrawTotal() {
        return attackDrawTotal;
    }

    public int getDefenseWinTotal() {
        return defenseWinTotal;
    }

    public int getDefenseLostTotal() {
        return defenseLostTotal;
    }

    public int getDefenseDrawTotal() {
        return defenseDrawTotal;
    }

    public String getStrength() {
        return strength;
    }

    public String getBattleType() {
        return battleType;
    }

    public int getColor() {
        return color;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBattleNum(int battleNum) {
        this.battleNum = battleNum;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setAttackWinTotal(int attackWinTotal) {
        this.attackWinTotal = attackWinTotal;
    }

    public void setAttackLostTotal(int attackLostTotal) {
        this.attackLostTotal = attackLostTotal;
    }

    public void setAttackDrawTotal(int attackDrawTotal) {
        this.attackDrawTotal = attackDrawTotal;
    }

    public void setDefenseWinTotal(int defenseWinTotal) {
        this.defenseWinTotal = defenseWinTotal;
    }

    public void setDefenseLostTotal(int defenseLostTotal) {
        this.defenseLostTotal = defenseLostTotal;
    }

    public void setDefenseDrawTotal(int defenseDrawTotal) {
        this.defenseDrawTotal = defenseDrawTotal;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public void setBattleType(String battleType) {
        this.battleType = battleType;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
