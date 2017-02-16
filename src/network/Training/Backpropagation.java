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

import data.DataSet;
import data.Pattern;
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
    public void applyRule(Network net, DataSet set) {
        //Cast network
        FeedForwardNet network = (FeedForwardNet) net;
        
        //Extract testpatterns from dataset
        DataSet[] sets = this.extractTestSet(set);
        DataSet trainingSet = sets[0];
        DataSet testSet = sets[1];
        
        //Hashmaps for delta and increment
        HashMap<Synapse,Double> incrementMap = new HashMap<>();
        HashMap<Neuron,Double> deltaMap = new HashMap<>();
               
        //Set denominator for computatin of delta
        if(!network.isLearningOnline()){
            denominator = trainingSet.getPatterns().size();
        }else{
            denominator = 1;
        }
        
        //Set iterator and initial error
        this.iterations = 1;
        double errorTrain = 0;
        double errorTest = 0;
        //Compute initial error for training- and testpatterns
        for(Pattern testPattern : testSet.getPatterns()){
           double[] testInput = testPattern.getIn();
           network.solve(testInput);
           errorTest += errorFunction.compGlobalError(network.getOutputData(), testPattern.getOut())/testSet.getPatterns().size();
        }
        globalErrorTest.add(0, errorTest);
        for(Pattern trainingPattern : trainingSet.getPatterns()){
            double[] trainingInput = trainingPattern.getIn();
            network.solve(trainingInput);
            errorTrain += errorFunction.compGlobalError(network.getOutputData(),trainingPattern.getOut())/trainingSet.getPatterns().size();
        }
        globalErrorTrain.add(0, errorTrain);
        
        
        //Loop until breaking-criterion is reached
        while(!breakCriterion.isReached()){
                        
            //Loop over all patterns
            int patternCount = 0;
            for (Pattern pattern : trainingSet.getPatterns()) {
                //Count patterns 
                patternCount++;
                                
                //Solve for pattern
                double[] input = pattern.getIn();
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
                            double delta = activityDeriv*errorFunction.compDerivative(network.outputData[j], pattern.getOut()[j])/denominator;
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
                if(network.isLearningOnline() || patternCount == trainingSet.getPatterns().size()){
                    //update weights
                    this.updateWeights(network, incrementMap);
                    incrementMap.clear();
                    deltaMap.clear();                    
                }
                
                //Shuffle pattern after every pattern shown once
                if(patternCount >= trainingSet.getPatterns().size()){
                    trainingSet.shufflePatterns();
                    patternCount = 1;
                }
                
            }//end pattern-loop
            
            //compute global Error for all training- and testpatterns
            errorTrain = 0; //reset
            for (Pattern trainingPattern : trainingSet.getPatterns()) {
                double[] inputErrorCalc = trainingPattern.getIn();
                network.solve(inputErrorCalc);
                double[] outputErrorCalc = network.getOutputData();
                errorTrain += errorFunction.compGlobalError(outputErrorCalc, trainingPattern.getOut())/trainingSet.getPatterns().size();
                
            }
            globalErrorTrain.add(errorTrain);

            //reset
            errorTest = 0;
            for(Pattern testPattern : testSet.getPatterns()){
               double[] inputErrorCalc = testPattern.getIn();
               network.solve(inputErrorCalc);
               double[] outputErrorCalc = network.getOutputData();
               errorTest += errorFunction.compGlobalError(outputErrorCalc, testPattern.getOut())/testSet.getPatterns().size();
               
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

    @Override
    public String toString() {
        return "Backpropagation";
    }
    
    
}
