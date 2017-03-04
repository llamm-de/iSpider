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
import data.TrainingPattern;
import helper.NdTimeSeries;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import network.Network.FullyConnectedFeedForward;
import network.Training.Backpropagation;
import network.Training.BreakErrorAndIteration;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;

import tools.Function.HyperbolicTangentFct;
import tools.FunctionGraph;
import tools.FunctionPlotter;
import tools.ToolClass;

/**
 *
 * @author lukas
 */
public class ParameterStudy {
    
    
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        
        //Get training-and testdata
        File file = ParameterStudy.chooseFile();        
        WindWaveReader reader = new WindWaveReader();
        NdTimeSeries timeSeries = reader.readFile(file);
        //Create trainingset from timeseries
        long[] timeRange = new long[]{(long) 1.1124144E12, (long) 1.1131308E12};
        HashMap dataMap = reader.getDataWithTime(timeSeries, "training", timeRange);
        //HashMap dataMap = reader.getDataWithTime(timeSeries, "training");
        DataSet set = reader.createTrainingSet(dataMap);
        //plot selected data
        //Get set of keys from hashmap and convert into array
        int counter = 0;
        double[] xValues = new double[dataMap.keySet().size()];
        double[][] yValues = new double[7][xValues.length];
        
        Iterator iterator = dataMap.entrySet().iterator();
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
        
        //plot with functionplotter
         FunctionGraph graphData = new FunctionGraph("Trainingdata", "Date", "Value");
         String[] key = new String[]{"wind_v", "wind_dir_x", "wind_dir_y", 
           "wave_dir_x", "wave_dir_y", "wave_hm", "wave_Tm"};
        for(int i = 0; i < yValues.length; i++){
            graphData.addOrUpdateSeries(xValues, yValues[i], key[i]);
            graphData.setSeriesLinesAndShapesVisible(key[i], true, false);
        }
            ValueAxis date = new DateAxis(" ");
            graphData.getChart().getXYPlot().setDomainAxis(date);
            JFrame inData = new JFrame("Given data");            
            inData.setSize(800,600);
            inData.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            inData.add(graphData);
            inData.setVisible(true);
                    
        
        //Set Parameters for network
        
        int inNeurons = 3;
        int outNeurons = 4;
        int numLayers = 5;
        double learningRate = 0.01;
        int maxIter = 10000;
        double maxError = 0.01;
        int maxHiddenNeurons = 15;
        int maxLayers = 10;
        boolean learningType = false; //true: Online; false: Offline
        
        /**
         * Check for impact of numHiddenNeurons with one hidden layer
         * 
         * 
         */
        
        System.out.println("");
        System.out.println("Parameterstudy for number of neurons in one layer started.");
        
        double[][] trainingError = new double[maxHiddenNeurons][maxIter];
        double[][] testError = new double[maxHiddenNeurons][maxIter];
        ArrayList<Boolean> success = new ArrayList<>();
        ArrayList<Double> minError = new ArrayList<>();
        
        for(int hiddenNeurons = 4; hiddenNeurons <= maxHiddenNeurons; hiddenNeurons++){
            
            //Create network and set learningalgorithm
            FullyConnectedFeedForward network = new FullyConnectedFeedForward(inNeurons, outNeurons, hiddenNeurons, numLayers);
            network.assembleNet();
            network.setAllActivityFcts(new HyperbolicTangentFct());     
            Backpropagation learningRule = (Backpropagation) network.learningRule;
            network.setLearningType(learningType);
            learningRule.setLearingRate(learningRate);
            learningRule.setBreakCriterion(new BreakErrorAndIteration(learningRule));
            learningRule.getBreakCriterion().setMaxError(maxError);
            learningRule.getBreakCriterion().setMaxIterations(maxIter);
            

            network.trainNet(set);
            
            success.add(learningRule.isTrainingSuccess());
            
            ArrayList<Double> errorListTrain = learningRule.getGlobalErrorTrain();
            ArrayList<Double> errorListTest = learningRule.getGlobalErrorTest();
            minError.add(errorListTrain.get(errorListTrain.indexOf(Collections.min(errorListTrain))));
            for(int i = 0; i < maxIter; i++){
                if(i < errorListTrain.size()){
                    trainingError[hiddenNeurons-4][i] = errorListTrain.get(i);
                    testError[hiddenNeurons-4][i] = errorListTest.get(i);
                }else{
                    trainingError[hiddenNeurons-4][i] = errorListTrain.get(errorListTrain.size()-1);
                    testError[hiddenNeurons-4][i] = errorListTest.get(errorListTest.size()-1);
                }   
            } 
            
            System.out.println("\t Learning completed for " + hiddenNeurons + " hidden neurons.");
            
        }
        //Visualize Data
        System.out.println("Visualization started...");
        FunctionPlotter graphTrain = new FunctionPlotter("TrainingError", "Iterations", "Trainingerror");
        FunctionPlotter graphTest = new FunctionPlotter("TestError", "Iterations", "Testerror");
        double[] iterations = ToolClass.makeRangeArray(0, maxIter, 1);
        for(int j = 0; j < maxHiddenNeurons-4; j++){
            graphTrain.addFunction(iterations, trainingError[j], "Neurons = " + (j+5));
            graphTest.addFunction(iterations, testError[j], "Neurons = " + (j+5));                
        }
        graphTrain.plot(false);
        graphTest.plot(false);
                
