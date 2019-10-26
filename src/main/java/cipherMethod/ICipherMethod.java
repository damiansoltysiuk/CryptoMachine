package cipherMethod;

public interface ICipherMethod {
    String encode();
    String decode();
    void setText(String text);
}
