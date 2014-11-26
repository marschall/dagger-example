package com.github.marschall.dagger.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

class ImageDrawer {
  
  static void drawImage(OutputStream output) throws IOException {
    int width = 320;
    int height = 320;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = image.createGraphics();
    g2d.setColor(new Color(247, 247, 255));
    g2d.fillRect(0, 0, width, height);
    
    String text = "Dagger";
    Font lucida = new Font("Lucida Sans", Font.PLAIN, 16);
    g2d.setColor(new Color(0, 0, 64));
    g2d.setFont(lucida);
    FontMetrics fontMetrics = g2d.getFontMetrics(lucida);
    int stringWidth = fontMetrics.stringWidth(text);
    int fontHeight = fontMetrics.getHeight();
    g2d.drawString(text, (width - stringWidth) / 2, (height + fontHeight) / 2);
    
    ImageIO.write(image, "PNG", output);
  }
  
  static String getFontFamilyNames() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String []fontFamilies = ge.getAvailableFontFamilyNames();
    return String.join(", ", fontFamilies);
  }

}
