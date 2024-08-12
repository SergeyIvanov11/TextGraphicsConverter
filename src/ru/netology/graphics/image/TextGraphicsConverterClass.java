package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class TextGraphicsConverterClass implements TextGraphicsConverter {
    TextColorSchema schema = new TextColorSchemaClass();
    private int width = 2_000_000_000;
    private int height = 2_000_000_000;
    private double maxRatio = 15.0;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));
        double imgRatioWH = Double.valueOf(img.getWidth() / img.getHeight());
        double imgRatioHW = Double.valueOf(img.getHeight() / img.getWidth());
        if (maxRatio < imgRatioWH) {
            new BadImageSizeException(maxRatio, imgRatioWH);
            System.out.println("Слишком широкое изображение для конвертации!");
        }
        if (maxRatio < imgRatioHW) {
            new BadImageSizeException(maxRatio, imgRatioHW);
            System.out.println("Слишком длинное изображение для конвертации!");
        }

        int newWidth = img.getWidth();
        int newHeight = img.getHeight();

        if (height <= img.getHeight()) {
            newHeight = height;
            newWidth = img.getWidth() * height / img.getHeight();
        }

        if (width < newWidth) {
            newHeight = newHeight * width / newWidth;
            newWidth = width;
        }
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();

        StringBuilder convertedImage = new StringBuilder();

        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                convertedImage.append(c);
                convertedImage.append(c);
            }
            convertedImage.append("\n");
        }
        return convertedImage.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
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