        System.out.println("\t Training successfull: " + success);
        System.out.println("\t Minimum error made: " + minError);
        
//        /**
//         * Study for layers
//         */
//        System.out.println("");
//        System.out.println("Layer-study started...");
//        int hiddenNeurons = 5;
//        success.removeAll(success);
//        minError.removeAll(minError);
//        double[][] trainingErrorLayer = new double[maxLayers][maxIter];
//        double[][] testErrorLayer = new double[maxLayers][maxIter];
//        for(int layers = 2; layers <= maxLayers; layers++){
//            //Create network and set learningalgorithm
//            FullyConnectedFeedForward network = new FullyConnectedFeedForward(inNeurons, outNeurons, hiddenNeurons, layers);
//            network.assembleNet();
//            network.setAllActivityFcts(new HyperbolicTangentFct());     
//            Backpropagation learningRule = (Backpropagation) network.learningRule;
//            network.setLearningType(learningType);
//            learningRule.setLearingRate(learningRate);
//            learningRule.setBreakCriterion(new BreakErrorAndIteration(learningRule));
//            learningRule.getBreakCriterion().setMaxError(maxError);
//            learningRule.getBreakCriterion().setMaxIterations(maxIter);
//            
//
//            network.trainNet(set);
//            
//            success.add(learningRule.isTrainingSuccess());
//            
//            ArrayList<Double> errorListTrain = learningRule.getGlobalErrorTrain();
//            ArrayList<Double> errorListTest = learningRule.getGlobalErrorTest();
//            minError.add(errorListTrain.get(errorListTrain.indexOf(Collections.min(errorListTrain))));
//            for(int i = 0; i < maxIter; i++){
//                if(i < errorListTrain.size()){
//                    trainingErrorLayer[layers-2][i] = errorListTrain.get(i);
//                    testErrorLayer[layers-2][i] = errorListTest.get(i);
//                }else{
//                    trainingErrorLayer[layers-2][i] = errorListTrain.get(errorListTrain.size()-1);
//                    testErrorLayer[layers-2][i] = errorListTest.get(errorListTest.size()-1);
//                }   
//            } 
//            
//            System.out.println("\t Learning completed for " + layers + " hidden layers.");
//            
//        }
//        //Visualize Data
//        System.out.println("Visualization started...");
//        FunctionPlotter graphTrainLayer = new FunctionPlotter("Trainingerror", "Iterations", "Trainingerror");
//        FunctionPlotter graphTestLayer = new FunctionPlotter("Testerror", "Iterations", "Testerror");
//        
//        for(int j = 0; j < maxLayers-2; j++){
//            graphTrainLayer.addFunction(iterations, trainingErrorLayer[j], "Layers = " + (j+2));
////            graphTrainLayer.setSeriesLinesAndShapesVisible("Layers = " + (j+2), true, false);
//            graphTestLayer.addFunction(iterations, testErrorLayer[j], "Layers = " + (j+2));    
////            graphTestLayer.setSeriesLinesAndShapesVisible("Layers = " + (j+2), true, false);
//            
//        }
//        graphTrainLayer.plot(false);
//        graphTestLayer.plot(false);
//        
////        JFrame trainLayer = new JFrame("Trainingerror");
////        JFrame testLayer = new JFrame("Testerror");
////        trainLayer.setSize(800,600);
////        trainLayer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
////        trainLayer.add(graphTrainLayer);
////        trainLayer.setVisible(true);
////        testLayer.setSize(800,600);
////        testLayer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
////        testLayer.add(graphTestLayer);
////        testLayer.setVisible(true);
//        
//        System.out.println("\t Training successfull: " + success);
//        System.out.println("\t Minimum error made: " + minError);
        
        
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
}
