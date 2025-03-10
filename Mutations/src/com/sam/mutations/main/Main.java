package com.sam.mutations.main;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.sam.mutations.plugin.CmdManager;
import com.sam.mutations.plugin.CreeperChaos;
import com.sam.mutations.plugin.DataManager;
import com.sam.mutations.plugin.EnderThief;
import com.sam.mutations.plugin.ExplosivePower;
import com.sam.mutations.plugin.LightningStorm;
import com.sam.mutations.plugin.MobEffects;
import com.sam.mutations.plugin.PearlCurse;
import com.sam.mutations.plugin.ProjectileChange;
import com.sam.mutations.plugin.TabCompletion;
import com.sam.mutations.plugin.BlazeCurse;

public class Main extends JavaPlugin{
	public static Main main;
	private static File file;
	public static DataManager data;

	
    // Fired when plug-in is first enabled
	public static Main getInstance() {
		return main;
	}
	

    @Override
    public void onEnable() 
    {
    	this.data = new DataManager(this);
    	main = this;
    	getServer().getPluginManager().registerEvents(new BlazeCurse(), this);
    	getServer().getPluginManager().registerEvents(new CreeperChaos(), this);
    	getServer().getPluginManager().registerEvents(new ExplosivePower(), this);
    	getServer().getPluginManager().registerEvents(new ProjectileChange(), this);
    	getServer().getPluginManager().registerEvents(new EnderThief(), this);
    	getServer().getPluginManager().registerEvents(new MobEffects(), this);
    	getServer().getPluginManager().registerEvents(new LightningStorm(), this);
    	getServer().getPluginManager().registerEvents(new PearlCurse(), this);
    	getCommand("blazeCurse").setTabCompleter(new TabCompletion());
    	getCommand("mutations").setTabCompleter(new TabCompletion());
    	getCommand("creeperDupe").setTabCompleter(new TabCompletion());
    	getCommand("explodeSummon").setTabCompleter(new TabCompletion());
    	getCommand("explosiveScale").setTabCompleter(new TabCompletion());
    	getCommand("explosiveProjectiles").setTabCompleter(new TabCompletion());
    	getCommand("projectileSummon").setTabCompleter(new TabCompletion());
    	getCommand("enderThief").setTabCompleter(new TabCompletion());
    	getCommand("mobEffects").setTabCompleter(new TabCompletion());
    	getCommand("mobCrit").setTabCompleter(new TabCompletion());
    	getCommand("mobDash").setTabCompleter(new TabCompletion());
    	getCommand("neutralAttack").setTabCompleter(new TabCompletion());
    	getCommand("strongStorm").setTabCompleter(new TabCompletion());
    	getCommand("mobStrength").setTabCompleter(new TabCompletion());
    	getCommand("creeperFuse").setTabCompleter(new TabCompletion());
    	getCommand("mobGroup").setTabCompleter(new TabCompletion());
    	getCommand("bossHeal").setTabCompleter(new TabCompletion());
    	getCommand("bossEffects").setTabCompleter(new TabCompletion());
    	getCommand("pearlCurse").setTabCompleter(new TabCompletion());
}

    // Fired when plug-in is disabled
    @Override
    public void onDisable()
    {
    	main = null;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String sLabel, String[] args) {
    	return CmdManager.IncomingCommand(sender, command, sLabel, args);
    }
}
