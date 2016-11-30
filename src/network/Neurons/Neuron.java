/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Neurons;

import tools.Function.*;
import network.Connections.*;

import java.util.LinkedList;

/**
 * General class for objects of type NEURON.
 * Represents a neuron in an artificial neural network.
 * @author LammLukas
 */
public class Neuron {
   
    // Attributes
       
    /**
     * Input and outputlinks.
     */
    protected LinkedList<Synapse> inSynapses;
    protected LinkedList<Synapse> outSynapses;
    
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
    public double output;

    /**
     * Constructor with predefined activityFunction
     * @param activityFunction 
     */
    public Neuron(ScalarFct activityFunction) {
        this.inSynapses = new LinkedList<>();
        this.outSynapses = new LinkedList<>();
        this.activityFunction = activityFunction;
        this.totalInput = 0;
        this.output = 0;
    }
    
       
    /**
     * Adds new input synapse to inSynapses
     * @param synapse synapse to be added
     */
    public void addInputSynapse(Synapse synapse){
        if(!this.hasInSynapseFrom(synapse.getInNeuron())){
            this.inSynapses.add(synapse);  
        }
        else{
            //Exception
        }
    }

    /**
     * Adds new output synapse to outSynapses of this object
     * and to inSynapses of receiving neuron
     * @param neuron neuron to be connected with
     */
    public void addOutputSynapse(Neuron neuron){
        if(!this.hasOutSynapseTo(neuron)){
            Synapse synapse = new Synapse(this, neuron);
            this.outSynapses.add(synapse);
            neuron.addInputSynapse(synapse);
        }
        else{
            //Exception
        }
    }
    
    /**
     * Removing inputsynapse 
     * with reference to index of synapse
     * @param index 
     */
    public void remInputSynapse(int index){
        if (this.hasInputSynapses()){
            if(this.inSynapses.contains(this.inSynapses.get(index))){
                this.inSynapses.remove(index);
            }
        }
        else{
            //Isert exception
        }
    }
    
    /**
     * Removing inputsynapse
     * with reference to the object itself
     * @param synapse 
     */
    public void remInputSynapse(Synapse synapse){
        if (this.hasInputSynapses()){
            if(this.inSynapses.contains(synapse)){
                this.inSynapses.remove(synapse);
            }
        }
        else {
            //Insert exceptioncode
        }
    }
    
    /**
     * Removing inputsynapse 
     * with reference to index of synapse
     * @param index 
     */
    public void remOutputSynapse(int index){
        if(this.hasOutputSynapses()){
            if(this.outSynapses.contains(this.outSynapses.get(index))){
                this.outSynapses.remove(index);
            }
        }
        else{
            //Insert exceptioncode
        }
    }

    
    
    /**
     * Removing outputsynapse
     * with reference to the object itself
     * @param synapse 
     */
    public void remOutputSynapse(Synapse synapse){
        if (this.hasOutputSynapses()){    
            if(this.outSynapses.contains(synapse)){
                this.outSynapses.remove(synapse);
            }
        }
        else{
            //Isert exception
        }
    }
    
    /**
     * Computes totalInput from inputsynapses
     */
    public void compTotalInput(){
        for (Synapse inSynapse : inSynapses) {
            this.totalInput += inSynapse.signal*inSynapse.getWeight();
        }
    }
    
    /**
     * Setter for totalInput
     * to set inputdata at inputneurons
     * @param input 
     */
    public void setTotalInput(double input){
        this.totalInput = input;
    }
    
    /**
     * Computes output of neuron via activityfunction
     */
    public void compOutput(){
        this.output = activityFunction.getValue(this.totalInput);
    }

    /**
     * Getter for Synapses
     * @return 
     */
    public LinkedList<Synapse> getInSynapses() {
        return inSynapses;
    }

    /**
     * Getter for Synapses
     * @return 
     */
    public LinkedList<Synapse> getOutSynapses() {
        return outSynapses;
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
    
    /**
     * Checks if this neuron has inputsynapse from inNeuron
     * @param inNeuron 
     * @return true if synapse exists
     */
    public boolean hasInSynapseFrom(Neuron inNeuron){
        for (Synapse inSynapse : inSynapses) {
            if(inSynapse.getInNeuron() == inNeuron){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if this neuron has outputsynapse to outNeuron
     * @param outNeuron
     * @return true is synapse exists
     */
    public boolean hasOutSynapseTo(Neuron outNeuron){
        for (Synapse outSynapse : outSynapses) {
            if(outSynapse.getOutNeuron() == outNeuron){
                return true;
            }
        }
        return false;
    }
}
