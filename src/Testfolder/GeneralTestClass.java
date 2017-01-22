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
package Testfolder;

import network.Network.*;
import tools.*;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author LammLukas
 */
public class GeneralTestClass {
    
    public static void main(String[] args) {
        
        double d1 = 10;
        double d2 = 112;
        double d3 = 3293;
        
        ArrayList<Double> list = new ArrayList<>();
        list.add(d3);
        list.add(d2);
        list.add(d1);
        
        double[] array = ToolClass.listToArray(list);
        
        System.out.println(list);
        System.out.println(Arrays.toString(array));
        
        double[] intArray = ToolClass.makeRangeArray(0, 10, 1);
        System.out.println(Arrays.toString(intArray));
        
    }
    
}
