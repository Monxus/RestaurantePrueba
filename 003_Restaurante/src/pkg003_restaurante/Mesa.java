/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg003_restaurante;

/**
 *
 * @author Ramon
 */
public class Mesa{
    
    private final int maxHamburguesas;
    
    private int hamburguesas;
    private int totalServidas;
    private int totalComidas;

    public int getMaxHamburguesas() {
        return maxHamburguesas;
    }
    
    /* CONSTRUCTOR ---------------------------------------------------------- */
    public Mesa(){        
        this.maxHamburguesas=50;
        this.hamburguesas=0;
        this.totalComidas=0;
        this.totalServidas=0;
    }
  
    /* GETTERS Y SETTERS ---------------------------------------------------- */  
    public int getHamburguesas() {
        return hamburguesas;
    }

    public int getTotalServidas() {
        return totalServidas;
    }

    public int getTotalComidas() {
        return totalComidas;
    }
       
    /* METODOS PUBLICOS ----------------------------------------------------- */    
    //Quita una hamburguesa de la mesa
    public synchronized void comerHamburguesa(){
        while (this.hamburguesas<=0) {
            try { 
                wait();
            } catch (InterruptedException e) {}
        }
        this.hamburguesas--;
        this.totalComidas++;
        notifyAll();
    }
    
    //Pone una hamburguesa en la mesa
    public synchronized void ponerHamburguesa(){
        while (this.hamburguesas>=this.maxHamburguesas) {
            try { 
                wait();
            } catch (InterruptedException e) {}
        }
        this.hamburguesas++;
        this.totalServidas++;
        notifyAll();
    }
    
}
