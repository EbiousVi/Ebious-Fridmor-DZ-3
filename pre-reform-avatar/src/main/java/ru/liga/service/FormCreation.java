package ru.liga.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FormCreation {

    public byte[] execute(String description) throws IOException, FontFormatException {

        System.err.println(description.contains("\n"));
        InputStream imageStream = getClass().getClassLoader().getResourceAsStream("prerev-background.jpg");
        InputStream fontStream = getClass().getClassLoader().getResourceAsStream("OldStandardTT-Regular.ttf");
        int minFontSize = 10;

        BufferedImage image = ImageIO.read(Objects.requireNonNull(imageStream));
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(fontStream));
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        Font headerFont = new Font("Old Standard TT", Font.BOLD, minFontSize);
        Font textFont = new Font("Old Standard TT", Font.PLAIN, minFontSize);

        Graphics graphics = image.getGraphics();
        graphics.setFont(headerFont);
        graphics.setColor(Color.BLACK);

        FontMetrics metrics = graphics.getFontMetrics();

        int curX = 0;

        String[] lines = description.split("\n");

        String header;
        List<String> wordList = new ArrayList<>();
        if (lines.length > 1) {
            header = lines[0];
            for (int i = 1; i < lines.length; i++) {
                wordList.addAll(Arrays.asList(lines[i].split("\\s+")));
            }
        } else {
            String[] wordArray = lines[0].split("\\s+");
            header = wordArray[0];
            wordList.addAll(Arrays.asList(wordArray).subList(1, wordArray.length));
        }

        /* ------------------------------------------------------------------------------------------------------ */

        int headerWidth = metrics.stringWidth(header);

        int headerFontSize = minFontSize;
        while (headerWidth < imageWidth - imageWidth / 4) {
            headerFontSize = headerFontSize + 1;
            headerFont = headerFont.deriveFont((float) headerFontSize);
            graphics.setFont(headerFont);
            metrics = graphics.getFontMetrics();
            headerWidth = metrics.stringWidth(header);
        }

        int headerY = headerFontSize;

        graphics.drawString(header, curX, headerY);

        /* ------------------------------------------------------------------------------------------------------ */

        graphics.setFont(textFont);

        metrics = graphics.getFontMetrics();

        int textY = headerY + metrics.getHeight() * 3 / 2;

        int textHeight = expectedTextHeight(graphics, wordList, curX, textY, imageWidth);

        int fontSize = minFontSize;
        while (textHeight < imageHeight && fontSize < headerFontSize - headerFontSize / 4) {
            fontSize++;
            textFont = textFont.deriveFont((float) fontSize);
            graphics.setFont(textFont);
            metrics = graphics.getFontMetrics();
            textY = headerY + metrics.getHeight() * 3 / 2;
            textHeight = expectedTextHeight(graphics, wordList, curX, textY, imageWidth);
        }
        fontSize--;
        textFont = textFont.deriveFont((float) fontSize);
        graphics.setFont(textFont);

        drawText(graphics, wordList, curX, textY, imageWidth);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);

        return os.toByteArray();
    }

    private void drawText(Graphics graphics, List<String> wordList, int x, int y, int imageWight) {
        FontMetrics metrics = graphics.getFontMetrics();
        int lineHeight = metrics.getHeight();
        int curX = x;
        int curY = y;
        for (String word : wordList) {
            int wordWidth = metrics.stringWidth(word + " ");
            if (curX + wordWidth >= imageWight) {
                curY += lineHeight;
                curX = x;
            }
            graphics.drawString(word, curX, curY);
            curX += wordWidth;
        }
    }

    private int expectedTextHeight(Graphics graphics, List<String> wordList, int x, int y, int imageWidth) {
        FontMetrics metrics = graphics.getFontMetrics();
        int lineHeight = metrics.getHeight();
        int curX = x;
        int curY = y;
        for (String word : wordList) {
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
