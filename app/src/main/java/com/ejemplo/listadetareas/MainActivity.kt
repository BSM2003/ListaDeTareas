package com.ejemplo.listadetareas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ejemplo.listadetareas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listaTareas = mutableListOf<Tarea>()
    private lateinit var adapter: TareaAdapter
    private var contadorId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configurarRecyclerView()
        configurarBotones()
    }

    private fun configurarRecyclerView() {
        adapter = TareaAdapter(listaTareas) { posicion ->
            eliminarTarea(posicion)
        }
        binding.rvTareas.layoutManager = LinearLayoutManager(this)
        binding.rvTareas.adapter = adapter
    }

    private fun configurarBotones() {
        binding.btnAgregar.setOnClickListener {
            val texto = binding.etNuevaTarea.text.toString().trim()
            if (texto.isNotEmpty()) {
                agregarTarea(texto)
                binding.etNuevaTarea.text.clear()
            } else {
                Toast.makeText(this, "Escribe una tarea primero", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun agregarTarea(titulo: String) {
        contadorId++
        val nuevaTarea = Tarea(id = contadorId, titulo = titulo)
        listaTareas.add(nuevaTarea)
        adapter.notifyItemInserted(listaTareas.size - 1)
        actualizarContador()
    }

    private fun eliminarTarea(posicion: Int) {
        listaTareas.removeAt(posicion)
        adapter.notifyItemRemoved(posicion)
        adapter.notifyItemRangeChanged(posicion, listaTareas.size)
        actualizarContador()
    }

    private fun actualizarContador() {
        val pendientes = listaTareas.count { !it.completada }
        binding.tvContador.text = "$pendientes tareas pendientes"
    }
}