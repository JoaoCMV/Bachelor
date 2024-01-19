package sma.projeto.smafut.views

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import sma.projeto.smafut.R
import sma.projeto.smafut.extensions.Extensions.toast
import sma.projeto.smafut.model.GetData
import sma.projeto.smafut.utils.FirebaseUtils.firebaseAuth

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
// sign out a user

        btnSignOut.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, CreateAccountActivity::class.java))
            toast("signed out")
            //finish()
        }

        // Teste para o Crashlytics do Firebase
        /*
        val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))

         */

        btnGame.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }

        btnPlayerList.setOnClickListener {
            startActivity(Intent(this, GetData::class.java))
            finish()
        }


    }
}