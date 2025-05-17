package com.invernadero.auth_service.client;

import com.invernadero.auth_service.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserServiceClient {

    @GetMapping("/usuarios/{email}")
    Usuario getUsuarioPorEmail(@PathVariable("email") String email);
}
