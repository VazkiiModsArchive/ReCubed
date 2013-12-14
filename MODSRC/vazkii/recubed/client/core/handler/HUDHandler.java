/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 14, 2013, 1:55:25 PM (GMT)]
 */
package vazkii.recubed.client.core.handler;

import java.awt.Point;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;
import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.ClientData;
import vazkii.recubed.api.internal.PlayerCategoryData;
import vazkii.recubed.client.gui.GuiMoveHUD;
import vazkii.recubed.client.renders.StatBarsRender;

public final class HUDHandler {

	@ForgeSubscribe
	public void onDrawScreen(RenderGameOverlayEvent.Post event) {		
		if(event.type == ElementType.ALL && shouldRenderHUD()) {
			Point coords = getCoords(event.resolution.getScaledWidth(), event.resolution.getScaledHeight(), ClientCacheHandler.hudRelativeTo, ClientCacheHandler.hudPosX, ClientCacheHandler.hudPosY);
			
			String categoryName = ClientCacheHandler.hudCategory;

			Category category = ClientData.categories.get(categoryName);
			if(category != null) {
				String playerName = ClientCacheHandler.hudPlayer;
				if(playerName == null || playerName.isEmpty())
					StatBarsRender.fromCategory(category).renderStatBars(coords.x, coords.y);
				else {
					PlayerCategoryData data = category.playerData.get(playerName);
					StatBarsRender.fromPlayerData(data, category).renderStatBars(coords.x, coords.y);
				}
			}
		}
	}
	
	private static boolean shouldRenderHUD() {
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.currentScreen != null && mc.currentScreen instanceof GuiMoveHUD) 
			return true;
		return ClientCacheHandler.drawHud;
	}
	
	public static Point getCoords(int screenX, int screenY, int relativePos, int posX, int posY) {
		switch(relativePos) {
			case 0 : return new Point(screenX - posX, posY);
			case 1 : return new Point(posX, posY);
			case 2 : return new Point(posX, screenY - posY);
			default : return new Point(screenX - posX, screenY - posY);
		}
	}
	
}
