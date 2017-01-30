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

import org.apache.commons.math3.util.FastMath;

/**
 * Class for computation of a sigmoidfunction including its derivative
 * @author LammLukas
 */
public class SigmoidFct extends DifferentiableFct implements ScalarFct{
    
    // Attributes
    /**
     * Coefficient for sigmoid function
     */
    public double k;
    
    /**
     * Constructor for given k
     * @param k Coefficient
     */
    public SigmoidFct(double k) {
        this.k = k;
    }
    
    /**
     * General constructor.
     * 
     */
    public SigmoidFct(){
        this.k = 1;
    }
    
    /**
     * Computes value of function at given value
     * @param value given value
     * @return value of function
     */    
    @Override
    public double getValue(double value){
        return (1/(1 + FastMath.exp(-value*k)));
    }
    
    /**
     * Computes derivative of function at given value
     * @param value given value
     * @return derivative at given value
     */
    @Override
    public double getDerivative(double value) {
        return this.getValue(value)*(1-this.getValue(value));
    }

    /**
     * Getter for k
     * @return k
     */
    public double getK() {
        return k;
    }

    /**
     * Setter for k
     * @param k 
     */
    public void setK(double k) {
        this.k = k;
    }
    
    
}
