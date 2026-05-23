package com.tutorial.intermediate;

/**
 * Clase principal para los ejemplos intermedios de AspectJ.
 * Demuestra autenticación, auditoría y manejo de errores como aspectos.
 */
public class MainIntermedio {

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("   TUTORIAL ASPECTJ — EJEMPLOS INTERMEDIOS     ");
        System.out.println("═══════════════════════════════════════════════\n");

        ServicioBancario banco = new ServicioBancario();

        // ─── DEMO 1: Sin autenticación ─────────────────────────────
        System.out.println("──── DEMO 1: SIN AUTENTICACIÓN ────");
        System.out.println("Intentando consultar saldo sin login:");
        try {
            banco.consultarSaldo("CTA-001");
        } catch (SecurityException e) {
            System.out.println("  Resultado: " + e.getMessage());
        }

        // ─── DEMO 2: Login como usuario normal ─────────────────────
        System.out.println("\n──── DEMO 2: LOGIN COMO USUARIO NORMAL ────");
        AutenticacionAspect.login("maria.garcia", "USER");

        System.out.println("\nConsultando saldo (permitido para USER):");
        double saldo = banco.consultarSaldo("CTA-001");
        System.out.println("  Saldo: $" + saldo);

        System.out.println("\nIntentando transferencia (solo ADMIN):");
        try {
            banco.transferir("CTA-001", "CTA-002", 500.0);
        } catch (SecurityException e) {
            System.out.println("  Resultado: " + e.getMessage());
        }

        AutenticacionAspect.logout();

        // ─── DEMO 3: Login como administrador ──────────────────────
        System.out.println("\n──── DEMO 3: LOGIN COMO ADMINISTRADOR ────");
        AutenticacionAspect.login("admin.sistema", "ADMIN", "USER");

        System.out.println("\nTransferencia (permitida para ADMIN):");
        banco.transferir("CTA-001", "CTA-002", 1000.0);

        System.out.println("\nBloqueando cuenta:");
        banco.bloquearCuenta("CTA-999");

        // ─── DEMO 4: Ver historial de auditoría ────────────────────
        System.out.println("\n──── DEMO 4: HISTORIAL DE AUDITORÍA ────");
        AuditoriaAspect.mostrarHistorial();

        AutenticacionAspect.logout();

        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("  FIN DE LA DEMO INTERMEDIA");
        System.out.println("  Autenticación y auditoría sin código en los servicios");
        System.out.println("═══════════════════════════════════════════════");
    }
}
