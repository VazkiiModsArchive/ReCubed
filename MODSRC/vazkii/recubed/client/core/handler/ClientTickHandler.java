/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 13, 2013, 4:04:23 PM (GMT)]
 */
package vazkii.recubed.client.core.handler;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import vazkii.recubed.api.internal.ClientData;
import vazkii.recubed.client.renders.InventoryCogwheelRender;
import vazkii.recubed.common.core.handler.ConfigHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// NO-OP
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if(type.equals(EnumSet.of(TickType.CLIENT))) {
			World world = Minecraft.getMinecraft().theWorld;
			if(world == null)
				ClientData.categories.clear();
		} else if(ConfigHandler.useCogwheel)
			InventoryCogwheelRender.renderCogwheel();

	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT, TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return "ReCubed";
	}

}
