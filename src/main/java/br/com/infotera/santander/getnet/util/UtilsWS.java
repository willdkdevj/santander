package br.com.infotera.santander.getnet.util;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSCliente;
import br.com.infotera.common.WSEndereco;
import br.com.infotera.common.enumerator.WSPagtoCartaoTipoEnum;
import static br.com.infotera.common.enumerator.WSPagtoCartaoTipoEnum.AMEX;
import static br.com.infotera.common.enumerator.WSPagtoCartaoTipoEnum.ELO;
import static br.com.infotera.common.enumerator.WSPagtoCartaoTipoEnum.HIPERCARD;
import static br.com.infotera.common.enumerator.WSPagtoCartaoTipoEnum.MASTERCARD;
import static br.com.infotera.common.enumerator.WSPagtoCartaoTipoEnum.VISA;
import br.com.infotera.common.pagto.WSPagtoCartao;
import br.com.infotera.common.pagto.threeds.WSDevice;
import br.com.infotera.santander.getnet.model.sdk.Address;
import br.com.infotera.santander.getnet.model.sdk.BillTo;
import br.com.infotera.santander.getnet.model.sdk.BillingAddress;
import br.com.infotera.santander.getnet.model.sdk.Card;
import br.com.infotera.santander.getnet.model.sdk.Customer;
import br.com.infotera.santander.getnet.model.sdk.Device;
import br.com.infotera.santander.getnet.model.sdk.Shippings;
import br.com.infotera.santander.getnet.model.sdk.ThreeDSOrder;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lucas
 */
@Component
public class UtilsWS {

    public static Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        UtilsWS.gson = gson;
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static String montaBrand(WSPagtoCartaoTipoEnum cartaoTipo) throws ErrorException {
        String brand = null;

        switch (cartaoTipo) {
            case VISA:
                brand = "Visa";
                break;
            case MASTERCARD:
                brand = "Mastercard";
                break;
            case ELO:
                brand = "Elo";
                break;
            case AMEX:
                brand = "Amex";
                break;
            case HIPERCARD:
                brand = "Hipercard";
                break;
            default:
                throw new ErrorException("Bandeira nao permitida.");
        }
        return brand;
    }

    public static BillingAddress montaBillingAddress(WSEndereco endereco) throws ErrorException {
        BillingAddress billingAddress = null;
        try {
            if (endereco != null) {
                billingAddress = new BillingAddress(endereco.getDsEndereco(),
                        endereco.getNrEndereco(),
                        endereco.getNmComplemento(),
                        endereco.getNmBairro(),
                        endereco.getMunicipio().getNmMunicipio(),
                        endereco.getMunicipio().getSgEstado(),
                        endereco.getMunicipio().getNmPais(),
                        endereco.getNrCep().replace("-", ""));
            }
        } catch (Exception ex) {
            throw new ErrorException("Erro a o montar BillingAddress");
        }
        return billingAddress;
    }

    public static Customer montaCustomer(String idCustomer, WSCliente cliente) throws ErrorException {
        Customer customer = null;
        try {

            if (cliente != null) {
                BillingAddress billingAddress = UtilsWS.montaBillingAddress(cliente.getEndereco());

                //Split de nome
                String[] nomeSplit = cliente.getNmCompleto().split(" ");

                customer = new Customer(idCustomer,
                        nomeSplit[0],
                        nomeSplit[nomeSplit.length - 1],
                        cliente.getNmCompleto(),
                        cliente.getNmEmail(),
                        cliente.getDocumento().getDocumentoTipo().getNmTipo(),
                        cliente.getDocumento().getNrDocumento(),
                        cliente.getTelefone().getNrTelefone(),
                        billingAddress);
            }
        } catch (Exception ex) {
            throw new ErrorException("Erro a o montar Customer");
        }
        return customer;
    }

    public static Shippings montaShippings(WSCliente cliente) throws ErrorException {
        Shippings shippings = null;
        try {

            if (cliente != null) {
                Address address = montaAddress(cliente.getEndereco());

                //Split de nome
                String[] nomeSplit = cliente.getNmCompleto().split(" ");

                shippings = new Shippings(nomeSplit[0], cliente.getNmCompleto(), cliente.getNmEmail(), cliente.getTelefone().getNrTelefone(), 0, address);
            }
        } catch (Exception ex) {
            throw new ErrorException("Erro a o montar Customer");
        }
        return shippings;
    }

