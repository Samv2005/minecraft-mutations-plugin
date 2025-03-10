# minecraft-mutations-plugin
A Minecraft plugin which adds mutations, meant for Java 1.21.1, coded in Spigot.
This file contains a more detailed description of each mutation, along with their respective commands

/mutations
  This command has the following options:
    enable/disable: Turns the mutations plugin on/off, if the plugin is disabled, all mutations will no longer have an effect, even if they remain enabled.
    enableall/disableall: Turns every mutation on/off, does not effect whether the plugin itself is enabled or disabled. If disableAll is run, scaling mutations will be reset to their original values.
    list: Lists all mutations, along with whether they are enabled/disabled (will display as true/false).

The following mutations only have an option to enable or disable (the commands are the names of the mutations, all lower case):
  blazeCurse: Blaze fireballs will summon a random hostile or neutral mob, with an increased chance of spawning creepers and blazes.
  bossEffects: Bosses have a 5% chance per second to get a random positive effect for 30 seconds. If a boss gets absorption, it will instead remain for 300 seconds.
  bossHeal: Bosses can heal when they damage a player. Chances vary for each boss, with a 50% chance for the Ender Dragon and Elder Guardian to heal, a 20% chance for the wither to heal, and a 30% chance for the Warden to heal.
  creeperDupe: Creepers will launch 2 more creepers when exploding. If a creeper is hit by a creeper, it becomes charged, with charged creepers launching 3 creepers upon exploding. If a charged creeper is hit by a creeper, it launches 4 creepers and is killed.
  creeperFuse: Creeper fuse lengths range from 20 ticks to 35 ticks, instead of always being 30 ticks.
  enderThief: Endermen will grab blocks and teleport more often, occasionally teleporting when hit by players as well (this one is a bit of a work in progress, as it has some issues).
  explodeSummon: Non-creeper explosions will summon various amounts of creepers, depending on what's exploding. If multiple are summoned, they are launched from the explosion. Creepers spawning due to end crystal explosions are given temporary resistance to survive the     fall from the pillars in the end.
  explosiveProjectiles: Projectiles (excluding blaze fireballs) have a 5% chance to explode when landing.
  mobCrit: Most mobs have a 10% chance to deal magic damage, piercing armor
  mobDash: Most mobs have a 3% chance per second of player movement to dash towards the player
  mobEffects: Most mobs have a 20% chance to inflict a random negative effect on hit.
  mobStrength: Most mobs have a 40% chance to spawn with a permanent buff, creepers get weakness due to leaving behind lingering effects when exploding.
  neutralAttack: Neutral mobs near the player will become hostile.
  pearlCurse: Ender pearls have a 20% chance to teleport the player randomly in a circle, with a radius determined by the player's distance to the ender pearl's landing location. The player's y-value will remain unchanged if this triggers.
  projectileSummon: Most projectiles have a 10% chance to summon a random hostile mob.
  strongStorm: During thunderstorms, lightning has a 5% chance to strike either near the player, or on a nearby transformable mob. Lightning also deals magic damage to the player. Lightning storms will replace rainstorms. Players can no longer sleep during                   thunderstorms, even at night.

Other mutations:
  explosiveScale: Increases the power of most explosions, including explosions caused by other mutations.
    enable/disable: Turns the mutation on/off.
    increase/decrease: Increases or decreases explosive power by 25%, compounding.
    set: Sets the explosive multiplier.
  mobGroup: Adds a chance for most mobs to spawn extra mobs, potentially recusively.
    enable/disable: Turns the mutation on/off.
    increase/decrease: Increases or decreases the chance for a mob to spawn another mob by (100% - ((100% - current %) * 0.8).
    set: Sets the chance for mobs to spawn more mobs. At 1.0, the chance is 0%. At 2.0, the chance is 100%, and mobs spawn recursively until an infinite loop error stops it (after about 100 mobs), hence the logarithmic increasing/decreasing setup.
