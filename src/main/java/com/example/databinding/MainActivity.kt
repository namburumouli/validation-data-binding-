package com.example .databinding


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.databinding.FieldValidators.isStringContainNumber
import com.example.databinding.FieldValidators.isStringContainSpecialCharacter
import com.example.databinding.FieldValidators.isStringLowerAndUpperCase
import com.example.databinding.FieldValidators.isValidEmail
import com.example.databinding.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title="Lotus App"
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupListeners()

        binding.loginButton.setOnClickListener {
            if (isValidate()) {
                Toast.makeText(this, "validated", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidate(): Boolean =
         validateEmail() && validatePassword()

    private fun setupListeners() {
        binding.email.addTextChangedListener(TextFieldValidation(binding.email))
        binding.password.addTextChangedListener(TextFieldValidation(binding.password))
    }


    private fun validateEmail(): Boolean {
        if (binding.email.text.toString().trim().isEmpty()) {
            binding.emailTextInputLayout.error = "Required Field!"
            binding.email.requestFocus()
            return false
        } else if (!isValidEmail(binding.email.text.toString())) {
            binding.emailTextInputLayout.error = "Invalid Email!"
            binding.email.requestFocus()
            return false
        } else {
            binding.emailTextInputLayout.isErrorEnabled = false
        }
        return true
    }


    private fun validatePassword(): Boolean {
        if (binding.password.text.toString().trim().isEmpty()) {
            binding.passwordTextInputLayout.error = "Required Field!"
            binding.password.requestFocus()
            return false
        } else if (binding.password.text.toString().length < 6) {
            binding.passwordTextInputLayout.error = "password can't be less than 6"
            binding.password.requestFocus()
            return false
        } else if (!isStringContainNumber(binding.password.text.toString())) {
            binding.passwordTextInputLayout.error = "Required at least 1 digit"
            binding.password.requestFocus()
            return false
        } else if (!isStringLowerAndUpperCase(binding.password.text.toString())) {
            binding.passwordTextInputLayout.error =
                "Password must contain upper and lower case letters"
            binding.password.requestFocus()
            return false
        } else if (!isStringContainSpecialCharacter(binding.password.text.toString())) {
            binding.passwordTextInputLayout.error = "1 special character required"
            binding.password.requestFocus()
            return false
        } else {
            binding.passwordTextInputLayout.isErrorEnabled = false
        }
        return true
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.email -> {
                    validateEmail()
                }
                R.id.password -> {
                    validatePassword()
                }
            }
        }
    }
}