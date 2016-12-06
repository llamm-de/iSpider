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
package network.Training;

import java.util.LinkedList;
import java.util.Random;

/**
 * Class for a set of trainingpatterns
 * @author LammLukas
 */
public class TrainingSet {
    
    //Attributes
    /**
     * List of trainingspatterns
     */
    public LinkedList<TrainingPattern> patterns;
    
    /**
     * Generator for random numbers
     */
    Random random = new Random();

    /**
     * Empty Constructor
     */
    public TrainingSet() {
        this.patterns = new LinkedList<>();
    }

    /**
     * Constructor with predefines patterns
     * @param patterns 
     */
    public TrainingSet(LinkedList<TrainingPattern> patterns) {
        this.patterns = patterns;
    }
    
    /**
     * Gets random pattern from set
     * @return 
     */
    public TrainingPattern getRandom(){
        //Get size of set and generate random number in range
        int numPatterns = patterns.size();
        int index = random.nextInt(numPatterns);
        
        return patterns.get(index);
    }
    
    
    /**
     * Adds pattern to set
     * @param pattern 
     */
    public void addPattern(TrainingPattern pattern){
        this.patterns.add(pattern);
    }
    
    /**
     * Removes pattern from set
     * @param pattern 
     */
    public void remPattern(TrainingPattern pattern){
        this.patterns.remove(pattern);
    }
    
    
}
