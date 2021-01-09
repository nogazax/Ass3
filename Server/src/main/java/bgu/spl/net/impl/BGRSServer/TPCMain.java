package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.Message;
import bgu.spl.net.api.MessageEncoderDecoderImpl;
import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.srv.*;
public class TPCMain {

        public static void main(String[] args){
            int port = Integer.parseInt(args[0]);
            BaseServerImpl server = new BaseServerImpl(port
                    ,()-> {return new MessagingProtocolImpl<>();}
                    ,()->{return new MessageEncoderDecoderImpl<>();});
            server.serve();
    }
}


