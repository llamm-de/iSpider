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

import tools.Function.*;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class for Backpropagation of error.
 * Implementation of abstract class LearningRule.
 * @author LammLukas
 */
public class Backpropagation extends LearningRule{
    
        
    /**
     * Denominator for delta
     * Case: Online -> denominator = 1
     * Case: Batch -> denominator = number of TrainingPatterns
     */
    private int denominator;
    
    /**
     * Empty Constructor
     * Learningrate predefined as 1.
     * Errorfunction predefined as MeanSquaredError.
     * Breakcriterion predefined as Error and Iteration.
     */
    public Backpropagation() {
        this.learningRate = 1;
        this.errorFunction = new MeanSquaredError();
        this.TrainingSuccess = false;
        this.globalErrorTrain = new ArrayList<>();
        this.globalErrorTest = new ArrayList<>();
        this.breakCriterion = new BreakErrorAndIteration(this);
    }

    /**
     * Constructor with variable errorfunction
     * @param errorFunction
     */
    public Backpropagation(ErrorFunction errorFunction) {
        this.learningRate = 1;
        this.errorFunction = errorFunction;
        this.TrainingSuccess = false;
        this.globalErrorTrain = new ArrayList<>();
        this.globalErrorTest = new ArrayList<>();
        this.breakCriterion = new BreakErrorAndIteration(this);
    }

      
    @Override     
    public void applyRule(Network net, TrainingSet set) {
        //Cast network
        FeedForwardNet network = (FeedForwardNet) net;
        
        //Hashmaps for delta and increment
        HashMap<Synapse,Double> incrementMap = new HashMap<>();
        HashMap<Neuron,Double> deltaMap = new HashMap<>();
               
        //Set denominator for computatin of delta
        if(!network.isLearningOnline()){
            denominator = set.getTrainingPatterns().size();
        }else{
            denominator = 1;
        }
        
        //Set iterator and initial error
        this.iterations = 1;
        this.globalErrorTrain.add(Double.MAX_VALUE);
        this.globalErrorTest.add(Double.MAX_VALUE);
        double errorTrain = 0;
        double errorTest = 0;
        
        
        //Loop until breaking-criterion is reached
        while(!breakCriterion.isReached()){
            // set/reset error-variables
            if(iterations == 1){
                this.globalErrorTest.clear();
                this.globalErrorTrain.clear();
                //Compute initial error for testpatterns
                for(TrainingPattern testPattern : set.getTestPatterns()){
                   double[] testInput = testPattern.p;
                   network.solve(testInput);
                   errorTest += errorFunction.compGlobalError(testInput, testPattern.t)/set.getTestPatterns().size();
                }
                globalErrorTest.add(0, errorTest);
            }
            
            //Loop over all patterns
            int patternCount = 0;
            for (TrainingPattern pattern : set.getTrainingPatterns()) {
                //Count patterns 
                patternCount++;
                                
                //Solve for pattern
                double[] input = pattern.p;
                network.solve(input); 
                
                //Compute and accumulate initial error for trainingpatterns
                if(iterations == 1){
                    errorTrain += errorFunction.compGlobalError(network.getOutputData(),pattern.t)/denominator;
                    if(patternCount >= set.getTrainingPatterns().size()){
                        globalErrorTrain.add(0, errorTrain);
                    }
                }
                                               
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
                            double delta = activityDeriv*errorFunction.compDerivative(network.outputData[j], pattern.t[j])/denominator;
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
                            if(!incrementMap.containsKey(inSynapse)){                   //online
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
            errorTrain = 0; //reset
            for (TrainingPattern trainingPattern : set.getTrainingPatterns()) {
                double[] inputErrorCalc = trainingPattern.p;
                network.solve(inputErrorCalc);
                double[] outputErrorCalc = network.getOutputData();
                errorTrain += errorFunction.compGlobalError(outputErrorCalc, trainingPattern.t)/set.getTrainingPatterns().size();
                
            }
            globalErrorTrain.add(errorTrain);

            //reset
            errorTest = 0;
            for(TrainingPattern testPattern : set.getTestPatterns()){
               double[] inputErrorCalc = testPattern.p;
               network.solve(inputErrorCalc);
               double[] outputErrorCalc = network.getOutputData();
               errorTest += errorFunction.compGlobalError(outputErrorCalc, testPattern.t)/set.getTestPatterns().size();
               
            }
            globalErrorTest.add(errorTest);
            
            //check error and break out of loop if small enough
            if (globalErrorTrain.get(iterations) <= breakCriterion.getMaxError()){
                this.TrainingSuccess = true;
                break;
            }
            
            iterations++;         
            
        }//end iterator-loop  
        
    }//end method
    
    /**
     * Updates synaptic weights of network from a HashMap
     * @param incrementMap Map of increments for synaptic connections
     * @param net Network which should be updated
     */
    @Override
    public void updateWeights(Network net, HashMap incrementMap){
        //Loop over all layers, neurons and synaptic outputconnections
        FeedForwardNet network = (FeedForwardNet) net;
        for (Layer layer : network.getLayers()) {
            for(Neuron neuron : layer.getNeurons()){
                for(Synapse outSynapse : neuron.getOutSynapses()){
                    double newWeight;
                    newWeight = (outSynapse.getWeight() + (double) incrementMap.get(outSynapse));
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
    
}
