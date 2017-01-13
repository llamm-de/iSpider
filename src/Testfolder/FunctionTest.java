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
package Testfolder;

import tools.Function.*;
import tools.*;

/**
 *
 * @author LammLukas
 */
public class FunctionTest {
    
    public static void main(String[] args) {
        
        //Initialize Plot
        FunctionGraph graph = new FunctionGraph("Functiontest","X","Y");
        double xMin = -10;
        double xMax = 10;
        double step = 1;
        
        //Identity
        IdentityFct identity = new IdentityFct();
        graph.addOrUpdateSeries(identity, xMin, xMax, step,"identity");
        graph.plot(500,500);
        
        //Heavisidefunction
        HeavisideFct heaviside = new HeavisideFct();
        graph.addOrUpdateSeries(heaviside, xMin, xMax, step,"heaviside");
        graph.plot(500,500);
        
        //Sigmoidfunction
        SigmoidFct sigmoid = new SigmoidFct();
        graph.addOrUpdateSeries(sigmoid, xMin, xMax, step,"sigmoid");
        graph.plot(500,500);
        
        //Hyperbolic tangent
        HyperbolicTangentFct tanh = new HyperbolicTangentFct();
        graph.addOrUpdateSeries(tanh, xMin, xMax, step,"tanh");
        graph.plot(500,500);
        
        //Sinus
        SinusFct sinus = new SinusFct();
        graph.addOrUpdateSeries(sinus, xMin, xMax, step,"sinus");
        graph.plot(500,500);
    }
    
}
