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

import java.util.Collections;

/**
 * Class for a set of trainingpatterns
 * @author LammLukas
 */
public class TrainingSet {
    
    //Attributes
    /**
     * List of trainingspatterns
     */
    private LinkedList<TrainingPattern> trainingPatterns;
    
    /**
     * List of testpatterns
     */
    private LinkedList<TrainingPattern> testPatterns;
     
    /**
     * Empty Constructor
     */
    public TrainingSet() {
        this.trainingPatterns = new LinkedList<>();
        this.testPatterns = new LinkedList<>();
    }

    /**
     * Constructor with predefines trainingpatterns
     * and empty testpatterns
     * @param trainingPatterns 
     */
    public TrainingSet(LinkedList<TrainingPattern> trainingPatterns) {
        this.trainingPatterns = trainingPatterns;
        this.testPatterns = new LinkedList<>();
    }
    
    /**
     * Constructor with predefined patterns
     * @param trainingPatterns
     * @param testPatterns 
     */
    public TrainingSet(LinkedList<TrainingPattern> trainingPatterns, LinkedList<TrainingPattern> testPatterns){
        this.trainingPatterns = trainingPatterns;
        this.testPatterns = testPatterns;
    }
    
      
    /**
     * Shuffles patterns
     */
    public void shufflePatterns(){
        Collections.shuffle(trainingPatterns);
    }
    
    /**
     * Adds pattern to set
     * @param pattern 
     */
    public void addTrainingPattern(TrainingPattern pattern){
        this.trainingPatterns.add(pattern);
    }
    
    /**
     * Removes pattern from set
     * @param pattern 
     */
    public void remTrainingPattern(TrainingPattern pattern){
        this.trainingPatterns.remove(pattern);
    }
    
        /**
     * Adds pattern to set
     * @param pattern 
     */
    public void addTestPattern(TrainingPattern pattern){
        this.testPatterns.add(pattern);
    }
    
    /**
     * Removes pattern from set
     * @param pattern 
     */
    public void remTestPattern(TrainingPattern pattern){
        this.testPatterns.remove(pattern);
    }
    

    //Getter and Setter
    public LinkedList<TrainingPattern> getTrainingPatterns() {
        return trainingPatterns;
    }

    public LinkedList<TrainingPattern> getTestPatterns() {
        return testPatterns;
    }
    
    
    
    
}
