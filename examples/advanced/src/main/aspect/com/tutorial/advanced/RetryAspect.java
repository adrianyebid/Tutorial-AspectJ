package com.tutorial.advanced;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;

/**
 * ASPECTO AVANZADO: Retry automático con backoff exponencial.
 *
 * Intercepta métodos anotados con @Retryable y los reintenta automáticamente
 * cuando fallan con el tipo de excepción configurado.
 *
 * Backoff exponencial: el tiempo de espera se duplica en cada intento.
 * Intento 1: falla  → espera 100ms
 * Intento 2: falla  → espera 200ms
 * Intento 3: falla  → espera 400ms
 * Intento 4: éxito  ✓
 *
 * Este patrón es fundamental en sistemas distribuidos para manejar
 * fallas transitorias sin sobrecargar los servicios dependientes.
 */
@Aspect
public class RetryAspect {

    /**
     * Intercepta métodos con @Retryable.
     * La anotación se inyecta como parámetro (binding por nombre).
     */
    @Around("@annotation(retryable)")
    public Object ejecutarConRetry(ProceedingJoinPoint pjp,
                                    Retryable retryable) throws Throwable {
        int maxIntentos = retryable.maxIntentos();
        long esperaBaseMs = retryable.esperaMs();
        Class<? extends Exception>[] tiposRetryable = retryable.on();
        String nombreMetodo = pjp.getSignature().getName();

        Exception ultimaExcepcion = null;

        for (int intento = 1; intento <= maxIntentos; intento++) {
            try {
                if (intento == 1) {
                    System.out.printf("🔄 [RETRY] Ejecutando: %s (máx %d intentos)%n",
                            nombreMetodo, maxIntentos);
                } else {
                    System.out.printf("🔄 [RETRY] Reintentando: %s (intento %d/%d)%n",
                            nombreMetodo, intento, maxIntentos);
                }

                // Intentar ejecutar el método original
                Object resultado = pjp.proceed();

                if (intento > 1) {
                    System.out.printf("✅ [RETRY] Éxito en el intento %d/%d%n",
                            intento, maxIntentos);
                }

                return resultado;

            } catch (Exception excepcionActual) {
                ultimaExcepcion = excepcionActual;

                // ¿Deberíamos reintentar para este tipo de excepción?
                boolean esRetryable = esExcepcionRetryable(excepcionActual, tiposRetryable);

                if (!esRetryable) {
                    System.out.printf("🚫 [RETRY] Excepción no retryable (%s): no se reintentará%n",
                            excepcionActual.getClass().getSimpleName());
                    throw excepcionActual;
                }

                if (intento == maxIntentos) {
                    System.out.printf("❌ [RETRY] Agotados todos los intentos (%d/%d). " +
                            "Última causa: %s%n",
                            intento, maxIntentos, excepcionActual.getMessage());
                    throw excepcionActual;
                }

                // Calcular tiempo de espera con backoff exponencial
                long tiempoEspera = esperaBaseMs * (long) Math.pow(2, intento - 1);
                System.out.printf("⏳ [RETRY] Error en intento %d: '%s'. " +
                        "Esperando %dms...%n",
                        intento, excepcionActual.getMessage(), tiempoEspera);

                try {
                    Thread.sleep(tiempoEspera);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw ultimaExcepcion;
                }
            }
        }

        // No debería llegar aquí, pero por seguridad:
        throw ultimaExcepcion != null ? ultimaExcepcion
                : new RuntimeException("Retry fallido sin excepción capturada");
    }

    /**
     * Verifica si la excepción lanzada es del tipo que debe activar retry.
     */
    private boolean esExcepcionRetryable(Exception excepcion,
                                          Class<? extends Exception>[] tiposRetryable) {
        return Arrays.stream(tiposRetryable)
                .anyMatch(tipo -> tipo.isInstance(excepcion));
    }
}
