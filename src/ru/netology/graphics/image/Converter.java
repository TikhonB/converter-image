package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {
    private int maxHeight;
    private int maxWidth;
    private int width;
    private int height;
    private double maxRatio;
    private TextColorSchema schema;
    private double ratio;


    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        int newWidth = img.getWidth();
        int newHeight = img.getHeight();
        ratio = img.getWidth() / img.getHeight();

        if (maxHeight != 0 && maxWidth != 0) {
            double temp1 = img.getWidth() / (double) maxHeight;
            double temp2 = img.getHeight() / (double) maxWidth;
            if (temp1 >= temp2) {
                newWidth = img.getWidth() / (int) Math.ceil(temp1);
                newHeight = img.getHeight() / (int) Math.ceil(temp1);
            } else {
                newWidth = img.getWidth() / (int) Math.ceil(temp2);
                newHeight = img.getHeight() / (int) Math.ceil(temp2);
            }
        }

        if ((newWidth != 0 || newHeight != 0) && (newWidth < maxWidth || newHeight < maxHeight)) {
            newHeight = maxHeight;
            newWidth = maxWidth;
        }
        if (ratio > maxRatio) {
            throw new BadImageSizeException(ratio, maxRatio);
        }
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();
        char[][] array = new char[newWidth][newHeight];
        for (int w = 0; w < newHeight; w++) {
            for (int h = 0; h < newWidth; h++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                array[h][w] = c;
            }
        }
        StringBuilder str = new StringBuilder();
        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                str.append(array[h][w]);
                str.append(array[h][w]);
            }
            str.append("\n");
        }
        return str.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;

    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;

    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