    public static Address montaAddress(WSEndereco endereco) throws ErrorException {
        Address address = null;
        try {
            if (endereco != null) {
                address = new Address(endereco.getDsEndereco(),
                        endereco.getNrEndereco(),
                        endereco.getNmComplemento(),
                        endereco.getNmBairro(),
                        endereco.getMunicipio().getNmMunicipio(),
                        endereco.getMunicipio().getSgEstado(),
                        endereco.getMunicipio().getNmPais(),
                        endereco.getNrCep().replace("-", ""));
            }
        } catch (Exception ex) {
            throw new ErrorException("Erro a o montar montaAddress");
        }
        return address;
    }

    public static Card montaCard(String cardNumberToken, WSPagtoCartao pagtoCartao) throws ErrorException {
        Card card = null;
        try {

            if (pagtoCartao != null) {

                //Pega apenas os 2 ultimos digitos do ano, esta é o formato a ser enviado para a conector
                String anoValidade = null;
                if (pagtoCartao.getAaValidade() != null && pagtoCartao.getAaValidade().length() >= 2) {
                    anoValidade = pagtoCartao.getAaValidade().substring(pagtoCartao.getAaValidade().length() - 2);
                }
                //[FIM]Pega apenas os 2 ultimos digitos do ano, esta é o formato a ser enviado para a conector

                card = new Card(cardNumberToken,
                        pagtoCartao.getNmTitular(),
                        pagtoCartao.getCdSeguranca(),
                        montaBrand(pagtoCartao.getCartaoTipo()),
                        pagtoCartao.getMmValidade(),
                        anoValidade);
            }
        } catch (Exception ex) {
            throw new ErrorException("Erro a o montar Card");
        }
        return card;
    }

    public static Device montaDevice(WSDevice wsDevice) throws ErrorException {
        Device device = null;
        try {

            if (wsDevice != null) {
                device = new Device(
                        wsDevice.getIpAddress(),
                        wsDevice.getHttpBrowserColorDepth(),
                        wsDevice.getHttpBrowserJavaEnabled(),
                        wsDevice.getHttpBrowserJavaScriptEnabled(),
                        wsDevice.getHttpBrowserLanguage(),
                        wsDevice.getHttpBrowserScreenHeight(),
                        wsDevice.getHttpBrowserScreenWidth(),
                        wsDevice.getHttpBrowserTimeDifference()
                );
            }
        } catch (Exception ex) {
            throw new ErrorException("Erro a o montar Device");
        }
        return device;
    }

    public static ThreeDSOrder montaThreeDSOrder(WSPagtoCartao pagtoCartao, Integer valorPagto) throws ErrorException {
        ThreeDSOrder threeDSOrder = null;
        try {
            if (pagtoCartao != null) {
                WSCliente cliente = pagtoCartao.getCliente();
                WSEndereco endereco = cliente.getEndereco();

                //Split de nome
                String address1 = endereco.getDsEndereco() + (endereco.getNrEndereco() != null ? " - " + endereco.getNrEndereco() : "");
                String address2 = endereco.getNmBairro() + (endereco.getNmComplemento() != null ? " - " + endereco.getNmComplemento() : "");

                BillTo billTo = new BillTo(
                        address1,
                        address2,
                        endereco.getMunicipio().getSgEstado(),
                        endereco.getMunicipio().getSgPais(),
                        cliente.getNmEmail(),
                        cliente.getNmNome(),
                        cliente.getNmSobrenome(),
                        endereco.getMunicipio().getNmMunicipio(),
                        cliente.getTelefone().getNrTelefone(),
                        cliente.getTelefone().getNrTelefone(),
                        endereco.getNrCep().replace("-", "")
                );

                threeDSOrder = new ThreeDSOrder(
                        null,
                        pagtoCartao.getSgMoeda(),
                        valorPagto,
                        billTo
                );
            }
        } catch (Exception ex) {
            throw new ErrorException("Erro a o montar ThreeDSOrder");
        }
        return threeDSOrder;
    }
}
