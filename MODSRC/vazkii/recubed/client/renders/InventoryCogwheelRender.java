/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 4:39:53 PM (GMT)]
 */
package vazkii.recubed.client.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import vazkii.recubed.client.lib.LibResources;

public final class InventoryCogwheelRender {

	static ResourceLocation cogwheel = new ResourceLocation(LibResources.RESOURCE_COGWHEEL);
	
	public static void renderCogwheel() {
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.currentScreen != null && (mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiContainerCreative)) {
			boolean creative = mc.currentScreen instanceof GuiContainerCreative;
			
			ScaledResolution res = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			int x = res.getScaledWidth() / 2 - 19;
			int y = res.getScaledHeight() / 2 - 75;
			
			if(!mc.thePlayer.getActivePotionEffects().isEmpty())
				x += 60;
			
			if(creative) {
				GuiContainerCreative container = (GuiContainerCreative) mc.currentScreen;
				if(container.getCurrentTabIndex() == CreativeTabs.tabInventory.getTabIndex()) {
					x -= 26;
					y += 13;
				} else return;
			}
			
			RenderHelper.disableStandardItemLighting();
			GL11.glColor4f(1F, 1F, 1F, 1F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			mc.renderEngine.bindTexture(cogwheel);
			Tessellator tess = Tessellator.instance;
			
			tess.startDrawingQuads();
			tess.addVertexWithUV(x * 2, y * 2 + 16, 0, 0, 1);
			tess.addVertexWithUV(x * 2 + 16, y * 2 + 16, 0, 1, 1);
			tess.addVertexWithUV(x * 2 + 16, y * 2, 0, 1, 0);
			tess.addVertexWithUV(x * 2, y * 2, 0, 0, 0);
			tess.draw();
			
			GL11.glScalef(2F, 2F, 2F);
			GL11.glDisable(GL11.GL_BLEND);
			RenderHelper.enableStandardItemLighting();
		}
	}
	
}
