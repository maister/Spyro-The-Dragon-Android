package dam.pmdm.spyrothedragon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dam.pmdm.spyrothedragon.R
import dam.pmdm.spyrothedragon.models.Character
import dam.pmdm.spyrothedragon.ui.MagicView // Asegúrate de haber creado esta clase

class CharactersAdapter(
    private val list: List<Character>
) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    private val characterImages = mapOf(
        "spyro" to R.drawable.spyro,
        "hunter" to R.drawable.hunter,
        "elora" to R.drawable.elora,
        "ripto" to R.drawable.ripto
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character = list[position]
        holder.nameTextView.text = character.name

        val drawableRes = characterImages[character.image] ?: R.drawable.placeholder
        holder.imageImageView.setImageResource(drawableRes)

        // --- EASTER EGG RIPTO: Pulsación prolongada ---
        holder.itemView.setOnLongClickListener {
            // Verificamos si el personaje es Ripto (ajusta "Ripto" si en tu XML de datos está en minúsculas)
            if (character.name.equals("Ripto", ignoreCase = true)) {
                holder.magicView.startAnimation()
                true // Evento consumido
            } else {
                false // No es Ripto, no hacemos nada
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val imageImageView: ImageView = itemView.findViewById(R.id.image)
        // Referencia a la vista personalizada que añadiremos al XML
        val magicView: MagicView = itemView.findViewById(R.id.magicEffect)
    }
}