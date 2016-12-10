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
 * Parentclass for Feedforwardnetwork
 * @author LammLukas
 */
public abstract class FeedForwardNet implements Network{
    
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
    protected int numLayers;
    
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
     * LearningType
     *  - 0: Offline
     *  - 1: Online
     */
    protected int learningType;
    
    /**
     * BiasNeuron
     */
    protected BiasNeuron bias;

    @Override
    public abstract void assembleNet();

    @Override
    public abstract void solve();
        
    @Override
    public abstract void trainNet();   
    
    
    /**
     * Adding layers to network
     */
    public void addLayers(){
        for (int i = 0; i < numLayers; i++) {
            layers.add(new Layer());            
        }
    }
  
    /**
     * Adds single layer at specified position
     * @param layer
     * @param position 
     */
    public void addSingleLayer(Layer layer, int position){
        layers.add(position, layer);
    }
    
    /**
     * Adds single layer right before outputlayer
     * @param layer 
     */
    public void addSingleLayer(Layer layer){
        layers.add((numLayers-1), layer);
    }
    
    /**
     * Removes specific layer from network
     * @param layer
     */
    public void removeLayer(Layer layer){
        layers.remove(layer);
    }

    //Getter and Setter
    public int getNumInputNeurons() {
        return numInputNeurons;
    }

    public void setNumInputNeurons(int numInputNeurons) {
        this.numInputNeurons = numInputNeurons;
    }

    public int getNumOutputNeurons() {
        return numOutputNeurons;
    }

    public void setNumOutputNeurons(int numOutputNeurons) {
        this.numOutputNeurons = numOutputNeurons;
    }

    public int getNumLayers() {
        return numLayers;
    }

    public void setNumLayers(int numLayers) {
        this.numLayers = numLayers;
    }

    public double[] getInputData() {
        return inputData;
    }

    public void setInputData(double[] inputData) {
        this.inputData = inputData;
    }

    public double[] getOutputData() {
        return outputData;
    }

    public void setOutputData(double[] outputData) {
        this.outputData = outputData;
    }

    public LearningRule getLearningRule() {
        return learningRule;
    }

    public void setLearningRule(LearningRule learningRule) {
        this.learningRule = learningRule;
    }

    public LinkedList<Layer> getLayers() {
        return layers;
    }

    public BiasNeuron getBias() {
        return bias;
    }

    @Override
    public int getLearningtype(){
        return learningType;
    }

    public void setLearningType(int learningType) {
        this.learningType = learningType;
    }

    
    
        
    
    
    
    
    
    
    
   
    
    
}
