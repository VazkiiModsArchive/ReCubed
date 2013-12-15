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

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import vazkii.recubed.api.internal.ClientData;
import vazkii.recubed.client.gui.GuiReCubedMenu;
import vazkii.recubed.common.lib.LibMisc;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class KeybindHandler extends KeyHandler {

	private static KeyBinding key = new KeyBinding(LibMisc.MOD_NAME, Keyboard.KEY_F7);
	
	public KeybindHandler() {
		super(new KeyBinding[] { key }, new boolean[] { false });
	}

	@Override
	public String getLabel() {
		return LibMisc.MOD_NAME + "_key";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,	boolean tickEnd, boolean isRepeat) {
		if(!ClientData.categories.isEmpty())
			Minecraft.getMinecraft().displayGuiScreen(new GuiReCubedMenu());
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {

	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER);
	}

}
