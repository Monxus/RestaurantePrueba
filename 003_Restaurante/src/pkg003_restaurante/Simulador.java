/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg003_restaurante;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author Ramon
 */
public class Simulador extends JFrame implements ActionListener, Runnable {

    private Restaurante restaurante;
    private Mesa mesa;
    private Cliente[] cliente;
    private Cocinero[] cocinero;

    private boolean pausa = true;

    private JButton bPlay;
    private JButton bPause;
    private JButton bRestart;
    private JTable jtCocina;
    private JTable jtComedor;

    /* CONSTRUCTOR ---------------------------------------------------------- */
    public Simulador() {
        this.generarObjetos();
        this.crearVentana();
        this.restartEstaditicas();
    }

    /* METODOS PUBLICOS ----------------------------------------------------- */
    public static void main(String[] args) {
        Simulador s = new Simulador();
    }

    /* METODOS PRIVADOS ----------------------------------------------------- */
    
    //Actualiza estadísticas cocina
    private void actualizarEstadisticasCocina() {

        TableModel model = this.jtCocina.getModel();
        model.setValueAt(10, 0, 1);
        model.setValueAt(this.mesa.getHamburguesas(), 1, 1);
        model.setValueAt(this.mesa.getTotalServidas(), 2, 1);
        model.setValueAt(this.calcularTiempoCocinando(), 3, 1);

    }

    //Actualiza estadísticas comdor
    private void actualizarEstadisticasComedor() {
        TableModel model = this.jtComedor.getModel();
        model.setValueAt(100, 0, 1);
        model.setValueAt(this.mesa.getTotalComidas(), 1, 1);
        model.setValueAt(this.calcularTiempoComiendo(), 2, 1);
        model.setValueAt(this.calcularTiempoDescansando(), 3, 1);

    }

    //Añade los botones de control
    private void addControles(Container panel, GridBagConstraints c) {
        //Crear botones y añadirles los actionlistener
        this.bPlay = new JButton("PLAY");
        this.bPause = new JButton("PAUSE");
        this.bRestart = new JButton("RESTART");

        this.bPlay.addActionListener(this);
        this.bPause.addActionListener(this);
        this.bRestart.addActionListener(this);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        panel.add(this.bPlay, c);

        c.gridx = 1;
        panel.add(this.bPause, c);

        c.gridx = 2;
        panel.add(this.bRestart, c);

    }

    //Añade el canvas del restaurante
    private void addRestaurante(Container panel, GridBagConstraints c) {
        this.restaurante = new Restaurante(this.mesa, this.cocinero, this.cliente);
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 7;
        panel.add(this.restaurante, c);
    }

