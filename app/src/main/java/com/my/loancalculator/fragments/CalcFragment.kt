package com.my.loancalculator.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.my.loancalculator.*
import com.my.loancalculator.entity.EntityProfile
import com.my.loancalculator.sync.SyncViewModel
import com.my.loancalculator.utils.*
import kotlinx.android.synthetic.main.fragment_calculator.*
import java.lang.StringBuilder

class CalcFragment : Fragment() {

    private val textViewIdCode by lazy { view?.findViewById<TextView>(R.id.textViewIdCode) }
    private val textInputLayoutIdCode by lazy { view?.findViewById<TextInputLayout>(R.id.textInputLayoutIdCode) }
    private val buttonIdCode by lazy { view?.findViewById<Button>(R.id.buttonIdCode) }
    private val textViewNotification by lazy { view?.findViewById<TextView>(R.id.textViewNotification) }

    private val textViewWelcome by lazy { view?.findViewById<TextView>(R.id.textViewWelcome) }
    private val relativeLayoutLoan by lazy { view?.findViewById<RelativeLayout>(R.id.relativeLayoutLoan) }
    private val editTextLoanAmount by lazy { view?.findViewById<EditText>(R.id.editTextLoanAmount) }
    private val editTextLoanPeriod by lazy { view?.findViewById<EditText>(R.id.editTextLoanPeriod) }
    private val buttonSubmit by lazy { view?.findViewById<Button>(R.id.buttonSubmit) }
    private val buttonSignOut by lazy { view?.findViewById<Button>(R.id.buttonSignOut) }
    private val textViewStatus by lazy { view?.findViewById<TextView>(R.id.textViewStatus) }
    private lateinit var _profile: EntityProfile

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: CalcViewModel by viewModels {
        CalcViewModelFactory()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        viewModel.setContext(requireContext())

        buttonIdCode?.setOnClickListener {
            val inputText = textInputLayoutIdCode?.editText?.text.toString()
            Log.wtf(TAG, inputText)
            viewModel.getProfileByIdCode(inputText)
        }
        editTextIdCode?.setOnEditorActionListener({ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                textInputLayoutIdCode?.hideKeyboard()
                buttonIdCode?.performClick()
                true
            } else {
                false
            }
        })

