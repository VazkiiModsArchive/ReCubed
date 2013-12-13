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

import java.util.List;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.common.core.helper.MiscHelper;
import vazkii.recubed.common.lib.LibCategories;

public final class GeneralEventHandler {

	// ARROWS SHOT
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onEntityTakeDamage(ArrowLooseEvent event) {
        float f = (float) event.charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        
        if(ReCubedAPI.validatePlayer(event.entityPlayer))
			ReCubedAPI.addValueToCategory(LibCategories.ARROWS_SHOT, event.entityPlayer.username, f >= 1F ? "recubed.misc.critical_shot" : "recubed.misc.shot", (int) 1);
	}
	
	// DAMAGE DEALT
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onEntityTakeDamage(LivingHurtEvent event) {
		if(event.source.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			String name = MiscHelper.getEntityString(event.entity);

			if(ReCubedAPI.validatePlayer(player))
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

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.DAMAGE_TAKEN, player.username, name, (int) event.ammount);
		}
	}
	
	// ITEMS BROKEN
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onItemBroken(PlayerDestroyItemEvent event) {
		if(ReCubedAPI.validatePlayer(event.entityPlayer))
			ReCubedAPI.addValueToCategory(LibCategories.ITEMS_BROKEN, event.entityPlayer.username, event.original.getUnlocalizedName() + ".name", 1);
	}
	
	// ITEMS DROPPED
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onPlayerTossItem(ItemTossEvent event) {
		ItemStack stack = event.entityItem.getEntityItem();
		if(ReCubedAPI.validatePlayer(event.player))
			ReCubedAPI.addValueToCategory(LibCategories.ITEMS_DROPPED, event.player.username, stack.getUnlocalizedName() + ".name", stack.stackSize);

	}

	// ITEMS PICKED UP
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onItemPickedUp(EntityItemPickupEvent event) {
		ItemStack stack = event.item.getEntityItem();
		if(ReCubedAPI.validatePlayer(event.entityPlayer))
			ReCubedAPI.addValueToCategory(LibCategories.ITEMS_PICKED_UP, event.entityPlayer.username, stack.getUnlocalizedName() + ".name", stack.stackSize);
	}

	// JUMPS DONE
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onPlayerJump(LivingJumpEvent event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			System.out.println("jump");

			if(ReCubedAPI.validatePlayer(player)) {
				ReCubedAPI.addValueToCategory(LibCategories.JUMPS_DONE, player.username, player.isSprinting() ? "recubed.misc.sprint_jump" : "recubed.misc.jump", 1);
			}
		}
	}

	// MESSAGES SENT
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onMessageReceived(ServerChatEvent event) {
		if(ReCubedAPI.validatePlayer(event.player))
			ReCubedAPI.addValueToCategory(LibCategories.MESSAGES_SENT, event.username, "recubed.misc.chat", 1);
	}

	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onMessageReceived(CommandEvent event) {
		if(event.sender instanceof EntityPlayer && ReCubedAPI.validatePlayer((EntityPlayer) event.sender))
			ReCubedAPI.addValueToCategory(LibCategories.MESSAGES_SENT, event.sender.getCommandSenderName(), "recubed.misc.command", 1);
	}
	
	// MOBS AGGROED
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onMobGetTarget(LivingSetAttackTargetEvent event) {
		if(event.target instanceof EntityPlayer && EntityList.getEntityString(event.entity) != null) {
			EntityPlayer player = (EntityPlayer) event.target;
			if(ReCubedAPI.validatePlayer(player));
				ReCubedAPI.addValueToCategory(LibCategories.MOBS_AGGROED, player.username, MiscHelper.getEntityString(event.entity), 1);
		}
	}

	// MOBS KILLED
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onEntityDie(LivingDeathEvent event) {
		if(event.source.getEntity() instanceof EntityPlayer && !(event.entity instanceof EntityPlayer)) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			String name = MiscHelper.getEntityString(event.entity);

			if(ReCubedAPI.validatePlayer(player));
				ReCubedAPI.addValueToCategory(LibCategories.MOBS_KILLED, player.username, name, 1);
		}
	}

	// TIMES DIED + PLAYER KILLS
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onPlayerDie(LivingDeathEvent event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			String name = "recubed.misc.envDamage";
			if(event.source.getEntity() != null)
				name = MiscHelper.getEntityString(event.source.getEntity());
			if(event.source.getEntity() instanceof EntityPlayer) {
				name = ((EntityPlayer) event.source.getEntity()).username;
				if(ReCubedAPI.validatePlayer((EntityPlayer) event.entity));
					ReCubedAPI.addValueToCategory(LibCategories.PLAYER_KILLS, name, player.username, 1);
			}

			if(ReCubedAPI.validatePlayer(player));
				ReCubedAPI.addValueToCategory(LibCategories.TIMES_DIED, player.username, name, 1);
		}
	}

	// TIMES SLEPT
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onPlayerSleep(PlayerSleepInBedEvent event) {
		if(!ReCubedAPI.validatePlayer(event.entityPlayer))
			return;

		
		EnumStatus status = event.result;
		if(status == null) {
			findStatus : {
				if(!event.entityPlayer.worldObj.isRemote) {
					if (event.entityPlayer.isPlayerSleeping() || !event.entityPlayer.isEntityAlive()) {
						status = EnumStatus.OTHER_PROBLEM;
						break findStatus;
					}
	
					if (!event.entityPlayer.worldObj.provider.isSurfaceWorld()) {
						status = EnumStatus.NOT_POSSIBLE_HERE;
						break findStatus;
					}
	
					if (event.entityPlayer.worldObj.isDaytime()) {
						status = EnumStatus.NOT_POSSIBLE_NOW;
						break findStatus;
					}
	
					if (Math.abs(event.entityPlayer.posX - (double) event.x) > 3D || Math.abs(event.entityPlayer.posY - (double) event.y) > 3D || Math.abs(event.entityPlayer.posZ - (double) event.z) > 3D) {
						status = EnumStatus.TOO_FAR_AWAY;
						break findStatus;
					}
	
					double d0 = 8.0D;
					double d1 = 5.0D;
					List list = event.entityPlayer.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getAABBPool().getAABB((double) event.x - d0, (double) event.y - d1, (double) event.z - d0, (double) event.x + d0, (double) event.y + d1, (double) event.z + d0));
	
					if (!list.isEmpty()) {
						status = EnumStatus.NOT_SAFE;
						break findStatus; 
					}
					status = EnumStatus.OK;
				}
			}
		}

		if(status == EnumStatus.OK)
			ReCubedAPI.addValueToCategory(LibCategories.TIMES_SLEPT, event.entityPlayer.username, "recubed.misc.sleep", 1);
	}

}
