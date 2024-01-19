package sma.projeto.smafut.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import sma.projeto.smafut.databinding.ActivityNewplayerBinding
import sma.projeto.smafut.model.Jogador

class NewPlayerActivity:AppCompatActivity() {

    private lateinit var binding: ActivityNewplayerBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNewPlayer.setOnClickListener {

            val nome = binding.pNome.text.toString()
            val idade = binding.pIdade.text.toString()
            val posicao = binding.pPosicao.text.toString()
            val equipa = binding.pEquipa.text.toString()
            val overal = binding.pOveral.text.toString()

            database = FirebaseDatabase.getInstance("https://smafut-14e28-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Jogadores")

            val Jogador = Jogador(nome,Integer.parseInt(idade),posicao, equipa, Integer.parseInt(overal))
            database.child(nome).setValue(Jogador).addOnSuccessListener {

                Toast.makeText(this,"Successfully Created",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()

            }.addOnFailureListener{
                binding.pNome.text.clear()
                binding.pIdade.text.clear()
                binding.pPosicao.text.clear()
                binding.pEquipa.text.clear()
                binding.pOveral.text.clear()
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }

        }
    }
}