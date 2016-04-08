/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentiment;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sharmarke
 */
public class SentimentManager {
    
    List<SentimentScore> scores;
    
    
    public SentimentManager()
    {
        scores = new ArrayList<>();
    }
    
    public void addScore(SentimentScore score)
    {
        scores.add(score);
    }
    
    public void printScores()
    {
        for (SentimentScore score : scores) {
            System.out.println("Time :"+ score.getTime() +
                    "\nScore :"+ score.getScore());
        }
    }
    
    public List<SentimentScore> getScores()
    {
        return this.scores;
    }
    
    public List<List<SentimentScore>> getScoresSubList(int size)
    {
        List<List<SentimentScore>> smallerLists = Lists.partition(scores, size);
        return  smallerLists;
    }
    
    
    
    public Object[][] getData() {

        Object[][] table = new Object[scores.size()][];
        for (int i = 0; i < scores.size(); i++) {
            table[i] = scores.get(i).toObjectArray();
        }
        return table;
    }
}
