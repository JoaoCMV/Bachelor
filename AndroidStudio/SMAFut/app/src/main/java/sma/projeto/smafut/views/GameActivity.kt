package sma.projeto.smafut.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_game.*
import sma.projeto.smafut.R
import sma.projeto.smafut.model.Jogador

class GameActivity : AppCompatActivity(){

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        btnGame_to_home.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        database = FirebaseDatabase.getInstance("https://smafut-14e28-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Jogadores")

        getTeams()
    }


    private fun getTeams() {

        var res = ArrayList<Jogador>()
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
                    Log.i("Players List for Game", "NOT EMPTY")
                    println(list)

                    // Remove seis jogadores da lista inicial
                    // É certo existirem 6 jogadores
                    for (i in 0..6) {
                        val start = 0
                        val end = list.size - 1
                        val r = rand(start, end)
                        Log.i("R...", res.toString())
                        res.add(list.get(r))
                        list.removeAt(r)
                    }
                    findViewById<TextView>(R.id.team1_p1).text = res.get(0).nome + " " + res.get(0).overal + "⭐️"
                    findViewById<TextView>(R.id.team1_p2).text = res.get(1).nome + " " +  res.get(1).overal + "⭐️"
                    findViewById<TextView>(R.id.team1_p3).text = res.get(2).nome + " " +  res.get(2).overal + "⭐️"

                    findViewById<TextView>(R.id.team2_p1).text = res.get(3).nome + " " +  res.get(3).overal + "⭐️"
                    findViewById<TextView>(R.id.team2_p2).text = res.get(4).nome + " " +  res.get(4).overal + "⭐️"
                    findViewById<TextView>(R.id.team2_p3).text = res.get(5).nome + " " +  res.get(5).overal + "⭐️"

                }else{
                    Log.i("recycler", "EMPTY")
                }
            }
        })
    }

    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (start..end).random()
    }
}