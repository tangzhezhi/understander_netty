package org.tang.handlers.string.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.tang.dto.string.test.MsgDTO;
import org.tang.service.string.test.TestService;

import com.google.gson.Gson;

@Component
@Qualifier("testHandler")
@Sharable
public class TestHandler extends SimpleChannelInboundHandler<String>{
	
	private static final Logger LOG = LoggerFactory.getLogger(TestHandler.class);
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private ThreadPoolTaskExecutor threadPool;
	
	@Override
	public void channelRead0(final ChannelHandlerContext ctx, final String msg)
			throws Exception {
		LOG.info(msg);
		
		threadPool.execute(new Runnable() {
	            @Override
	            public void run() {
	                if(msg.equals("heartbeat")){
	                    System.out.println("我是心跳包 : " + msg);
	                    ctx.channel().writeAndFlush(msg);
	                }
	                else{
	                	String remsg = "";
	                	Gson gson = new Gson();
	                	MsgDTO dto = gson.fromJson(msg, MsgDTO.class);
	                	int result = 0;
						try {
							result = testService.saveMsg(dto);
		                	if(result!=0){
		                		remsg = dto.getContent();
		                	}
		                	else{
		                		remsg = "false";
		                	}
		                	ctx.writeAndFlush(remsg);
						} catch (Exception e) {
							e.printStackTrace();
						}
	                }
	            }
	        });
		
		
		
//        if(msg.equals("heartbeat")){
//            System.out.println("我是心跳包 : " + msg);
//            ctx.channel().writeAndFlush(msg);
//        }
//        else{
//        	String remsg = "";
//        	Gson gson = new Gson();
//        	MsgDTO dto = gson.fromJson(msg, MsgDTO.class);
//        	int result = 0;
//			try {
//				result = testService.saveMsg(dto);
//            	if(result!=0){
//            		remsg = dto.getContent();
//            	}
//            	else{
//            		remsg = "false";
//            	}
//            	ctx.writeAndFlush(remsg);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//        }
//		
		
	}

}
