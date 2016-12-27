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
import network.Neurons.*;
import network.Connections.*;
import tools.Function.*;

import java.util.LinkedList;


/**
 * Implementation of the Perceptron rule (as shwon in KRIESEL p.78)
 * @author LammLukas
 */
public class PerceptronRule implements LearningRule{

    /**
     * Errorfunction
     */
    private ErrorFunction errorFunction;
    
    
    /**
     * Minimal error
     */
    public double minError;
    
    /**
     * Maximal iterations for algorithm
     */
    public int maxIter;

    /**
     * Constructor
     * with variable Errorfunction
     * @param errorFunction 
     * @param minError
     * @param maxIter
     */
    public PerceptronRule(ErrorFunction errorFunction, double minError, int maxIter) {
        this.errorFunction = errorFunction;
        this.minError = minError;
        this.maxIter = maxIter;
    }

    /**
     * Constructor
     * with predefined errorfunction as MeanSqaredError
     * @param minError
     * @param maxIter
     */
    public PerceptronRule(double minError, int maxIter) {
        this.errorFunction = new MeanSquaredError();
        this.maxIter = maxIter;
        this.minError = minError;
    }
    
    /**
     * Constructor
     * with predefined errorfunction as MeanSqaredError
     */
    public PerceptronRule() {
        this.errorFunction = new MeanSquaredError();
        this.maxIter = 1000000;
        this.minError = 0.01;
    }
    
    
    
    /**
     * Applies Rule to network
     * @param network
     * @param set
     */
    @Override
    public void applyRule(Network network, TrainingSet set) {
       
        LinkedList<Neuron> outputNeurons = network.getOutputNeurons();
        LinkedList<Neuron> inputNeurons = network.getInputNeurons();
        double error = Double.MAX_VALUE;
        int iterator = 1;
        
        while(this.minError < error && iterator < this.maxIter){
            //Get random trainingpattern with input and teachingInput
            TrainingPattern pattern = set.getRandom();
            double[] input = pattern.p;
            double[] teachingInput = pattern.t;

            //Set networks input, solve network for Input and get output
            network.solve(input);
            double[] output = network.getOutput();

            //Compute Error
            error = errorFunction.compGlobalError(output, teachingInput);

            //Set weights
            for (Neuron outputNeuron : outputNeurons) {
                double singleOutput = outputNeuron.output;
                if(singleOutput != teachingInput[outputNeurons.indexOf(outputNeuron)]){
                    if(singleOutput == 0){
                        for (Neuron inputNeuron : inputNeurons) {
                            LinkedList<Synapse> outSynapses = inputNeuron.getOutSynapses();
                            for (Synapse outSynapse : outSynapses) {
                                double newWeight = outSynapse.getWeight() + singleOutput;
                                outSynapse.setWeight(newWeight);
                            }
                        }
                    }else if(singleOutput == 1){
                        for (Neuron inputNeuron : inputNeurons) {
                            LinkedList<Synapse> outSynapses = inputNeuron.getOutSynapses();
                            for (Synapse outSynapse : outSynapses) {
                                double newWeight = outSynapse.getWeight() - singleOutput;
                                outSynapse.setWeight(newWeight);
                            }
                        }
                    }
                }
            }
            iterator++;
        }
    
        
        
        
    }

    //Getter and Setter
    public double getMinError() {
        return minError;
    }

    public void setMinError(double minError) {
        this.minError = minError;
    }

    public int getMaxIter() {
        return maxIter;
    }

    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
    }
    
    
    
    
    
}
