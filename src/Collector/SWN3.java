/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import com.sun.org.apache.xpath.internal.patterns.NodeTest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sharmarke
 */
public class SWN3 {

    private HashMap<String, Double> _dict;
    public static final String STR_POS = "strong_positive";
    public static final String POS = "positive";
    public static final String WEK_POS = "weak_positive";
    public static final String STR_NEG = "strong_negative";
    public static final String NEG = "negative";
    public static final String WEK_NEG = "weak_negative";
    public static final String NU = "";

    public SWN3(String pathToSWN) {

        _dict = new HashMap<String, Double>();
        HashMap<String, Double> _temp = new HashMap<String, Double>();
        try {
            BufferedReader csv = new BufferedReader(new FileReader(pathToSWN));
            String line = "";
            while ((line = csv.readLine()) != null) {
                if (line.startsWith("#") || line.startsWith("				#")) {
                    continue;
                }

                String[] data = line.split("\t");
                Double score = Double.parseDouble(data[2])
                        - Double.parseDouble(data[3]);
                String[] words = data[4].split(" ");
                for (String w : words) {
                    String[] w_n = w.split("#");
		
                    if (_temp.containsKey(w_n[0])) {
                        Double v = _temp.get(w_n[0]);
                        _temp.put(w_n[0], (v + score) / 2);
                    } else {
                        _temp.put(w_n[0], score);
                    }
                }
            }
            Set<String> temp = _temp.keySet();
            for (Iterator<String> iterator = temp.iterator(); iterator
                    .hasNext();) {
                String word = (String) iterator.next();
                double score = _temp.get(word);

                _dict.put(word, new Double(score));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public double extract(String word) {
        double score = 0;
        try {
           score  = _dict.get(word).doubleValue();
        } catch (NullPointerException e) {
           
        }
        return score;
    }
  
    

    public double extractSentence(List<String> sentence) {
        double d = 0.0d;
        for (String word : sentence) {
            if (_dict.containsKey(word)) {
                d += _dict.get(word).doubleValue();
            }
        }
        return d;
    }
    
   public HashMap<String, Double> getDictionary()
   {
       return _dict;
   }
}
