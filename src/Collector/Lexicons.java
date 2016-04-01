/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sharmarke
 */
public class Lexicons {

   

    private  String sentiWordNetFile = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\SentiWordNet_3.0.0_20130122.txt";
    private  String negationsFile = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\TwitterTest\\Twitterfiles\\negations.txt";
    private  String intensifiersFile = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\TwitterTest\\Twitterfiles\\intensifiers.txt";

    private Map<String, Double> dictionary;
    private List<String> negations;
    Map<String, Double> intensifiers;

    private final SWN3 swn = new SWN3(sentiWordNetFile);

    public Lexicons() {
        try {
            loadDictionary();
            loadNegations();
            removeNegationsFromDict();
            loadIntensifiers();
            removeIntensifiersFromDict();
            System.out.println("Loading files Successfull");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please check files are in correct locations");
        }

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

    public void loadNegations() throws FileNotFoundException, IOException {

        String line = "";

        BufferedReader reader = new BufferedReader(new FileReader(negationsFile));
        try {

            while ((line = reader.readLine()) != null) {
                negations.add(line);
            }

        } catch (Exception x) {
            System.out.println("Files have not been Loaded, check that they are in correct location");
        }
        reader.close();

    }

    public Map<String, Double> getIntensifiers() {
        return intensifiers;
    }

    public void loadIntensifiers() throws FileNotFoundException, IOException {

        String line = "";
        BufferedReader reader = new BufferedReader(new FileReader(intensifiersFile));
        try {

            int x = 0;
            while ((line = reader.readLine()) != null) {
                String s = "";
                String[] parts = line.split("\t");
                intensifiers.put(parts[0], (Double) Double.parseDouble(parts[1]));
            }
        } catch (Exception ex) {
            System.out.println("Files have not been Loaded, check that they are in correct location");
        } finally {
            reader.close();
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
