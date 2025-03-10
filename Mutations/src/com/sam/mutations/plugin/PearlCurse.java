package com.sam.mutations.plugin;

import org.bukkit.event.Listener;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.sam.mutations.main.Main;

import org.bukkit.event.EventHandler;

public class PearlCurse implements Listener{
	private Player player;
	private boolean trigger;
	
	public static Random rgen = new Random();
	
	// Make ender pearls teleport players randomly sometimes
	@EventHandler
	public void eventT(PlayerTeleportEvent eventT) {
		if (CmdManager.enable && CmdManager.pearlCurse) {
			if (eventT.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
				player = eventT.getPlayer();
				Location to = eventT.getTo();
				Location from = eventT.getFrom();
				Location sent;
				Vector addV = new Vector(0,0,0);
				
				
				int random = rgen.nextInt(100) + 1;
				if (random <= 20) {
					trigger = true;
				}
				else trigger = false;
				
				int dx = to.getBlockX() - from.getBlockX();
				int dz = to.getBlockZ() - from.getBlockZ();
				// Circle with a radius equal to the distance from the player to the ender pearl
				double dr = Math.pow(Math.pow(dx, 2) + Math.pow(dz, 2), 0.5);
				if (dr > 0) {
					int maxR = (int) Math.ceil(dr);
					// More likely that the player ends up on the outer areas of the circle
					int r = (int) Math.ceil(Math.pow(rgen.nextInt((int) Math.pow(maxR, 2)) + 1, 0.5));
					int angle = rgen.nextInt(360) + 1;
					double rad = angle * Math.PI/180.0;
					double sinA = Math.sin(rad);
					double cosA = Math.cos(rad);
					
					// If the chance triggers, the player teleports randomly in a circle with radius r
					if (trigger) {
						eventT.setCancelled(trigger);
						// Zero tick delay so the player teleports randomly after the pearl's teleport triggers
						new BukkitRunnable() {
							
							@Override
							public void run() {
								
							}
						}.runTaskLater(Main.main, 0);
						player.teleport(from);
						addV.setX(r * cosA);
						addV.setZ(r * sinA);
						sent = from.add(addV);
						player.teleport(sent);
						player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 2.0f, 0.5f);
						
						// If case the player teleports into a block, this breaks those blocks
						Location loc = player.getLocation().add(0,0,0);
						Location loc2 = player.getLocation().add(0,1,0);
												
						if (loc.getBlock().getType() != Material.AIR) {
							loc.getBlock().setType(Material.AIR);
						}
						if (loc2.getBlock().getType() != Material.AIR) {
							loc2.getBlock().setType(Material.AIR);
						}
					}
				}	
			}
		}
	}
}
