/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.Function;

/**
 *
 * @author LammLukas
 */
public abstract class DifferentiableFun implements ScalarFunction1d {
    
    /**
     * Computes value if function at given point 
     * @param value given value
     * @return 
     */
    public abstract double getValue(double value);
    
    /**
     * Computes the derivative of the function at given value
     * @param value given value
     * @return 
     */
    public abstract double getDerivative(double value);
}
