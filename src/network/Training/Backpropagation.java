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
import network.Connections.*;
import network.Neurons.*;
import network.Layer.*;
import data.ErrorData;

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
    public ErrorData applyRule(Network net, TrainingSet set) {
        //Initialize iterator, globalError and HashMaps for weightIncrement and local gradient delta
        FeedForwardNet network = (FeedForwardNet) net;
        int iterator = 0;
        ErrorData errorData = new ErrorData(); //Object for storing errordata
        HashMap<Synapse,Double> incrementMap = new HashMap<>();
        HashMap<Neuron, Double> deltaMap = new HashMap<>();
        
        //Loop until error is small enough or maxIter is reached
        outerloop:
        while(iterator < maxIter){
            // set/reset error-variables
            double globalErrorTrain = 0;
            double globalErrorTest = 0;
            //Loop over all patterns
            int patternCount = 0;
            for (TrainingPattern pattern : set.getTrainingPatterns()) {
                //Count patterns 
                patternCount++;
                //Solve for pattern and compute error
                double[] input = pattern.p;
                network.solve(input);        
                                               
                //Compute deltas and new weights (Loop backwards over all layers except inputlayer)
                for (int i = (network.getLayers().size()-1); i > 0  ; i--) {
                    Layer currentLayer = network.getLayers().get(i);
                    //Loop over all neurons in layer
                    int j = 0;
                    for (Neuron neuron : currentLayer.getNeurons()) {
                        //Compute derivation of activityfunction
                        DifferentiableFct activityFct = (DifferentiableFct) neuron.getActivityFunction();
                        double activityDeriv = activityFct.getDerivative(neuron.output);
                        //Distinguish if output or hidden layer and compute delta for neuron
                        if(i == (network.getLayers().size()-1)){    //output
                            double delta = activityDeriv*errorFunction.compDerivative(network.outputData[j], pattern.t[j]);
                            deltaMap.put(neuron, delta);
                        }else{                                      //hidden
                            double sumDelta = 0;
                            //Loop over all connected neurons in next layer
                            for(Synapse outSynapse : neuron.getOutSynapses()){
                                Neuron outNeuron = outSynapse.getOutNeuron();
                                sumDelta += deltaMap.get(outNeuron)*outSynapse.getWeight();
                            }
                            double delta = activityDeriv * sumDelta;
                            deltaMap.put(neuron, delta);
                        }
                        //Compute weightincrement and store in map
                        for(Synapse inSynapse : neuron.getInSynapses()){
                            Neuron inNeuron = inSynapse.getInNeuron();
                            double weightIncrement = learningRate*deltaMap.get(neuron)*inNeuron.output;
                            
                            //Distinguish: online or batch-learning
                            if(!incrementMap.containsKey(inSynapse)){
                                incrementMap.put(inSynapse, weightIncrement);
                            }else{                                                      //batch
                                double oldIncrement = incrementMap.get(inSynapse);
                                double newIncrement = oldIncrement + weightIncrement;
                                incrementMap.put(inSynapse, newIncrement);
                            }
                        }
                        j++;
                    }
                    
                }//end backprop-loop
                
                //Check for learningtype, update weights and compute globalError
                if(network.isLearningOnline() || patternCount == set.getTrainingPatterns().size()){
                    //update weights
                    this.updateWeights(network, incrementMap);
                    incrementMap.clear();
                    deltaMap.clear();                    
                }
                
                //Shuffle pattern after every pattern shown once
                if(patternCount >= set.getTrainingPatterns().size()){
                    set.shufflePatterns();
                    patternCount = 1;
                }

            }//end pattern-loop
            
            //compute global Error for all training- and testpatterns
            for (TrainingPattern trainingPattern : set.getTrainingPatterns()) {
                double[] inputErrorCalc = trainingPattern.p;
                network.solve(inputErrorCalc);
                double[] outputErrorCalc = network.getOutputData();
                globalErrorTrain += errorFunction.compGlobalError(outputErrorCalc, trainingPattern.t);   
            }
            // Store error to errorobject
            errorData.addGlobalErrorTrain(globalErrorTrain);

            for(TrainingPattern testPattern : set.getTestPatterns()){
               double[] inputErrorCalc = testPattern.p;
               network.solve(inputErrorCalc);
               double[] outputErrorCalc = network.getOutputData();
               globalErrorTest += errorFunction.compGlobalError(outputErrorCalc, testPattern.t);
            }
            // Store error and set number of iterations in errordata-object
            errorData.addGlobalErrorTest(globalErrorTest);
            errorData.setNumIter(iterator);
            
            //check error and break out of loop if small enough
            if (globalErrorTrain <= maxError){
                errorData.setTrainingSuccss(true);
                break outerloop;
            }
            
            iterator ++;         
            
        }//end iterator-loop
        
      
    
        return errorData;   
        
    }//end method
    
    /**
     * Updates synaptic weights of network from a HashMap
     * @param incrementMap
     * @param network
     */
    private void updateWeights(Network net, HashMap incrementMap){
        //Loop over all layers, neurons and synaptic outputconnections
        FeedForwardNet network = (FeedForwardNet) net;
        for (Layer layer : network.getLayers()) {
            for(Neuron neuron : layer.getNeurons()){
                for(Synapse outSynapse : neuron.getOutSynapses()){
                    double newWeight;
                    newWeight = (outSynapse.getWeight()+ (double) incrementMap.get(outSynapse));
                    outSynapse.setWeight(newWeight);
                }
            }
        }
        //Update bias-synapses
        for(Synapse outSynapse : network.getBias().getOutSynapses()){
            double newWeight;
            newWeight = (outSynapse.getWeight()+ (double) incrementMap.get(outSynapse));
            outSynapse.setWeight(newWeight);
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

    public ErrorFunction getErrorFunction() {
        return errorFunction;
    }

    public void setErrorFunction(ErrorFunction errorFunction) {
        this.errorFunction = errorFunction;
    }
    
    
    
    
    
}
