package Codigo;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jp
 */
public class ventanaBuscaminas extends javax.swing.JFrame {

    int filas = 15;
    int columnas = 20;

    Boton[][] arrayBotones = new Boton[filas][columnas];

    /**
     * Creates new form ventanaBuscaminas
     */
    public ventanaBuscaminas() {
        initComponents();
        setSize(800, 600);
        getContentPane().setLayout(new GridLayout(filas, columnas));
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Boton boton = new Boton(i, j);
                getContentPane().add(boton);
                arrayBotones[i][j] = boton;
                boton.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        botonPulsado(evt);
                    }
                });

            }
        }
        ponMinas(30);
        cuentaMinas();
    }

    private void botonPulsado(MouseEvent e) {
        Boton miBoton = (Boton) e.getComponent();
        if (e.getButton() == MouseEvent.BUTTON3 && miBoton.isEnabled()) {
            miBoton.setText("?");
        }
        if (e.getButton() == MouseEvent.BUTTON1 && miBoton.getText().equals("")) {
            if (miBoton.getMina() == 1) {
                miBoton.setText("M");
            } else if (miBoton.getNumeroMinasAlrededor() == 0) {
                miBoton.setFocusPainted(false);
                elimina0(miBoton);
            } else {
                miBoton.setText(String.valueOf(miBoton.getNumeroMinasAlrededor()));
                miBoton.setEnabled(false);
                miBoton.setFocusPainted(false);
            }
            //
            if (e.getButton() == MouseEvent.BUTTON1 && miBoton.getText().equals("M")) {

                inutiiil();
                jDialog1.setVisible(true);
                jDialog1.setSize(445, 200);
//                
//                for (int  i=0; i<filas; i++){
//                    for(int j=0; j<columnas;j++){
//                        if(arrayBotones[i][j].getMina() != 1 ){
//                            arrayBotones[i][j].setNumeroMinasAlrededor(miBoton.getNumeroMinasAlrededor());
//                        }
//                    else {
//                        arrayBotones[i][j].setText("M");
//                    }
//                    arrayBotones[i][j].setEnabled(false);  
//                    }
//                }
            }
        }
        //

    }
    
    
    private void inutiiil() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (arrayBotones[i][j].getMina() == 0) {
                    if (arrayBotones[i][j].getNumeroMinasAlrededor() != 0) {
                        arrayBotones[i][j].setText(String.valueOf(arrayBotones[i][j].getNumeroMinasAlrededor()));
                    }
                } else {
                    arrayBotones[i][j].setText("M");
                }
                arrayBotones[i][j].setEnabled(false);
            }
        }
    }

    private void elimina0(Boton boton) {

        if (boton.getNumeroMinasAlrededor() == 0) {
            boton.setEnabled(false);
            for (int k = -1; k < 2; k++) {
                for (int m = -1; m < 2; m++) {
                    if ((boton.getI() + k >= 0) && (boton.getJ() + m >= 0) && (boton.getI() + k < filas) && (boton.getJ() + m < columnas)) {
                        if (arrayBotones[boton.getI() + k][boton.getJ() + m].isEnabled()) {
                            if (arrayBotones[boton.getI() + k][boton.getJ() + m].getNumeroMinasAlrededor() == 0) {
                                arrayBotones[boton.getI() + k][boton.getJ() + m].setEnabled(false);
                                elimina0(arrayBotones[boton.getI() + k][boton.getJ() + m]);
                            } 
                            else if (arrayBotones[boton.getI() + k][boton.getJ() + m].getNumeroMinasAlrededor() != 0) {
                                arrayBotones[boton.getI() + k][boton.getJ() + m].setEnabled(false);
                                arrayBotones[boton.getI() + k][boton.getJ() + m].setText(
                                        String.valueOf(arrayBotones[boton.getI() + k][boton.getJ() + m].getNumeroMinasAlrededor()));
                            }
                        }
                    }
                }
            }
        }

    }

    private void ponMinas(int numeroMinas) {
        Random r = new Random();
        for (int i = 0; i < numeroMinas; i++) {
            int f = r.nextInt(filas);
            int c = r.nextInt(columnas);
            //TODO hay q hacer una version q chequee si en las casillas seleccionadas
            //ya hay uan mina, pq en ese caso tiene que buscar otra
            while (arrayBotones[f][c].getMina() == 1) {
                f = r.nextInt(filas);
                c = r.nextInt(columnas);
            }
            arrayBotones[f][c].setMina(1);
            arrayBotones[f][c].setText("M");
        }
    }

    //cuenta minas es un metodo q para cada boton calcula el numero de minas que hay alrededor
    private void cuentaMinas() {

        int minas = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                for (int k = -1; k < 2; k++) {
                    for (int m = -1; m < 2; m++) {
                        if ((i + k >= 0) && (j + m >= 0) && (i + k < filas) && (j + m < columnas)) {
                            minas = minas + arrayBotones[i + k][j + m].getMina();
                        }
                    }
                }
                arrayBotones[i][j].setNumeroMinasAlrededor(minas);

//                //TODO comentar la siguiente parte para que no aparezcan los numeros al iniciar la partida
//                
////                if(arrayBotones[i][j].isEnabled()){
////                    if(arrayBotones[i][j].getMina() == 0){
////                        arrayBotones[i][j].setText(String.valueOf(minas)); 
////                        
////                    }
////                }
//                //TODO lo de arriba es lo que hay que comentar
                if (arrayBotones[i][j].getMina() == 1) {
                    arrayBotones[i][j].setText("");
                }
                arrayBotones[i][j].setNumeroMinasAlrededor(minas);
                minas = 0;
                if ((arrayBotones[i][j].getNumeroMinasAlrededor() < 0) && (arrayBotones[i][j].getMina() >= 0)) {
                    arrayBotones[i][j].setText(String.valueOf(arrayBotones[i][j].getNumeroMinasAlrededor()));
                }

            }

        }
    }

    private void Reiniciar() {
        ventanaBuscaminas reiniciar = new ventanaBuscaminas();
        reiniciar.setVisible(true);
        dispose();
        jDialog1.dispose();
    }

    private void cerrar() {
        dispose();
        jDialog1.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PERDISTE");

        jButton1.setText("REINICIAR");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("SALIR");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        Reiniciar();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        cerrar();
    }//GEN-LAST:event_jButton2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ventanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaBuscaminas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
