package com.example.semana8

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class SongListViewModel : ViewModel() {

    //Con MutableLiveData songList se vuelve observable de las variables.

    var _songList: MutableLiveData<ArrayList<Song>> = MutableLiveData()
    /* //BUENAS PRACTICAS
    val songList : LiveData<ArrayList<Song>>
            get (){
                return _songList
            }
*/
    fun GETListOfSongs(search: String) {
        //Llamado a internet, función asincrona es por esto que se debe llamar el scope

        viewModelScope.launch(Dispatchers.IO) {
            //Se ejecuta en un hilo aparte
            val url = URL("https://api.deezer.com/search?q=$search")
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "GET"
            val json = connection.inputStream.bufferedReader().readText()

            val deezerObj = Gson().fromJson(json,DeezerObj::class.java)
            val songs = ArrayList<Song>()
            deezerObj.data.forEach {
                songs.add(Song(it.title,it.album.title))
            }

            withContext(Dispatchers.Main){
                _songList.value = songs
            }

        }

    }

    //Modelo para los datos

    data class DeezerObj (
        var data : ArrayList<Track>
            )

    data class Track (
        var title : String,
        var album : Album
            )

    data class Album (
        var title : String
            )

}