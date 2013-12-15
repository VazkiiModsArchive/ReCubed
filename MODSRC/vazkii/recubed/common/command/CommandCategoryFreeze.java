/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 15, 2013, 2:11:30 PM (GMT)]
 */
package vazkii.recubed.common.command;

import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.PlayerCategoryData;
import vazkii.recubed.api.internal.ServerData;
import vazkii.recubed.common.core.helper.MiscHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

public class CommandCategoryFreeze extends CommandBase {

	@Override
	public String getCommandName() {
		return "recubed-freezecategory";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "recubed-freezecategory <category> <frozen(true/false)>";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if(astring.length != 2)
			throw new WrongUsageException(getCommandUsage(icommandsender), (Object[]) astring);
		
		if(icommandsender instanceof EntityPlayer && !MiscHelper.isPlayerAllowedToUseCommands(icommandsender.getCommandSenderName()))
			throw new CommandException("recubed.commands.no_perms");
				
		Category category = ServerData.categories.get(ReCubedAPI.shortTerms.get(astring[0]));
		if(category == null)
			throw new CommandException("recubed.commands.no_category");
		
		category.isFrozen = Boolean.parseBoolean(astring[1]);
		icommandsender.sendChatToPlayer(new ChatMessageComponent().addKey("recubed.commands.command_sucessful"));
	}
	
	@Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

}
