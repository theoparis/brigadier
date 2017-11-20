package com.mojang.brigadier.arguments;

import com.google.common.collect.ImmutableMap;
import com.google.common.testing.EqualsTester;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class DoubleArgumentTypeTest {
    private DoubleArgumentType type;
    @Mock
    private CommandContextBuilder<Object> context;

    @Before
    public void setUp() throws Exception {
        type = doubleArg(-100, 100);
    }

    @Test
    public void parse() throws Exception {
        final StringReader reader = new StringReader("15");
        assertThat(doubleArg().parse(reader, context), is(15.0));
        assertThat(reader.canRead(), is(false));
    }

    @Test
    public void parse_tooSmall() throws Exception {
        final StringReader reader = new StringReader("-5");
        try {
            doubleArg(0, 100).parse(reader, context);
            fail();
        } catch (final CommandSyntaxException ex) {
            assertThat(ex.getType(), is(DoubleArgumentType.ERROR_TOO_SMALL));
            assertThat(ex.getData(), equalTo(ImmutableMap.<String, Object>of("found", "-5.0", "minimum", "0.0")));
            assertThat(ex.getCursor(), is(0));
        }
    }

    @Test
    public void parse_tooBig() throws Exception {
        final StringReader reader = new StringReader("5");
        try {
            doubleArg(-100, 0).parse(reader, context);
            fail();
        } catch (final CommandSyntaxException ex) {
            assertThat(ex.getType(), is(DoubleArgumentType.ERROR_TOO_BIG));
            assertThat(ex.getData(), equalTo(ImmutableMap.<String, Object>of("found", "5.0", "maximum", "0.0")));
            assertThat(ex.getCursor(), is(0));
        }
    }

    @Test
    public void testEquals() throws Exception {
        new EqualsTester()
            .addEqualityGroup(doubleArg(), doubleArg())
            .addEqualityGroup(doubleArg(-100, 100), doubleArg(-100, 100))
            .addEqualityGroup(doubleArg(-100, 50), doubleArg(-100, 50))
            .addEqualityGroup(doubleArg(-50, 100), doubleArg(-50, 100))
            .testEquals();
    }

    @Test
    public void testToString() throws Exception {
        assertThat(doubleArg(), hasToString("double()"));
        assertThat(doubleArg(-100), hasToString("double(-100.0)"));
        assertThat(doubleArg(-100, 100), hasToString("double(-100.0, 100.0)"));
        assertThat(doubleArg(Integer.MIN_VALUE, 100), hasToString("double(-2.147483648E9, 100.0)"));
    }
}