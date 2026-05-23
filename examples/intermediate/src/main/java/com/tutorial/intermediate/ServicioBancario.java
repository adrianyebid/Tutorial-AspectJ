package com.tutorial.intermediate;

/**
 * Servicio bancario con operaciones de diferentes niveles de seguridad.
 *
 * Nota: El código de autenticación NO está aquí. El aspecto
 * AutenticacionAspect lo maneja automáticamente al detectar
 * la anotación @RequiereAutenticacion.
 */
public class ServicioBancario {

    @RequiereAutenticacion(roles = {"USER", "ADMIN"})
    public double consultarSaldo(String cuentaId) {
        System.out.println("  [Banco] Consultando saldo de cuenta: " + cuentaId);
        return 15_750.50;
    }

    @RequiereAutenticacion(roles = {"USER", "ADMIN"})
    public String[] obtenerMovimientos(String cuentaId, int cantidad) {
        System.out.println("  [Banco] Obteniendo últimos " + cantidad
                + " movimientos de cuenta: " + cuentaId);
        return new String[]{
            "2026-05-01: Depósito $500.00",
            "2026-05-10: Retiro $200.00",
            "2026-05-15: Transferencia enviada $300.00"
        };
    }

    @RequiereAutenticacion(roles = {"ADMIN"})
    public void transferir(String cuentaOrigen, String cuentaDestino, double monto) {
        System.out.printf("  [Banco] Transferencia: %s → %s | Monto: $%.2f%n",
                cuentaOrigen, cuentaDestino, monto);
    }

    @RequiereAutenticacion(roles = {"ADMIN"})
    public void bloquearCuenta(String cuentaId) {
        System.out.println("  [Banco] Cuenta " + cuentaId + " BLOQUEADA");
    }

    // Este método NO requiere autenticación (es público)
    public String obtenerNombreTitular(String cuentaId) {
        return "Juan García";
    }
}
