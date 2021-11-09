package ru.job4j.tdd;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

public class CinemaTest {
    @Ignore
    @Test
    public void whenBuyWasSuccessful() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        var session1 = new Session3D();
        cinema.add(session1);
        Calendar date = Calendar.getInstance();
        date.set(2021, 11, 25, 23, 00);
        Ticket ticket = cinema.buy(account, 1, 1, date);
        assertNotNull(ticket);
    }

    @Ignore
    @Test
    public void whenNoSessionAndBuyWasUnsuccessful() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        Calendar date = Calendar.getInstance();
        date.set(2021, 12, 10, 23, 00);
        Ticket ticket = cinema.buy(account, 1, 1, date);
        assertNull(ticket);
    }

    @Ignore
    @Test
    public void whenPlaceIsBusyAndBuyWasUnsuccessful() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        var session1 = new Session3D();
        cinema.add(session1);
        Calendar date = Calendar.getInstance();
        date.set(2021, 12, 10, 23, 00);
        Ticket ticket = cinema.buy(account, 1, 1, date);
        assertNull(ticket);
    }

    @Ignore
    @Test
    public void whenDateIsOldAndBuyWasUnsuccessful() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        var session1 = new Session3D();
        cinema.add(session1);
        Calendar date = Calendar.getInstance();
        date.set(2020, 11, 25, 23, 00);
        Ticket ticket = cinema.buy(account, 1, 1, date);
        assertNull(ticket);
    }

    @Ignore
    @Test
    public void whenSessionAddedAndFound() {
        Cinema cinema = new Cinema3D();
        var session1 = new Session3D();
        cinema.add(session1);
        assertEquals(List.of(session1),
                cinema.find(session -> true));
    }

    @Ignore
    @Test
    public void whenSessionNotFound() {
        Cinema cinema = new Cinema3D();
        assertEquals(List.of(),
                cinema.find(session -> true));
    }
}