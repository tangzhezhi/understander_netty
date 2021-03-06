package org.tang.handlers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Just a dummy protocol mainly to show the ServerBootstrap being initialized.
 * 
 * @author Abraham Menacherry
 * 
 */
@Component
@Qualifier("springProtocolInitializer")
public class StringProtocolInitalizer extends ChannelInitializer<SocketChannel> {

	@Autowired
	StringDecoder stringDecoder;

	@Autowired
	StringEncoder stringEncoder;

	@Autowired
	ServerHandler serverHandler;

	@Autowired
	IdleStateHandler idleStateHandler;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", stringDecoder);
		pipeline.addLast("encoder", stringEncoder);
//		pipeline.addLast("heartbeat", idleStateHandler);
		pipeline.addLast("handler", serverHandler);
	}

	public StringDecoder getStringDecoder() {
		return stringDecoder;
	}

	public void setStringDecoder(StringDecoder stringDecoder) {
		this.stringDecoder = stringDecoder;
	}

	public StringEncoder getStringEncoder() {
		return stringEncoder;
	}

	public void setStringEncoder(StringEncoder stringEncoder) {
		this.stringEncoder = stringEncoder;
	}

	public ServerHandler getServerHandler() {
		return serverHandler;
	}

	public void setServerHandler(ServerHandler serverHandler) {
		this.serverHandler = serverHandler;
	}

}
