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

import java.util.HashMap;
import network.Neurons.Neuron;

/**
 *
 * @author LammLukas
 */
public class HashTest {
    
    public static void main(String[] args) {
        
        HashMap<Neuron, String> map = new HashMap<>();
        
        Neuron n1 = new Neuron();
        Neuron n2 = new Neuron();
        Neuron n3 = new Neuron();
        
        String s1 = "Neuron1";
        String s2 = "Neuron2";
        String s3 = "Neuron3";
        
        map.put(n1, s1);
        map.put(n2, s2);
        map.put(n3, s3);
        map.put(n2, s1);
        
        System.out.println(map.get(n1));
        System.out.println(map.get(n3));
        System.out.println(map.get(n2));
        
    }
        
    
}
