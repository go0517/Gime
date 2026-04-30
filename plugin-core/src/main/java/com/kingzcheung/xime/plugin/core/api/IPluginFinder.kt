package com.kingzcheung.xime.plugin.core.api

import com.kingzcheung.xime.plugin.core.runtime.loader.PluginClassLoader

interface IPluginFinder {
    fun findClass(className: String, requester: PluginClassLoader): Class<*>?
}