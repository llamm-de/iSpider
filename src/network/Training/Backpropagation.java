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
package network.Training;

import network.Network.Network;
import network.Connections.*;
import network.Neurons.*;
import network.Layer.*;

import tools.Function.*;

import java.util.HashMap;

/**
 * Class for Backpropagation of error.
 * Implementation of interface LearningRule.
 * @author LammLukas
 */
public class Backpropagation implements LearningRule{
    
    /**
     * Learningrate
     */
    private double learningRate;
    
    /**
     * Maximum error allowed
     */
    private double maxError;
    
    /**
     * Maximum number of iterations
     */
    private double maxIter;
    
    /**
     * Errorfunction
     */
    private ErrorFunction errorFunction;

    /**
     * Empty Constructor
     * Learningrate predefined as 1.
     * Errorfunction predefined as MeanSquaredError.
     */
    public Backpropagation() {
        this.learningRate = 1;
        this.errorFunction = new MeanSquaredError();
    }

    /**
     * Constructor with variable learningrate
     * @param learningRate
     * @param errorFunction
     */
    public Backpropagation(double learningRate, ErrorFunction errorFunction) {
        if (learningRate <= 1 && learningRate > 0){
            this.learningRate = learningRate;
        }else{
            this.learningRate = 1;
        }
        this.errorFunction = errorFunction;
    }
      
    @Override
    public void applyRule(Network network, TrainingSet set) {
        //Initialize iterator, globalError and HashMaps for weightIncrement and local gradient delta
        int iterator = 0;
        double globalError = Double.MAX_VALUE;
        HashMap<Synapse,Double> weightIncrement = new HashMap<>();
        HashMap<Neuron, Double> delta = new HashMap<>();
        
        //Loop until error is small enough or maxIter is reached
        while(globalError > maxError || iterator < maxIter){
            //Loop over all patterns
            int patternCount = 1;
            for (TrainingPattern pattern : set.getPatterns()) {
                //Solve for pattern and compute error
                double[] input = pattern.p;
                network.solve(input);
                globalError = errorFunction.compGlobalError(network.getOutput(), pattern.t);
                
                //BACKPROPAGATION of ERROR
                
                //Shuffle pattern after every pattern shown once
                patternCount += 1;
                if(patternCount >= set.getPatterns().size()){
                    set.shufflePatterns();
                    patternCount = 1;
                }
                
            }
            
            
            
        }
        
    }
    
    
    //Getter and setter

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearingRate(double learningRate) {
        if (learningRate <= 1 && learningRate > 0){
            this.learningRate = learningRate;
        }else{
            this.learningRate = 1;
        }
    }

    public void setMaxError(double maxError) {
        this.maxError = maxError;
    }

    public void setMaxIter(double maxIter) {
        this.maxIter = maxIter;
    }
    
    
    
}
