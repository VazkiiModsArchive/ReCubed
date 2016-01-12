/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 13, 2013, 2:32:54 PM (GMT)]
 */
package vazkii.recubed.common.core.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.api.internal.ServerData;
import vazkii.recubed.common.command.CommandCategoryFreeze;
import vazkii.recubed.common.command.CommandClearCategory;
import vazkii.recubed.common.command.CommandClearPlayer;
import vazkii.recubed.common.command.CommandDelBackup;
import vazkii.recubed.common.command.CommandFreeze;
import vazkii.recubed.common.command.CommandLoadBackup;
import vazkii.recubed.common.command.CommandSaveBackup;
import vazkii.recubed.common.command.CommandWipeData;
import vazkii.recubed.common.command.CommandWipePlayer;
import vazkii.recubed.common.core.handler.ConfigHandler;
import vazkii.recubed.common.core.handler.GeneralEventHandler;
import vazkii.recubed.common.core.handler.PlayerTracker;
import vazkii.recubed.common.core.handler.ServerTickHandler;
import vazkii.recubed.common.core.handler.WorldSaveHandler;
import vazkii.recubed.common.core.helper.CacheHelper;
import vazkii.recubed.common.lib.LibCategories;
import vazkii.recubed.common.network.PacketCategory;
import vazkii.recubed.common.network.PacketHandler;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.load(event.getSuggestedConfigurationFile());

		registerCategory(LibCategories.ANIMALS_SHEARED);
		registerCategory(LibCategories.ARROWS_SHOT);
		registerCategory(LibCategories.BOSS_KILLS);
		registerCategory(LibCategories.BLOCKS_BROKEN);
		registerCategory(LibCategories.COWS_MILKED);
		registerCategory(LibCategories.DAMAGE_DEALT);
		registerCategory(LibCategories.DAMAGE_TAKEN);
		registerCategory(LibCategories.DIMENSIONS_CHANGED);
		registerCategory(LibCategories.DISCS_PLAYED);
		registerCategory(LibCategories.ENDER_EYES_USED);
		registerCategory(LibCategories.ENDER_PEARLS_THROWN);
		registerCategory(LibCategories.ENTITIES_RIDDEN);
		registerCategory(LibCategories.EXPERIENCE_GATHERED);
		registerCategory(LibCategories.FOOD_EATEN);
		registerCategory(LibCategories.ITEMS_BROKEN);
		registerCategory(LibCategories.ITEMS_DROPPED);
		registerCategory(LibCategories.ITEMS_PICKED_UP);
		registerCategory(LibCategories.ITEMS_SPAWNED);
		registerCategory(LibCategories.LEVELS_GAINED);
		registerCategory(LibCategories.MOBS_AGGROED);
		registerCategory(LibCategories.MOBS_KILLED);
		registerCategory(LibCategories.MESSAGES_SENT);
		registerCategory(LibCategories.PLAYER_KILLS);
		registerCategory(LibCategories.POTIONS_DRANK);
		registerCategory(LibCategories.POTIONS_GOTTEN);
		registerCategory(LibCategories.POTIONS_THROWN);
		registerCategory(LibCategories.SHEEP_DYED);
		registerCategory(LibCategories.SNOWBALLS_THROWN);
		registerCategory(LibCategories.TIMES_DIED);
		registerCategory(LibCategories.TIMES_FISHED);
		registerCategory(LibCategories.TIMES_PLAYED);
		registerCategory(LibCategories.TIMES_SLEPT);
	}

	private static void registerCategory(String category) {
		ReCubedAPI.registerCategory(category, category.substring(LibCategories.CATEGORY.length()));
	}

	public void receivePacket(PacketCategory packet) {
		// NO-OP
	}

	public void init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new ServerTickHandler());
		FMLCommonHandler.instance().bus().register(new PlayerTracker());

		MinecraftForge.EVENT_BUS.register(new WorldSaveHandler());
		MinecraftForge.EVENT_BUS.register(new GeneralEventHandler());

		PacketHandler.init();
	}

	public void serverStarting(FMLServerStartingEvent event) {
		ServerData.init();

		event.registerServerCommand(new CommandCategoryFreeze());
		event.registerServerCommand(new CommandClearCategory());
		event.registerServerCommand(new CommandClearPlayer());
		event.registerServerCommand(new CommandDelBackup());
		event.registerServerCommand(new CommandFreeze());
		event.registerServerCommand(new CommandLoadBackup());
		event.registerServerCommand(new CommandSaveBackup());
		event.registerServerCommand(new CommandWipeData());
		event.registerServerCommand(new CommandWipePlayer());
	}

	public void serverStarted() {
		CacheHelper.findCompoundAndLoad();
	}

	public void serverStopping() {
		CacheHelper.findCompoundAndWrite();
	}

	public void serverStopped() {
		ServerData.reset();
	}
}
