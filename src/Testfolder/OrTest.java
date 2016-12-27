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
package Testfolder;

import network.Connections.*;
import network.Neurons.*;
import tools.Function.*;
import network.Layer.*;
import network.Network.*;
import network.Training.*;

import java.util.LinkedList;

/**
 * Testclass for simple network and learningalgorithm(perceptronRule)
 * @author LammLukas
 */
public class OrTest {
    
    public static void main(String[] args) {
        //Create network with 2 ins and 1 out and set activityFunction to Heaviside with threshold 0
        SingleLayerPerceptron SLP = new SingleLayerPerceptron(2,1);
        SLP.assembleNet();
        System.out.println("Network assembled!");
        LinkedList<Neuron> outNeurons = SLP.getOutputNeurons();
        for (Neuron outNeuron : outNeurons) {
            outNeuron.setActivityFunction(new HeavisideFct());
        }
        //create trainingSet
        OrTrainingSet orSet = new OrTrainingSet();
        orSet.createSet();
        System.out.println("Trainingset created!");
        
        //train network
        System.out.println("Training starts!");
        SLP.trainNet(orSet);
        
        System.out.println("Training finished!");
        
        //test if training successful
        TrainingPattern testPattern = orSet.getRandom();
        double[] input = testPattern.p;
        System.out.println("Inputvalues:");
        for (double d : input) {  
            System.out.println(d);
        }
        SLP.solve(input);
        double[] output = SLP.getOutput();
        System.out.println("Outputdata:");
        for (double k : output) {  
            System.out.println(k);
        }
    }
    
}
