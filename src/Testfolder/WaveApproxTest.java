/*                                       r
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

import data.IO.WindWaveReader;

import data.*;
import helper.NdTimeSeries;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import network.Network.*;
import network.Training.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import tools.Function.HyperbolicTangentFct;
import tools.*;
import tools.Function.IdentityFct;

/**
 * Class for testing the waveapproximation
 * @author lukas
 */
public class WaveApproxTest {
    
    /**
     * MAIN METHOD!!!
     * @param args
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        
        //Initialize parameters for network
        int inNeurons = 3;
        int outNeurons = 4;
        int hiddenNeurons = 6;
        int numLayers = 3;
        double learningRate = 0.01;
        int maxIter = 2000000;
        double maxError = 0.01;
        boolean learningType = false; //true: Online; false: Offline
        
        WaveApproxTest.dispWelcome();
               
        //Create neural network
        FeedForwardNet network = (FeedForwardNet) WaveApproxTest.setUpNetwork(inNeurons, outNeurons, hiddenNeurons, numLayers);
             
        //Setting up learning-algorithm
        Backpropagation learningRule = WaveApproxTest.setLearningAlgorithm(network, learningRate, learningType, maxIter, maxError);
        
        //Read .jbo-file and extract time series
        File file = WaveApproxTest.chooseFile();        
        WindWaveReader reader = new WindWaveReader();
        NdTimeSeries timeSeries = reader.readFile(file);
        //Create trainingset from timeseries
        long[] timeRangeInput = new long[]{(long) 1.103750E12, (long) 1.104440E12};
        long[] timeRange = new long[]{(long) 1.1018592E12, (long) 1.103184E12};
        HashMap dataMap = reader.getDataWithTime(timeSeries, "training", timeRange);
//        HashMap dataMap = reader.getDataWithTime(timeSeries, "training");
        DataSet set = reader.createTrainingSet(dataMap);
        // get set of inputpattern
        HashMap inputMap = reader.getDataWithTime(timeSeries, "training", timeRangeInput);
//        DataSet inputSet = reader.createInputSet(inputMap);
        
        //Plot original data from timeseries
            System.out.println("Given input-data are being displayed...");
        WaveApproxTest.plotGivenData(dataMap);
            System.out.println("");
        
        
        //Start training
            System.out.println("Training started...");
            System.out.println("");
        network.trainNet(set);
        
               
        //check if training was successfull
        int numIter = learningRule.getIterations() + 1;
        double[] errorTest = ToolClass.listToArray(learningRule.getGlobalErrorTest());        
        if(learningRule.isTrainingSuccess()){
            System.out.println("Training successfull!");
            System.out.println("\t" +"Final error: " + errorTest[(numIter-2)]);
            System.out.println("\t" + "Iterations made: " + numIter);
            System.out.println("");
        }else{
            System.out.println("Training NOT successfull!");
            System.out.println("Maximum of iterations reached! Error too big: " + errorTest[(numIter-2)]);
            System.out.println("");
        }
       
        //visualize error
            System.out.println("Results are being displayed...");
        WaveApproxTest.plotErrorData(learningRule);
        
        //visualize computed data
        WaveApproxTest.plotResults(inputMap, network);
        
//        //compute data from wind
//        System.out.println("");
//        System.out.println("Computing data from wind...");
//        String[] allKeys =  new String[]{
//            "wind_dir_x","wind_dir_y", "wind_v",
//            "wave_dir_x", "wave_dir_y", "wave_Tm","wave_hm"};
//        double[][] allData = timeSeries.getTimeSeries(allKeys, NdTimeSeries.GetMode.OR);
//        //Get data which shall be computed (no wave, but wind existing in the set)
//        DataSet inputSet = new DataSet();
//        ArrayList<Double> time = new ArrayList<>();
//        for(int i = 0; i < allData[0].length; i++){
//            double[] temp = new double[3];
//            if(Math.abs(allData[1][i]) > 1e-13 && Math.abs(allData[2][i]) > 1e-13  && Math.abs(allData[3][i]) > 1e-13 ){
//                if(Double.isNaN(allData[4][i])&& Double.isNaN(allData[5][i])&& Double.isNaN(allData[6][i]) && Double.isNaN(allData[7][i])){
//                    temp[0] = allData[1][i]; 
//                    temp[1] = allData[2][i];
//                    temp[2] = allData[3][i];
//                    inputSet.addPattern(new InputPattern(temp));
//                    time.add(allData[0][i]);
//                }
//            }
//        }
//        double[] timeLine = ToolClass.listToArray(time);
//        
//        
//        LinkedList<Pattern> patterns = inputSet.getPatterns();
//        double[][] results = new double[4][patterns.size()];
//        int iterator = 0;
//        for (Pattern pattern : patterns) {
//            InputPattern pat = (InputPattern) pattern;
//            double[] input = pat.getIn();
//            
//            network.solve(input);
//            
//            double[] output = network.getOutput();
//            
//            for(int i = 0; i < output.length; i++){
//                results[i][iterator] = output[i];
//            }
//            
//            iterator++;
//        }
//        System.out.println("Displaying computed data...");
//        String[] key = new String[]{"wave_dir_x", "wave_dir_y", "wave_hm", "wave_Tm"};        
//        for(int i = 0; i < results.length; i++){
//            FunctionGraph graph = new FunctionGraph(key[i], "Date", "Value");
//            graph.addOrUpdateSeries(timeLine, results[i], key[i]);
//            graph.setSeriesLinesAndShapesVisible(key[i], true, false);
//            ValueAxis date = new DateAxis(" ");
//            graph.getChart().getXYPlot().setDomainAxis(date);
//            JFrame f = new JFrame(key[i]);
//            f.setSize(800,600);
//            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            f.add(graph);
//            f.setVisible(true); 
            
//        }
        
        
        

        
        
        
        
   }
    
    
    
    
    /**
     * Opens dialog to choose file
     * @return file
     */
    private static File chooseFile(){
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
        return file;
    }
    
