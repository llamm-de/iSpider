/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Connections;

import network.Neurons.*;

/**
 *
 * @author LammLukas
 */
public class Synapse{
    
    // Attributes
    private final Neuron inNeuron;
    private final Neuron outNeuron;
    private double weight;
    
    public double output;
    
       
    /** Constructor
     * 
     * @param inNeuron
     * @param outNeuron 
     */
    public Synapse(Neuron inNeuron, Neuron outNeuron) {
        this.weight = Math.random();
        this.inNeuron = inNeuron;
        this.outNeuron = outNeuron;
    }
    
    /** Computation of weighted result
     * 
     * @param input from inputneuron
    */
    public void compWeightedOutput(double input){
        output = input*weight;
    }
    
   
    
    /** Getter for Weight
     * 
     * @return 
     */
    public double getWeight() {
        return weight;
    }
    
    /** Setter for Weight
     * 
     * @param weight 
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

        /** Getter for inputneuron
     * 
     * @return 
     */
    public Neuron getInNeuron() {
        return inNeuron;
    }

    /** Getter for outputneuron
     * 
     * @return 
     */
    public Neuron getOutNeuron() {
        return outNeuron;
    }
    
    
    
    
    
}
