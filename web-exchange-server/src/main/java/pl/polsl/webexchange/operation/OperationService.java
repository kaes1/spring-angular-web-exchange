package pl.polsl.webexchange.operation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.currency.CurrencyRepository;
import pl.polsl.webexchange.currencyrate.CurrencyRate;
import pl.polsl.webexchange.currencyrate.CurrencyRateRepository;
import pl.polsl.webexchange.errorhandling.InvalidOperationException;
import pl.polsl.webexchange.operation.addfunds.AddFundsOperation;
import pl.polsl.webexchange.operation.addfunds.AddFundsOperationRepository;
import pl.polsl.webexchange.operation.addfunds.AddFundsRequest;
import pl.polsl.webexchange.operation.addfunds.AddFundsResponse;
import pl.polsl.webexchange.operation.tradecurrency.TradeCurrencyOperation;
import pl.polsl.webexchange.operation.tradecurrency.TradeCurrencyOperationRepository;
import pl.polsl.webexchange.operation.tradecurrency.TradeCurrencyRequest;
import pl.polsl.webexchange.operation.tradecurrency.TradeCurrencyResponse;
import pl.polsl.webexchange.user.User;
import pl.polsl.webexchange.usercurrencybalance.UserCurrencyBalance;
import pl.polsl.webexchange.usercurrencybalance.UserCurrencyBalanceRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;
    private final AddFundsOperationRepository addFundsOperationRepository;
    private final TradeCurrencyOperationRepository tradeCurrencyOperationRepository;
    private final UserCurrencyBalanceRepository userCurrencyBalanceRepository;

    @Transactional
    public AddFundsResponse addFunds(User user, AddFundsRequest addFundsRequest) {
        Currency currency = currencyRepository.findByCurrencyCode(addFundsRequest.getCurrencyCode()).orElseThrow();
        UserCurrencyBalance userCurrencyBalance = getUserCurrencyBalance(user, currency);

        userCurrencyBalance.addAmount(addFundsRequest.getAmount());
        userCurrencyBalanceRepository.save(userCurrencyBalance);

        AddFundsOperation operation = new AddFundsOperation(user, LocalDateTime.now(), currency, addFundsRequest.getAmount());
        addFundsOperationRepository.save(operation);
        return new AddFundsResponse(currency.getCurrencyCode(), addFundsRequest.getAmount(), userCurrencyBalance.getAmount());
    }

    public TradeCurrencyResponse tradeCurrency(User user, TradeCurrencyRequest tradeCurrencyRequest) {
        Currency boughtCurrency = currencyRepository.findByCurrencyCode(tradeCurrencyRequest.getBoughtCurrencyCode()).orElseThrow();
        Currency soldCurrency = currencyRepository.findByCurrencyCode(tradeCurrencyRequest.getSoldCurrencyCode()).orElseThrow();

        CurrencyRate currencyRate = currencyRateRepository.findFirstByBaseCurrencyAndTargetCurrencyOrderByDateTimeDesc(soldCurrency, boughtCurrency).orElseThrow();

        UserCurrencyBalance boughtBalance = getUserCurrencyBalance(user, boughtCurrency);
        UserCurrencyBalance soldBalance = getUserCurrencyBalance(user, soldCurrency);

        if (soldBalance.getAmount().compareTo(tradeCurrencyRequest.getSellAmount()) < 0) {
            throw new InvalidOperationException("Not enough funds to sell currency");
        }

        BigDecimal soldAmount = tradeCurrencyRequest.getSellAmount();
        BigDecimal boughtAmount = currencyRate.getRate().multiply(tradeCurrencyRequest.getSellAmount());

        soldBalance.subtractAmount(soldAmount);
        boughtBalance.addAmount(boughtAmount);

        userCurrencyBalanceRepository.save(boughtBalance);
        userCurrencyBalanceRepository.save(soldBalance);

        TradeCurrencyOperation operation = new TradeCurrencyOperation(user, LocalDateTime.now(), boughtCurrency, boughtAmount, soldCurrency, soldAmount, currencyRate);
        tradeCurrencyOperationRepository.save(operation);

        return new TradeCurrencyResponse(boughtCurrency.getCurrencyCode(), soldCurrency.getCurrencyCode(), boughtAmount, soldAmount, boughtBalance.getAmount(), soldBalance.getAmount());
    }

    private UserCurrencyBalance getUserCurrencyBalance(User user, Currency currency) {
        return userCurrencyBalanceRepository.findByUserAndCurrency(user, currency)
                .orElse(new UserCurrencyBalance(user, currency));
    }
}
