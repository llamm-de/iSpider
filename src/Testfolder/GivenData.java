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
package Testfolder;

import data.DataSet;
import data.IO.WindWaveReader;
import data.InputPattern;
import helper.NdTimeSeries;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import tools.FunctionGraph;
import tools.ToolClass;

/**
 *
 * @author lukas
 */
public class GivenData {
    
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        //Read .jbo-file and extract time series
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file to open...");
            System.out.println("Choose a file for traingdata...");
        //Open dialog and choose file
        int response = fileChooser.showOpenDialog(null);
        File file = null;
        String filename = null;
        if(response == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
            filename = file.getAbsolutePath();
            System.out.println("\t" + "File chosen: " + filename);
            System.out.println("");
        }else{
            System.out.println("\t" + "No File Chosen!");
        }        
        WindWaveReader reader = new WindWaveReader();
        NdTimeSeries timeSeries = reader.readFile(file);
        
        String[] allKeys =  new String[]{
            "wind_dir_x","wind_dir_y", "wind_v",
            "wave_dir_x", "wave_dir_y", "wave_Tm","wave_hm"};
        
        String[] waveKeys = new String[]{"wave_dir_x", "wave_dir_y", "wave_Tm","wave_hm"};
    
        String[] windKeys = new String[]{"wind_dir_x","wind_dir_y", "wind_v"};
        
        double[][] allData = timeSeries.getTimeSeries(allKeys, NdTimeSeries.GetMode.AND);
        double[][] windData = timeSeries.getTimeSeries(windKeys, NdTimeSeries.GetMode.OR);  
        
        //Get data which shall be computed (no wave, but wind existing in the set)
        DataSet inputSet = new DataSet();
        ArrayList<Double> time = new ArrayList<>();
        for(int i = 0; i < allData[0].length; i++){
            double[] temp = new double[3];
            if(Math.abs(allData[1][i]) > 1e-13 && Math.abs(allData[2][i]) > 1e-13  && Math.abs(allData[3][i]) > 1e-13 ){
                if(Double.isNaN(allData[4][i])&& Double.isNaN(allData[5][i])&& Double.isNaN(allData[6][i]) && Double.isNaN(allData[7][i])){
                    temp[0] = allData[1][i]; 
                    temp[1] = allData[2][i];
                    temp[2] = allData[3][i];
                    inputSet.addPattern(new InputPattern(temp));
                    time.add(allData[0][i]);
                }
            }
        }
        double[] timeLine = ToolClass.listToArray(time);
                
        FunctionGraph windGraph = new FunctionGraph("Windata", "Date", "Value");
        for(int i = 1; i < allData.length; i++){
            windGraph.addOrUpdateSeries(allData[0], allData[i], allKeys[i-1]);
            windGraph.setSeriesLinesAndShapesVisible(allKeys[i-1], true, false);
        }
        windGraph.plot(800, 800);
    }
    
}
