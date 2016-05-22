/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 15, 2013, 2:52:08 PM (GMT)]
 */
package vazkii.recubed.common.command;

import java.io.File;
import java.io.IOException;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import vazkii.recubed.common.core.helper.CacheHelper;
import vazkii.recubed.common.core.helper.MiscHelper;
import vazkii.recubed.common.lib.LibMisc;

public class CommandDelBackup extends CommandBase {

	@Override
	public String getCommandName() {
		return "recubed-delbackup";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "recubed-delbackup <name>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender icommandsender, String[] astring) throws CommandException {
		if(astring.length != 1)
			throw new WrongUsageException(getCommandUsage(icommandsender), (Object[]) astring);

		if(icommandsender instanceof EntityPlayer && !MiscHelper.isPlayerAllowedToUseCommands(icommandsender.getName()))
			throw new CommandException("recubed.commands.no_perms");

		String backupName = astring[0];

		try {
			File file = CacheHelper.getCacheFile(LibMisc.MOD_ID + "Backups/", backupName + ".dat", false);
			if(file == null)
				throw new CommandException("recubed.commands.no_backup");

			file.delete();
			icommandsender.addChatMessage(new TextComponentTranslation("recubed.commands.command_sucessful"));
		} catch (IOException e) {
			throw new CommandException(e.getMessage(), (Object[]) e.getStackTrace());
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

}
