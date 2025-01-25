package com.example.uas_pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uas_pam.ui.view.HomeView.DestinasiSplash
import com.example.uas_pam.ui.view.HomeView.Splash
import com.example.uas_pam.ui.view.Tanaman.DestinasiDetailTanaman
import com.example.uas_pam.ui.view.Tanaman.DetailTanamanView
import com.pam.uas_pam.view.DestinasiInsert
import com.pam.uas_pam.view.EntryTanamanScreen
import com.pam.uas_pam.view.tanaman.DestinasiTanamanHome
import com.pam.uas_pam.view.tanaman.HomeScreenTanaman

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplash.route,
        modifier = modifier
    ) {
        // Splash Screen
        composable(DestinasiSplash.route) {
            Splash(
                onTanamanClick = {
                    navController.navigate(DestinasiTanamanHome.route) {
                        popUpTo(DestinasiSplash.route) {
                            inclusive = true
                        }
                    }
                }
            ) // Tambahkan tanda kurung tutup di sini
        }

        // Halaman Home Tanaman
        composable(DestinasiTanamanHome.route) {
            HomeScreenTanaman(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsert.route)
                },
                navigateToSplash = {
                    navController.navigate(DestinasiSplash.route)
                }
            )
        }

        // Halaman Entry Tanaman
        composable(DestinasiInsert.route) {
            EntryTanamanScreen(
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }
    }
}