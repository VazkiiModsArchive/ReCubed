/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 5:21:53 PM (GMT)]
 */
package vazkii.recubed.client.gui;

import vazkii.recubed.client.core.handler.ClientCacheHandler;
import vazkii.recubed.client.lib.LibResources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiShowHUDCheckboxButton extends GuiButton {

	static ResourceLocation check = new ResourceLocation(LibResources.RESOURCE_CHECK);
	
	public GuiShowHUDCheckboxButton(int par1, int par2, int par3) {
		super(par1, par2, par3, 20, 20, "");
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		super.drawButton(par1Minecraft, par2, par3);
		
		if(ClientCacheHandler.drawHud) {
			par1Minecraft.renderEngine.bindTexture(check);
			int x = xPosition + 2;
			int y = yPosition + 2;
			
			zLevel += 1;
			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();
			tess.addVertexWithUV(x, y + 16, zLevel, 0, 1);
			tess.addVertexWithUV(x + 16, y + 16, zLevel, 1, 1);
			tess.addVertexWithUV(x + 16, y, zLevel, 1, 0);
			tess.addVertexWithUV(x, y, zLevel, 0, 0);
			tess.draw();
			zLevel -= 1;
		}
		
		par1Minecraft.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("recubed.misc.hud_enabled"), xPosition + 25, yPosition + 7, 0xFFFFFF);
	}
}
