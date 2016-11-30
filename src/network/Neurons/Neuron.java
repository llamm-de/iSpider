/*
 * The MIT License
 *
 * Copyright 2016 LammLukas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
     * Inputfunction
     */
    protected InputFct inputFunction;
    
    /**
     * Total input for the neuron. Calculated from all linkinputs.
     */
    protected double activity;
    
    /**
     * Output of neuron.
     */
    public double output;

    /**
     * Constructor
     * predefined: 
     * - activityFunction: SigmoidFunction
     * - inputFunction: WeightedSum
     */
    public Neuron(){
        this.inSynapses = new LinkedList<>();
        this.outSynapses = new LinkedList<>();
        this.activityFunction= new SigmoidFct();
        this.inputFunction = new WeightedSumFct();
        this.activity = 0;
        this.output = 0;
    }
    
    /**
     * Constructor
     * @param inputFunction
     * @param activityFunction 
     */
    public Neuron(ScalarFct activityFunction, InputFct inputFunction) {
        this.inSynapses = new LinkedList<>();
        this.outSynapses = new LinkedList<>();
        this.activityFunction = activityFunction;
        this.inputFunction = inputFunction;
        this.activity = 0;
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
    public void compActivity(){
        this.activity = this.inputFunction.getValue(this);
    }
    
        
    /**
     * Computes output of neuron via activityfunction
     */
    public void compOutput(){
        this.output = activityFunction.getValue(this.activity);
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
