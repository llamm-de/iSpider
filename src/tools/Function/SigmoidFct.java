/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.Function;

import java.lang.Math;

/**
 *
 * @author LammLukas
 */
public class SigmoidFct implements ScalarFunction1d{
    
    
    @Override
    public double getValue(double input){
        return 0.5*(1+ Math.tanh(0.5*input));
    }
}
