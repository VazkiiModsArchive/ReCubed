/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 14, 2013, 1:55:03 PM (GMT)]
 */
package vazkii.recubed.client.renders;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.PlayerCategoryData;
import vazkii.recubed.client.core.handler.ClientCacheHandler;
import vazkii.recubed.client.core.helper.RenderHelper;
import vazkii.recubed.client.lib.LibResources;
import vazkii.recubed.common.core.helper.MiscHelper;

public final class StatBarsRender {

	static ResourceLocation hudBar = new ResourceLocation(LibResources.RESOURCE_HUD_BAR);

	public Collection<Entry> entries = new TreeSet();
	public final boolean isCategory;
	final String displayName;
	Object data;

	public static class Entry implements Comparable<Entry> {
		int val;
		int color;
		int pos;
		float percentage;

		public String name;

		public Entry(int val, String name) {
			this(val, MiscHelper.generateColorFromString(name), name);
		}

		public Entry(int val, int color, String name) {
			this.val = val;
			this.color = color;
			this.name = name;
		}

		@Override
		public int compareTo(Entry e) {
			return name.compareTo(e.name);
		}

	}

	public static StatBarsRender fromCategory(Category category) {
		if(category == null)
			return null;

		StatBarsRender render = new StatBarsRender(true, StatCollector.translateToLocal(category.name));
		for(String s : category.playerData.keySet())
			render.entries.add(new Entry(category.getTotalValueFromPlayerData(s), s));

		render.data = category;
		int totalVal = render.getTotalVal();

		render.sortValues(totalVal);

		return render;
	}

	public static StatBarsRender fromPlayerData(PlayerCategoryData data, Category category) {
		if(data == null || category == null)
			return null;

		StatBarsRender render = new StatBarsRender(false, StatCollector.translateToLocal(category.name) + " - " + data.name);
		if(data != null) {
			for(String s : data.stats.keySet())
				render.entries.add(new Entry(data.stats.get(s), s));

			render.data = data;
		}

		int totalVal = render.getTotalVal();

		render.sortValues(totalVal);

		return render;
	}

	private int getTotalVal() {
		int totalValue = 0;
		for(Entry entry : entries)
			totalValue += entry.val;

		float mul = 100F / totalValue;
		for(Entry entry : entries)
			entry.percentage = Math.round(entry.val * mul * 100F) / 100F;

		return totalValue;
	}

	private void sortValues(int totalValue) {
		List<Entry> sortedEntries = new ArrayList(entries);

		Collections.sort(sortedEntries, new Comparator<Entry>() {

			@Override
			public int compare(Entry a, Entry b) {
				return b.val - a.val;
			}

		});

		List<Entry> newEntries = new ArrayList();

		int totalVal = 0;
		float totalPercentage = 0F;
		int size = 14;
		int i = 0;
		boolean addedClientPlayer = false;

		String name = Minecraft.getMinecraft().thePlayer.getGameProfile().getName();
		int clientPos = 0;
		for(Entry entry : sortedEntries) {
			if(entry.name.equals(name)) {
				clientPos = i;
				break;
			}
			++i;
		}

		i = 0;
		for(Entry entry : sortedEntries) {
			if(i >= size) {
				if(!isCategory) {
					Entry othersEntry = new Entry(totalValue - totalVal, "recubed.misc.others");
					othersEntry.pos = 15;
					othersEntry.percentage = Math.round((100F - totalPercentage) * 100F) / 100F;
					newEntries.add(othersEntry);

					break;
				} else if(!addedClientPlayer) {
					Category category = (Category) data;
					Entry playerEntry = new Entry(category.getTotalValueFromPlayerData(name), name);
					playerEntry.pos = clientPos;
					newEntries.add(playerEntry);

					break;
				}
			}

			totalVal += entry.val;
			totalPercentage += entry.percentage;
			entry.pos = i + 1;
			newEntries.add(entry);

			if(i >= size)
				break;

			i++;
		}

		entries = newEntries;
	}

