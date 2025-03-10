package com.sam.mutations.plugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.util.Vector;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CreeperChaos implements Listener {
	private Creeper creeper;
	private Location loc;
	public static Random rgen = new Random();
	// All the bed types
	BlockType[] bedType = {BlockType.BLACK_BED, BlockType.BLUE_BED, BlockType.BROWN_BED, BlockType.CYAN_BED, BlockType.GRAY_BED, BlockType.GREEN_BED, BlockType.LIGHT_BLUE_BED, BlockType.LIGHT_GRAY_BED, BlockType.LIME_BED, BlockType.MAGENTA_BED, BlockType.ORANGE_BED, BlockType.PINK_BED, BlockType.PURPLE_BED, BlockType.RED_BED, BlockType.WHITE_BED, BlockType.YELLOW_BED};
		
	// Making creepers summon more of themselves when exploding
	@EventHandler
	public void eventE(EntityExplodeEvent eventE) {
		if (CmdManager.enable && CmdManager.creeperDupe) {
			if (eventE.getEntityType() == EntityType.CREEPER) {
				
				this.creeper = (Creeper) eventE.getEntity();
				loc = creeper.getLocation();
				// Charged creepers spawn 3 instead of 2
				if (creeper.isPowered()) {
					creeperCircle(3, rgen.nextInt(360), creeper);
				}
				else {
					creeperCircle(2, rgen.nextInt(360), creeper);
				}
			}
		}

		
		// Making entity explosions summon creepers
		if (CmdManager.enable && CmdManager.explodeSummon && !(eventE.getEntityType() == EntityType.CREEPER)) {
			loc = eventE.getEntity().getLocation();
			if (eventE.getEntityType() == EntityType.FIREBALL) {
				eventE.getEntity().getWorld().spawnEntity(loc, EntityType.CREEPER);
			}
			else if (eventE.getEntityType() == EntityType.TNT) {
				creeperCircle(4, rgen.nextInt(360), eventE.getEntity());
			}
			else if (eventE.getEntityType() == EntityType.END_CRYSTAL) {
				creeperCircle(6, rgen.nextInt(360), eventE.getEntity());
			}
		}
	}
	
	// Making dragon fireballs summon creepers
	@EventHandler
	public void eventP(ProjectileHitEvent eventP) {
		if (CmdManager.enable && CmdManager.explodeSummon) {
			loc = eventP.getEntity().getLocation();
			if (eventP.getEntityType() == EntityType.DRAGON_FIREBALL) {
				creeperCircle(2, rgen.nextInt(360), eventP.getEntity());
			}
		}
	}
	
	// Making bed explosions summon creepers
	@SuppressWarnings("deprecation")
	@EventHandler
	public void eventB(PlayerBedEnterEvent eventB) {
		if (CmdManager.enable && CmdManager.explodeSummon) {
			if (eventB.getPlayer().getWorld().getEnvironment().equals(World.Environment.NETHER) || eventB.getPlayer().getWorld().getEnvironment().equals(World.Environment.THE_END)) {
				loc = eventB.getBed().getLocation();
				
				// Same setup as CreeperCircle method, but this uses blocks instead of entities, so it had to be remade
				double degree = rgen.nextInt(360);
				for (int x = 0; x < 4; x++) {
					degree += 360/4;
					
					Vector vChange = new Vector(0,0,0);
					vChange.setX(Math.sin(degree * Math.PI/(rgen.nextInt(180) + 180)) * 0.6);
					vChange.setZ(Math.cos(degree * Math.PI/(rgen.nextInt(180) + 180)) * 0.6);
					creeper = (Creeper) eventB.getBed().getWorld().spawnEntity(loc, EntityType.CREEPER);
					// Creepers need a tick of resistance to not die to the bed exploding
					creeper.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 1, 255), true);
					creeper.setVelocity(vChange);
				}
			}
		}
	}
	
	// Making creepers charge if hit by an explosion, and explode into more creepers if hit again by explosions
	@EventHandler
	public void eventD(EntityDamageByEntityEvent eventD) {
		if (CmdManager.enable && CmdManager.creeperDupe) {
			if (eventD.getEntityType() == EntityType.CREEPER) {
				this.creeper = (Creeper) eventD.getEntity();
				loc = creeper.getLocation();
				if (eventD.getDamager().getType() == EntityType.CREEPER) {
					// Creepers explode into 4 if charged, and hit by another creeper
					if (creeper.isPowered()) {
						creeper.remove();
						creeperCircle(4, rgen.nextInt(360), creeper);
					}
					// Creepers charge if hit by another creeper
					else {
						creeper.setPowered(true);
					}
				}
			}
		}
	}
	// Making a range of creeper fuse lengths
	@EventHandler
	public void eventS(EntitySpawnEvent eventS) {
		if (CmdManager.enable && CmdManager.creeperFuse) {
			if (eventS.getEntityType() == EntityType.CREEPER) {
				this.creeper = (Creeper) eventS.getEntity();
				// Ranging fuse ticks from 20 to 35 (normal is 30)
				creeper.setMaxFuseTicks(rgen.nextInt(16)+20);
			}
		}
	}
	
	// Creepers exploding in circular patterns
	@SuppressWarnings("deprecation")
	public void creeperCircle(int count, double degreeStart, Entity entity) {
		double degree = degreeStart;
		for (int x = 0; x < count; x++) {
			degree += 360/count;
			
			Vector vChange = new Vector(0,0.75,0);
			vChange.setX(Math.sin(degree * Math.PI/(rgen.nextInt(360))) * 0.6);
			vChange.setZ(Math.cos(degree * Math.PI/(rgen.nextInt(360))) * 0.6);
			creeper = (Creeper) entity.getWorld().spawnEntity(loc, EntityType.CREEPER);
			// Needed to stop creepers from dying of fall damage if summoned by an end crystal exploding
			if (entity.getType() == EntityType.END_CRYSTAL) {
				creeper.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 255), true);
			}
			creeper.setVelocity(vChange);
		}
	}

}
