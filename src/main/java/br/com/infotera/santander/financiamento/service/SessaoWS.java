package br.com.infotera.santander.financiamento.service;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.WSSessao;
import br.com.infotera.santander.financiamento.client.SantanderClient;
import br.com.infotera.santander.financiamento.rqrs.AuthTokenRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessaoWS {

    @Autowired
    private SantanderClient santanderClient;

    public WSIntegrador abreSessao(WSIntegrador integrador) throws ErrorException {
        //BUSCA SESSAO DISPONIVEL
        AuthTokenRQ authToken = new AuthTokenRQ(integrador.getDsCredencialList().get(2), integrador.getDsCredencialList().get(3));

        WSSessao sessao = santanderClient.abrirSessao(integrador, authToken);
        integrador.setSessao(sessao);

        return integrador;
    }
}
