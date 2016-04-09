/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sharmarke
 */
public class Tweet {

    private Date date;
 
    private String tweetTxt;
    
    public Tweet ()
    {
        
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
//    
//    public String getFormatedDate()
//    {
//     
//    }

    public String getTweetTxt() {
        return tweetTxt;
    }

    public void setTweetTxt(String tweetTxt) {
        this.tweetTxt = tweetTxt;
    }

    @Override
    public String toString() {
        return "Tweet{" + "date=" + date.toString() + ", tweetTxt=" + tweetTxt + '}';
    }
    
    
    
    
    
}
