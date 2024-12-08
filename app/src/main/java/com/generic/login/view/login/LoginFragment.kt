package com.generic.login.view.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.generic.login.R
import com.generic.login.databinding.FragmentLoginBinding
import com.generic.login.model.login.DataModelLoginBody
import com.generic.login.utils.Resource
import com.generic.login.utils.hideKeyboard
import com.generic.login.view.base.BaseFragment
import com.generic.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.regex.Pattern

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    @ExperimentalCoroutinesApi
    override val viewModel: LoginViewModel by viewModels()
    private lateinit var stringEmailMobile: String
    private lateinit var stringPassword: String
    private lateinit var deviceId: String

    @SuppressLint("HardwareIds")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deviceId = Settings.Secure.getString(
            requireContext().contentResolver, Settings.Secure.ANDROID_ID
        )

        setupComponentTree()
    }

    private fun setupComponentTree() = with(binding) {
        attachTextWatchers()
        buttonLogin.setOnClickListener {
            hideKeyboard()
            stringEmailMobile = edEmailormobileLogin.text.toString().trim()
            stringPassword = edPasswordLogin.text.toString().trim()
            if (!validateUserEmailMobile() or !validateUserPassword()) {
                return@setOnClickListener
            } else {
                loginUser()
            }
        }

        buttonRegister.setOnClickListener {
            hideKeyboard()
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun loginUser() {
        val dataModelLoginBody = DataModelLoginBody(
            stringEmailMobile,
            stringPassword, "To sign in existing user", deviceId
        )
        viewModel.loginUser(dataModelLoginBody)
        viewModel.loginData.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { loginResponse ->
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        // since we do not have real APIs' now, mock the result and navigate
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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
        binding.progressbarLogin.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressbarLogin.visibility = View.GONE
    }

    private fun validateUserPassword(): Boolean = with(binding){
        if (edPasswordLogin.text.toString()
                .isEmpty() or !isValidPassword(edPasswordLogin.text.toString())
        ) {
            tverrorPasswordViewlogin.error = tverrorPasswordViewlogin.error
            tverrorPasswordViewlogin.visibility = View.VISIBLE

            return false
        } else {
            tverrorPasswordViewlogin.isEnabled = false
            tverrorPasswordViewlogin.visibility = View.GONE
            tverrorPasswordViewlogin.error = null
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

    private fun validateUserEmailMobile(): Boolean = with(binding) {
        val email: String =
            edEmailormobileLogin.text.toString().trim()

        if (email.isEmpty() or !validateEmailAddress(email) and !validateMobileNumber(email)
        ) {
            tverrorEmailormobileLogin.error = tverrorEmailormobileLogin.error
            tverrorEmailormobileLogin.visibility = View.VISIBLE

            return false
        } else {
            tverrorEmailormobileLogin.isEnabled = false
            tverrorEmailormobileLogin.visibility = View.GONE
            tverrorEmailormobileLogin.error = null
        }

        return true

    }

    private fun validateMobileNumber(password: String): Boolean {

        val p = Pattern.compile("(0/91)?[7-9][0-9]{9}")

        val m = p.matcher(password)
        return m.find() && m.group() == password

    }

    private fun validateEmailAddress(email: String): Boolean {

        val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"

        val pat = Pattern.compile(emailRegex)
        return pat.matcher(email).matches()

    }

    private fun attachTextWatchers() = with(binding){
        edEmailormobileLogin.addTextChangedListener(emailTextWatcher)
        edPasswordLogin.addTextChangedListener(passwordTextWatcher)
    }

    private val emailTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            validateUserEmailMobile()
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
    ) = FragmentLoginBinding.inflate(inflater, container, false)
}