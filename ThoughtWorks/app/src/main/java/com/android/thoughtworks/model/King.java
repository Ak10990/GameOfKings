package com.android.thoughtworks.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.thoughtworks.utils.Constants;

/**
 * Created by akanksha on 8/1/17.
 */
public class King implements Parcelable {

    private long id;
    private String name;
    private int battleNum;
    private double rating = Constants.INITIAL_RATING;
    private int attackWinTotal = 0;
    private int attackLostTotal = 0;
    private int attackDrawTotal = 0;
    private int defenseWinTotal = 0;
    private int defenseLostTotal = 0;
    private int defenseDrawTotal = 0;
    private String strength;
    private String battleType;
    private int color;

    public King() {
    }

    protected King(Parcel in) {
        id = in.readLong();
        name = in.readString();
        battleNum = in.readInt();
        rating = in.readDouble();
        attackWinTotal = in.readInt();
        attackLostTotal = in.readInt();
        attackDrawTotal = in.readInt();
        defenseWinTotal = in.readInt();
        defenseLostTotal = in.readInt();
        defenseDrawTotal = in.readInt();
        strength = in.readString();
        battleType = in.readString();
        color = in.readInt();
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

    public void addToWinAttack() {
        attackWinTotal++;
    }

    public void addToLostAttack() {
        attackLostTotal++;
    }

    public void addToDrawAttack() {
        attackDrawTotal++;
    }

    public void addToWinDefense() {
        defenseWinTotal++;
    }

    public void addToLostDefense() {
        defenseLostTotal++;
    }

    public void addToDrawDefense() {
        defenseDrawTotal++;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeInt(battleNum);
        dest.writeDouble(rating);
        dest.writeInt(attackWinTotal);
        dest.writeInt(attackLostTotal);
        dest.writeInt(attackDrawTotal);
        dest.writeInt(defenseWinTotal);
        dest.writeInt(defenseLostTotal);
        dest.writeInt(defenseDrawTotal);
        dest.writeString(strength);
        dest.writeString(battleType);
        dest.writeInt(color);
    }

    public static final Creator<King> CREATOR = new Creator<King>() {
        @Override
        public King createFromParcel(Parcel in) {
            return new King(in);
        }

        @Override
        public King[] newArray(int size) {
            return new King[size];
        }
    };

    public King getKingFromDb(KingDb kingDb) {
        id = kingDb.getId();
        name = kingDb.getName();
        battleNum = kingDb.getBattleNum();
        rating = kingDb.getRating();
        attackWinTotal = kingDb.getAttackWinTotal();
        attackLostTotal = kingDb.getAttackLostTotal();
        attackDrawTotal = kingDb.getAttackDrawTotal();
        defenseWinTotal = kingDb.getDefenseWinTotal();
        defenseLostTotal = kingDb.getDefenseLostTotal();
        defenseDrawTotal = kingDb.getDefenseDrawTotal();
        strength = kingDb.getStrength();
        battleType = kingDb.getBattleType();
        color = kingDb.getColor();
        return this;
    }

    public KingDb getKingDb() {
        return new KingDb(this.id, this.name, this.battleNum, this.rating,
                this.attackWinTotal, this.attackLostTotal, this.attackDrawTotal, this.defenseWinTotal, this.defenseLostTotal, this.defenseDrawTotal,
                this.strength, this.battleType, this.color);
    }

}
