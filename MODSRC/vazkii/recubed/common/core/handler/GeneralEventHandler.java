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

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandGive;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.EnumStatus;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.CommandEvent;
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
import vazkii.recubed.common.lib.LibObfuscation;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;

public final class GeneralEventHandler {

	// ARROWS SHOT
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityTakeDamage(ArrowLooseEvent event) {
		float f = event.charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if(ReCubedAPI.validatePlayer(event.entityPlayer))
			ReCubedAPI.addValueToCategory(LibCategories.ARROWS_SHOT, event.entityPlayer.getGameProfile().getName(), f >= 1F ? "recubed.misc.critical_shot" : "recubed.misc.shot", 1);
	}

	// BLOCKS BROKEN
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onBlockBroken(BlockEvent.BreakEvent event) {
		if(ReCubedAPI.validatePlayer(event.getPlayer()) && event.block != null && Item.getItemFromBlock(event.block) != null)
			ReCubedAPI.addValueToCategory(LibCategories.BLOCKS_BROKEN, event.getPlayer().getGameProfile().getName(), Item.getItemFromBlock(event.block).getUnlocalizedName(new ItemStack(event.block, 1, event.blockMetadata)) + ".name", 1);
	}


	// COWS MILKED + ANIMALS SHEARED + SHEEP DYED
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityInteracted(EntityInteractEvent event) {
		if(ReCubedAPI.validatePlayer(event.entityPlayer)) {
			ItemStack currentItem = event.entityPlayer.getCurrentEquippedItem();
			if(currentItem != null && currentItem.getItem() == Items.bucket && event.target instanceof EntityCow)
				ReCubedAPI.addValueToCategory(LibCategories.COWS_MILKED, event.entityPlayer.getGameProfile().getName(), "item.milk.name", 1);

			if(currentItem != null && currentItem.getItem() == Items.shears && event.target instanceof IShearable && ((IShearable) event.target).isShearable(currentItem, event.target.worldObj, (int) event.target.posX, (int) event.target.posY, (int) event.target.posZ))
				ReCubedAPI.addValueToCategory(LibCategories.ANIMALS_SHEARED, event.entityPlayer.getGameProfile().getName(), MiscHelper.getEntityString(event.target), 1);

			if(currentItem != null && currentItem.getItem() instanceof ItemDye && event.target instanceof EntitySheep && !((EntitySheep) event.target).getSheared() && 15 - ((EntitySheep) event.target).getFleeceColor() != currentItem.getItemDamage())
				ReCubedAPI.addValueToCategory(LibCategories.SHEEP_DYED, event.entityPlayer.getGameProfile().getName(), currentItem.getUnlocalizedName() + ".name", 1);
		}
	}

