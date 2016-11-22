/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Layer;

import network.Neurons.*;
import java.util.ArrayList;
/**
 *
 * @author LammLukas
 */
public abstract class Layer {
    
     /**
     * Neurons in this layer
     */
    protected ArrayList<Neuron> neurons;
    
    /**
     * Adds neurons to layer
     * @param numNeurons 
     */
    public abstract void addNeurons(int numNeurons);
    
        
    /**
     * Removes neurons from layer
     */
    public abstract void removeNeurons();

    /**
     * Getter for neurons
     * @return 
     */
    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }


    

    

    
    
    
}
