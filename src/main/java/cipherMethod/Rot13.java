package cipherMethod;

class Rot13 implements ICipherMethod {
    private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private String data;

    public Rot13(String text) {
        this.data = text;
    }

    public Rot13() {
        data = "";
    }

    @Override
    public void setText(String text) {
        this.data = text;
    }

    public String encode() {
        String code = "";

        char[] letters = data.toCharArray();
        for (char letter : letters) {
            if(!Character.isLetter(letter)) {
                code+= letter;
                continue;
            }
            boolean isLower = isLower(letter);
            Character _char = Character.toUpperCase(letter);
            int val = charIndexOf(_char);
            int newVal = (val + 13) % 26;
            String newLetter = codeLetter(newVal);
            code += valueNewLetter(isLower, newLetter);
        }

        return code;
    }

    public String decode() {
        return encode();
    }

    private static String valueNewLetter(boolean isLower, String newLetter) {
        return (isLower) ? newLetter.toLowerCase() : newLetter.toUpperCase();
    }

    private String codeLetter(int newVal) {
        return String.valueOf(ALPHABET.charAt(newVal));
    }

    private static boolean isLower(char letter) {
        return Character.isLowerCase(letter);
    }

    private int charIndexOf(Character _char) {
        return ALPHABET.indexOf(_char);
    }
}
