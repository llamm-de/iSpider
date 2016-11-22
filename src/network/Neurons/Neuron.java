/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Neurons;

import tools.Function.*;
import network.Connections.*;
import java.util.ArrayList;

/**
 *
 * @author LammLukas
 */
public abstract class Neuron {
   
    // Attributes
       
    /**
     * Input and outputlinks.
     */
    protected ArrayList<Synapse> inSynapses;
    protected ArrayList<Synapse> outSynapses;
    
    /**
     * Activityfunction.
     */
    protected ScalarFct activityFunction;
    
    /**
     * Total input for the neuron. Calculated from all linkinputs.
     */
    protected double totalInput;
    
    /**
     * Output of neuron.
     */
    protected double output;
    
        
    /**
     * Adds new input synapse to inSynapses
     * @param synapse synapse to be added
     */
    public void addInputSynapse(Synapse synapse){
        this.inSynapses.add(synapse);
    }

    /**
     * Adds new output synapse to outSynapses of this object
     * and to inSynapses of receiving neuron
     * @param neuron neuron to be connected with
     */
    public void addOutputSynapse(Neuron neuron){
        Synapse synapse = new Synapse(this, neuron);
        this.outSynapses.add(synapse);
        neuron.addInputSynapse(synapse);
    }
    
    /**
     * Shows wether neuron has inputsynapses or not
     * @return boolean
     */
    public boolean hasInputSynapses(){
        return (this.inSynapses.size() > 0);
    }
    
    /**
     * Shows wether neuron has outputsynapses or not
     * @return boolean
     */
    public boolean hasOutputSynapses(){
        return (this.outSynapses.size() > 0);
    }
    
}
