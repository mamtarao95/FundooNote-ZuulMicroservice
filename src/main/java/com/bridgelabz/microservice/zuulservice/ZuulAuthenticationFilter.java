package com.bridgelabz.microservice.zuulservice;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;

public class ZuulAuthenticationFilter extends ZuulFilter {

	
	@Value("${key}")
	private String secret;
	
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		System.out.println(request.getRequestURI());
		
		if(!request.getRequestURI().startsWith("/user")) {
		
		String token = request.getHeader("token");
		System.out.println(token + "token fron zuuuulll");
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(token)
				.getBody();
		ctx.addZuulRequestHeader("UserId",claims.getId());
		}
		return null;

	}

}
