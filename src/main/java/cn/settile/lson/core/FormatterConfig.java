package cn.settile.lson.core;

/**
 * @author lzjyzq2
 * @date 2020-08-10 10:40:20
 */
public class FormatterConfig {

    private char indentChar = ' ';
    private int indents = 0;
    private boolean leftObjectSepBreak = false;
    private boolean rightObjectSepBreak = false;
    private boolean leftArraySepBreak = false;
    private boolean rightArraySepBreak = false;
    private boolean commaBreak = false;

    public char getIndentChar() {
        return indentChar;
    }

    public void setIndentChar(char indentChar) {
        this.indentChar = indentChar;
    }

    public int getIndents() {
        return indents;
    }

    public void setIndents(int indents) {
        this.indents = indents;
    }

    public boolean isLeftObjectSepBreak() {
        return leftObjectSepBreak;
    }

    public void setLeftObjectSepBreak(boolean leftObjectSepBreak) {
        this.leftObjectSepBreak = leftObjectSepBreak;
    }

    public boolean isLeftArraySepBreak() {
        return leftArraySepBreak;
    }

    public void setLeftArraySepBreak(boolean leftArraySepBreak) {
        this.leftArraySepBreak = leftArraySepBreak;
    }

    public boolean isCommaBreak() {
        return commaBreak;
    }

    public void setCommaBreak(boolean commaBreak) {
        this.commaBreak = commaBreak;
    }

    public boolean isRightObjectSepBreak() {
        return rightObjectSepBreak;
    }

    public void setRightObjectSepBreak(boolean rightObjectSepBreak) {
        this.rightObjectSepBreak = rightObjectSepBreak;
    }

    public boolean isRightArraySepBreak() {
        return rightArraySepBreak;
    }

    public void setRightArraySepBreak(boolean rightArraySepBreak) {
        this.rightArraySepBreak = rightArraySepBreak;
    }

    public FormatterConfig() {
    }

    public FormatterConfig(char indentChar, int indents, boolean leftObjectSepBreak, boolean leftArraySepBreak, boolean commaBreak) {
        this.indentChar = indentChar;
        this.indents = indents;
        this.leftObjectSepBreak = leftObjectSepBreak;
        this.leftArraySepBreak = leftArraySepBreak;
        this.commaBreak = commaBreak;
    }

    public static class Builder{

        private FormatterConfig instance;

        public Builder(){
            instance = new FormatterConfig();
        }
        

        public Builder setIndentChar(char indentChar) {
            instance.indentChar = indentChar;
            return this;
        }
        

        public Builder setIndents(int indents) {
            instance.indents = indents;
            return this;
        }

        public Builder setLeftObjectSepBreak(boolean leftObjectSepBreak) {
            instance.leftObjectSepBreak = leftObjectSepBreak;
            return this;
        }
        

        public Builder setLeftArraySepBreak(boolean leftArraySepBreak) {
            instance.leftArraySepBreak = leftArraySepBreak;
            return this;
        }

        public Builder setCommaBreak(boolean commaBreak) {
            instance.commaBreak = commaBreak;
            return this;
        }
        

        public Builder setRightObjectSepBreak(boolean rightObjectSepBreak) {
            instance.rightObjectSepBreak = rightObjectSepBreak;
            return this;
        }

        public Builder setRightArraySepBreak(boolean rightArraySepBreak) {
            instance.rightArraySepBreak = rightArraySepBreak;
            return this;
        }
        
        public FormatterConfig build(){
            return instance;
        }
    }
}
