package kr.co.gitt.kmaps.tools.shutoffleakingpipe;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;

class HttpListener implements ChannelFutureListener
{
	private Channel _channel;
	
	private int _timeout; 
	
	
	public HttpListener(int timeout)
	{
		_timeout = timeout;
	}
	
	public void operationComplete(ChannelFuture future) throws Exception
	{
		_channel = future.getChannel();
	}
	
	public Channel getChannel()
	{
		try
		{
			int time = 0;
    		while(time < _timeout)
    		{
    			if (_channel != null)
    				return _channel;
    			
    			Thread.sleep(100);
    			time += 100;
    		}
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}	
}