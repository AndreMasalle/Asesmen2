package org.d3if3155.asesmen2.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

    var showDialog by remember { mutableStateOf(false) }

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
//                    IconButton(onClick = {
//                    }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.baseline_view_list_24) ,
//                            contentDescription = stringResource(R.string.view_list),
//                            tint = MaterialTheme.colorScheme.primary
//                        )
//                    }
                    IconButton(onClick = {
                        if (id!= null) { navController.navigate(Screen.FormUbah.withId(id)) }

                    }) {
//                        Setelah di-klik akan pergi ke halaman ubah kontak
                        Icon(
                            imageVector = Icons.Outlined.Create,
                            contentDescription = stringResource(R.string.edit_kontak),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id!=null){
                        DeleteAction {
                            showDialog = true
                        }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false },
                            onConfirmation = {
                                viewModel.delete(id)
                                navController.popBackStack()
                            }
                        )
                        {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
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

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(id = R.string.kontak),
                modifier = Modifier.size(64.dp) // Adjust size as needed
            )
        }

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

@Composable
fun DisplayAlertDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    function: () -> Unit
) {
    if (openDialog){
        AlertDialog(
            text = { Text(text = stringResource(id = R.string.hapus_kontak))},
            confirmButton = { TextButton(onClick = { onConfirmation() }) {
                Text(text = stringResource(id = R.string.hapus))
            }
            },
            dismissButton = { TextButton(onClick = { onDismissRequest()}) {
                Text(text = stringResource(id = R.string.batal))
            }
            },
            onDismissRequest = { /*TODO*/ }

        )
    }
}

@Composable
fun DeleteAction(delete: ()->Unit) {

    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = {expanded = true}) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.opsi_lain),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded=false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus_kontak))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus_kontak))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
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