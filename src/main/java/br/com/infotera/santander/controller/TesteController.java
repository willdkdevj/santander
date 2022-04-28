/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.controller;

import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.WSReservaNome;
import br.com.infotera.common.enumerator.WSAmbienteEnum;
import br.com.infotera.common.enumerator.WSEmpresaEnum;
import br.com.infotera.common.enumerator.WSIntegradorEnum;
import br.com.infotera.common.enumerator.WSPaxTipoEnum;
import br.com.infotera.common.enumerator.WSSexoEnum;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoAnaliseRQ;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoAnaliseRS;
import br.com.infotera.common.util.Utils;
import br.com.infotera.santander.service.SimularWS;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author William Dias
 */
@RestController
public class TesteController {
    
    @Autowired
    private SimularWS simularWS;
    
    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    @ResponseBody
    public String teste() {
        try {
            System.out.println("ENTROU TESTE");
            List<String> dsCredencialList = new ArrayList();
            dsCredencialList.add("QZylsfyU5FpHE0Wwnfhzvo1vzEEa");// Consumer Key
            dsCredencialList.add("OwrzUbVfWiJcowMIGkFSejHVr3sa"); // Consumer Secret
            dsCredencialList.add("09551043000130"); // Username
            dsCredencialList.add("InfssteR@@2569");// Password
            WSIntegrador integrador = new WSIntegrador(WSEmpresaEnum.INFOTERA.getId(), "", WSIntegradorEnum.SANTANDER, "BRL", 30, WSAmbienteEnum.HOMOLOGACAO, dsCredencialList);
            
            integrador.setDsMetodo("simular");
            integrador.setStGerarLog(true);
            

            List<WSReservaNome> reservaNomeList = new ArrayList();
            reservaNomeList.add(new WSReservaNome("PEDRO", "SILVA", WSPaxTipoEnum.ADT, Utils.toDate("01/05/1990"), WSSexoEnum.MASCULINO, null, null));
            reservaNomeList.add(new WSReservaNome("MARIA", "SILVA", WSPaxTipoEnum.ADT, Utils.toDate("01/05/1990"), WSSexoEnum.MASCULINO, null, null));

            
            WSPagtoAnaliseRQ dispRQ = new WSPagtoAnaliseRQ();
            dispRQ.setIntegrador(integrador);

//            dispRQ.getIntegrador().setStGerarLog(true);
            WSPagtoAnaliseRS pagtoAnalise = simularWS.simularFinanciamento(dispRQ);
//            System.out.println(dispRS.getIntegrador().getIntegradorLogList().get(0).getSsTempo());
            return "OK";
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(TesteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("teste");
        return "Erro ao tentar autenticar";
    }
}
