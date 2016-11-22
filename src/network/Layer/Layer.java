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
public class Layer {
    
     /**
     * Neurons in this layer
     */
    protected ArrayList<Neuron> neurons;
    
    /**
     * Identificationnumber
     */
    private final int id;

    /**
     * Constructor
     * @param id Indentificationnumber
     */
    public Layer(int id) {
        this.id = id;
        this.neurons = new ArrayList<>();
    }
    
    /**
     * Adds neurons to layer
     * @param numNeurons 
     */
    public void addNeurons(int numNeurons){
        for (int i = 0; i < numNeurons; i++) {
            neurons.add(new Neuron(1));
            
        }
    
    }
    
    /**
     * Removes neurons from layer
     */
    public void removeNeurons(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    /**
     * Getter for neurons
     * @return 
     */
    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    /**
     * Getter for id
     * @return 
     */
    public int getId() {
        return id;
    }

    

    
    
    
}
