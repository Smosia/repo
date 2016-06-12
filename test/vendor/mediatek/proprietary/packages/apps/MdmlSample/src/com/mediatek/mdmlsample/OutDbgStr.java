package com.mediatek.mdmlsample;

public class OutDbgStr {
    static private java.io.FileWriter fw;
    static private java.io.BufferedWriter bw;

    public OutDbgStr(String fileName) {
        try {
            fw = new java.io.FileWriter(fileName);
            bw = new java.io.BufferedWriter(fw);
        } catch (Exception e) {
            // Do nothing
        }
    }

    static public void write(String str) {
        try {
            bw.write(str);
            bw.flush();
        } catch (Exception e) {
            // Do nothing
        }
    }

    static public void writeLine(String str) {
        try {
            bw.write(str);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            // Do nothing
        }
    }

    static public void newLine() {
        try {
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            // Do nothing
        }
    }

    public void close() {
        try {
            fw.close();
        } catch (Exception e) {
            // Do nothing
        }
    }
}