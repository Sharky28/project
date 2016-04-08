/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentiment;

import org.joda.time.DateTime;

/**
 *
 * @author sharmarke
 */
public class SentimentScore {
    
    private DateTime time;
    private double score;
    
    
    public SentimentScore(DateTime t, double s) {
        time=t;
        score=s;
        
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
