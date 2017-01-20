package com.company;

import java.io.*;

/**
 * Created by josef on 2016-12-29.
 */
public class SpliceClass {

    public static void spliceFile(String filePath, int nrOfOutputFiles){

        InputStream input = null;
        try {
            input = new FileInputStream(filePath);

            OutputStream[] outputs = new OutputStream[nrOfOutputFiles];

            for(int i = 0; i < nrOfOutputFiles; i ++){
                try {
                    String tempString = filePath + ".splc_piece" + i;
                    outputs[i] = new FileOutputStream(tempString);
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR: Can not create output file \"" + filePath + ".splc_piece" + i + "\"");
                }
            }


            // Buffering
            input = new BufferedInputStream(input);
            for(int i = 0; i < nrOfOutputFiles; i++) {
                outputs[i] = new BufferedOutputStream(outputs[i]);
            }


            int currentInt;
            System.out.println("Splicing file");
            File tempFile = new File(filePath);
            long filesize = tempFile.length();

            try {
                int currentFile = 0;
                int currentByte = 0;
                while ((currentInt = input.read()) != -1){
                    outputs[currentFile].write(currentInt);
                    currentFile ++;

                    if(currentFile >= nrOfOutputFiles)
                        currentFile = 0;

                    if(currentByte % 100000 == 0){
                        System.out.printf("%.2f", (100 * ((double)currentByte / (double)filesize)));
                        System.out.println(" %");
                    }
                    currentByte ++;

                }
                input.close();
                for(OutputStream out : outputs) {
                    out.close();
                }
                System.out.println("100,0 %");

                long pieceSizes = 0;
                for(int i = 0; i < outputs.length; i ++){
                    tempFile = new File(filePath + ".splc_piece" + i);
                    pieceSizes += tempFile.length();
                }

                if(filesize != pieceSizes){
                    System.out.println("ERROR: Failed to splice files. The pieces are corrupted. \nBe sure not to open either files under the process.");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: No such file: " + filePath);
        }
    }
}
