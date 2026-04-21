package dam.pmdm.spyrothedragon.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dam.pmdm.spyrothedragon.R
import dam.pmdm.spyrothedragon.VideoEasterEggActivity // Importamos tu actividad del vídeo
import dam.pmdm.spyrothedragon.models.World

class WorldsAdapter(
    private val list: List<World>
) : RecyclerView.Adapter<WorldsAdapter.WorldsViewHolder>() {

    // --- VARIABLES PARA EL EASTER EGG ---
    private var clickCount = 0
    private var lastClickTime: Long = 0
    private var lastPosition: Int = -1
    // ------------------------------------

    private val worldImages = mapOf(
        "sunny_beach" to R.drawable.sunny_beach,
        "midday_gardens" to R.drawable.midday_gardens,
        "autumn_plains" to R.drawable.autumn_plains,
        "glimmer" to R.drawable.glimmer,
        "cloud_spires" to R.drawable.cloud_spires,
        "hurricane_halls" to R.drawable.hurricane_halls,
        "frozen_altars" to R.drawable.frozen_altars,
        "lost_fleet" to R.drawable.lost_fleet,
        "sunset_beach" to R.drawable.sunset_beach
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorldsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview, parent, false)
        return WorldsViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorldsViewHolder, position: Int) {
        val world = list[position]
        holder.nameTextView.text = world.name

        val drawableRes = worldImages[world.image] ?: R.drawable.placeholder
        holder.imageImageView.setImageResource(drawableRes)

        // --- LÓGICA DEL TRIPLE CLIC ---
        holder.itemView.setOnClickListener {
            val currentTime = System.currentTimeMillis()

            // Verificamos si es el mismo mundo y si el clic es rápido (menos de 500ms)
            if (position == lastPosition && (currentTime - lastClickTime) < 500) {
                clickCount++
            } else {
                clickCount = 1 // Reset si cambia de mundo o tarda mucho
            }

            lastClickTime = currentTime
            lastPosition = position

            if (clickCount == 3) {
                clickCount = 0 // Reiniciamos para que pueda volver a hacerlo

                val context = holder.itemView.context
                val intent = Intent(context, VideoEasterEggActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class WorldsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val imageImageView: ImageView = itemView.findViewById(R.id.image)
    }
}