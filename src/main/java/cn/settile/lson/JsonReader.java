package cn.settile.lson;

import cn.settile.lson.core.CharReader;
import cn.settile.lson.core.Token;
import cn.settile.lson.core.TokenType;
import cn.settile.lson.exception.JsonParseException;

import java.io.IOException;
import java.io.Reader;

/**
 * @author lzjyzq2
 * @date 2020-08-07 14:21:46
 */
public class JsonReader {

    private CharReader reader;

    public JsonReader(Reader reader){
        this.reader = new CharReader(reader);
    }

    public Token nextToken() throws IOException, JsonParseException {
        char ch;
        do {
            //先读一个字符，若为空白符（ASCII码在[0, 20H]上）则接着读，直到刚读的字符非空白符
            if (!reader.hasNext()) {
                return new Token(TokenType.END_DOCUMENT, null);
            }
            ch = reader.next();
        } while (isWhiteSpace(ch));
        switch (ch) {
            case '{':
                return new Token(TokenType.BEGIN_OBJECT, String.valueOf(ch));
            case '}':
                return new Token(TokenType.END_OBJECT, String.valueOf(ch));
            case '[':
                return new Token(TokenType.BEGIN_ARRAY, String.valueOf(ch));
            case ']':
                return new Token(TokenType.END_ARRAY, String.valueOf(ch));
            case ',':
                return new Token(TokenType.SEP_COMMA, String.valueOf(ch));
            case ':':
                return new Token(TokenType.SEP_COLON, String.valueOf(ch));
            case 'n':
                return readNull();
            case 't':
            case 'f':
                return readBoolean();
            case '"':
                return readString();
            case '-':
                return readNumber();
            default:
                if(isDigit(ch)){
                    return readNumber();
                }
        }
        throw new JsonParseException("Illegal character");
    }

    public void back(){
        reader.back();
    }

    private Token readNumber() throws IOException, JsonParseException {
        char ch = reader.peek();
        StringBuilder sb = new StringBuilder();
        if (ch == '-') {
            // 处理负数
            sb.append(ch);
            ch = reader.next();
            if (ch == '0') {
                // 处理 -0.xxxx
                sb.append(ch);
                sb.append(readFracAndExp());
            } else if (isDigitOneToNine(ch)) {
                do {
                    sb.append(ch);
                    ch = reader.next();
                } while (isDigit(ch));
                if (ch != (char) -1) {
                    reader.back();
                    sb.append(readFracAndExp());
                }
            } else {
                throw new JsonParseException("Invalid minus number");
            }
        } else if (ch == '0') {
            // 处理小数
            sb.append(ch);
            sb.append(readFracAndExp());
        } else {
            do {
                sb.append(ch);
                ch = reader.next();
            } while (isDigit(ch));
            if (ch != (char) -1) {
                reader.back();
                sb.append(readFracAndExp());
            }
        }

        return new Token(TokenType.NUMBER, sb.toString());
    }



    private String readFracAndExp() throws IOException, JsonParseException {
        StringBuilder sb = new StringBuilder();
        char ch = reader.next();
        if (ch ==  '.') {
            sb.append(ch);
            ch = reader.next();
            if (!isDigit(ch)) {
                throw new JsonParseException("Invalid frac");
            }
            do {
                sb.append(ch);
                ch = reader.next();
            } while (isDigit(ch));

            if (isExp(ch)) {
                // 处理科学计数法
                sb.append(ch);
                sb.append(readExp());
            } else {
                if (ch != (char) -1) {
                    reader.back();
                }
            }
        } else if (isExp(ch)) {
            sb.append(ch);
            sb.append(readExp());
        } else {
            reader.back();
        }

        return sb.toString();
    }

