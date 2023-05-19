package hu.alagi.logixspring.service;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CountryService {

    public boolean isValidISOCountryCode(String countryCode) {
        String[] isoCountries = Locale.getISOCountries();
        for (String isoCountry : isoCountries) {
            if (isoCountry.equals(countryCode)) {
                return true;
            }
        }
        return false;
    }
}
