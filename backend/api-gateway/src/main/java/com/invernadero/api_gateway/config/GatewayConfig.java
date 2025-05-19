package com.invernadero.api_gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

@Configuration
public class GatewayConfig {


    @Value("${gateway.invernadero-service.uri}")
    private String invernaderoServiceUri;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("invernadero-route", r -> r.path("/lectura")
                        .uri(invernaderoServiceUri))
                .build();
    }



    @Bean
    public GlobalFilter lecturaRedirectFilter() {
        return (exchange, chain) -> {
            if (exchange.getRequest().getPath().value().equals("/lectura") &&
                    exchange.getRequest().getMethod() == HttpMethod.POST &&
                    exchange.getRequest().getHeaders().getContentType() != null &&
                    exchange.getRequest().getHeaders().getContentType().includes(MediaType.APPLICATION_JSON)) {

                return DataBufferUtils.join(exchange.getRequest().getBody())
                        .flatMap(dataBuffer -> {
                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(bytes);
                            DataBufferUtils.release(dataBuffer); // liberar buffer

                            try {
                                ObjectMapper mapper = new ObjectMapper();
                                Map<String, Object> body = mapper.readValue(bytes, Map.class);

                                Integer sensorId = (Integer) body.get("sensorId");
                                if (sensorId == null) {
                                    return Mono.error(new IllegalArgumentException("sensorId es requerido"));
                                }

                                // Nueva URI
                                URI uri = UriComponentsBuilder
                                        .fromUri(URI.create(invernaderoServiceUri))
                                        .replacePath("/api/sensores/" + sensorId + "/lecturas")
                                        .build(true).toUri();

                                // Nuevo request con body reintegrado
                                Flux<DataBuffer> cachedBodyFlux = Flux.defer(() -> {
                                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                                    return Flux.just(buffer);
                                });

                                ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                                    @Override
                                    public Flux<DataBuffer> getBody() {
                                        return cachedBodyFlux;
                                    }

                                    @Override
                                    public URI getURI() {
                                        return uri;
                                    }
                                };

                                ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

                                // Guardar la nueva URI para que Gateway redirija
                                mutatedExchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, uri);

                                return chain.filter(mutatedExchange);

                            } catch (Exception e) {
                                return Mono.error(new RuntimeException("Error procesando el cuerpo JSON", e));
                            }
                        });
            }

            return chain.filter(exchange);
        };
    }
}
