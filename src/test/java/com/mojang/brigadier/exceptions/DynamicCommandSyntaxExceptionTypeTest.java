// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license.

package com.mojang.brigadier.exceptions;

import static org.hamcrest.Matchers.assertThat;
import static org.hamcrest.Matchers.is;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class DynamicCommandSyntaxExceptionTypeTest {
	private DynamicCommandExceptionType type;

	@BeforeEach
	public void setUp() throws Exception {
		type = new DynamicCommandExceptionType(name -> new LiteralMessage("Hello, " + name + "!"));
	}

	@Test
	public void createWithContext() throws Exception {
		final StringReader reader = new StringReader("Foo bar");
		reader.setCursor(5);
		final CommandSyntaxException exception = type.createWithContext(reader, "World");
		assertThat(exception.getType(), is(type));
		assertThat(exception.getInput(), is("Foo bar"));
		assertThat(exception.getCursor(), is(5));
	}
}
