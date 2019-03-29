package com.example.androidkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)

        val users = ArrayList<User>()

        users.add(User("Grishma","Kim"))
        users.add(User("Dhruvi","Surat"))
        users.add(User("Palak", "Surat"))
        users.add(User("Pooja","Kim"))
        users.add(User("Vrutika","Ankleshwar"))
        users.add(User("Madhuri", "Rajpipla"))

        val adapter = CustomAdapter(users)

        recyclerView.adapter = adapter
    }
}