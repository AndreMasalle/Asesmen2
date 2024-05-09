package org.d3if3155.asesmen2.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3155.asesmen2.screen.ContactScreen
import org.d3if3155.asesmen2.screen.KEY_ID_CONTACT
//import org.d3if3155.asesmen2.screen.ContactScreen
import org.d3if3155.asesmen2.screen.KEY_ID_UBAH_KONTAK
import org.d3if3155.asesmen2.screen.MainScreen
import org.d3if3155.asesmen2.screen.UbahScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(route = Screen.Home.route){
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route){
            UbahScreen(navController)
        }

        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(navArgument(KEY_ID_UBAH_KONTAK){type = NavType.LongType})
        ){navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_UBAH_KONTAK)
            UbahScreen(navController,id)
        }

        composable(
            route = Screen.LiatKontak.route,
            arguments = listOf(navArgument(KEY_ID_CONTACT){type = NavType.LongType})
        ){navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_CONTACT)
            ContactScreen(navController,id)
        }

    }
}