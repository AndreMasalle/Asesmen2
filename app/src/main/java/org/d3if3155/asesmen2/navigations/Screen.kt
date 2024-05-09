package org.d3if3155.asesmen2.navigations

import org.d3if3155.asesmen2.screen.KEY_ID_CONTACT
import org.d3if3155.asesmen2.screen.KEY_ID_UBAH_KONTAK

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")

    data object FormBaru: Screen("ubahScreen")

    data object FormUbah: Screen("ubahScreen/{$KEY_ID_UBAH_KONTAK}"){
        fun withId(id :Long) = "ubahScreen/$id"
    }

    data object LiatKontak: Screen("contactScreen/{$KEY_ID_CONTACT}"){
        fun withId(id :Long) = "contactScreen/$id"
    }

}