package com.proyecto3.general;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Visualizer extends JFrame {
	
	private JPanel contentPane;
	private JPanel otro;
	int tamGrafo=20;
	int[] coordenadasx={100,200,350,400,550,270,150,600,710,565};
	int[] coordenadasy={130,260,89,450,115,530,330,234,405,30};
	int xA=0;
	int xB=0;
	int xC=0;
	int xI=0;
	int w=0;
	Image msj=new ImageIcon(getClass().getClassLoader().getResource("Imagenes/mensaje.png")).getImage();
	
	Timer t3= new Timer(1000,new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Graphics gp=getGraphics();
			for(int r=0;r<coordenadasx.length;r++){
			gp.setColor(Color.red);
			gp.drawOval(coordenadasx[r]-xC, coordenadasy[r]-xC,tamGrafo+xC*2,tamGrafo+xC*2);
			if(xC!=0){
			gp.setColor(Color.black);
			gp.drawOval(coordenadasx[r]-(xC-8), coordenadasy[r]-(xC-8), tamGrafo+(xC-8)*2, tamGrafo+(xC-8)*2);
			}
			if(xC>tamGrafo*5){
			gp.drawOval(coordenadasx[r]-xC, coordenadasy[r]-xC, tamGrafo+(xC)*2, tamGrafo+(xC)*2);
			}
			otro.paint(gp);
			}
			if(xC<=tamGrafo*5){xC=xC+8;}
			else{
				xC=0;
			}
		}
		
	});
	
	Timer t2= new Timer(1000,new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Graphics gp=getGraphics();
			for(int r=0;r<coordenadasx.length;r++){
			gp.setColor(Color.red);
			gp.drawOval(coordenadasx[r]-xB, coordenadasy[r]-xB,tamGrafo+xB*2,tamGrafo+xB*2);
			if(xB!=0){
			gp.setColor(Color.black);
			gp.drawOval(coordenadasx[r]-(xB-8), coordenadasy[r]-(xB-8), tamGrafo+(xB-8)*2, tamGrafo+(xB-8)*2);
			}
			if(xB>tamGrafo*5){
			gp.drawOval(coordenadasx[r]-xB, coordenadasy[r]-xB, tamGrafo+(xB)*2, tamGrafo+(xB)*2);
			}
			otro.paint(gp);
			}
			if(xB<=tamGrafo*5){xB=xB+8;}
			else{
				xB=0;
			}
			if(t3.isRunning()==false && xB==32){
				t3.start();
			}
		}
		
	});
	
	Timer t= new Timer(1000,new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Graphics gp=getGraphics();
			for(int r=0;r<coordenadasx.length;r++){
			gp.setColor(Color.red);
			gp.drawOval(coordenadasx[r]-xA, coordenadasy[r]-xA,tamGrafo+xA*2,tamGrafo+xA*2);
			
			if(xA!=0){
			gp.setColor(Color.black);
			gp.drawOval(coordenadasx[r]-(xA-8), coordenadasy[r]-(xA-8), tamGrafo+(xA-8)*2, tamGrafo+(xA-8)*2);
			}
			if(xA>tamGrafo*5){
			gp.drawOval(coordenadasx[r]-xA, coordenadasy[r]-xA, tamGrafo+(xA)*2, tamGrafo+(xA)*2);
			}
			otro.paint(gp);
			}
			if(xA<=tamGrafo*5){xA=xA+8;}
			else{
				xA=0;
			}
			
			if(t2.isRunning()==false && xA==32){
				t2.start();
			}
		}
		
	});
	
	
	Timer tI= new Timer(1000,new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//for(int s=0;s+1<coordenadasx.length;s++){
			transmision(coordenadasx[3],coordenadasy[3],coordenadasx[4],coordenadasy[4]);
			//}
			if(xI<16){
				xI++;
			}
		}
	});
	
	public Visualizer() {
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        contentPane.setBackground(Color.black);
        setSize(800,600);
        setTitle("Visualizer");
        setLocationRelativeTo(null);
		setVisible(true);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		otro=new JPanel();
		add(otro);
		//t.start();
		tI.start();
	}
	
	public void transmision(int x,int y,int x2,int y2){
		Graphics g=getGraphics();
		x=x+tamGrafo/2;
		x2=x2+tamGrafo/2;
		y=y+tamGrafo/2;
		y2=y2+tamGrafo/2;
		g.setColor(Color.green);
		g.drawLine(x,y,x2,y2);
		//int distx=Math.abs(x-x2);
		//int disty=Math.abs(y-y2);
		int m=(y2-y)/(x2-x);
		int b=y-(m*x);
		
		/*System.out.println(m+" "+b);
		m=((y2+tamGrafo/2)-(y+tamGrafo/2))/((x2+tamGrafo/2)-(x+tamGrafo/2));
		b=(y+tamGrafo/2)-(m*(x+tamGrafo/2));
		System.out.println(m+" "+b);
		//x=x+(distx/6)*xI;*/
		//g.drawLine(x, m*x+b, x2, y2);
		g.drawImage(msj,x+1*xI,m*(x+1*xI)+b,25,25,new ImageObserver(){
			@Override
			public boolean imageUpdate(Image arg0, int arg1, int arg2,
					int arg3, int arg4, int arg5) {
				return false;
				}
			});
	}
	
	
	public void paint (Graphics g)
    {
		
        super.paint(g);
        
        g.setColor(Color.white);
        for(int d=0;d<800;d=d+20){
        	g.drawLine(d,0, d, 600);
        }
        for(int d=0;d<600;d=d+20){
        	g.drawLine(0,d, 800, d);
        }
        
        g.setColor(Color.yellow);
        for(int h=0;h+1<coordenadasx.length;h++){
        	int mod=tamGrafo/2;
        	g.drawLine(coordenadasx[h]+mod,coordenadasy[h]+mod,coordenadasx[h+1]+mod,coordenadasy[h+1]+mod);
        }
        g.setColor(Color.blue);
        for(int i=0;i<coordenadasx.length;i++){
        	g.fillOval(coordenadasx[i],coordenadasy[i],tamGrafo,tamGrafo);
        }
        g.dispose();
    }
	public static void main(String[] args){
		new Visualizer();
	}

}
