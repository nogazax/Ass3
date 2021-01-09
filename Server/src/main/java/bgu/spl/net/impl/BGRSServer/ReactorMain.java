package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoderImpl;
import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.srv.*;

public class ReactorMain {
    public static void main(String[] args){
        int port;
        int numOfThreads;
        if (args.length==0){
            port = 7777;
            numOfThreads=5;

        }
        else {
            port = Integer.parseInt(args[0]);
            numOfThreads = Integer.parseInt(args[1]);
        }
        Reactor server = new Reactor(numOfThreads
                                     ,port
                                     ,()-> {return new MessagingProtocolImpl<>();}
                                     ,()->{return new MessageEncoderDecoderImpl<>();}
        );
        server.serve();
    }
}
