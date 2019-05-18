package Crud.serializado;

/*
 * @author orodicio
 * @version 1
 */
import java.util.Scanner;

// Crear la clase Producto y completar los métodos

public class MiAlmacen {
	static private ModeloAbs almacen;
	static Scanner sc;

	public static void main(String[] args) {
		almacen = new ModeloArrayList();
		sc = new Scanner(System.in);
		int opcion = 0;
		do {
			mostrarMenu();
			opcion = leerOpcion(1, 9);
			switch (opcion) {
			case 1:
				crear();
				break;
			case 2:
				consultar();
				break;
			case 3:
				borrar();
				break;
			case 4:
				modificarPrecio();
				break;
			case 5:
				comprar();
				break;
			case 6:
				vender();
				break;
			case 7:
				listar();
				break;
			case 8:
				listarPocoStock();
				break;
			}
			System.out.println("\n---------------------------- ");
			System.out.print("Pulse enter para continuar");
			sc.nextLine();
		} while (opcion != 9);
		if (almacen instanceof ModeloArrayList) {
			ModeloArrayList almacen2 = (ModeloArrayList) almacen;
			almacen2.grabarObjetos();
		}
		System.out.println("Adios");
		sc.close();

	}

	private static void mostrarMenu() {
		System.out.println("\n\n    MENU");
		System.out.println("1. Nuevo producto ");
		System.out.println("2. Consulta producto ");
		System.out.println("3. Borrar producto ");
		System.out.println("4. Modificar precio ");
		System.out.println("5. Compra de productos ");
		System.out.println("6. Venta de productos ");
		System.out.println("7. Listado completo de productos ");
		System.out.println("8. Listado de productos con stock inferior al mínimo");
		System.out.println("9. Terminar ");
		System.out.print("Elige una opción (1-9)");
	}

	// Lee un entero del System.in que este comprendido entre primero y ultimo
	private static int leerOpcion(int primero, int ultimo) {
		int valor = leerEntero();
		while (valor < primero || valor > ultimo) {
			valor = leerEntero();
		}
		return valor;
	}

	// Metodos Auxiliares leerFloat y LeerEntero,
	// Lee de la System.in con el scanner sc y controlan la excepcion de
	// NumberFormatException
	// para leer el precio

	static private float leerFloat() {

		boolean error = false;
		float valor = 0;
		String cadena;
		do {
			error = false;
			try {
				// Intento leer directamente un float
				cadena = sc.nextLine();
				valor = Float.parseFloat(cadena);

			} catch (NumberFormatException e) {
				System.out.println("Error en formato.");
				error = true;
			}
		} while (error);
		return valor;
	}
	// Para leer las opciones, el stock maximo y minimo

	static private int leerEntero() {
		boolean error = false;
		Integer valor = 0;
		String cadena;

		do {
			error = false;
			try {
				// Intento leer directamente un integer
				cadena = sc.nextLine();
				valor = Integer.valueOf(cadena);

			} catch (NumberFormatException e) {
				System.out.println("Error en formato.");
				error = true;
			}
		} while (error);
		return valor;
	}

	// Muestra los datos de un producto a partir de su codigo

	private static void consultar() {
		Producto p = hallarProductoPorCodigo();

		if (p == null) {
			System.out.println("El producto no se encuentra en almacen");
			return;
		}
		System.out.println("PRODUCTO " + p);

	}

	// Borrar un producto a partir de su codigo

	private static void borrar() {
		Producto p = hallarProductoPorCodigo();

		if (p == null) {
			System.out.println("El producto no se encuentra en almacen");
			return;
		}

		System.out.println("PRODUCTO " + p);
		System.out.println("¿Está seguro de que desea eliminar el producto?(s/n)");
		char respuesta = Character.toLowerCase(sc.nextLine().charAt(0));

		if (respuesta == 's' && almacen.borrarProducto(p.getCodigo())) {
			System.out.println("El producto se ha podido borrar");
			return;

		}

		System.out.println("El producto no se ha podido borrar");
		return;

	}

