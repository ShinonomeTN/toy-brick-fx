package com.shinonometn.fx.app

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import javafx.application.Platform
import javafx.scene.image.Image
import javafx.stage.Stage
import javafx.stage.Window
import com.shinonometn.fx.*
import java.io.File
import java.io.FileOutputStream

class FxAppContextImpl(private val rootStage: Stage) {

    val window: Window = rootStage
    var windowTitle: String by rootStage.titleProperty()

    private val configurations = HashMap<String, AppSettingBean>()
    private var appSettingTree: JsonNode? = null

    init {
        val appInfoJsonTree = JsonUtils.objectMapper.readTree(resourceAsStream("/app.json"))

        windowTitle = appInfoJsonTree["application"]["name"].asText()
        rootStage.icons.add(Image(resourceAsStream(appInfoJsonTree["application"]["icon"].asText())))

        val settingFile = File("./settings.json")
        if (settingFile.exists()) {
            appSettingTree = JsonUtils.toJsonTree(settingFile)
        }

        window.width = 640.0
        window.height = 480.0

        rootStage.apply {
            setOnCloseRequest {
                saveSettings()
                Platform.exit()
            }
        }
    }

    /*
    *
    *
    * */

    fun getConfiguration(name: String): AppSettingBean? {
        return configurations[name]
    }

    fun saveSettings() {
        val destFile = "./settings.json"
        val map = configurations.entries.map { it.key to it.value.properties }.toMap()
        JsonUtils.objectMapper.writeValue(FileOutputStream(File(destFile)), map)
    }

    fun registerSetting(appSettingBean: AppSettingBean) {
        appSettingTree?.let {
            it[appSettingBean.name]
        }?.let {
            val type = object : TypeReference<Map<String, Any>>() {}
            appSettingBean.properties.putAll(it.asValue(type))
        }

        configurations[appSettingBean.name] = appSettingBean
    }
}