/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author sharmarke
 */
public class Stock {

    private Date date;
    private double open, high, low, close, adjustedClose;
    private int volume;

    public Stock(Date date, double open, double high, double low, double close, int volume, double adjustedClose) {
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

    public String getFormatedDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }

    public void setDate(Date d) {
        this.date = d;
    }

    public String getNumericalDate() {
//        GregorianCalendar temp =new GregorianCalendar(date.getYear(), date.getMonth(), date.getDate());
//        String result = ""+this.date.get(Calendar.DAY_OF_MONTH)+","+date.get(Calendar.MONTH);
        String result2 = date.getDate() + "/" + (date.getMonth() + 1);
        return result2;
    }

    public double getOpen() {
        return format(open);
    }

    public void setOpen(double o) {
        this.open = o;
    }

    public double getHigh() {
        return format(high);
    }

    public void setHigh(double h) {
        this.high = h;
    }

    public double getLow() {
        return format(low);
    }

    public void setLow(double l) {
        this.low = l;
    }

    public double getClose() {
        return format(close);
    }

    public void setClose(double c) {
        this.close = c;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(int v) {
        this.volume = v;
    }

    public double getAdjustedClose() {
        return format(adjustedClose);
    }

    public void setAdjustedClose(double aC) {
        this.adjustedClose = aC;
    }

    public Object[] toObjectArray() {
        return new Object[]{getFormatedDate(), getOpen(), getClose(), getHigh(), getLow(), getVolume(), getAdjustedClose()};
    }

    public double format(Double value) {
        DecimalFormat df = new DecimalFormat("#.###");
        return Double.valueOf(df.format(value));
    }

}
