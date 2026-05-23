package com.tutorial.advanced;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simula un servicio externo (API REST, base de datos, microservicio)
 * que puede fallar de forma transitoria.
 *
 * En el mundo real, estas fallas son comunes:
 * - Timeout de red
 * - Servicio momentáneamente caído
 * - Límite de rate de la API alcanzado
 * - Conexión rechazada temporalmente
 */
public class ServicioExterno {

    private final AtomicInteger contadorLlamadas = new AtomicInteger(0);
    private final int fallasDespuesDe; // Cuántas veces falla antes de tener éxito

    public ServicioExterno(int fallasDespuesDe) {
        this.fallasDespuesDe = fallasDespuesDe;
    }

    /**
     * @Retryable: el aspecto RetryAspect interceptará este método
     * y lo reintentará hasta 3 veces si lanza RuntimeException.
     */
    @Retryable(maxIntentos = 3, esperaMs = 100, on = {RuntimeException.class})
    public String llamarApiExterna(String endpoint) {
        int llamada = contadorLlamadas.incrementAndGet();
        System.out.printf("  [API] Llamada #%d a endpoint: %s%n", llamada, endpoint);

        if (llamada <= fallasDespuesDe) {
            throw new RuntimeException("Timeout conectando a " + endpoint
                    + " (intento " + llamada + ")");
        }

        return "{ \"status\": \"ok\", \"data\": \"respuesta del servidor\", " +
               "\"llamada\": " + llamada + " }";
    }

    @Retryable(maxIntentos = 5, esperaMs = 50, on = {IllegalStateException.class})
    public String consultarServicioInestable(String recurso) {
        int llamada = contadorLlamadas.incrementAndGet();

        // Falla aleatoriamente el 60% de las veces
        if (Math.random() < 0.6) {
            throw new IllegalStateException(
                    "Servicio temporalmente no disponible para: " + recurso);
        }

        return "Recurso obtenido: " + recurso + " (en llamada #" + llamada + ")";
    }
}
