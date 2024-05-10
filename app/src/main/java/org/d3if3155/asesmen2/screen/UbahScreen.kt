package org.d3if3155.asesmen2.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3155.asesmen2.R
import org.d3if3155.asesmen2.database.ContactDb
import org.d3if3155.asesmen2.ui.theme.Asesmen2Theme
import org.d3if3155.asesmen2.util.ViewModelFactory

const val KEY_ID_UBAH_KONTAK = "idUbahKontak"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UbahScreen(navController: NavHostController, id: Long? = null) {
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
                        Text(text = stringResource(id = R.string.edit_kontak))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(
                        onClick = {
                            if (nomor == "" || email == ""){
                                Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                                return@IconButton
                            }

                            if (id==null){
                                viewModel.insert(nama,nomor,email)
                            }else{
                                viewModel.update(id,nama,nomor,email)
                            }
                            navController.popBackStack()
                        }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) {padding ->
        FormUbah(
            nama = nama,
            onNamaChange = {nama = it},
            nomor = nomor,
            onNomorChange = {nomor =  it},
            email = email,
            onEmailChange = {email =  it},
            modifier = Modifier.padding(padding)
        )
    }
}


@Composable
fun FormUbah(
    nama: String, onNamaChange: (String) -> Unit,
    nomor: String, onNomorChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama ,
            onValueChange = {onNamaChange(it)},
            label = { Text(text = stringResource(id = R.string.nama)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nomor,
            onValueChange = {onNomorChange(it)},
            label = { Text(text = stringResource(id = R.string.nomor)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = {onEmailChange(it)},
            label = { Text(text = stringResource(id = R.string.email)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun UbahScreenPreview() {
    Asesmen2Theme {
        UbahScreen(rememberNavController())
    }
}