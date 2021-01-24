package pl.polsl.webexchange.currencyrate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExchangeRateApiService {

    public List<String> getAllValidCurrencyCodes() {
        final String uri = "https://api.exchangeratesapi.io/latest";
        ExchangeRateApiModel response = new RestTemplate().getForObject(uri, ExchangeRateApiModel.class);
        if (response == null)
            return new ArrayList<>();
        List<String> validCurrencyCodes = new ArrayList<>(response.getRates().keySet());
        validCurrencyCodes.add(response.getBase());
        return validCurrencyCodes;
    }


    public ExchangeRateApiModel getCurrencyRatesFromApi(String currencyCode) {
        final String uri = "https://api.exchangeratesapi.io/latest?base=" + currencyCode;
        return new RestTemplate().getForObject(uri, ExchangeRateApiModel.class);
    }

}
