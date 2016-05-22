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

import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandGive;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.SleepResult;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.common.core.helper.MiscHelper;
import vazkii.recubed.common.lib.LibCategories;

public final class GeneralEventHandler {

	private static ItemStack stackFromState(IBlockState state) {
		return new ItemStack(state.getBlock(), state.getBlock().getMetaFromState(state));
	}
	
	// ARROWS SHOT
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityTakeDamage(ArrowLooseEvent event) {
		float f = event.getCharge() / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if(ReCubedAPI.validatePlayer(event.getEntityPlayer()))
			ReCubedAPI.addValueToCategory(LibCategories.ARROWS_SHOT, event.getEntityPlayer().getGameProfile().getName(), f >= 1F ? "recubed.misc.critical_shot" : "recubed.misc.shot", 1);
	}

	// BLOCKS BROKEN
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onBlockBroken(BlockEvent.BreakEvent event) {
		if(ReCubedAPI.validatePlayer(event.getPlayer()) && event.getState().getBlock() != null && Item.getItemFromBlock(event.getState().getBlock()) != null)
			ReCubedAPI.addValueToCategory(LibCategories.BLOCKS_BROKEN, event.getPlayer().getGameProfile().getName(), Item.getItemFromBlock(event.getState().getBlock()).getUnlocalizedName(stackFromState(event.getState())) + ".name", 1);
	}


	// COWS MILKED + ANIMALS SHEARED + SHEEP DYED
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityInteracted(EntityInteract event) {
		if(ReCubedAPI.validatePlayer(event.getEntityPlayer())) {
			ItemStack mainStack = event.getEntityPlayer().getHeldItemMainhand();
			ItemStack offStack = event.getEntityPlayer().getHeldItemOffhand();
			
			if(mainStack != null) {
				if(mainStack.getItem() == Items.BUCKET && event.getTarget() instanceof EntityCow)
					ReCubedAPI.addValueToCategory(LibCategories.COWS_MILKED, event.getEntityPlayer().getGameProfile().getName(), "item.milk.name", 1);
		
				if(mainStack.getItem() == Items.SHEARS && event.getTarget() instanceof IShearable && ((IShearable) event.getTarget()).isShearable(mainStack, event.getTarget().worldObj, new BlockPos(event.getTarget())))
					ReCubedAPI.addValueToCategory(LibCategories.ANIMALS_SHEARED, event.getEntityPlayer().getGameProfile().getName(), MiscHelper.getEntityString(event.getTarget()), 1);

				if(mainStack.getItem() instanceof ItemDye && event.getTarget() instanceof EntitySheep && !((EntitySheep) event.getTarget()).getSheared() && 15 - ((EntitySheep) event.getTarget()).getFleeceColor().getDyeDamage() != mainStack.getItemDamage())
					ReCubedAPI.addValueToCategory(LibCategories.SHEEP_DYED, event.getEntityPlayer().getGameProfile().getName(), MiscHelper.getStackName(mainStack), 1);
			}
			
			if(offStack != null) {
				if(offStack.getItem() == Items.BUCKET && event.getTarget() instanceof EntityCow)
					ReCubedAPI.addValueToCategory(LibCategories.COWS_MILKED, event.getEntityPlayer().getGameProfile().getName(), "item.milk.name", 1);
		
				if(offStack.getItem() == Items.SHEARS && event.getTarget() instanceof IShearable && ((IShearable) event.getTarget()).isShearable(offStack, event.getTarget().worldObj, new BlockPos(event.getTarget())))
					ReCubedAPI.addValueToCategory(LibCategories.ANIMALS_SHEARED, event.getEntityPlayer().getGameProfile().getName(), MiscHelper.getEntityString(event.getTarget()), 1);

				if(offStack.getItem() instanceof ItemDye && event.getTarget() instanceof EntitySheep && !((EntitySheep) event.getTarget()).getSheared() && 15 - ((EntitySheep) event.getTarget()).getFleeceColor().getDyeDamage() != offStack.getItemDamage())
					ReCubedAPI.addValueToCategory(LibCategories.SHEEP_DYED, event.getEntityPlayer().getGameProfile().getName(), MiscHelper.getStackName(offStack), 1);
			}
		}
	}

