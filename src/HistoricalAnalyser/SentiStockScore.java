/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HistoricalAnalyser;

import java.util.Date;

/**
 *
 * @author sharmarke
 */
public class SentiStockScore {

    private Date date;
    private double closingPrice;
    private double sentimentPolarity;

    public SentiStockScore() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(double closingPrice) {
        this.closingPrice = closingPrice;
    }

    public double getSentimentPolarity() {
        return sentimentPolarity;
    }

    public void setSentimentPolarity(double sentimentPolarity) {
        this.sentimentPolarity = sentimentPolarity;
    }

    @Override
    public String toString() {
        return "SentiStockScore{" + "date=" + date + ", closingPrice=" + closingPrice + ", sentimentPolarity=" + sentimentPolarity + '}';
    }
    

}
