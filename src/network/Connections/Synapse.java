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
package network.Connections;

import network.Neurons.*;

/**
 *
 * @author LammLukas
 */
public class Synapse{
    
    // Attributes
    /**
     * Input ans outputneurons
     */
    private final Neuron inNeuron;
    private final Neuron outNeuron;
    /**
     * Weight of Synapse
     */
    protected double weight;
    
    /**
     * Signal passed by synapse
     */
    public double signal;
    
          
    /** 
     * Predefined Constructor
     * @param inNeuron
     * @param outNeuron 
     */
    public Synapse(Neuron inNeuron, Neuron outNeuron) {
        this.weight = Math.random();
        this.inNeuron = inNeuron;
        this.outNeuron = outNeuron;        
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
