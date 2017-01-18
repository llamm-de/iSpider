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
import java.util.LinkedList;
import java.util.Random;

/**
 * Ein Beispiel für das Problem
 * 
 * Ich möchte mehrere Arrays mit pseudo-random Werten von typ double füllen und 
 * diese Arrays anschließend in einer Liste ablegen.
 * Wie im unteren Beispiel gezeigt, entsteht nun allerdings folgendes Problem.
 * Da lediglich eine referenz auf den speicherplatz in der liste abgelegt wird, 
 * erhalte ich in jedem listeneitrag das selbe objekt, wodurch die schleife sinnlos
 * wird, da lediglich der zuletzt berechnete Wert abgespeichert wird.
 * Auch wenn ich in der schleife ein neues objekt erzeuge, entsteht dieser fehler.
 * 
 * @author lukas
 */
public class MinimalExample {
    
    public static void main(String[] args) {
        
        //number of arrays to be stored in listobject
        int numArrays = 5;
        
        //number of entries in double array
        int numEntries = 1;
        
//        //create double array
//        double[] array = new double[numEntries];
        
        //create listobject
        LinkedList<double[]> arrayCollection = new LinkedList<>();
        
        //create new pseudorandom generator
        Random random = new Random();
        
        //Loop over all possible arrays
        for(int i = 0; i < numArrays; i++){
            
            //create double array
            double[] array = new double[numEntries];
            
            //create random number for array
            array[0] = random.nextDouble();
            //add array to List
            arrayCollection.add(array);
        }
        
        System.out.println(arrayCollection.getFirst());
        System.out.println(arrayCollection.getLast());
        System.out.println(Arrays.toString(arrayCollection.getFirst()));
        System.out.println(Arrays.toString(arrayCollection.getLast()));
    }
    
}
