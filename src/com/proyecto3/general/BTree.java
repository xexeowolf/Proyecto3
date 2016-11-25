package com.proyecto3.general;

import com.proyecto3.general.Lista.Nodo;

public class BTree<Llave extends Comparable<Llave>, Valor>{
	
	public static int orden=5;
	public int altura;
	public Pagina<Llave,Valor> raiz;
	public Pagina<Llave,Valor> ant;
	
	public static final class Pagina<Llave extends Comparable<Llave>, Valor>{
		public Lista<Llave,Valor,Pagina<Llave,Valor>> contenido=new Lista<Llave,Valor,Pagina<Llave,Valor>>();
		public int limite,hijos;
		public Pagina<Llave,Valor> antecesor;
		
		public Pagina(){
			limite=orden-1;
			antecesor=null;
		}
		
		public void agregarLlave(Llave llave,Valor valor){
			contenido.agregar(llave, valor);
			
		}
		
		public void definirReferenciaIzq(Pagina<Llave,Valor> referencia,Llave llave){
			contenido.buscar(contenido, llave, 0, contenido.size).izq=referencia;
		}
		public void definirReferenciaDer(Pagina<Llave,Valor> referencia,Llave llave){
			contenido.buscar(contenido, llave, 0, contenido.size).der=referencia;
		}
		
		
	}
	
	
	public BTree(){
		raiz=null;
		altura=0;
	}
	
	public Nodo<Llave,Valor,Pagina<Llave,Valor>> ubicar(Lista<Llave,Valor,Pagina<Llave,Valor>> lista, Llave llave){
		Nodo<Llave,Valor,Pagina<Llave,Valor>> temp=lista.head;
		while(temp.next!=null){
			if(temp.llave.compareTo(llave)<0 && temp.next.llave.compareTo(llave)>0){
				return temp;
			}
			temp=temp.next;
		}
		return temp;
	}
	
	public Pagina<Llave,Valor> buscar(Llave llave){
		Pagina<Llave,Valor> temp=raiz;
		ant=temp;
		Nodo<Llave,Valor,Pagina<Llave,Valor>> punt=ubicar(temp.contenido,llave);
		while(punt.esHoja()==false){
			ant=temp;
			if(punt.next!=null){
				temp=punt.der;
			}else{
				if(punt.llave.compareTo(llave)>0){
					temp=punt.izq;
				}else{
					temp=punt.der;
				}
			}
			if(temp!=null){
				punt=ubicar(temp.contenido,llave);
			}else{
				break;
			}
		}
		if(temp!=null){
			temp.antecesor=ant;
			return temp;
		}else{
			return null;
		}
	}
	
	
	public void insertar(Llave llave, Valor valor){
		if(raiz==null){
			altura++;
			Pagina<Llave,Valor> pag=new Pagina<Llave,Valor>();
			pag.agregarLlave(llave, valor);
			raiz=pag;
		}else{
			Pagina<Llave,Valor> temp=buscar(llave);
			//System.out.println("Antes: "+temp.contenido.size);
			if(temp.contenido.size<orden-1){
				temp.agregarLlave(llave, valor);
				//System.out.println("Menor insertando"+llave);
			}else{
				//System.out.println("Explotando insertando"+llave);
				temp.contenido.agregar(llave, valor);
				explotar(temp);
			}
			//System.out.println("Despues: "+temp.contenido.size);
		}
	}
	
	public void explotar(Pagina<Llave,Valor> objetivo){
		Nodo<Llave,Valor,Pagina<Llave,Valor>> nodo=objetivo.contenido.ubicar(objetivo.contenido, objetivo.contenido.size/2);
		nodo.izq=new Pagina<Llave,Valor>();
		nodo.der=new Pagina<Llave,Valor>();
		for(Nodo<Llave,Valor,Pagina<Llave,Valor>> temp=objetivo.contenido.head;temp!=null;temp=temp.next){
			if(temp.posicion<nodo.posicion){
				nodo.izq.contenido.crear(temp);
			}else if(temp.posicion>nodo.posicion){
				nodo.der.contenido.crear(temp);
			}
		}
		Lista<Llave,Valor,Pagina<Llave,Valor>> lita=new Lista<Llave,Valor,Pagina<Llave,Valor>>();
		lita.crear(nodo);
		objetivo.contenido=lita;
		if(objetivo.antecesor!=objetivo){
		objetivo.antecesor.contenido.crear(nodo);
		Nodo<Llave,Valor,Pagina<Llave,Valor>> ntemp=objetivo.antecesor.contenido.buscar(objetivo.antecesor.contenido, nodo.llave, 0, objetivo.antecesor.contenido.size);
		if(ntemp==objetivo.antecesor.contenido.head){
			ntemp.next.izq=ntemp.der;
		}else if(ntemp==objetivo.antecesor.contenido.tail){
			Nodo<Llave,Valor,Pagina<Llave,Valor>> antemp=objetivo.antecesor.contenido.ubicar(objetivo.antecesor.contenido, ntemp.posicion-1);
			antemp.der=ntemp.izq;
		}else{
			Nodo<Llave,Valor,Pagina<Llave,Valor>> antemp=objetivo.antecesor.contenido.ubicar(objetivo.antecesor.contenido, ntemp.posicion-1);
			antemp.der=ntemp.izq;
			ntemp.next.izq=ntemp.der;
			}
		}
		if(objetivo.antecesor.contenido.size>orden-1){
			explotar(objetivo.antecesor);
		}
		/*if(objetivo.antecesor.antecesor==null){
			objetivo.antecesor.contenido.crear(nodo);
			nodo=objetivo.antecesor.contenido.buscar(objetivo.antecesor.contenido, nodo.llave, 0, objetivo.antecesor.contenido.size);
			if(nodo.next!=null){
				if(nodo.next.izq!=null){
					for(Nodo<Llave,Valor,Pagina<Llave,Valor>> y=nodo.der.contenido.head;y!=null;y=y.next){
						nodo.next.izq.contenido.crear(y);
					}
					nodo.der=nodo.next.izq;
				}else{
					nodo.next.izq.contenido=nodo.der.contenido;
				}
			}
			Nodo<Llave,Valor,Pagina<Llave,Valor>> punt=objetivo.antecesor.contenido.ubicar(objetivo.antecesor.contenido, nodo.posicion-1);
			if(punt!=null){
				if(punt.der!=null){
					for(Nodo<Llave,Valor,Pagina<Llave,Valor>> y=nodo.izq.contenido.head;y!=null;y=y.next){
						punt.der.contenido.crear(y);
					}
					nodo.izq=punt.der;
				}else{
					punt.der=nodo.izq;
				}
			}
			
			Pagina<Llave,Valor> nueva=new Pagina<Llave,Valor>();
			nodo=objetivo.antecesor.contenido.ubicar(objetivo.antecesor.contenido, objetivo.antecesor.contenido.size/2);
			nueva.contenido.crear(nodo);
			nodo.izq=new Pagina<Llave,Valor>();
			nodo.der=new Pagina<Llave,Valor>();
			for(Nodo<Llave,Valor,Pagina<Llave,Valor>> temp=objetivo.antecesor.contenido.head;temp!=null;temp=temp.next){
				if(temp.posicion<nodo.posicion){
					nodo.izq.contenido.crear(temp);
				}else if(temp.posicion>nodo.posicion){
					nodo.der.contenido.crear(temp);
				}
			}
			raiz=nueva;
		}else if(objetivo.antecesor.antecesor.contenido.size<orden-1){
			objetivo.antecesor.contenido.crear(nodo);
		}else{
			
		}*/
		
	}
	
	
	
