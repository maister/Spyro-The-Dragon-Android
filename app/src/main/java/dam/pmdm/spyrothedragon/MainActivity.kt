package dam.pmdm.spyrothedragon

import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        navHostFragment?.let {
            navController = NavHostFragment.findNavController(it)
            NavigationUI.setupWithNavController(binding.navView, navController!!)
            NavigationUI.setupActionBarWithNavController(this, navController!!)
        }

        binding.navView.setOnItemSelectedListener { selectedBottomMenu(it) }

        // --- LÓGICA DE PERSISTENCIA (MOSTRADO ÚNICO) ---
        val prefs = getSharedPreferences("GuiaPrefs", MODE_PRIVATE)
        if (prefs.getBoolean("isFirstRun", true)) {
            // Retraso para asegurar que la UI y el NavController están listos
            binding.root.postDelayed({ showStep1() }, 800)
        }
    }

    // --- FUNCIÓN PARA REPRODUCIR SONIDOS (Apartado D) ---
    private fun reproducirSonido(resourceId: Int) {
        try {
            val mediaPlayer = MediaPlayer.create(this, resourceId)
            mediaPlayer?.let {
                it.setOnCompletionListener { player -> player.release() }
                it.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // --- PASO 1: PANTALLA DE BIENVENIDA ---
    private fun showStep1() {
        val rootView = findViewById<ViewGroup>(android.R.id.content)
        val view = layoutInflater.inflate(R.layout.pantalla_guia_bienvenida, rootView, false)
        rootView.addView(view)

        // Botón Comenzar -> Sonido bat1 y avanza
        view.findViewById<Button>(R.id.btnComenzar)?.setOnClickListener {
            reproducirSonido(R.raw.bat1)
            rootView.removeView(view)
            showStep2(rootView)
        }

        // Botón Omitir -> Sonido bat1 y finaliza
        view.findViewById<View>(R.id.btnOmitir)?.setOnClickListener {
            reproducirSonido(R.raw.bat1)
            finishGuide(rootView, view)
        }
    }

    // --- PASO 2: PERSONAJES ---
    private fun showStep2(rootView: ViewGroup) {
        val view = inflateStep(rootView)
        view.findViewById<TextView>(R.id.txtGuia).text = "Aquí puedes ver todos los personajes de Spyro."

        positionBocadillo(view, R.id.nav_characters)

        view.findViewById<View>(R.id.btnInvisibleSiguiente)?.setOnClickListener {
            reproducirSonido(R.raw.bat2) // Sonido de avance
            rootView.removeView(view)
            showStep3(rootView)
        }
        view.findViewById<View>(R.id.btnOmitir)?.setOnClickListener {
            reproducirSonido(R.raw.bat1)
            finishGuide(rootView, view)
        }
    }

    // --- PASO 3: MUNDOS ---
    private fun showStep3(rootView: ViewGroup) {
        navController?.navigate(R.id.navigation_worlds)

        val view = inflateStep(rootView)
        view.findViewById<TextView>(R.id.txtGuia).text = "Explora los mundos mágicos que Spyro debe recorrer."

        positionBocadillo(view, R.id.nav_worlds)

        view.findViewById<View>(R.id.btnInvisibleSiguiente)?.setOnClickListener {
            reproducirSonido(R.raw.bat2) // Sonido de avance
            rootView.removeView(view)
            showStep4(rootView)
        }
        view.findViewById<View>(R.id.btnOmitir)?.setOnClickListener {
            reproducirSonido(R.raw.bat1)
            finishGuide(rootView, view)
        }
    }

    // --- PASO 4: COLECCIONABLES ---
    private fun showStep4(rootView: ViewGroup) {
        navController?.navigate(R.id.navigation_collectibles)

        val view = inflateStep(rootView)
        view.findViewById<TextView>(R.id.txtGuia).text = "Gestiona tus tesoros y objetos encontrados."

        positionBocadillo(view, R.id.nav_collectibles)

        view.findViewById<View>(R.id.btnInvisibleSiguiente)?.setOnClickListener {
            reproducirSonido(R.raw.bat2) // Sonido de avance
            rootView.removeView(view)
            showStep5(rootView)
        }
        view.findViewById<View>(R.id.btnOmitir)?.setOnClickListener {
            reproducirSonido(R.raw.bat1)
            finishGuide(rootView, view)
        }
    }

    // --- PASO 5: INFORMACIÓN ---
    private fun showStep5(rootView: ViewGroup) {
        val view = inflateStep(rootView)
        view.findViewById<TextView>(R.id.txtGuia).text = "Pulsa el icono superior para ver información de la App."

        positionBocadillo(view, R.id.nav_worlds) // Centrado

        view.findViewById<View>(R.id.btnInvisibleSiguiente)?.setOnClickListener {
            reproducirSonido(R.raw.bat2) // Sonido de avance
            rootView.removeView(view)
            showStep6(rootView)
        }
        view.findViewById<View>(R.id.btnOmitir)?.setOnClickListener {
            reproducirSonido(R.raw.bat1)
            finishGuide(rootView, view)
        }
    }

    // --- PASO 6: FINAL ---
    private fun showStep6(rootView: ViewGroup) {
        val view = inflateStep(rootView)
        view.findViewById<TextView>(R.id.txtGuia).text = "¡Eso es todo! Disfruta de tu aventura en Spyro Guide."

        positionBocadillo(view, R.id.nav_worlds)

        view.findViewById<View>(R.id.btnInvisibleSiguiente)?.setOnClickListener {
            reproducirSonido(R.raw.bat3) // Sonido final épico
            finishGuide(rootView, view)
            Toast.makeText(this, "¡Guía completada!", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<View>(R.id.btnOmitir)?.visibility = View.GONE
    }

    // --- CIERRE Y GUARDADO ---
    private fun finishGuide(rootView: ViewGroup, currentView: View) {
        rootView.removeView(currentView)
        val prefs = getSharedPreferences("GuiaPrefs", MODE_PRIVATE)
        prefs.edit().putBoolean("isFirstRun", false).apply()
    }

    // --- MÉTODOS AUXILIARES ---

    private fun inflateStep(rootView: ViewGroup): ConstraintLayout {
        val view = layoutInflater.inflate(R.layout.guia_bocadillo, rootView, false) as ConstraintLayout
        rootView.addView(view)

        // Apartado C: Animación de los bocadillos
        val bocadillo = view.findViewById<View>(R.id.bocadilloContenedor)
        val anim = AnimationUtils.loadAnimation(this, R.anim.aparecer)
        bocadillo.startAnimation(anim)

        return view
    }

    private fun positionBocadillo(view: ConstraintLayout, navId: Int) {
        val cs = ConstraintSet()
        cs.clone(view)
        val bocadilloId = R.id.bocadilloContenedor
        val btnId = R.id.btnInvisibleSiguiente

        cs.clear(bocadilloId, ConstraintSet.START); cs.clear(bocadilloId, ConstraintSet.END)
        cs.clear(btnId, ConstraintSet.START); cs.clear(btnId, ConstraintSet.END)

        when (navId) {
            R.id.nav_characters -> {
                cs.connect(bocadilloId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 60)
                cs.connect(btnId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            }
            R.id.nav_worlds -> {
                cs.centerHorizontally(bocadilloId, ConstraintSet.PARENT_ID)
                cs.centerHorizontally(btnId, ConstraintSet.PARENT_ID)
            }
            R.id.nav_collectibles -> {
                cs.connect(bocadilloId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 60)
                cs.connect(btnId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            }
        }
        cs.applyTo(view)
    }

    private fun selectedBottomMenu(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_characters -> navController?.navigate(R.id.navigation_characters)
            R.id.nav_worlds -> navController?.navigate(R.id.navigation_worlds)
            else -> navController?.navigate(R.id.navigation_collectibles)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_info) { showInfoDialog(); true }
        else super.onOptionsItemSelected(item)
    }

    private fun showInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.title_about)
            .setMessage(R.string.text_about)
            .setPositiveButton(R.string.accept, null)
            .show()
    }
}