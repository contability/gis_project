package kr.co.gitt.kmaps.tools.shutoffleakingpipe;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;

public class HttpClientHandler extends SimpleChannelUpstreamHandler
{
	// 청크인지 확인
	private boolean isChunks = false;
	
	// 작업 종료
	private boolean isEnd = false;

	// 상태
	private int status;
	
	// 결과물 저장
	private ChannelBuffer responseBuffer = null;		
	
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		if (!isChunks) 
		{
			HttpResponse response = (HttpResponse) e.getMessage();
			  
			status = response.getStatus().getCode();
			
			if (response.isChunked()) 
			{
				isChunks = true;
				responseBuffer = ChannelBuffers.dynamicBuffer();				
			} 
			else 
			{
				ChannelBuffer content = response.getContent();
			    if (content.readable()) 
			    	responseBuffer = content;
			    
			    isEnd = true;
			}
		} 
		else 
		{
			HttpChunk chunk = (HttpChunk) e.getMessage();
			if (chunk.isLast()) 
			{
				isEnd = true;
				isChunks = false;
			} 
			else 
			{
				responseBuffer.writeBytes(chunk.getContent());
			}
		}
		
	}
	
	/**
	 * 상태를 반환.
	 * 
	 * @return 상태
	 */
	public int getStatus()
	{
		return status;
	}
	
	/**
	 * 전송 받았는지 확인.
	 * 
	 * @return 모두 전송 받았으면 true 를 반환. 
	 */
	public boolean isEnd()
	{
		return isEnd;
	}
	
	/**
	 * 결과를 반환.
	 * 
	 * @return 결과.
	 */
	public String getResponse()
	{
		if (responseBuffer == null)
			return null;
		
    	byte[] bytes = new byte[responseBuffer.readableBytes()];
    	responseBuffer.readBytes(bytes);
    	
    	String response = new String(bytes, CharsetUtil.UTF_8);
    	try
		{
			response = URLDecoder.decode(response.trim(), CharsetUtil.UTF_8.name());
		} 
    	catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
    	
		return response;
	}
}
