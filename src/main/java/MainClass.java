
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class MainClass {
    // Load data from CSV file
    public static Table loadDataFromCVS(String path) throws IOException {
        Table titanicData=Table.read().csv(path);
        return titanicData;
    }
    
    /// get the structure of the data
    public static String getDataInfoStructure(Table data){
        Table dataStructure= data.structure();
        return dataStructure.toString();
    }
    
    //get Data Summary
    public static String getDataSummary(Table data){
        Table summary = data.summary();
        return summary.toString();
    }
    
    //Adding Columns
    public static Table addDateColumnToData(Table data){
        int rowCount=data.rowCount();
            List<LocalDate> dateList=new ArrayList<LocalDate>();
            for(int i=0;i<rowCount;i++){
                dateList.add(LocalDate.of(2021, 3, i%31==0?1:i%31));
            }
            DateColumn dateColumn = DateColumn.create("Fake Date",dateList);
            data.insertColumn(data.columnCount(),dateColumn);
            return data;
    }
    
    public static void main(String[] args) {
        
        try {
            Table titanicTable = loadDataFromCVS("Titanic.csv");
            System.out.println(getDataInfoStructure(titanicTable));
            
            System.out.println("\n############ Summary Data of Titanic.csv ###################");
            System.out.println(getDataSummary(titanicTable));
            
            System.out.println("\n############## After adding new column #################");
            addDateColumnToData(titanicTable);
            System.out.println(getDataInfoStructure(titanicTable));
            
            System.out.println("\n############## Concatenate t1,t2 tables #################");
            //create table t1 of two colums "animals","cuteness"
            String[] animals = {"bear", "cat", "giraffe"};
            double[] cuteness = {90.1, 84.3, 99.7};
            Table t1 = Table.create("t1").addColumns(
            StringColumn.create("Animal types", animals),
            DoubleColumn.create("cuteness", cuteness));
            
            //create table t2 of two colums "size","legs"
            int[] legs = {4, 4, 4};
            double[] height = {20.1, 30.2, 40.5};
            Table t2 = Table.create("t2").addColumns(
            IntColumn.create("Legs", legs),
            DoubleColumn.create("height", height));
            
            Table concatTable = t1.copy();
            concatTable.setName("concatenated table");
            concatTable.concat(t2);
            System.out.println(t1);
            System.out.println(t2);
            System.out.println(concatTable);
            
            System.out.println("\n############## appending t1,t3 tables #################");
            String[] animals1 = {"Lion","Tiger"};
            double[] cuteness1 = {100.0,150.0};
            Table t3 = Table.create("t3").addColumns(
                    StringColumn.create("Animal types",animals1 ),
                    DoubleColumn.create("cuteness", cuteness1));
            Table appendedTable = t1.copy();
            appendedTable.append(t3);
            System.out.println(t1);
            System.out.println(t3);
            System.out.println(appendedTable);
            
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
