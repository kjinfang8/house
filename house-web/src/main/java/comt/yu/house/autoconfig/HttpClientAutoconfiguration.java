package comt.yu.house.autoconfig;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({HttpClient.class})
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoconfiguration { 
	private final HttpClientProperties properties;
	public HttpClientAutoconfiguration(HttpClientProperties properties) {
		this.properties = properties;
	}
	
	@Bean
	@ConditionalOnMissingBean(HttpClient.class)
	public HttpClient httpClient() {
		//构建requestConfig
		RequestConfig requestConfig = RequestConfig.custom()//
				.setConnectTimeout(properties.getConnectTimeOut())//设置连接超时时间，默认1秒
				.setSocketTimeout(properties.getSocketTimeOut())//设置读超时时间，默认10秒
				.build();
		HttpClient client = HttpClientBuilder.create()//
				.setDefaultRequestConfig(requestConfig)//设置requestConfig
				.setUserAgent(properties.getAgent())//设置User-Agent
				.setMaxConnPerRoute(properties.getMaxConnPerRoute())//设置一个远端IP最大的连接数
				.setConnectionReuseStrategy(new NoConnectionReuseStrategy())//不重用连接，默认是重用，建议保持默认开启连接池，节省建立连接开销
				.build();
		return client;
	}
}
