package com.tutorial.advanced;

/**
 * Clase principal para los ejemplos avanzados de AspectJ.
 * Demuestra performance monitoring, retry automático y seguridad.
 */
public class MainAvanzado {

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("    TUTORIAL ASPECTJ — EJEMPLOS AVANZADOS      ");
        System.out.println("═══════════════════════════════════════════════\n");

        // ─── DEMO 1: Performance Monitoring ────────────────────────
        System.out.println("──── DEMO 1: PERFORMANCE MONITORING ────");
        ApiController controller = new ApiController();

        controller.procesarPedido(1001);
        controller.procesarPedido(1002);
        controller.verificarInventario("SKU-999");
        controller.generarReporte("VENTAS_MENSUAL"); // Esta será lenta
        controller.enviarNotificacion("gerencia@empresa.com", "Reporte listo");
        controller.procesarPedido(1003);

        // Ver reporte estadístico completo
        PerformanceAspect.imprimirReporte();

        // ─── DEMO 2: Retry Automático ───────────────────────────────
        System.out.println("\n──── DEMO 2: RETRY AUTOMÁTICO ────");
        System.out.println("Escenario: API que falla las primeras 2 veces\n");

        ServicioExterno servicioExterno = new ServicioExterno(2);

        try {
            String resultado = servicioExterno.llamarApiExterna("/api/datos");
            System.out.println("  ✅ Resultado final: " + resultado);
        } catch (Exception e) {
            System.out.println("  ❌ Fallo definitivo: " + e.getMessage());
        }

        // ─── DEMO 3: Retry que agota todos los intentos ────────────
        System.out.println("\n──── DEMO 3: RETRY AGOTADO (falla 5 veces) ────");
        ServicioExterno servicioSiempreFalla = new ServicioExterno(10);

        try {
            servicioSiempreFalla.llamarApiExterna("/api/datos-criticos");
        } catch (Exception e) {
            System.out.println("  ❌ Resultado: " + e.getMessage());
        }

        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("  FIN DE LA DEMO AVANZADA");
        System.out.println("  Retry + Performance Monitoring sin código repetido");
        System.out.println("═══════════════════════════════════════════════");
    }
}
