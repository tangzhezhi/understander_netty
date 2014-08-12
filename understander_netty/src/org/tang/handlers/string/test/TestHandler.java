package org.tang.handlers.string.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.tang.dto.string.test.MsgDTO;
import org.tang.entity.test.Msg;
import org.tang.handlers.ServerHandler;
import org.tang.service.string.test.TestService;

import com.google.gson.Gson;

@Component
@Qualifier("testHandler")
@Sharable
public class TestHandler extends SimpleChannelInboundHandler<String>{
	
	private static final Logger LOG = LoggerFactory.getLogger(TestHandler.class);
	
	@Autowired
	private TestService testService;
	
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
        	
        	MsgDTO dto = gson.fromJson(msg, MsgDTO.class);
        	
        	testService.saveMsg(dto);
        	
        }
	}

}
