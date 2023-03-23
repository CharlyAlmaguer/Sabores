package com.car.sabores.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.car.dsapp.databinding.ListItemBinding
import com.car.sabores.activities.ListActivity
import com.car.sabores.model.ListS

class ListAdapter(private val context: Context, private val list: ArrayList<ListS>): RecyclerView.Adapter<ListAdapter.ViewHolder>()  {

    class ViewHolder(view: ListItemBinding): RecyclerView.ViewHolder(view.root) {
        val ivList = view.ivList
        val tvTitle = view.tvTitle
        val tvDescripcion = view.tvDescription
        val tvPrecio = view.tvPrecio
        val btnAgregar = view.btnAgregar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = list[position].nombre
        holder.tvDescripcion.text = list[position].descripcion
        holder.tvPrecio.text = list[position].precio
        Glide.with(context).load(list[position].imagen).centerCrop().into(holder.ivList)

        //Capturamos los clicks
        holder.btnAgregar.setOnClickListener {
            if (context is ListActivity) context.selectedProduct(list[position])
        }
    }

    override fun getItemCount(): Int = list.size
}