    /**
     * Sets up neural network
     * @param inNeurons
     * @param outNeurons
     * @param hiddenNeurons
     * @param numLayers
     * @return 
     */
    private static Network setUpNetwork(int inNeurons, int outNeurons, int hiddenNeurons, int numLayers){
            System.out.println("Initializing Network...");
            
        FullyConnectedFeedForward network = new FullyConnectedFeedForward(inNeurons, outNeurons, hiddenNeurons, numLayers);
            System.out.println("\t" + "Networktype: " + network.toString());
            System.out.println("\t" + "Number of layers: " + numLayers);
            System.out.println("\t" + "Number of inputneurons: " + inNeurons);
            System.out.println("\t" + "Number of outputneurons: " + outNeurons);
            System.out.println("\t" + "Number of hiddenneurons per layer: " + hiddenNeurons);
            System.out.print("\t" + "Assembling Network...");
        network.assembleNet();
            System.out.println("DONE!");
        network.setAllActivityFcts(new HyperbolicTangentFct());
            System.out.println("\t" + "Setting Activityfunction to: " + network.getOutputNeurons().getFirst().getActivityFunction().toString() );
            System.out.println("\t" + "Setting up network: DONE!");
            System.out.println("");
        
        return network;
    }
    
    /**
     * sets up learningalgorithm 
     * @param network
     * @param learningRate
     * @param learningType
     * @param maxIter
     * @param maxError
     * @return 
     */
    private static Backpropagation setLearningAlgorithm(FeedForwardNet network, double learningRate, boolean learningType, int maxIter, double maxError){
            System.out.println("Setting up learning-algorithm...");       
        Backpropagation learningRule = (Backpropagation) network.learningRule;
            System.out.println("\t" + "Algorithm: " + learningRule.toString());
            System.out.print("\t" + "Learningtype: ");
        network.setLearningType(learningType);
            if(network.isLearningOnline()){
                System.out.println("ONLINE!");
            }else{
                System.out.println("BATCH/OFFLINE!");
            }    
            System.out.print("\t" + "Setting learningrate to: ");
        learningRule.setLearingRate(learningRate);
            System.out.println(learningRate);
            System.out.print("\t" + "Setting break-criterion: ");
        learningRule.setBreakCriterion(new BreakErrorAndIteration(learningRule));
            System.out.println(learningRule.getBreakCriterion().toString());
        learningRule.getBreakCriterion().setMaxError(maxError);
        learningRule.getBreakCriterion().setMaxIterations(maxIter);
            System.out.println("\t" + "Maximum number of iterations: " + maxIter);
            System.out.println("\t" + "Maximum error tolerated: " + maxError);
            System.out.println("\t" + "Setting up algorithm: DONE!");
            System.out.println("");
                
        return learningRule;
    }
    
    /**
     * Plots Errordata
     * @param learningRule 
     */
    private static void plotErrorData(LearningRule learningRule){
        
        int numIter = learningRule.getIterations() + 1;
        double[] trainingError = ToolClass.listToArray(learningRule.getGlobalErrorTrain());
        double[] testError = ToolClass.listToArray(learningRule.getGlobalErrorTest());    
        double[] iterations = ToolClass.makeRangeArray(0, (numIter-1), 1);

        //plot with functiongraph
        FunctionPlotter graph = new FunctionPlotter("Global Error", "Iterations", "Error");
        graph.addFunction(iterations, trainingError, "Trainingerror");
        graph.addFunction(iterations, testError, "Testerror");
        graph.plot(false);
    }
    
