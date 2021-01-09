package bgu.spl.net.api;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageEncoderDecoderImpl<T> implements MessageEncoderDecoder<Message> {
    private byte[] bytes = new byte[1 << 16];
    private int len = 0;
    private int numOfZeroBytes;


    public Command decodeNextByte(byte nextByte) {

        if (messageDone(nextByte))
            return popCommand();
        pushByte(nextByte);
        return null;

    }


    public byte[] encode(Message m) {
        Response response = (Response) m;
        short op = response.opcode;
        if (response.isAck) {
            byte[] infoBytes;
            if (response.info != null) { //there is info, add info and \0
                infoBytes = ("\n" + response.info + "\0").getBytes(StandardCharsets.UTF_8); // ============SECOND \N
            } else //no info, only \n needed
            {
                infoBytes = "\0".getBytes(StandardCharsets.UTF_8);
            }

            byte[] header = buildAnswerHeader(12,op);
            return mergeBytes(header, infoBytes);
        } else //send ERR+opcode byteArray
             return buildAnswerHeader(13, op);
        }


    private byte[] buildAnswerHeader(int ack, int op){
        byte[] ack_op = new byte[4];
        ack_op[0] = (byte) ((ack >> 8) & 0xFF);
        ack_op[1] = (byte) (ack & 0xFF);
        ack_op[2] = (byte) ((op >> 8) & 0xFF);
        ack_op[3] = (byte) (op & 0xFF);
        return ack_op;
    }


    private byte[] mergeBytes(byte[] a, byte[] b) {
        //return merged array, a is first b is second
        int aLen = a.length;
        int bLen = b.length;
        byte[] c = new byte[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    private Command popCommand() {
        short op = getOpFromBytes();
        Command cmd = new Command(op);
        if (op == 4 || op == 11) {
        } //do nothing, just return the cmd with op code
        else if (op == 1 || op == 2 || op == 3) { //register, login - message is short-string-string
            String str = new String(bytes, 2, len - 3, StandardCharsets.UTF_8); //cutting the \0 byte in the end
            String[] usrData = str.split("\0");
            cmd.setUserName(usrData[0]);
            cmd.setPass(usrData[1]);
        } else if (op == 5 || op == 6 || op == 7 || op == 9 || op == 10) {//regcourse, kdamcheck, course-stat, stud-stat, checkisreg, unreg - messgae is short-short
            short course = (short) ((bytes[2] & 0xff) << 8);
            course += (short) (bytes[3] & 0xff);
            cmd.setCourse(course);

        } else if (op == 8) { //studentstat - message is short-string
            String usr = new String(bytes, 2, len - 3, StandardCharsets.UTF_8); //cutting the \0 byte in the end
            cmd.setUserName(usr);

        }

        len = 0;
        numOfZeroBytes = 0;
        return cmd;

    }

    private short getOpFromBytes(){
        short op = (short) ((bytes[0] & 0xff) << 8);
        op += (short) (bytes[1] & 0xff);
        return op;
    }

    private boolean messageDone(byte nextByte) {
        if (len < 2)
            return false;
        short op = getOpFromBytes();
        if (op == 4 | op == 11) { //logout, mycourses - message is 2 bytes
            return true;
        } else if (op == 1 | op == 2 | op == 3) { //register, login - message is short-string-string
            if (numOfZeroBytes == 3)
                return true;
        } else if (op == 5 | op == 6 | op == 7 | op == 9 | op == 10) {//regcourse, kdamcheck, course-stat, stud-stat, checkisreg, unreg - messgae is short-short
            if (len == 4)
                return true;
        } else if (op == 8) { //studentstat - message is short-string
            if (numOfZeroBytes == 2)
                return true;
        }

        return false;

    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length)
            bytes = Arrays.copyOf(bytes, len * 2);
        bytes[len++] = nextByte;
        if (nextByte == '\0')
            numOfZeroBytes++;

    }

}
