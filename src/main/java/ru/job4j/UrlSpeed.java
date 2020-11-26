package ru.job4j;

import java.net.URL;

/**
 * UrlSpeed.
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 19.11.2020
 */
public class UrlSpeed {
    /**
     * URL.
     */
    private final URL url;
    /**
     * Speed.
     */
    private final int speed;

    /**
     * Instantiates a new Url speed.
     *
     * @param url   the url
     * @param speed the speed
     */
    public UrlSpeed(final URL url, final int speed) {
        this.url = url;
        this.speed = speed;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public final URL getUrl() {
        return this.url;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public final int getSpeed() {
        return this.speed;
    }
}
