public class YernarString {

    public static String padLeft(String inputString, int length, char c) {
        if (inputString.length() >= length) {
            return inputString;
        }

        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append(c);
        }
        sb.append(inputString);

        return sb.toString();
    }

}
