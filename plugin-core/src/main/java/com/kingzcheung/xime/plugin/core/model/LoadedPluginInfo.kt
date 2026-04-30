package com.kingzcheung.xime.plugin.core.runtime.loader

import com.kingzcheung.xime.plugin.core.model.PluginInfo

data class LoadedPluginInfo(
    val pluginInfo: PluginInfo,
    val classLoader: PluginClassLoader
)