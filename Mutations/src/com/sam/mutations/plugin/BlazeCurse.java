package com.sam.mutations.plugin;

import org.bukkit.event.Listener;
import org.bukkit.entity.SmallFireball;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.EventHandler;

public class BlazeCurse implements Listener{
	private SmallFireball fireball;
	private Location loc;
	private int x = 0;
	private int randomNum;
	
	public static Random rgen = new Random();
	
	Player[] online;
	public static EntityType[] summonPool = {EntityType.BREEZE, EntityType.BLAZE, EntityType.BOGGED, EntityType.CAVE_SPIDER, EntityType.ENDERMITE, EntityType.ENDERMAN, EntityType.DROWNED, EntityType.GHAST, EntityType.HOGLIN, EntityType.HUSK, EntityType.SKELETON, EntityType.SLIME, EntityType.RAVAGER, EntityType.PILLAGER, EntityType.PIGLIN, EntityType.PHANTOM, EntityType.MAGMA_CUBE, EntityType.PIGLIN_BRUTE, EntityType.SILVERFISH, EntityType.SPIDER, EntityType.STRAY, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITHER_SKELETON, EntityType.WITCH, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.SHULKER, EntityType.EVOKER};
	
	static EntityType summoned;
	
	// Blaze fireballs summon mobs
	@EventHandler
	public void eventD(ProjectileHitEvent event) {
		
		// Making blaze fireballs summon enemies
		if (CmdManager.enable && CmdManager.blazeCurse) {
			if (event.getEntityType() == EntityType.SMALL_FIREBALL) {
				int summonNum = 0;
				
				// For some reason 2 events are counted when fireballs hit, so x is used to summon a mob every other event
				x++;
				this.fireball = (SmallFireball) event.getEntity();
				loc = fireball.getLocation();
				if (x == 1) {
					//Randomizing entity spawned
					randomNum = rgen.nextInt(92) + 1;
					if (randomNum <= 15) {
						summoned = EntityType.BLAZE;
					}
					else if (randomNum > 15 && randomNum < 35) {
						summoned = EntityType.CREEPER;
					}
					else {
						// Logic to randomize summonPool, since 0-35 is taken, and to make it closer to an exact percentage, the generated number is from 0-92, 
						// that being 40 + # of mobs times 2 (minus one for evokers to be less likely)
						summonNum = (randomNum - 35)/2;
						summoned = summonPool[summonNum];
					}
					fireball.getWorld().spawnEntity(loc, summoned);
				}
				if (x >= 2) {
					x = 0;
				}
				
			}
		}
	}
}
