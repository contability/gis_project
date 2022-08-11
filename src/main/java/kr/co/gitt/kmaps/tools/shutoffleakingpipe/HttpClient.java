package kr.co.gitt.kmaps.tools.shutoffleakingpipe;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * Http 클라이언트
 * 
 * @author kwangsu.lee
 *
 */
public class HttpClient
{
	protected boolean isOpen;
	
	protected String host;
	
	protected int port;
	
	protected int timeout;
	
	private ClientBootstrap bootstrap;
	
	
	public HttpClient(String host, int port, int timeout)
	{
		this.host = host;
		this.port = port;
		this.timeout = timeout;
		isOpen = false;
	}
	
	public String getHost()
	{
		return host;
	}
	
	public int getPort()
	{
		return port;
	}
	
	/**
	 * 연결
	 * 
	 * @return 성공여부
	 */
	public boolean open()
	{
		ExecutorService boss = Executors.newCachedThreadPool();
		ExecutorService worker = Executors.newCachedThreadPool(); 
		NioClientSocketChannelFactory ncscf = new NioClientSocketChannelFactory(boss, worker);
		
		bootstrap = new ClientBootstrap(ncscf);
        if (bootstrap == null)
        	return false;
        
		// set pipeline factory
        HttpClientPipelineFactory pipeline = new HttpClientPipelineFactory();
		bootstrap.setPipelineFactory(pipeline);
		
		isOpen = true;
		
		return true;
	}
	
	/**
	 * 연결을 종료.
	 */
	public void close()
	{
		bootstrap.releaseExternalResources();
		bootstrap.shutdown();
		isOpen = false;
	}
	
	/**
	 * 채널을 반환.
	 * @return 채널
	 */
	public synchronized Channel getChannel()
	{
		try
		{
			ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
			
			HttpListener listener = new HttpListener(timeout);
			future.addListener(listener);
						
			return listener.getChannel();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
}
