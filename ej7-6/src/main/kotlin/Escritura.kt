import java.io.File
import java.io.PrintWriter

class Escritura(ruta: String) {
    val file = File(ruta)
    val writer = PrintWriter(file)
    fun generar(informacion: String){
        writer.append(informacion)
        writer.close()}
}