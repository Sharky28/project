/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author sharmarke
 */
public class Stock {

    private Date date;
    private double open ,high, low, close, volume, adjustedClose;

    public Stock(Date date, double open, double high, double low, double close, double volume, double adjustedClose) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.adjustedClose = adjustedClose;
    }

   

    public Date getDate() {
        return date;
    }

    public void setDate(Date d) {
        this.date = d;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double o) {
        this.open = o;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double h) {
        this.high = h;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double l) {
        this.low = l;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double c) {
        this.close = c;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double v) {
        this.volume = v;
    }

    public double getAdjustedClose() {
        return adjustedClose;
    }

    public void setAdjustedClose(double aC) {
        this.adjustedClose = aC;
    }

}
