package com.ejemplo.listadetareas

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ejemplo.listadetareas.databinding.ItemTareaBinding

class TareaAdapter(
    private val tareas: MutableList<Tarea>,
    private val onEliminar: (Int) -> Unit
) : RecyclerView.Adapter<TareaAdapter.TareaViewHolder>() {

    inner class TareaViewHolder(val binding: ItemTareaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val binding = ItemTareaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TareaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = tareas[position]

        holder.binding.tvTitulo.text = tarea.titulo
        actualizarEstiloTexto(holder, tarea.completada)

        holder.binding.cbCompletada.setOnCheckedChangeListener(null)
        holder.binding.cbCompletada.isChecked = tarea.completada

        holder.binding.cbCompletada.setOnCheckedChangeListener { _, isChecked ->
            tarea.completada = isChecked
            actualizarEstiloTexto(holder, isChecked)
        }

        holder.binding.btnEliminar.setOnClickListener {
            val posicionActual = holder.adapterPosition
            if (posicionActual != RecyclerView.NO_ID.toInt()) {
                onEliminar(posicionActual)
            }
        }
    }
    override fun getItemCount(): Int = tareas.size

    private fun actualizarEstiloTexto(holder: TareaViewHolder, completada: Boolean) {
        if (completada) {
            holder.binding.tvTitulo.paintFlags =
                holder.binding.tvTitulo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.binding.tvTitulo.paintFlags =
                holder.binding.tvTitulo.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}
