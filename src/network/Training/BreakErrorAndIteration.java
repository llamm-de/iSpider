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
 *
 * @author lukas
 */
public class BreakErrorAndIteration implements BreakCriterion{

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

    /**
     * Constructor
     * @param learningRule 
     */
    public BreakErrorAndIteration(LearningRule learningRule) {
        this.learningRule = learningRule;
    }
    
    
    
    @Override
    public boolean isReached() {
        
        int iterations = learningRule.getIterations();
        double trainingError = learningRule.getGlobalErrorTrain().get(iterations-1);
                
        return (trainingError <= maxError || iterations >= maxIterations);
    }

    @Override
    public int getMaxIterations() {
        return this.maxIterations;
    }

    @Override
    public double getMaxError() {
        return this.maxError;
    }

    @Override
    public void setMaxIterations(int maxIter) {
        this.maxIterations = maxIter;
    }

    @Override
    public void setMaxError(double maxError) {
        this.maxError = maxError;
    }
    
    
}
