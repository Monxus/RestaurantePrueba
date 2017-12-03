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
public class Cocinero implements Runnable {

    private final Mesa mesa;
    
    private int tiempoCocinar;
    private int hamurguesasCocinadas;
    
    private String estado = "";
    private boolean pausa = true;

    /* CONSTRUCTOR ---------------------------------------------------------- */   
    public Cocinero(Mesa m) {
        this.mesa = m;
        this.generarTiempos();
        this.hamurguesasCocinadas = 0;
    }
    
    /* GETTERS Y SETTERS ---------------------------------------------------- */    
    public String getEstado() {
        return estado;
    }

    public int getTiempoCocinar() {
        return tiempoCocinar;
    }
    
    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }
   
    /* METODOS PRIVADOS ----------------------------------------------------- */    
    //Hace 10 hamburguesas en el tiempo de ocinado.
    private void cocinar() {
        try {
            this.estado = "Cocinando";
            Thread.sleep(this.tiempoCocinar);
        } catch (InterruptedException ex) {
        }
        this.hamurguesasCocinadas = 10;

    }

    //Genera tiempo cocionado
    private void generarTiempos() {
        int cocinarSegundos = (int) Math.floor(Math.random() * 6 + 5);
        this.tiempoCocinar = cocinarSegundos * 1000;
    }
    
    /* THREAD --------------------------------------------------------------- */
    @Override
    public void run() {
        while (!this.pausa) {
            this.cocinar();
            while (this.hamurguesasCocinadas > 0) {
                this.estado = "Sirviendo";
                this.mesa.ponerHamburguesa();
                this.hamurguesasCocinadas--;
            }
        }
    }

}
