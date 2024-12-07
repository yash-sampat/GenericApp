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

        setupComponentTree()
    }

    private fun setupComponentTree() = with(binding) {
        attachTextWatchers()
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
        binding.progressbarRegister.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressbarRegister.visibility = View.GONE
    }

    private fun validateUserPassword(): Boolean {
        binding.apply {
            if (edPasswordRegister.text.toString()
                    .isEmpty() or !isValidPassword(edPasswordRegister.text.toString())
            ) {
                tverrorPasswordViewregister.error = tverrorPasswordViewregister.error
                tverrorPasswordViewregister.visibility = View.VISIBLE

                return false
            } else {
                tverrorPasswordViewregister.isEnabled = false
                tverrorPasswordViewregister.visibility = View.GONE
                tverrorPasswordViewregister.error = null
            }

            return true
        }

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
        binding.apply {
            val email: String =
                edEmailormobileRegister.text.toString().trim()

            if (edEmailormobileRegister.text.toString()
                    .isEmpty() or !isValidEmailaddress(email) and !validmobilenumber(email)
            ) {
                tverrorEmailormobileRegister.error = tverrorEmailormobileRegister.error
                tverrorEmailormobileRegister.visibility = View.VISIBLE

                return false
            } else {
                tverrorEmailormobileRegister.isEnabled = false
                tverrorEmailormobileRegister.visibility = View.GONE
                tverrorEmailormobileRegister.error = null
            }

            return true
        }


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
        binding.apply {
            val age: String =
                edAgeRegister.text.toString().trim()

            if (age.isEmpty()
            ) {
                tverror2AgeViewregister.isEnabled = false
                tverror2AgeViewregister.visibility = View.GONE
                tverror2AgeViewregister.error = null

                tverrorAgeViewregister.error = binding.tverrorAgeViewregister.error
                tverrorAgeViewregister.visibility = View.VISIBLE

                return false
            } else if (age.toInt() < 18) {
                tverrorAgeViewregister.isEnabled = false
                tverrorAgeViewregister.visibility = View.GONE
                tverrorAgeViewregister.error = null

                tverror2AgeViewregister.error = binding.tverror2AgeViewregister.error
                tverror2AgeViewregister.visibility = View.VISIBLE

                return false
            } else {
                tverrorAgeViewregister.isEnabled = false
                tverrorAgeViewregister.visibility = View.GONE
                tverrorAgeViewregister.error = null

                tverror2AgeViewregister.isEnabled = false
                tverror2AgeViewregister.visibility = View.GONE
                tverror2AgeViewregister.error = null

                return true
            }
        }
    }

    private fun attachTextWatchers() {
        binding.apply {
            edEmailormobileRegister.addTextChangedListener(emailTextWatcher)
            edPasswordRegister.addTextChangedListener(passwordTextWatcher)
            edAgeRegister.addTextChangedListener(ageTextWatcher)
        }

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

    private val ageTextWatcher: TextWatcher = object :TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            validateAge()
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater, container, false)
}