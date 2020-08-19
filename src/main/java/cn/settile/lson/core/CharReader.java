package cn.settile.lson.core;

import java.io.IOException;
import java.io.Reader;

/**
 * @author lzjyzq2
 * @date 2020-08-07 14:26:12
 */
public class CharReader {

    private static final int BUFFER_SIZE = 1024;
    private Reader reader;
    private char[] buffer;
    private int index;
    private int size;

    public CharReader(Reader reader) {
        this.reader = reader;
        buffer = new char[BUFFER_SIZE];
    }

    /**
     * 返回 pos 下标处的字符，并返回
     * @return char
     */
    public char peek() {
        if (index - 1 >= size) {
            return (char) -1;
        }

        return buffer[Math.max(0, index - 1)];
    }

    /**
     * 返回 pos 下标处的字符，并将 pos + 1，最后返回字符
     * @return 当前下标 index ,若空则返回{@code -1}
     * @throw IOException
     */
    public char next() throws IOException {
        if (!hasNext()) {
            return (char) -1;
        }
        return buffer[index++];
    }

    /**
     * 下标回退
     */
    public void back() {
        index = Math.max(0, --index);
    }

    /**
     * 判断流是否结束
     */
    public boolean hasNext() throws IOException {
        if (index < size) {
            return true;
        }

        fillBuffer();
        return index < size;
    }

    /**
     * 填充buffer数组
     * @throw IOException
     */
    private void fillBuffer() throws IOException {
        int n = reader.read(buffer);
        if (n == -1) {
            return;
        }
        index = 0;
        size = n;
    }

    public void close() throws IOException {
        reader.close();
    }

    public char[] getBuffer() {
        return buffer;
    }

    public int getIndex() {
        return index;
    }
}
