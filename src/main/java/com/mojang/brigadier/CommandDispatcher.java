package com.mojang.brigadier;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.exceptions.CommandException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;

public class CommandDispatcher<T> {
    public static final SimpleCommandExceptionType ERROR_UNKNOWN_COMMAND = new SimpleCommandExceptionType("unknown_command", "Unknown command");
    public static final String ARGUMENT_SEPARATOR = " ";

    private final RootCommandNode root = new RootCommandNode();

    public void register(LiteralArgumentBuilder command) {
        root.addChild(command.build());
    }

    public void execute(String command, T source) throws CommandException {
        CommandContext<T> context = parseNodes(root, command, new CommandContextBuilder<T>(source));
        context.getCommand().run(context);
    }

    protected CommandContext<T> parseNodes(CommandNode node, String command, CommandContextBuilder<T> contextBuilder) throws CommandException {
        CommandException exception = null;

        for (CommandNode child : node.getChildren()) {
            try {
                CommandContextBuilder<T> context = contextBuilder.copy();
                String remaining = child.parse(command, context);
                if (child.getCommand() != null) {
                    context.withCommand(child.getCommand());
                }
                return parseNodes(child, remaining, context);
            } catch (CommandException ex) {
                exception = ex;
            }
        }

        if (exception != null) {
            throw exception;
        }
        if (command.length() > 0) {
            throw ERROR_UNKNOWN_COMMAND.create();
        }

        return contextBuilder.build();
    }
}