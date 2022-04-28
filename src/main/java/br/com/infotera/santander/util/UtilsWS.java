package br.com.infotera.santander.util;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSCliente;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.enumerator.WSTelefoneTipoEnum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UtilsWS {

    public static void geraArquivo(String saida, String caminho, String nomeFile) {
        File diretorio = new File(caminho);
        File arqSaida = new File(diretorio, nomeFile);

        try {
            boolean statusArq = arqSaida.createNewFile();
            System.out.print(statusArq);

            FileWriter writer = new FileWriter(arqSaida, false);
            PrintWriter printer = new PrintWriter(writer);
            printer.println(saida);

            printer.flush();
            printer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean verificarCliente(WSIntegrador integrador, WSCliente cliente) throws ErrorException{
        boolean statusCadastro = false;

        if (cliente != null) {
            if (cliente.getDocumento() != null) {
                if (cliente.getDocumento().getDocumentoTipo() != null && cliente.getDocumento().getDocumentoTipo() == cliente.getDocumento().getDocumentoTipo().CPF) {
                    statusCadastro = true;
                } else {
                    throw new ErrorException(integrador, UtilsWS.class, "verificarCliente", WSMensagemErroEnum.ADI, "Documento necessario: CPF", WSIntegracaoStatusEnum.NEGADO, null, false);
                }
            } else {
                throw new ErrorException(integrador, UtilsWS.class, "verificarCliente", WSMensagemErroEnum.ADI, "Necessario informar documento", WSIntegracaoStatusEnum.NEGADO, null, false);
            }
            if (cliente.getTelefone() != null){
                if (!cliente.getTelefone().getTelefoneTipo().equals(WSTelefoneTipoEnum.CELULAR)){
                    statusCadastro = true;
                } else {
                    throw new ErrorException(integrador, UtilsWS.class, "verificarCliente", WSMensagemErroEnum.ADI, "Necessario informar o telefone padrão (CELULAR)", WSIntegracaoStatusEnum.NEGADO, null, false);
                }
            } else {
                throw new ErrorException(integrador, UtilsWS.class, "verificarCliente", WSMensagemErroEnum.ADI, "Necessario informar o telefone padrão (CELULAR)", WSIntegracaoStatusEnum.NEGADO, null, false);
            }
        } else {
            throw new ErrorException(integrador, UtilsWS.class, "verificarCliente", WSMensagemErroEnum.ADI, "Não foi localizado o Cliente na base - Entre em contato com suporte", WSIntegracaoStatusEnum.NEGADO, null, false);
        }

        return statusCadastro;
    }
}
