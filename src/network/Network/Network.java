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
package network.Network;

import java.util.LinkedList;
import network.Neurons.*;
import network.Training.*;
/**
 *
 * @author LammLukas
 */
public interface Network {
    
    /**
     * Assebler for neuronal network
     */
    public void assembleNet();
    
    /**
     * Solver for network
     */
    public void solve();
    
    /**
     * Trains the neuronal network
     */
    public void trainNet(TrainingSet set);
    
    /**
     * Getter for Outputdata
     * @return 
     */
    public double[] getOutput();
    
    /**
     * Getter for Learningtype
     * @return 1: Online; 0: Offline 
     */
    public int getLearningtype();
    
    /**
     * Setter for inputdata at network
     * @param input Inputdata for network
     */
    public void setInputData(double[] input);
    
    /**
     * Getter for outputneurons
     * @return LinkedList with outputneurons
     */
    public LinkedList<Neuron> getOutputNeurons();
    
    /**
     * Getter for inputneurons
     * @return LinkedList
     */
    public LinkedList<Neuron> getInputNeurons();
}
