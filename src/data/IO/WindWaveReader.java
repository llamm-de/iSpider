/*
 * The MIT License
 *
 * Copyright 2017 lukas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package data.IO;

import data.DataSet;
import data.InputPattern;
import data.TrainingPattern;
import helper.NdTimeSeries;
import java.util.Collection;
import java.util.HashMap;


/**
 *
 * @author lukas
 */
public class WindWaveReader extends JboReader{

    //Attributes
    //Keys for identification of entries in timeseries
    private final String[] allKeys =  new String[]{
            "wind_dir_x","wind_dir_y", "wind_v",
            "wave_dir_x", "wave_dir_y", "wave_Tm","wave_hm"};
    
    private final String[] windKeys = new String[]{"wind_dir_x","wind_dir_y", "wind_v"};
    
    private final String[] waveKeys = new String[]{"wave_dir_x", "wave_dir_y", "wave_Tm","wave_hm"};
    
   
    /**
     * Method for creation of a trainingset from given time series.
     * @param map
     * @return 
     */
    @Override
    public DataSet createTrainingSet(HashMap map) {
        //Create DataSet
        DataSet set = new DataSet();
       
        Collection<TrainingPattern> patterns = map.values();
        for (TrainingPattern pattern : patterns) {
            set.addPattern(pattern);
        }
               
        return set;
    }  
    
    /**
     * Creates dataset with inputpatterns
     * @param map
     * @return 
     */
    public DataSet createInputSet(HashMap map) {
        //Create DataSet
        DataSet set = new DataSet();
       
        Collection<InputPattern> patterns = map.values();
        for (InputPattern pattern : patterns) {
            set.addPattern(pattern);
        }
               
        return set;
    } 
 
    /**
     * Extracts data with time from timeseries
     * @param timeSeries
     * @param mode
     * @return 
     */
    @Override
    public HashMap getDataWithTime(NdTimeSeries timeSeries, String mode){
        //Create new Hashmap
        HashMap map = new HashMap();
        
        //Check case
        String[] keys;
        double[][] data;
        switch(mode){
            case "input":
                keys = this.windKeys;
                //Extract data
                data = timeSeries.getTimeSeries(keys, NdTimeSeries.GetMode.AND);
                //loop over length of data-array
                for(int i = 0; i < data[0].length; i++){
                    double[] input = new double[3];
                    double time = data[0][i];
                    for(int j = 0; j < keys.length; j++){
                        input[j] = data[j+1][i];
                    }
                    map.put(time, new InputPattern(input));
                }
                
                return map;                
                
            case "training":
                keys = this.allKeys;
                //Extract data
                data = timeSeries.getTimeSeries(keys, NdTimeSeries.GetMode.AND);
                //loop over length of data-array
                for(int i = 0; i < data[0].length; i++){
                    double[] input = new double[3];
                    double[] output = new double[4];
                    double time = data[0][i];
                    for(int j = 0; j < 3; j++){
                        input[j] = data[j+1][i];
                    }
                    for(int k =  0; k < 4; k++){
                        output[k] = data[k+4][i];
                    }
                    map.put(time, new TrainingPattern(input, output));
                }
                
                return map; 
                
            default:
                
                throw new UnsupportedOperationException("Mode must be: \"training\" or \"input!\"");
        }
        
    }
    
    /**
     * Extracts data with time from timeseries
     * @param timeSeries
     * @param mode
     * @param timeRange
     * @return 
     */
    public HashMap getDataWithTime(NdTimeSeries timeSeries, String mode, long[] timeRange){
        //Create new Hashmap
        HashMap map = new HashMap();
        
        //Create DataItem-objects from time range 
        NdTimeSeries.DataItem fromTime = new NdTimeSeries.DataItem(timeRange[0]);
        NdTimeSeries.DataItem untilTime = new NdTimeSeries.DataItem(timeRange[1]); 
        
        //Check case
        String[] keys;
        double[][] data;
        switch(mode){
            case "input":
                keys = this.windKeys;
                //Extract data
                data = timeSeries.getTimeSeries(keys, fromTime, true, untilTime, true, NdTimeSeries.GetMode.AND);
                //loop over length of data-array
                for(int i = 0; i < data[0].length; i++){
                    double[] input = new double[3];
                    double time = data[0][i];
                    for(int j = 0; j < keys.length; j++){
                        input[j] = data[j+1][i];
                    }
                    map.put(time, new InputPattern(input));
                }
                
                return map;                
                
            case "training":
                keys = this.allKeys;
                //Extract data
                data = timeSeries.getTimeSeries(keys, fromTime, true, untilTime, true, NdTimeSeries.GetMode.AND);
                //loop over length of data-array
                for(int i = 0; i < data[0].length; i++){
                    double[] input = new double[3];
                    double[] output = new double[4];
                    double time = data[0][i];
                    for(int j = 0; j < 3; j++){
                        input[j] = data[j+1][i];
                    }
                    for(int k =  0; k < 4; k++){
                        output[k] = data[k+4][i];
                    }
                    map.put(time, new TrainingPattern(input, output));
                }
                
                return map; 
                
            default:
                
                throw new UnsupportedOperationException("Mode must be: \"training\" or \"input!\"");
        }
        
    }
    
    
    
}
