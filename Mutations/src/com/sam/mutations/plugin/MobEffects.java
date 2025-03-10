package com.sam.mutations.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.sam.mutations.main.Main;

public class MobEffects implements Listener{
	private Player player;
	public static Random rgen = new Random();
	private Integer num;
	private int y = 0;
	private Arrow arrow;
	private Mob mob;
	private boolean bossEffectLoopOn;
	// Positive effect pool for withers
	PotionEffectType[] positiveWither = {PotionEffectType.INVISIBILITY, PotionEffectType.ABSORPTION, PotionEffectType.RESISTANCE, PotionEffectType.INFESTED};
	// Positive effect pool for ender dragons
	PotionEffectType[] positiveDragon = {PotionEffectType.REGENERATION, PotionEffectType.ABSORPTION, PotionEffectType.RESISTANCE, PotionEffectType.INFESTED};
	// Positive effect pool
	PotionEffectType[] positive = {PotionEffectType.SPEED, PotionEffectType.STRENGTH, PotionEffectType.JUMP_BOOST, PotionEffectType.RESISTANCE, PotionEffectType.FIRE_RESISTANCE, PotionEffectType.INVISIBILITY, PotionEffectType.HEALTH_BOOST, PotionEffectType.ABSORPTION, PotionEffectType.INFESTED, PotionEffectType.WEAVING, PotionEffectType.OOZING, PotionEffectType.WIND_CHARGED};
	// Negative effect pool
	PotionEffectType[] effectList = {PotionEffectType.BLINDNESS, PotionEffectType.POISON, PotionEffectType.SLOWNESS, PotionEffectType.MINING_FATIGUE, PotionEffectType.NAUSEA, PotionEffectType.HUNGER, PotionEffectType.WEAKNESS, PotionEffectType.WITHER, PotionEffectType.LEVITATION, PotionEffectType.DARKNESS, PotionEffectType.RAID_OMEN, PotionEffectType.INFESTED};
	// Mob pool
	EntityType[] mobList = {EntityType.BREEZE, EntityType.BOGGED, EntityType.CAVE_SPIDER, EntityType.ENDERMITE, EntityType.ENDERMAN, EntityType.DROWNED, EntityType.GHAST, EntityType.HOGLIN, EntityType.HUSK, EntityType.SKELETON, EntityType.SLIME, EntityType.RAVAGER, EntityType.PILLAGER, EntityType.PIGLIN, EntityType.PHANTOM, EntityType.MAGMA_CUBE, EntityType.PIGLIN_BRUTE, EntityType.SILVERFISH, EntityType.SPIDER, EntityType.STRAY, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITHER_SKELETON, EntityType.WITCH, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.SHULKER, EntityType.EVOKER, EntityType.GUARDIAN, EntityType.WITHER, EntityType.ENDER_DRAGON, EntityType.WITHER_SKULL, EntityType.WARDEN, EntityType.ELDER_GUARDIAN, EntityType.CREEPER, EntityType.BLAZE, EntityType.FOX, EntityType.WOLF, EntityType.ENDERMAN, EntityType.PIGLIN, EntityType.ZOMBIFIED_PIGLIN, EntityType.DOLPHIN, EntityType.LLAMA, EntityType.PANDA, EntityType.BEE, EntityType.POLAR_BEAR, EntityType.GOAT, EntityType.IRON_GOLEM};
	// Hostile mob pool
	EntityType[] mobList2 = {EntityType.BREEZE, EntityType.BOGGED, EntityType.CAVE_SPIDER, EntityType.ENDERMITE, EntityType.ENDERMAN, EntityType.DROWNED, EntityType.GHAST, EntityType.HOGLIN, EntityType.HUSK, EntityType.SKELETON, EntityType.SLIME, EntityType.RAVAGER, EntityType.PILLAGER, EntityType.PIGLIN, EntityType.PHANTOM, EntityType.MAGMA_CUBE, EntityType.PIGLIN_BRUTE, EntityType.SILVERFISH, EntityType.SPIDER, EntityType.STRAY, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITHER_SKELETON, EntityType.WITCH, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.SHULKER, EntityType.EVOKER, EntityType.GUARDIAN, EntityType.CREEPER, EntityType.BLAZE, EntityType.ENDERMAN, EntityType.PIGLIN, EntityType.ZOMBIFIED_PIGLIN};
	// Neutral mob pool
	EntityType[] neutral = {EntityType.FOX, EntityType.WOLF, EntityType.ENDERMAN, EntityType.PIGLIN, EntityType.ZOMBIFIED_PIGLIN, EntityType.DOLPHIN, EntityType.LLAMA, EntityType.PANDA, EntityType.BEE, EntityType.POLAR_BEAR, EntityType.GOAT, EntityType.IRON_GOLEM, EntityType.SPIDER, EntityType.CAVE_SPIDER};
	// Boss list
	List<Entity> bosses = new ArrayList<>();
	
