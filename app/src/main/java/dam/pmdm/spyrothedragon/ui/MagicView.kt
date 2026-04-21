package dam.pmdm.spyrothedragon.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class MagicView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private var radius = 0f
    private var alphaValue = 255
    private val colors = intArrayOf(Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.WHITE)
    private var colorIndex = 0

    // Función para iniciar la animación
    fun startAnimation() {
        visibility = VISIBLE
        radius = 0f
        alphaValue = 255
        invalidate() // Llama a onDraw
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Dibujamos varios círculos superpuestos para el efecto de "brillo"
        paint.color = colors[colorIndex]
        paint.alpha = alphaValue

        // Círculo central (diamante)
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)

        // Efecto de halo/onda
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        canvas.drawCircle(width / 2f, height / 2f, radius * 1.5f, paint)

        // Lógica de progresión
        if (radius < width / 3f) {
            radius += 4f
            alphaValue -= 5
            if (alphaValue < 0) alphaValue = 0

            // Cambiamos el color aleatoriamente para el efecto mágico
            colorIndex = (colors.indices).random()

            postInvalidateDelayed(20) // 20ms para una animación fluida
        } else {
            visibility = GONE // Se oculta al terminar la energía
        }
    }
}