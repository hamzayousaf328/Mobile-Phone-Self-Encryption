package com.example.yawarkhan.mobileselfencryption;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Yawar Khan on 1/14/2018.
 */
public class FileEncrypt {

    public static final int MAX_READ_BUFFER_SIZE = 2048;

    JTrivium cipher;
    DataInputStream reader;
    DataOutputStream  writer;

    public FileEncrypt(String inputFilePath, String outputFilePath, JTrivium cipher) throws FileNotFoundException {
        this.cipher = cipher;

        reader = new DataInputStream(new FileInputStream(new File(inputFilePath)));
        writer = new DataOutputStream(new FileOutputStream(new File(outputFilePath)));
    }

    public void encrypt() throws IOException {
        int readBytes = 0;

        byte[] buffer = new byte[FileEncrypt.MAX_READ_BUFFER_SIZE];

        do {
            readBytes = reader.read(buffer, 0, FileEncrypt.MAX_READ_BUFFER_SIZE);

            for (int i = 0; i < readBytes; i++) {
                buffer[i] ^= cipher.getKeyByte();

            }

            if (readBytes > 0) {
                writer.write(buffer, 0, readBytes);
                writer.flush();
            }

        } while (readBytes > 0);
    }

    public void close() throws IOException{
        reader.close();
        writer.close();
    }
}
