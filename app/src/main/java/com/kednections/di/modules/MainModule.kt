package com.kednections.di.modules

import com.kednections.view.activity.MainActivity
import com.kednections.view.auth.AuthFragment
import com.kednections.view.form.avatar.AvatarFragment
import com.kednections.view.form.nickname.NickNameFragment
import com.kednections.view.form.specialization.SpecializationFragment
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

}