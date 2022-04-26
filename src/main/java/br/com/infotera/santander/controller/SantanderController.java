package br.com.infotera.santander.controller;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoFinanciamentoConfirmarRQ;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoFinanciamentoConfirmarRS;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoFinanciamentoRQ;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoFinanciamentoRS;
import br.com.infotera.common.util.LogWS;
import br.com.infotera.santander.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static br.com.infotera.common.util.Utils.gson;
import org.springframework.boot.info.BuildProperties;

@RestController
public class SantanderController {

    @Autowired
    private CancelarWS cancelarWS;

    @Autowired
    private ConfirmarWS confirmarWS;

    @Autowired
    private ConsultaClienteWS consultaClienteWS;

    @Autowired
    private ConsultarWS consultarWS;

    @Autowired
    private SimularWS simularWS;
    
    @Autowired
    private BuildProperties build;

    @RequestMapping(value = "/ola", method = RequestMethod.GET)
    @ResponseBody
    public String ola() {
        return "Hello " + build.getName().toUpperCase() + " - Version: " + build.getVersion();
    }

//    @RequestMapping(value = "/conciliarBoleto", method = RequestMethod.POST)
//    public String conciliarBoleto(@RequestBody String jsonRQ) throws ErrorException {
//        WSPagtoConciliaBoletoRQ wsRQ = gson.fromJson(jsonRQ, WSPagtoConciliaBoletoRQ.class);
//        WSPagtoConciliaBoletoRS result = null;
//        wsRQ.getIntegrador().setDsMetodo("conciliarBoleto");
//        try {
//            result = conciliarBoletoWS.conciliarBoleto(wsRQ);
//        } catch (ErrorException ex) {
//            result = new WSPagtoConciliaBoletoRS(ex.getIntegrador(), null, WSIntegracaoStatusEnum.NEGADO);
//        } finally {
//            LogWS.gerarLog(result.getIntegrador(), jsonRQ);
//        }
//        return (gson.toJson(result));
//    }
//
//    @RequestMapping(value = "/gerarBoleto", method = RequestMethod.POST)
//    public String gerarBoleto(@RequestBody String jsonRQ) throws ErrorException, UnsupportedEncodingException {
//        WSPagtoBoletoRQ wsRQ = gson.fromJson(jsonRQ, WSPagtoBoletoRQ.class);
//        WSPagtoBoletoRS result = null;
//        wsRQ.getIntegrador().setDsMetodo("gerarBoleto");
//        try {
//            result = gerarBoletoWS.geraBoleto(wsRQ);
//        } catch (ErrorException ex) {
//            result = new WSPagtoBoletoRS(ex.getIntegrador(), null, WSIntegracaoStatusEnum.NEGADO);
//        } finally {
//            LogWS.gerarLog(result.getIntegrador(), jsonRQ);
//        }
//        return (gson.toJson(result));
//    }

