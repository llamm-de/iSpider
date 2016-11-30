/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Neurons;

import network.Connections.Synapse;
import java.util.LinkedList;
import tools.Function.*;


/**
 *
 * @author LammLukas
 */
public class BiasNeuron extends Neuron{

    /**
     * Constructor for BiasNeuron
     */
    public BiasNeuron() {
        this.outSynapses = new LinkedList<>();
        this.activityFunction = new IdentityFct();
        this.output = 1;
        this.activity = 1;
    }

    
    @Override
    public void compActivity() {
        this.activity = 1;
    }

    @Override
    public void addInputSynapse(Synapse synapse) {
        //DO NOTHING!
    }

    /**
     * Avoids that other function than identity can be chosen for activityfunction
     * of biasneuron.
     * @param activityFunction 
     */
    @Override
    public void setActivityFunction(ScalarFct activityFunction) {
        this.activityFunction = new IdentityFct();
    }
    
    

    
     
    
    
}
