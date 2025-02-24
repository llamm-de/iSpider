/*
 * The MIT License
 *
 * Copyright 2017 lukas.
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

import java.util.Random;
import data.*;

import tools.Function.*;

/**
 *
 * @author lukas
 */
public class QuadraticFctSet {
    
    public static DataSet createSet(int numPatterns, double[] range){
       
        //initialize datastructures
        DataSet quadraticSet = new DataSet();
        QuadraticFct quadraticFct = new QuadraticFct();
        Random random = new Random();
        double rangeMin = range[0];
        double rangeMax = range[1];
        double[] allInputs = new double[numPatterns];
        double[] allOutputs = new double[numPatterns];

        
        //Create random trainingpatterns        
        for (int i = 0; i < numPatterns; i++) {   
            //create new objects for in- and outputdata and pattern
            double[] input = new double[1];
            double[] output = new double[1];
            TrainingPattern trainingPattern = new TrainingPattern();
            TrainingPattern testPattern = new TrainingPattern();
            
            //get random input
            input[0] = rangeMin + (rangeMax - rangeMin)*random.nextDouble();
            //round input
            input[0] = input[0]*100;
            input[0] = Math.round(input[0]);
            input[0] = input[0]/100;
            
            //compute desired output from input and create pattern
            output[0] = quadraticFct.getValue(input[0]);
            trainingPattern.setIn(input);
            trainingPattern.setOut(output);
                        
            //store trainingpatterns
            quadraticSet.addPattern((Pattern) trainingPattern);
            
            
           
        }      
        return quadraticSet;
    };
    
}
