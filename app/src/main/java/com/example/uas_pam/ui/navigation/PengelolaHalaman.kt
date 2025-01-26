package com.example.uas_pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uas_pam.ui.view.CatatanPanen.DestinasiCatatanHome
import com.example.uas_pam.ui.view.CatatanPanen.DestinasiInsertCatatan
import com.example.uas_pam.ui.view.CatatanPanen.EntryCatatanScreen
import com.example.uas_pam.ui.view.CatatanPanen.HomeScreenCatatan
import com.example.uas_pam.ui.view.HomeView.DestinasiSplash
import com.example.uas_pam.ui.view.HomeView.Splash
import com.example.uas_pam.ui.view.Pekerja.DestinasiHomePekerja
import com.example.uas_pam.ui.view.Pekerja.DestinasiInsertPekerja
import com.example.uas_pam.ui.view.Pekerja.EntryPekerjaScreen
import com.example.uas_pam.ui.view.Pekerja.HomeScreenPekerja
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
                    navController.navigate(DestinasiTanamanHome.route)
                },
                onPekerjaClick = {
                    // Tambahkan rute untuk Pekerja
                    navController.navigate(DestinasiHomePekerja.route)
                },
                onCatatanPanenClick = {
                    // Tambahkan rute untuk Catatan Panen
                    navController.navigate(DestinasiCatatanHome.route)
                },
                onAktivitasPertanianClick = {
                    // Tambahkan rute untuk Aktivitas Pertanian
                    navController.navigate("aktivitas_pertanian_route")
                }
            )
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
        // Halaman Home Tanaman
        composable(DestinasiHomePekerja.route) {
            HomeScreenPekerja(
                navigateToPekerjaEntry = {
                    navController.navigate(DestinasiInsertPekerja.route)
                },
                navigateToSplash = {
                    navController.navigate(DestinasiSplash.route)
                }
            )
        }

        // Halaman Entry Tanaman
        composable(DestinasiInsertPekerja.route) {
            EntryPekerjaScreen(
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }
        // HALAMAN HOME CATATAN
        composable(DestinasiCatatanHome.route) {
            HomeScreenCatatan(
                navigateToCatatanEntry = {
                    navController.navigate(DestinasiInsert.route)
                },
                navigateToSplash = {
                    navController.navigate(DestinasiSplash.route)
                }
            )
        }
        // Halaman Entry Tanaman
        composable(DestinasiInsertCatatan.route) {
            EntryCatatanScreen(
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }
    }
}