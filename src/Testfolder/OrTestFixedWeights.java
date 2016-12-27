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

import network.Connections.*;
import network.Neurons.*;
import tools.Function.*;
import network.Layer.*;
import network.Network.*;

import java.util.LinkedList;

/**
 *
 * @author LammLukas
 */
public class OrTestFixedWeights {
    
    public static void main(String[] args) {
        
        //Initialize input and output
        double[] input = new double[2];
        input[0] = 0;
        input[1] = 1;
        double output[];
        
        System.out.println("Input:");
        System.out.println(input[0]);
        System.out.println(input[1]);
        System.out.println("");
        
        //Initialize network
        FullyConnectedFeedForward fullyNet = new FullyConnectedFeedForward(2, 1, 2);
        fullyNet.assembleNet();

        
        //Set synaptic weights and set activityfunction to identity
        LinkedList<Layer> layers = fullyNet.getLayers();
        Layer inputLayer = layers.getFirst();
        LinkedList<Neuron> neurons = inputLayer.getNeurons();
        for (Neuron neuron : neurons) {
            LinkedList<Synapse> synapses = neuron.getOutSynapses();
            for (Synapse synapse : synapses) {
                synapse.setWeight(1);
            }
        }
        Layer outputLayer = layers.getLast();
        LinkedList<Neuron> outNeurons = outputLayer.getNeurons();
        for (Neuron outNeuron : outNeurons) {
            outNeuron.setActivityFunction(new HeavisideFct());
        }
        
        //Set biasweight
        BiasNeuron bias = fullyNet.getBias();
        LinkedList<Synapse> synapses = bias.getOutSynapses();
        for (Synapse synapse : synapses) {
            synapse.setWeight(-1);
        }
        
        //Solve problem
        fullyNet.solve(input);
        
        output = fullyNet.outputData;
        
        System.out.println("Output:");
        for (int i = 0; i < output.length; i++) {
            System.out.println(output[i]);
            
        }
        
    }
    
}
