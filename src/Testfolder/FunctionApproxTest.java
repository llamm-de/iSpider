/*
 * The MIT License
 *
 * Copyright 2017 LammLukas.
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
package Testfolder;

import network.Network.*;
import network.Training.*;

import tools.Function.*;

/**
 * Testclass for backpropagation-algorithm
 * Tests, if network is able to learn to approximate a sinus function.
 * @author LammLukas
 */
public class FunctionApproxTest {
    
    public static void main(String[] args) {
        
       //Initialize Network
       int inNeurons = 1;
       int outNeurons = 1;
       int numLayers = 3;
       FullyConnectedFeedForward network = new FullyConnectedFeedForward(inNeurons, outNeurons, numLayers);
       network.assembleNet();
       network.setAllActivityFcts(new HyperbolicTangentFct());
       
        
        
       //Get TrainingSet
       double[] range = new double[2];
       range[0] = -10;
       range[1] = 10;
       TrainingSet trainingSet = SinusSet.createSet(100, range);
       
       
       //Train network
       
       
        
    }
    
}
