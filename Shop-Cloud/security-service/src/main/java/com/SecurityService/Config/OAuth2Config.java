package com.SecurityService.Config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.SecurityService.Service.SecurityService;

import io.jsonwebtoken.security.Keys;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	
	  @Bean
	    public JwtAccessTokenConverter jwtAccessTokenConverter() {
	        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	        converter.setSigningKey("JwtKeyJwtKeyJwtKeyJwtKeyJwtKeyJwtKeyJwtKeyJwtKeyJwtKeyJwtKey");
	        
	        return converter;
	    }

	    @Bean
	    public TokenStore tokenStore() {
	        return new JwtTokenStore(jwtAccessTokenConverter());
	    }

	    @Override
	    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	        String encodedSecret = passwordEncoder.encode("secret");
	        clients
	            .inMemory()
	            .withClient("client")
	            .secret(encodedSecret)
	            .authorizedGrantTypes("password")
	            .scopes("read", "write")
	            .accessTokenValiditySeconds(3600);
	    }

	    @Override
	    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	        endpoints
	            .authenticationManager(authenticationManager)
	            .userDetailsService(securityService)
	            .accessTokenConverter(jwtAccessTokenConverter())
	            .tokenStore(tokenStore());
	    }
	}
