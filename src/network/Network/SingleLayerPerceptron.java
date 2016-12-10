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

import network.Training.*;
import network.Layer.*;
import network.Connections.*;
import network.Neurons.*;

/**
 *
 * @author LammLukas
 */
public class SingleLayerPerceptron extends FullyConnectedFeedForward{

    /**
     * Constructor with variable learningrule
     * @param nInputNeurons
     * @param nOutputNeurons
     * @param learningRule 
     */
    public SingleLayerPerceptron(int nInputNeurons, int nOutputNeurons, LearningRule learningRule) {
        this.learningRule = learningRule;
        this.numInputNeurons = nInputNeurons;
        this.numOutputNeurons = nOutputNeurons;
        this.numLayers = 2;
    }
    
    /**
     * Constructor with predefined learningrule (Delta-Rule)
     * @param nInputNeurons
     * @param nOutputNeurons
    */
    public SingleLayerPerceptron(int nInputNeurons, int nOutputNeurons) {
        this.learningRule = new DeltaRule();
        this.numInputNeurons = nInputNeurons;
        this.numOutputNeurons = nOutputNeurons;
        this.numLayers = 2;
    }
    
    
    
    
}
