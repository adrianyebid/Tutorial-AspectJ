package com.tutorial.basic;

/**
 * Clase principal para los ejemplos básicos de AspectJ.
 *
 * Al ejecutar este main, el código de ServicioProducto ya tiene
 * tejidos (woven) los aspectos LoggingAspect y TimingAspect.
 * Aunque ServicioProducto no tiene ni una línea de logging,
 * veremos los logs en la salida gracias a los aspectos.
 */
public class MainBasico {

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("   TUTORIAL ASPECTJ — EJEMPLOS BÁSICOS     ");
        System.out.println("═══════════════════════════════════════════\n");

        ServicioProducto servicio = new ServicioProducto();

        // ─── DEMO 1: Logging automático ────────────────────────────
        System.out.println("──── DEMO 1: LOGGING AUTOMÁTICO ────");
        System.out.println("Llamando a buscarProducto('P001'):");
        String producto = servicio.buscarProducto("P001");
        System.out.println("  Resultado: " + producto);

        System.out.println();
        System.out.println("Llamando a actualizarPrecio('P001', 149.99):");
        servicio.actualizarPrecio("P001", 149.99);

        System.out.println();
        System.out.println("Llamando a eliminarProducto('P003'):");
        servicio.eliminarProducto("P003");

        // ─── DEMO 2: Medición de tiempo (Around) ───────────────────
        System.out.println("\n──── DEMO 2: MEDICIÓN DE TIEMPO (AROUND) ────");
        System.out.println("Llamando a listarProductos() — operación más lenta:");
        String[] lista = servicio.listarProductos();
        System.out.println("  Productos encontrados: " + lista.length);

        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("  FIN DE LA DEMO BÁSICA");
        System.out.println("  Observar: ServicioProducto NO tiene código");
        System.out.println("  de logging ni timing — ¡los puso AspectJ!");
        System.out.println("═══════════════════════════════════════════");
    }
}
