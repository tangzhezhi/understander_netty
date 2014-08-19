package org.tang.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.tang.dto.BaseDTO;
import org.tang.handlers.http.badidea.QueryBadIdeaHandler;
import org.tang.handlers.string.test.TestHandler;

import com.google.gson.Gson;

@Component
@Qualifier("serverHandler")
@Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {
	private static final Logger LOG = LoggerFactory.getLogger(ServerHandler.class);
	
	@Autowired
	private TestHandler testHandler;
	
	@Autowired
	private QueryBadIdeaHandler queryBadIdeaHandler;
	
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		LOG.info(msg);
		
        if(msg.equals("heartbeat")){
            System.out.println("我是心跳包 : " + msg);
            ctx.channel().writeAndFlush(msg);
        }
        else{
        	
        	Gson gson = new Gson();
        	
        	BaseDTO dto = gson.fromJson(msg, BaseDTO.class);
        	
        	if(("msg").equals(dto.getEntityType())){
        		testHandler.channelRead(ctx, msg);
        	}
        	else if(("badidea").equals(dto.getEntityType())){
        		queryBadIdeaHandler.channelRead(ctx, msg);
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
        ctx.flush();  
    }  
	
	
//	@Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
//            throws Exception {
//        /*心跳处理*/
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent event = (IdleStateEvent) evt;
//            if (event.state() == IdleState.READER_IDLE) {
//                /*读超时*/
//                LOG.info("READER_IDLE 读超时");
//                ctx.disconnect();
//            } else if (event.state() == IdleState.WRITER_IDLE) {
//                /*写超时*/   
//                LOG.info("WRITER_IDLE 写超时");
//                i++;
//                
//                if(i==3){
//                	 ctx.close();
//                }
//            } else if (event.state() == IdleState.ALL_IDLE) {
//                /*总超时*/
////                LOG.info("ALL_IDLE 总超时");
//            }
//        }
//    }

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
