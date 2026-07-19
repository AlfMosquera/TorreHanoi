package com.mycompany.torrehanoi.view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.mycompany.torrehanoi.model.ModeloHanoi;

public class TorreHanoiPanel extends JPanel {

    private ModeloHanoi modelo;
    private int torreSeleccionada = -1;
    private boolean modoAutomaticoActivo = false;
    private Thread hiloAutomatico;

    public TorreHanoiPanel() {
        solicitarNumeroDiscos();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (modoAutomaticoActivo) return;

                int x = e.getX();
                int torreClic = (x < getWidth() / 3) ? 1 : (x < (getWidth() * 2) / 3) ? 2 : 3;

                if (torreSeleccionada == -1) {
                    if (!modelo.getTorre(torreClic).isEmpty()) torreSeleccionada = torreClic;
                } else {
                    if (torreSeleccionada != torreClic) moverManual(torreSeleccionada, torreClic);
                    torreSeleccionada = -1;
                }
                repaint();
            }
        });
    }

    public void solicitarNumeroDiscos() {
        String entrada = JOptionPane.showInputDialog(null, "Inserte el número de discos:", "Configuración Inicial", JOptionPane.QUESTION_MESSAGE);
        if (entrada == null || entrada.trim().isEmpty()) {
            if (modelo == null) System.exit(0);
            return;
        }

        int numeroDiscos;
        try {
            numeroDiscos = Integer.parseInt(entrada.trim());
        } catch (NumberFormatException e) {
            numeroDiscos = 3;
        }

        reiniciarPartida(numeroDiscos);
    }

    private void reiniciarPartida(int numeroDiscos) {
        modelo = new ModeloHanoi(numeroDiscos);
        torreSeleccionada = -1;
        modoAutomaticoActivo = false;
        if (hiloAutomatico != null && hiloAutomatico.isAlive()) hiloAutomatico.interrupt();
        repaint();
    }

    private void moverManual(int origen, int destino) {
        if (modelo.esMovimientoValido(origen, destino)) {
            modelo.mover(origen, destino);
            verificarVictoria();
        } else {
            JOptionPane.showMessageDialog(this, "Movimiento inválido: El disco superior es más pequeño que el que intentas colocar.", "Regla de la Torre", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void verificarVictoria() {
        if (modelo.esVictoria()) {
            repaint();
            JOptionPane.showMessageDialog(this, "¡Felicidades! Has resuelto el rompecabezas.", "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (modelo == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(new GradientPaint(0, 0, new Color(240, 244, 248), 0, getHeight(), new Color(208, 225, 240)));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        int anchoTercio = getWidth() / 3;
        int yBase = getHeight() - 80;
        int[] centros = { anchoTercio / 2, anchoTercio + anchoTercio / 2, anchoTercio * 2 + anchoTercio / 2 };

        g2d.setColor(new Color(180, 180, 180, 100));
        g2d.fillRoundRect(35, yBase + 15, getWidth() - 70, 25, 20, 20);

        g2d.setPaint(new GradientPaint(0, yBase, new Color(80, 90, 100), 0, yBase + 20, new Color(50, 60, 70)));
        g2d.fillRoundRect(30, yBase, getWidth() - 60, 25, 20, 20);

        for (int c : centros) dibujarPoste(g2d, c, yBase);
        for (int i = 0; i < 3; i++) dibujarDiscos(g2d, i + 1, centros[i], yBase);
    }

    private void dibujarPoste(Graphics2D g2d, int x, int yBase) {
        g2d.setPaint(new GradientPaint(x - 7, yBase - 250, new Color(200, 200, 200), x + 7, yBase, new Color(130, 130, 130)));
        g2d.fillRoundRect(x - 7, yBase - 260, 14, 270, 10, 10);
    }

    private void dibujarDiscos(Graphics2D g2d, int indiceTorre, int xCentroPoste, int yBase) {
        var pila = modelo.getTorre(indiceTorre);

        for (int i = 0; i < pila.size(); i++) {
            int tamanoDisco = pila.get(i);
            int ancho = 40 + tamanoDisco * 25, alto = 24;
            int x = xCentroPoste - ancho / 2, y = yBase - 5 - (i + 1) * alto;

            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.fillRoundRect(x + 3, y + 4, ancho, alto, 15, 15);

            boolean seleccionado = indiceTorre == torreSeleccionada && i == pila.size() - 1;
            g2d.setPaint(seleccionado
                    ? new GradientPaint(x, y, new Color(255, 100, 100), x, y + alto, new Color(200, 30, 30))
                    : new GradientPaint(x, y, new Color(100, 180, 255), x, y + alto, new Color(30, 100, 200)));
            g2d.fillRoundRect(x, y, ancho, alto, 15, 15);

            g2d.setColor(new Color(255, 255, 255, 80));
            g2d.drawRoundRect(x + 1, y + 1, ancho - 3, alto - 3, 15, 15);
        }
    }

    public void iniciarResolucionAutomatica() {
        if (modoAutomaticoActivo || modelo.esVictoria()) return;
        if (!modelo.estaEnEstadoInicial()) reiniciarPartida(modelo.getNumeroDiscos());

        modoAutomaticoActivo = true;
        torreSeleccionada = -1;

        hiloAutomatico = new Thread(() -> {
            try {
                resolverRecursivo(modelo.getNumeroDiscos(), 1, 2, 3);
                SwingUtilities.invokeLater(this::verificarVictoria);
            } catch (InterruptedException ignored) {
            } finally {
                modoAutomaticoActivo = false;
            }
        });
        hiloAutomatico.start();
    }

    private void resolverRecursivo(int discos, int origen, int auxiliar, int destino) throws InterruptedException {
        if (discos == 0) return;

        resolverRecursivo(discos - 1, origen, destino, auxiliar);

        Thread.sleep(350);
        if (Thread.currentThread().isInterrupted()) throw new InterruptedException();

        modelo.mover(origen, destino);
        repaint();

        resolverRecursivo(discos - 1, auxiliar, origen, destino);
    }
}