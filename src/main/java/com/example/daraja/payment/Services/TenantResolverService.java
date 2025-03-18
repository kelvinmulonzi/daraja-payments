package com.example.daraja.payment.Services;

import com.example.daraja.payment.configuration.MultiTenancyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class TenantResolverService {

    private static final Logger logger = Logger.getLogger(TenantResolverService.class.getName());

    private final MultiTenancyConfig.TenantContext tenantContext;

    @Autowired
    public TenantResolverService(MultiTenancyConfig.TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    /**
     * Get the current tenant ID
     * @return tenant ID as Long
     */
    public Long getCurrentTenantId() {
        String tenantIdStr = tenantContext.getTenantId();
        try {
            // Try to convert to Long
            return Long.parseLong(tenantIdStr);
        } catch (NumberFormatException e) {
            // If tenant ID isn't numeric, use a mapping or default
            logger.warning("Non-numeric tenant ID: " + tenantIdStr + ", using default");
            return 1L; // Default tenant ID
        }
    }

    /**
     * Map tenant ID from external identifier
     * Could be used to map from business shortcode to tenant ID
     *
     * @param businessShortCode the M-Pesa business shortcode
     * @return corresponding tenant ID
     */
    public Long resolveTenantFromShortCode(String businessShortCode) {
        // Implement mapping logic from shortcode to tenant ID
        // This could be a database lookup or a simple switch statement

        switch (businessShortCode) {
            case "123456":
                return 1L;
            case "789012":
                return 2L;
            default:
                logger.warning("Unknown business shortcode: " + businessShortCode + ", using default tenant");
                return 1L;
        }
    }
}
