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
package data;

import java.util.ArrayList;

/**
 * Class for saving data of error made while executing learnalgorithm
 * @author LammLukas
 */
public class ErrorData {
    
    /*
    * Number of iterations made by learnalgorithm
    */
    private int numIter;
    
    /*
    * ArrayList for the global error made while executing lernalgorithm
    */
    private ArrayList<Double> globalError;

    /**
     * Empty Constructor
    */
    public ErrorData() {
        this.globalError = new ArrayList<>();
    }
    
    //Getter and Setter

    public int getNumIter() {
        return numIter;
    }

    public void setNumIter(int numIter) {
        this.numIter = numIter;
    }

    public ArrayList<Double> getGlobalError() {
        return globalError;
    }

    /**
     * Sets value for global error at specified position
     * @param globalError
     * @param index 
     */
    public void setGlobalErrorAt(double globalError, int index) {
        this.globalError.set(index, globalError);
    }
    
    
    
    
    
    
}
