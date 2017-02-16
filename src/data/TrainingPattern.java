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
package data;

/**
 * Class for a pattern of trainingdata
 * @author LammLukas
 */
public class TrainingPattern implements Pattern{
    
    //Attributes
    /**
     * Inputexamples 
     */
    public double[] p;
    
    /**
     * Teachinginput 
     * Array of expected outputs
     */
    public double[] t;

    
    /**
     * Constructor for trainingpattern
     * @param p Input
     * @param t Teachinginput (expected Output)
     */
    public TrainingPattern(double[] p, double[] t) {
        this.p = p;
        this.t = t;
    }

    /**
     * Empty constructor
     */
    public TrainingPattern() {        
    }

    //Getter and Setter
    public double[] getIn() {
        return p;
    }

    public void setIn(double[] p) {
        this.p = p;
    }

    public double[] getOut() {
        return t;
    }

    public void setOut(double[] t) {
        this.t = t;
    }
    
    
    
    
    
    
}
