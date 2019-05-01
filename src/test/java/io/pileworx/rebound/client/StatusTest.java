package io.pileworx.rebound.client;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class StatusTest {

    private static final String STATUS = "mystatus";
    private static final String MESSAGE = "this is my message";

    @Test
    public void canConstructAndAccessGetters() {
        Status status = new Status(
                STATUS,
                MESSAGE);

        assertThat(status.getStatus(), is(equalTo(STATUS)));
        assertThat(status.getMessage(), is(equalTo(MESSAGE)));
    }
}
