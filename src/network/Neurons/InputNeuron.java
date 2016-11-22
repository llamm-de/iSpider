/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Neurons;

import java.util.ArrayList;
import network.Connections.Synapse;

/**
 *
 * @author LammLukas
 */
public class InputNeuron extends Neuron{

    public InputNeuron() {
        this.outSynapses = new ArrayList<>();
        this.output = 0;
        this.totalInput = 0;
    }
    
    /**
     * Sets inputvalue for inputnode
     * @param input inputvalue
     */
    public void setInput(double input){
        this.totalInput = input;
    }

    @Override
    public boolean hasOutputSynapses() {
        return super.hasOutputSynapses(); 
    }

    @Override
    public boolean hasInputSynapses() {
        return false;
    }

    @Override
    public void addOutputSynapse(Neuron neuron) {
        super.addOutputSynapse(neuron); 
    }

    @Override
    public void addInputSynapse(Synapse synapse) {
        //DO NOTHING!!!
    }
    
    
}
