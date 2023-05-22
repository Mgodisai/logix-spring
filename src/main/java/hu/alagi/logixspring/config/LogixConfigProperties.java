package hu.alagi.logixspring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "logix.revenue-reduction")
@EnableConfigurationProperties
public class LogixConfigProperties {
    private Map<Integer, Double> valueLimits;

    public Map<Integer, Double> getValueLimits() {
        return valueLimits;
    }

    public void setValueLimits(Map<Integer, Double> valueLimits) {
        this.valueLimits = valueLimits;
    }
}
