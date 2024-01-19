package sma.projeto.smafut.model

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import sma.projeto.smafut.R

class GetData : AppCompatActivity(){

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_list)

        database = FirebaseDatabase.getInstance("https://smafut-14e28-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Jogadores")

        findViewById<RecyclerView>(R.id.rv_players).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = DataAdapter()
        }

        getData()
    }


    private fun getData() {
        database =
            FirebaseDatabase.getInstance("https://smafut-14e28-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Jogadores")

        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("cancel", p0.toString())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val list = ArrayList<Jogador>()
                for (data in p0.children) {
                    println(data)
                    var jogador = Jogador(
                        data.child("nome").getValue<String>(),
                        data.child("idade").getValue<Int>(),
                        data.child("posicao").getValue<String>(),
                        data.child("equipa").getValue<String>(),
                        data.child("overal").getValue<Int>()
                    )
                    list.add(jogador)
                }

                if (list.isNotEmpty()) {
                    Log.i("recycler", "NOT EMPTY")
                    println(list)

                    val adapter = findViewById<RecyclerView>(R.id.rv_players).adapter as DataAdapter
                    adapter.submitList(list)

                }else{
                    Log.i("recycler", "EMPTY")
                }
            }
        })
    }
}