package cipherMethod;

public class Vigenere implements ICipherMethod {
    private String key = "key";
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private String data;

    public Vigenere(String message) {
        this.data = message;
    }

    public Vigenere() {
        this.data = "";
    }

    @Override
    public void setText(String text) {
        this.data = text;
    }

    public String encode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char ch = data.charAt(i);
            if (ALPHABET.contains(String.valueOf(ch).toLowerCase())) {
                boolean lowerCase = Character.isLowerCase(ch);
                int index = (ALPHABET.indexOf(String.valueOf(ch).toLowerCase()) + ALPHABET.indexOf(key.charAt(i % key.length()))) % 26;
                sb.append(lowerCase ? ALPHABET.charAt(index) : ALPHABET.toUpperCase().charAt(index));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public String decode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char ch = data.charAt(i);
            if (ALPHABET.contains(String.valueOf(ch).toLowerCase())) {
                boolean lowerCase = Character.isLowerCase(ch);
                int index = (ALPHABET.indexOf(String.valueOf(ch).toLowerCase()) + 26 - ALPHABET.indexOf(key.charAt(i % key.length()))) % 26;
                sb.append(lowerCase ? ALPHABET.charAt(index) : ALPHABET.toUpperCase().charAt(index));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public void setKey(String key) {
        this.key = key;
    }
}
