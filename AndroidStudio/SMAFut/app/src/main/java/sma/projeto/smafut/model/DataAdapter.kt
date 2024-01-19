package sma.projeto.smafut.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sma.projeto.smafut.R

class DataAdapter : ListAdapter<Jogador, DataAdapter.PlayerViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return PlayerViewHolder(inflater.inflate(R.layout.item_player, parent, false))
    }


    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val jogador = getItem(position)
        holder.player.text = jogador.nome + ", " + jogador.overal + "⭐️"
    }

    private class DiffCallback : DiffUtil.ItemCallback<Jogador>(){
        override fun areItemsTheSame(oldItem: Jogador, newItem: Jogador): Boolean {
            return oldItem.nome == newItem.nome
        }

        override fun areContentsTheSame(oldItem: Jogador, newItem: Jogador): Boolean {
            return oldItem == newItem
        }

    }


    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var player = itemView.findViewById(R.id.tv_player_info) as TextView
    }
}