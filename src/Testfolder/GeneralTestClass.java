/*
 * The MIT License
 *
 * Copyright 2016 LammLukas.
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
import data.IO.*;
import java.io.File;
import javax.swing.JFileChooser;
import network.Network.FullyConnectedFeedForward;
import network.Training.Backpropagation;
import network.Training.BreakErrorAndIteration;
import tools.Function.HyperbolicTangentFct;
import tools.FunctionGraph;
import tools.ToolClass;

/**
 *
 * @author LammLukas
 */
public class GeneralTestClass {
    
    public static void main(String[] args) {
        
        //Initialize parameters for network
        int inNeurons = 3;
        int outNeurons = 4;
        int hiddenNeurons = 4;
        int numLayers = 3;
        double learningRate = 0.01;
        int maxIter = 10000;
        double maxError = 0.001;
        boolean learningType = false; //true: Online; false: Offline
        
        

        //create new filechooser-object and set parameters
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file to open...");
        //Open dialog and choose file
        int response = fileChooser.showOpenDialog(null);
        File file = null;
        if(response == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
        }else{
            System.out.println("No File Chosen!");
        }
        
       
        //Read file and extract set
       JboReader reader = new JboReader();
       String[] keys = new String[]{"wind_v", "wind_dir_x", "wind_dir_y", 
           "wave_dir_x", "wave_dir_y", "wave_hm", "wave_Tm"};
       DataSet set = reader.readFile(file, "training", keys);
       
       
       //Create neural network
            System.out.println("Initializing Network...");
            System.out.println("DONE!");
       FullyConnectedFeedForward network = new FullyConnectedFeedForward(inNeurons, outNeurons, hiddenNeurons, numLayers);
            System.out.println("Assembling Network...");
       network.assembleNet();
            System.out.println("DONE!");
            System.out.println("Setting Activityfunction...");
       network.setAllActivityFcts(new HyperbolicTangentFct());
            System.out.println("DONE!");
       
       //Setting up learning-algorithm
            System.out.println("Initializing learningrule...");       
        Backpropagation learningRule = (Backpropagation) network.learningRule;
            System.out.println("DONE!");
            System.out.println("Setting learningrate to: " + learningRate);
        learningRule.setLearingRate(learningRate);
            System.out.println("DONE!");
            System.out.println("Setting learningtype...");
        network.setLearningType(learningType);
        if(network.isLearningOnline()){
            System.out.println("Learningtype: ONLINE!");
        }else{
            System.out.println("Learningtype: BATCH/OFFLINE!");
        }
            System.out.println("Setting break-criterion...");
        learningRule.setBreakCriterion(new BreakErrorAndIteration(learningRule));
        learningRule.getBreakCriterion().setMaxError(maxError);
        learningRule.getBreakCriterion().setMaxIterations(maxIter);
            System.out.println("DONE!");
        
        //Start training
            System.out.println("");
            System.out.println("Training started...");
            System.out.println("...");
        network.trainNet(set);
        //read errordata
        int numIter = learningRule.getIterations() + 1;
        double[] errorTrain = ToolClass.listToArray(learningRule.getGlobalErrorTrain());
        double[] errorTest = ToolClass.listToArray(learningRule.getGlobalErrorTest());
        
        //check if training was successfull
        if(learningRule.isTrainingSuccess()){
            System.out.println("Training successfull!");
        }else{
            System.out.println("Training NOT successfull!");
            System.out.println("Error is still too big: " + errorTest[(numIter-2)]);
        }
       
       //visualize error
            System.out.println("");
            System.out.println("Results are being displayed...");
        FunctionGraph graph = new FunctionGraph("Global error", "Iteration", "Error");
        double[] iterVec = ToolClass.makeRangeArray(0, (numIter-1), 1);
        graph.addOrUpdateSeries(iterVec, errorTrain, "Training error");
        graph.addOrUpdateSeries(iterVec, errorTest, "Test error");
        graph.setSeriesLinesAndShapesVisible("Training error", true, false);
        graph.setSeriesLinesAndShapesVisible("Test error", true, false);
        graph.plot(800,800);
        
        
            
    }
    
}
