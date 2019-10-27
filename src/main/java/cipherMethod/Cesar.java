package cipherMethod;

class Cesar implements ICipherMethod {
    private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private String data;

    public Cesar(String text) {
        this.data = text;
    }

    public Cesar() {
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
            int newVal = (val + 3) % 26;
            String newLetter = codeLetter(newVal);
            code += valueNewLetter(isLower, newLetter);
        }

        return code;
    }

    public String decode() {
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
            int newVal = (val + 23) % 26;
            String newLetter = codeLetter(newVal);
            code += valueNewLetter(isLower, newLetter);
        }

        return code;
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
