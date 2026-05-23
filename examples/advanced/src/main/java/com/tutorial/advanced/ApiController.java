package com.tutorial.advanced;

/**
 * Simula un controlador de API REST.
 * Todos los métodos son monitoreados por PerformanceAspect automáticamente.
 */
public class ApiController {

    public String procesarPedido(int pedidoId) {
        simularTrabajo(80);
        return "Pedido #" + pedidoId + " procesado correctamente";
    }

    public String generarReporte(String tipo) {
        simularTrabajo(350); // Operación más lenta — debería activar alerta
        return "Reporte de tipo '" + tipo + "' generado con 1000 registros";
    }

    public boolean verificarInventario(String productoId) {
        simularTrabajo(15);
        return true;
    }

    public void enviarNotificacion(String destinatario, String mensaje) {
        simularTrabajo(200);
        System.out.println("  [Notif] Email enviado a " + destinatario);
    }

    private void simularTrabajo(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
