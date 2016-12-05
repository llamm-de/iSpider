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
        input[1] = 0;
        double output = 0;
        
        System.out.println("Input:");
        System.out.println(input[0]);
        System.out.println(input[1]);
        System.out.println("");
        
        //Initialize layers
        Layer inLayer = new Layer();
        Layer outLayer = new Layer();
        
        //Add bias neuron
        BiasNeuron bias = new BiasNeuron();
        
        //Add neurons to layers
        inLayer.addNeurons(2, "InputNeuron");
        LinkedList<Neuron> inNeurons = inLayer.getNeurons();
        
        outLayer.addNeurons(1, "OutputNeuron");
        LinkedList<Neuron> outNeurons = outLayer.getNeurons();
        
        //Add synaptic connecions and set weights
        for (Neuron outNeuron : outNeurons) {
            //Add bias synapse
            bias.addOutputSynapse(outNeuron);
                        
            //Add synapses to inputneurons
            for (Neuron inNeuron : inNeurons) {
                inNeuron.addOutputSynapse(outNeuron);
            }
                        
            //Set activityfunction for outputneurons
            outNeuron.setActivityFunction(new HeavisideFct());
            
            //Set synaptic weights
            LinkedList<Synapse> synapses = outNeuron.getInSynapses();
            for (Synapse synapse : synapses) {
                synapse.setWeight(1);
            }
            
        }
        
        //Set bias weight and fire biasneuron
        LinkedList<Synapse> biasSynapse = bias.getOutSynapses();
        for (Synapse synapse : biasSynapse) {
            synapse.setWeight(-1);
            synapse.signal = bias.output;
        }
        
        //Set inputdata and process data
        int i = 0;
        for (Neuron inNeuron : inNeurons){
            inNeuron.setInput(input[i]);
            //Update iterator            
            i++;
            
            inNeuron.compActivity();
            inNeuron.compOutput();
            
            LinkedList<Synapse> synapses = inNeuron.getOutSynapses();
            for (Synapse outSynapse : synapses) {
                outSynapse.signal = inNeuron.output;
            }
        }
        for (Neuron outNeuron : outNeurons){
            outNeuron.compActivity();
            outNeuron.compOutput();
            output = outNeuron.output;
        }
        
        System.out.println("Output:");
        System.out.println(output);
    }
    
}
