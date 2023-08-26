package moe.feo.bbstoper;

import me.xiaozhangup.velocity.VelocityMessage;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import moe.feo.bbstoper.gui.GUIManager;
import moe.feo.bbstoper.sql.SQLManager;

public class BBSToper extends JavaPlugin {
	private static BBSToper bbstoper;

	public static BBSToper getInstance() {
		return bbstoper;
	}
	public static VelocityMessage velocity;

	@Override
	public void onEnable() {
		bbstoper = this;
		velocity = new VelocityMessage(bbstoper);
		this.saveDefaultConfig();
		Option.load();
		Message.saveDefaultConfig();
		Message.load();
		SQLManager.initializeSQLer();
		this.getCommand("bbstoper").setExecutor(CLI.getInstance());
		this.getCommand("bbstoper").setTabCompleter(CLI.getInstance());
		new Reminder(this);
		new GUIManager(this);
		SQLManager.startTimingReconnect();
		Util.startAutoReward();
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new PAPIExpansion().register();
		}
		new Metrics(this);
		this.getLogger().info(Message.ENABLE.getString());
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(bbstoper);
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				Util.waitForAllTask();// 此方法会阻塞
				SQLManager.closeSQLer();
				bbstoper = null;
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

}
