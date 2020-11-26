package ru.job4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Wget.
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 19.11.2020
 */
public class Wget {
    /**
     * Help message.
     */
    private static final String HELP_MESSAGE = "Please enter a link to "
            + "the downloaded file as the first argument"
            + " and the download speed limit (KB/s) as the second."
            + "\nThe second argument can be left blank.\n"
            + "For example: java -jar wget.jar "
            + "https://raw.githubusercontent.com/peterarsentev/"
            + "course_test/master/pom.xml 20";
    /**
     * Nano s.
     */
    private static final int NANO = 1_000_000;
    /**
     * Milli s.
     */
    private static final int MILLIS = 1000;
    /**
     * Buffer size.
     */
    private static final int BYTE_SIZE = 1024;
    /**
     * KBytes.
     */
    private static final float F_BYTE = 1024F;
    /**
     * Milli s. in float.
     */
    private static final float F_SEC = 1000F;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {
        new Wget().go(args);
    }

    /**
     * Main work.
     *
     * @param args the input arguments
     */
    private void go(final String[] args) {
        UrlSpeed urlSpeed;
        try {
            urlSpeed = ArgsChecker.check(args);
        } catch (NumberFormatException e) {
            System.out.printf("%s is not a number!", e.getMessage());
            System.out.println(HELP_MESSAGE);
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(HELP_MESSAGE);
            return;
        }
        long start = System.currentTimeMillis();
        float size = new Wget().download(urlSpeed) / F_BYTE;
        float totalTime = (System.currentTimeMillis() - start) / F_SEC;
        System.out.printf("Download time  = %,9.3f s.\n", totalTime);
        System.out.printf("File size      = %,9.3f KB.\n", size);
        System.out.printf("Download speed = %,9.3f KB/s.\n", size / totalTime);
    }

    /**
     * Try to download the file from url and speed limit.
     * @param urlSpeed url an speed
     * @return size of file
     */
    @SuppressWarnings("BusyWait")
    private long download(final UrlSpeed urlSpeed) {
        String file = urlSpeed.getUrl().toString();
        long lengthDownloadFile = 0;
        int argSpeed = NANO / urlSpeed.getSpeed();
        String outFileName = String.valueOf(Paths.get(urlSpeed.getUrl().getFile()).getFileName());
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(outFileName)) {
            Thread spinner = new Thread(
                    new Spinner(
                            new String[]{"Downloading", "Downloading.", "Downloading..", "Downloading..."})
            );
            byte[] dataBuffer = new byte[BYTE_SIZE];
            int bytesRead;
            long startTime = System.nanoTime();
            spinner.start();
            while ((bytesRead = in.read(dataBuffer, 0, BYTE_SIZE)) != -1) {
                long t = System.nanoTime() - startTime;
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                if (t < argSpeed) {
                    Thread.sleep((argSpeed - t) / MILLIS);
                }
                startTime = System.nanoTime();
            }
            spinner.interrupt();
            lengthDownloadFile = new File(outFileName).length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lengthDownloadFile;
    }
}
