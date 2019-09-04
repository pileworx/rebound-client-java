package io.pileworx.rebound.client.definition;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HeaderTest {

    private static final String NAME = "Accept";
    private static final String VALUE = "application/json";

    @Test
    public void canCreateTenHeaders() {
        var headers = Header.of(
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE);

        assertThat(headers.size(), is(equalTo(10)));
    }

    @Test
    public void canCreateNineHeaders() {
        var headers = Header.of(
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE);

        assertThat(headers.size(), is(equalTo(9)));
    }

    @Test
    public void canCreateEightHeaders() {
        var headers = Header.of(
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE);

        assertThat(headers.size(), is(equalTo(8)));
    }

    @Test
    public void canCreateSevenHeaders() {
        var headers = Header.of(
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE);

        assertThat(headers.size(), is(equalTo(7)));
    }

    @Test
    public void canCreateSixHeaders() {
        var headers = Header.of(
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE);

        assertThat(headers.size(), is(equalTo(6)));
    }

    @Test
    public void canCreateFiveHeaders() {
        var headers = Header.of(
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE);

        assertThat(headers.size(), is(equalTo(5)));
    }

    @Test
    public void canCreateFourHeaders() {
        var headers = Header.of(
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE);

        assertThat(headers.size(), is(equalTo(4)));
    }

    @Test
    public void canCreateThreeHeaders() {
        var headers = Header.of(
                NAME, VALUE,
                NAME, VALUE,
                NAME, VALUE);

        assertThat(headers.size(), is(equalTo(3)));
    }

    @Test
    public void canCreateTwoHeaders() {
        var headers = Header.of(
                NAME, VALUE,
                NAME, VALUE);

        assertThat(headers.size(), is(equalTo(2)));
    }

    @Test
    public void canCreateOneHeaders() {
        var headers = Header.of(NAME, VALUE);

        assertThat(headers.size(), is(equalTo(1)));
    }
}
