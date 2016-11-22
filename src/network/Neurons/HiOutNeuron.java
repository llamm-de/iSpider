/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Neurons;

import java.util.ArrayList;
import network.Connections.Synapse;
import tools.Function.*;
/**
 *
 * @author LammLukas
 */
public class HiOutNeuron extends Neuron {

    /**
     * Constructor
     */
    public HiOutNeuron() {
        this.inSynapses = new ArrayList<>();
        this.outSynapses = new ArrayList<>();
    }
    
    /**
     * Sets activityfunction
     * @param function 
     */
    public void setActivityFct(ScalarFct function){
        this.activityFunction = function;
    }
    
    /**
     * Computes totalInput from synapses
     */
    public void compTotalInput(){
        double sum = 0;
        for (Synapse synapse : inSynapses) {
            sum += synapse.output;
        }
        this.totalInput = sum;
    }
    
    /**
     * Computes output from neuron to outputsynapses
     */
    public void compOutput(){
        this.output = this.activityFunction.getValue(this.totalInput);
    }
}
