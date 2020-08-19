package cn.settile.lson.core;

/**
 * @author lzjyzq2
 * @date 2020-08-07 14:19:24
 */
public class Token {

    private TokenType tokenType;

    private String value;

    public Token(TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    /** 获取当前Token的TokenType
     * @return TokenType
     */
    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", value='" + value + '\'' +
                '}';
    }
}