    @RequestMapping(value = "/simularFinanciamento", method = RequestMethod.POST)
    public String simularFinanciamento(@RequestBody String jsonRQ) throws ErrorException {
        WSPagtoFinanciamentoRQ wsRQ = gson.fromJson(jsonRQ, WSPagtoFinanciamentoRQ.class);
        WSPagtoFinanciamentoRS result = null;
        wsRQ.getIntegrador().setDsMetodo("simularFinanciamento");
        try {
//            result = simularWS.simularFinanciamento(wsRQ);
        } catch (Exception ex) {
            result = new WSPagtoFinanciamentoRS(null,null);
        } finally {
            LogWS.gerarLog(result.getIntegrador(), jsonRQ, false);
        }
        return (gson.toJson(result));
    }

//    @RequestMapping(value = "/consultarCliente", method = RequestMethod.POST)
//    public String consultarCliente(@RequestBody String jsonRQ) {
//        WSPagtoConsultaClienteRS result = null;
//        WSPagtoConsultaClienteRQ wsRQ = gson.fromJson(jsonRQ, WSPagtoConsultaClienteRQ.class);
//        boolean stGerarErro = false;
//        try {
//            result = pagtoConsultaClienteWS.pagtoConsulta(wsRQ, false);
//        } catch (ErrorException ex) {
//            stGerarErro = true;
//            result = new WSTarifarHotelRS(null, ex.getIntegrador());
//        } catch (Exception ex){
//            stGerarErro = true;
//            result = new WSTarifarHotelRS(null, new ErrorException(wsRQ.getIntegrador(), ApiController.class, "tarifar", WSMensagemErroEnum.GENNULO, ex.getMessage(), WSIntegracaoStatusEnum.INCONSISTENTE, ex).getIntegrador());
//        }
//        try {
//            Utils.gerarLog(result.getIntegrador(), "tarifar", true, jsonRQ, stGerarErro);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return (gson.toJson(result));
//    }

//    @RequestMapping(value = "/cadastrarCliente", method = RequestMethod.POST)
//    public String cadastrarCliente(@RequestBody String jsonRQ) {
//        WSPagtoCadastraClienteRS result = null;
//        WSPagtoCadastraClienteRQ wsRQ = gson.fromJson(jsonRQ, WSPagtoCadastraClienteRQ.class);
//        boolean stGerarErro = false;
//        try {
//            result = pagtoCadastraClienteWS.pagtoCadastraCliente(wsRQ);
//        } catch (ErrorException ex) {
//            stGerarErro = true;
//            result = new WSReservarRS(null, ex.getIntegrador());
//        } catch (Exception ex) {
//            stGerarErro = true;
//            result = new WSReservarRS(null, new ErrorException(wsRQ.getIntegrador(), ApiController.class, "reservar", WSMensagemErroEnum.GENNULO, ex.getMessage(), WSIntegracaoStatusEnum.INCONSISTENTE, ex).getIntegrador());
//        }
//        try {
//            Utils.gerarLog(result.getIntegrador(), "reservar", true, jsonRQ, stGerarErro);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return (gson.toJson(result));
//    }

//    @RequestMapping(value = "/confirmarFinanciamento", method = RequestMethod.POST)
//    public String confirmarFinanciamento(@RequestBody String jsonRQ) {
//        WSPagtoFinanciamentoConfirmarRS result = null;
//        WSPagtoFinanciamentoConfirmarRQ wsRQ = gson.fromJson(jsonRQ, WSPagtoFinanciamentoConfirmarRQ.class);
//        wsRQ.getIntegrador().setDsMetodo("confirmarFinanciamento");
//        try {
//            result = confirmarWS.confirmarPropostaFinanciamento(wsRQ);
//        } catch (ErrorException ex) {
//            result = new WSPagtoFinanciamentoConfirmarRS(ex.getIntegrador(), null);
//        } finally {
//            LogWS.gerarLog(result.getIntegrador(), jsonRQ, false);
//        }
//        return (gson.toJson(result));
//    }

//    @RequestMapping(value = "/cancelaFinanciamento", method = RequestMethod.POST)
//    public String cancelarFinanciamento(@RequestBody String jsonRQ) {
//        WSPagtoFinanciamentoConfirmarRS result = null;
//        WSPagtoFinanciamentoConfirmarRQ wsRQ = gson.fromJson(jsonRQ, WSPagtoFinanciamentoConfirmarRQ.class);
//        wsRQ.getIntegrador().setDsMetodo("simularFinanciamento");
//        try {
//            result = pagtoFinanciamentoWS.simularFinanciamento(wsRQ);
//        } catch (ErrorException ex) {
//            result = new WSPagtoFinanciamentoRS(ex.getIntegrador(), null);
//        } finally {
//            LogWS.gerarLog(result.getIntegrador(), jsonRQ);
//        }
//        return (gson.toJson(result));
//    }

//    @RequestMapping(value = "/consultar", method = RequestMethod.POST)
//    public String consultar(@RequestBody String jsonRQ) {
//        WSPagtoFinanciamentoRS result = null;
//        WSPagtoFinanciamentoRQ wsRQ = gson.fromJson(jsonRQ, WSPagtoFinanciamentoRQ.class);
//        wsRQ.getIntegrador().setDsMetodo("consultar");
//        try {
//            result = consultarWS.consulta(wsRQ);
//        } catch (ErrorException ex) {
//            result = new WSPagtoFinanciamentoRS(ex.getIntegrador(), null);
//        } finally {
//            LogWS.gerarLog(result.getIntegrador(), jsonRQ, false);
//        }
//        return (gson.toJson(result));
//    }
}
