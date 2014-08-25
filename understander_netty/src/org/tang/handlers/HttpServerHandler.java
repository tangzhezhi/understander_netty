package org.tang.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpHeaders.Values;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.tang.dto.BaseDTO;
import org.tang.handlers.http.novelty.QueryNoveltyHandler;
import org.tang.handlers.http.test.MsgHandler;
import org.tang.handlers.string.test.TestHandler;
import org.tang.utils.ByteBufToBytes;

import com.google.gson.Gson;

@Component
@Qualifier("httpServerHandler")
@Sharable
public class HttpServerHandler  extends ChannelInboundHandlerAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(HttpServerHandler.class);
	private ByteBufToBytes reader;  
	
	@Autowired
	private MsgHandler msgHandler;
	
	@Autowired
	private QueryNoveltyHandler queryNoveltyHandler;
	
	
	 @Override  
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		 
	        if (msg instanceof HttpRequest) {  
	            HttpRequest request = (HttpRequest) msg;  
	            System.out.println("messageType:" + request.headers().get("messageType"));  
	            System.out.println("businessType:" + request.headers().get("businessType"));  
	            if (HttpHeaders.isContentLengthSet(request)) {  
	                reader = new ByteBufToBytes((int) HttpHeaders.getContentLength(request));  
	            }  
	        }  
	  
	        if (msg instanceof HttpContent) {  
	            HttpContent httpContent = (HttpContent) msg;  
	            ByteBuf content = httpContent.content();  
	            reader.reading(content);  
	            content.release();  
	  
	            if (reader.isEnd()) {  
	                String resultStr = new String(reader.readFull());  
	                
	                if(resultStr.equals("heartbeat")){
	                    System.out.println("我是心跳包 : " + msg);
	                    
    	                FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer("heartbeat"  
    	                        .getBytes()));  
    	                response.headers().set(CONTENT_TYPE, "text/plain");  
    	                response.headers().set(CONTENT_LENGTH, response.content().readableBytes());  
    	                response.headers().set(CONNECTION, Values.KEEP_ALIVE);   
    	                ctx.writeAndFlush(response);  
	                    
	                    
	                    ctx.channel().writeAndFlush(msg);
	                }
	                else{
	                	
	                	Gson gson = new Gson();
	                	
	                	
	                	BaseDTO dto = gson.fromJson(resultStr, BaseDTO.class);
	                	
	                	if(("msg").equals(dto.getEntityType())){
	                		msgHandler.channelRead(ctx, resultStr);
	                	}
	                	else if(("query_novelty").equals(dto.getEntityType())){
	                		queryNoveltyHandler.channelRead(ctx, resultStr);
	                	}
	                	
	                	else {
	    	                FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer("不是正确的格式"  
	    	                        .getBytes()));  
	    	                response.headers().set(CONTENT_TYPE, "text/plain");  
	    	                response.headers().set(CONTENT_LENGTH, response.content().readableBytes());  
	    	                response.headers().set(CONNECTION, Values.KEEP_ALIVE);  
	    	                ctx.writeAndFlush(response);  
	                	}
	                }
	            }  
	        }  
	    }  
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LOG.info("Channel is active\n");
		super.channelActive(ctx);
	}
	
	@Override  
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {  
		LOG.info("HttpServerInboundHandler.channelReadComplete");  
        ctx.flush();  
    }  
	
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		LOG.info("\nChannel is disconnected");
		super.channelInactive(ctx);
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}
	

}
