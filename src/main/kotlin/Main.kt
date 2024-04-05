package org.ejemploficheros

import kotlin.jvm.JvmStatic
import java.io.File
import java.io.IOException
import java.util.*

/*
1. Anotación @JvmStatic.

La anotación @JvmStatic es específica para interoperar con Java.

Kotlin tiene su propio sistema de clases y objetos, pero cuando queremos asegurarnos de que nuestro código Kotlin sea
fácilmente accesible desde código Java, especialmente en casos donde necesitemos definir un método main o cualquier
método estático, utilizamos @JvmStatic.

En el contexto de Java, los métodos estáticos son aquellos que puedes llamar en una clase sin necesidad de crear una
instancia de dicha clase. Kotlin no tiene métodos estáticos de forma nativa porque usa objetos para lograr el mismo
resultado.

Cuando marcamos una función con @JvmStatic dentro de un objeto o un companion object en Kotlin, le indicamos al
compilador de Kotlin que genere bytecode que exponga esa función como un método estático de la clase desde el punto
de vista de Java. Esto significa que el método puede ser llamado desde Java sin necesidad de instanciar la clase que
lo contiene, alineándose con el modelo de clases de Java.

En resumen, la anotación @JvmStatic permite que el método main sea invocado como un punto de entrada estándar por
la JVM (Java Virtual Machine) sin necesidad de crear una instancia del objeto Pruebasarchivos. Es importante si el
código debe ser interoperable con Java o si estás escribiendo una aplicación Kotlin que se ejecuta en un entorno
Java tradicional.

2. Interoperabilidad Kotlin-Java.

En Kotlin, al trabajar con clases de Java que siguen el patrón de diseño "getter" y "setter" para el acceso a
propiedades, se puede acceder a estas mediante una sintaxis de propiedad más simple y directa, que es más natural en
Kotlin. Esto es posible gracias a las convenciones de acceso a propiedades de Kotlin, que automáticamente mapean entre
las llamadas a métodos getter y setter de Java y el acceso a propiedades en Kotlin.

Cuando accedemos a f.parent en Kotlin, el compilador de Kotlin automáticamente lo traduce a una llamada al método
getParent() de la instancia de java.io.File subyacente. De manera similar, si hubiera un método setParent(String parent)
en la clase (lo cual no ocurre con java.io.File, pero es útil como ejemplo), podríamos asignar un valor a esa
"propiedad" con una sintaxis como f.parent = "nuevoValor", y Kotlin traduciría eso a una llamada a setParent("nuevoValor").

Este comportamiento hace que trabajar con código Java en Kotlin sea más idiomático y conciso, permitiendo que el código
Kotlin se sienta más natural y manteniendo las ventajas de la orientación a objetos y encapsulamiento proporcionadas
por los métodos getter y setter en Java.

En resumen, la llamada a f.parent en tu código Kotlin es equivalente a una llamada a f.getParent() debido a la
interoperabilidad de Kotlin con Java y su capacidad para trabajar con las convenciones de nombres de getter y setter
de manera más expresiva.
*/

object PruebasArchivos {
    @JvmStatic
    fun main(args: Array<String>) {
        // Dos rutas absolutas
        val carpetaAbs = File("/home/lionel/fotos")
        val archivoAbs = File("/home/lionel/fotos/albania1.jpg")

        // Dos rutas relativas
        val carpetaRel = File("trabajos")
        val fitxerRel = File("trabajos/documento.txt")

        // Mostremos sus rutas
        mostrarRutas(carpetaAbs)
        mostrarRutas(archivoAbs)
        mostrarRutas(carpetaRel)
        mostrarRutas(fitxerRel)
    }

    fun mostrarRutas(f: File) {
        println("getParent(): " + f.parent)
        println("getName(): " + f.name)
        println("getAbsolutePath(): " + f.absolutePath)
    }
}

fun main() {
    println("Hello World!")
}

fun leerFichero01(rutaFichero: String) {
    // En este fragmento de código, use { ... } asegura que el BufferedReader se cierre después de ejecutar el
    // bloque de código proporcionado, que en este caso es leer todas las líneas del archivo y almacenarlas en text.
    // Esto elimina la necesidad de cerrar explícitamente el flujo de datos de entrada y maneja adecuadamente las
    // excepciones, haciendo que el código sea más seguro y limpio.
    // * Usar el bloque "use" cierra el recurso automáticamente incluso si se lanza una excepción.

    val file = File(rutaFichero)
    val text: List<String>

    try {
        text = file.bufferedReader().use { it.readLines() }
    } catch (e: IOException) {
        // Manejo de IOException para errores de I/O
        println("Error al leer el archivo: ${e.message}")
        return
    } catch (e: Exception) {
        // Manejo de otras excepciones generales
        println("Error inesperado: ${e.message}")
        return
    }

    text.forEach { println(it) }
}

