package ru.liga.service;

import lombok.Cleanup;
import org.springframework.stereotype.Service;
import ru.liga.domain.Description;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Service
public class FormCreation {

    public byte[] execute(Description description) {
        try {
            @Cleanup InputStream imageStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("prerev-background.jpg");
            @Cleanup InputStream fontStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("OldStandardTT-Regular.ttf");
            int minFontSize = 10;

            BufferedImage image = ImageIO.read(Objects.requireNonNull(imageStream));
            int imageHeight = image.getHeight();
            int imageWidth = image.getWidth();

            Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(fontStream));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            Font headerFont = new Font("Old Standard TT", Font.BOLD, minFontSize);
            Font textFont = new Font("Old Standard TT", Font.PLAIN, minFontSize);

            Graphics graphics = image.getGraphics();
            graphics.setFont(headerFont);
            graphics.setColor(Color.BLACK);

            int curX = 0;

            String header = description.getTittle().trim();
            List<String> wordList = List.of(description.getBody().trim().split("\\s+"));

            int headerFontSize = getFontSizeForHeader(graphics, headerFont, header, imageWidth);
            graphics.drawString(header, curX, headerFontSize);

            graphics.setFont(textFont);
            int fontSize = getFontSizeForBody(graphics, textFont, wordList, headerFontSize, imageHeight, imageWidth);
            textFont = textFont.deriveFont((float) fontSize);
            graphics.setFont(textFont);

            FontMetrics fontMetrics = graphics.getFontMetrics();
            int textY = headerFontSize + fontMetrics.getHeight() * 3 / 2;

            drawText(graphics, wordList, curX, textY, imageWidth);

            @Cleanup ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", os);

            return os.toByteArray();
        } catch (IOException | FontFormatException e) {
            return null;
        }
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

    private int getFontSizeForBody(Graphics graphics, Font font, List<String> wordList, int headerFontSize, int imageHeight, int imageWidth) {
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Font textFont = font;
        int textY = headerFontSize + fontMetrics.getHeight() * 3 / 2;
        int textHeight = getExpectedTextHeight(graphics, wordList, textY, imageWidth);
        int fontSize = 0;
        while (textHeight < imageHeight && fontSize < headerFontSize * 3 / 4) {
            fontSize++;
            textFont = textFont.deriveFont((float) fontSize);
            graphics.setFont(textFont);
            fontMetrics = graphics.getFontMetrics();
            textY = headerFontSize + fontMetrics.getHeight() * 3 / 2;
            textHeight = getExpectedTextHeight(graphics, wordList, textY, imageWidth);
        }
        fontSize--;
        return fontSize;
    }

    private int getExpectedTextHeight(Graphics graphics, List<String> wordList, int y, int imageWidth) {
        FontMetrics metrics = graphics.getFontMetrics();
        int lineHeight = metrics.getHeight();
        int curX = 0;
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

    private int getFontSizeForHeader(Graphics graphics, Font font, String text, int imageWidth) {
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Font textFont = font;
        int textWidth = fontMetrics.stringWidth(text);
        int textFontSize = 0;
        while (textWidth < imageWidth * 3 / 4) {
            textFontSize = textFontSize + 1;
            textFont = textFont.deriveFont((float) textFontSize);
            graphics.setFont(textFont);
            fontMetrics = graphics.getFontMetrics();
            textWidth = fontMetrics.stringWidth(text);
        }
        return textFontSize;
    }
}
