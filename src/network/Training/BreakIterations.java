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
package network.Training;



/**
 * Class wich breaks iterative-loop after number of iterations reached a limit value.
 * @author lukas
 */
public class BreakIterations implements BreakCriterion{

    /**
     * Learningrule
     */
    private LearningRule learningRule;
    
    /**
     * Maximum number of iterations
     */
    private int maxIterations;
    
    /**
     * Maximum of allowed error
     */
    private double maxError;
   

    public BreakIterations(LearningRule learningRule, int maxIter) {
        this.learningRule = learningRule;
        this.maxIterations = maxIter;
    }
    
    /**
     * Checks if break criterion is reached
     * @return true if reached, otherwise false
     */
    @Override
    public boolean isReached() {
        return (maxIterations <= learningRule.getIterations());
    }

    //Getter and setter
    
    @Override
    public int getMaxIterations() {
        return maxIterations;
    }

    @Override
    public double getMaxError() {
        return maxError;
    }

    @Override
    public void setMaxIterations(int maxIter) {
        this.maxIterations = maxIter;
    }

    @Override
    public void setMaxError(double maxError) {
        this.maxError= maxError;
    }

    @Override
    public String toString() {
        return "Maximum of iterations";
    }
    
    
    
    
    
    
}
