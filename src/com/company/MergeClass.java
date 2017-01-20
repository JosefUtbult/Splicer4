package com.company;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by josef on 2016-12-29.
 */
public class MergeClass {
    public static void merge(String filePath) {

        if(filePath.charAt(filePath.length() - 1) != '0'){
            System.out.println("ERROR: You must choose piece nr 0 to properly read files.");
        }
        else {
            String tempString = "";
            String cleanFilePath = "";
            for (int i = 0; i < filePath.length() - 1; i++) {
                tempString += filePath.charAt(i);

                if (i < filePath.length() - 12)
                    cleanFilePath += filePath.charAt(i);
            }
            filePath = tempString;

            ArrayList<InputStream> listInputs = new ArrayList<>();
            InputStream tempStream;
            String tempPath;
            while (true) {
                try {
                    tempPath = filePath + listInputs.size();
                    tempStream = new FileInputStream(tempPath);
                } catch (FileNotFoundException e) {
                    break;
                }
                listInputs.add(tempStream);
            }
            InputStream[] inputs = new InputStream[listInputs.size()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = listInputs.get(i);
            }

            OutputStream output = null;
            try {
                output = new FileOutputStream(cleanFilePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            // Buffered
            for(int i = 0; i < inputs.length; i++) {
                inputs[i] = new BufferedInputStream(inputs[i]);
            }
            output = new BufferedOutputStream(output);


            long filesize = 0;
            File tempFile;
            for(int i = 0; i < inputs.length; i ++){
                tempFile = new File(filePath + i);
                filesize += tempFile.length();
            }


            System.out.println("Merging files.");
            boolean isLeft;
            long currentByte = 0;
            while (true) {
                isLeft = false;
                int tempInt;
                for (int i = 0; i < inputs.length; i++) {
                    try {
                        if((tempInt = inputs[i].read()) != -1){
                            isLeft = true;
                            output.write(tempInt);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(currentByte % 100000 == 0){
                        System.out.printf("%.2f", (100 * ((double)currentByte / (double)filesize)));
                        System.out.println(" %");
                    }
                    currentByte ++;
                }
                if(!isLeft)
                    break;
            }
            System.out.println("100,00 %\nDone!");

            try {
                output.close();
                for (int i = 0; i < inputs.length; i ++)
                    inputs[i].close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            File file = null;
            long inputSize = 0;
            for(int i = 0; i < inputs.length; i ++){
                file = new File(filePath + i);
                inputSize += file.length();
            }

            file = new File(cleanFilePath);
            if(file.length() < inputSize)
                System.out.println("ERROR: Failed to merge files. The product is corrupted. \nBe sure not to open either files under the process.");

        }
    }
}
