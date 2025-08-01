package com.kednections.di.modules

import com.kednections.view.activity.MainActivity
import com.kednections.view.auth.AuthFragment
import com.kednections.view.feed.ShowCaseFragment
import com.kednections.view.form.about.AboutFragment
import com.kednections.view.form.avatar.AvatarFragment
import com.kednections.view.form.choose_communicate.ChooseCommunicateFragment
import com.kednections.view.form.geolocation.GeolocationFragment
import com.kednections.view.form.nickname.NickNameFragment
import com.kednections.view.form.purposes.PurposesFragment
import com.kednections.view.form.specialization.SpecializationFragment
import com.kednections.view.form.success_reg.SuccessRegFragment
import com.kednections.view.form.welcome.WelcomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {
    @ContributesAndroidInjector
    fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun bindAuthFragment(): AuthFragment

    @ContributesAndroidInjector
    fun bindNickNameFragment(): NickNameFragment

    @ContributesAndroidInjector
    fun bindAvatarFragment(): AvatarFragment

    @ContributesAndroidInjector
    fun bindSpecializationFragment(): SpecializationFragment

    @ContributesAndroidInjector
    fun bindGeolocationFragment(): GeolocationFragment

    @ContributesAndroidInjector
    fun bindWelcomeFragment(): WelcomeFragment
  
    @ContributesAndroidInjector
    fun bindPurposesFragment(): PurposesFragment

    @ContributesAndroidInjector
    fun bindChooseCommunicateFragment(): ChooseCommunicateFragment

    @ContributesAndroidInjector
    fun bindAboutFragment(): AboutFragment

    @ContributesAndroidInjector
    fun bindSuccessRegFragment(): SuccessRegFragment

    @ContributesAndroidInjector
    fun bindShowCaseFragment(): ShowCaseFragment

}