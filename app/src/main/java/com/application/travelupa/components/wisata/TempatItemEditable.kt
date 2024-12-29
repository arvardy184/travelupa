package com.application.travelupa.components.wisata

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.application.travelupa.R
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun TempatItemEditable(
    tempat: TempatWisata,
    onDelete: () -> Unit,
) {
    val firestore = FirebaseFirestore.getInstance()
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                tempat.gambarUriString?.let { uriString ->
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(Uri.parse(uriString))
                            .crossfade(true)
                            .build(),
                        contentDescription = tempat.nama,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        error = painterResource(id = R.drawable.default_image)
                    )
                } ?: Image(
                    painter = tempat.gambarResId?.let {
                        painterResource(id = it)
                    } ?: painterResource(id = R.drawable.default_image),
                    contentDescription = tempat.nama,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                if (tempat.nama == null && tempat.deskripsi == null &&
                    (tempat.gambarUriString != null || tempat.gambarResId != null)
                ) {
                    Text(
                        text = "Tempat wisata kosong",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(vertical = 16.dp)
                    )
                } else {
                    Column(
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Text(
                            text = tempat.nama,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 8.dp, top = 12.dp)
                        )
                        Text(
                            text = tempat.deskripsi,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Box(modifier = Modifier.align(Alignment.TopEnd)) {
                    IconButton(
                        onClick = { expanded = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        offset = DpOffset(x = (-8).dp, y = 0.dp)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                expanded = false
                                if (tempat.nama.isNullOrEmpty()) {
                                    Log.w("TempatItemEditable", "Nama tempat wisata kosong, tidak dapat dihapus")
                                    return@DropdownMenuItem
                                }
                                firestore.collection("tempat_wisata")
                                    .document(tempat.nama)
                                    .delete()
                                    .addOnSuccessListener {
                                        onDelete()
                                        Toast.makeText(
                                            context,
                                            "Tempat wisata berhasil dihapus",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            context,
                                            "Gagal menghapus tempat wisata: ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.w("TempatItemEditable", "Error deleting document", e)
                                    }
                            }
                        )

                    }
                }
            }
        }
    }
}