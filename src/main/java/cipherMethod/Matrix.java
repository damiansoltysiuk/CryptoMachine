package cipherMethod;

import java.util.Random;

public class Matrix implements ICipherMethod {
    private String data;
    private String code = "";

    public Matrix() {
        data = "";
    }

    @Override
    public void setText(String text) {
        this.data = text;
    }

    public Matrix(String messege) {
        this.data = messege;
    }

    public String encode() {
        int side = (int)Math.ceil(Math.sqrt(data.length()));
        if (side < 2) return data;
        StringBuilder sb = new StringBuilder(data);
        while (sb.length() != side * side) {
            int i = new Random().nextInt(16) + 32;
            sb.append((char)i);
        }
        char[] chars = new char[sb.length()];
        for(int i = 0; i < side; i++) {
            for(int j = 0; j < side; j++) {
                char ch = sb.toString().charAt(i + side * j);
                chars[i * side + j] = ch;
            }
        }
        code = String.valueOf(chars);
        return code;
    }

    public String decode() {
//        int side = (int)Math.ceil(Math.sqrt(code.length()));
//        if (side < 2) return code;
//        StringBuilder sb = new StringBuilder(code);
//        while (sb.length() != side * side) {
//            int i = new Random().nextInt(16) + 32;
//            sb.append((char)i);
//        }
//        char[] chars = new char[sb.length()];
//        for(int i = 0; i < side; i++) {
//            for(int j = 0; j < side; j++) {
//                char ch = sb.toString().charAt(i + side * j);
//                chars[i * side + j] = ch;
//            }
//        }
//        return String.valueOf(chars).replaceAll("\\W", "");
        return encode();
    }
}
