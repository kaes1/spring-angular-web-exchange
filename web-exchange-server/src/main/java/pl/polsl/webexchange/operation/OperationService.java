package pl.polsl.webexchange.operation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.currency.CurrencyService;
import pl.polsl.webexchange.currencyrate.CurrencyRate;
import pl.polsl.webexchange.currencyrate.CurrencyRateService;
import pl.polsl.webexchange.errorhandling.InvalidArgumentException;
import pl.polsl.webexchange.errorhandling.InvalidCurrencyRateException;
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
import pl.polsl.webexchange.usercurrencybalance.UserCurrencyBalanceService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OperationService {

    private final CurrencyService currencyService;
    private final CurrencyRateService currencyRateService;
    private final UserCurrencyBalanceService userCurrencyBalanceService;
    private final AddFundsOperationRepository addFundsOperationRepository;
    private final TradeCurrencyOperationRepository tradeCurrencyOperationRepository;
    private final OperationRepository operationRepository;

    public AddFundsResponse addFunds(User user, AddFundsRequest addFundsRequest) {
        Currency currency = currencyService.getActiveCurrency(addFundsRequest.getCurrencyCode());
        UserCurrencyBalance userCurrencyBalance = userCurrencyBalanceService.getUserCurrencyBalance(user, currency);

        userCurrencyBalance.addAmount(addFundsRequest.getAmount());
        userCurrencyBalanceService.save(userCurrencyBalance);

        AddFundsOperation operation = new AddFundsOperation(user, LocalDateTime.now(), currency, addFundsRequest.getAmount());
        addFundsOperationRepository.save(operation);
        return new AddFundsResponse(currency.getCurrencyCode(), addFundsRequest.getAmount(), userCurrencyBalance.getAmount());
    }

    public TradeCurrencyResponse tradeCurrency(User user, TradeCurrencyRequest tradeCurrencyRequest) {
        Currency boughtCurrency = currencyService.getActiveCurrency(tradeCurrencyRequest.getBoughtCurrencyCode());
        Currency soldCurrency = currencyService.getActiveCurrency(tradeCurrencyRequest.getSoldCurrencyCode());

        CurrencyRate currencyRate = currencyRateService.getLatestCurrencyRate(soldCurrency, boughtCurrency);

        if (!currencyRate.getRate().equals(tradeCurrencyRequest.getRate())){
            throw new InvalidCurrencyRateException("Currency rate is not the latest rate");
        }

        UserCurrencyBalance boughtBalance = userCurrencyBalanceService.getUserCurrencyBalance(user, boughtCurrency);
        UserCurrencyBalance soldBalance = userCurrencyBalanceService.getUserCurrencyBalance(user, soldCurrency);

        if (soldBalance.getAmount().compareTo(tradeCurrencyRequest.getSellAmount()) < 0) {
            throw new InvalidArgumentException("Not enough funds to sell currency");
        }

        BigDecimal soldAmount = tradeCurrencyRequest.getSellAmount();
        BigDecimal boughtAmount = currencyRate.getRate().multiply(tradeCurrencyRequest.getSellAmount());

        soldBalance.subtractAmount(soldAmount);
        boughtBalance.addAmount(boughtAmount);

        userCurrencyBalanceService.save(boughtBalance);
        userCurrencyBalanceService.save(soldBalance);

        TradeCurrencyOperation operation = new TradeCurrencyOperation(user, LocalDateTime.now(), boughtCurrency, boughtAmount, soldCurrency, soldAmount, currencyRate);
        tradeCurrencyOperationRepository.save(operation);

        return new TradeCurrencyResponse(boughtCurrency.getCurrencyCode(), soldCurrency.getCurrencyCode(), boughtAmount, soldAmount, boughtBalance.getAmount(), soldBalance.getAmount());
    }

    public List<OperationHistory> getOperationHistory(User user)  {
        List<Operation> operations = operationRepository.findTop20ByUserOrderByDateTimeDesc(user);
        return operations.stream()
                .map(Operation::toOperationHistory)
                .collect(Collectors.toList());
    }

    public List<OperationHistory> getOperationHistory(User user, LocalDate from, LocalDate to)  {

        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.plusDays(1).atStartOfDay();

        if (toDateTime.isBefore(fromDateTime)) {
            throw new InvalidArgumentException("End date cannot be before Start date");
        }

        List<Operation> operations = operationRepository.findAllByUserAndDateTimeBetweenOrderByDateTimeDesc(user, fromDateTime, toDateTime);
        return operations.stream()
                .map(Operation::toOperationHistory)
                .collect(Collectors.toList());
    }

}
