/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Layer;

import network.Neurons.*;
/**
 *
 * @author LammLukas
 */
public class HiOutLayer extends Layer{
    
    @Override
    public void addNeurons(int numNeurons) {
        for (int i = 0; i < numNeurons; i++) {
            this.neurons.add(new HiOutNeuron());
        }
                
    }

    @Override
    public void removeNeurons() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
}
