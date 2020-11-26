package ru.job4j;

import java.net.URL;

/**
 * ArgsChecker.
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 26.11.2020
 */
public final class ArgsChecker {
    /**
     * Instantiates a new Args checker.
     */
    private ArgsChecker() {
    }

    /**
     * Check url speed.
     *
     * @param args the args
     * @return the url speed
     * @throws Exception the exception
     */
    public static UrlSpeed check(final String[] args) throws Exception {
        int speed = 0;
        if (args.length == 0) {
            throw new IllegalArgumentException("Not arguments found.");
        }
        if (args.length >= 2) {
            speed = Integer.parseInt(args[1]);
        }
        return new UrlSpeed(new URL(args[0]), speed);
    }
}
