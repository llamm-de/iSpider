/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ispider;

import java.util.ArrayList;
import network.Connections.*;
import network.Layer.*;
import tools.*;

/**
 *
 * @author LammLukas
 */
public class ISpider {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
      
     Layer layer = new Layer(1);
     
     layer.addNeurons(2);
     
     System.out.println(layer.getNeurons());
    
}
}
