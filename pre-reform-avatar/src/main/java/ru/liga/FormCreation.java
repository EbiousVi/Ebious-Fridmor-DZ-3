package ru.liga;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class FormCreation {
//    private static final InputStream IMAGE_STREAM = App.class.getResourceAsStream("prerev-background.jpg");
//    private static final InputStream FONT_STREAM = App.class.getResourceAsStream("OldStandardTT-Regular.ttf");
//
//    private static final int MIN_FONT_SIZE = 10;

    public byte[] execute(String description) throws IOException, FontFormatException {
        InputStream IMAGE_STREAM = getClass().getClassLoader().getResourceAsStream("prerev-background.jpg");
        InputStream FONT_STREAM = getClass().getClassLoader().getResourceAsStream("OldStandardTT-Regular.ttf");
        int MIN_FONT_SIZE = 10;

        BufferedImage image = ImageIO.read(Objects.requireNonNull(IMAGE_STREAM));
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FONT_STREAM));
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        Font headerFont = new Font("Old Standard TT", Font.BOLD, MIN_FONT_SIZE);
        Font textFont = new Font("Old Standard TT", Font.PLAIN, MIN_FONT_SIZE);

        Graphics graphics = image.getGraphics();
        graphics.setFont(headerFont);
        graphics.setColor(Color.BLACK);

        FontMetrics metrics = graphics.getFontMetrics();

        int curX = 0;
        int curY = metrics.getHeight();

        String[] words = description.split("\\s+");

        /* ------------------------------------------------------------------------------------------------------ */

        String header = words[0].toUpperCase();
        int headerWidth = metrics.stringWidth(header);
        int headerHeight = metrics.getHeight();

        float headerFontSize = MIN_FONT_SIZE;
        while (headerWidth < imageWidth / 2) {
            headerFontSize = headerFontSize + 1;
            headerFont = headerFont.deriveFont(headerFontSize);
            graphics.setFont(headerFont);
            metrics = graphics.getFontMetrics();
            headerWidth = metrics.stringWidth(header);
        }

        int headerY = metrics.getHeight();

        graphics.drawString(header, curX, headerY);

        /* ------------------------------------------------------------------------------------------------------ */

        String[] text = new String[words.length - 1];
        System.arraycopy(words, 1, text, 0, words.length - 1);

        graphics.setFont(textFont);

        metrics = graphics.getFontMetrics();

        int textY = headerY + metrics.getHeight();

        int textHeight = expectedTextHeight(graphics, text, curX, textY, imageWidth);

        int fontSize = MIN_FONT_SIZE;
        while (textHeight < imageHeight - headerY) {
            fontSize++;
            textFont = textFont.deriveFont((float) fontSize);
            graphics.setFont(textFont);
            metrics = graphics.getFontMetrics();
            textY = headerY + metrics.getHeight();
            textHeight = expectedTextHeight(graphics, text, curX, textY, imageWidth);
        }
        fontSize--;
        textFont = textFont.deriveFont((float) fontSize);
        graphics.setFont(textFont);

        drawText(graphics, text, curX, textY, imageWidth);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);

        return os.toByteArray();

//        ImageIO.write(image, "png", new File("output_image.png"));
    }

    private void drawText(Graphics graphics, String[] words, int x, int y, int imageWight) {
        FontMetrics metrics = graphics.getFontMetrics();
        int lineHeight = metrics.getHeight();
        int curX = x;
        int curY = y;
        for (String word : words) {
            int wordWidth = metrics.stringWidth(word + " ");
            if (curX + wordWidth >= imageWight) {
                curY += lineHeight;
                curX = x;
            }
            graphics.drawString(word, curX, curY);
            curX += wordWidth;
        }
    }

    private int expectedTextHeight(Graphics graphics, String[] words, int x, int y, int imageWidth) {
        FontMetrics metrics = graphics.getFontMetrics();
        int lineHeight = metrics.getHeight();
        int curX = x;
        int curY = y;
        for (String word : words) {
            int wordWidth = metrics.stringWidth(word + " ");
            if (curX + wordWidth >= imageWidth) {
                curY += lineHeight;
                curX = 0;
            }
            curX += wordWidth;
        }
        return curY;
    }
}
