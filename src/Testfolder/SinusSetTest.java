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
package Testfolder;

import java.util.Arrays;
import network.Training.*;
import tools.FunctionGraph;
import java.util.LinkedList;

/**
 * Short test for sinusset
 * @author lukas
 */
public class SinusSetTest {
    
    public static void main(String[] args) {
        double[] range = new double[2];
        range[0] = -10;
        range[1] = 10;
        int numPatterns = 100;
        
        // Create set
        TrainingSet sinSet = SinusSet.createSet(numPatterns, range);
        
        //Create graph
        FunctionGraph graph = new FunctionGraph("SinusTest","x","y");
        
        //extract data from set for plotting
        LinkedList<TrainingPattern> patterns = sinSet.getTrainingPatterns();
        int i = 0;
        double[] xVal = new double[numPatterns];
        double[] yVal = new double[numPatterns];
        for (TrainingPattern pattern : patterns) {
            xVal[i] = pattern.p[0];
            yVal[i] = pattern.t[0];
            
            i++;
        }
        
        //plot data
        graph.addOrUpdateSeries(xVal, yVal, "sinusPattern");
        graph.plot(500, 500);
        
        System.out.println(Arrays.toString(xVal));
        System.out.println(Arrays.toString(yVal));
        
        
    }
    
}
