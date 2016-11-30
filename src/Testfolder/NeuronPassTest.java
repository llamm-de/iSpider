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

import java.util.LinkedList;

/**
 * Testclass
 * Tests, if signal is passed throu a couple of neurons
 * @author LammLukas
 */
public class NeuronPassTest {
    
    public static void main(String[] args){
        
        //Initialize input and output
        double input = 1;
        double output;
        System.out.println("Input:");
        System.out.println(input);
        
        //Create neurons (one input, one hidden, one output)
        InputNeuron inNeuron = new InputNeuron();
        Neuron outNeuron = new Neuron();
        
        //Create synaptic connections and set weights to 1
        inNeuron.addOutputSynapse(outNeuron);
        LinkedList<Synapse> synapses = inNeuron.getOutSynapses();
        Synapse synapse = synapses.getFirst();
        synapse.setWeight(1);  
        
        System.out.println("Synaptic weight:");
        System.out.println(synapse.getWeight());
                
        //Pass data from input to output (expected result = 1)
        inNeuron.setInput(input);
        
        inNeuron.compActivity();
        inNeuron.compOutput();
        
        synapse.signal = inNeuron.output;
        outNeuron.setActivityFunction(new IdentityFct());
        outNeuron.compActivity();
        outNeuron.compOutput();
        
        output = outNeuron.output;
        
        //Display Output
        System.out.println("Output:");
        System.out.println(output);
        
    }
}
