/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentiment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sharmarke
 */
public class Lexicons {

    private String sentiWordNetFile = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\SentiWordNet_3.0.0_20130122.txt";
    private String negationsFile = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\TwitterTest\\Twitterfiles\\negations.txt";
    private String intensifiersFile = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\TwitterTest\\Twitterfiles\\intensifiers.txt";

    Map<String, Double> dictionary;
    List<String> negations;
    static Map<String, Double> intensifiers;

    private final SWN3 swn;

    public Lexicons() {
        swn = new SWN3(sentiWordNetFile);
        dictionary = new HashMap<String, Double>();
        negations = new ArrayList<String>();
        intensifiers = new HashMap<String, Double>();
        try {

            loadDictionary();
            loadNegations();
            removeNegationsFromDict();
            loadIntensifiers();
            removeIntensifiersFromDict();
   //         System.out.println("Loading files Successfull");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please check files are in correct locations");
        }

    }

    public static void main(String[] args) {

    }

    public Map<String, Double> getDictionary() {
        return dictionary;
    }

    public void loadDictionary() {
        this.dictionary = swn.getDictionary();
    }

    public List<String> getNegations() {
        return negations;
    }

    public void loadNegations() {

        String line = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(negationsFile));
            while ((line = reader.readLine()) != null) {
                negations.add(line);
            }

        } catch (FileNotFoundException x) {
            System.out.println("Files not found");
        } catch (IOException ix) {
            System.out.println(ix.getMessage());
        }

    }

    public Map<String, Double> getIntensifiers() {
        return intensifiers;
    }

    public void loadIntensifiers() {

        String line = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(intensifiersFile));

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\t");
                intensifiers.put(parts[0], (Double) Double.parseDouble(parts[1]));
            }
        } catch (FileNotFoundException x) {
            System.out.println("Files not found");
        } catch (IOException ix) {
            System.out.println(ix.getMessage());
        }
    }

    public void removeIntensifiersFromDict() {
        Collection<String> coll = intensifiers.keySet();
        for (String negation : coll) {
            if (dictionary.containsKey(negation)) {
                dictionary.remove(negation);
            }
        }
    }

    public void removeNegationsFromDict() {
        for (String negation : negations) {
            if (dictionary.containsKey(negation)) {
                dictionary.remove(negation);
            }
        }
    }

}