    private String readExp() throws JsonParseException, IOException {
        StringBuilder sb = new StringBuilder();
        char ch = reader.next();
        if (ch == '+' || ch =='-') {
            sb.append(ch);
            ch = reader.next();
            if (isDigit(ch)) {
                do {
                    sb.append(ch);
                    ch = reader.next();
                } while (isDigit(ch));

                if (ch != (char) -1) {    
                    // 读取结束，不用回退
                    reader.back();
                }
            } else {
                throw new JsonParseException("e or E");
            }
        } else {
            throw new JsonParseException("e or E");
        }

        return sb.toString();
    }

    private boolean isExp(char ch) {
        return ch == 'e' || ch == 'E';
    }

    private Token readString() throws IOException, JsonParseException {
        StringBuilder sb = new StringBuilder();
        while(true) {
            char ch = reader.next();

            // 处理转义字符
            if (ch == '\\') {
                if (!isEscape()) {
                    throw new JsonParseException("Invalid escape character");
                }
                sb.append('\\');
                ch = reader.peek();
                sb.append(ch);
                if (ch == 'u') {   
                    // 处理 Unicode 编码，形如 \u4e2d。且只支持 \u0000 ~ \uFFFF 范围内的编码
                    for (int i = 0; i < 4; i++) {
                        ch = reader.next();
                        if (isHex(ch)) {
                            sb.append(ch);
                        } else {
                            throw new JsonParseException("Invalid character");
                        }
                    }
                }
            } else if (ch == '"') {     
                // 碰到另一个双引号，则认为字符串解析结束，返回 Token
                return new Token(TokenType.STRING, sb.toString());
            } else if (ch == '\r' || ch == '\n') {     
                // 传入的 JSON 字符串不允许换行
                throw new JsonParseException("Invalid character");
            } else {
                sb.append(ch);
            }
        }
    }

    private boolean isHex(char ch) {
        return ((ch >= '0' && ch <= '9') || ('a' <= ch && ch <= 'f')
                || ('A' <= ch && ch <= 'F'));
    }

    private boolean isEscape() throws IOException {
        char ch = reader.next();
        return (ch == '"' || ch == '\\' || ch == 'u' || ch == 'r'
                || ch == 'n' || ch == 'b' || ch == 't' || ch == 'f' || ch == '/');
    }

    private Token readBoolean() throws IOException, JsonParseException {
        if (reader.peek() == 't') {
            if (!(reader.next() == 'r' && reader.next() == 'u' && reader.next() == 'e')) {
                throw new JsonParseException("Invalid json string");
            }

            return new Token(TokenType.BOOLEAN, "true");
        } else {
            if (!(reader.next() == 'a' && reader.next() == 'l'
                    && reader.next() == 's' && reader.next() == 'e')) {
                throw new JsonParseException("Invalid json string");
            }

            return new Token(TokenType.BOOLEAN, "false");
        }
    }

    private Token readNull() throws IOException, JsonParseException {
        if (!(reader.next() == 'u' && reader.next() == 'l' && reader.next() == 'l')) {
            throw new JsonParseException("Invalid json string");
        }
        return new Token(TokenType.NULL, "null");
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
    private boolean isDigitOneToNine(char ch) {
        return ch >= '1' && ch <= '9';
    }
    private boolean isWhiteSpace(char ch) {
        return (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n');
    }

    public void close() throws IOException {
        reader.close();
    }

    public String getBufferIndexString(){
        StringBuilder sb = new StringBuilder();
        int startIndex = 0;
        if(reader.getIndex()<10){
            startIndex = 0;
        }else {
            startIndex = reader.getIndex()-10;
        }
        char[] nowChars  = arraySub(reader.getBuffer(),startIndex,startIndex+20);
        sb.append("\n").append(new String(nowChars).replace("\n"," ")).append("\n");
        sb.append("-".repeat(Math.max(0, Math.min(startIndex, 10)))).append("^");
        return sb.toString();
    }

    public static char[] arraySub(char[] data,int start,int end){
        //新建数组C长度为start-end
        char[] chars=new char[end-start];
        int j=0;
        for(int i=start;i<end;i++){
            chars[j]=data[i];
            j++;
        }
        return chars;
    }
}