	// Making players receive random effects when hit, or get crit
	@SuppressWarnings("deprecation")
	@EventHandler
	public void eventD(EntityDamageByEntityEvent eventD) {
		if (CmdManager.enable && (CmdManager.mobEffects || CmdManager.mobCrit || CmdManager.bossHeal || CmdManager.bossEffects)) {
			// Making sure a player was damaged
			if (eventD.getEntityType() == EntityType.PLAYER) {
				player = (Player) eventD.getEntity();
				// Making sure a player didn't deal the damage
				if (!(eventD.getDamager().getType() == EntityType.PLAYER)) {
					// Seperate section so skeletons will also function
					if (eventD.getDamager().getType() == EntityType.ARROW) {
						arrow = (Arrow) eventD.getDamager();
						if (!(arrow.getShooter() instanceof Player)) {
							// Inflicting random effect
							if (CmdManager.mobEffects) {
								if ((rgen.nextInt(100) + 1) <= 20) {
									num = rgen.nextInt(effectList.length);
									player.addPotionEffect(new PotionEffect(effectList[num], 90, 1), true);
								}
							}
							// Mobs can crit
							if (CmdManager.mobCrit) {
								if ((rgen.nextInt(100) + 1) <= 10) {
									num = rgen.nextInt(effectList.length);
									player.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, 1), true);
									player.playSound(player, Sound.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, SoundCategory.PLAYERS, 2.0f, 1.0f);
								}
							}
						}
					}
					// Non-ranged mobs
					else {
						// Inflicting random effect
						if (CmdManager.mobEffects) {
							if ((rgen.nextInt(100) + 1) <= 20) {
								num = rgen.nextInt(effectList.length);
								player.addPotionEffect(new PotionEffect(effectList[num], 90, 1), true);
							}
						}
						// Mobs can crit
						if (CmdManager.mobCrit) {
							if ((rgen.nextInt(100) + 1) <= 10) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, 1), true);
								player.playSound(player, Sound.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, SoundCategory.PLAYERS, 2.0f, 1.0f);
							}
						}
						// Bosses can heal
						if (CmdManager.bossHeal) {
							if (rgen.nextInt(100) + 1 <= 50 && (eventD.getDamager().getType() == EntityType.ENDER_DRAGON || eventD.getDamager().getType() == EntityType.ELDER_GUARDIAN)) {
								if (eventD.getDamager().getType() == EntityType.ENDER_DRAGON) {
									EnderDragon dragon = (EnderDragon) eventD.getDamager();
									if (dragon.getMaxHealth() - dragon.getHealth() < 100) {
										dragon.setHealth(dragon.getMaxHealth());
									}
									else {
										dragon.setHealth(dragon.getHealth() + 100);
									}
								}
								else if (eventD.getDamager().getType() == EntityType.ELDER_GUARDIAN) {
									ElderGuardian elder = (ElderGuardian) eventD.getDamager();
									if (elder.getMaxHealth() - elder.getHealth() < 75) {
										elder.setHealth(elder.getMaxHealth());
									}
									else {
										elder.setHealth(elder.getHealth() + 75);
									}
								}
							}
							else if (rgen.nextInt(100) + 1 <= 20 && (eventD.getDamager().getType() == EntityType.WITHER || eventD.getDamager().getType() == EntityType.WITHER_SKULL)) {
								if (eventD.getDamager().getType() == EntityType.WITHER) {
									Wither wither = (Wither) eventD.getDamager();
									if (wither.getMaxHealth() - wither.getHealth() < 75) {
										wither.setHealth(wither.getMaxHealth());
									}
									else {
										wither.setHealth(wither.getHealth() + 75);
									}
								}
								else if (eventD.getDamager().getType() == EntityType.WITHER_SKULL) {
									WitherSkull skull = (WitherSkull) eventD.getDamager();
									Wither wither = (Wither) skull.getShooter();
									if (wither.getMaxHealth() - wither.getHealth() < 75) {
										wither.setHealth(wither.getMaxHealth());
									}
									else {
										wither.setHealth(wither.getHealth() + 75);
									}
								}
							}
							else if (rgen.nextInt(100) + 1 >= 30 && (eventD.getDamager().getType() == EntityType.WARDEN)) {
								Warden warden = (Warden) eventD.getDamager();
								if (warden.getMaxHealth() - warden.getHealth() < 100) {
									warden.setHealth(warden.getMaxHealth());
								}
								else {
									warden.setHealth(warden.getHealth() + 100);
								}
							}
						}
					}
				}
				
			}
			// This is for bossEffects, to make sure server restarts don't mess up the list too much
			else if ((eventD.getEntityType() == EntityType.ENDER_DRAGON || eventD.getEntityType() == EntityType.WITHER) && CmdManager.bossEffects) {
				if (!bossEffectLoopOn) {
	    			bossEffectLoop(bosses);
	    		}
				// tempVar is used to ensure the boss isn't already on the list before adding it to the list
				boolean tempVar = true;
				for(int x = 0; x < bosses.size(); x++) {
					if (bosses.get(x) == eventD.getEntity()) {
						tempVar = false;
						break;
					}
				}
				if (tempVar) {
					bosses.add(eventD.getEntity());
				}
			}
		}
	}
	
    @EventHandler
    public void eventV(PlayerMoveEvent eventV) {
        if (eventV.getFrom().getZ() != eventV.getTo().getZ() && eventV.getFrom().getX() != eventV.getTo().getX()) {
        	if (CmdManager.enable && (CmdManager.mobDash || CmdManager.neutralAttack)) {
        		if (y < 20) {
        			y++;
        		}
        		else {
        			// Making mobs have a 3% chance of dashing every second of movement
        			if (CmdManager.mobDash) {
        				Vector v = new Vector(4,6,4);
            			y = 0;
            			player = eventV.getPlayer();
            			List<Entity> mobs = player.getNearbyEntities(25, 6, 25);
            			for (int x = 0; x < mobs.size(); x++) {
            				for (int z = 0; z < mobList.length; z++) {
            					if (mobs.get(x).getType() == mobList[z]) {
            						if ((rgen.nextInt(100) + 1) <= 3) {
                    					mobs.get(x).setVelocity((player.getLocation().subtract(mobs.get(x).getLocation())).toVector().divide(v).add(new Vector(0,0.35,0)));
                    					particleCircle (5, mobs.get(x).getLocation(), mobs.get(x));
                    					player.playSound(player, Sound.BLOCK_WOOL_STEP, SoundCategory.PLAYERS, 1.2f, 0.1f);
                    				}
            					}
            				}
            				
            			}
        			}
        			// Making neutral enemies attack players automatically
        			if (CmdManager.neutralAttack) {
        				player = eventV.getPlayer();
        				if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
        					List<Entity> mobs = player.getNearbyEntities(20, 6, 20);
            				for (int x = 0; x < mobs.size(); x++) {
                				for (int z = 0; z < neutral.length; z++) {
                					if (mobs.get(x).getType() == neutral[z]) {
                						this.mob = (Mob) mobs.get(x);
                						mob.setTarget(player);
                					}
                				}
                			}
        				}
        				
        			}
        		}
    		}
        }
    }
    
    // Giving mobs random effects, and making them spawn in groups
    @SuppressWarnings("deprecation")
	@EventHandler
    public void eventS(EntitySpawnEvent eventS) {
		// Giving mobs random effects
    	if (CmdManager.enable && CmdManager.mobStrength) {
    		for (int x = 0; x < mobList.length; x++) {
    			if ((eventS.getEntityType() == mobList[x]) && (rgen.nextInt(100) <= 40) && (eventS.getEntityType() != EntityType.CREEPER)) {
    				mob = (Mob) eventS.getEntity();
    				mob.addPotionEffect(new PotionEffect(positive[rgen.nextInt(positive.length)], Integer.MAX_VALUE , 1), false);
    			}
    			if ((rgen.nextInt(100) <= 40) && (eventS.getEntityType() == EntityType.CREEPER)) {
    				mob = (Mob) eventS.getEntity();
    				mob.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300 , 1), false);
    			}
    		}
    	}
    	// Making mobs spawn in groups
    	if (CmdManager.enable && CmdManager.mobGroup) {
    		double groupMult = CmdManager.mobGroupMulti;
    		groupMult *= 100;
    		groupMult -= 100;
			for (int z = 0; z < mobList2.length; z++) {
				if ((eventS.getEntityType() == mobList2[z]) && (rgen.nextInt(100) + 1) <= groupMult) {
    				mob = (Mob) eventS.getEntity();
    				// Using persistent data containers to ensure mobs can't loop group spawning
    				PersistentDataContainer data = mob.getPersistentDataContainer();
    				try {
    					if (data.get(new NamespacedKey(Main.main, "isGrouped"), PersistentDataType.INTEGER) != 1) {
	    					mob = (Mob) mob.getWorld().spawnEntity(mob.getLocation(), mob.getType());
    	    				mob.getPersistentDataContainer().set(new NamespacedKey(Main.main, "isGrouped"), PersistentDataType.INTEGER, 1);
	    				}
    				}
    				catch (Exception e) {
    					mob.getPersistentDataContainer().set(new NamespacedKey(Main.main, "isGrouped"), PersistentDataType.INTEGER, 0);
    					Mob mob2 = (Mob) mob.getWorld().spawnEntity(mob.getLocation(), mob.getType());
    					data = mob2.getPersistentDataContainer();
	    				mob2.getPersistentDataContainer().set(new NamespacedKey(Main.main, "isGrouped"), PersistentDataType.INTEGER, 1);
    				}
    			}
    		}
    	}
    	// Making bosses get random effects
    	if (CmdManager.enable && CmdManager.bossEffects) {
    		if (eventS.getEntityType() == EntityType.ENDER_DRAGON || eventS.getEntityType() == EntityType.WITHER){
    			if (!(eventS.getEntityType() == EntityType.WITHER_SKULL)) {
    				bosses.add(eventS.getEntity());
        			if (!bossEffectLoopOn) {
        				bossEffectLoop(bosses);
        			}
    			}
    		}
    	}
    }
    
    @EventHandler
    public void eventJ(PlayerJoinEvent eventJ) {
    	if (CmdManager.enable && CmdManager.bossEffects) {
    		if (!bossEffectLoopOn) {
    			bossEffectLoop(bosses);
    		}
    	}
    }
        
    public void particleCircle(int count, Location loc, Entity entity) {
    	DustOptions dustOptions = new DustOptions(Color.fromRGB(255, 255, 255), 1.0F);
    	Double degree = 0.0;
    	
    	for (int x = 0; x < count; x++) {
    		degree += 360/count;
        	Vector particles = new Vector(0,0,0);
        	particles.setX(Math.sin(degree * Math.PI/180) / 3);
    		particles.setZ(Math.cos(degree * Math.PI/180) / 3);
    		entity.getWorld().spawnParticle(Particle.DUST, entity.getLocation().add(particles), 50, dustOptions);
    	}
    }
    
    public void bossEffectLoop(List<Entity> bosses) {
    	bossEffectLoopOn = true;
		new BukkitRunnable() {
    		@SuppressWarnings("deprecation")
			@Override
    		public void run() {
    			if (CmdManager.enable && CmdManager.bossEffects) {
	    			for(int x = 0; x < bosses.size(); x++) {
	    				if (bosses.get(x).isDead()) {
	    					bosses.remove(x);
	    				}
	    				// 5% chance per second
	    				else if (rgen.nextInt(100) + 1 <= 5) {
	    					Mob boss = (Mob) bosses.get(x);
	    					if (boss.getType() == EntityType.WITHER) {
	    						PotionEffectType temp = positiveWither[(rgen.nextInt(positiveWither.length))];
	    						if (temp == PotionEffectType.ABSORPTION) {
	    							boss.addPotionEffect(new PotionEffect(temp, 6000, 2), true);
	    						}
	    						else {
	    							boss.addPotionEffect(new PotionEffect(temp, 600, 1), true);
	    						}
	    					}
	    					else if (boss.getType() == EntityType.ENDER_DRAGON) {
	    						PotionEffectType temp = positiveDragon[(rgen.nextInt(positiveWither.length))];
	    						if (temp == PotionEffectType.ABSORPTION) {
	    							boss.addPotionEffect(new PotionEffect(temp, 6000, 2), true);
	    						}
	    						else {
		    						boss.addPotionEffect(new PotionEffect(temp, 600, 1), true);

	    						}
    						}
	    				}
	    			}
    			}
    			else {
    				bossEffectLoopOn = false;
    				this.cancel();
    			}
    		}
    	}.runTaskTimer(Main.main, 0, 20);
    	
    }
}
