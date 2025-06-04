package com.genesis.ai.app.di

import com.genesis.ai.app.ai.core.Agent
import com.genesis.ai.app.ai.core.AuraAgent
import com.genesis.ai.app.ai.core.GenesisAgent
import com.genesis.ai.app.ai.core.KaiAgent
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

/**
 * Hilt module for providing AI agent dependencies
 */
@Module
@InstallIn(ViewModelComponent::class)
object AiModule {
    
    @Provides
    @ViewModelScoped
    fun provideAuraAgent(): AuraAgent = AuraAgent()
    
    @Provides
    @ViewModelScoped
    fun provideKaiAgent(): KaiAgent = KaiAgent()
    
    @Provides
    @ViewModelScoped
    fun provideGenesisAgent(
        auraAgent: AuraAgent,
        kaiAgent: KaiAgent
    ): GenesisAgent = GenesisAgent(auraAgent, kaiAgent)
    
    @Provides
    @ViewModelScoped
    fun provideAgent(genesisAgent: GenesisAgent): Agent = genesisAgent
}