	private StatBarsRender(boolean isCategory, String displayName) {
		this.isCategory = isCategory;
		this.displayName = displayName;
	}

	public void renderStatBars(int x, int y) {
		final int width = 100;
		final int height = 98;

		Tessellator tess = Tessellator.instance;
		Minecraft mc = Minecraft.getMinecraft();

		GL11.glEnable(GL11.GL_BLEND);

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Gui.drawRect(x, y + 8, x + width, y + height, 0x77000000);

		int yp = 8;
		for(Entry entry : entries) {
			if(!ClientCacheHandler.useGradients)
				Gui.drawRect(x, y + yp, x + width, y + 6 + yp, entry.color);
			else {
				Color color = new Color(entry.color).brighter();
				Color color1 = ClientCacheHandler.useGradients ? new Color(entry.color).darker().darker() : color;
				RenderHelper.drawGradientRect(x, y + yp, 0, x + width, y + 6 + yp, color.getRGB(), color1.getRGB());
			}

			yp += 6;
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glScalef(0.5F, 0.5F, 0.5F);

		mc.renderEngine.bindTexture(hudBar);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		tess.startDrawingQuads();
		tess.addVertexWithUV(x * 2, (y + 8) * 2, 0, 0, 1);
		tess.addVertexWithUV((x + width) * 2, (y + 8) * 2, 0, 1, 1);
		tess.addVertexWithUV((x + width) * 2, y * 2, 0, 1, 0);
		tess.addVertexWithUV(x * 2, y * 2, 0, 0, 0);
		tess.draw();
		
		GL11.glPushMatrix();
		for(int i = 0; i < 2; i++) {
			Gui.drawRect(x * 2, (y + 8) * 2, x * 2 + 1, (y + height) * 2, 0xFF000000);
			GL11.glTranslatef(width * 2 - 1, 0F, 0F);
		}
		GL11.glPopMatrix();
		Gui.drawRect(x * 2, (y + height) * 2 - 1, (x + width) * 2, (y + height) * 2, 0xFF000000);

		boolean unicode = mc.fontRenderer.getUnicodeFlag();
		mc.fontRenderer.setUnicodeFlag(!ClientCacheHandler.useVanillaFont);

		mc.fontRenderer.drawStringWithShadow(displayName, (x + 4) * 2, (y + 2) * 2, 0xFFFFFF);
		yp = 9;
		for(Entry entry : entries) {
			String posStr = "#" + entry.pos + " - ";
			String valAndPercentageStr = ": " + entry.val + " (" + entry.percentage + "%)";
			int remainingLenght = width * 2 - 4 - (mc.fontRenderer.getStringWidth(posStr) + mc.fontRenderer.getStringWidth(valAndPercentageStr));

			String name = StatCollector.translateToLocal(entry.name);
			String nameStr = mc.fontRenderer.trimStringToWidth(StatCollector.translateToLocal(entry.name), remainingLenght);
			if(!name.equals(nameStr)) {
				String elipsis = "(...)";
				remainingLenght -= mc.fontRenderer.getStringWidth(elipsis);
				nameStr = mc.fontRenderer.trimStringToWidth(nameStr, remainingLenght);
				nameStr = nameStr + elipsis;
			}

			String s1 = posStr + nameStr + valAndPercentageStr;

			int colorRGB = 0xFFFFFF;
			if(ClientCacheHandler.contrastHudText) {
				Color color = new Color(entry.color);
				Color color1 = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
				colorRGB = color1.getRGB();
			}

			mc.fontRenderer.drawStringWithShadow(s1, (x + 2) * 2, (y + yp) * 2, colorRGB);

			yp += 6;
		}

		mc.fontRenderer.setUnicodeFlag(unicode);

		GL11.glScalef(2F, 2F, 2F);
		GL11.glPopMatrix();
	}

}
