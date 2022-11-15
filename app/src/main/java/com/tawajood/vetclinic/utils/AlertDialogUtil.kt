package com.tawajood.vetclinic.utils

import androidx.fragment.app.FragmentManager

import com.tawajood.vetclinic.ui.main.profile.DeleteAccountDialogFragment

fun showAlertDialog(fragmentManager: FragmentManager) {
    DeleteAccountDialogFragment.newInstance()
        .show(fragmentManager, DeleteAccountDialogFragment::class.java.canonicalName)
}
