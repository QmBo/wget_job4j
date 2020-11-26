package ru.job4j;

/**
 * Spinner.
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 26.11.2020
 */
public class Spinner implements Runnable {
    private static final int DELAY = 125;
    /**
     * Animation.
     */
    private final String[] array;
    /**
     * Position.
     */
    private int pos = 0;

    /**
     * Instantiates a new Spinner.
     */
    @SuppressWarnings("unused")
    public Spinner() {
        this(new String[]{"/", "--", "\\"});
    }

    /**
     * Instantiates a new Spinner.
     *
     * @param array the array
     */
    public Spinner(final String[] array) {
        this.array = array;
    }

    /**
     * Next string.
     *
     * @return the string
     */
    public final String next() {
        if (this.array.length == this.pos) {
            this.pos = 0;
            System.out.print("                           \r");
        }
        return array[this.pos++];
    }

    /**
     * Show animation.
     */
    @SuppressWarnings("BusyWait")
    @Override
    public final void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.printf("%s\r", this.next());
                Thread.sleep(DELAY);
            }
        } catch (InterruptedException e) {
            System.out.print("                           \r");
            System.out.println("Done.");
        }
    }
}
