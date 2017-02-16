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
package data;

/**
 *
 * @author lukas
 */
public class InputPattern implements Pattern{

    /**
     * Array of inputdata for network
     */
    public double[] input;

    /**
     * Empty constructor
     */
    public InputPattern() {
    }
    
    /**
     * Constrctor
     * @param input 
     */
    public InputPattern(double[] input) {
        this.input = input;
    }
    
    //Getter and Setter
        
    @Override
    public double[] getIn() {
        return input;
    }

    @Override
    public void setIn(double[] in) {
        this.input = in;
    }

    @Override
    public double[] getOut() {
        return null;
    }

    @Override
    public void setOut(double[] out) {
        // Do Nothing!
    }
    
    
    
}
