/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 3:23:32 PM (GMT)]
 */
package vazkii.recubed.common.core.handler;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.common.core.helper.MiscHelper;
import vazkii.recubed.common.lib.LibCategories;

public final class GeneralEventHandler {

	// DAMAGE DEALT
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onEntityTakeDamage(LivingHurtEvent event) {
		if(event.source.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			String name = MiscHelper.getEntityString(event.entity);
			
			ReCubedAPI.addValueToCategory(LibCategories.DAMAGE_DEALT, player.username, name, (int) event.ammount);
		}
	}
	
	// DAMAGE TAKEN
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onPlayerTakeDamage(LivingHurtEvent event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			String name = "recubed.misc.envDamage";
			if(event.source.getEntity() != null)
				name = MiscHelper.getEntityString(event.source.getEntity());
			if(event.source.getEntity() instanceof EntityPlayer)
				name = ((EntityPlayer) event.entity).username;
			
			ReCubedAPI.addValueToCategory(LibCategories.DAMAGE_TAKEN, player.username, name, (int) event.ammount);
		}
	}
	
	// MOBS KILLED
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onEntityDie(LivingDeathEvent event) {
		if(event.source.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			String name = MiscHelper.getEntityString(event.entity);
			
			ReCubedAPI.addValueToCategory(LibCategories.MOBS_KILLED, player.username, name, 1);
		}
	}
	
	// TIMES DIED
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onPlayerDie(LivingDeathEvent event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			String name = "recubed.misc.envDamage";
			if(event.source.getEntity() != null)
				name = MiscHelper.getEntityString(event.source.getEntity());
			if(event.source.getEntity() instanceof EntityPlayer)
				name = ((EntityPlayer) event.entity).username;
			
			ReCubedAPI.addValueToCategory(LibCategories.TIMES_DIED, player.username, name, 1);
		}
	}
	
}
