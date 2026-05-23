package com.tutorial.advanced;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ASPECTO AVANZADO: Monitoreo de performance con estadísticas acumuladas.
 *
 * Registra para cada método:
 * - Total de llamadas
 * - Tiempo total acumulado
 * - Tiempo promedio
 * - Tiempo máximo y mínimo
 * - Tasa de errores
 *
 * Al final genera un reporte completo similar a lo que haría
 * Micrometer/Actuator en Spring Boot.
 */
@Aspect
public class PerformanceAspect {

    private static final long UMBRAL_ALERTA_MS = 200;

    // Estructura: nombreMetodo → [llamadas, tiempoTotal, tiempoMax, tiempoMin, errores]
    private static final Map<String, long[]> metricas = new ConcurrentHashMap<>();

    // Índices en el arreglo de métricas
    private static final int IDX_LLAMADAS   = 0;
    private static final int IDX_TIEMPO     = 1;
    private static final int IDX_MAX        = 2;
    private static final int IDX_MIN        = 3;
    private static final int IDX_ERRORES    = 4;

    @Pointcut("execution(* com.tutorial.advanced.*.*(..))"
            + " && !within(PerformanceAspect)"
            + " && !within(RetryAspect)")
    public void metodosAMonitorear() {}

    @Around("metodosAMonitorear()")
    public Object monitorear(ProceedingJoinPoint pjp) throws Throwable {
        String clave = pjp.getTarget().getClass().getSimpleName()
                + "." + pjp.getSignature().getName() + "()";

        // Inicializar métricas si es la primera llamada
        metricas.computeIfAbsent(clave, k -> new long[]{0, 0, Long.MIN_VALUE, Long.MAX_VALUE, 0});

        long inicio = System.currentTimeMillis();
        boolean exitoso = true;

        try {
            return pjp.proceed();
        } catch (Throwable t) {
            exitoso = false;
            metricas.get(clave)[IDX_ERRORES]++;
            throw t;
        } finally {
            long duracion = System.currentTimeMillis() - inicio;
            long[] m = metricas.get(clave);

            m[IDX_LLAMADAS]++;
            m[IDX_TIEMPO] += duracion;
            m[IDX_MAX] = Math.max(m[IDX_MAX], duracion);
            m[IDX_MIN] = Math.min(m[IDX_MIN], duracion);

            String icono = exitoso ? "📊" : "❌";
            System.out.printf("%s [PERF] %s | %dms%s%n",
                    icono, clave, duracion,
                    duracion > UMBRAL_ALERTA_MS ? " ⚠️  LENTO!" : "");
        }
    }

    public static void imprimirReporte() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║               REPORTE DE PERFORMANCE                      ║");
        System.out.println("╠════════════════════╦═════════╦═══════╦═══════╦══════╦═════╣");
        System.out.println("║ Método             ║ Llamadas║Prom ms║ Max ms║Min ms║Errrs║");
        System.out.println("╠════════════════════╬═════════╬═══════╬═══════╬══════╬═════╣");

        new TreeMap<>(metricas).forEach((metodo, m) -> {
            long llamadas   = m[IDX_LLAMADAS];
            long tiempoTotal = m[IDX_TIEMPO];
            long max        = m[IDX_MAX] == Long.MIN_VALUE ? 0 : m[IDX_MAX];
            long min        = m[IDX_MIN] == Long.MAX_VALUE ? 0 : m[IDX_MIN];
            long promedio   = llamadas > 0 ? tiempoTotal / llamadas : 0;
            long errores    = m[IDX_ERRORES];
            String nombre   = metodo.length() > 20
                    ? metodo.substring(0, 17) + "..."
                    : metodo;

            System.out.printf("║ %-20s║ %7d ║ %5d ║ %5d ║ %4d ║ %3d ║%n",
                    nombre, llamadas, promedio, max, min, errores);
        });

        System.out.println("╚════════════════════╩═════════╩═══════╩═══════╩══════╩═════╝\n");
    }
}
