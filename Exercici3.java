package Exercicis;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
public class Exercici3 {
	public static void main(String[] args) throws ParserConfigurationException, TransformerException, SAXException, IOException {
		Scanner lector=new Scanner(System.in);
		boolean fi=false;
		do {
			System.out.println("Tria una de les 4 opcions \n1. Generar el següent fitxer XML mitjançant Java DOM\n2. Afegir nova pel·lícula al fitxer XML. \n3. Mostrar totes les pel·lícules del fitxer XML per consola.\n4. Sortir de l’aplicació");
			int opcio=lector.nextInt();
			switch(opcio) {
			case 1:
				opcio1();
				break;
			case 2:
				opcio2();
				break;
			case 3:
				opcio3();
				break;
			case 4:
				fi=true;
				break;
			default:
				System.out.println("La opcio que has posat no es correcta");
				break;
			}
		}while (!fi);

	}
	public static void opcio1() throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document doc=docBuilder.newDocument();
		Element rootElem=doc.createElement("cartellera");
		doc.appendChild(rootElem);
		Element pelicula=doc.createElement("pelicula");
		rootElem.appendChild(pelicula);
		Element titol=doc.createElement("titol");
		titol.appendChild(doc.createTextNode("Jurasic Park"));
		pelicula.appendChild(titol);
		Element genere=doc.createElement("genere");
		genere.appendChild(doc.createTextNode("fantasia"));
		pelicula.appendChild(genere);
		Element any=doc.createElement("any");
		any.appendChild(doc.createTextNode("1995"));
		pelicula.appendChild(any);
		Element duracio=doc.createElement("duracio");
		duracio.appendChild(doc.createTextNode("120"));
		pelicula.appendChild(duracio);
		pelicula=doc.createElement("pelicula");
		rootElem.appendChild(pelicula);
		titol=doc.createElement("titol");
		titol.appendChild(doc.createTextNode("Pulp Fiction"));
		pelicula.appendChild(titol);
		genere=doc.createElement("genere");
		genere.appendChild(doc.createTextNode("accio"));
		pelicula.appendChild(genere);
		any=doc.createElement("any");
		any.appendChild(doc.createTextNode("1996"));
		pelicula.appendChild(any);
		duracio=doc.createElement("duracio");
		duracio.appendChild(doc.createTextNode("138"));
		pelicula.appendChild(duracio);
		TransformerFactory tFactory=TransformerFactory.newInstance();
		Transformer transformer=tFactory.newTransformer();
		DOMSource source=new DOMSource(doc);
		StreamResult result=new StreamResult(new File("pelicula.xml"));
		transformer.transform(source, result);
		
	}
	public static void opcio2() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		Scanner lector=new Scanner(System.in);
		File f=new File("pelicula.xml");
		DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document doc=docBuilder.parse(f);
		Element arrel =doc.createElement("pelicula");
		System.out.println("Introdueix el titol de la pelicula");
		CrearElement("titol",lector.nextLine(),arrel,doc);
		lector.nextLine();
		System.out.println("Introdueix el genere de la pelicula");
		CrearElement("genere",lector.next(),arrel,doc);
		System.out.println("Introdueix el any de la pelicula");
		CrearElement("any",Integer.toString(lector.nextInt()),arrel,doc);
		System.out.println("Introdueix la duracio de la pelicula");
		CrearElement("duracio",Integer.toString(lector.nextInt()),arrel,doc);

		Source source = new DOMSource(doc);
		Result result = new StreamResult(new java.io.File("pelicula.xml"));
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(source, result);
		//he ficat aixo pero no me ha funcionat lo de guardar
		/* hola que tal*/
	}
	static void CrearElement(String datoPeli, String valor, Element raiz, Document doc) {
		Element elem = doc.createElement(datoPeli);
		Text text = doc.createTextNode(valor);
		raiz.appendChild(elem);
		elem.appendChild(text);
	}
	public static void opcio3() {
		try {
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse(new File("pelicula.xml"));
			doc.getDocumentElement().normalize();
			System.out.println("Element arrel:"+doc.getDocumentElement().getNodeName());
			NodeList pelicules=doc.getElementsByTagName("pelicula");
			for(int i=0;i<pelicules.getLength();i++) {
				Node peli=pelicules.item(i);
				if(peli.getNodeType()==Node.ELEMENT_NODE) {
					Element elemento=(Element)peli;
					System.out.println("Titol: "+getNodo("titol",elemento));
					System.out.println("Genere: "+getNodo("genere",elemento));
					System.out.println("Any: "+getNodo("any",elemento));
					System.out.println("Duracio: "+getNodo("duracio",elemento));
					System.out.println("-----------------------");
				}
			}
		}catch (Exception e) {
			System.err.print("Error"+e);
		}
	}
	private static String getNodo(String etiqueta, Element elem) {
		NodeList node=elem.getElementsByTagName(etiqueta).item(0).getChildNodes();
		Node valornode=(Node) node.item(0);
		return valornode.getNodeValue();
	}
}
