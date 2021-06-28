package top.lijingyuan.guava.io;

import com.google.common.base.Preconditions;

/**
 * Base64
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-21
 * @since 1.0.0
 */
public class Base64 {

    private static final String CODE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final char[] CDOE_DICT = CODE_STRING.toCharArray();

    private Base64() {

    }

    public static String encode(String plain) {
        Preconditions.checkNotNull(plain);
        StringBuilder sb = new StringBuilder();
        String binaryString = toBinary(plain);
//        System.out.println(binaryString);
        // should append
        int delta = 6 - binaryString.length() % 6;
        for (int i = 0; i < delta && delta != 6; i++) {
            binaryString += "0";
        }
        for (int i = 0; i < binaryString.length() / 6; i++) {
            int begin = i * 6;
            String encodeString = binaryString.substring(begin, begin + 6);
            char encodeChar = CDOE_DICT[Integer.valueOf(encodeString, 2)];
            sb.append(encodeChar);
        }
        if (delta != 6) {
            for (int i = 0; i < delta / 2; i++) {
                sb.append("=");
            }
        }
        return sb.toString();
    }

    private static String toBinary(final String source) {
        final StringBuilder binaryResult = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            String charBin = Integer.toBinaryString(source.charAt(i));
            // 补齐byte的8位
            int delta = 8 - charBin.length();
            for (int j = 0; j < delta; j++) {
                charBin = "0" + charBin;
            }
            binaryResult.append(charBin);
        }
        return binaryResult.toString();
    }

    public static String decode(final String encrypt) {
        StringBuilder sb = new StringBuilder();
        String temp = encrypt;
        int equalIndex = temp.indexOf("=");
        if (equalIndex > 0) {
            temp = temp.substring(0, equalIndex);
        }
//        System.out.println(temp);
        String base64Binary = toBase64Binary(temp);
        for (int i = 0; i < base64Binary.length() / 8; i++) {
            int begin = i * 8;
            String str = base64Binary.substring(begin, begin + 8);
            char c = Character.toChars(Integer.valueOf(str, 2))[0];
            sb.append(c);
        }
        return sb.toString();
    }

    public static String toBase64Binary(final String source) {
        final StringBuilder sb = new StringBuilder();
        for (int index = 0; index < source.length(); index++) {
            int i = CODE_STRING.indexOf(source.charAt(index));
            String charBin = Integer.toBinaryString(i);
            int delta = 6 - charBin.length();
            for (int j = 0; j < delta; j++) {
                charBin = "0" + charBin;
            }
            sb.append(charBin);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        // 1100001
        System.out.println(Integer.toBinaryString(97));
    }

}
