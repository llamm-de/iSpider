/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Connections;

import network.Neurons.Neuron;

/**
 *
 * @author LammLukas
 */
public class Connection {
    
    // Attributes
    int id;
    double weight;
    Neuron[] connectedNeur;
    
       
    // Constructor
    public Connection(int id, double weight, Neuron[] connectedNeur) {
        this.id = id;
        this.weight = weight;
        this.connectedNeur = connectedNeur;
    }
    
    // Methods
    public double compWeightedRes(double input){
        return input*weight;
    }
    
    /*
    public double passData(double input){
                       
        return ;
    }
    */
    
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    
    
    
    
}