        buttonSubmit?.setOnClickListener {
            creditCalculator()
        }
        buttonSignOut?.setOnClickListener {
            signOut()
        }
        editTextLoanPeriod?.setOnEditorActionListener({ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //editTextLoanPeriod?.hideKeyboard()
                buttonSubmit?.performClick()
                true
            } else {
                false
            }
        })
        textInputLayoutIdCode?.setEndIconOnClickListener {
            textViewNotification?.text = ""
            textInputLayoutIdCode?.editText?.setText("")
        }
    }

    private fun setupObservers() {
        viewModel.showItemsError.observe(viewLifecycleOwner, EventObserver {
            showError(it)
        })

        viewModel.profile.observe(viewLifecycleOwner, EventObserver {
            showProfile(it)
        })
    }

    private fun showError(error: String) {
        textViewNotification?.text = error
        editTextIdCode?.showKeyboard()
    }

    private fun showProfile(profile: EntityProfile) {
        textViewNotification?.text = ""
        textViewStatus?.text = ""
        _profile = profile

        textViewWelcome?.visibility = View.VISIBLE
        textViewWelcome?.text = getString(R.string.welcome, profile.fullName)
        textViewIdCode?.visibility = View.GONE
        textInputLayoutIdCode?.visibility = View.GONE
        buttonIdCode?.visibility = View.GONE

        if (profile.debt) {
            textViewNotification?.text = getString(R.string.refusedByDebt)
            buttonSignOut?.visibility = View.VISIBLE
        } else {
            relativeLayoutLoan?.visibility = View.VISIBLE
            buttonSubmit?.visibility = View.VISIBLE
            textViewStatus?.visibility = View.VISIBLE
            buttonSignOut?.visibility = View.VISIBLE
        }
    }

    private fun signOut() {
        textViewNotification?.text = ""
        textViewStatus?.text = ""
        textViewIdCode?.visibility = View.VISIBLE
        textInputLayoutIdCode?.visibility = View.VISIBLE
        buttonIdCode?.visibility = View.VISIBLE
        textInputLayoutIdCode?.showKeyboard()

        textViewWelcome?.visibility = View.GONE
        relativeLayoutLoan?.visibility = View.GONE
        buttonSubmit?.visibility = View.GONE
        textViewStatus?.visibility = View.GONE
        buttonSignOut?.visibility = View.GONE
    }

    private fun creditCalculator() {
        editTextLoanPeriod?.hideKeyboard()
        textViewNotification?.text = ""
        textViewStatus?.text = ""
        val strBuilder = StringBuilder()
        var loanApproved = false
        val currency = getString(R.string.currency)
        val months = getString(R.string.months)
        var limitsAllowed = true

        val loanAmountString = editTextLoanAmount?.text.toString()
        val loanAmount: Double? = loanAmountString.toDoubleOrNull()
        if (loanAmount == null) {
            strBuilder.append(getString(R.string.wrongType))
        } else {
            if (loanAmount < MINIMUM_AMOUNT) {
                strBuilder.append(
                    getString(
                        R.string.errorLoanTooSmall,
                        "%.0f".format(MINIMUM_AMOUNT)
                    )
                )
                limitsAllowed = false
            }
            if (loanAmount > MAXIMUM_AMOUNT) {
                strBuilder.append(
                    getString(
                        R.string.errorLoanTooBig,
                        "%.0f".format(MAXIMUM_AMOUNT)
                    )
                )
                limitsAllowed = false
            }
        }

        val loanPeriodString = editTextLoanPeriod?.text.toString()
        val loanPeriod: Int? = loanPeriodString.toIntOrNull()
        if (loanPeriod == null) {
            strBuilder.append(getString(R.string.wrongType))
        } else {
            if (loanPeriod < MINIMUM_PERIOD) {
                strBuilder.append(
                    getString(
                        R.string.errorLoanPeriodTooSmall,
                        MINIMUM_PERIOD.toString()
                    )
                )
                limitsAllowed = false
            }
            if (loanPeriod > MAXIMUM_PERIOD) {
                strBuilder.append(
                    getString(
                        R.string.errorLoanPeriodTooBig,
                        MAXIMUM_PERIOD.toString()
                    )
                )
                limitsAllowed = false
            }
        }

        if (loanAmountString.isNumber() && loanPeriodString.isNumber() && limitsAllowed) {
            val loanAmountDouble: Double = Utilities().convertToDouble(loanAmount)
            val loanPeriodInt: Int = Utilities().convertToInt(loanPeriod)

            val creditScore: Double = Calculation().calcCreditScore(
                _profile.creditModifier,
                loanAmountDouble,
                loanPeriodInt
            )
            Log.wtf(TAG, "credit score is: $creditScore")

            if (creditScore >= 1) {
                val maxLoanAmount = Calculation().findMaxPossibleAmount(
                    _profile.creditModifier,
                    loanAmountDouble,
                    loanPeriodInt
                )
                strBuilder.append(
                    getString(
                        R.string.maxPossibleAmount,
                        "%.0f".format(maxLoanAmount),
                        currency
                    )
                )
                loanApproved = true

            } else if (creditScore < 1) {
                val minLoanAmount = Calculation().findMinPossibleAmount(
                    _profile.creditModifier,
                    loanAmountDouble,
                    loanPeriodInt
                )
                Log.wtf(TAG, "min loan amount: $minLoanAmount")
                if (minLoanAmount.equals(0.0)) {

                    strBuilder.append(
                        getString(
                            R.string.creditScoreInsufficientMaxAmount,
                            "%.0f".format(loanAmount),
                            currency
                        )
                    )
                    val minLoanPeriod = Calculation().findMinPossiblePeriod(
                        _profile.creditModifier,
                        loanAmountDouble,
                        loanPeriodInt
                    )
                    if (minLoanPeriod > MAXIMUM_PERIOD) {
                        strBuilder.append(
                            getString(
                                R.string.minPossiblePeriodFailed,
                                minLoanPeriod.toString(),
                                MAXIMUM_PERIOD.toString()
                            )
                        )
                        loanApproved = false
                    } else {
                        strBuilder.append(
                            getString(
                                R.string.minPossiblePeriod,
                                minLoanPeriod.toString(),
                                months
                            )
                        )
                        loanApproved = true
                    }

                } else {
                    strBuilder.append(
                        getString(
                            R.string.creditScoreInsufficientMaxAmount,
                            "%.0f".format(minLoanAmount),
                            currency
                        )
                    )
                    loanApproved = true
                }
            }

            if (loanApproved) {
                textViewStatus?.text = getString(R.string.loanApproved)
            } else {
                textViewStatus?.text = getString(R.string.loanDeclined)
            }
        }
        textViewNotification?.text = strBuilder.toString()
    }

    companion object {
        private const val TAG = "CalcFragment"
    }
}