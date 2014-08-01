/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 15, 2013, 1:37:12 PM (GMT)]
 */
package vazkii.recubed.client.core.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import vazkii.recubed.client.gui.GuiReCubedMenu;
import vazkii.recubed.common.lib.LibMisc;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public class KeybindHandler {

	private static KeyBinding key = new KeyBinding(LibMisc.MOD_NAME, Keyboard.KEY_F7, "key.categories.misc");

	public KeybindHandler() {
		ClientRegistry.registerKeyBinding(key);
	}

	@SubscribeEvent
	public void playerTick(ClientTickEvent event) {
		if(event.phase == Phase.START) {
			if(key.getIsKeyPressed())
				Minecraft.getMinecraft().displayGuiScreen(new GuiReCubedMenu());
		}
	}
}
