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

import data.DataSet;
import data.IO.WindWaveReader;
import helper.NdTimeSeries;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author lukas
 */
public class ReaderTest {
    
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        
        WindWaveReader reader = new WindWaveReader();
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file to open...");
        //Open dialog and choose file
        int response = fileChooser.showOpenDialog(null);
        File file = null;
        if(response == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
        }else{
            System.out.println("No File Chosen!");
        }
                
        NdTimeSeries timeSeries = reader.readFile(file);
        long[] timeRange = new long[]{(long) 1.1018592E12, (long) 1.104555E12};
        DataSet set = reader.createTrainingSet(timeSeries, timeRange);
        
        System.out.println(set);
        
    }
}
