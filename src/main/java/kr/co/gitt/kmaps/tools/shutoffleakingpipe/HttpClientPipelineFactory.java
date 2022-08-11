package kr.co.gitt.kmaps.tools.shutoffleakingpipe;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpClientCodec;
import org.jboss.netty.handler.codec.http.HttpContentDecompressor;

public class HttpClientPipelineFactory implements ChannelPipelineFactory
{
	/**
	 * 생성자
	 */
    public HttpClientPipelineFactory()
    {
    }
    
    public ChannelPipeline getPipeline() throws Exception 
    {
        // Create a default pipeline implementation.
    	ChannelPipeline pipeline = Channels.pipeline();
    	
        pipeline.addLast("codec", new HttpClientCodec());
  
        // Remove the following line if you don't want automatic content decompression.
        pipeline.addLast("inflater", new HttpContentDecompressor());
  
        // Uncomment the following line if you don't want to handle HttpChunks.
        //pipeline.addLast("aggregator", new HttpChunkAggregator(1048576));
  
		pipeline.addLast("handler", new HttpClientHandler());
        
        return pipeline;
    }
}
