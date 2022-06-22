package br.com.infotera.santander.getnet.controller;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.pagto.rqrs.WSPagtoCartaoRQ;
import br.com.infotera.common.pagto.rqrs.WSPagtoCartaoRS;
import br.com.infotera.common.util.LogWS;
import br.com.infotera.santander.getnet.services.MontaWS;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author enioj
 */
@RestController
@RequestMapping(value = "/getnet")
public class GetNetController {

    @Autowired
    private Gson gson;
    @Autowired
    private MontaWS montaWS;

    @RequestMapping(value = "ola", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello World GetNet";
    }

    @RequestMapping(value = "/autorizar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String autorizar(@RequestBody String dsRQ) {

        WSPagtoCartaoRS wsRS = null;
        WSPagtoCartaoRQ wsRQ = gson.fromJson(dsRQ, WSPagtoCartaoRQ.class);
        wsRQ.getIntegrador().setDsMetodo("autorizar");

        try {
            wsRS = montaWS.autorizar(wsRQ);
        } catch (ErrorException ex) {
            wsRS = new WSPagtoCartaoRS(null, ex.getIntegrador());
        } catch (Exception ex) {
            wsRS = new WSPagtoCartaoRS(null, new ErrorException(wsRQ.getIntegrador(), GetNetController.class, "autorizar", WSMensagemErroEnum.GENNULO, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex).getIntegrador());
        } finally {
            LogWS.gerarLog(wsRS.getIntegrador(), dsRQ, wsRS);
        }

        return gson.toJson(wsRS);
    }

    @RequestMapping(value = "/capturar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String capturar(@RequestBody String dsRQ) {

        WSPagtoCartaoRS wsRS = null;
        WSPagtoCartaoRQ wsRQ = gson.fromJson(dsRQ, WSPagtoCartaoRQ.class);
        wsRQ.getIntegrador().setDsMetodo("capturar");

        try {
            wsRS = montaWS.capturar(wsRQ);
        } catch (ErrorException ex) {
            wsRS = new WSPagtoCartaoRS(null, ex.getIntegrador());
        } catch (Exception ex) {
            wsRS = new WSPagtoCartaoRS(null, new ErrorException(wsRQ.getIntegrador(), GetNetController.class, "capturar", WSMensagemErroEnum.GENNULO, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex).getIntegrador());
        } finally {
            LogWS.gerarLog(wsRS.getIntegrador(), dsRQ, wsRS);
        }

        return gson.toJson(wsRS);
    }

    @RequestMapping(value = "/cancelar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String cancelar(@RequestBody String dsRQ) {

        WSPagtoCartaoRS wsRS = null;
        WSPagtoCartaoRQ wsRQ = gson.fromJson(dsRQ, WSPagtoCartaoRQ.class);
        wsRQ.getIntegrador().setDsMetodo("cancelar");

        try {
            wsRS = montaWS.cancelar(wsRQ);
        } catch (ErrorException ex) {
            wsRS = new WSPagtoCartaoRS(null, ex.getIntegrador());
        } catch (Exception ex) {
            wsRS = new WSPagtoCartaoRS(null, new ErrorException(wsRQ.getIntegrador(), GetNetController.class, "cancelar", WSMensagemErroEnum.GENNULO, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex).getIntegrador());
        } finally {
            LogWS.gerarLog(wsRS.getIntegrador(), dsRQ, wsRS);
        }

        return gson.toJson(wsRS);
    }

    @RequestMapping(value = "/autorizarToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String autorizarToken(@RequestBody String dsRQ) {

        WSPagtoCartaoRS wsRS = null;
        WSPagtoCartaoRQ wsRQ = gson.fromJson(dsRQ, WSPagtoCartaoRQ.class);
        wsRQ.getIntegrador().setDsMetodo("autorizarToken");

        try {
            wsRS = montaWS.autorizarComToken(wsRQ);
        } catch (ErrorException ex) {
            wsRS = new WSPagtoCartaoRS(null, ex.getIntegrador());
        } catch (Exception ex) {
            wsRS = new WSPagtoCartaoRS(null, new ErrorException(wsRQ.getIntegrador(), GetNetController.class, "autorizarToken", WSMensagemErroEnum.GENNULO, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex).getIntegrador());
        } finally {
            LogWS.gerarLog(wsRS.getIntegrador(), dsRQ, wsRS);
        }

        return gson.toJson(wsRS);
    }

