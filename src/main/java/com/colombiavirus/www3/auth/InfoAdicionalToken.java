package com.colombiavirus.www3.auth;

import com.colombiavirus.www3.models.Usuario;
import com.colombiavirus.www3.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

    @Autowired
    private UsuarioServices usuarioServicesObj;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        System.out.println("authentication: " + authentication.getName());

        Usuario usuario = usuarioServicesObj.findByEmail(authentication.getName());

        Map<String, Object> info = new HashMap<>();
        info.put("nombre", usuario.getNombre());
        info.put("apellido",usuario.getApellido());
        info.put("email",usuario.getEmail());
        info.put("edad", usuario.getEdad());
        info.put("cedula", usuario.getCedula());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }

}
