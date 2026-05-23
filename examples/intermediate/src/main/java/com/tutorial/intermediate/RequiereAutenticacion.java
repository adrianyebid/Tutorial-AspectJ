package com.tutorial.intermediate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación personalizada para marcar métodos que requieren autenticación.
 *
 * @Retention(RUNTIME) → La anotación existe en tiempo de ejecución (necesario para AOP)
 * @Target(METHOD)     → Solo se puede aplicar a métodos
 *
 * El aspecto AutenticacionAspect detectará esta anotación e interceptará
 * los métodos anotados para verificar que el usuario esté autenticado y
 * tenga los roles necesarios.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiereAutenticacion {
    /**
     * Roles permitidos para ejecutar el método.
     * Por defecto, cualquier usuario autenticado puede ejecutarlo.
     */
    String[] roles() default {"USER"};
}
