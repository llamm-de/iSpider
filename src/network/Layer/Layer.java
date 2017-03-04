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
package network.Layer;

import network.Neurons.*;
import java.util.LinkedList;
/**
 * Layerclass
 * Represents a layer of neurons in specific kind of neural network
 * @author LammLukas
 */
public class Layer {
    
     /**
     * Neurons in this layer
     */
    protected LinkedList<Neuron> neurons;

    /**
     * Empty Constructor
     */
    public Layer() {
        this.neurons = new LinkedList<>();
        
    }     
    
    /**
     * Adds a number of new inputneurons to inputlayer
     * @param numNeurons 
     */
    public void addInputNeurons(int numNeurons){
        for (int i = 0; i < numNeurons; i++) {
            this.neurons.add(new InputNeuron());
        }
    }
    
    /**
     * Adds a number of new neurons of specified type to layer
     * @param numNeurons 
     */
    public void addNeurons(int numNeurons){
        for (int i = 0; i < numNeurons; i++) {
            this.neurons.add(new Neuron());
        }
    }
    
    /**
     * Adds specific neuron to layer
     * 
     * @param neuron 
     */
    public void addSingleNeuron(Neuron neuron){
        if(!this.neurons.contains(neuron)){
            this.neurons.add(neuron);
        }
    }
    
        
    /**
     * Removes neuron from layer by index of neuron
     * @param index
     */
    public void removeNeuron(int index){
        this.neurons.remove(index);
    }
    
    /**
     * Removes single neuron from from layer by object itself
     * @param neuron 
     */
    public void removeNeuron(Neuron neuron){
        if(this.neurons.contains(neuron)){
            this.neurons.remove(neuron);
        }
    }
    
    /**
     * Removes all neurons from Layer
     */
    public void removeAllNeurons(){
        if(!this.neurons.isEmpty()){
            this.neurons.clear();
        }
    }

    /**
     * Getter for neurons
     * @return 
     */
    public LinkedList<Neuron> getNeurons() {
        return neurons;
    }


    

    

    
    
    
}
