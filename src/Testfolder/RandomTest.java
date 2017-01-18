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

import java.util.Random;
import java.util.LinkedList;
/**
 *
 * @author lukas
 */
public class RandomTest {
    
    public static void main(String[] args) {
        Random random = new Random();
        int iterator = 10;
        double number;
        double rangeMin =  -10;
        double rangeMax = 10;
        LinkedList<Double> list = new LinkedList<>();
        
        for (int i = 0; i < iterator; i++) {
            number = rangeMin + (rangeMax - rangeMin)*random.nextDouble();
            number = number * 100;
            number = Math.round(number);
            number = number / 100;
            list.add(number);
            System.out.println(number);
        }
        
        System.out.println(list);
        
        
    }
    
}
