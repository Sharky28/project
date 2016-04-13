/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.joda.time.DateTime;
import sentiment.SentimentScore;
import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


/**
 *
 * @author sharmarke
 */
public class Processor {

    private String pathToFile = "C:\\Users\\sharmarke\\Documents\\data4.csv";
    private List<SentimentScore> scores;
    CSVReader reader;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public Processor() throws ParseException, IOException {
        scores = new ArrayList<>();

//        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//        DateTime dt = new DateTime();
//        String datetime = dtf.print(dt);
        //       System.out.println(datetime);
        String dateTime = "11/15/2013 08:00:00";
// Format for input
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
// Parsing the date
        DateTime jodatime = dtf.parseDateTime(dateTime);
// Format for output
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MM/dd/yyyy");
// Printing the date
        System.out.println(dtfOut.print(jodatime));
        
        loadScores();

        System.out.println(scores.size());
        //      sorter();
        //    calculateAverages();
        getAvg();
    }

    public void loadScores() throws FileNotFoundException, IOException, ParseException {

        try {
            reader = new CSVReader(new FileReader(pathToFile));

            String[] record = null;

            while ((record = reader.readNext()) != null) {
                String dateString = record[0];
                Date date = formatter.parse(dateString);
//                DateTime correctDate = new DateTime(date);
                double value = Double.parseDouble(record[1]);
                SentimentScore score = new SentimentScore(date, value);
                //       System.out.println(score.getScore());
                scores.add(score);

            }

        } catch (Exception e) {
        }
    }

    public void calculateAverages() {
        List<SentimentScore> newScores = new ArrayList<>();
        Iterator scoreIterator = scores.iterator();
        SentimentScore temp = (SentimentScore) scoreIterator.next();
        DateTime currentDate = temp.getTime();
        while (scoreIterator.hasNext()) {
            SentimentScore currentRow = (SentimentScore) scoreIterator.next();
            if (currentRow.getTime().equals(currentDate)) {
                List<Double> values = new ArrayList<>();
                values.add(currentRow.getScore());
                double sum = 0;
                for (Double value : values) {
                    sum = sum + value;
                }
                double avg = sum / values.size();
                SentimentScore averagedScore = new SentimentScore(currentRow.getTime(), avg);
                System.out.println(averagedScore.toString());
                newScores.add(averagedScore);

                // then parse to new csv 
            }

        }

    }

    

    public void getAvg() {
        Map<DateTime, Double> collect = scores.stream().collect(
                Collectors.groupingBy(SentimentScore::getTime,
                        Collectors.averagingDouble(SentimentScore::getScore)));

        Collection<Double> col = collect.values();
        //        for (Double val : col) {
        //            
        //            System.out.println( val);
        //        }

        for (Map.Entry<DateTime, Double> entry : collect.entrySet()) {
            long date = entry.getKey().getMillis();
            double score = entry.getValue();
            Date f = new Date(date);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
            
            
            

            System.err.println("date: " + df.format(f) + ", avg :" + score);
        }
    }

    public void printScoreDetails(List<SentimentScore> scores) {
        for (SentimentScore score : scores) {
            System.out.println(score);
        }
    }

    public static void main(String[] args) throws ParseException, IOException {
        Processor p = new Processor();
    }
}