fun leerFichero02(rutaFichero: String) {
    // Si no usamos el bloque "use" use y manejamos el BufferedReader directamente, deberíamos cerrar el flujo
    // manualmente en un bloque "finally" para asegurarnos de que se cierre correctamente incluso si ocurre una
    // excepción.

    val file = File(rutaFichero)
    val bufferedReader = file.bufferedReader()
    val text: List<String> =
        try {
            bufferedReader.readLines()
        }
        catch (e: IOException) {
            println("Ocurrió un error al leer el archivo: ${e.message}")
            emptyList() // Retorna una lista vacía en caso de error
        }
        catch (e: Exception) {
            println("Ocurrió un error inesperado: ${e.message}")
            emptyList()
        }
        finally {
            bufferedReader.close()
        }

    text.forEach { println(it) }
}

fun leerFichero03(rutaFichero: String) {
    // En la mayoría de los casos, usar la abstracción proporcionada por Kotlin con readLines() para leer todas las
    // líneas de un archivo es la opción más recomendable por su simplicidad y seguridad.
    // Este enfoque es conciso, legible y maneja automáticamente los recursos subyacentes, cerrando el flujo de
    // entrada una vez que todas las líneas han sido leídas, lo cual simplifica el manejo de errores y recursos.
    //
    // Por lo tanto, cuando utilizamos readLines():
    // 1- Se abre un flujo de entrada: Internamente, readLines() abre un flujo de entrada para el archivo especificado.
    // 2- Se leen los datos: Lee todas las líneas del archivo, las almacena en una lista y retorna dicha lista.
    // 3- Se cierra el flujo: Cierra el flujo de entrada automáticamente una vez que todas las líneas han sido leídas o
    //    si ocurre una excepción durante el proceso de lectura.

    val file = File(rutaFichero)

    val text: List<String> =
        try {
            file.readLines()
        }
        catch (e: IOException) {
            println("Ocurrió un error al leer el archivo: ${e.message}")
            emptyList() // Retorna una lista vacía en caso de error
        }
        catch (e: Exception) {
            println("Ocurrió un error inesperado: ${e.message}")
            emptyList()
        }

    text.forEach { println(it) }
}

fun leerFicherosGrandes01(rutaFichero: String) {
    // Rendimiento y uso de memoria: Si bien readLines() es muy conveniente para leer archivos pequeños a medianos,
    // puede no ser la mejor opción para archivos muy grandes.
    // Esto se debe a que readLines() carga todas las líneas del archivo en la memoria simultáneamente, lo que podría
    // llevar a un "OutOfMemoryError" si el archivo es demasiado grande.
    // En tales casos, puede ser más apropiado leer el archivo línea por línea manualmente usando un BufferedReader,
    // lo que permite procesar cada línea individualmente y minimizar el uso de memoria.

    val file = File(rutaFichero)
    val text: MutableList<String> = mutableListOf()

    try {
        file.bufferedReader().use { br ->
            do {
                val line: String? = br.readLine()
                if (line != null) {
                    text.add(line)
                }
            } while (line != null)
        }
    }
    catch (e: IOException) {
        println("Ocurrió un error al leer el archivo: ${e.message}")
    }
    catch (e: Exception) {
        println("Ocurrió un error inesperado: ${e.message}")
    }
}

fun leerFicherosGrandes02(rutaFichero: String) {
    //Otra forma de hacer lo mismo...

    val file = File(rutaFichero)
    val text: MutableList<String> = mutableListOf()

    try {
        file.bufferedReader().use { br ->
            var line: String?
            while (br.readLine().also { line = it } != null) {
                // Procesa cada línea individualmente
                line?.let { text.add(it) }
            }
        }
    }
    catch (e: IOException) {
        println("Ocurrió un error al leer el archivo: ${e.message}")
    }
    catch (e: Exception) {
        println("Ocurrió un error inesperado: ${e.message}")
    }
}

fun leerFicherosGrandes03(rutaFichero: String) {
    //Otra forma de hacer lo mismo, por ejemplo para mostrar el contenido de un fichero.

    val file = File(rutaFichero)

    try {
        file.forEachLine { println(it) }
    }
    catch (e: IOException) {
        println("Ocurrió un error al leer el archivo: ${e.message}")
    }
    catch (e: Exception) {
        println("Ocurrió un error inesperado: ${e.message}")
    }
}