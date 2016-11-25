package com.proyecto3.general;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Clase encargada de representar graficamente imagenes en la interfaz dentro de una ventana.
 * @author Luis Alfredo Piedra Esquivel
 * @since 02/10/2016
 * @see JPanel
 */public class Panel  extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Dimension d;
	
	
	/**
	 * Constructor de la clase.
	 * @param act Lista de imagenes que seran representadas graficamente.
	 */
	public Panel() {
		d=new Dimension(700,700);
		setDoubleBuffered(true);
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
		doDrawing(g);
		repaint();
		}
	/**
	 * Metodo que representa graficamente las imagenes cargadas.
	 * @param g atributo grafico de la clase JPanel
	 */
	private void doDrawing(Graphics g){
		Graphics2D g2d=(Graphics2D) g;
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		Image fondo=new ImageIcon(getClass().getClassLoader().getResource("Imagenes/carretera704x704.gif")).getImage();
		g2d.drawImage(fondo,0,0,this);
		Toolkit.getDefaultToolkit().sync();
	    g2d.dispose();
	}

}
