package org.d3if3155.asesmen2.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3155.asesmen2.R
import org.d3if3155.asesmen2.database.ContactDb
import org.d3if3155.asesmen2.navigations.Screen
import org.d3if3155.asesmen2.ui.theme.Asesmen2Theme
import org.d3if3155.asesmen2.util.ViewModelFactory

const val KEY_ID_CONTACT = "idKontak"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = ContactDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: ContactViewModel = viewModel(factory = factory)

    var nama by remember { mutableStateOf("") }
    var nomor by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(true){
        if (id==null) return@LaunchedEffect
        val data =viewModel.getContact(id) ?: return@LaunchedEffect
        nama = data.nama
        nomor = data.nomor
        email = data.email
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_kontak))
                    else
                        Text(text = stringResource(id = R.string.kontak))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (id!= null){
                        navController.navigate(Screen.FormUbah.withId(id))
                        }

                    }) {
//                        Setelah di-klik akan pergi ke halaman ubah kontak
                        Icon(
                            imageVector = Icons.Outlined.Create,
                            contentDescription = stringResource(R.string.edit_kontak),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) {padding ->
        FormContact(
            nama = nama,
            nomor = nomor,
            email = email,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun FormContact(
    nama: String,
    nomor: String,
    email: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = nama,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = nomor,
            readOnly = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Call, contentDescription = "callIcon") },
            onValueChange = {},
            label = { Text(text = "Nomor telepon") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = email,
            readOnly = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
            onValueChange = {},
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth()
        )


    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ContactScreenPreview() {
    Asesmen2Theme {
        ContactScreen(rememberNavController())
    }
}