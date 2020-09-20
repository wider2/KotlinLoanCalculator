package com.my.loancalculator.utils

import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.ViewModelProvider.Factory


inline fun <reified VM : ViewModel> Fragment.provideViewModel(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> Factory)? = null
): Lazy<VM> = 
   OverridableLazy(viewModels(ownerProducer, factoryProducer))

/**
 * Method to replace the fragment.
 */
fun Fragment.replaceFragmentSafely(
    fragment: Fragment,
    tag: String,
    allowStateLoss: Boolean = false,
    @IdRes containerViewId: Int,
    @AnimRes enterAnimation: Int = 0,
    @AnimRes exitAnimation: Int = 0,
    @AnimRes popEnterAnimation: Int = 0,
    @AnimRes popExitAnimation: Int = 0
) {
    if (isAdded) {
        val ft = childFragmentManager.beginTransaction()
        ft.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
        ft.replace(containerViewId, fragment, tag)
        if (!childFragmentManager.isStateSaved) {
            ft.commit()
        } else if (allowStateLoss) {
            ft.commitAllowingStateLoss()
        }
    }
}

/**
 * Method to add the fragment.
 */
fun <T : Fragment> Fragment.addFragmentSafely(
    fragment: T,
    tag: String,
    allowStateLoss: Boolean = false,
    @IdRes containerViewId: Int,
    @AnimRes enterAnimation: Int = 0,
    @AnimRes exitAnimation: Int = 0,
    @AnimRes popEnterAnimation: Int = 0,
    @AnimRes popExitAnimation: Int = 0
): T {
    if (isAdded && !existsFragmentByTag(tag)) {
        val ft = childFragmentManager.beginTransaction()
        ft.setReorderingAllowed(true)
        ft.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
        ft.add(containerViewId, fragment, tag)
        ft.addToBackStack(tag)
        if (!childFragmentManager.isStateSaved) {
            ft.commit()
        } else if (allowStateLoss) {
            ft.commitAllowingStateLoss()
        }
        return fragment
    }
    return findFragmentByTag(tag) as T
}

/**
 * Method to check if fragment exists.
 */
fun Fragment.existsFragmentByTag(tag: String): Boolean {
    return childFragmentManager.findFragmentByTag(tag) != null
}

/**
 * Method to get fragment by tag.
 */
fun Fragment.findFragmentByTag(tag: String): Fragment? {
    return childFragmentManager.findFragmentByTag(tag)
}