package com.genesis.ai.app.di

import com.genesis.ai.app.ui.viewmodel.ChatViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideChatViewModel() = ChatViewModel()
}
