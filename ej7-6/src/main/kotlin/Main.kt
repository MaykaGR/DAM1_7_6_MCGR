import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlin.math.pow
import kotlin.math.sqrt

fun media(datos: MutableList<String>):Float{
    val lista = datos[0].split(" ")
    val size = lista.size.toFloat()
    var suma = 0F
    for(i in 0..lista.size-1){
        suma = suma + lista[i].toFloat()
    }
    val media = suma/size
    return media
}

fun desviacionTipica(datos: MutableList<String>, media: Float):Float{
    val lista = datos[0].split(" ")
    val size = lista.size.toFloat()
    val param = 1/(size-1)
    val listaSumatoria: MutableList<Float> = mutableListOf()
    for(i in 0..lista.size-1){
        listaSumatoria.add((lista[i].toFloat()-media).pow(2))
    }
    var suma = 0F
    for(i in 0..listaSumatoria.size-1){
        suma = suma + listaSumatoria[i]
    }
    val desviacion = sqrt(param*suma)
    return desviacion
}

fun contrastador(media: Float, desviacion: Float):String{
    var diagnostico = ""
    when{
        media < 40F  -> diagnostico = "Calibración del microscopio"
        media >= 40F  -> diagnostico = "Cáncer de piel"
        media <= 80F -> diagnostico = "Cáncer de piel"
        media > 80F -> diagnostico = "Lunar cutáneo benigno"
        media <= 230F -> diagnostico = "Lunar cutáneo benigno"
        media > 230F -> diagnostico = "Calibración del microscopio"

    }
    if(diagnostico == "Cáncer de piel"){
        if(desviacion<10F){
            diagnostico = "Lunar cutáneo benigno"
        }
    }
    return diagnostico
}

fun main(args: Array<String>) {
    val parser = ArgParser("Cáncer de piel")
    val input by parser.option(ArgType.String, shortName = "i", description = "Input file")
    val output by parser.option(ArgType.String, shortName = "o", description = "Output file name")

    parser.parse(args)
    val dir = input?:""
    if(dir==""){println("Error, no hay ruta de entrada")}
    val out = output?:""
    if(out==""){println("Error, no hay ruta de salida")}
    val lectura= Lectura(dir)
    val media = media(lectura.datos)
    val desviacion = desviacionTipica(lectura.datos,media)
    val escritura = Escritura(out)
    escritura.generar(contrastador(media,desviacion))
}