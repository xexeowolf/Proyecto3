package com.proyecto3.general;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


@Path("/mensajes")
public class MessageService  {
	
	public ListaDoble<String,ListaDoble<String,String>> mensajes=lectura();
	public ListaDoble<String,String> conectados=lecturaGeneral("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto3/WebContent/WEB-INF/Conectados.xml");
	public ListaDoble<String,String> desconectados=lecturaGeneral("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto3/WebContent/WEB-INF/Desconectados.xml");
	public ListaDoble<String,String> baneados=lecturaGeneral("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto3/WebContent/WEB-INF/Baneados.xml");
	
	public MessageService(){
		
	}
	
	@POST
	@Consumes(MediaType.TEXT_HTML)
	@Path("id/{messageId}")
	public void guardarMensaje(String mensaje,@PathParam("messageId") String Id){
		NodoDoble<String,ListaDoble<String,String>> temp=Busqueda.busquedaBinariaDL(mensajes, Id, 0, mensajes.size);
		temp.valor.addLast("mensaje"+temp.valor.size, mensaje);
		escritura(mensajes);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("id/{messageId}")
	public String obtenerMensajes(@PathParam("messageId") String menId){
		NodoDoble<String,ListaDoble<String,String>> temp=Busqueda.busquedaBinariaDL(mensajes, menId, 0, mensajes.size);
		return toJSON(temp.valor);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/conectados")
	public String todosConectados(){
		return toJSON(conectados);
	}
	
	
	public String toJSON(ListaDoble<String,String> lista){
		JSONObject objeto=new JSONObject();
		NodoDoble<String,String> head=lista.head;
		while(head!=null){
			try {
				objeto.put(head.llave, head.valor);	
			} catch (JSONException e) {
				e.printStackTrace();
			}
			head=head.next;
		}
		return objeto.toString();
	}
	
	public ListaDoble<String,String> lecturaGeneral(String direccion){
		ListaDoble<String,String> contenido=new ListaDoble<String,String>();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file=new File(direccion);
			if (file.exists()){
				Document doc = db.parse(file);
				Element docEle = doc.getDocumentElement();
					NodeList informacion = docEle.getElementsByTagName("usuario");
					if (informacion != null && informacion.getLength() > 0) {
						for (int i = 0; i < informacion.getLength(); i++) {
							Node nodo = informacion.item(i);
							if (nodo.getNodeType() == Node.ELEMENT_NODE) {
								
								Element e = (Element) nodo;
								
								NodeList elementos=e.getElementsByTagName("ip");
								String ip=elementos.item(0).getChildNodes().item(0).getNodeValue();
								elementos = e.getElementsByTagName("nombre");	
								contenido.addLast(ip,elementos.item(0).getChildNodes().item(0).getNodeValue());
						}
					}
				}
			}else{System.exit(1);}		
		} catch (Exception e) {
			e.printStackTrace();
		}
			return contenido;

		
	}
	
	public void escrituraGeneral(String raiz, ListaDoble<String,String> lista,String direccion){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(raiz);
			doc.appendChild(rootElement);
			
			NodoDoble<String,String> punt=lista.head;
			while(punt!=null){
				Element oro = doc.createElement("usuario");
				rootElement.appendChild(oro);
					
				Element Generico=doc.createElement("ip");
				Generico.appendChild(doc.createTextNode(punt.llave));
				oro.appendChild(Generico);
				
				Generico=doc.createElement("nombre");
				Generico.appendChild(doc.createTextNode(punt.valor));
				oro.appendChild(Generico);
				
				punt=punt.next;
			}
				
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult resulto = new StreamResult(new File(direccion));
			transformer.transform(source, resulto);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	
	
	
	
	public void escritura(ListaDoble<String,ListaDoble<String,String>> lista){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("todo");
			doc.appendChild(rootElement);
			
			NodoDoble<String,ListaDoble<String,String>> punt=lista.head;
			while(punt!=null){
				Element oro = doc.createElement("persona");
				rootElement.appendChild(oro);
					
				Element Generico=doc.createElement("id");
				Generico.appendChild(doc.createTextNode(punt.llave));
				oro.appendChild(Generico);
				
				NodoDoble<String,String>temp=punt.valor.head;
				if(temp!=null){
					while(temp!=null){
						Generico=doc.createElement(temp.llave);
						Generico.appendChild(doc.createTextNode(temp.valor));
						oro.appendChild(Generico);
						temp=temp.next;
					}
				}
				
				punt=punt.next;
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult resulto = new StreamResult(new File("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto3/WebContent/WEB-INF/Mensajes.xml"));
			transformer.transform(source, resulto);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}	
	
		
	public ListaDoble<String,ListaDoble<String,String>> lectura(){
		ListaDoble<String,ListaDoble<String,String>> contenido=new ListaDoble<String,ListaDoble<String,String>>();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file=new File("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto3/WebContent/WEB-INF/Mensajes.xml");
			if (file.exists()){
				Document doc = db.parse(file);
				Element docEle = doc.getDocumentElement();
					NodeList informacion = docEle.getElementsByTagName("persona");
					if (informacion != null && informacion.getLength() > 0) {
						for (int i = 0; i < informacion.getLength(); i++) {
							Node nodo = informacion.item(i);
							if (nodo.getNodeType() == Node.ELEMENT_NODE) {
								
								Element e = (Element) nodo;
								
								NodeList elementos=e.getElementsByTagName("cantidad");
								int max= Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								int cont=0;
								ListaDoble<String,String> subcontenido=new ListaDoble<String,String>();
								while(cont<max){
									cont++;
									elementos=e.getElementsByTagName("mensaje"+cont);
									subcontenido.addLast("mensaje"+cont, elementos.item(0).getChildNodes().item(0).getNodeValue());
								}
								
								elementos = e.getElementsByTagName("id");	
								contenido.addLast(elementos.item(0).getChildNodes().item(0).getNodeValue(),subcontenido);
								
						}
					}
				}
			}else{System.exit(1);}		
		} catch (Exception e) {
			e.printStackTrace();
		}
			return contenido;
	}
	
	
	
}