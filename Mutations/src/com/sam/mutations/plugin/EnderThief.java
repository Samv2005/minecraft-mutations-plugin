package com.sam.mutations.plugin;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.sam.mutations.main.Main;

public class EnderThief implements Listener{
	private Location loc;
	private Enderman enderman;
	public static Random rgen = new Random();
	
	// Endermen grab blocks and teleport after spawning
	@EventHandler
	public void eventS(CreatureSpawnEvent eventS) {
		if (CmdManager.enable && CmdManager.enderThief) {
			if (eventS.getEntityType() == EntityType.ENDERMAN) {
				this.enderman = (Enderman) eventS.getEntity();
				loc = enderman.getLocation();
				loc.add(0,-1,0);
				if (!loc.getBlock().isEmpty()) {
					loc = enderman.getLocation();
					loc.add(0,-1,0);
					enderman.setCarriedBlock(loc.getBlock().getBlockData());
					loc.getBlock().breakNaturally();
					enderman.teleport();
					
					loc = enderman.getLocation();
					loc.add(1,0,1);
					
					loc.getBlock().setBlockData(enderman.getCarriedBlock());
					enderman.setCarriedBlock(null);
				}
			}
		}
	}
	
	// Endermen grab blocks and teleport 5 seconds after after teleporting
	@EventHandler
	public void eventT(EntityTeleportEvent eventT) {
		if (CmdManager.enable && CmdManager.enderThief) {
			if (eventT.getEntityType() == EntityType.ENDERMAN) {
				this.enderman = (Enderman) eventT.getEntity();
				loc = enderman.getLocation();
				loc.add(0,-1,0);
				if (!loc.getBlock().isEmpty()) {
				enderman.teleportTowards(enderman);
					new BukkitRunnable() {	
						@Override
						public void run() {
							loc = enderman.getLocation();
							loc.add(0,-1,0);
							enderman.setCarriedBlock(loc.getBlock().getBlockData());
							loc.getBlock().breakNaturally();
							enderman.teleport();
							
							loc = enderman.getLocation();
							loc.add(1,0,1);
							
							loc.getBlock().setBlockData(enderman.getCarriedBlock());
							enderman.setCarriedBlock(null);
						}
					}.runTaskLater(Main.main, 100);
				}
			}
		}
	}
	
	// Endermen grab blocks and teleport after getting damaged
	@EventHandler
	public void eventD(EntityDamageByEntityEvent eventD) {
		if (CmdManager.enable && CmdManager.enderThief) {
			if (eventD.getEntityType() == EntityType.ENDERMAN) {
				// 10% chance after players hit so it's not too hard to kill them
				if (eventD.getDamager().getType() == EntityType.PLAYER) {
					if ((rgen.nextInt(100) + 1) <= 10) {
						this.enderman = (Enderman) eventD.getEntity();
						loc = enderman.getLocation();
						loc.add(0,-1,0);
						if (!loc.getBlock().isEmpty()) {
							loc = enderman.getLocation();
							loc.add(0,-1,0);
							enderman.setCarriedBlock(loc.getBlock().getBlockData());
							loc.getBlock().breakNaturally();
							enderman.teleport();
							
							loc = enderman.getLocation();
							loc.add(1,0,1);
							
							loc.getBlock().setBlockData(enderman.getCarriedBlock());
							enderman.setCarriedBlock(null);
						}
					}
				}
				else {
					this.enderman = (Enderman) eventD.getEntity();
					loc = enderman.getLocation();
					loc.add(0,-1,0);
					if (!loc.getBlock().isEmpty()) {
						loc = enderman.getLocation();
						loc.add(0,-1,0);
						enderman.setCarriedBlock(loc.getBlock().getBlockData());
						loc.getBlock().breakNaturally();
						enderman.teleport();
						
						loc = enderman.getLocation();
						loc.add(1,0,1);
						
						loc.getBlock().setBlockData(enderman.getCarriedBlock());
						enderman.setCarriedBlock(null);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void eventN(EntityDamageEvent eventN) {
		if (CmdManager.enable && CmdManager.enderThief) {
			if (eventN.getEntityType() == EntityType.ENDERMAN) {
				if (eventN.getDamageSource().getCausingEntity() == null) {
					this.enderman = (Enderman) eventN.getEntity();
					loc = enderman.getLocation();
					loc.add(0,-1,0);
					if (!loc.getBlock().isEmpty()) {
						loc = enderman.getLocation();
						loc.add(0,-1,0);
						enderman.setCarriedBlock(loc.getBlock().getBlockData());
						loc.getBlock().breakNaturally();
						enderman.teleport();
						
						loc = enderman.getLocation();
						loc.add(1,0,1);
						
						loc.getBlock().setBlockData(enderman.getCarriedBlock());
						enderman.setCarriedBlock(null);
					}
				}
			}
		}
	}
}
