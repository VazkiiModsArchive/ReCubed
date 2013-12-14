/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 5:23:43 PM (GMT)]
 */
package vazkii.recubed.client.core.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.ServerData;
import vazkii.recubed.common.lib.LibCategories;
import vazkii.recubed.common.lib.LibMisc;

public final class ClientCacheHandler {

	public static boolean drawHud = true;
	public static boolean useGradients = true;
	public static boolean contrastHudText = false;
	
	public static int hudRelativeTo = 0;
	public static int hudPosX = 0;
	public static int hudPosY = 0;
	
	public static String hudCategory = LibCategories.DAMAGE_DEALT;
	public static String hudPlayer = "";
	
	public static File getCacheFile() throws IOException {
		File loc = new File(".");
		File cacheFile = new File(loc, LibMisc.MOD_ID + ".dat");
		
		if(!cacheFile.exists())
			cacheFile.createNewFile();
		
		return cacheFile;
	}
	
	public static NBTTagCompound getCacheCompound() {
		File cache = null;
		
		try {
			cache = getCacheFile();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		if(cache == null)
			throw new RuntimeException("No cache file!");
		
		try {
			NBTTagCompound cmp = CompressedStreamTools.readCompressed(new FileInputStream(cache));
			return cmp;
		} catch(IOException e) {
			NBTTagCompound cmp = new NBTTagCompound();
			
			try {
				CompressedStreamTools.writeCompressed(cmp, new FileOutputStream(cache));
				return getCacheCompound();
			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			}
		}
	}
	
	public static void injectNBTToFile(NBTTagCompound cmp) {
		try {
			File f = getCacheFile();
			CompressedStreamTools.writeCompressed(cmp, new FileOutputStream(f));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void findCompoundAndLoad() {
		NBTTagCompound cmp = getCacheCompound();
		
		if(cmp.hasNoTags()) {
			Minecraft mc = Minecraft.getMinecraft();
			hudPosX = 100;
			hudPosY = 98;
			hudRelativeTo = 3;
			findCompoundAndWrite();
		} else {
			drawHud = cmp.getBoolean("drawHud");
			useGradients = cmp.getBoolean("useGradients");
			contrastHudText = cmp.getBoolean("contrastHudText");
			hudRelativeTo = cmp.getInteger("hudRelativeTo");
			hudPosX = cmp.getInteger("hudPosX");
			hudPosY = cmp.getInteger("hudPosY");
			hudCategory = cmp.getString("hudCategory");
			hudPlayer = cmp.getString("hudPlayer");
		}
	}
	
	public static void findCompoundAndWrite() {
		NBTTagCompound cmp = new NBTTagCompound();
		
		cmp.setBoolean("useGradients", useGradients);
		cmp.setBoolean("contrastHudText", contrastHudText);
		cmp.setBoolean("drawHud", drawHud);
		cmp.setInteger("hudRelativeTo", hudRelativeTo);
		cmp.setInteger("hudPosX", hudPosX);
		cmp.setInteger("hudPosY", hudPosY);
		cmp.setString("hudCategory", hudCategory);
		cmp.setString("hudPlayer", hudPlayer);
		
		injectNBTToFile(cmp);
	}
	
}
