/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        this.neurons = new LinkedList<Neuron>();
        this.neurons.add(new BiasNeuron());
    } 
    
    /**
     * Constructor with given number of neurons
     * @param numNeurons 
     */
    public Layer(int numNeurons, String neuronType) {
        this.neurons = new LinkedList<Neuron>();
        this.neurons.add(new BiasNeuron());
        this.addNeurons(numNeurons, neuronType);
    }
       
    
    /**
     * Adds a number of new neurons to layer
     * @param numNeurons 
     */
    public void addNeurons(int numNeurons, String neuronType){
        for (int i = 0; i < numNeurons; i++) {
            if(neuronType == "InputNeuron"){
                this.neurons.add(new InputNeuron());
            }
            else {
                this.neurons.add(new Neuron());
            }
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
    public void removeSingleNeuron(Neuron neuron){
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
