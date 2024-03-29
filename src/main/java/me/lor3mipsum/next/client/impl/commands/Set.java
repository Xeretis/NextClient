package me.lor3mipsum.next.client.impl.commands;

import me.lor3mipsum.next.Next;
import me.lor3mipsum.next.client.command.Command;
import me.lor3mipsum.next.client.command.CommandException;
import me.lor3mipsum.next.client.impl.settings.*;
import me.lor3mipsum.next.client.module.Module;
import me.lor3mipsum.next.client.setting.Setting;
import me.lor3mipsum.next.client.setting.SettingManager;
import me.lor3mipsum.next.client.utils.ChatUtils;
import me.lor3mipsum.next.client.utils.Utils;
import net.minecraft.client.util.InputUtil;

import java.util.Locale;

public class Set extends Command {
    public Set() {
        super("set", "Allows you to set a specific setting in a module to a certain value");
    }

    @Override
    public void run(String alias, String[] args) {
        if (args.length < 3)
            throw new CommandException("Usage: " + Next.prefix + alias + " <module> <setting> <value>");

        Module mod = Next.INSTANCE.moduleManager.getModule(args[0], false);

        if (mod == null) throw new CommandException("The module '" + args[0] + "' doesn't exist");

        Setting toSet = SettingManager.get(mod.getName(), args[1]);

        if (toSet == null) throw new CommandException("The module '" + args[0] + "' does not have any setting called '" + args[1] + "'");

        if (toSet instanceof KeybindSetting) {
            int key = Utils.getKeyFromName(args[2]);
            if (key == -2)
                throw new CommandException("Unknown key: " + args[2]);
            ((KeybindSetting) toSet).setKey(key);
        } else if (toSet instanceof NumberSetting) {
            double value;
            try {
                value = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                throw new CommandException(args[0] + " is not a valid number setting");
            }
            ((NumberSetting) toSet).setNumber(value);
        } else if (toSet instanceof BooleanSetting) {
            if (!(args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false") || args[2].equalsIgnoreCase("toggle")))
                throw new CommandException(args[0] + " is not a valid boolean setting");
            if (args[2].equalsIgnoreCase("toggle")) {
                ((BooleanSetting) toSet).setEnabled(!((BooleanSetting) toSet).isOn());
                args[2] = Boolean.toString(((BooleanSetting) toSet).isOn());
            } else
                ((BooleanSetting) toSet).setEnabled(Boolean.parseBoolean(args[2]));
        } else if (toSet instanceof ModeSetting) {
            String value = args[2];
            if(!((ModeSetting) toSet).modes.contains(value))
                throw new CommandException(args[2] + " is not a valid mode setting");
            ((ModeSetting) toSet).setMode(value);
        } else if (toSet instanceof ColorSetting) {
            long value;
            try {
                value = Long.parseLong(args[2]);
            } catch (NumberFormatException e) {
                throw new CommandException(args[0] + " is not a valid color setting");
            }
            ((ColorSetting) toSet).fromInteger(value);
        }

        ChatUtils.info("(highlight)%s(default) of (highlight)%s(default) has been set to (highlight)%s (default)", toSet.getName(), mod.getName(), args[2]);
    }
}
