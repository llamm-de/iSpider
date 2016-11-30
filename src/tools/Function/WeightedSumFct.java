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
package tools.Function;

import network.Neurons.*;
import network.Connections.*;
import java.util.LinkedList;

/**
 * WeightedSum Inputfunction
 * Computes the weighted sum of the synapseinput for a single neuron
 * @author LammLukas
 */
public class WeightedSumFct implements InputFct{

    /**
     * Computes activity for given neuron
     * @param neuron
     * @return activity
     */
    @Override
    public double getValue(Neuron neuron){
        LinkedList<Synapse> inSynapses = neuron.getInSynapses();
        double activity = 0;
        for (Synapse inSynapse : inSynapses) {
            activity += inSynapse.signal * inSynapse.getWeight();
        }
        return activity;
    }
    
    
}