	// DAMAGE DEALT
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityTakeDamage(LivingHurtEvent event) {
		if(event.source.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			String name = MiscHelper.getEntityString(event.entity);

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.DAMAGE_DEALT, player.getGameProfile().getName(), name, (int) Math.min(event.entityLiving.getHealth(), event.ammount));
		}
	}

	// DAMAGE TAKEN
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerTakeDamage(LivingHurtEvent event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			String name = "recubed.damage." + event.source.getDamageType();
			if(event.source.getEntity() != null)
				name = MiscHelper.getEntityString(event.source.getEntity());

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.DAMAGE_TAKEN, player.getGameProfile().getName(), name, (int) Math.min(player.getHealth(), event.ammount));
		}
	}

	// ITEMS BROKEN
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onItemBroken(PlayerDestroyItemEvent event) {
		if(ReCubedAPI.validatePlayer(event.entityPlayer) && event.original.isItemStackDamageable() && event.original.getItemDamage() == event.original.getMaxDamage())
			ReCubedAPI.addValueToCategory(LibCategories.ITEMS_BROKEN, event.entityPlayer.getGameProfile().getName(), event.original.getUnlocalizedName() + ".name", 1);
	}

	// ITEMS DROPPED
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerTossItem(ItemTossEvent event) {
		ItemStack stack = event.entityItem.getEntityItem();
		if(ReCubedAPI.validatePlayer(event.player))
			ReCubedAPI.addValueToCategory(LibCategories.ITEMS_DROPPED, event.player.getGameProfile().getName(), stack.getUnlocalizedName() + ".name", stack.stackSize);

	}

	// ITEMS PICKED UP
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onItemPickedUp(EntityItemPickupEvent event) {
		ItemStack stack = event.item.getEntityItem();
		if(ReCubedAPI.validatePlayer(event.entityPlayer))
			ReCubedAPI.addValueToCategory(LibCategories.ITEMS_PICKED_UP, event.entityPlayer.getGameProfile().getName(), stack.getUnlocalizedName() + ".name", stack.stackSize);
	}

	// MESSAGES SENT + ITEMS SPAWNED
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onMessageReceived(ServerChatEvent event) {
		if(ReCubedAPI.validatePlayer(event.player))
			ReCubedAPI.addValueToCategory(LibCategories.MESSAGES_SENT, event.username, "recubed.misc.chat", 1);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onMessageReceived(CommandEvent event) {
		if(event.sender instanceof EntityPlayer && ReCubedAPI.validatePlayer((EntityPlayer) event.sender)) {
			ReCubedAPI.addValueToCategory(LibCategories.MESSAGES_SENT, event.sender.getCommandSenderName(), "/" + event.command.getCommandName(), 1);

			if(event.command instanceof CommandGive) {
				String name = event.parameters[1];
				Item item = CommandBase.getItemByText(event.sender, name);
				int j = 1;
				int k = 0;

				if(item == null)
					return;

				if(event.parameters.length >= 3)
					j = CommandBase.parseIntBounded(event.sender, event.parameters[2], 1, 64);

				if(event.parameters.length >= 4)
					k = CommandBase.parseInt(event.sender, event.parameters[3]);

				ItemStack stack = new ItemStack(item, j, k);

				ReCubedAPI.addValueToCategory(LibCategories.ITEMS_SPAWNED, event.sender.getCommandSenderName(), stack.getUnlocalizedName() + ".name", 1);
			}
		}
	}

	// MOBS AGGROED
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onMobGetTarget(LivingSetAttackTargetEvent event) {
		if(event.target instanceof EntityPlayer && EntityList.getEntityString(event.entity) != null) {
			EntityPlayer player = (EntityPlayer) event.target;
			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.MOBS_AGGROED, player.getGameProfile().getName(), MiscHelper.getEntityString(event.entity), 1);
		}
	}

	// MOBS KILLED + BOSS KILLS
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityDie(LivingDeathEvent event) {
		if(event.source.getEntity() instanceof EntityPlayer && !(event.entity instanceof EntityPlayer)) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			String name = MiscHelper.getEntityString(event.entity);

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(event.entity instanceof IBossDisplayData ? LibCategories.BOSS_KILLS : LibCategories.MOBS_KILLED, player.getGameProfile().getName(), name, 1);
		}
	}


	// TIMES DIED + PLAYER KILLS
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerDie(LivingDeathEvent event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			String name = "recubed.damage." + event.source.getDamageType();
			if(event.source.getEntity() != null)
				name = MiscHelper.getEntityString(event.source.getEntity());
			if(event.source.getEntity() instanceof EntityPlayer) {
				name = ((EntityPlayer) event.source.getEntity()).getGameProfile().getName();
				if(ReCubedAPI.validatePlayer((EntityPlayer) event.source.getEntity()))
					ReCubedAPI.addValueToCategory(LibCategories.PLAYER_KILLS, name, player.getGameProfile().getName(), 1);
			}

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.TIMES_DIED, player.getGameProfile().getName(), name, 1);
		}
	}

	// SNOWBALLS THROWN + ENDER PEARLS THROWN + ENDER EYES USED + TIMES FISHED + POTIONS THROWN
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(ReCubedAPI.validatePlayer(event.entityPlayer)) {
			ItemStack stack = event.entityPlayer.getCurrentEquippedItem();

			if(stack != null) {
				if(stack.getItem() == Items.snowball)
					ReCubedAPI.addValueToCategory(LibCategories.SNOWBALLS_THROWN, event.entityPlayer.getGameProfile().getName(), "item.snowball.name", 1);

				if(!event.entityPlayer.capabilities.isCreativeMode && stack.getItem() == Items.ender_pearl)
					ReCubedAPI.addValueToCategory(LibCategories.ENDER_PEARLS_THROWN, event.entityPlayer.getGameProfile().getName(), "item.enderPearl.name", 1);

				if(stack.getItem() instanceof ItemRecord && event.action == Action.RIGHT_CLICK_BLOCK && event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) == Blocks.jukebox)
					ReCubedAPI.addValueToCategory(LibCategories.DISCS_PLAYED, event.entityPlayer.getGameProfile().getName(), ((ItemRecord) stack.getItem()).recordName, 1);

				if(event.entityPlayer.dimension == 0 && stack.getItem() == Items.ender_eye)
					ReCubedAPI.addValueToCategory(LibCategories.ENDER_EYES_USED, event.entityPlayer.getGameProfile().getName(), "item.eyeOfEnder.name", 1);

				if(stack.getItem() == Items.fishing_rod && event.entityPlayer.fishEntity == null)
					ReCubedAPI.addValueToCategory(LibCategories.TIMES_FISHED, event.entityPlayer.getGameProfile().getName(), "recubed.misc.hook_casted", 1);

				if(stack.getItem() == Items.potionitem) {
					ItemPotion potion = (ItemPotion) stack.getItem();
					if(ItemPotion.isSplash(stack.getItemDamage())) {
						List<PotionEffect> effects = potion.getEffects(stack);
						for(PotionEffect effect : effects)
							ReCubedAPI.addValueToCategory(LibCategories.POTIONS_THROWN, event.entityPlayer.getGameProfile().getName(), Potion.potionTypes[effect.getPotionID()].getName(), 1);
					}
				}
			}
		}
	}

	// TIMES SLEPT
	@SubscribeEvent(priority = EventPriority.LOWEST)
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
				List list = event.entityPlayer.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(event.x - d0, event.y - d1, event.z - d0, event.x + d0, event.y + d1, event.z + d0));

				if (!list.isEmpty()) {
					status = EnumStatus.NOT_SAFE;
					break findStatus;
				}
				status = EnumStatus.OK;
			}
		}
		}

		if(status == EnumStatus.OK)
			ReCubedAPI.addValueToCategory(LibCategories.TIMES_SLEPT, event.entityPlayer.getGameProfile().getName(), "recubed.misc.sleep", 1);
	}

}
