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

import network.Network.*;

import tools.Function.*;

import java.util.LinkedList;
import org.apache.commons.math3.util.FastMath;

/**
 * This class provides an algorithm for scaling data to a range which can be 
 * computed by the activityfunction of the neural network.
 * @author lukas
 */
public class ScaleTool {
    
    /**
     * Scalingfactor
     */
    private final double[] scaleFactor;
    
    /**
     * Boolean for checking whether scaleFactor is already initialized or not.
     */
    private boolean initialized;

    /**
     * Empty constructor
     */
    public ScaleTool() {
        this.initialized = false;
        this.scaleFactor = new double[2];
    }
    
    /**
     * Method for initializing the scalefactor for the data to be processed in the network.
     * It takes the set of trainingdata and finds a factor, which scales the data to 
     * fit in the displayable range.
     * This method should be called before learningalgorithm is starten, to get 
     * the proper scaling for the data which should be processed.
     * Once this method has been called, it can't be called again.
     * @param net Network for which the scalefactor should be computed.
     * @param set The set of trainingdata the scale should be fitted on.
     */
    public void initializeFactor(Network net, DataSet set){
        //check if already initialized
        if(!initialized){
            //initialize maximal value
            double maxOutValue = 0;
            double maxInValue = 0;
            
            //find displayable range for values
            double[] range = this.findRange(net);
            
            //find max value for output in training- and testpatterns and raise by 20 percent
            LinkedList<Pattern> Patterns = set.getPatterns();
            
            //Trainingpatterns
            for (Pattern pattern : Patterns) {
                for (double output : pattern.getOut()) {
                    if(FastMath.abs(output) > maxOutValue){
                        maxOutValue = output;
                        for(double input : pattern.getIn()){
                            if(FastMath.abs(input) > maxInValue){
                                maxInValue = input;
                            }
                        }
                    }
                }
            }
            
            maxOutValue = maxOutValue*1.2;
            maxInValue = maxInValue*1.2;

            
            //calculate factor
            double maxRange = 0;
            for(int i = 0; i < range.length; i++){
                if(FastMath.abs(range[i])>maxRange){
                    maxRange = range[i];
                }
            }
            
            this.scaleFactor[0] = maxRange/FastMath.abs(maxInValue); //Factor for inputdata
            this.scaleFactor[1] = maxRange/FastMath.abs(maxOutValue); //Factor for outputdata
                        
            initialized = true;
            
        }else{
            throw new UnsupportedOperationException("ScaleFactor has already been initialized!");
        }
        
    }
    
    /**
     * Applies scalefactor to a specified array of inputdata
     * @param values Array of values
     * @return Scaled Array of values
     */
    public double[] scaleInput(double[] values){
        double[] scaledValues = new double[values.length];

        for(int i = 0; i < values.length; i++){
            scaledValues[i] = values[i]*scaleFactor[0];
        }

        return scaledValues;
    }
    
    /**
     * Applies scalefactor to a specified array of outputdata
     * @param scaledValues Array of values
     * @return Array of values
     */
    public double[] unscaleOutput(double[] scaledValues){
        double[] values = new double[scaledValues.length];
        
        for(int i = 0; i < values.length; i++){
            values[i] = scaledValues[i]/scaleFactor[1];
        }
        
        return values;
    }
    
    /**
     * A Method for finding the displayable range of the outputdata from network.
     * @param net Network for which the range shall be computed
     * @return Double-array with maximal and minimal value for scaling
     */
    private double[] findRange(Network net){
    
        double[] range = new double[2];
        
        //get activityfunction of outputlayer and compute range
        ScalarFct activityFunction = net.getOutputNeurons().getFirst().getActivityFunction();
        
        range[0] = activityFunction.getValue(Double.MIN_VALUE);
        range[1] = activityFunction.getValue(Double.MAX_VALUE);
        
        return range;
    }  
    
    //Setter and Getter

    public double[] getScaleFactor() {
        return scaleFactor;
    }

    public boolean isInitialized() {
        return initialized;
    }
    
    
    
    
    
}
