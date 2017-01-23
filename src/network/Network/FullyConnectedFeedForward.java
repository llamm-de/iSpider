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
import tools.Function.ScalarFct;

/**
 * Class for a fully connected feed-forward-network
 * @author LammLukas
 */
public class FullyConnectedFeedForward extends FeedForwardNet{

    /**
     * Empty Constructor
     */
    public FullyConnectedFeedForward() {
    }
  
    
    /**
     * Constructor 
     * with predefined learningrule, set as backpropagation
     * with predefined LearningType, set as Online
     * @param nInputNeurons
     * @param nOutputNeurons
     * @param nLayers 
     */
    public FullyConnectedFeedForward(int nInputNeurons, int nOutputNeurons, int nLayers) {
        this.layers = new LinkedList<>();
        this.numInputNeurons = nInputNeurons;
        this.numOutputNeurons = nOutputNeurons;
        this.numHiddenNeurons = nOutputNeurons;
        this.numLayers = nLayers;
        this.learningRule = new Backpropagation();
        this.inputData = new double[numInputNeurons];
        this.outputData = new double[numOutputNeurons];
        this.onlineLearning = true;
    }
    
        /**
     * Constructor 
     * with predefined learningrule, set as backpropagation
     * with predefined LearningType, set as Online
     * @param nInputNeurons
     * @param nOutputNeurons
     * @param nHiddenNeurons
     * @param nLayers 
     */
    public FullyConnectedFeedForward(int nInputNeurons, int nOutputNeurons, int nHiddenNeurons, int nLayers) {
        this.layers = new LinkedList<>();
        this.numInputNeurons = nInputNeurons;
        this.numOutputNeurons = nOutputNeurons;
        this.numHiddenNeurons = nHiddenNeurons;
        this.numLayers = nLayers;
        this.learningRule = new Backpropagation();
        this.inputData = new double[numInputNeurons];
        this.outputData = new double[numOutputNeurons];
        this.onlineLearning = true;
    }
    
    /**
     * Constructor
     * with variable learningrule
     * with predefined LearningType, set as Online
     * @param nInputNeurons
     * @param nOutputNeurons
     * @param nLayers
     * @param learningRule 
     */
    public FullyConnectedFeedForward(int nInputNeurons, int nOutputNeurons, int nLayers, LearningRule learningRule) {
        this.layers = new LinkedList<>();
        this.numInputNeurons = nInputNeurons;
        this.numOutputNeurons = nOutputNeurons;
        this.numHiddenNeurons = nOutputNeurons;
        this.numLayers = nLayers;
        this.learningRule = learningRule;
        this.inputData = new double[numInputNeurons];
        this.outputData = new double[numOutputNeurons];
        this.onlineLearning = true;
    }
    
    @Override
    public void assembleNet() {
        //Create layers
       this.addLayers();
        
        //Create Neurons
        this.bias = new BiasNeuron();
        int j = 1;
        for (Layer layer : layers) {
            //check for input- and outputlayer
            if(j == 1){                             //Inputlayer
                layer.addInputNeurons(numInputNeurons);
            }else if(j > 1 && j < numLayers){       //Hidden
                layer.addNeurons(numHiddenNeurons);
            }else{                                  //Output
                layer.addNeurons(numOutputNeurons);
            }
            j++;
        }
        
        //Establish synaptic connetions
        
        for (int i = 0; i < numLayers; i++) {
            Layer currentLayer = layers.get(i);
            LinkedList<Neuron> currentNeurons = currentLayer.getNeurons();
            //Check for in- and outputlayer
            if(i < (numLayers-1)){
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
    public void solve(double[] input) {
        //set inputdata
        this.setInputData(input);
        
        //fire bias neuron
        LinkedList<Synapse> biasSynapses = bias.getOutSynapses();
        for (Synapse biasSynapse : biasSynapses) {
            biasSynapse.signal = bias.output;
        }
        //Solve layer by layer
        if(inputData.length == numInputNeurons){
            for (Layer layer : layers) {        
                LinkedList<Neuron> neurons = layer.getNeurons();
                for (int j = 0; j < neurons.size(); j++) {
                    Neuron currentNeuron = neurons.get(j);
                    //Pass inputdata to inputneurons
                    if(!currentNeuron.hasInputSynapses()){
                        InputNeuron currNeuron = (InputNeuron) currentNeuron;
                        currNeuron.input = this.inputData[j];
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
        throw new UnsupportedOperationException("Dimension Mismatch! InputDatat not same size as Inputlayer of network."); 
        }
    }


    @Override
    public double[] getOutput() {
        return this.outputData;
    }

    @Override
    public boolean isLearningOnline() {
        return onlineLearning;
    }

    @Override
    public LinkedList<Neuron> getOutputNeurons() {
        return this.layers.getLast().getNeurons();
    }

    @Override
    public LinkedList<Neuron> getInputNeurons() {
        return this.layers.getFirst().getNeurons();
    }

    @Override
    public void setAllActivityFcts(ScalarFct activityFct) {
        //Loop over all layers
        for (Layer layer : layers) {
            //Loop over all neurons of layer if not inputLayer
            LinkedList<Neuron> currNeurons = layer.getNeurons();
            if(!(currNeurons.get(0) instanceof InputNeuron)){
                for (Neuron currNeuron : currNeurons) {
                    currNeuron.setActivityFunction(activityFct);
                }
            }
        }
    }
    
    
    /**
     * Getter for layers
     * @return all layers of network
     */
    public LinkedList<Layer> getLayers(){
      return this.layers;  
    }
    
    
    
    
    
    

}