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

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandGive;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumStatus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.common.core.helper.MiscHelper;
import vazkii.recubed.common.lib.LibCategories;

public final class GeneralEventHandler {

	// ARROWS SHOT
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onEntityTakeDamage(ArrowLooseEvent event) {
        float f = event.charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;

        if(ReCubedAPI.validatePlayer(event.entityPlayer))
			ReCubedAPI.addValueToCategory(LibCategories.ARROWS_SHOT, event.entityPlayer.username, f >= 1F ? "recubed.misc.critical_shot" : "recubed.misc.shot", 1);
	}

	// BLOCKS BROKEN
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onBlockBroken(BlockEvent.BreakEvent event) {
        if(ReCubedAPI.validatePlayer(event.getPlayer()))
			ReCubedAPI.addValueToCategory(LibCategories.BLOCKS_BROKEN, event.getPlayer().username, Item.itemsList[event.block.blockID].getUnlocalizedName(new ItemStack(event.block.blockID, 1, event.blockMetadata)) + ".name", 1);
	}

	
	// COWS MILKED
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onEntityInteracted(EntityInteractEvent event) {
        if(ReCubedAPI.validatePlayer(event.entityPlayer)) {
        	ItemStack currentItem = event.entityPlayer.getCurrentEquippedItem();
        	if(currentItem != null && currentItem.itemID == Item.bucketEmpty.itemID && event.target instanceof EntityCow)
        		ReCubedAPI.addValueToCategory(LibCategories.COWS_MILKED, event.entityPlayer.username, "item.milk.name", 1);

        	if(currentItem != null && currentItem.itemID == Item.shears.itemID && event.target instanceof IShearable && ((IShearable) event.target).isShearable(currentItem, event.target.worldObj, (int) event.target.posX, (int) event.target.posY, (int) event.target.posZ))
        		ReCubedAPI.addValueToCategory(LibCategories.ANIMALS_SHEARED, event.entityPlayer.username, MiscHelper.getEntityString(event.target), 1);

        	if(currentItem != null && currentItem.getItem() instanceof ItemDye && event.target instanceof EntitySheep && !((EntitySheep) event.target).getSheared() && 15 - ((EntitySheep) event.target).getFleeceColor() != currentItem.getItemDamage())
        		ReCubedAPI.addValueToCategory(LibCategories.SHEEP_DYED, event.entityPlayer.username, currentItem.getUnlocalizedName() + ".name", 1);

        }
	}

