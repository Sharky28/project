/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.io.File;

/**
 *
 * @author sharmarke
 */
public class StockMarket {
    
    public static void main(String[] args) {
       
        ReadFiles r = new ReadFiles();
        System.out.println(r.checkIsFile());
        System.err.println(""+r.findRowNumber());
        r.convertToArray();
        r.printArray();
        Formulas f = new Formulas();
        
        f.createDataArray(r.getArray());
        f.printArray();
    }
}