	// Cambia el precio de un producto a partir de su codigo

	private static void modificarPrecio() {
		Producto p = hallarProductoPorCodigo();

		if (p == null) {
			System.out.println("El producto no se encuentra en almacen");
			return;
		}

		System.out.println("PRODUCTO " + p);
		System.out.println("PRECIO ACTUAL:" + p.getPrecio());
		System.out.println("Introduzca nuevo precio:");
		float precio = leerFloat();

		while (precio <= 0) {
			System.out.println("El precio no puede ser menor o igual a cero. Introduzca otra cantidad:");
			precio = leerFloat();
		}

		p.setPrecio(precio);
		boolean resultado = almacen.modificarProducto(p);

		if (!resultado) {
			System.out.println("El precio no se ha podido modificar");
			return;
		}

		System.out.println("El precio se ha modificado");
	}

	// Incrementa el stock

	private static void comprar() {
		Producto p = hallarProductoPorCodigo();

		if (p == null) {
			System.out.println("El producto no se encuentra en almacen");
			return;
		}

		System.out.println("PRODUCTO " + p);
		int stock = stockMayorQueCero();
		int finalstock = p.getStock() + stock;
		p.setStock(finalstock);
		boolean resultado = almacen.modificarProducto(p);

		if (!resultado) {
			System.out.println("El stock no se ha podido modificar");
		}
	}

	// Decrementa el stock

	private static void vender() {
		Producto p = hallarProductoPorCodigo();

		if (p == null) {
			System.out.println("El producto no se encuentra en almacen");
			return;
		}

		System.out.println("PRODUCTO " + p);
		int stock = stockMayorQueCero();
		int finalStock = p.getStock() - stock;

		if (finalStock < 0) {
			System.out.println("No disponemos de suficiente stock. La compra no se puede realizar.");
			return;
		}

		p.setStock(finalStock);
		boolean resultado = almacen.modificarProducto(p);

		if (!resultado) {
			System.out.println("El stock no se ha podido modificar");
		}
	}

	// Listado de todos los productos

	private static void listar() {

		almacen.listarProductos();
	}

	// Listado de todos los productos con stock inferior a stock minimo

	private static void listarPocoStock() {

		if (almacen instanceof ModeloArrayList) {
			ModeloArrayList almacen2 = (ModeloArrayList) almacen;
			almacen2.ListarMenosStockmin();
		}
	}

	// Solicita datos al usuario para dar de alta un nuevo producto
	// El codigo no se puede repetir

	private static void crear() {
		Producto p;
		int codigo = leerCodigo();

		while (almacen.buscarProducto(codigo) != null) {
			System.out.println("Ya existe un producto con ese código.Introduzca otro distinto");
			codigo = leerCodigo();
		}

		System.out.println("Introduzca el nombre");
		String nombre = sc.nextLine();

		System.out.println("Introduzca el precio");
		float precio = leerFloat();

		while (precio <= 0) {
			System.out.println("El precio no puede ser menor o igual a cero. Introduzca otra cantidad:");
			precio = leerFloat();
		}

		int stock = stockMayorQueCero();

		System.out.println("Cantidad minima de stock");
		int stockMin = stockMayorQueCero();

		p = new Producto(codigo, nombre, stock, stockMin, precio);

		almacen.insertarProducto(p);
	}

	// Métodos auxiliares mios

	// Leer codigo

	private static int leerCodigo() {
		System.out.print("Introduzca codigo:");
		int codigo = leerEntero();
		return codigo;
	}

	// Comprobar que el stock que se desea comprar/vender sea mayor que cero

	private static int stockMayorQueCero() {
		System.out.println("Introduzca el stock:");
		int stock = leerEntero();

		while (stock <= 0) {
			System.out.println("Cantidad errónea.Introduzca de nuevo la cantidad:");
			stock = leerEntero();
		}

		return stock;
	}

	// Hallar el producto

	private static Producto hallarProductoPorCodigo() {
		int codigo = leerCodigo();
		Producto p = almacen.buscarProducto(codigo);
		return p;
	}
}
