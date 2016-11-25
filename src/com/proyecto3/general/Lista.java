package com.proyecto3.general;

public class Lista<Llave extends Comparable<Llave>,Valor,Tipo> {

	public static final class Nodo<Llave extends Comparable<Llave>,Valor,Tipo>{
		public Nodo<Llave,Valor,Tipo> next;
		public int posicion;
		public Llave llave;
		public Valor valor;
		public Tipo izq,der;
		public Nodo(){
			next=null;
		}
		
		public Nodo(Llave llave,Valor valor,int pos){
			next=null;
			posicion=pos;
			this.llave=llave;
			this.valor=valor;
			der=izq=null;
		}
		
		public void definirIzq(Tipo tipo){
			izq=tipo;
		}
		
		public void definirDer(Tipo tipo){
			der=tipo;
		}
		
		public boolean esHoja(){
			return der==null && izq==null;
		}
	}
	
	public Nodo<Llave,Valor,Tipo> head,tail;
	public int size;
	
	public Lista() {
		head=tail=null;
		size=0;
		
	}
	
	public Nodo<Llave,Valor,Tipo> ubicar(Lista<Llave,Valor,Tipo> lista,int num){
		Nodo<Llave,Valor,Tipo> temp=lista.head;
		while(temp!=null && temp.posicion!=num ){
			temp=temp.next;
		}
		return temp;
	}
	
	public void crear(Nodo<Llave,Valor,Tipo> nodo){
		Nodo<Llave,Valor,Tipo> nuevo=new Nodo<Llave,Valor,Tipo>(nodo.llave,nodo.valor,nodo.posicion);
		nuevo.der=nodo.der;
		nuevo.izq=nodo.izq;
		if(head==null){
			head=tail=nuevo;
		}else{
			tail.next=nuevo;
			tail=nuevo;
		}
		ordenar(this);
		size++;
	}
	
	public void ordenar(Lista<Llave,Valor,Tipo> lista){
		int adentro,afuera;
		Llave temp;
		Valor vtemp;
		int h=1;
		while(h<=lista.size/3){
			h=h*3+1;
		}
		while(h>0){
			for(afuera=h;afuera<lista.size;afuera++){
				temp=ubicar(lista,afuera).llave;
				vtemp=ubicar(lista,afuera).valor;
				adentro=afuera;
				
				while(adentro>h-1 && ubicar(lista,adentro-h).llave.compareTo(temp)>=0){
					ubicar(lista,adentro).llave=ubicar(lista,adentro-h).llave;
					ubicar(lista,adentro).valor=ubicar(lista,adentro-h).valor;
					adentro -=h;
				}
				ubicar(lista,adentro).llave=temp;
				ubicar(lista,adentro).valor=vtemp;
			}
			h=(h-1)/3;
		}
		ajustarPosicion();
	}
	
	public void ajustarPosicion(){
		int cont=0;
		for(Nodo<Llave,Valor,Tipo> punt=head;punt!=null;punt=punt.next){
			punt.posicion=cont;
			cont++;
		}
	}
	
	public void agregar(Llave llave,Valor valor){
		Nodo<Llave,Valor,Tipo> nuevo=new Nodo<Llave,Valor,Tipo>(llave,valor,size);
		size++;
		if(head==null){
			head=tail=nuevo;
		}else{
			tail.next=nuevo;
			tail=nuevo;
		}
		ordenar(this);
	}
	
	public Nodo<Llave,Valor,Tipo> buscar(Lista<Llave,Valor,Tipo> lista, Llave valor, int min, int max){
			if (min < max) {
			    int mitad = min+((max-min)/2);
			    if((max-min)==1 && min!=0){
			    	mitad++;
			    }
			    int cmp = ubicar(lista,mitad).llave.compareTo(valor);
			    if (cmp > 0) 
			    	{return buscar(lista, valor, min, mitad);}
			    else if (cmp < 0) 
			    	{return buscar(lista, valor, mitad, max);}
			    else{
			    	return ubicar(lista,mitad);	
			    }
			} 
			return null;
	}
	
	public void eliminar(Llave llave){
		Nodo<Llave,Valor,Tipo> temp=buscar(this,llave,0,size);
		if(temp!=null){
			if(temp==head){
				head=head.next;
			}else if(temp==tail){
				tail=ubicar(this,tail.posicion-1);
				tail.next=null;
			}else{
				Nodo<Llave,Valor,Tipo> ant=ubicar(this,temp.posicion-1);
				ant.next=temp.next;
			}
			size--;
			ajustarPosicion();
		}
	}
	
	public void imprimir(){
		Nodo<Llave,Valor,Tipo> temp=head;
		while(temp!=null){
			System.out.println("Llave: "+temp.llave+" Valor:"+temp.valor+" Posicion:"+temp.posicion);
			temp=temp.next;
		}
	}
	
	public static void main(String[] args){
		Lista<String,Integer,Integer> l=new Lista<String,Integer,Integer>();
		l.agregar("hola", 0);
		l.agregar("ayer", 1);
		l.agregar("manzana", 2);
		l.agregar("uva", 3);
		l.imprimir();
	}
	

}
