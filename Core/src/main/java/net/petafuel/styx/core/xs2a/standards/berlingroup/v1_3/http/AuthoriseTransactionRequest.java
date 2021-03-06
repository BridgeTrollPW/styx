package net.petafuel.styx.core.xs2a.standards.berlingroup.v1_3.http;

import net.petafuel.styx.core.xs2a.contracts.XS2AAuthorisationRequest;
import net.petafuel.styx.core.xs2a.entities.PaymentProduct;
import net.petafuel.styx.core.xs2a.entities.PaymentService;

import javax.json.Json;
import java.util.Optional;

public class AuthoriseTransactionRequest extends XS2AAuthorisationRequest {
    // "/v1/{payment-service}/{payment.product}/{paymentId}/authorisations/{authorisationId}"
    public static final String PIS_UPDATE_PSU_DATA = "/v1/%s/%s/%s/authorisations/%s";
    // "/v1/consents/{consentId}/authorisations/{authorsationId}"
    public static final String CS_UPDATE_PSU_DATA = "/v1/consents/%s/authorisations/%s";
    private String authorisationId;
    private String scaAuthenticationData;

    public AuthoriseTransactionRequest(String scaAuthenticationData, PaymentService paymentService, PaymentProduct paymentProduct, String paymentId, String authorisationId) {
        super(paymentService, paymentProduct, paymentId);
        this.scaAuthenticationData = scaAuthenticationData;
        this.authorisationId = authorisationId;
    }

    public AuthoriseTransactionRequest(String scaAuthenticationData, String consentId, String authorisationId) {
        super(consentId);
        this.scaAuthenticationData = scaAuthenticationData;
        this.authorisationId = authorisationId;
    }

    @Override
    public Optional<String> getRawBody() {
        String responseBody = Json.createObjectBuilder()
                .add("scaAuthenticationData", scaAuthenticationData).build().toString();
        return Optional.ofNullable(responseBody);
    }

    @Override
    public String getServiceURL() {
        if (isPIS()) {
            return String.format(PIS_UPDATE_PSU_DATA,
                    getPaymentService().getValue(),
                    getPaymentProduct().getValue(),
                    getPaymentId(),
                    authorisationId);
        } else {
            return String.format(CS_UPDATE_PSU_DATA, getConsentId(), authorisationId);
        }
    }
}
