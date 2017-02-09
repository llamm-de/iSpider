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

import network.Network.*;
import tools.Function.ErrorFunction;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Interface for Learningrule
 * @author LammLukas
 */
public abstract class LearningRule {
    
    /**
     * Learningrate
     */
    protected double learningRate;
    
    /**
     * Number of iterations made by algorithm
     */
    protected int iterations;
    
    /**
     * Global error computed from trainingset made at each iteration
     */
    protected ArrayList<Double> globalErrorTrain;
    
    /**
     * Global error computed from testset made after each iteration
     */
    protected ArrayList<Double> globalErrorTest;
    
    /**
     * Errorfunction
     */
    protected ErrorFunction errorFunction;
    
    /**
     * Criterion for breaking iteration-loop
     */
    protected BreakCriterion breakCriterion;
    
    /**
     * Shows, whther or not training was successfull
     */
    protected boolean TrainingSuccess;

    
    /**
     * Applies Learningrule to network
     * @param net
     * @param set
     */
    public abstract void applyRule(Network net, TrainingSet set);
    
    /**
     * Updates synaptic weights of network
     * @param net
     * @param incrementMap 
     */
    protected abstract void updateWeights(Network net, HashMap incrementMap);

    
    //Getter
    
    public double getLearningRate() {
        return learningRate;
    }

    public int getIterations() {
        return iterations;
    }

    public ArrayList<Double> getGlobalErrorTrain() {
        return globalErrorTrain;
    }

    public ArrayList<Double> getGlobalErrorTest() {
        return globalErrorTest;
    }

    public ErrorFunction getErrorFunction() {
        return errorFunction;
    }

    public BreakCriterion getBreakCriterion() {
        return breakCriterion;
    }

    public boolean isTrainingSuccess() {
        return TrainingSuccess;
    }
        
    //Setter

    public void setLearingRate(double learningRate) {
        if (learningRate <= 1 && learningRate > 0){
            this.learningRate = learningRate;
        }else{
            this.learningRate = 1;
        }
    }

    public void setErrorFunction(ErrorFunction errorFunction) {
        this.errorFunction = errorFunction;
    }

    public void setBreakCriterion(BreakCriterion breakCriterion) {
        this.breakCriterion = breakCriterion;
    }
    
    
}
