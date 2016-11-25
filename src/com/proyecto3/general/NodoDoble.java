package com.proyecto3.general;

public class NodoDoble<E,T> {
	
	public NodoDoble<E,T> next,prev;
	public E llave;
	public T valor;
	
	public NodoDoble(){
		
	}
	
	public NodoDoble(E Llave,T Valor){
		llave=Llave;
		valor=Valor;
		next=null;
		prev=null;
	}

}
