package com.sam.mutations.plugin;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileChange implements Listener{
	private Location loc;
	private double power;
	public static Random rgen = new Random();
	
	// All projectiles except blaze and ghast fireballs will now explode with roughly the power of a creeper
	@EventHandler
	public void eventP(ProjectileHitEvent eventP) {
		if (CmdManager.enable) {
			if (eventP.getEntityType() != EntityType.SMALL_FIREBALL) {
				loc = eventP.getEntity().getLocation();
				power = 2.6;
				if (CmdManager.explosiveScale) {
					power *= CmdManager.explosivePower;
				}
				if (CmdManager.explosiveProjectiles) {
					// 5% chance to explode
					if ((rgen.nextInt(100) + 1) <= 5) {
						eventP.getEntity().getWorld().createExplosion(loc, (float) power, false, true);
						eventP.getEntity().remove();
					}
				}
			}
			if (CmdManager.projectileSummon) {
				// 10% chance to summon
				if ((rgen.nextInt(100) + 1) <= 10) {
					loc = eventP.getEntity().getLocation();
					// Pulls summon pool from BlazeCurse summon pool
					eventP.getEntity().getWorld().spawnEntity(loc, BlazeCurse.summonPool[rgen.nextInt(BlazeCurse.summonPool.length)]);
				}
			}
		}
	}

}
