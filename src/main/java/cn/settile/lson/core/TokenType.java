package cn.settile.lson.core;

/**
 * Token类型
 * @author lzjyzq2
 * @date 2020-08-07 14:05:26
 */

public enum  TokenType {
    /** Object begin tag - { */
    BEGIN_OBJECT(1),
    /** Object end tag - } */
    END_OBJECT(2),
    /** Array begin tag - [ */
    BEGIN_ARRAY(4),
    /** Array end tag - ] */
    END_ARRAY(8),
    /** Null - null */
    NULL(16),
    /** Number */
    NUMBER(32),
    /** String */
    STRING(64),
    /** Boolean - true/false */
    BOOLEAN(128),
    /** sep - : */
    SEP_COLON(256),
    /** sep - , */
    SEP_COMMA(512),
    /** Document end tag - EOF */
    END_DOCUMENT(1024);

    /** 类型码 */
    private int code;

    /**
     * @param code 类型码
     */
    TokenType(int code) {
        this.code = code;
    }

    /** 取得类型码
     * @return 类型码
     */
    public int getCode() {
        return code;
    }
}
