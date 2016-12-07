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

import network.Network.*;
import network.Training.TrainingPattern;

/**
 * Abstract class for errorfunctions
 * @author LammLukas
 */
public abstract class ErrorFunctions {
    
    /**
     * Computes mean-squared-error for use in training a neuronal network
     * @return Error as scalar double
     */
    public static double compMeanSquaredError(Network net, TrainingPattern pattern){
        //Initialize variables
        double[] output = net.getOutput();
        double error = 0;
        //Compute squaredsum of outputvalues and trainingOutput
        error = ErrorFunctions.compSquaredSum(pattern.t, output);
        
        return 0.5*error;
    }
    
    /**
     * Computes euklid-error for use in training a neuronal network
     * @return Error as scalar double
     */
    public static double compEuklidError(Network net, TrainingPattern pattern){
        //Initialize variables
        double[] output = net.getOutput();
        double error = 0;
        //Compute squaredsum of outputvalues and trainingOutput
        error = ErrorFunctions.compSquaredSum(pattern.t, output);
        
        return Math.sqrt(error);
    }
    
    /**
     * Computes root-mean-squared-error for use in training a neuronal network
     * @return Error as scalar double
     */
    public static double compRMSError(Network net, TrainingPattern pattern){
        //Initialize variables
        double[] output = net.getOutput();
        double error = 0;
        //Compute squaredsum of outputvalues and trainingOutput
        error = ErrorFunctions.compSquaredSum(pattern.t, output);
        
        return Math.sqrt(error/output.length);
    }
    
    
    
    /**
     * Computes the negative squared sum of all elements in a and b
     * @param a
     * @param b
     * @return result
     */
    private static double compSquaredSum(double[] a, double[] b){
        double result = 0;
        //Check for dimensionmismatch
        if(a.length == b.length){
            for (int i = 0; i < a.length; i++) {
                result += Math.pow(b[i] - a[i], 2);
            }
        }else{
            throw new UnsupportedOperationException("Dimension mismatch.");
        }
        return result;
    }
}
