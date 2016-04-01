/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.util.StringTokenizer;
import java.io.*;

/**
 *
 * @author sharmarke
 */
public class ReadFiles {

    File file = new File("C:\\stocks\\AppleStocks.csv");
    int row = 0;
    String[][] items;

    
    public boolean checkIsFile() {
        return file.isFile();
    }

    public int findRowNumber() {
        row = 0;
        if (checkIsFile()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while (reader.readLine() != null) {
                    row++;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("This is not a File");
        }
        return row;

    }

    public void convertToArray() {
        int r = 0;
        items = new String[findRowNumber()][7];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                while (tokenizer.hasMoreTokens()) {
                    for (int i = 0; i < 7; i++) {
                        items[r][i] = tokenizer.nextToken();
                    }
                    r++;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // prints out items array
    public void printArray() {
        for (int x = 0; x < items.length; x++) {
            System.out.printf("%s - ", x);
            for (int y = 0; y < items[x].length; y++) {
                System.out.printf("%s ", items[x][y]);
            }
            System.out.println();
        }
    }

    public String[][] getArray() {
        return items;
    }
}
