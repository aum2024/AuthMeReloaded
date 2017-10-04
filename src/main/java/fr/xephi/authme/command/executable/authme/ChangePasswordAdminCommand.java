package fr.xephi.authme.command.executable.authme;

import fr.xephi.authme.command.ExecutableCommand;
import fr.xephi.authme.data.auth.PlayerCache;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.process.Management;
import fr.xephi.authme.security.PasswordSecurity;
import fr.xephi.authme.service.BukkitService;
import fr.xephi.authme.service.CommonService;
import fr.xephi.authme.service.ValidationService;
import fr.xephi.authme.service.ValidationService.ValidationResult;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.List;

/**
 * Admin command for changing a player's password.
 */
public class ChangePasswordAdminCommand implements ExecutableCommand {

    @Inject
    private PasswordSecurity passwordSecurity;

    @Inject
    private PlayerCache playerCache;

    @Inject
    private DataSource dataSource;

    @Inject
    private BukkitService bukkitService;

    @Inject
    private ValidationService validationService;

    @Inject
    private CommonService commonService;

    @Inject
    private Management management;

    @Override
    public void executeCommand(final CommandSender sender, List<String> arguments) {
        // Get the player and password
        final String playerName = arguments.get(0);
        final String playerPass = arguments.get(1);

        // Validate the password
        ValidationResult validationResult = validationService.validatePassword(playerPass, playerName);
        if (validationResult.hasError()) {
            commonService.send(sender, validationResult.getMessageKey(), validationResult.getArgs());
            return;
        }

        // Set the password
        management.performPasswordChangeAsAdmin(sender, playerName, playerPass);
    }
}
