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

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import vazkii.recubed.client.gui.GuiReCubedMenu;
import vazkii.recubed.common.lib.LibMisc;

public class KeybindHandler {

	private static KeyBinding key = new KeyBinding(LibMisc.MOD_NAME, Keyboard.KEY_F7, "key.categories.misc");

	public KeybindHandler() {
		ClientRegistry.registerKeyBinding(key);
	}

	@SubscribeEvent
	public void playerTick(ClientTickEvent event) {
		if(event.phase == Phase.START) {
			if(key.isKeyDown())
				Minecraft.getMinecraft().displayGuiScreen(new GuiReCubedMenu());
		}
	}
}
