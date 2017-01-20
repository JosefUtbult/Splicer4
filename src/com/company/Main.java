package com.company;

public class Main {

    public static void main(String[] args) {

        if(args.length < 1){
            System.out.println("This is a file-splicing program. for help, write -h.");
        }
        else if(args[0].equals("-h")){
            System.out.println("List of commands:\n" +
                    "-s     Splice a file. Requires first a number of resulting \n" +
                    "       pieces and later a file-path to the targeted file.\n" +
                    "       For example \"Splicer -s [nr] [path]\"\n" +
                    "\n" +
                    "-m     Merges pieces of a file. Requires the path of the \n" +
                    "       first piece, dvs nr: 0. The pieces must have the same \n" +
                    "       names followed by its number in the series.\n" +
                    "\n" +
                    "-h     Shows help screen.");
        }
        else if(args[0].equals("-s")){
            if(args.length >= 3){
                SpliceClass.spliceFile(args[2], Integer.parseInt(args[1]));
            }
            else
                System.out.println("To few arguments. Requires three.");
        }
        else if(args[0].equals("-m")){
            if(args.length >= 2)
                MergeClass.merge(args[1]);
            else
                System.out.println("To few arguments. Requires twos.");
        }
        else{
            System.out.println("There is no such command. Use \"-h\" to get a list of available commands.");
        }
    }
}
