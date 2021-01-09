package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;

import java.util.function.Supplier;

public class BaseServerImpl<T> extends BaseServer<T> {
        int i;
    public BaseServerImpl( int port,
                    Supplier<MessagingProtocol<T>> protocolFactory,
                    Supplier<MessageEncoderDecoder<T>> encdecFactory){
        super(port,protocolFactory,encdecFactory);
    }

    @Override
    protected void execute(BlockingConnectionHandler<T> handler) {
        Thread t1 = new Thread(handler);//TODO need check if thread is overwrittern
        t1.start();
    }

    @Override
    public void serve() {
        super.serve();
    }
}
