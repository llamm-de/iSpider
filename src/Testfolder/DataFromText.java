/*
 * The MIT License
 *
 * Copyright 2016 lukas.
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.util.ArrayList;

/**
 *
 * @author lukas
 */
public class DataFromText {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        //Select inputfile
        final JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null);
        File file = null;
        if(response == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
        }else{
            System.out.println("No File Chosen!");
        }
        //Create inputstream
        BufferedReader inStream = new BufferedReader(new FileReader(file));
        
        //read input and store
        ArrayList<double[]> rowList = new ArrayList<>();
        String line;
        while((line = inStream.readLine()) != null){
            String[] strArray;
            strArray = line.split(" ");               //split row at whitespace
            
            //convert into double and store in list
            double[] rowArray = new double[strArray.length];
            int j = 0;
            for (String string : strArray) {
                rowArray[j] = Double.parseDouble(string);
                j++;
            }
            rowList.add(rowArray);
        }
       
        System.out.println(rowList);
    }
    
}
