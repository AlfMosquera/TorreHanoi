package com.mycompany.torrehanoi;

import com.mycompany.torrehanoi.view.ComponentesUI;
import com.mycompany.torrehanoi.view.TorreHanoiPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        JFrame ventana = new JFrame("Torre de Hanói - Edición Premium");
        TorreHanoiPanel panelDibujo = new TorreHanoiPanel();

        JPanel panelControles = new JPanel();
        panelControles.setBackground(new Color(240, 244, 248));
        panelControles.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JButton btnReiniciar = ComponentesUI.crearBotonEstilizado("Reiniciar / Cambiar Discos", new Color(100, 110, 120));
        JButton btnResolver = ComponentesUI.crearBotonEstilizado("Resolver Automáticamente", new Color(40, 160, 100));
        btnReiniciar.addActionListener(e -> panelDibujo.solicitarNumeroDiscos());
        btnResolver.addActionListener(e -> panelDibujo.iniciarResolucionAutomatica());
        panelControles.add(btnReiniciar);
        panelControles.add(btnResolver);

        ventana.setLayout(new BorderLayout());
        ventana.add(panelDibujo, BorderLayout.CENTER);
        ventana.add(panelControles, BorderLayout.SOUTH);
        ventana.setSize(900, 600);
        ventana.setMinimumSize(new Dimension(600, 400));
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}