    //Añade el panel de estadísticas de la cocina
    private void addEstadisticasCocina(Container panel, GridBagConstraints c) {
        JLabel label = new JLabel("COCINA");
        label.setFont(new Font("Arial Black", 0, 14));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        panel.add(label, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.fill = MAXIMIZED_HORIZ;
        this.jtCocina = this.crearTableCocina();
        panel.add(this.jtCocina.getTableHeader(), c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.fill = MAXIMIZED_HORIZ;
        panel.add(this.jtCocina, c);
    }

    //Añade el panel de estadísticas del comedor
    private void addEstadisticasComedor(Container panel, GridBagConstraints c) {
        JLabel label = new JLabel("COMEDOR");
        label.setFont(new Font("Arial Black", 0, 14));

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.fill = NORMAL;
        panel.add(label, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 3;
        c.fill = MAXIMIZED_HORIZ;
        this.jtComedor = this.crearTableComedor();
        panel.add(this.jtComedor.getTableHeader(), c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 3;
        c.fill = MAXIMIZED_HORIZ;
        panel.add(this.jtComedor, c);
    }

    //Añade los componentes a la ventana
    private void addUIComponentes(Container panel) {
        GridBagConstraints c;
        c = new GridBagConstraints();

        this.addEstadisticasCocina(panel, c);
        this.addEstadisticasComedor(panel, c);
        this.addControles(panel, c);
        this.addRestaurante(panel, c);
    }

    //Calcula los tiempos para las estaditicas
    private int calcularTiempoCocinando() {
        int t = 0;
        for (int i = 0; i < this.cocinero.length; i++) {
            t += this.cocinero[i].getTiempoCocinar() / 1000;
        }
        t = t / 10;
        return t;
    }

    //Calcula los tiempos para las estaditicas
    private int calcularTiempoComiendo() {
        int t = 0;
        for (int i = 0; i < this.cliente.length; i++) {
            t += this.cliente[i].getTiempoComer() / 1000;
        }
        t = t / 100;
        return t;
    }

    //Calcula los tiempos para las estaditicas
    private int calcularTiempoDescansando() {
        int t = 0;
        for (int i = 0; i < this.cliente.length; i++) {
            t += this.cliente[i].getTiempoDescanso() / 1000;
        }
        t = t / 100;
        return t;
    }

    //Crear JTable cocina
    private JTable crearTableCocina() {
        String[] columnHeaders = {"Descripción", "Valor"};
        Object[][] dataRows = {
            {"Cocineros", ""}, {"Hamburguesas en mesa", ""},
            {"Total Hamburguesas Servidas", ""}, {"Tiempo medio cocinado (seg)", ""}};

        JTable table = new JTable(dataRows, columnHeaders);
        table.getColumnModel().getColumn(1).setMaxWidth(40);
        return table;

    }

    //Crear JTable comedor
    private JTable crearTableComedor() {
        String[] columnHeaders = {"Descripción", "Valor"};
        Object[][] dataRows = {
            {"Clientes", ""}, {"Total Hamburguesas Comidas", ""},
            {"Tiempo medio comiendo (seg)", ""}, {"Tiempo medio descanso (seg)", ""}};

        JTable table = new JTable(dataRows, columnHeaders);
        table.getColumnModel().getColumn(1).setMaxWidth(40);
        return table;
    }

    //Crea la interfaz
    private void crearVentana() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("2do DAM - Simulador Restaurante");
        this.setLayout(new GridBagLayout());

        this.addUIComponentes(getContentPane());

        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    //Crea los objetos del simulador
    private void generarObjetos() {
        this.mesa = new Mesa();

        this.cocinero = new Cocinero[10];
        for (int i = 0; i < this.cocinero.length; i++) {
            this.cocinero[i] = new Cocinero(this.mesa);
        }

        this.cliente = new Cliente[100];
        for (int i = 0; i < this.cliente.length; i++) {
            this.cliente[i] = new Cliente(this.mesa);
        }

    }

    //Inicia los threads y la actualización de estadisticas
    public void iniciar() {
        Thread t = new Thread(this.restaurante);
        t.start();

        for (int i = 0; i < this.cocinero.length; i++) {
            t = new Thread(this.cocinero[i]);
            t.start();
        }

        for (int i = 0; i < this.cliente.length; i++) {
            t = new Thread(this.cliente[i]);
            t.start();
        }

        t = new Thread(this);
        t.start();

    }

    //Pausa todos los threads
    private void pausar() {
        this.restaurante.setPausa(this.pausa);

        for (int i = 0; i < this.cocinero.length; i++) {
            this.cocinero[i].setPausa(this.pausa);
        }
        for (int i = 0; i < this.cliente.length; i++) {
            this.cliente[i].setPausa(this.pausa);
        }
    }

    //Reiniciar objetos restaurante
    private void restartRestaurante() {
        this.restaurante.setCliente(cliente);
        this.restaurante.setCocinero(cocinero);
        this.restaurante.setMesa(mesa);
        this.restaurante.paint();
    }

    //Reiniciar estadisticas
    private void restartEstaditicas() {
        TableModel model = this.jtCocina.getModel();
        model.setValueAt(10, 0, 1);
        model.setValueAt(0, 1, 1);
        model.setValueAt(0, 2, 1);
        model.setValueAt(0, 3, 1);

        model = this.jtComedor.getModel();
        model.setValueAt(100, 0, 1);
        model.setValueAt(0, 1, 1);
        model.setValueAt(0, 2, 1);
        model.setValueAt(0, 3, 1);
    }

    /* LISTENER ------------------------------------------------------------- */
    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        switch (str) {
            case "PLAY":
                System.out.println("Play");
                this.pausa = false;
                this.pausar();
                this.iniciar();
                break;
            case "PAUSE":
                System.out.println("Pause");
                this.pausa = true;
                this.pausar();
                break;
            case "RESTART":
                System.out.println("Restart");
                this.pausa = true;
                this.pausar();
                this.generarObjetos();
                this.restartRestaurante();
                this.restartEstaditicas();
                break;
            default:
                System.out.println("Acción no tratada");
        }
    }

    /* THREAD --------------------------------------------------------------- */
    @Override
    public void run() {
        while (!this.pausa) {
            this.actualizarEstadisticasCocina();
            this.actualizarEstadisticasComedor();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
    }

}
