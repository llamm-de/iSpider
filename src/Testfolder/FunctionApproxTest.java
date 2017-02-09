/*
 * The MIT License
 *
 * Copyright 2017 LammLukas.
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

import network.Network.*;
import network.Training.*;

import java.awt.Color;
import tools.Function.*;
import tools.*;

import java.util.LinkedList;

/**
 * Testclass for backpropagation-algorithm
 * Tests, if network is able to learn to approximate a scalar-function.
 * @author LammLukas
 */
public class FunctionApproxTest {
    
    public static void main(String[] args) {
        
       //Set parameters for test
       int whichFct = 2;    //1:sinus; 2:x²
       int inNeurons = 1;
       int outNeurons = 1;
       int hiddenNeurons = 6;
       int numLayers = 3;
       double learningRate = 0.01;
       int maxIter = 10000;
       double maxError = 0.035;
       boolean learningType = false; //true: Online; false: Offline
       double[] range = new double[2];
       range[0] = -2;
       range[1] = 2;
   
       //Initialize Network
            System.out.print("Initializing Network...");
            System.out.println("DONE!");
       FullyConnectedFeedForward network = new FullyConnectedFeedForward(inNeurons, outNeurons, hiddenNeurons, numLayers);
            System.out.print("Assembling Network...");
       network.assembleNet();
            System.out.println("DONE!");
            System.out.print("Setting Activityfunction...");
       network.setAllActivityFcts(new HyperbolicTangentFct());
            System.out.println("DONE!");
            
       
        
        
       //Get TrainingSet
        System.out.print("Creating Training- and Testpattern...");
       
        String title = new String();
        TrainingSet trainingSet = new TrainingSet();
        if(whichFct == 1){
            trainingSet = SinusSet.createSet(1000, range);
            title = "Sin(x)";
        }else{
            trainingSet = QuadraticFctSet.createSet(100, range);
            title = "x²";
        }
       
        System.out.println("DONE!");
        
        // Initialize Data for visualisation
        LinkedList<TrainingPattern> testPatterns = trainingSet.getTestPatterns();
        double[] input = new double[testPatterns.size()];
        double[] desiredOutput = new double[testPatterns.size()];
        int iterator = 0;
        for (TrainingPattern testPattern : testPatterns) {
            input[iterator] = testPattern.p[0];
            desiredOutput[iterator] = testPattern.t[0];
            iterator++;
        }
        //get data from untrained network
        double[] untrainedOutput = new double[testPatterns.size()];
        for (int i = 0; i < testPatterns.size(); i++) {
            double[] in = new double[1];
            in[0] = input[i];
            network.solve(in);
            untrainedOutput[i] = network.outputData[0];
        }
       
       
       //Train network      
            System.out.println("Learningalgorithm started...");
       
       Backpropagation learningRule = (Backpropagation) network.learningRule;
       
            System.out.println("Setting learningrate to: " + learningRate);
       learningRule.setLearingRate(learningRate);
       
       
       //setting for learningtype
       network.setLearningType(learningType);
       if(network.isLearningOnline()){
           System.out.println("Learningtype: ONLINE!");
           System.out.println("...");
       }else{
           System.out.println("Learningtype: BATCH/OFFLINE!");
           System.out.println("...");
       }
       //setting breakcriterion
       learningRule.setBreakCriterion(new BreakErrorAndIteration(learningRule));
       learningRule.getBreakCriterion().setMaxError(maxError);
       learningRule.getBreakCriterion().setMaxIterations(maxIter);
       
       //read errordata
       network.trainNet(trainingSet);
       int numIter = learningRule.getIterations() + 1;
       double[] errorTrain = ToolClass.listToArray(learningRule.getGlobalErrorTrain());
       double[] errorTest = ToolClass.listToArray(learningRule.getGlobalErrorTest());
             
       
       //check if training was successfull
       if(learningRule.isTrainingSuccess()){
           System.out.println("Training successfull!");
       }else{
           System.out.println("Training NOT successfull!");
           System.out.println("Error is still too big: " + errorTest[numIter-1]);
       }
       
       //visualize error
        System.out.println("");
        System.out.println("Results are being displayed...");
        
        FunctionGraph graph = new FunctionGraph("Global error", "Iteration", "Error");
        double[] iterVec = ToolClass.makeRangeArray(0, (numIter), 1);
        graph.addOrUpdateSeries(iterVec, errorTrain, "Training error");
        graph.addOrUpdateSeries(iterVec, errorTest, "Test error");
        graph.setSeriesLinesAndShapesVisible("Training error", true, false);
        graph.setSeriesLinesAndShapesVisible("Test error", true, false);
        graph.plot(800,800);
        
        //visualize result
        double[] trainedOutput = new double[testPatterns.size()];
        for (int i = 0; i < testPatterns.size(); i++) {
            double[] in = new double[1];
            in[0] = input[i];
            network.solve(in);
            trainedOutput[i] = network.outputData[0];
        }
        FunctionGraph graph2 = new FunctionGraph(title, "x", "y");
        graph2.addOrUpdateSeries(input, untrainedOutput, "Network (untrained)");
        graph2.addOrUpdateSeries(input, trainedOutput, "Network (trained)");
        graph2.addOrUpdateSeries(input, desiredOutput, "Analytic");
        graph2.setSeriesLinesAndShapesVisible("Network (untrained)", true, false);
        graph2.setSeriesLinesAndShapesVisible("Network (trained)", true, false);
        graph2.setSeriesLinesAndShapesVisible("Analytic", true, false);
        graph2.setSeriesColor("Network (untrained)", Color.MAGENTA);
        graph2.setSeriesColor("Network (trained)", Color.BLUE);
        graph2.setSeriesColor("Analytic", Color.RED);
        graph2.plot(800,800);
        
        
        System.out.println("");
        System.out.println("END OF PROGRAM!");
 
       
        
    }
    
}
