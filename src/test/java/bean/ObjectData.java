package bean;

public class ObjectData{

    public class Data{
        String data = "data";
        int index = 36;
        Integer integer = Integer.MAX_VALUE;
        byte aByte = Byte.MIN_VALUE;
        Byte oByte = Byte.MAX_VALUE;
        short aShort = Short.MIN_VALUE;
        Short oShort = Short.MAX_VALUE;
        char aChar = 'D';
        boolean aBoolean = false;

        public void setaChar(char aChar) {
            if(aChar=='D'){
                this.aChar='C';
            }else {
                this.aChar = aChar;
            }
        }

        public char getaChar() {
            return aChar;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "data='" + data + '\'' +
                    ", index=" + index +
                    ", integer=" + integer +
                    ", aByte=" + aByte +
                    ", oByte=" + oByte +
                    ", aShort=" + aShort +
                    ", oShort=" + oShort +
                    ", aChar=" + aChar +
                    '}';
        }
    }
}