    @RequestMapping(value = "/autenticarThreeDS", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String autenticarThreeDS(@RequestBody String dsRQ) {

        WSPagtoCartaoRS wsRS = null;
        WSPagtoCartaoRQ wsRQ = gson.fromJson(dsRQ, WSPagtoCartaoRQ.class);
        wsRQ.getIntegrador().setDsMetodo("auntenticarThreeDS");

        try {
            wsRS = montaWS.autenticarThreeDS(wsRQ);
        } catch (ErrorException ex) {
            wsRS = new WSPagtoCartaoRS(null, ex.getIntegrador());
        } catch (Exception ex) {
            wsRS = new WSPagtoCartaoRS(null, new ErrorException(wsRQ.getIntegrador(), GetNetController.class, "auntenticarThreeDS", WSMensagemErroEnum.GENNULO, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex).getIntegrador());
        } finally {
            LogWS.gerarLog(wsRS.getIntegrador(), dsRQ, wsRS);
        }

        return gson.toJson(wsRS);
    }

    @RequestMapping(value = "/gerarTokenThreeDS", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String gerarTokenThreeDS(@RequestBody String dsRQ) {

        WSPagtoCartaoRS wsRS = null;
        WSPagtoCartaoRQ wsRQ = gson.fromJson(dsRQ, WSPagtoCartaoRQ.class);
        wsRQ.getIntegrador().setDsMetodo("gerarTokenThreeDS");

        try {
            wsRS = montaWS.gerarTokenThreeDS(wsRQ);
        } catch (ErrorException ex) {
            wsRS = new WSPagtoCartaoRS(null, ex.getIntegrador());
        } catch (Exception ex) {
            wsRS = new WSPagtoCartaoRS(null, new ErrorException(wsRQ.getIntegrador(), GetNetController.class, "auntenticarThreeDS", WSMensagemErroEnum.GENNULO, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex).getIntegrador());
        } finally {
            LogWS.gerarLog(wsRS.getIntegrador(), dsRQ, wsRS);
        }

        return gson.toJson(wsRS);
    }

    @RequestMapping(value = "/autorizarThreeDS", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String preAutorizarThreeDS(@RequestBody String dsRQ) {

        WSPagtoCartaoRS wsRS = null;
        WSPagtoCartaoRQ wsRQ = gson.fromJson(dsRQ, WSPagtoCartaoRQ.class);
        wsRQ.getIntegrador().setDsMetodo("preAutorizarThreeDS");

        try {
            wsRS = montaWS.preAutorizarThreeDS(wsRQ);
        } catch (ErrorException ex) {
            wsRS = new WSPagtoCartaoRS(null, ex.getIntegrador());
        } catch (Exception ex) {
            wsRS = new WSPagtoCartaoRS(null, new ErrorException(wsRQ.getIntegrador(), GetNetController.class, "auntenticarThreeDS", WSMensagemErroEnum.GENNULO, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex).getIntegrador());
        } finally {
            LogWS.gerarLog(wsRS.getIntegrador(), dsRQ, wsRS);
        }

        return gson.toJson(wsRS);
    }

    @RequestMapping(value = "/consultaAutorizarThreeDS", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String consultaAutorizarThreeDS(@RequestBody String dsRQ) {

        WSPagtoCartaoRS wsRS = null;
        WSPagtoCartaoRQ wsRQ = gson.fromJson(dsRQ, WSPagtoCartaoRQ.class);
        wsRQ.getIntegrador().setDsMetodo("consultaAutorizarThreeDS");

        try {
            wsRS = montaWS.consultaAutorizarThreeDS(wsRQ);
        } catch (ErrorException ex) {
            wsRS = new WSPagtoCartaoRS(null, ex.getIntegrador());
        } catch (Exception ex) {
            wsRS = new WSPagtoCartaoRS(null, new ErrorException(wsRQ.getIntegrador(), GetNetController.class, "auntenticarThreeDS", WSMensagemErroEnum.GENNULO, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex).getIntegrador());
        } finally {
            LogWS.gerarLog(wsRS.getIntegrador(), dsRQ, wsRS);
        }

        return gson.toJson(wsRS);
    }

}
