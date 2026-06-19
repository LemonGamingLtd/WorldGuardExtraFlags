package net.goldtreeservers.worldguardextraflags.wg.handlers;

import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldguard.LocalPlayer;
import net.goldtreeservers.worldguardextraflags.wg.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandDispatchUtil {
	private CommandDispatchUtil() {
	}

	static void dispatchForPlayer(LocalPlayer localPlayer, String command, boolean asConsole) {
		if (!(localPlayer instanceof BukkitPlayer)) {
			return;
		}

		Player player = ((BukkitPlayer) localPlayer).getPlayer();
		String parsedCommand = parseCommand(command, localPlayer.getName());
		WorldGuardUtils.getScheduler().getScheduler().runAtEntity(player, task -> {
			CommandSender sender = asConsole ? Bukkit.getServer().getConsoleSender() : player;
			Bukkit.getServer().dispatchCommand(sender, parsedCommand);
		});
	}

	private static String parseCommand(String command, String playerName) {
		String parsedCommand = command.startsWith("/") ? command.substring(1) : command;
		return parsedCommand.replace("%username%", playerName);
	}
}
