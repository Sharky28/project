/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentiment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author sharmarke
 */
public class SentimentScore {

    private DateTime time;
    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private DateTimeFormatter dtf;
    private double score;

    public SentimentScore(DateTime t, double s) {
        dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
        time = t;
        score = s;

    }

    public SentimentScore(Date d, double s) {
        time = new DateTime(d);
        score = s;
    }

    @Override
    public String toString() {
       
        String formatedDate = dtf.print(time);
        return "SentimentScore{" + "time=" + formatedDate+ ", score=" + score + '}';
    }

    public DateTime getTime() {
        return time;
    }
    
    
    
    public String getDateString()
    {
        return dtf.print(time);
    }

    public double getScore() {
        return score;
    }

    public Object[] toObjectArray() {
        return new Object[]{getTime(), getScore()};
    }

//    public String getFormattedDate() {
//        return dtf.print(time.getMillis());
//    }

}
