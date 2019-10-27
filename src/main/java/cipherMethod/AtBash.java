package cipherMethod;

class AtBash implements ICipherMethod {
    private String data = "";
    private String digits = "0123456789";
    private String letter = "abcdefghijklmnopqrstuwvxyz";

    public AtBash(String message) {
        this.data = message;
    }

    public AtBash() {
    }

    @Override
    public void setText(String text) {
        this.data = text;
    }

    public String encode() {
        StringBuilder sb = new StringBuilder();
        for(char ch : data.toCharArray()) {
            if (Character.isLetter(ch)) {
                int index = 25 - letter.indexOf(String.valueOf(ch).toLowerCase());
                sb.append(Character.isLowerCase(ch) ? letter.charAt(index) : letter.toUpperCase().charAt(index));
            } else if (Character.isDigit(ch)) {
                sb.append(digits.charAt(9 - digits.indexOf(ch)));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public String decode() {
        return encode();
    }
}
