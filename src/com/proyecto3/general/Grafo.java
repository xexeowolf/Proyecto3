package com.proyecto3.general;

public class Grafo<Llave extends Comparable<Llave>> {

	private ListaDoble<Llave,ListaDoble<Llave,Integer>> lista_adyacencia;
	public int cantidad;
	
	public Grafo() {
		lista_adyacencia=new ListaDoble<Llave,ListaDoble<Llave,Integer>>();
		cantidad=0;
	}
	
	public void inicializar(Llave llave){
		ListaDoble<Llave,Integer> sublista=new ListaDoble<Llave,Integer>();
		lista_adyacencia.addFirst(llave, sublista);
		OrdenamientoDLL.ShellSort(lista_adyacencia);
		cantidad++;
	}
	
	public void eliminar(Llave llave){
		lista_adyacencia.delete(llave);
	}
	
	public NodoDoble<Llave,ListaDoble<Llave,Integer>> buscar(Llave llave){
		return Busqueda.busquedaBinariaDL(lista_adyacencia, llave, 0, lista_adyacencia.size);
	}
	public void conectar(Llave nodoA,Llave nodoB,Integer peso){
		NodoDoble<Llave,ListaDoble<Llave,Integer>> temp=Busqueda.busquedaBinariaDL(lista_adyacencia, nodoA, 0, lista_adyacencia.size);
		temp.valor.addFirst(nodoB, peso);
		OrdenamientoDLL.ShellSort(temp.valor);
		temp=Busqueda.busquedaBinariaDL(lista_adyacencia, nodoB, 0, lista_adyacencia.size);
		temp.valor.addFirst(nodoA, peso);
		OrdenamientoDLL.ShellSort(temp.valor);
	}
	
	public void mostrar(){
		NodoDoble<Llave,ListaDoble<Llave,Integer>> temp=lista_adyacencia.head;
		while(temp!=null){
			
			System.out.println("Contenido: "+temp.llave);
			NodoDoble<Llave,Integer> punt=temp.valor.head;
			while(punt!=null){
				
				System.out.println("Conexion a: "+punt.llave+"\n Peso: "+punt.valor);
				punt=punt.next;
			}
			temp=temp.next;
		}
	}
	
	public ListaDoble<Llave,ListaDoble<Llave,Integer>> obtenerLista(){
		return lista_adyacencia;
	}
		
	private boolean comparar(char[] uno,char[] dos){
		int cont;
		if(uno.length<dos.length){
			cont=uno.length;
		}else{
			cont=dos.length;
		}
		for(int i=0;i<cont;i++){
			if(uno[i]!=dos[i]){
				return false;
			}
		}
		
		return true;
	}
	
	
	private NodoDoble<String,String> localizar(ListaDoble<String,String> lista,String elemento){
		NodoDoble<String,String> head=lista.head;
		char[] uno=elemento.toCharArray();
		while(head!=null){
			if(comparar(uno,head.llave.toCharArray())){
				break;
			}
			head=head.next;
		}
		return head;
		
	}
	
	public String rutaMasCorta(Llave nodoA, Llave nodoB, ListaDoble<String,String> lista){
		String[]temp;
		NodoDoble<String,String> actual=localizar(lista,(String)nodoB);
		String ruta=actual.llave+"-";
		int peso=0;
		while(actual!=null && actual.llave!=(String)nodoA){
			temp=actual.valor.split("jk");
			ruta+=(temp[0]+"-");
			if(actual.llave==nodoB){
				peso=Integer.parseInt(temp[1]);
			}
			actual=localizar(lista,temp[0]);
		}
		ruta = ruta.substring(0,ruta.length()-1);
		//return "Ruta: "+ruta+"\n Peso: "+String.valueOf(peso);
		return ruta+"jk"+String.valueOf(peso);
	}
	
	
	public String rutaDijkstra(Llave nodoOrigen,Llave nodoDestino){
		ListaDoble<Llave,Boolean> visitados=new ListaDoble<Llave,Boolean>();
		ListaDoble<Integer,Llave> propuestas=new ListaDoble<Integer,Llave>();
		int cont=0;
		NodoDoble<Llave,ListaDoble<Llave,Integer>> origen=Busqueda.busquedaBinariaDL(lista_adyacencia, nodoOrigen, 0, lista_adyacencia.size);
		ListaDoble<String,String> camino=new ListaDoble<String,String>();
		
		if(origen!=null){
			camino.addFirst((String)origen.llave, "");
			visitados.addFirst(origen.llave, true);
			while(Busqueda.busquedaBinariaDL(visitados, nodoDestino, 0, visitados.size)==null){
				NodoDoble<Llave,Integer> punt=origen.valor.head;
				while(punt!=null){
					
					NodoDoble<Integer,Llave> verf=propuestas.head;
					while(verf!=null){
						if(verf.valor==punt.llave){
							
							if(verf.llave>punt.valor+cont){
								verf.llave=punt.valor+cont;
								
								NodoDoble<String,String> algo=camino.head;
								while(algo!=null){
									if(algo.llave==punt.llave){
										algo.valor=origen.llave+"jk"+(punt.valor+cont);
									}
									algo=algo.next;
								}
							}
							break;
						}
						verf=verf.next;
					}
					
					NodoDoble<Llave,Boolean> estado=visitados.head;
					while(estado!=null){
						if(estado.llave==punt.llave){
							break;
						}
						estado=estado.next;
					}
					
					if(verf==null && estado==null){
					
					propuestas.addFirst(punt.valor+cont,punt.llave);
					camino.addFirst((String)punt.llave, origen.llave+"jk"+(punt.valor+cont));
					}
					
					punt=punt.next;
				}
				if(propuestas.head==null){
					System.out.println("Salio por break Ultimo visitado: "+origen.llave);
					break;
				}
				OrdenamientoDLL.ShellSort(propuestas);
				
				origen=Busqueda.busquedaBinariaDL(lista_adyacencia, propuestas.head.valor, 0, lista_adyacencia.size);
				
				visitados.addFirst(origen.llave, true);
				cont=propuestas.head.llave;
				propuestas.deleteFirst();
				
				
				
			}
			
			
			
		}else{
			System.out.println("El nodo de origen debe ser parte del grafo");
			return null;
		}
		return rutaMasCorta(nodoOrigen,nodoDestino,camino);
		
	}
	
	
	
	public static void main(String[] args){

		Grafo<String> grafo=new Grafo<String>();
		grafo.inicializar("s");
		grafo.inicializar("b");
		grafo.inicializar("c");
		grafo.inicializar("d");
		grafo.inicializar("e");
		grafo.inicializar("t");
		grafo.conectar("s", "b", 4);
		grafo.conectar("s", "c", 2);
		grafo.conectar("b", "c", 1);
		grafo.conectar("b", "d", 5);
		grafo.conectar("d", "c", 8);
		grafo.conectar("d", "e", 2);
		grafo.conectar("d", "t", 6);
		grafo.conectar("c", "e", 10);
		grafo.conectar("e", "t", 2);
		//grafo.mostrar();
		System.out.println(grafo.rutaDijkstra("s", "t"));
	}
	
	
	

}
