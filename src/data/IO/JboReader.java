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
package data.IO;

import data.DataSet;
import helper.NdTimeSeries;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;


/**
 * This class provides a reader for .jbo files
 * @author lukas
 */
public abstract class JboReader implements Reader<NdTimeSeries>{

    /**
     * Reads file and returns a set of trainingData
     * @param file 
     * @return Set of TrainingData
     * @throws java.io.FileNotFoundException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @Override
    public NdTimeSeries readFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        //get path of file to read from
        String filePath = file.getAbsolutePath();
        
        //Readfile and create NdTimeSeries
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object o = ois.readObject();
        if (o instanceof NdTimeSeries) {
            return (NdTimeSeries)o;
        }
        return null;
                      
    }
    
    /**
     * Abstract method for creating a trainingset out of a time series.
     * @param timeSeries given time series
     * @param timeRange given range of time
     * @return 
     */
    public abstract DataSet createTrainingSet(NdTimeSeries timeSeries, long[] timeRange);
    
    /**
     * Abstract method for creating inputdata out of a time series
     * @param timeSeries
     * @return 
     */
    public abstract HashMap createInputData(NdTimeSeries timeSeries);
    
    
    
    
    
}
