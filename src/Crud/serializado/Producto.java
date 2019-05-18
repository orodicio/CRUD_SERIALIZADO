package Crud.serializado;
/**
 * Write a description of class Producto here.
 * 
 * @author (orodicio)
 * @version (1.0)
 */
import java.io.Serializable;

public class Producto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int codigo; // Código del producto, se utiliza para buscar
	private String nombre; // Nombre un texto
	private int stock; // existencia actuales
	private int stock_min; // Número mínimo de existencias recomedadas
	private float precio; // Precio

	/**
	 * Constructor for objects of class Producto
	 */

	public Producto(int codigo, String nombre, int stock, int stock_min, float precio) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.stock = stock;
		this.stock_min = stock_min;
		this.precio = precio;
	}

	public int getCodigo() {
		return codigo;
	}

	@Override
	public String toString() {
		return codigo + ":" + nombre + ":" + stock+ ":"+ stock_min + ":"+ precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int valor) {
		stock = valor;
	}

	public int getStock_min() {
		return stock_min;
	}

	public void setStock_min(int valor) {
		stock_min = valor;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float valor) {
		precio = valor;
	}
}
