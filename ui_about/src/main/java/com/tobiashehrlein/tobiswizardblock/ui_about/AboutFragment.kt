package com.tobiashehrlein.tobiswizardblock.ui_about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.tobiashehrlein.tobiswizardblock.presentation.about.AboutViewModel
import com.tobiashehrlein.tobiswizardblock.ui_about.databinding.FragmentAboutBinding
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AboutFragment :
    BaseFragment<AboutViewModel, FragmentAboutBinding>() {

    override val viewModel: AboutViewModel by sharedViewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_about
    override val hasOptionsMenu: Boolean = true

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        viewModel.sendEmail.observe(viewLifecycleOwner) {
            sendEmail()
        }
        viewModel.openWizard.observe(viewLifecycleOwner) {
            openWizard()
        }
        viewModel.openFahrstuhl.observe(viewLifecycleOwner) {
            openFahrstuhl()
        }
        viewModel.openMovieBase.observe(viewLifecycleOwner) {
            openMoviebase()
        }
    }

    private fun openWizard() {
        openInPlayStore(requireContext().packageName)
    }

    private fun openFahrstuhl() {
        openInPlayStore(getString(R.string.about_package_name_fahrstuhl_block))
    }

    private fun openMoviebase() {
        openInPlayStore(getString(R.string.about_package_name_moviebase))
    }

    private fun openInPlayStore(packageName: String) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.about_playstore_prefix, packageName))
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.about_playstore_url, packageName))
                )
            )
        }
    }

    private fun sendEmail() {
        val text = getString(R.string.about_email_text)
        val email = getString(R.string.about_email)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.general_app_name))
            putExtra(Intent.EXTRA_TEXT, text)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }

        val mailer = Intent.createChooser(intent, getString(R.string.about_email_chooser_title))
        try {
            startActivity(mailer)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                getString(R.string.about_no_email_clients),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
