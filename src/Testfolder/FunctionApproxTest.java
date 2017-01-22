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

import data.ErrorData;
import tools.Function.*;
import tools.*;

/**
 * Testclass for backpropagation-algorithm
 * Tests, if network is able to learn to approximate a sinus function.
 * @author LammLukas
 */
public class FunctionApproxTest {
    
    public static void main(String[] args) {
        
       //Set parameters for test
       int inNeurons = 1;
       int outNeurons = 1;
       int hiddenNeurons = 4;
       int numLayers = 3;
       double learningRate = 0.2;
       int maxIter = 10000;
       double maxError = 0.01;
   
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
       double[] range = new double[2];
       range[0] = -10;
       range[1] = 10;
       //TrainingSet trainingSet = SinusSet.createSet(100, range);
       TrainingSet trainingSet = QuadraticFctSet.createSet(100, range);
        System.out.println("DONE!");
       
       
       //Train network      
        System.out.println("Learningalgorithm started...");
       
       Backpropagation learningRule = (Backpropagation) network.learningRule;
       
        System.out.println("Setting learningrate to: " + learningRate);
       learningRule.setLearingRate(learningRate);
       
       //setting for learningtype
       network.setLearningType(true);
       if(network.isLearningOnline()){
           System.out.println("Learningtype: ONLINE!");
           System.out.println("...");
       }else{
           System.out.println("Learningtype: BATCH/OFFLINE!");
           System.out.println("...");
       }
       learningRule.setMaxError(maxError);
       learningRule.setMaxIter(maxIter);
       
       //read errordata
       ErrorData errorData = learningRule.applyRule(network, trainingSet);
       int numIter = errorData.getNumIter();
       double[] errorTrain = ToolClass.listToArray(errorData.getGlobalErrorTrain());
       double[] errorTest = ToolClass.listToArray(errorData.getGlobalErrorTest());
             
       
       //check if training was successfull
       if(errorData.isTrainingSuccess()){
           System.out.println("Training successfull!");
       }else{
           System.out.println("Training NOT successfull!");
           System.out.println("Error is still too big: " + errorTest[numIter-1]);
       }
       
       //visualize results
        System.out.println("");
        System.out.println("Results are being displayed...");
        
        FunctionGraph graph = new FunctionGraph("Global error", "Iteration", "Error");
        double[] iterVec = ToolClass.makeRangeArray(0, (numIter+1), 1);
        graph.addOrUpdateSeries(iterVec, errorTrain, "Training error");
        graph.addOrUpdateSeries(iterVec, errorTest, "Test error");
        graph.setSeriesLinesAndShapesVisible("Training error", true, false);
        graph.setSeriesLinesAndShapesVisible("Test error", true, false);
        graph.plot(800,800);
        
        
        System.out.println("");
        System.out.println("END OF PROGRAM!");
        
        
       
        
    }
    
}
