/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 14, 2013, 8:17:34 PM (GMT)]
 */
package vazkii.recubed.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.PlayerCategoryData;
import vazkii.recubed.api.internal.ServerData;

public class CommandClearCategory extends CommandBase {

	@Override
	public String getCommandName() {
		return "recubed-clrcategory";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "recubed-clrcategory <category>";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if(astring.length != 1)
			throw new WrongUsageException(getCommandUsage(icommandsender), (Object[]) astring);
		
		Category category = ServerData.categories.get(ReCubedAPI.shortTerms.get(astring[0]));
		if(category == null)
			throw new CommandException("That category doesn't exist!");
		
		for(String s : category.playerData.keySet())
			category.playerData.put(s, new PlayerCategoryData(s));
	}
	
	@Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

}
