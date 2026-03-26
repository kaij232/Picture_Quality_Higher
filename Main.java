package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

//        Get Color as String Array
        Scanner scan = new Scanner(System.in);
//        String[] colorRGB = scan.nextLine().split(" ");


//        int a1 = scan.nextInt();
//        int a2 = scan.nextInt();
//        String a3 = scan.next();
//        System.out.println(a3 + (a1+a2));




//        Get Start Image
        BufferedImage st_im = ImageIO.read(new File("D:\\DevelopingPrograms\\IntelijIDEA\\IntelljWorkspace\\highter_image_quality\\src\\main\\resources\\test.bmp"));


//        Show Start Image
        showImage(st_im);


//      Create new Image
        BufferedImage im = new BufferedImage(st_im.getWidth()*2-1, st_im.getHeight()*2-1, BufferedImage.TYPE_INT_RGB);
//        int[][][] testArray = new int[im.getWidth()][im.getHeight()][3];


//        set Zero-step Pixels
        for(int x = 0; x < st_im.getWidth(); x++){
            for(int y = 0; y < st_im.getHeight(); y++){
                im.setRGB(x*2, y*2, st_im.getRGB(x,y));
//                testArray[x*2][y*2] = getRGB3Ints(st_im.getRGB(x,y));
            }
        }


        int[] centerPixelIntsColor;
        Color c1;
//        Set One-step Pixels
            for(int x = 1; x < im.getWidth(); x+=2) {
//            Top of Image
                centerPixelIntsColor = getCenterPixelOneStepInLineRGB(x, 0, im);
                c1 = new Color(centerPixelIntsColor[0], centerPixelIntsColor[1], centerPixelIntsColor[2]);
                im.setRGB(x, 0, c1.getRGB());
//                testArray[x][0] = centerPixelIntsColor;

//            Bottom of Image
                centerPixelIntsColor = getCenterPixelOneStepInLineRGB(x, im.getHeight() - 1, im);
                c1 = new Color(centerPixelIntsColor[0], centerPixelIntsColor[1], centerPixelIntsColor[2]);
                im.setRGB(x, im.getHeight() - 1, c1.getRGB());
//                testArray[x][im.getHeight() - 1] = centerPixelIntsColor;

            }
            for(int y = 1; y < im.getHeight(); y+=2){
//            Left of Image
            centerPixelIntsColor = getCenterPixelOneStepInColomnRGB(0, y, im);
            c1 = new Color(centerPixelIntsColor[0], centerPixelIntsColor[1], centerPixelIntsColor[2]);
            im.setRGB(0,y, c1.getRGB());
//            testArray[0][y] = centerPixelIntsColor;

//            Right of Image
            centerPixelIntsColor = getCenterPixelOneStepInColomnRGB(im.getWidth()-1, y, im);
            c1 = new Color(centerPixelIntsColor[0], centerPixelIntsColor[1], centerPixelIntsColor[2]);
            im.setRGB(im.getWidth()-1,y, c1.getRGB());
//            testArray[im.getWidth()-1][y] = centerPixelIntsColor;


        }

//        set Two-step Pixels
        for(int y = 1; y < im.getHeight()-1; y++){
            if(y%2==0){
                for(int x = 1; x < im.getWidth()-1; x+=2){
                    centerPixelIntsColor = getCenterPixelOneStepInLineRGB(x,y, im);
                    c1 = new Color(centerPixelIntsColor[0], centerPixelIntsColor[1], centerPixelIntsColor[2]);
                    im.setRGB(x,y, c1.getRGB());
//                    testArray[x][y] = centerPixelIntsColor;
                }
            }else{
                for(int x = 2; x < im.getWidth()-1; x+=2){
                    centerPixelIntsColor = getCenterPixelOneStepInColomnRGB(x,y, im);
                    c1 = new Color(centerPixelIntsColor[0], centerPixelIntsColor[1], centerPixelIntsColor[2]);
                    im.setRGB(x,y, c1.getRGB());
//                    testArray[x][y] = centerPixelIntsColor;
                }
            }
        }

//        Set Three-step pixels
        for(int y = 1; y < im.getHeight()-1; y+=2){
            for(int x = 1; x < im.getWidth()-1; x+=2){
                centerPixelIntsColor = getCenterPixelThreeStepRGB(x,y, im);
                c1 = new Color(centerPixelIntsColor[0], centerPixelIntsColor[1], centerPixelIntsColor[2]);
                im.setRGB(x,y, c1.getRGB());
//                testArray[x][y] = centerPixelIntsColor;
            }
        }

//        Show Final Image
        showImage(im);
//        for(int line = 0; line < testArray.length; line++){
//            System.out.println("Line " + (line) + " :    " + Arrays.deepToString(testArray[line]) + "\n");
//        }

//        Save as File
        System.out.println("0 - Stop Programm\n1 - Save Foto as name ...");
        int Flag = -1;
        Flag = scan.nextInt();
        while(Flag != -2){
            if(Flag ==0) {
                System.exit(1);
            }else if(Flag == 1){
            String Name = scan.next();
            ImageIO.write(im, "BMP", new File("D:\\DevelopingPrograms\\IntelijIDEA\\IntelljWorkspace\\highter_image_quality\\src\\main\\resources\\" + Name + ".bmp"));
            }
            Flag = scan.nextInt();
        }

    }


//    Get from one int of color three ints of R G abd B
private static int[] getRGB3Ints(int c){
        return new int[]{(c >> 16) & 0xFF, (c >> 8) & 0xFF, c & 0xFF};
}


private static int[] getCenterPixelOneStepInLineRGB(int x, int y, BufferedImage im){
    int[] left = getRGB3Ints(im.getRGB(x-1,y));
    int[] right = getRGB3Ints(im.getRGB(x+1,y));
        return new int[]{Math.round((float) (left[0] + right[0]) /2), Math.round((float) (left[1] + right[1]) /2), Math.round((float) (left[2] + right[2]) /2)};
}

private static int[] getCenterPixelOneStepInColomnRGB(int x, int y, BufferedImage im){
        int[] Top = getRGB3Ints(im.getRGB(x,y-1));
        int[] Bottom = getRGB3Ints(im.getRGB(x,y+1));
        return new int[]{Math.round((float) (Top[0] + Bottom[0]) /2), Math.round((float) (Top[1] + Bottom[1]) /2), Math.round((float) (Top[2] + Bottom[2]) /2)};
    }

    private static int[] getCenterPixelThreeStepRGB(int x, int y, BufferedImage im){
        int[] Top = getRGB3Ints(im.getRGB(x,y-1));
        int[] Bottom = getRGB3Ints(im.getRGB(x,y+1));
        int[] left = getRGB3Ints(im.getRGB(x-1,y));
        int[] right = getRGB3Ints(im.getRGB(x+1,y));
        return new int[]{Math.round((float) (Top[0] + Bottom[0] + left[0] + right[0]) /4), Math.round((float) (Top[1] + Bottom[1] + left[1] + right[1]) /4), Math.round((float) (Top[2] + Bottom[2] + left[2] + right[2]) /4)};
    }


    private static void showImage(BufferedImage im){
//        create JFrame
        JFrame fr = new JFrame();
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setSize(im.getWidth(), im.getHeight());

//        Show Image
        JPanel jp = new JPanel() {
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(im, 0, 0, null);
            }
        };
        fr.add(jp);
        fr.setVisible(true);
    }
}