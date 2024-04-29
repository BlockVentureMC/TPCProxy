package de.themeparkcraft.proxy.extensions

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.TextComponent.Builder
import net.kyori.adventure.text.minimessage.MiniMessage

val String.asStyledComponent: TextComponent
    get() = Component.text().append(MiniMessage.miniMessage().deserializeOr(this, Component.empty())!!).build()

inline fun String.asStyledComponent(builder: Builder.() -> Unit) =
    Component.text().append(asStyledComponent).apply(builder).build()

inline fun text(content: String, builder: Builder.() -> Unit = { }) = content.asStyledComponent(builder)