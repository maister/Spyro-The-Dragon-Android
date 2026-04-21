package dam.pmdm.spyrothedragon

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoEasterEggActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Creamos el VideoView dinámicamente para que ocupe toda la pantalla
        val videoView = VideoView(this)
        setContentView(videoView)

        // Ponemos la actividad en modo pantalla completa real (ocultar barras)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        // Ruta al vídeo en res/raw (asegúrate de que el archivo se llame video_easter_egg.mp4)
        val videoUri = Uri.parse("android.resource://$packageName/${R.raw.video_easter_egg}")
        videoView.setVideoURI(videoUri)

        // Al terminar el vídeo, cerramos la actividad para volver a Mundos
        videoView.setOnCompletionListener {
            finish()
        }

        videoView.start()
    }
}