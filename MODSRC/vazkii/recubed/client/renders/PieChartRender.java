/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 13, 2013, 6:02:37 PM (GMT)]
 */
package vazkii.recubed.client.renders;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.PlayerCategoryData;
import vazkii.recubed.client.core.handler.ClientCacheHandler;
import vazkii.recubed.client.core.helper.RenderHelper;
import vazkii.recubed.common.core.helper.MiscHelper;

public strictfp final class PieChartRender {

	public boolean clickable;
	public int totalVal;

	public Collection<Entry> entries = new TreeSet();

	public static class Entry implements Comparable<Entry> {
		int val;
		float angle;
		int color;
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

	public static PieChartRender fromCategory(Category category) {
		PieChartRender render = new PieChartRender(true);
		for(String s : category.playerData.keySet())
			render.entries.add(new Entry(category.getTotalValueFromPlayerData(s), s));

		int totalVal = render.buildAngles();
		if(totalVal == 0)
			return null;
		render.truncateSmallValues(totalVal);

		return render;
	}

	public static PieChartRender fromPlayerData(PlayerCategoryData data) {
		PieChartRender render = new PieChartRender(false);
			for(String s : data.stats.keySet())
				render.entries.add(new Entry(data.stats.get(s), s));

			int totalVal = render.buildAngles();
			if(totalVal == 0)
				return null;
			render.truncateSmallValues(totalVal);

		return render;
	}


	private PieChartRender(boolean clickable) {
		this.clickable = clickable;
	}

	private int buildAngles() {
		int totalValue = 0;
		for(Entry entry : entries)
			totalValue += entry.val;

		float mul = 360F / totalValue;
		for(Entry entry : entries)
			entry.angle = entry.val * mul;

		return totalValue;
	}

	private void truncateSmallValues(int totalValue) {
		List<Entry> sortedEntries = new ArrayList(entries);

		Collections.sort(sortedEntries, new Comparator<Entry>() {

			@Override
			public int compare(Entry a, Entry b) {
				return b.val - a.val;
			}

		});

		List<Entry> newEntries = new ArrayList();
		float totalAngle = 0;
		int totalVal = 0;
		int size = 9;
		int i = 0;
		for(Entry entry : sortedEntries) {
			if(i >= size) {
				Entry othersEntry = new Entry(totalValue - totalVal, "recubed.misc.others");
				othersEntry.angle = 360F - totalAngle;
				newEntries.add(othersEntry);

				break;
			}
			totalAngle += entry.angle;
			totalVal += entry.val;
			newEntries.add(entry);

			i++;
		}

		this.totalVal = totalVal;
		entries = newEntries;
	}

	public Entry renderChart(int radius, int x, int y, int mx, int my) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		Entry tooltip = null;
		float tooltipDeg = 0;

		boolean mouseIn = (x - mx) * (x - mx) + (y - my) * (y - my) <= radius * radius;
		float angle = mouseAngle(x, y, mx, my);
		int highlight = 5;

		GL11.glShadeModel(GL11.GL_SMOOTH);
		float totalDeg = 0;
		for(Entry entry : entries) {
			boolean mouseInSector = mouseIn && angle > totalDeg && angle < totalDeg + entry.angle;
			Color color = new Color(entry.color).brighter();
			Color color1 = ClientCacheHandler.useGradients ? new Color(entry.color).darker().darker() : color;

			if(mouseInSector) {
				tooltip = entry;
				tooltipDeg = totalDeg;

				radius += highlight;
			}

			GL11.glBegin(GL11.GL_TRIANGLE_FAN);

			GL11.glColor4ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) 255);
			GL11.glVertex2i(x, y);

			GL11.glColor4ub((byte) color1.getRed(), (byte) color1.getGreen(), (byte) color1.getBlue(), (byte) 255);
			for(float i = entry.angle; i >= 0; i -= 0.01) {
				float rad = (float) ((i + totalDeg) / 180F * Math.PI);
				GL11.glVertex2d(x + Math.cos(rad) * radius, y + Math.sin(rad) * radius);
			}
			totalDeg += entry.angle;

			GL11.glColor4ub((byte) 0, (byte) 0, (byte) 0, (byte) 255);
			GL11.glVertex2i(x, y);
			GL11.glEnd();

			if(mouseInSector)
				radius -= highlight;
		}
		GL11.glShadeModel(GL11.GL_FLAT);

		totalDeg = 0;
		GL11.glLineWidth(2F);
		GL11.glColor4f(0F, 0F, 0F, 1F);
		for(Entry entry : entries) {
			if(Math.round(entry.angle) == 360)
				break;

			boolean mouseInSector = mouseIn && angle > totalDeg && angle < totalDeg + entry.angle;

			if(mouseInSector)
				radius += highlight;

			float rad = (float) (totalDeg / 180F * Math.PI);

			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2i(x, y);
			GL11.glVertex2d(x + Math.cos(rad) * radius, y + Math.sin(rad) * radius);
			GL11.glEnd();

			if(mouseInSector)
				radius -= highlight;

			totalDeg += entry.angle;
		}


		GL11.glLineWidth(3F);
		GL11.glColor4f(0F, 0F, 0F, 1F);

		GL11.glBegin(GL11.GL_LINE_LOOP);
		for(float i = 0; i < 360; i++) {
			boolean sectorHighlighted = tooltip != null && i >= tooltipDeg && i < tooltip.angle + tooltipDeg;
			boolean first = tooltip != null && tooltip.angle != 360 && i - tooltipDeg == 0;
			boolean last = tooltip != null && tooltip.angle != 360 && i - (tooltipDeg + tooltip.angle) == -1;

			if(first)
				addVertexForAngle(x, y, i, radius);

			if(sectorHighlighted)
				radius += highlight;

			addVertexForAngle(x, y, i, radius);

			if(last)
				addVertexForAngle(x, y, i + 1, radius);

			if(sectorHighlighted)
				radius -= highlight;
		}
		GL11.glEnd();

		if(tooltip != null) {
			List<String> tooltipList = new ArrayList(Arrays.asList(StatCollector.translateToLocal(tooltip.name), EnumChatFormatting.GRAY + "" + tooltip.val + " (" + Math.round(tooltip.angle / 3.6F * 100D) / 100D + "%)"));
			if(Minecraft.getMinecraft().gameSettings.advancedItemTooltips)
				tooltipList.add(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + tooltip.name);
			RenderHelper.renderTooltip(mx, my, tooltipList);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		return tooltip;
	}

	private static void addVertexForAngle(int x, int y, float i, int radius) {
		double rad = i / 180F * Math.PI;
		GL11.glVertex2d(x + Math.cos(rad) * radius, y + Math.sin(rad) * radius);
	}

	private static float mouseAngle(int x, int y, int mx, int my) {
		Vector2f baseVec = new Vector2f(1F, 0F);
		Vector2f mouseVec = new Vector2f(mx - x, my - y);

		float ang = (float) (Math.acos(Vector2f.dot(baseVec, mouseVec) / (baseVec.length() * mouseVec.length())) * (180F / Math.PI));
		return my < y ? 360F - ang : ang;
	}
}
