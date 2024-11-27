package com.generic.login.view.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.generic.login.R
import com.generic.login.databinding.FragmentRegisterBinding
import com.generic.login.model.register.DataModelRegisterBody
import com.generic.login.utils.Resource
import com.generic.login.utils.hideKeyboard
import com.generic.login.view.base.BaseFragment
import com.generic.login.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.regex.Pattern

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {
    @ExperimentalCoroutinesApi
    override val viewModel: RegisterViewModel by viewModels()
    lateinit var stringEmailorMobile: String
    lateinit var stringPassword: String
    lateinit var stringAge: String
    lateinit var deviceId: String

    @SuppressLint("HardwareIds")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deviceId = Settings.Secure.getString(
            requireContext().contentResolver, Settings.Secure.ANDROID_ID
        )

        doinits()
    }

    private fun doinits() = with(binding) {
        gettextwathcerregister()
        buttonRegister.setOnClickListener {
            hideKeyboard()
            stringEmailorMobile = edEmailormobileRegister.text.toString().trim()
            stringPassword = edPasswordRegister.text.toString().trim()
            stringAge = edAgeRegister.text.toString().trim()
            if (!validateUserEmailorMobile() or !validateUserPassword() or !validateAge()) {
                return@setOnClickListener
            } else {
                doRegister()
            }
        }
    }

    private fun doRegister() {
        val dataModelregisterBody = DataModelRegisterBody(
            stringEmailorMobile,
            stringPassword, stringAge, "To register new user", deviceId
        )
        viewModel.registerUser(dataModelregisterBody)
        viewModel.registerData.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { registerResponse ->
                            findNavController().navigate(R.id.action_registerFragment_to_dashboardFragment)
                        }
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        // since we do not have real APIs' now, mock the result and navigate
                        findNavController().navigate(R.id.action_registerFragment_to_dashboardFragment)
                        //response.message?.let { toast(it) }
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        })
    }

    private fun showProgressBar() {
        progressbar_register.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressbar_register.visibility = View.GONE
    }

    private fun validateUserPassword(): Boolean {
        if (ed_password_register.text.toString()
                .isEmpty() or !isValidPassword(ed_password_register.text.toString())
        ) {
            tverror_password_viewregister.error = tverror_password_viewregister.error
            tverror_password_viewregister.visibility = View.VISIBLE

            return false
        } else {
            tverror_password_viewregister.isEnabled = false
            tverror_password_viewregister.visibility = View.GONE
            tverror_password_viewregister.error = null
        }

        return true

    }

    private fun isValidPassword(password: String): Boolean {

        val regex = ("^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{6,12}$")

        val p = Pattern.compile(regex)
        val m = p.matcher(password)
        return m.matches()

    }

    private fun validateUserEmailorMobile(): Boolean {
        val email: String =
            ed_emailormobile_register.text.toString().trim()

        if (ed_emailormobile_register.text.toString()
                .isEmpty() or !isValidEmailaddress(email) and !validmobilenumber(email)
        ) {
            tverror_emailormobile_register.error = tverror_emailormobile_register.error
            tverror_emailormobile_register.visibility = View.VISIBLE

            return false
        } else {
            tverror_emailormobile_register.isEnabled = false
            tverror_emailormobile_register.visibility = View.GONE
            tverror_emailormobile_register.error = null
        }

        return true

    }

    private fun validmobilenumber(password: String): Boolean {

        val p = Pattern.compile("(0/91)?[7-9][0-9]{9}")

        val m = p.matcher(password)
        return m.find() && m.group() == password

    }

    private fun isValidEmailaddress(email: String): Boolean {

        val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"

        val pat = Pattern.compile(emailRegex)
        return pat.matcher(email).matches()

    }

    private fun validateAge(): Boolean {
        val age: String =
            ed_age_register.text.toString().trim()

        if (age.isEmpty()
        ) {
            tverror2_age_viewregister.isEnabled = false
            tverror2_age_viewregister.visibility = View.GONE
            tverror2_age_viewregister.error = null

            tverror_age_viewregister.error = tverror_age_viewregister.error
            tverror_age_viewregister.visibility = View.VISIBLE

            return false
        } else if (age.toInt() < 18) {
            tverror_age_viewregister.isEnabled = false
            tverror_age_viewregister.visibility = View.GONE
            tverror_age_viewregister.error = null

            tverror2_age_viewregister.error = tverror2_age_viewregister.error
            tverror2_age_viewregister.visibility = View.VISIBLE

            return false
        } else {
            tverror_age_viewregister.isEnabled = false
            tverror_age_viewregister.visibility = View.GONE
            tverror_age_viewregister.error = null

            tverror2_age_viewregister.isEnabled = false
            tverror2_age_viewregister.visibility = View.GONE
            tverror2_age_viewregister.error = null
        }
        return true
    }

    private fun gettextwathcerregister() {
        ed_emailormobile_register.addTextChangedListener(emailTextWatcher)

        ed_password_register.addTextChangedListener(passwordTextWatcher)
    }

    private val emailTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            validateUserEmailorMobile()
        }
    }

    private val passwordTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            validateUserPassword()
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater, container, false)
}