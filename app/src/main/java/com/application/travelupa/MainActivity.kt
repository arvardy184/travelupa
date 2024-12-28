package com.application.travelupa

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.volley.toolbox.ImageRequest
import com.application.travelupa.ui.theme.TravelupaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelupaTheme {
                RekomendasiTempatScreen()
            }
        }
    }
}

@Composable
fun GreetingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Selamat datang di Travelupa!",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Solusi buat kamu yang lupa kemana-mana",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* TODO: Handle click */ },
                modifier = Modifier
                    .width(360.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Mulai")
            }
        }
    }
}

data class TempatWisata(
    val nama: String,
    val deskripsi: String,
    val gambarUriString: String? = null,
    val gambarResId: Int? = null,
)

val daftarTempatWisata = listOf(
    TempatWisata(
        "Tumpak Sewu",
        "Air terjun tercantik di Jawa Timur.",
        gambarResId = R.drawable.tumpak_sewu
    ),
    TempatWisata(
        "Gunung Bromo",
        "Matahari terbitnya bagus banget.",
        gambarResId = R.drawable.gunung_bromo
    )
)

@Composable
fun RekomendasiTempatScreen() {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(daftarTempatWisata) { tempat ->
            TempatItem(tempat)
        }
    }
}

    
@Composable
fun TempatItem(
    tempat: TempatWisata,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            ){
        Image(painter = painterResource(id = tempat.gambarResId!!), contentDescription = tempat.nama,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop)
            Text(text = tempat.nama, style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp, top = 12.dp))
            Text(
                text = tempat. deskripsi,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
    }
    

//@Composable
//fun TambahTempatWisataDialog(
//    onDismiss: () -> Unit,
//    onTambah: (String, String, String?) -> Unit,
//) {
//    var nama by remember { mutableStateOf("") }
//    var deskripsi by remember { mutableStateOf("") }
//    var gambarUri by remember { mutableStateOf<Uri?>(null) }
//
//    val gambarLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        gambarUri = uri
//    }
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = {
//            Text("Tempat Wisata Baru")
//        },
//        text = {
//            Column {
//                TextField(
//                    value = nama,
//                    onValueChange = { nama = it },
//                    label = { Text("Nama Tempat") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                TextField(
//                    value = deskripsi,
//                    onValueChange = { deskripsi = it },
//                    label = { Text("Deskripsi") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                gambarUri?.let { uri ->
//                    Image(
//                        painter = rememberAsyncImagePainter(model = uri),
//                        contentDescription = "Gambar yang dipilih",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(200.dp),
//                        contentScale = ContentScale.Crop
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//                Button(
//                    onClick = { gambarLauncher.launch("image/*") },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Pilih Gambar", color = Color.White)
//                }
//            }
//        },
//        confirmButton = {
//            Button(
//                onClick = {
//                    if (nama.isNotBlank() && deskripsi.isNotBlank()) {
//                        onTambah(nama, deskripsi, gambarUri?.toString())
//                        onDismiss()
//                    }
//                },
//            ) {
//                Text("Tambah", color = Color.White)
//            }
//        },
//        dismissButton = {
//            Button(
//                onClick = onDismiss,
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
//            ) {
//                Text("Batal")
//            }
//        }
//    )
//}
//
//@Composable
//fun GambarPicker(
//    gambarUri: Uri?,
//    onPilihGambar: () -> Unit,
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        gambarUri?.let { uri ->
//            Image(
//                painter = rememberAsyncImagePainter(
//                    ImageRequest.Builder(LocalContext.current)
//                        .data(uri)
//                        .build()
//                ),
//                contentDescription = "Gambar Tempat Wisata",
//                modifier = Modifier
//                    .clickable { onPilihGambar() }
//                    .fillMaxWidth()
//                    .height(200.dp),
//                contentScale = ContentScale.Crop
//            )
//        } ?: run {
//            OutlinedButton(
//                onClick = onPilihGambar,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Icon(Icons.Filled.Add, contentDescription = "Pilih Gambar")
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("Pilih Gambar")
//            }
//        }
//    }
//}