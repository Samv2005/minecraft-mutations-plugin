package com.sam.mutations.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.sam.mutations.main.Main;

public class CmdManager implements CommandExecutor{
	public static boolean enable = Main.data.getConfig().getBoolean("mutations");
	public static boolean blazeCurse = Main.data.getConfig().getBoolean("blazeCurse");
	public static boolean creeperDupe = Main.data.getConfig().getBoolean("creeperDupe");
	public static boolean explodeSummon = Main.data.getConfig().getBoolean("explodeSummon");
	public static boolean explosiveScale = Main.data.getConfig().getBoolean("explosiveScale");
	public static boolean explosiveProjectiles = Main.data.getConfig().getBoolean("explosiveProjectiles");
	public static boolean projectileSummon = Main.data.getConfig().getBoolean("projectileSummon");
	public static boolean enderThief = Main.data.getConfig().getBoolean("enderThief");
	public static boolean mobEffects = Main.data.getConfig().getBoolean("mobEffects");
	public static boolean mobCrit = Main.data.getConfig().getBoolean("mobCrit");
	public static boolean mobDash = Main.data.getConfig().getBoolean("mobDash");
	public static boolean neutralAttack = Main.data.getConfig().getBoolean("neutralAttack");
	public static boolean strongStorm = Main.data.getConfig().getBoolean("strongStorm");
	public static boolean mobStrength = Main.data.getConfig().getBoolean("mobStrength");
	public static boolean creeperFuse = Main.data.getConfig().getBoolean("creeperFuse");
	public static boolean mobGroup = Main.data.getConfig().getBoolean("mobGroup");
	public static boolean bossHeal = Main.data.getConfig().getBoolean("bossHeal");
	public static boolean bossEffects = Main.data.getConfig().getBoolean("bossEffects");
	public static boolean pearlCurse = Main.data.getConfig().getBoolean("pearlCurse");
	
	public static double explosivePower = Main.data.getConfig().getDouble("explosivePower");
	public static double mobGroupMulti = Main.data.getConfig().getDouble("mobGroupMulti");
	
