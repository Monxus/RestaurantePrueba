/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg003_restaurante;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Ramon
 */
public class Restaurante extends Canvas implements Runnable {

    private Mesa mesa;
    private Cliente[] cliente;
    private Cocinero[] cocinero;
    private boolean pausa = true;

    

    /* CONSTRUCTOR ---------------------------------------------------------- */
    public Restaurante(Mesa m, Cocinero[] co, Cliente[] cl) {
        this.mesa = m;
        this.cocinero = co;
        this.cliente = cl;

        this.setBackground(Color.LIGHT_GRAY);
        this.setSize(800, 600);

    }

    /* GETTERS Y SETTERS ---------------------------------------------------- */
    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public void setCliente(Cliente[] cliente) {
        this.cliente = cliente;
    }

    public void setCocinero(Cocinero[] cocinero) {
        this.cocinero = cocinero;
    }
       
    /* METODOS PUBLICOS ----------------------------------------------------- */
    @Override
    public void paint(Graphics g) {
        this.pintarBase(g);
        this.pintarElementos(g);

    }
    
    public void paint() {
        this.paint(this.getGraphics());

    }
    
    /* METODOS PRIVADOS ----------------------------------------------------- */
    //Pinta el fondo del canvas
    private void pintarBase(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(150, 125, 500, 250);

    }
    
    //Pinta los cocineros según su estado
    private void pintarCocineros(Graphics g) {
        for (int i = 0; i < cocinero.length; i++) {
            BufferedImage img = null;
            String url = null;
            switch (this.cocinero[i].getEstado()) {
                case "Cocinando":
                    url = "src/imagenes/cocineroCocinando.png";
                    break;
                case "Sirviendo":
                    url = "src/imagenes/cocineroEsperando.png";
                    break;
                default:
                    url = "src/imagenes/cocinero.png";
                    break;
            }
            try {
                img = ImageIO.read(new File(url));
                g.drawImage(img, 25 + i * 75, 25, 75, 75,Color.LIGHT_GRAY, this);
            } catch (IOException e) {
                System.out.println("Error al pintar cocinero");
            }

        }
    }

    //Pinta los clientes según su estado
    private void pintarClientes(Graphics g) {
        for (int i = 0; i < this.cliente.length; i++) {
            BufferedImage img = null;
            String url = null;
            switch (this.cliente[i].getEstado()) {
                case "Comiendo":
                    url = "src/imagenes/clienteComiendo.png";
                    break;
                case "Descansando":
                    url = "src/imagenes/clienteDescansando.png";
                    break;
                case "Esperando":
                    url = "src/imagenes/clienteEsperando.png";
                    break;
                default:
                    url = "src/imagenes/cliente.png";
                    break;
            }
            try {
                int y = 400 + ((i / 20) * 35);
                int x = 50 + ((i % 20) * 35);
                img = ImageIO.read(new File(url));
                g.drawImage(img, x, y, 35, 35,Color.LIGHT_GRAY, this);
            } catch (IOException e) {
                System.out.println("Error al pintar cliente");
            }

        }
    }
    
    //Pinta los cocineros, clientes y la mesa
    private void pintarElementos(Graphics g) {
        this.pintarCocineros(g);
        this.pintarMesa(g);
        this.pintarClientes(g);
    }

    //Pinta las hamburguesas de la mesa
    private void pintarMesa(Graphics g) {
        for (int i = 0; i < this.mesa.getMaxHamburguesas(); i++) {
            BufferedImage img = null;
            String url = null;
            if (i < this.mesa.getHamburguesas()) {
                url = "src/imagenes/platoBurguer.png";
            } else {
                url = "src/imagenes/plato.png";
            }
            try {
                int y = 125 + ((i / 10) * 50);
                int x = 150 + ((i % 10) * 50);
                img = ImageIO.read(new File(url));
                g.drawImage(img, x, y, 50, 50,Color.PINK, this);
            } catch (IOException e) {
                System.out.println("Error al pintar hamburguesa");
            }

        }

    }

    /* THREAD --------------------------------------------------------------- */
    @Override
    public void run() {
        while (!this.pausa) {           
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {               
            }  
            this.pintarElementos(this.getGraphics());
        }
    }

}
