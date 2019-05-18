package Crud.serializado;
/**
 * Implementa la parte de Modelo de Datow
 * 
 * @author orodicio
 * @version 1
 */
import java.io.IOException;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ModeloArrayList extends ModeloAbs {
	private ArrayList<Producto> lista;


	public ModeloArrayList() {
		lista = new ArrayList<Producto>();
		lista = rellenarLista();
	}

	// Implementar los metodos abstractos de ModeloAbs
	public boolean insertarProducto(Producto p) {
		return lista.add(p);

	}

	public boolean borrarProducto(int codigo) {
		return lista.removeIf(producto -> producto.getCodigo() == codigo);
	}

	public Producto buscarProducto(int codigo) {
		Producto buscado = null;
		for (Producto p : lista) {
			if (p.getCodigo() == codigo) {
				buscado = p;
				break;
			}
		}
		return buscado;
	}

	public void listarProductos() {
		lista.forEach(producto -> System.out.println(producto));
	}

	public boolean modificarProducto(Producto nuevo) {

		Producto p = buscarProducto(nuevo.getCodigo());
		if (p == null) {
			return false;
		}
		p.setPrecio(nuevo.getPrecio());
		p.setStock(nuevo.getStock());
		p.setStock_min(nuevo.getStock_min());
		return true;
	}

	public void ListarMenosStockmin() {
		
		lista.stream().filter(producto -> producto.getStock_min() > producto.getStock()).
		forEach(System.out::println);
		
	}
	//Grabar el ArrayList en el documento 
	public void grabarObjetos() {
		 try{
	         FileOutputStream fos= new FileOutputStream( "productos.objetos");
	         ObjectOutputStream oos= new ObjectOutputStream(fos);
	         
	        	  lista.forEach(producto->{
					try {
						oos.writeObject(producto);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	                 
	         oos.close(); 
	         fos.close();
	       }catch(IOException ioe){
	            ioe.printStackTrace();
	        }    
	        
	    }
		
	
	

	
	
	
	//Metodos privados para cargar el archivo en la arrylist creada en el constructor
	private ArrayList<Producto>rellenarLista(){
		ArrayList<Producto>lista=new ArrayList<Producto>();
		FileInputStream fr;
		ObjectInputStream input;
		Producto producto;
		try {
		    fr = new FileInputStream( "productos.objetos");
		    input = new ObjectInputStream (fr);
		    producto = leer(input);
		    while (producto!=null) {
		    	lista.add(producto);
		    	producto = leer(input);
		    	} 
			input.close();
			fr.close();
		}
		    catch (IOException eo) {
		    eo.printStackTrace();
	        } 
		    catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	
		return lista;
	}
	private Producto leer (ObjectInputStream input) throws ClassNotFoundException, IOException {
		Producto producto=null;
		
		if (input!=null) {
		try {
		producto = (Producto) input.readObject();
		} catch (EOFException eof) {
		// Fin del fichero
		}
		}
		return producto;	
	}
	
}
