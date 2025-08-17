package com.phoenix;

import java.time.LocalDate;

public class Coin {
    int coinId;
    String country;
    int denomination;
    int yearOfMinting;
    double currentValue;
    LocalDate aquireDate;

    public Coin() {
    }

    public Coin(int coinId, String country, int denomination, int yearOfMinting, double currentValue, LocalDate aquireDate) {
        this.coinId = coinId;
        this.country = country;
        this.denomination = denomination;
        this.yearOfMinting = yearOfMinting;
        this.currentValue = currentValue;
        this.aquireDate = aquireDate;
    }

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int getYearOfMinting() {
        return yearOfMinting;
    }

    public void setYearOfMinting(int yearOfMinting) {
        this.yearOfMinting = yearOfMinting;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public LocalDate getAquireDate() {
        return aquireDate;
    }

    public void setAquireDate(LocalDate aquireDate) {
        this.aquireDate = aquireDate;
    }

    @Override
    public String toString() {
        return "CoinId:" + coinId + ", Country:" + country + ", Denomination:" + denomination + ", YearOfMinting:"
                + yearOfMinting + ", CurrentValue:" + currentValue + ", AquireDate:" + aquireDate;
    }
}
