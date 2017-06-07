
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tanjila Mawla Tania
 */
public class ImageMatching {
    
    public static float compareImage(File fileA, File fileB) {

    float percentage = 0;
    try {
        // take buffer data from both image files //
        BufferedImage biA = ImageIO.read(fileA);
        DataBuffer dbA = biA.getData().getDataBuffer();
        int sizeA = dbA.getSize();
        BufferedImage biB = ImageIO.read(fileB);
        DataBuffer dbB = biB.getData().getDataBuffer();
        int sizeB = dbB.getSize();
        int count = 0;
        // compare data-buffer objects //
        if (sizeA == sizeB) {

            for (int i = 0; i < sizeA; i++) {

                if (dbA.getElem(i) == dbB.getElem(i)) {
                    count = count + 1;
                }

            }
            percentage = (count * 100) / sizeA;
        } else {
            System.out.println("Both the images are not of same size");
        }

    } catch (Exception e) {
        System.out.println("Failed to compare image files ...");
    }
    return percentage;
}
    
    public static void main(String args[]){
    
    File fileA , fileB;
    fileA = new File("C:\\Users\\Tanjila Mawla Tania\\Documents\\NetBeansProjects\\sqliteProject\\SQLiteProject\\src\\imageA.jpg");
    fileB = new File("C:\\Users\\Tanjila Mawla Tania\\Documents\\NetBeansProjects\\sqliteProject\\SQLiteProject\\src\\imageB.jpg");
    float per;
    per  = compareImage(fileA, fileB);
        System.out.println("percentage: " + per);
    
    }

    
}
