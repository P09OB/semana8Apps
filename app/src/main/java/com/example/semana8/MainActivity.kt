package com.example.semana8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.semana8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var songListViewModel : SongListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.songList.movementMethod = ScrollingMovementMethod()

        songListViewModel = ViewModelProvider(this)
            .get(SongListViewModel::class.java)

        binding.searchBtn.setOnClickListener {
            // llamar el metodo del VM
            songListViewModel.GETListOfSongs(binding.searchET.text.toString())
        }

        //Observar el VH

        songListViewModel.songList.observe(this) {
            binding.songList.text = ""
            it.forEach { song ->
                binding.songList.append("${song.name}\n${song.album}\n\n")
            }
        }
    }
}