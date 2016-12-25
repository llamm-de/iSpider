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

import network.Training.*;

import java.util.LinkedList;

/**
 * Creates TrainingSet for logical or-operation
 * @author LammLukas
 */
public class OrTrainingSet extends TrainingSet{

    /**
     * Constructor
     */
    public OrTrainingSet() {
        super();
    }

    
    /**
     * creates set for logical or-operation (hard gecoded, to be changed)
     */
    public void createSet(){
            int numInputs = 2;
            int numOutputs = 1;
            double[] p = new double[numInputs];
            double[] t = new double[numOutputs];
            p[0] = 1;
            p[1] = 0;
            t[0] = 1;        
            this.patterns.add(new TrainingPattern(p, t));
            
            p[0] = 1;
            p[1] = 1;
            t[0] = 1;        
            this.patterns.add(new TrainingPattern(p, t));
            
            p[0] = 0;
            p[1] = 1;
            t[0] = 1;        
            this.patterns.add(new TrainingPattern(p, t));
            
            p[0] = 0;
            p[1] = 0;
            t[0] = 0;        
            this.patterns.add(new TrainingPattern(p, t));
            
                        
        }  
    
    
}
