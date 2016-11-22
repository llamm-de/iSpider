/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Neurons;

import tools.Function.*;
import network.Connections.*;
import java.util.ArrayList;

/**
 *
 * @author LammLukas
 */
public class Neuron {
   
    // Attributes
    
    /**
     * Identificationsnumber for neuron.
     */
    private final int id;
    
    /**
     * Input and outputlinks.
     */
    private ArrayList<Synapse> inSynapses;
    private ArrayList<Synapse> outSynapses;
    
    /**
     * Activityfunction.
     */
    private ScalarFunction1d activityFunction;
    
    /**
     * Total input for the neuron. Calculated from all linkinputs.
     */
    protected double totalInput = 0;
    
    /**
     * Output of neuron.
     */
    protected double output = 0;
    
    /**
     * Constructor
     * @param id Identificationnumber for neuron
     */
    public Neuron(int id) {
        this.id = id;
        this.activityFunction = new SigmoidFct();
        
        // empty array to be filled with connections
        this.inSynapses = new ArrayList<>();
        this.outSynapses = new ArrayList<>();      
    }
    
    /**
     * Adds new input synapse to field
     */
    public void addInputSynapse(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Adds new output synapse to field
     */
    public void addOutputSynapse(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