    /**
     * Plots given trainingdata
     * @param map 
     */
    private static void plotGivenData(HashMap map){
        
        //Get set of keys from hashmap and convert into array
        int counter = 0;
        double[] xValues = new double[map.keySet().size()];
        double[][] yValues = new double[7][xValues.length];
        
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            HashMap.Entry mapEntry = (HashMap.Entry) iterator.next();           
            
            //Extract y-values
            TrainingPattern pattern = (TrainingPattern) mapEntry.getValue();
            for(int i = 0; i < pattern.getIn().length; i++){
                yValues[i][counter] = pattern.getIn()[i];
            }
            for(int i = 0; i < pattern.getOut().length; i++){
                yValues[i+3][counter] = pattern.getOut()[i];
            }
            
            //Extract x-values
            xValues[counter] = (double) mapEntry.getKey();
            counter++;
        }
        
        //plot with functiongraph
        WaveApproxTest.plot(xValues, "Date", yValues, new String[]{"wind_v", "wind_dir_x", "wind_dir_y", 
           "wave_dir_x", "wave_dir_y", "wave_hm", "wave_Tm"}, "", "Given Data..", false );
        
    }
    
    
    private static void plotResults(HashMap map, Network network){
        
        //Extract data from hashmap and calculate results with network
        int counter = 0;
        double[] time = new double[map.keySet().size()];
        double[][] calcResults = new double[4][time.length];
        double[][] givenResults = new double[4][time.length];
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            HashMap.Entry mapEntry = (HashMap.Entry) iterator.next();           
                      
            TrainingPattern pattern = (TrainingPattern) mapEntry.getValue();
            double[] input = pattern.getIn();
            network.solve(input);
            double[] output = network.getOutput();
            for(int i = 0; i< output.length; i++){
                calcResults[i][counter] =  output[i];
                givenResults[i][counter] = pattern.getOut()[i];
            }
            
            time[counter] = (double) mapEntry.getKey();
            counter++;
        }
        //Sort result into arrays and plot
        String[] waveKeys = new String[]{"wave_dir_x", "wave_dir_y", "wave_hm", "wave_Tm"};
        String[] resultStrings = new String[]{"Computed results", "Expected results"};
        
        for(int i = 0; i < calcResults.length; i++){
            double[][] results = new double[2][calcResults[i].length];
            for(int j = 0; j < calcResults[i].length; j++){
                results[0][j] = calcResults[i][j];
                results[1][j] = givenResults[i][j];
            }
            WaveApproxTest.plot(time, "Date", results, resultStrings, "Value", waveKeys[i], false);
            //Make scatterplot
            WaveApproxTest.plot(calcResults[i], "Computed results", results, new String[]{}, "Expected Results", waveKeys[i],true);
        }
                       
        
    }
    
    /**
     * 
     * @param xValues
     * @param yValues1
     * @param name1
     * @param yValues2
     * @param name2
     * @param title 
     */
    private static void plot(double[] xValues, String xLabel, double[][] yValues, String[] names, String yLabel, String title, boolean scatter){
            
        FunctionGraph graph = new FunctionGraph(title, xLabel, yLabel);
        if(!scatter){
            
            for(int i = 0; i < yValues.length; i++){
                graph.addOrUpdateSeries(xValues, yValues[i], names[i]);
                graph.setSeriesLinesAndShapesVisible(names[i], true, false);
            }

            if(xLabel.equals("Date")){
                ValueAxis date = new DateAxis(" ");
                graph.getChart().getXYPlot().setDomainAxis(date);
            }
            
        }else{
            IdentityFct function = new IdentityFct();
            double min = ToolClass.getMin(xValues);
            double max = ToolClass.getMax(xValues);
            graph.addOrUpdateSeries(function, min, max, 1, "Matching results");
            graph.addOrUpdateSeries(xValues, yValues[1], title);
            graph.setSeriesLinesAndShapesVisible(title, false, true);
            graph.setSeriesLinesAndShapesVisible("Matching results", true, false);
        }
        JFrame f = new JFrame(title);
        f.setSize(800,600);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.add(graph);
        f.setVisible(true);
    }
    
    
        
    /**
     * Displays welcome message
     */
    private static void dispWelcome(){
        System.out.println("#----------------------------------------------#");
        System.out.println("#              Welcome to ISPIDER              #");
        System.out.println("#         A little neural network tool.        #");
        System.out.println("#----------------------------------------------#");
        System.out.println("");
    }
}
