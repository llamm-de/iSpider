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
package network.Network;

import java.util.LinkedList;

import network.Training.*;
import network.Layer.*;
import network.Neurons.*;
import network.Connections.Synapse;

/**
 *
 * @author LammLukas
 */
public class FullyConnectedFeedForward extends FeedForwardNet implements Network{

    /**
     * Constructor 
     * with predefined learningrule, set as backpropagation
     * @param nInputNeurons
     * @param nOutputNeurons
     * @param nLayers 
     */
    public FullyConnectedFeedForward(int nInputNeurons, int nOutputNeurons, int nLayers) {
        this.layers = new LinkedList<>();
        this.numInputNeurons = nInputNeurons;
        this.numOutputNeurons = nOutputNeurons;
        this.numLayers = nLayers;
        this.learningRule = new Backpropagation();
        this.inputData = new double[numInputNeurons];
        this.outputData = new double[numOutputNeurons];
    }
    
    /**
     * Constructor
     * with variable learningrule
     * @param nInputNeurons
     * @param nOutputNeurons
     * @param nLayers
     * @param learningRule 
     */
    public FullyConnectedFeedForward(int nInputNeurons, int nOutputNeurons, int nLayers, LearningRule learningRule) {
        this.layers = new LinkedList<>();
        this.numInputNeurons = nInputNeurons;
        this.numOutputNeurons = nOutputNeurons;
        this.numLayers = nLayers;
        this.learningRule = learningRule;
        this.inputData = new double[numInputNeurons];
        this.outputData = new double[numOutputNeurons];
    }
    
    @Override
    public void assembleNet() {
        //Create layers
        for (int i = 0; i < numLayers; i++) {
            layers.add(new Layer());  
        }
        
        //Create Neurons
        this.bias = new BiasNeuron();
        int j = 1;
        for (Layer layer : layers) {
            //check for input- and outputlayer
            if(j == 1){
                layer.addInputNeurons(numInputNeurons);
            }else if(j == (numLayers-1)){
                layer.addNeurons(numOutputNeurons);
            }else{
                layer.addNeurons(numInputNeurons);
            }
            j++;
        }
        
        //Establish synaptic connetions
        
        for (int i = 0; i < numLayers; i++) {
            Layer currentLayer = layers.get(i);
            LinkedList<Neuron> currentNeurons = currentLayer.getNeurons();
            //Check for in- and outputlayer
            if(i <= (numLayers-1)){
                Layer nextLayer = layers.get(i+1);
                LinkedList<Neuron> nextNeurons = nextLayer.getNeurons();
                //Create connection to all neurons of next layer and biasneuron
                for (Neuron currentNeuron : currentNeurons) {
                    if(i > 0){
                        bias.addOutputSynapse(currentNeuron);
                    }
                    for (Neuron nextNeuron : nextNeurons) {
                        currentNeuron.addOutputSynapse(nextNeuron);
                    }
                }
            }else{
                //create connection to biasneuron for outputlayer
                for (Neuron currentNeuron : currentNeurons) {
                    bias.addOutputSynapse(currentNeuron);
                }
            }         
        }
        
        
    }

    @Override
    public void solve() {
        if(inputData.length == numInputNeurons){
            for (Layer layer : layers) {        
                LinkedList<Neuron> neurons = layer.getNeurons();
                for (int j = 0; j < neurons.size(); j++) {
                    Neuron currentNeuron = neurons.get(j);
                    //Pass inputdata to inputneurons
                    if(!currentNeuron.hasInputSynapses()){
                        currentNeuron.input = this.inputData[j];
                    }
                    //Pass data through network and store in outputData
                    if(currentNeuron.hasOutputSynapses()){
                        currentNeuron.compActivity();
                        currentNeuron.compOutput();
                        LinkedList<Synapse> currOutSynapses = currentNeuron.getOutSynapses();
                        for (Synapse currOutSynapse : currOutSynapses) {
                            currOutSynapse.signal = currentNeuron.output;
                        }
                    }else{
                        currentNeuron.compActivity();
                        currentNeuron.compOutput();
                        this.outputData[j] = currentNeuron.output;
                    }
                }
            }
        }else{
        throw new UnsupportedOperationException("Not enough input arguments for network."); 
        }
    }

    @Override
    public void trainNet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}