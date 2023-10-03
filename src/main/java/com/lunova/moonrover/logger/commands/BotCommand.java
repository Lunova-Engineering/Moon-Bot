package com.lunova.moonrover.logger.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.List;

public abstract class BotCommand {

    private final String identifier;

    public BotCommand(String identifier) {
        this.identifier = identifier;
    }
    public String getIdentifier() {
        return identifier;
    }

    public static void deregister(RestAction<List<Command>> commandList, BotCommand botCommand) {
        commandList.queue(commands -> {
            commands.stream()
                    .filter(command -> command.getName().equalsIgnoreCase(botCommand.getIdentifier()))
                    .findAny()
                    .ifPresentOrElse(
                            commandToDelete -> commandToDelete.delete().queue(),
                            () -> {
                                throw new IllegalArgumentException("Command not found for identifier: " + botCommand.getIdentifier());
                            }
                    );
        }, throwable -> {
            System.err.println("Error deregistering command: " + throwable.getMessage());
        });
    }


    public abstract void register(CommandListUpdateAction commandList);
    public abstract void execute(SlashCommandInteractionEvent event);
}