	public static String[] mutations = {"blazeCurse", "creeperDupe", "explodeSummon", "explosiveScale", "explosiveProjectiles", "projectileSummon", "enderThief", "mobEffects", 
			"mobCrit", "mobDash", "neutralAttack", "strongStorm", "mobStrength", "creeperFuse", "mobGroup", "bossHeal", "bossEffects", "pearlCurse"};	
	public static boolean IncomingCommand(CommandSender sender, Command command, String sLabel, String[] args) {
		
		//bossEffectsEnable
		if (command.getName().equalsIgnoreCase("bossEffects")) {
			if (args.length > 0 && args.length <= 1) {
				bossEffects = ToggleMutation(args[0], "bossEffects", bossEffects, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "bossEffects", bossEffects);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		//bossHealEnable
		if (command.getName().equalsIgnoreCase("bossHeal")) {
			if (args.length > 0 && args.length <= 1) {
				bossHeal = ToggleMutation(args[0], "bossHeal", bossHeal, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "bossHeal", bossHeal);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// mobGroup
		if (command.getName().equalsIgnoreCase("mobGroup")) {
			if (args.length > 0 && args.length <= 2) {
				if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("disable")) {
					mobGroup = ToggleMutation(args[0], "mobGroup", mobGroup, sender);
				}
				else if (args[0].equalsIgnoreCase("increase") || args[0].equalsIgnoreCase("decrease") || args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("get")) {
					if (args[0].equalsIgnoreCase("increase")) {
						sender.sendMessage("Mob Group Multiplier " + args[0] + "d");
						// The math here is weird, but the way it works is at 1.0, no extra mobs spawn, and at 2.0, mobs are guaranteed to spawn another, recursively
						double temp = (2 - mobGroupMulti) * .8;
						mobGroupMulti = 2 - temp;
						Main.data.getConfig().set("mobGroupMulti", mobGroupMulti);
						Main.data.saveConfig();
					}
					else if (args[0].equalsIgnoreCase("decrease")) {
						sender.sendMessage("Mob Group Multiplier " + args[0] + "d");
						double temp = (2 - mobGroupMulti) / .8;
						mobGroupMulti = 2 - temp;
						Main.data.getConfig().set("mobGroupMulti", mobGroupMulti);
						Main.data.saveConfig();
					}
					
					else if (args[0].equalsIgnoreCase("set")) {
						if (args.length == 1) {
							sender.sendMessage("Mob Group Multiplier is currently set to: " + mobGroupMulti);
						}
						else if (args.length == 2) {
							try {
								mobGroupMulti = Double.parseDouble(args[1]);
								Main.data.getConfig().set("mobGroupMulti", mobGroupMulti);
								Main.data.saveConfig();
								sender.sendMessage("Mob Group Multiplier set to " + mobGroupMulti);
							}
							catch(Exception NumberFormatException){
								sender.sendMessage("Mob Group Multiplier is currently set to: " + mobGroupMulti);
							}
							finally{}
						}
					}
					else if (args[0].equalsIgnoreCase("get")) {
						sender.sendMessage("Mob Group Multiplier currently set to: " + mobGroupMulti);
					}
				}
				else if (args[0].equalsIgnoreCase("reset")) {
					sender.sendMessage("Mob Group Multiplier reset");
					mobGroupMulti = 1.0;
					Main.data.getConfig().set("mobGroupMulti", mobGroupMulti);
					Main.data.saveConfig();
				}
				else sender.sendMessage("Invalid input");
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "mobGroup", mobGroup);
			}
			else if (args.length > 2) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		//creeperFuseEnable
		if (command.getName().equalsIgnoreCase("creeperFuse")) {
			if (args.length > 0 && args.length <= 1) {
				creeperFuse = ToggleMutation(args[0], "creeperFuse", creeperFuse, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "creeperFuse", creeperFuse);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// mobStrengthEnable
		if (command.getName().equalsIgnoreCase("mobStrength")) {
			if (args.length > 0 && args.length <= 1) {
				mobStrength = ToggleMutation(args[0], "mobStrength", mobStrength, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "mobStrength", mobStrength);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// strongStormEnable
		if (command.getName().equalsIgnoreCase("strongStorm")) {
			if (args.length > 0 && args.length <= 1) {
				strongStorm = ToggleMutation(args[0], "strongStorm", strongStorm, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "strongStorm", strongStorm);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// neutralAttackEnable
		if (command.getName().equalsIgnoreCase("neutralAttack")) {
			if (args.length > 0 && args.length <= 1) {
				neutralAttack = ToggleMutation(args[0], "neutralAttack", neutralAttack, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "neutralAttack", neutralAttack);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// mobDashEnable
		if (command.getName().equalsIgnoreCase("mobDash")) {
			if (args.length > 0 && args.length <= 1) {
				mobDash = ToggleMutation(args[0], "mobDash", mobDash, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "mobDash", mobDash);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// mobCritEnable
		if (command.getName().equalsIgnoreCase("mobCrit")) {
			if (args.length > 0 && args.length <= 1) {
				mobCrit = ToggleMutation(args[0], "mobCrit", mobCrit, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "mobCrit", mobCrit);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// mobEffectsEnable
		if (command.getName().equalsIgnoreCase("mobEffects")) {
			if (args.length > 0 && args.length <= 1) {
				mobEffects = ToggleMutation(args[0], "mobEffects", mobEffects, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "mobEffects", mobEffects);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		
		// enderThiefEnable
		if (command.getName().equalsIgnoreCase("enderThief")) {
			if (args.length > 0 && args.length <= 1) {
				enderThief = ToggleMutation(args[0], "enderThief", enderThief, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "enderThief", enderThief);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// projectileSummonEnable
		if (command.getName().equalsIgnoreCase("projectileSummon")) {
			if (args.length > 0 && args.length <= 1) {
				projectileSummon = ToggleMutation(args[0], "projectileSummon", projectileSummon, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "projectileSummon", projectileSummon);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// explosiveProjectilesEnable
		if (command.getName().equalsIgnoreCase("explosiveProjectiles")) {
			if (args.length > 0 && args.length <= 1) {
				explosiveProjectiles = ToggleMutation(args[0], "explosiveProjectiles", explosiveProjectiles, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "explosiveProjectiles", explosiveProjectiles);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// explosiveScale
		if (command.getName().equalsIgnoreCase("explosiveScale")) {
			if (args.length > 0 && args.length <= 2) {
				if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("disable")) {
					explosiveScale = ToggleMutation(args[0], "explosiveScale", explosiveScale, sender);
				}
				else if (args[0].equalsIgnoreCase("increase") || args[0].equalsIgnoreCase("decrease") || args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("get")) {
					if (args[0].equalsIgnoreCase("increase")) {
						sender.sendMessage("Explosive Multiplier " + args[0] + "d");
						explosivePower *= 1.25;
						Main.data.getConfig().set("explosivePower", explosivePower);
						Main.data.saveConfig();
					}
					else if (args[0].equalsIgnoreCase("decrease")) {
						sender.sendMessage("Explosive Multiplier " + args[0] + "d");
						explosivePower /= 1.25;
						Main.data.getConfig().set("explosivePower", explosivePower);
						Main.data.saveConfig();
					}
					
					else if (args[0].equalsIgnoreCase("set")) {
						if (args.length == 1) {
							sender.sendMessage("Explosive Multiplier is currently set to: " + explosivePower);
						}
						else if (args.length == 2) {
							try {
								explosivePower = Double.parseDouble(args[1]);
								Main.data.getConfig().set("explosivePower", explosivePower);
								Main.data.saveConfig();
								sender.sendMessage("Explosive Multiplier set to " + explosivePower);
							}
							catch(Exception NumberFormatException){
								sender.sendMessage("Explosive Multiplier is currently set to: " + explosivePower);
							}
							finally{}
						}
					}
					else if (args[0].equalsIgnoreCase("get")) {
						sender.sendMessage("Explosive Multiplier currently set to: " + explosivePower);
					}
				}
				else if (args[0].equalsIgnoreCase("reset")) {
					sender.sendMessage("Explosive Multiplier reset");
					explosivePower = 1.0;
					Main.data.getConfig().set("explosivePower", explosivePower);
					Main.data.saveConfig();
				}
				else sender.sendMessage("Invalid input");
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "explosiveScale", explosiveScale);
			}
			else if (args.length > 2) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// blazeCurseEnable
		if (command.getName().equalsIgnoreCase("blazeCurse")) {
			if (args.length > 0 && args.length <= 1) {
				blazeCurse = ToggleMutation(args[0], "blazeCurse", blazeCurse, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "blazeCurse", blazeCurse);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// pearlCurseEnable
		if (command.getName().equalsIgnoreCase("pearlCurse")) {
			if (args.length > 0 && args.length <= 1) {
				pearlCurse = ToggleMutation(args[0], "pearlCurse", pearlCurse, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "pearlCurse", pearlCurse);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// creeperDupeEnable
		if (command.getName().equalsIgnoreCase("creeperDupe")) {
			if (args.length > 0 && args.length <= 1) {
				creeperDupe = ToggleMutation(args[0], "creeperDupe", creeperDupe, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "creeperDupe", creeperDupe);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// explodeSummonEnable
		if (command.getName().equalsIgnoreCase("explodeSummon")) {
			if (args.length > 0 && args.length <= 1) {
					explodeSummon = ToggleMutation(args[0], "explodeSummon", explodeSummon, sender);
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "explodeSummon", explodeSummon);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		// mutationsEnable
		if (command.getName().equalsIgnoreCase("mutations")) {
			if (args.length > 0 && args.length <= 1) {
				if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("disableAll") || args[0].equalsIgnoreCase("enableAll")) {
					if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("disable")) {
						enable = ToggleMutation(args[0], "mutations", enable, sender);
					}
					else if (args[0].equalsIgnoreCase("list")) {
						sender.sendMessage("BlazeCurse: " + blazeCurse);
						sender.sendMessage("CreeperDupe: " + creeperDupe);
						sender.sendMessage("ExplosiveScale: " + explosiveScale);
						sender.sendMessage("ExplodeSummon: " + explodeSummon);
						sender.sendMessage("ExplosiveProjectiles: " + explosiveProjectiles);
						sender.sendMessage("ProjectileSummon: " + projectileSummon);
						sender.sendMessage("EnderThief: " + enderThief);
						sender.sendMessage("MobEffects: " + mobEffects);
						sender.sendMessage("MobCrit: " + mobCrit);
						sender.sendMessage("MobDash: " + mobDash);
						sender.sendMessage("NeutralAttack: " + neutralAttack);
						sender.sendMessage("StrongStorm: " + strongStorm);
						sender.sendMessage("MobStrength: " + mobStrength);
						sender.sendMessage("CreeperFuse: " + creeperFuse);
						sender.sendMessage("MobGroup: " + mobGroup);
						sender.sendMessage("BossHeal: " + bossHeal);
						sender.sendMessage("BossEffects: " + bossEffects);
						sender.sendMessage("PearlCurse: " + pearlCurse);
					}
					else if (args[0].equalsIgnoreCase("disableAll")) {
						for (int x = 0; x < mutations.length; x++) {
							Main.data.getConfig().set(mutations[x], false);
						}
						sender.sendMessage("All mutations disabled");
						blazeCurse = false;
						creeperDupe = false;
						explodeSummon = false;
						explosivePower = 1.0;
						explosiveScale = false;
						explosiveProjectiles = false;
						projectileSummon = false;
						enderThief = false;
						mobEffects = false;
						mobCrit = false;
						mobDash = false;
						neutralAttack = false;
						strongStorm = false;
						mobStrength = false;
						creeperFuse = false;
						mobGroupMulti = 1.0;
						mobGroup = false;
						bossHeal = false;
						bossEffects = false;
						pearlCurse = false;
					}
					else if (args[0].equalsIgnoreCase("enableAll")) {
						for (int x = 0; x < mutations.length; x++) {
							Main.data.getConfig().set(mutations[x], true);
						}
						sender.sendMessage("All mutations enabled");
						blazeCurse = true;
						creeperDupe = true;
						explodeSummon = true;
						explosiveScale = true;
						explosiveProjectiles = true;
						projectileSummon = true;
						enderThief = true;
						mobEffects = true;
						mobCrit = true;
						mobDash = true;
						neutralAttack = true;
						strongStorm = true;
						mobStrength = true;
						creeperFuse = true;
						mobGroup = true;
						bossHeal = true;
						bossEffects = true;
						pearlCurse = true;
					}
					Main.data.saveConfig();
							
				}
				else sender.sendMessage("Invalid input");
			}
			else if (args.length == 0) {
				ReturnMutation(sender, "mutations", enable);
			}
			else if (args.length > 1) {
				sender.sendMessage("Too many arguments");
			}
			return true;
		}
		
		return false;
	}
	
	// True or False mutations
	public static boolean ToggleMutation(String toggle, String name, boolean mutation, CommandSender sender) {
		if (toggle.equalsIgnoreCase("enable")) {
			Main.data.getConfig().set(name, true);
			sender.sendMessage(name + " enabled");
			Main.data.saveConfig();
			return true;
		}
		else if (toggle.equalsIgnoreCase("disable")) {
			Main.data.getConfig().set(name, false);
			sender.sendMessage(name + " disabled");
			Main.data.saveConfig();
			return false;
		}
		sender.sendMessage("Input must be either enable or disable");
		return mutation;
	}
	
	// Sending if a mutation is enabled or disabled
	public static void ReturnMutation(CommandSender sender, String mutationName, boolean mutation) {
		if (mutation) {
			sender.sendMessage(mutationName + " is currently: enabled");
		}
		else {
			sender.sendMessage(mutationName + " is currently: disabled");
		}
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
