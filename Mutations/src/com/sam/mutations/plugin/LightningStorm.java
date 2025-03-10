package com.sam.mutations.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.sam.mutations.main.Main;

public class LightningStorm implements Listener{
	private Random rgen = new Random();
	private Location loc;
	private Player player;
	
	// Entities that have some form of transformation when struck by lightning
	EntityType[] transformable = {EntityType.CREEPER, EntityType.VILLAGER, EntityType.PIG, EntityType.TURTLE};
	
	@EventHandler
	public void eventT(ThunderChangeEvent eventT) {
		
		// This is needed since it takes a moment to change the weather, and it doubles as the loop to make lightning strike every so often
		new BukkitRunnable() {	
			private Entity entity;
	
			@Override
			public void run() {
				if (CmdManager.enable && CmdManager.strongStorm && eventT.getWorld().isThundering()) {
					// 5% chance per second for lightning to strike near the player
					if (rgen.nextInt(100) + 1 <= 5) {
						List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
						// Grabbing a random player
						player = players.get(rgen.nextInt(players.size()));
						// Making sure clear weather stops the loop
						if (player.getWorld().isClearWeather()) {
							Bukkit.getScheduler().cancelTask(getTaskId());
						}
						else {
							if (rgen.nextInt(2) + 1 <= 1) {
								// Striking the area around players with lightning
								Vector add = new Vector(rgen.nextInt(13) - 7, -1, rgen.nextInt(13) - 7);
								loc = player.getLocation().add(add);
								player.getWorld().strikeLightning(loc);
							}
							else {
								// Making transformable mobs get struck with lightning
								List<Entity> entities = player.getNearbyEntities(40, 8, 40);
								for (int x = 0; x < entities.size(); x++) {
		            				for (int z = 0; z < transformable.length; z++) {
		            					if (entities.get(x).getType() == transformable[z]) {
		            						this.entity = (Entity) entities.get(x);
		            						// Making sure charged creepers aren't struck
		            						if (entity.getType() == EntityType.CREEPER) {
		            							Creeper creeper = (Creeper) entity;
		            							if (!creeper.isPowered()) {
		            								entity.getWorld().strikeLightning(entity.getLocation());
		            								// Ensures not every creeper is struck
		            								x += rgen.nextInt(5) + 1;
		            							}
		            						}
		            						else {
		            							entity.getWorld().strikeLightning(entity.getLocation());
		            							// Ensures only one mob is struck if not a creeper
		                						x = entities.size() - 1;
		            						}
		            						
		            					}
		            				}
		            			}
							}
						}
					}
				}
				// Also making sure clear weather stops the loop
				else {
					Bukkit.getScheduler().cancelTask(getTaskId());
				}
			}
		}.runTaskTimer(Main.main, 20, 20);
		
	}
	
	// Changing rain to thunder
	@EventHandler
	public void eventW(WeatherChangeEvent eventW) {
		if (CmdManager.enable && CmdManager.strongStorm) {
			new BukkitRunnable() {	
				@Override
				public void run() {
					if (!eventW.getWorld().isClearWeather() && !eventW.getWorld().isThundering()) {
						eventW.getWorld().setThundering(true);
					}
				}
			}.runTaskLater(Main.main, 10);

			
		}
	}
	
	// Stopping players from sleeping away storms
	@EventHandler
	public void event(PlayerBedEnterEvent event) {
		if (CmdManager.enable && CmdManager.strongStorm) {
			if (event.getPlayer().getWorld().isThundering()) {
				event.setUseBed(Result.DENY);
				event.getPlayer().sendMessage("No");
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void eventD(EntityDamageByEntityEvent eventD) {
		if (CmdManager.enable && CmdManager.strongStorm) {
			// Making sure a player was damaged
			if ((eventD.getEntityType() == EntityType.PLAYER) && (eventD.getDamager().getType() == EntityType.LIGHTNING_BOLT)) {
				player = (Player) eventD.getEntity();
				// Increasing damage dealt to players
				player.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, 1), true);
			}
		}
	}
}
