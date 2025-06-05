package com.genesis.ai.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genesis.ai.app.ai.agents.GenesisAgent
import com.genesis.ai.app.model.AgentConfig
import com.genesis.ai.app.model.AgentType
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Singleton
class GenesisAgentViewModel @Inject constructor(
    private val genesisAgent: GenesisAgent
) : ViewModel() {
    
    val agents: StateFlow<List<AgentConfig>> = genesisAgent.getAgentsByPriority()
    
    fun toggleAgent(agent: AgentType) {
        viewModelScope.launch {
            genesisAgent.toggleAgent(agent)
        }
    }

    fun registerAuxiliaryAgent(
        name: String,
        capabilities: Set<String>
    ): AgentConfig {
        return genesisAgent.registerAuxiliaryAgent(name, capabilities)
    }

    fun getAgentConfig(name: String): AgentConfig? {
        return genesisAgent.getAgentConfig(name)
    }

    fun getAgentsByPriority(): List<AgentConfig> {
        return genesisAgent.getAgentsByPriority()
    }

    fun processQuery(query: String): List<AgentConfig> {
        viewModelScope.launch {
            genesisAgent.processQuery(query)
        }
        return emptyList() // Return empty list since processing is async
    }
}
