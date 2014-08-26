package org.tang.handlers.http.novelty;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Values;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.tang.dto.PageDTO;
import org.tang.dto.badidea.BadIdeaDTO;
import org.tang.dto.novelty.NoveltyDTO;
import org.tang.service.badidea.BadIdeaService;
import org.tang.service.novelty.NoveltyService;
import org.tang.utils.Pagination;

import com.google.gson.Gson;

@Component
@Qualifier("queryNoveltyHandler")
@Sharable
public class QueryNoveltyHandler  extends ChannelInboundHandlerAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(QueryNoveltyHandler.class);
	
	@Autowired
	private NoveltyService noveltyService;
	
	@Autowired
	private ThreadPoolTaskExecutor threadPool;
	
	 @Override  
	 public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {  
			threadPool.execute(new Runnable() {
	            @Override
	            public void run() {
			     	Gson gson = new Gson();
			    	
			     	NoveltyDTO dto = gson.fromJson(String.valueOf(msg), NoveltyDTO.class);
					try {
						
						Pagination<?> p = noveltyService.queryNovelty(dto);
						
						PageDTO pdto = new PageDTO(p);
						
						String resultmsg = gson.toJson(pdto).toLowerCase();
						
				        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(resultmsg  
				                .getBytes()));  
				        response.headers().set(CONTENT_TYPE, "text/plain");  
				        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());  
				        response.headers().set(CONNECTION, Values.KEEP_ALIVE);  
				        ctx.writeAndFlush(response); 
					} catch (Exception e) {
						e.printStackTrace();
					}
   
	            }
	        });		
	    }  
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}
	
	@Override  
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {  
        ctx.flush();  
    }  
	
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}
	

}
