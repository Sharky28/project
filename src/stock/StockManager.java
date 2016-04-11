
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author sharmarke
 */
public class StockManager {

    private List<Stock> stocks;

    private String[] columnNames = {"Date", "Open", "High", "Low", "Close", "Volume", "AdjClose"};
    
     static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    public StockManager() {

        stocks = new ArrayList<Stock>();

    }

    public List<Stock> getStocks(List<String> stockLines) {
        for (String stock : stockLines) {
            addStock(stock);
        }
        Collections.reverse(stocks);// yahoo gives them in backwards order
        return stocks;
    }

    public void addStock(String line) {
        DateFormat df = new SimpleDateFormat("y-M-d");
        StringTokenizer st = new StringTokenizer(line, ",");
        try {
            Date date = df.parse(st.nextToken());
            double open = Double.parseDouble(st.nextToken());
            double high = Double.parseDouble(st.nextToken());
            double low = Double.parseDouble(st.nextToken());
            double close = Double.parseDouble(st.nextToken());
            double volume = Double.parseDouble(st.nextToken());
            double adjClose = Double.parseDouble(st.nextToken());

            Stock temp = new Stock(date, open, high, low, close, volume, adjClose);
            stocks.add(temp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showStocks() {
        for (Stock stock : stocks) {
            System.out.println("Date :" + stock.getDate().toString()
                    + "\nOpen :" + stock.getOpen()
                    + "\nClose :" + stock.getClose()
                    + "\nHigh :" + stock.getHigh()
                    + "\nLow :" + stock.getLow()
                    + "\nVolume :" + stock.getVolume()
                    + "\nAdjusted Close :" + stock.getAdjustedClose()
                    + "\n*******************");
        }
    }

    public String[] getColumnNames() {
        return this.columnNames;
    }

    public Object[][] getData() {

        Object[][] table = new Object[stocks.size()][];
        for (int i = 0; i < stocks.size(); i++) {
            table[i] = stocks.get(i).toObjectArray();
        }
        return table;
    }
    
//    public static GregorianCalendar convertFromYMD(String yy_mm_dd) throws ParseException{
//
//        // this actually works, got rid of the original code idea
//        String[] splitDate = yy_mm_dd.split("-");
//        int year = Integer.parseInt(splitDate[0]);
//        int month = (Integer.parseInt(splitDate[1]) - 1);
//        int days = Integer.parseInt(splitDate[2]);
//        
//
//        // dates go in properly
//        GregorianCalendar dateConverted = new GregorianCalendar(year, month, days);
//        String finalDate = format(dateConverted);
//        return dateConverted;
//    }
    
//     public static String format(GregorianCalendar date) throws ParseException{
//
//       fmt.setCalendar(date);
//        String dateFormatted = fmt.format(date.getTime());
//   //     System.out.println(dateFormatted);
//        return dateFormatted;
//    }
    

}