	// DAMAGE DEALT
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityTakeDamage(LivingHurtEvent event) {
		if(event.getSource().getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getEntity();
			String name = MiscHelper.getEntityString(event.getEntity());

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.DAMAGE_DEALT, player.getGameProfile().getName(), name, (int) Math.min(event.getEntityLiving().getHealth(), event.getAmount()));
		}
	}

	// DAMAGE TAKEN
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerTakeDamage(LivingHurtEvent event) {
		if(event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			String name = "recubed.damage." + event.getSource().getDamageType();
			if(event.getSource().getEntity() != null)
				name = MiscHelper.getEntityString(event.getSource().getEntity());

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.DAMAGE_TAKEN, player.getGameProfile().getName(), name, (int) Math.min(player.getHealth(), event.getAmount()));
		}
	}

	// ITEMS BROKEN
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onItemBroken(PlayerDestroyItemEvent event) {
		if(ReCubedAPI.validatePlayer(event.getEntityPlayer()) && event.getOriginal().isItemStackDamageable() && event.getOriginal().getItemDamage() == event.getOriginal().getMaxDamage())
			ReCubedAPI.addValueToCategory(LibCategories.ITEMS_BROKEN, event.getEntityPlayer().getGameProfile().getName(), MiscHelper.getStackName(event.getOriginal()), 1);
	}

	// ITEMS DROPPED
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerTossItem(ItemTossEvent event) {
		ItemStack stack = event.getEntityItem().getEntityItem();
		if(ReCubedAPI.validatePlayer(event.getPlayer()))
			ReCubedAPI.addValueToCategory(LibCategories.ITEMS_DROPPED, event.getPlayer().getGameProfile().getName(), MiscHelper.getStackName(stack), stack.stackSize);

	}

	// ITEMS PICKED UP
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onItemPickedUp(EntityItemPickupEvent event) {
		ItemStack stack = event.getItem().getEntityItem();
		if(ReCubedAPI.validatePlayer(event.getEntityPlayer()))
			ReCubedAPI.addValueToCategory(LibCategories.ITEMS_PICKED_UP, event.getEntityPlayer().getGameProfile().getName(), MiscHelper.getStackName(stack), stack.stackSize);
	}

	// MESSAGES SENT + ITEMS SPAWNED
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onMessageReceived(ServerChatEvent event) {
		if(ReCubedAPI.validatePlayer(event.getPlayer()))
			ReCubedAPI.addValueToCategory(LibCategories.MESSAGES_SENT, event.getUsername(), "recubed.misc.chat", 1);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onMessageReceived(CommandEvent event) {
		if(event.getSender() instanceof EntityPlayer && ReCubedAPI.validatePlayer((EntityPlayer) event.getSender())) {
			ReCubedAPI.addValueToCategory(LibCategories.MESSAGES_SENT, event.getSender().getName(), "/" + event.getCommand().getCommandName(), 1);

			try {
				if(event.getCommand() instanceof CommandGive) {
					String name = event.getParameters()[1];
					Item item = CommandBase.getItemByText(event.getSender(), name);
					int j = 1;
					int k = 0;

					if(item == null)
						return;

					if(event.getParameters().length >= 3)
						j = CommandBase.parseInt(event.getParameters()[2], 1, 64);

					if(event.getParameters().length >= 4)
						k = CommandBase.parseInt(event.getParameters()[3]);

					ItemStack stack = new ItemStack(item, j, k);

					ReCubedAPI.addValueToCategory(LibCategories.ITEMS_SPAWNED, event.getSender().getName(), MiscHelper.getStackName(stack), 1);
				}
			} catch(NumberInvalidException e) { }
		}
	}

	// MOBS AGGROED
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onMobGetTarget(LivingSetAttackTargetEvent event) {
		if(event.getTarget() instanceof EntityPlayer && EntityList.getEntityString(event.getEntity()) != null) {
			EntityPlayer player = (EntityPlayer) event.getTarget();
			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.MOBS_AGGROED, player.getGameProfile().getName(), MiscHelper.getEntityString(event.getEntity()), 1);
		}
	}

	// MOBS KILLED + BOSS KILLS
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityDie(LivingDeathEvent event) {
		if(event.getSource().getEntity() instanceof EntityPlayer && !(event.getEntity() instanceof EntityPlayer)) {
			EntityPlayer player = (EntityPlayer) event.getSource().getEntity();
			String name = MiscHelper.getEntityString(event.getEntity());

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(event.getEntity().isNonBoss() ? LibCategories.MOBS_KILLED : LibCategories.BOSS_KILLS, player.getGameProfile().getName(), name, 1);
		}
	}


	// TIMES DIED + PLAYER KILLS
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerDie(LivingDeathEvent event) {
		if(event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			String name = "recubed.damage." + event.getSource().getDamageType();
			if(event.getSource().getEntity() != null)
				name = MiscHelper.getEntityString(event.getSource().getEntity());
			if(event.getSource().getEntity() instanceof EntityPlayer) {
				name = ((EntityPlayer) event.getSource().getEntity()).getGameProfile().getName();
				if(ReCubedAPI.validatePlayer((EntityPlayer) event.getSource().getEntity()))
					ReCubedAPI.addValueToCategory(LibCategories.PLAYER_KILLS, name, player.getGameProfile().getName(), 1);
			}

			if(ReCubedAPI.validatePlayer(player))
				ReCubedAPI.addValueToCategory(LibCategories.TIMES_DIED, player.getGameProfile().getName(), name, 1);
		}
	}

	// SNOWBALLS THROWN + ENDER PEARLS THROWN + ENDER EYES USED + TIMES FISHED + POTIONS THROWN
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerInteractItem(PlayerInteractEvent.RightClickItem event) {
		if(ReCubedAPI.validatePlayer(event.getEntityPlayer())) {
			ItemStack mainStack = event.getEntityPlayer().getHeldItemMainhand();
			ItemStack offStack = event.getEntityPlayer().getHeldItemOffhand();
			
			if(mainStack != null) {
				if(mainStack.getItem() == Items.SNOWBALL)
					ReCubedAPI.addValueToCategory(LibCategories.SNOWBALLS_THROWN, event.getEntityPlayer().getGameProfile().getName(), "item.snowball.name", 1);

				if(!event.getEntityPlayer().capabilities.isCreativeMode && mainStack.getItem() == Items.ENDER_PEARL)
					ReCubedAPI.addValueToCategory(LibCategories.ENDER_PEARLS_THROWN, event.getEntityPlayer().getGameProfile().getName(), "item.enderPearl.name", 1);

				if(event.getEntityPlayer().dimension == 0 && mainStack.getItem() == Items.ENDER_EYE)
					ReCubedAPI.addValueToCategory(LibCategories.ENDER_EYES_USED, event.getEntityPlayer().getGameProfile().getName(), "item.eyeOfEnder.name", 1);

				if(mainStack.getItem() == Items.FISHING_ROD && event.getEntityPlayer().fishEntity == null)
					ReCubedAPI.addValueToCategory(LibCategories.TIMES_FISHED, event.getEntityPlayer().getGameProfile().getName(), "recubed.misc.hook_casted", 1);

				if(mainStack.getItem() == Items.POTIONITEM) {
					ItemPotion potion = (ItemPotion) mainStack.getItem();
					if(potion instanceof ItemSplashPotion) {
						List<PotionEffect> effects = PotionUtils.getEffectsFromStack(mainStack);
						if(effects != null)
							for(PotionEffect effect : effects)
								ReCubedAPI.addValueToCategory(LibCategories.POTIONS_THROWN, event.getEntityPlayer().getGameProfile().getName(), effect.getPotion().getName(), 1);
					}
				}
			}
			
			if(offStack != null) {
				if(offStack.getItem() == Items.SNOWBALL)
					ReCubedAPI.addValueToCategory(LibCategories.SNOWBALLS_THROWN, event.getEntityPlayer().getGameProfile().getName(), "item.snowball.name", 1);

				if(!event.getEntityPlayer().capabilities.isCreativeMode && offStack.getItem() == Items.ENDER_PEARL)
					ReCubedAPI.addValueToCategory(LibCategories.ENDER_PEARLS_THROWN, event.getEntityPlayer().getGameProfile().getName(), "item.enderPearl.name", 1);

				if(event.getEntityPlayer().dimension == 0 && offStack.getItem() == Items.ENDER_EYE)
					ReCubedAPI.addValueToCategory(LibCategories.ENDER_EYES_USED, event.getEntityPlayer().getGameProfile().getName(), "item.eyeOfEnder.name", 1);

				if(offStack.getItem() == Items.FISHING_ROD && event.getEntityPlayer().fishEntity == null)
					ReCubedAPI.addValueToCategory(LibCategories.TIMES_FISHED, event.getEntityPlayer().getGameProfile().getName(), "recubed.misc.hook_casted", 1);

				if(offStack.getItem() == Items.POTIONITEM) {
					ItemPotion potion = (ItemPotion) offStack.getItem();
					if(potion instanceof ItemSplashPotion) {
						List<PotionEffect> effects = PotionUtils.getEffectsFromStack(offStack);
						if(effects != null)
							for(PotionEffect effect : effects)
								ReCubedAPI.addValueToCategory(LibCategories.POTIONS_THROWN, event.getEntityPlayer().getGameProfile().getName(), effect.getPotion().getName(), 1);
					}
				}
			}
		}
	}
	
	// DISCS PLAYED
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerInteractBlock(PlayerInteractEvent.RightClickItem event) {
		if(ReCubedAPI.validatePlayer(event.getEntityPlayer())) {
			ItemStack mainStack = event.getEntityPlayer().getHeldItemMainhand();
			ItemStack offStack = event.getEntityPlayer().getHeldItemOffhand();
			
			if(mainStack != null) {
				if(mainStack.getItem() instanceof ItemRecord && event.getEntityPlayer().worldObj.getBlockState(event.getPos()).getBlock() == Blocks.JUKEBOX)
					ReCubedAPI.addValueToCategory(LibCategories.DISCS_PLAYED, event.getEntityPlayer().getGameProfile().getName(), ((ItemRecord) mainStack.getItem()).getRecordNameLocal(), 1);
			}
			
			if(offStack != null) {
				if(offStack.getItem() instanceof ItemRecord && event.getEntityPlayer().worldObj.getBlockState(event.getPos()).getBlock() == Blocks.JUKEBOX)
					ReCubedAPI.addValueToCategory(LibCategories.DISCS_PLAYED, event.getEntityPlayer().getGameProfile().getName(), ((ItemRecord) offStack.getItem()).getRecordNameLocal(), 1);
			}
		}
	}

	// TIMES SLEPT
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerSleep(PlayerSleepInBedEvent event) {
		if(!ReCubedAPI.validatePlayer(event.getEntityPlayer()))
			return;

		SleepResult status = event.getResultStatus();
		if(status == null) {
			findStatus : {
			if(!event.getEntityPlayer().worldObj.isRemote) {
				if (event.getEntityPlayer().isPlayerSleeping() || !event.getEntityPlayer().isEntityAlive()) {
					status = SleepResult.OTHER_PROBLEM;
					break findStatus;
				}

				if (!event.getEntityPlayer().worldObj.provider.isSurfaceWorld()) {
					status = SleepResult.NOT_POSSIBLE_HERE;
					break findStatus;
				}

				if (event.getEntityPlayer().worldObj.isDaytime()) {
					status = SleepResult.NOT_POSSIBLE_NOW;
					break findStatus;
				}

				if (Math.abs(event.getEntityPlayer().posX - event.getPos().getX()) > 3D || Math.abs(event.getEntityPlayer().posY - event.getPos().getY()) > 3D || Math.abs(event.getEntityPlayer().posZ - event.getPos().getZ()) > 3D) {
					status = SleepResult.TOO_FAR_AWAY;
					break findStatus;
				}

				double d0 = 8.0D;
				double d1 = 5.0D;
				List list = event.getEntityPlayer().worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(event.getPos().getX() - d0, event.getPos().getY() - d1, event.getPos().getZ() - d0, event.getPos().getX() + d0, event.getPos().getY() + d1, event.getPos().getZ() + d0));

				if (!list.isEmpty()) {
					status = SleepResult.NOT_SAFE;
					break findStatus;
				}
				status = SleepResult.OK;
			}
		}
		}

		if(status == SleepResult.OK)
			ReCubedAPI.addValueToCategory(LibCategories.TIMES_SLEPT, event.getEntityPlayer().getGameProfile().getName(), "recubed.misc.sleep", 1);
	}

}
