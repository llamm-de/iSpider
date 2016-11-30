/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Neurons;

import java.util.LinkedList;
import network.Connections.Synapse;
import tools.Function.*;

/**
 *
 * @author LammLukas
 */
public class InputNeuron extends Neuron{
    // Attributes
    
    /**
     * Inputvalue for inputneuron
     */
    public double input;
    
    /**
     * Constructor for InputNeuron
     */
    public InputNeuron() {
        this.outSynapses = new LinkedList<>();
        this.activityFunction = new IdentityFct();
    }

    /**
     * Computes activity of neuron
     * in this case activity equals the inputvalue
     */
    @Override
    public void compActivity() {
        this.activity = this.input;
    }

    /**
     * No inputsynapses should be set!
     * @param synapse 
     */
    @Override
    public void addInputSynapse(Synapse synapse) {
        //DO NOTHING!
    }

    /**
     * Setter for inputvalues
     * @return 
     */
    public double getInput() {
        return input;
    }

    /**
     * Getter for inputvalues
     * @param input 
     */
    public void setInput(double input) {
        this.input = input;
    }
    
    
    
    
    

        
}
