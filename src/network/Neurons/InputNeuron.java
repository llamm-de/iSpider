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

import java.util.LinkedList;
import network.Connections.Synapse;
import tools.Function.*;

/**
 *
 * @author LammLukas
 */
public class InputNeuron extends Neuron{
    
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
