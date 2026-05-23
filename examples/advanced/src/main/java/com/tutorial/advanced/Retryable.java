package com.tutorial.advanced;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación para marcar métodos que deben reintentarse automáticamente
 * cuando fallan con excepciones transitorias.
 *
 * Ejemplo de uso:
 *   @Retryable(maxIntentos = 3, esperaMs = 100, on = {TimeoutException.class})
 *   public String llamarApi() { ... }
 *
 * El aspecto RetryAspect detecta esta anotación y aplica la lógica de retry
 * automáticamente con backoff exponencial.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Retryable {
    /** Número máximo de intentos (incluyendo el primero) */
    int maxIntentos() default 3;

    /** Tiempo de espera base en ms entre intentos (se multiplica exponencialmente) */
    long esperaMs() default 100;

    /** Tipos de excepción que activan el retry. Por defecto, cualquier Exception */
    Class<? extends Exception>[] on() default {Exception.class};
}
