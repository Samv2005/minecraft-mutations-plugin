package com.sam.mutations.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCompletion implements TabCompleter {	
	List<String> arguments = new ArrayList<String>();
	List<String> args2 = new ArrayList<String>();

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String slabel, String[] args) {
		if (arguments.isEmpty()) {
			if (command.getName().equalsIgnoreCase("blazeCurse")){
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("mutations")){
				addToggle(arguments);
				arguments.add("disableall");
				arguments.add("enableall");
				arguments.add("list");
			}
			else if (command.getName().equalsIgnoreCase("creeperDupe")){
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("explodeSummon")){
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("explosiveScale")) {
				addToggle(arguments);
				addScaling(arguments);
			}
			else if (command.getName().equalsIgnoreCase("explosiveProjectiles")) {
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("projectileSummon")) {
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("enderThief")) {
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("mobEffects")) {
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("mobCrit")) {
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("mobDash")) {
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("neutralAttack")) {
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("strongStorm")) {
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("mobStrength")) {
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("creeperFuse")) {
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("mobGroup")) {
				addToggle(arguments);
				addScaling(arguments);
			}
			else if (command.getName().equalsIgnoreCase("bossHeal")) {
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("bossEffects")) {
				addToggle(arguments);
			}
			else if (command.getName().equalsIgnoreCase("pearlCurse")) {
				addToggle(arguments);
			}
			else;
		}
		args2.clear();
		List<String> result = new ArrayList<String>();
		if (args.length == 1) {
			for (String a : arguments) {
				if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
					result.add(a);
				}
			}
			return result;
		}
		else if (args.length == 2) {
			result.clear();
			for (String b : args2) {
				if (b.toLowerCase().startsWith(args[1].toLowerCase())) {
					result.add(b);
				}
			}
			return result;
		}
		
		return null;
	}
	
	public void addToggle(List<String> args) {
		args.add("enable");
		args.add("disable");
	}
	public void addScaling(List<String> args) {
		arguments.add("increase");
		arguments.add("decrease");
		arguments.add("get");
		arguments.add("set");
		arguments.add("reset");
	}
	

}
