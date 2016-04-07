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
public class PreProcessor {

  
    public static String normalizeTweet(String tweet) {
        String s = tweet;
        s = s.replaceAll("https?://\\S+\\s?", "") // removes http / https
                .replaceAll("ftps?://\\S+\\s?", "") // removes ftp / ftps
                .replaceAll("@\\w+|#\\w+|\\bRT\\b", "") // removes hash tags and RT and words after
                .replaceAll("\n", " ")// removes empty lines
                .replaceAll("[^\\p{L}\\p{N} ]+", " ") // removes numbers
                .replaceAll(" +", " ")// removes double spacing
 //               .replaceAll("(.)\\1+", "$1") // removes duplicates such as aaa bbbb
                .trim();

        return s;
    }
    
   

   
}
