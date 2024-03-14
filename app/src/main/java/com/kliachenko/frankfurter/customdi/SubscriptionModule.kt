//package com.kliachenko.frankfurter.old
//
//import com.kliachenko.frankfurter.core.Core
//import com.kliachenko.presentation.subscritpion.SubscriptionViewModel
//
//class SubscriptionModule(
//    private val core: Core,
//    private val clear: Clear,
//) : Module<SubscriptionViewModel> {
//
//    override fun viewModel(): SubscriptionViewModel {
//        return SubscriptionViewModel(
//            navigation = core.provideNavigation(),
//            premiumUserStorage = core.providePremiumUserStorage(),
//            clear = clear
//        )
//    }
//}