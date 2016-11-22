/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Neurons;

import network.Connections.Synapse;
import java.util.ArrayList;

/**
 *
 * @author LammLukas
 */
public class BiasNeuron extends Neuron{
     
    public BiasNeuron() {
        this.outSynapses = new ArrayList<>();
        this.output = -1;
    }
    
        
    @Override
    public boolean hasOutputSynapses() {
        return super.hasOutputSynapses(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasInputSynapses() {
        return false;
    }

    @Override
    public void addOutputSynapse(Neuron neuron) {
        super.addOutputSynapse(neuron); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addInputSynapse(Synapse synapse) {
        //DO NOTHING!
    }
    
    
}
