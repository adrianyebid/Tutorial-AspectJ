package com.tutorial.intermediate;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;

/**
 * ASPECTO: Verificación automática de autenticación y autorización.
 *
 * Este aspecto intercepta todos los métodos anotados con @RequiereAutenticacion
 * y verifica que:
 * 1. Haya un usuario autenticado en la sesión actual
 * 2. El usuario tenga al menos uno de los roles requeridos
 *
 * Si la verificación falla, lanza SecurityException ANTES de ejecutar el método.
 * Si la verificación pasa, procede con la ejecución normal.
 */
@Aspect
public class AutenticacionAspect {

    // Sesión simulada (en producción sería ThreadLocal o SecurityContext de Spring)
    private static String usuarioActual = null;
    private static String[] rolesActuales = {};

    // ─── Métodos de sesión ─────────────────────────────────────────

    public static void login(String usuario, String... roles) {
        usuarioActual = usuario;
        rolesActuales = roles;
        System.out.println("🔐 [AUTH] Login exitoso: " + usuario
                + " | Roles: " + Arrays.toString(roles));
    }

    public static void logout() {
        System.out.println("🔓 [AUTH] Logout: " + usuarioActual);
        usuarioActual = null;
        rolesActuales = new String[]{};
    }

    public static String getUsuarioActual() {
        return usuarioActual != null ? usuarioActual : "anónimo";
    }

    // ─── El Advice principal ────────────────────────────────────────

    /**
     * Pointcut especial: @annotation(requiereAutenticacion)
     *
     * Este pointcut coincide con cualquier método que tenga la anotación
     * @RequiereAutenticacion. AspectJ además inyecta la instancia de la
     * anotación como parámetro, lo que nos permite leer sus atributos (roles).
     *
     * Sintaxis: el nombre del parámetro DEBE coincidir con el nombre en @Around
     */
    @Around("@annotation(requiereAutenticacion)")
    public Object verificarAcceso(ProceedingJoinPoint pjp,
                                   RequiereAutenticacion requiereAutenticacion) throws Throwable {

        String metodo = pjp.getSignature().getName();
        String[] rolesRequeridos = requiereAutenticacion.roles();

        System.out.println("🔍 [AUTH] Verificando acceso a: " + metodo
                + " | Requiere roles: " + Arrays.toString(rolesRequeridos));

        // VERIFICACIÓN 1: ¿Hay usuario autenticado?
        if (usuarioActual == null || usuarioActual.isBlank()) {
            throw new SecurityException(
                    "❌ [AUTH] Acceso denegado a '" + metodo
                    + "': No hay sesión activa. Use login() primero.");
        }

        // VERIFICACIÓN 2: ¿El usuario tiene alguno de los roles requeridos?
        boolean tienePermiso = Arrays.stream(rolesRequeridos)
                .anyMatch(rolRequerido ->
                        Arrays.asList(rolesActuales).contains(rolRequerido));

        if (!tienePermiso) {
            throw new SecurityException(
                    "❌ [AUTH] Acceso denegado a '" + metodo + "' para usuario '"
                    + usuarioActual + "'. Requiere: " + Arrays.toString(rolesRequeridos)
                    + " | Tiene: " + Arrays.toString(rolesActuales));
        }

        // ACCESO CONCEDIDO: proceder con el método original
        System.out.println("✅ [AUTH] Acceso concedido a '" + metodo
                + "' para usuario: " + usuarioActual);

        return pjp.proceed(); // Ejecutar el método real
    }
}