	// DAMAGE DEALT
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onEntityTakeDamage(LivingHurtEvent event) {
		if(event.source.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			String name = MiscHelper.getEntityString(event.entity);

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.DAMAGE_DEALT, player.username, name, (int) Math.min(event.entityLiving.getHealth(), event.ammount));
		}
	}

	// DAMAGE TAKEN
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onPlayerTakeDamage(LivingHurtEvent event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			String name = "recubed.damage." + event.source.getDamageType();
			if(event.source.getEntity() != null)
				name = MiscHelper.getEntityString(event.source.getEntity());
			if(event.source.getEntity() instanceof EntityPlayer)
				name = ((EntityPlayer) event.entity).username;

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.DAMAGE_TAKEN, player.username, name, (int) Math.min(player.getHealth(), event.ammount));
		}
	}

	// ITEMS BROKEN
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onItemBroken(PlayerDestroyItemEvent event) {
		if(ReCubedAPI.validatePlayer(event.entityPlayer) && event.original.isItemStackDamageable() && event.original.getItemDamage() == event.original.getMaxDamage())
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

	// MESSAGES SENT + ITEMS SPAWNED
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onMessageReceived(ServerChatEvent event) {
		if(ReCubedAPI.validatePlayer(event.player))
			ReCubedAPI.addValueToCategory(LibCategories.MESSAGES_SENT, event.username, "recubed.misc.chat", 1);
	}

	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onMessageReceived(CommandEvent event) {
		if(event.sender instanceof EntityPlayer && ReCubedAPI.validatePlayer((EntityPlayer) event.sender)) {
			ReCubedAPI.addValueToCategory(LibCategories.MESSAGES_SENT, event.sender.getCommandSenderName(), "/" + event.command.getCommandName(), 1);

			if(event.command instanceof CommandGive) {
				int i = CommandBase.parseIntWithMin(event.sender, event.parameters[1], 1);
	            int j = 1;
	            int k = 0;

	            if (Item.itemsList[i] == null)
	                return;

                if (event.parameters.length >= 3)
                    j = CommandBase.parseIntBounded(event.sender, event.parameters[2], 1, 64);

                if (event.parameters.length >= 4)
                    k = CommandBase.parseInt(event.sender, event.parameters[3]);

                ItemStack stack = new ItemStack(i, j, k);

    			ReCubedAPI.addValueToCategory(LibCategories.ITEMS_SPAWNED, event.sender.getCommandSenderName(), stack.getUnlocalizedName() + ".name", 1);
			}
		}
	}

	// MOBS AGGROED
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onMobGetTarget(LivingSetAttackTargetEvent event) {
		if(event.target instanceof EntityPlayer && EntityList.getEntityString(event.entity) != null) {
			EntityPlayer player = (EntityPlayer) event.target;
			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.MOBS_AGGROED, player.username, MiscHelper.getEntityString(event.entity), 1);
		}
	}

	// MOBS KILLED + BOSS KILLS
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onEntityDie(LivingDeathEvent event) {
		if(event.source.getEntity() instanceof EntityPlayer && !(event.entity instanceof EntityPlayer)) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			String name = MiscHelper.getEntityString(event.entity);

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(event.entity instanceof IBossDisplayData ? LibCategories.BOSS_KILLS : LibCategories.MOBS_KILLED, player.username, name, 1);
		}
	}


	// TIMES DIED + PLAYER KILLS
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onPlayerDie(LivingDeathEvent event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			String name = "recubed.damage." + event.source.getDamageType();
			if(event.source.getEntity() != null)
				name = MiscHelper.getEntityString(event.source.getEntity());
			if(event.source.getEntity() instanceof EntityPlayer) {
				name = ((EntityPlayer) event.source.getEntity()).username;
				if(ReCubedAPI.validatePlayer((EntityPlayer) event.entity));
					ReCubedAPI.addValueToCategory(LibCategories.PLAYER_KILLS, name, player.username, 1);
			}

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.TIMES_DIED, player.username, name, 1);
		}
	}

	// SNOWBALLS THROWN + ENDER PEARLS THROWN + ENDER EYES USED
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(ReCubedAPI.validatePlayer(event.entityPlayer)) {
			ItemStack stack = event.entityPlayer.getCurrentEquippedItem();

			if(stack != null) {
				if(stack.itemID == Item.snowball.itemID)
					ReCubedAPI.addValueToCategory(LibCategories.SNOWBALLS_THROWN, event.entityPlayer.username, "item.snowball.name", 1);

				if(!event.entityPlayer.capabilities.isCreativeMode && stack.itemID == Item.enderPearl.itemID)
					ReCubedAPI.addValueToCategory(LibCategories.ENDER_PEARLS_THROWN, event.entityPlayer.username, "item.enderPearl.name", 1);

				if(stack.getItem() instanceof ItemRecord && event.action == Action.RIGHT_CLICK_BLOCK && event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == Block.jukebox.blockID)
					ReCubedAPI.addValueToCategory(LibCategories.DISCS_PLAYED, event.entityPlayer.username, ((ItemRecord) stack.getItem()).recordName, 1);
			
				if(event.entityPlayer.dimension == 0 && stack.itemID == Item.eyeOfEnder.itemID)
					ReCubedAPI.addValueToCategory(LibCategories.ENDER_EYES_USED, event.entityPlayer.username, "item.eyeOfEnder.name", 1);
			}
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

					if (Math.abs(event.entityPlayer.posX - event.x) > 3D || Math.abs(event.entityPlayer.posY - event.y) > 3D || Math.abs(event.entityPlayer.posZ - event.z) > 3D) {
						status = EnumStatus.TOO_FAR_AWAY;
						break findStatus;
					}

					double d0 = 8.0D;
					double d1 = 5.0D;
					List list = event.entityPlayer.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getAABBPool().getAABB(event.x - d0, event.y - d1, event.z - d0, event.x + d0, event.y + d1, event.z + d0));

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
