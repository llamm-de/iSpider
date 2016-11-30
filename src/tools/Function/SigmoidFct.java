/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.Function;

import java.lang.Math;

/**
 * Class for computation of a sigmoidfunction including its derivative
 * @author LammLukas
 */
public class SigmoidFct extends DifferentiableFct implements ScalarFct{
    
    // Attributes
    /**
     * Coefficient for sigmoid function
     */
    public double k;
    
    /**
     * Constructor for given k
     * @param k Coefficient
     */
    public SigmoidFct(double k) {
        this.k = k;
    }
    
    /**
     * General constructor.
     * 
     */
    public SigmoidFct(){
        this.k = 1;
    }
    
    /**
     * Computes value of function at given value
     * @param value given value
     * @return value of function
     */    
    @Override
    public double getValue(double value){
        return (1/(1 + Math.exp(-value*k)));
    }
    
    /**
     * Computes derivative of function at given value
     * @param value given value
     * @return derivative at given value
     */
    @Override
    public double getDerivative(double value) {
        return this.getValue(value)*(1-this.getValue(value));
    }

    /**
     * Getter for k
     * @return k
     */
    public double getK() {
        return k;
    }

    /**
     * Setter for k
     * @param k 
     */
    public void setK(double k) {
        this.k = k;
    }
    
    
}
