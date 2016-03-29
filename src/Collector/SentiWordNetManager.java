/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.util.List;



/**
 *
 * @author sharmarke
 */
public class SentiWordNetManager {

    private final String path = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\SentiWordNet_3.0.0_20130122.txt";
    private final SWN3 swn ;
    public SentiWordNetManager()
    {
         swn = new SWN3(path);
    }
   
   
    
    
    public SWN3 getSwn3()
    {
        return this.swn;
    }
    
    public  double calculateWordScore(String word)
    {
      
       double score=  swn.extract(word);
       return score;
    }
    
    public double calculateSentancescore(List<String> sentance)
    {
        double score = swn.extractSentence(sentance);
        return score;
    }
}
