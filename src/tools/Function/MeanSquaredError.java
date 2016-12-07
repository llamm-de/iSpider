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

import network.Network.Network;
import network.Training.*;

/**
 * Class for mean squared error
 * @author LammLukas
 */
public class MeanSquaredError implements ErrorFunction{

    /**
     * Computes mean squared error
     * @param net Network for which the error should becomputed
     * @param pattern Trainingpattern for which the error should be computed
     * @return Error
     */
    @Override
    public double compError(Network net, TrainingPattern pattern) {
        //Initialize variables
        double[] output = net.getOutput();
        double error = 0;
        //Check for dimension-match
        if(output.length == pattern.t.length){
            //Compute sum of outputvalues and trainingOutput
            for (int i = 0; i < output.length; i++) {
                error += Math.pow(pattern.t[i] - output[i], 2);
            }
        }else{
            throw new UnsupportedOperationException("Dimension mismatch. Length of trainingoutput is not equal to length of netoutput.");
        }
        return 0.5*error;
    }
    
    
}
