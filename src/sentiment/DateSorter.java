/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentiment;

/**
 *
 * @author sharmarke
 */
    
    import java.util.Comparator;
 
/** IdSorter class is used to sort objects using employee Id*/
public class DateSorter implements Comparator<SentimentScore>{
 

    @Override
    public int compare(SentimentScore o1, SentimentScore o2) {
       return o1.getTime().compareTo(o2.getTime());
    }
}

