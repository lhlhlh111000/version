package com.ping.web.version.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Title: 文件sha1值工具类
 *
 * @author 二师兄
 * Create Time: 2019/10/16
 */
public class Sha1CodeUtil {


    /**
     * 根据文件路径获取返回sha1值
     * @param filePath 文件路径
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String sha1Code(String filePath) throws IOException, NoSuchAlgorithmException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        return sha1Code(fileInputStream);
    }

    /**
     * 根据输入流获取sha1值
     * @param is 输入流
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String sha1Code(InputStream is) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        DigestInputStream digestInputStream = new DigestInputStream(is, digest);
        byte[] bytes = new byte[1024];
        // read all file content
        while (digestInputStream.read(bytes) > 0);

        byte[] resultByteArray = digest.digest();
        return bytesToHexString(resultByteArray);
    }


    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int value = b & 0xFF;
            if (value < 16) {
                // if value less than 16, then it's hex String will be only
                // one character, so we need to append a character of '0'
                sb.append("0");
            }
            sb.append(Integer.toHexString(value).toLowerCase());
        }
        return sb.toString();
    }

    /**
     * 根据输入zip文件获取指定内容文件的sha1值
     * @param zipFile zip文件
     * @param fileName 要获取sha1值的文件名
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String readZipFileSha1Code(String zipFile, String fileName) throws IOException, NoSuchAlgorithmException  {
        String result = null;
        ZipFile zf = new ZipFile(zipFile);
        InputStream in = new BufferedInputStream(new FileInputStream(zipFile));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            System.out.println(ze.getName());
            if(ze.getName().equals(fileName)) {
                if(!ze.isDirectory()) {
                    result = sha1Code(zf.getInputStream(ze));
                }
            }
        }
        zin.closeEntry();
        return result;
    }
}
