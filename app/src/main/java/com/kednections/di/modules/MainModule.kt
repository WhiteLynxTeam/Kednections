package com.kednections.di.modules

import com.kednections.view.acquaintances.AcquaintancesFragment
import com.kednections.view.activity.FormActivity
import com.kednections.view.activity.MainActivity
import com.kednections.view.auth.AuthFragment
import com.kednections.view.communication.CommunicationFragment
import com.kednections.view.feed.FeedFragment
import com.kednections.view.feed.filter.FilterFeedFragment
import com.kednections.view.form.about.AboutFragment
import com.kednections.view.form.avatar.AvatarFragment
import com.kednections.view.form.choose_communicate.ChooseCommunicateFragment
import com.kednections.view.form.geolocation.GeolocationFragment
import com.kednections.view.form.nickname.NickNameFragment
import com.kednections.view.form.purposes.PurposesFragment
import com.kednections.view.form.specialization.SpecializationFragment
import com.kednections.view.form.success_reg.SuccessRegFragment
import com.kednections.view.form.welcome.WelcomeFragment
import com.kednections.view.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {
    @ContributesAndroidInjector
    fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun bindFormActivity(): FormActivity

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
    fun bindFeedFragment(): FeedFragment

    @ContributesAndroidInjector
    fun bindAcquaintancesFragment(): AcquaintancesFragment

    @ContributesAndroidInjector
    fun bindCommunicationFragment(): CommunicationFragment

    @ContributesAndroidInjector
    fun bindProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    fun bindFilterFeedFragment(): FilterFeedFragment
}