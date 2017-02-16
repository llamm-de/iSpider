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
import data.IO.JboReader;
import data.IO.WindWaveReader;
import helper.NdTimeSeries;
import helper.NdTimeSeries.GetMode;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import network.Network.FeedForwardNet;
import network.Network.FullyConnectedFeedForward;
import network.Network.Network;
import network.Training.Backpropagation;
import network.Training.BreakErrorAndIteration;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;

import tools.Function.HyperbolicTangentFct;
import tools.FunctionGraph;
import tools.ToolClass;

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
        int hiddenNeurons = 15;
        int numLayers = 3;
        double learningRate = 0.01;
        int maxIter = 10000;
        double maxError = 0.001;
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
        String[] keys = new String[]{"wind_v", "wind_dir_x", "wind_dir_y", 
           "wave_dir_x", "wave_dir_y", "wave_hm", "wave_Tm"};
        long[] timeRange = new long[]{(long) 1.1018592E12, (long) 1.104555E12};
        DataSet set = reader.createTrainingSet(timeSeries, timeRange);
        
        //Plot original data from timeseries
            System.out.println("Given input-data are being displayed...");
        WaveApproxTest.plotTimeSeries(timeSeries, keys);
            System.out.println("");
        
        
        //Start training
            System.out.println("Training started...");
            System.out.println("");
        network.trainNet(set);
        
        //read errordata
        int numIter = learningRule.getIterations() + 1;
        double[] errorTrain = ToolClass.listToArray(learningRule.getGlobalErrorTrain());
        double[] errorTest = ToolClass.listToArray(learningRule.getGlobalErrorTest());
        
        //check if training was successfull
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
        
        FunctionGraph graph = new FunctionGraph("Global error", "Iteration", "Error");
        double[] iterVec = ToolClass.makeRangeArray(0, (numIter-1), 1);
        graph.addOrUpdateSeries(iterVec, errorTrain, "Training error");
        graph.addOrUpdateSeries(iterVec, errorTest, "Test error");
        graph.setSeriesLinesAndShapesVisible("Training error", true, false);
        graph.setSeriesLinesAndShapesVisible("Test error", true, false);
        graph.plot(800,800);
        
        
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
     * Plots data from a given time series
     * @param timeSeries 
     * @param keys
     */
    private static void plotTimeSeries(NdTimeSeries timeSeries, String[] keys){
        FunctionGraph graph = new FunctionGraph("Given data...", "date", "");
        double[][] data = timeSeries.getTimeSeries(keys, new NdTimeSeries.DataItem((long) 1.1018592E12),true, new NdTimeSeries.DataItem((long) 1.104555E12),true,NdTimeSeries.GetMode.AND);
        int iterator = 1;
        for (String k : keys) {
            graph.addOrUpdateSeries(data[0], data[iterator], k);
            graph.setSeriesLinesAndShapesVisible(k, true, false);
            iterator++;
        }
        ValueAxis va = new DateAxis(" ");
        graph.getChart().getXYPlot().setDomainAxis(va);
        JFrame f = new JFrame("Gegebene Werte");
        f.setSize(800,600);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.add(graph);
        f.setVisible(true);
    }
    
    /**
     * Displays welcome message
     */
    private static void dispWelcome(){
        System.out.println("#--------------------------------------#");
        System.out.println("#          Welcome to ISPIDER          #");
        System.out.println("#     A little neural network tool.    #");
        System.out.println("#------------------------------------- #");
        System.out.println("");
    }
}
