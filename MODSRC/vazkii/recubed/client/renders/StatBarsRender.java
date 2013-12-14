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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import scala.collection.parallel.ParIterableLike.Min;
import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.PlayerCategoryData;
import vazkii.recubed.client.lib.LibResources;
import vazkii.recubed.common.core.helper.MiscHelper;
import vazkii.recubed.common.lib.LibMisc;

public final class StatBarsRender {

	static ResourceLocation hudBar = new ResourceLocation(LibResources.RESOURCE_HUD_BAR);
	
	public Collection<Entry> entries = new TreeSet();
	public final boolean isCategory;
	Object data;
	
	public static class Entry implements Comparable<Entry> {
		int val;
		int color;
		int pos; 
		
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
		StatBarsRender render = new StatBarsRender(true);
		for(String s : category.playerData.keySet())
			render.entries.add(new Entry(category.getTotalValueFromPlayerData(s), s));
		
		render.data = category;
		int totalVal = render.getTotalVal();

		render.sortValues(totalVal);
		
		return render;
	}
	
	public static StatBarsRender fromPlayerData(PlayerCategoryData data) {
		StatBarsRender render = new StatBarsRender(false);
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
		
		return totalValue;
	}
	
	private void sortValues(int totalValue) {
		List<Entry> sortedEntries = new ArrayList(this.entries);

		Collections.sort(sortedEntries, new Comparator<Entry>() {

			@Override
			public int compare(Entry a, Entry b) {
				return b.val - a.val;
			}
			
		});
		
		List<Entry> newEntries = new ArrayList();
		
		int totalVal = 0;
		int size = 9;
		int i = 0;
		boolean addedClientPlayer = false;
		
		String name = Minecraft.getMinecraft().thePlayer.username;
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
			entry.pos = i;
			newEntries.add(entry);
			
			if(i >= size)
				break;
			
			i++;
		}
		
		this.entries = newEntries;
	}
	
	private StatBarsRender(boolean isCategory) {
		this.isCategory = isCategory;
	}
	
	public void renderStatBars(int x, int y) {
		Tessellator tess = Tessellator.instance;
		Minecraft mc = Minecraft.getMinecraft();
		
		GL11.glEnable(GL11.GL_BLEND);
		mc.renderEngine.bindTexture(hudBar);
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + 8, 0, 0, 1);
		tess.addVertexWithUV(x + 50, y + 8, 0, 1, 1);
		tess.addVertexWithUV(x + 50, y, 0, 1, 0);
		tess.addVertexWithUV(x, y, 0, 0, 0);
		tess.draw();
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Gui.drawRect(x, y + 8, x + 50, y + 78, 0x77000000);
		
		int yp = 8;
		for(Entry entry : entries) {
			Gui.drawRect(x, y + yp, x + 50, y + 7 + yp, entry.color);

			
			yp += 7;
		}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		x *= 2;
		y *= 2;
		
		mc.fontRenderer.drawStringWithShadow(LibMisc.MOD_NAME, x + 5, y + 4, 0xFFFFFF);
		
		GL11.glScalef(2F, 2F, 2F);
		x /= 2;
		y /= 2;
		GL11.glPopMatrix();
	}
	
}
