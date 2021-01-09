package bgu.spl.net.api;

public class Response extends Message {

    final boolean isAck;
    final short opcode;
    final String info;

    public Response(boolean ack, short op){
        //for errors or infoless acks
        isAck=ack;
        opcode=op;
        info=null;
    }

    public Response(short op, String info){
        //for acks with info
        isAck=true;
        opcode=op;
        this.info=info;
    }
    public String toString(){
        return "ack: " + isAck +" opcode: " +opcode;
    }


}
