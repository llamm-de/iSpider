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
import network.Layer.*;
import network.Training.*;
import network.Neurons.*;
import network.Connections.*;


/**
 *
 * @author LammLukas
 */
public class FeedForwardNet implements Network{
    
    //Attributes
    /**
     * List of layers in network
     */
    LinkedList<Layer> layers;
    
    /**
     * Number of inputneurons for network
     */
    protected int numInputNeurons;
    
    /**
     * number of outputneurons for network
     */
    protected int numOutputNeurons;
    
    /**
     * Number of hidden layers for network
     */
    protected int numHiddenLayers;
    
    /**
     * Array of inputdata
     */
    public double[] inputData;
    
    /**
     * Array of outputdata
     */
    public double[] outputData;
    
    /**
     * Learning rule for Trainingalgorithm of network
     */
    protected LearningRule learningRule;
    
    /**
     * BiasNeuron
     */
    private BiasNeuron bias;
    
    
    /**
     * Constructor
     * with variable learningrule
     * @param numInputNeurons
     * @param numOutputNeurons
     * @param numHiddenLayers
     * @param learningRule 
     */
    public FeedForwardNet(int numInputNeurons, int numOutputNeurons, int numHiddenLayers, LearningRule learningRule) {
        this.layers = new LinkedList<>();
        this.inputData = new double[numInputNeurons];
        this.outputData = new double[numOutputNeurons];
        this.numInputNeurons = numInputNeurons;
        this.numOutputNeurons = numOutputNeurons;
        this.numHiddenLayers = numHiddenLayers;
        this.learningRule = learningRule;
    }
    
    /**
     * Constructor
     * with predefined learningrule as backpropagation
     * @param numInputNeurons
     * @param numOutputNeurons
     * @param numHiddenLayers
     */
    public FeedForwardNet(int numInputNeurons, int numOutputNeurons, int numHiddenLayers) {
        this.layers = new LinkedList<>();
        this.inputData = new double[numInputNeurons];
        this.outputData = new double[numOutputNeurons];
        this.numInputNeurons = numInputNeurons;
        this.numOutputNeurons = numOutputNeurons;
        this.numHiddenLayers = numHiddenLayers;
        this.learningRule = new Backpropagation();
    }
    
    
    @Override
    public void assembleNet() {
        //Create layers and fill with neurons
            //Inputlayer
            this.layers.add(new Layer(numInputNeurons, "InputNeuron"));
            
            //Hidden layers
            for (int i = 0; i < numHiddenLayers; i++) {
                this.layers.add(new Layer(numInputNeurons, "HiddenNeurons"));
            }
            //Outputlayer
            this.layers.add(new Layer(numOutputNeurons, "OutputNeuron"));
                
        //Add biasneuron
        this.bias = new BiasNeuron();
        
        //Make connections
        int numLayers = this.layers.size();
        for (int i = 0; i < (numLayers-1); i++) {
            Layer currentLayer = layers.get(i);
            Layer nextLayer = layers.get(i+1);
            LinkedList<Neuron> currentNeurons = currentLayer.getNeurons();
            LinkedList<Neuron> nextNeurons = nextLayer.getNeurons();
            for (int j = 0; j < currentNeurons.size(); j++) {
                Neuron currentNeuron = currentNeurons.get(j);
                bias.addOutputSynapse(currentNeuron);
                for(int k = 0; k < nextNeurons.size(); k++){
                    Neuron nextNeuron = nextNeurons.get(k);
                    currentNeuron.addOutputSynapse(nextNeuron);
                }
            }
            
            
        }
        
                
    }

    @Override
    public void solve() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void trainNet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
