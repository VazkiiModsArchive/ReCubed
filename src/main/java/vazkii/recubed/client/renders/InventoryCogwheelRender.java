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

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.recubed.client.core.helper.TransientScaledResolution;
import vazkii.recubed.client.gui.GuiReCubedMenu;
import vazkii.recubed.client.lib.LibResources;

public final class InventoryCogwheelRender {

	static ResourceLocation cogwheel = new ResourceLocation(LibResources.RESOURCE_COGWHEEL);

	public static void renderCogwheel() {
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.currentScreen != null && (mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiContainerCreative)) {
			boolean creative = mc.currentScreen instanceof GuiContainerCreative;

			TransientScaledResolution res = new TransientScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			int x = res.getScaledWidth() / 2 - 62;
			int y = res.getScaledHeight() / 2 - 75;

			if(creative) {
				GuiContainerCreative container = (GuiContainerCreative) mc.currentScreen;
				if(container.getSelectedTabIndex() == CreativeTabs.tabInventory.getTabIndex()) {
					x -= 7;
					y += 13;
				} else return;
			}

			RenderHelper.disableStandardItemLighting();
			GL11.glColor4f(1F, 1F, 1F, 1F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			mc.renderEngine.bindTexture(cogwheel);
			WorldRenderer wr = Tessellator.getInstance().getWorldRenderer();

			wr.begin(7, DefaultVertexFormats.POSITION_TEX);
			wr.pos(x * 2, y * 2 + 16, 0).tex(0, 1).endVertex();
			wr.pos(x * 2 + 16, y * 2 + 16, 0).tex(1, 1).endVertex();
			wr.pos(x * 2 + 16, y * 2, 0).tex(1, 0).endVertex();;
			wr.pos(x * 2, y * 2, 0).tex(0, 0).endVertex();
			Tessellator.getInstance().draw();

			GL11.glScalef(2F, 2F, 2F);
			GL11.glDisable(GL11.GL_BLEND);

			int mouseX = Mouse.getX() * res.getScaledWidth() / mc.displayWidth;
			int mouseY = res.getScaledHeight() - Mouse.getY() * res.getScaledHeight() / mc.displayHeight;

			if(mouseX >= x && mouseX < x + 8 && mouseY >= y && mouseY < y + 8) {
				vazkii.recubed.client.core.helper.RenderHelper.renderTooltip(mouseX, mouseY, Arrays.asList(EnumChatFormatting.RED + StatCollector.translateToLocal("recubed.misc.openMenu")));

				if(Mouse.isButtonDown(0))
					mc.displayGuiScreen(new GuiReCubedMenu());
			}
		}
	}

}
