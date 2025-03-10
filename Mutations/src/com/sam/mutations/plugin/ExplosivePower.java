package com.sam.mutations.plugin;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ExplosivePower implements Listener{
	private Creeper creeper;
	private TNTPrimed tnt;
	private Fireball fireball;
	private double power;
	
	// Scaling creeper and TNT explosions
	@EventHandler
	public void eventS(EntitySpawnEvent eventS) {
		if (CmdManager.enable && CmdManager.explosiveScale) {
			if (eventS.getEntityType() == EntityType.CREEPER) {
				this.creeper = (Creeper) eventS.getEntity();
				power = creeper.getExplosionRadius();
				creeper.setExplosionRadius((int) (power * CmdManager.explosivePower));
			}
			else if (eventS.getEntityType() == EntityType.TNT) {
				this.tnt = (TNTPrimed) eventS.getEntity();
				power = tnt.getYield();
				tnt.setYield((float) (power * CmdManager.explosivePower));
			}
		}
	}
	
	// Scaling fireball explosions
	@EventHandler
	public void eventL(ProjectileLaunchEvent eventL) {
		if (CmdManager.enable && CmdManager.explosiveScale) {
			if (eventL.getEntityType() == EntityType.FIREBALL) {
				this.fireball = (Fireball) eventL.getEntity();
				power = fireball.getYield();
				fireball.setYield((int) (power * CmdManager.explosivePower));
			}
		}
	}
}
