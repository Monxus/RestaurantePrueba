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
public class Cliente implements Runnable {

    private final Mesa mesa;

    private int tiempoComer;
    private int tiempoDescanso;

    private String estado = "";
    private boolean pausa = true;

    /* CONSTRUCTOR ---------------------------------------------------------- */
    public Cliente(Mesa m) {
        this.mesa = m;
        this.generarTiempos();

    }

    /* GETTERS Y SETTERS ---------------------------------------------------- */
    public String getEstado() {
        return estado;
    }

    public int getTiempoComer() {
        return tiempoComer;
    }

    public int getTiempoDescanso() {
        return tiempoDescanso;
    }

    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }

    /* METODOS PRIVADOS ----------------------------------------------------- */
    //Come una hamburguesa en el tiempo de comer
    private void comer() {
        try {
            this.estado = "Comiendo";
            Thread.sleep(this.tiempoComer);
        } catch (InterruptedException ex) {
        }
    }

    //Descansa el tiempo de descanso establecido
    private void decansar() {
        try {
            this.estado = "Descansando";
            Thread.sleep(this.tiempoDescanso);
        } catch (InterruptedException ex) {
        }
    }

    //Genera los tiempos de descanso y comida
    private void generarTiempos() {
        int comerSegundos = (int) Math.floor(Math.random() * 15 + 1);
        this.tiempoComer = comerSegundos * 1000;

        int descansoSegundos = (int) Math.floor(Math.random() * 60 + 1);
        this.tiempoDescanso = descansoSegundos * 1000;
    }

    /* THREAD --------------------------------------------------------------- */
    @Override
    public void run() {
        while (!this.pausa) {
            this.estado = "Esperando";
            this.mesa.comerHamburguesa();
            this.comer();
            this.decansar();
        }
    }

}
