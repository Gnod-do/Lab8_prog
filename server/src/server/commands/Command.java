package server.commands;

import common.interaction.User;

public interface Command {
    String getName();

    String getUsage();

    String getDescription();

    boolean execute(String commandStringArgument, Object commandObjectArgument, User user);
}
