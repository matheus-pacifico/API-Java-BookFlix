package br.edu.ifms.bookflix.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

@Configuration
public class CloudStorageConfiguration {
	
	@Value("${dropbox.accessToken}")
	private String accessToken;
	@Value("${dropbox.appname}")
	private String appname;
	
	@Bean
	public DbxClientV2 dbxClientV2() {
		DbxRequestConfig configuration = DbxRequestConfig.newBuilder(appname).build();
		return new DbxClientV2(configuration, accessToken);
	}

}