	/*public void insertar(Llave llave,Valor valor){
		if(raiz==null){
			altura++;
			Pagina<Llave,Valor> pag=new Pagina<Llave,Valor>();
			pag.agregarLlave(llave, valor);
			raiz=pag;
		}else{
			Pagina<Llave,Valor> temp=buscar(llave);
			Nodo<Llave,Valor,Pagina<Llave,Valor>> punt;
			if(temp.contenido.size<orden-1){
				temp.agregarLlave(llave, valor);
			}else{
				altura++;
				temp.agregarLlave(llave, valor);
				punt=temp.contenido.ubicar(temp.contenido, temp.contenido.size/2);
				Pagina<Llave,Valor> der=new Pagina<Llave,Valor>();
				Pagina<Llave,Valor> izq=new Pagina<Llave,Valor>();
				der.antecesor=izq.antecesor=ant;
				for(Nodo<Llave,Valor,Pagina<Llave,Valor>> lol=temp.contenido.head;lol!=null;lol=lol.next){
					if(lol.posicion<punt.posicion){
						izq.agregarLlave(lol.llave, lol.valor);
					}else if(lol.posicion>punt.posicion){
						der.agregarLlave(lol.llave, lol.valor);
					}
					
				}
				if(ant.contenido.size<orden-1){
					ant.contenido.agregar(punt.llave, punt.valor);
					punt=ant.contenido.buscar(ant.contenido, punt.llave, 0, ant.contenido.size);
					punt.der=der;
					punt.izq=izq;
					Nodo<Llave,Valor,Pagina<Llave,Valor>> atras,adelante;
					atras=ant.contenido.ubicar(ant.contenido, punt.posicion-1);
					adelante=ant.contenido.ubicar(ant.contenido, punt.posicion+1);
					if(atras!=null){
						atras.der=izq;
					}
					if(adelante!=null){
						adelante.izq=der;
					}
					
				}else{
					Lista<Llave,Valor,Pagina<Llave,Valor>> lista=new Lista<Llave,Valor,Pagina<Llave,Valor>>();
					lista.agregar(punt.llave, punt.valor);
					temp.contenido=lista;
					temp.contenido.buscar(temp.contenido, punt.llave, 0, temp.contenido.size).der=der;
					temp.contenido.buscar(temp.contenido, punt.llave, 0, temp.contenido.size).izq=izq;
				}
			}
		}
	}*/
	
	public void imprimir(Pagina<Llave,Valor> pag){
		if(pag==raiz){
		System.out.println("Raiz:");
		pag.contenido.imprimir();}
		for(Nodo<Llave,Valor,Pagina<Llave,Valor>> temp=pag.contenido.head;temp!=null;temp=temp.next){
			if(temp.esHoja()==false){
				System.out.println("Izq de: "+temp.llave);
				temp.izq.contenido.imprimir();
				System.out.println("Der de: "+temp.llave);
				temp.der.contenido.imprimir();
				imprimir(temp.izq);
				imprimir(temp.der);
			}
		}
 	}
	
	
	public static void main(String[] args){
		BTree<String,Integer> arbol=new BTree<String,Integer>();
		arbol.insertar("a", 5);
		arbol.insertar("b", 5);
		arbol.insertar("c", 5);
		arbol.insertar("d", 5);
		arbol.insertar("e", 5);
		arbol.insertar("f", 5);
		arbol.insertar("g", 5);
		arbol.insertar("h", 5);
		arbol.insertar("i", 5);
		arbol.insertar("j", 5);
		arbol.insertar("k", 5);
		arbol.insertar("l", 5);
		arbol.insertar("m", 5);
		arbol.insertar("n", 5);
		arbol.insertar("o", 5);
		arbol.insertar("p", 5);
		arbol.insertar("q", 5);
		arbol.imprimir(arbol.raiz);
	}
	
}


