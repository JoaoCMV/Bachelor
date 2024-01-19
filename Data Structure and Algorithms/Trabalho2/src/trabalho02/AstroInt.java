/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho02;

/**
 *
 * @author Joao
 */
public interface AstroInt {
    
    AstroInt add(AstroInt x);
    AstroInt sub(AstroInt x);
    AstroInt mult(AstroInt x);
    AstroInt div(AstroInt x);
    AstroInt mod(AstroInt x);
    public String toString();
    
}
