/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.ArrayList;
/**
 * Toolclass
 * @author LammLukas
 */
public class ToolClass {
    
    public static double[] listToArray(ArrayList<Double> list){
        double[] array = new double[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = list.get(i);
        }
        
        return array;
    }
    
    public static double[] makeRangeArray(double minVal, double maxVal, double step){
        int numEntries = (int)((maxVal- minVal)/step);
        double[] array = new double[numEntries];
        double value = minVal-step;
            for(int i = 0; i < numEntries; i++){
                value += step;
                array[i] = value;
            }
        return array;
    }
    
    public static double getMax(double[] a){
        double result = 0;
        
        for (double value : a) {
            if(result < value){
                result = value;
            }
        }
        return result;
    }
    
    public static double getMin(double[] a){
        double result = 0;
        
        for (double value : a) {
            if(result > value){
                result = value;
            }
        }
        return result;
    }
    
}
