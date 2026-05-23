package com.tutorial.basic;

/**
 * Servicio de productos — código de negocio PURO.
 *
 * Observar: NO hay ningún código de logging ni de medición de tiempo aquí.
 * Esos concerns los maneja el aspecto LoggingAspect y TimingAspect.
 *
 * Esto ilustra el principio de Separación de Responsabilidades (SRP):
 * esta clase se preocupa SOLO por la lógica de productos.
 */
public class ServicioProducto {

    public String buscarProducto(String id) {
        simularOperacion(50); // Simular tiempo de búsqueda en BD
        return "Producto[id=" + id + ", nombre=Widget Pro, precio=$99.99]";
    }

    public void actualizarPrecio(String id, double nuevoPrecio) {
        simularOperacion(30);
        System.out.println("  [Negocio] Precio del producto " + id
                + " actualizado a $" + nuevoPrecio);
    }

    public void eliminarProducto(String id) {
        simularOperacion(20);
        System.out.println("  [Negocio] Producto " + id + " eliminado del catálogo");
    }

    public String[] listarProductos() {
        simularOperacion(120); // Esta consulta es más lenta
        return new String[]{
            "Producto[P001, Widget Pro, $99.99]",
            "Producto[P002, Gadget Plus, $149.99]",
            "Producto[P003, Super Tool, $249.99]"
        };
    }

    private void simularOperacion(long milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
