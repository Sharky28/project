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

/**
 *
 * @author sharmarke
 */
public class SentimentScore {
    
    private DateTime time;
    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private double score;
    
    
    public SentimentScore(DateTime t, double s) {
        time=t;
        score=s;
        
    }

    @Override
    public String toString() {
        Date d = time.toDate();
        String formatedDate = formatter.format(d);
        return "SentimentScore{" + "time=" + time+ ", score=" + score + '}';
    }

    public DateTime getTime() {
        return time;
    }

    public double getScore() {
        return score;
    }
    
     public Object[] toObjectArray() {
        return new Object[]{getTime(), getScore()};
    }

    
    
    
    
}
