package com.car.sabores.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.car.dsapp.databinding.CarritoItemBinding
import com.car.sabores.activities.CarritoActivity
import com.car.sabores.model.CarritoSqlModel

class CarritoAdapter(private val context: Context, private val item: ArrayList<CarritoSqlModel>): RecyclerView.Adapter<CarritoAdapter.ViewHolder>()  {

    class ViewHolder(view: CarritoItemBinding): RecyclerView.ViewHolder(view.root) {
        val ivCarrito = view.ivCarrito
        val tvTituloCarrito = view.tvTituloCarrito
        val tvCantidadCarrrito = view.tvCantidadCarrrito
        val tvPrecioCarrrito = view.tvPrecioCarrrito
        val btnEliminar = view.btnEliminar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CarritoItemBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTituloCarrito.text = item[position].nombre
        holder.tvCantidadCarrrito.text = "Cantidad: "+ item[position].cantidad.toString()
        holder.tvPrecioCarrrito.text = "Precio: "+ item[position].precio
        Glide.with(context).load(item[position].imagen).centerCrop().into(holder.ivCarrito)

        //Capturamos los clicks
        holder.btnEliminar.setOnClickListener {
            if (context is CarritoActivity) context.selectedItem(item[position])
        }
    }

    override fun getItemCount(): Int = item.size
}