package comodines;

/**
 * Demuestra el uso de comodines * y .. en pointcuts.
 *
 * Reglas:
 *   *   sustituye exactamente UN token (un parámetro, una clase, etc.)
 *   ..  sustituye CERO O MÁS tokens
 *
 * Pointcuts definidos:
 *   twoParams()  → call(void AOPDemo.method1(*, *))
 *                  intercepta method1 con EXACTAMENTE 2 parámetros
 *
 *   anyParams()  → call(void AOPDemo.*(..))
 *                  intercepta CUALQUIER método de AOPDemo con CUALQUIER nº de parámetros
 *
 *   oneParam()   → call(void AOPDemo.method1(*))
 *                  intercepta method1 con EXACTAMENTE 1 parámetro
 *
 * Observar en la salida:
 *   method1(int)        → dispara oneParam  y anyParams
 *   method1(int,String) → dispara twoParams y anyParams  (NO oneParam)
 *   method2(String)     → dispara anyParams (NO oneParam porque el nombre es method1)
 */
public aspect DemoAspect {

    // Exactamente 2 parámetros en method1
    pointcut twoParams() : call(void AOPDemo.method1(*, *));

    // Cualquier método de AOPDemo con cualquier número de parámetros
    pointcut anyParams() : call(void AOPDemo.*(..));

    // method1 con exactamente 1 parámetro (solo la sobrecarga int)
    pointcut oneParam() : call(void AOPDemo.method1(*));

    before() : oneParam() {
        System.out.println("  [ASPECTO] before — oneParam: interceptó method1 con 1 parámetro");
    }

    after() : oneParam() {
        System.out.println("  [ASPECTO] after  — oneParam: method1(un param) terminó");
    }

    before() : twoParams() {
        System.out.println("  [ASPECTO] before — twoParams: interceptó method1 con 2 parámetros");
    }

    before() : anyParams() && !oneParam() && !twoParams() {
        System.out.println("  [ASPECTO] before — anyParams (otros): " +
                thisJoinPoint.getSignature().getName());
    }
}
