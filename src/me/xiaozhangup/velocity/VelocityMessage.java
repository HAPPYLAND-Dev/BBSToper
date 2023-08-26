package me.xiaozhangup.velocity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class VelocityMessage {

    Plugin plugin;

    public VelocityMessage(Plugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "slime:old");
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "slime:modern");
    }

    public void broadcast(String string) {
        Bukkit.getServer().sendPluginMessage(plugin, "slime:pull", string.getBytes());
    }

    public void broadcast(Component component) {
        String string = GsonComponentSerializer.gson().serialize(component);
        Bukkit.getServer().sendPluginMessage(plugin, "slime:pull", string.getBytes());
    }

}
