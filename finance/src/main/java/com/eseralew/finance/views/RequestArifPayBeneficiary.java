package com.kefiya.home.views;

import com.kefiya.home.configs.GojjebneasAccountSettings;
import com.kefiya.home.enums.BankEnum;
import lombok.Getter;

import java.util.List;

@Getter
public class RequestArifPayBeneficiary {
    String accountNumber;
    BankEnum bank;
    Double amount;

    public RequestArifPayBeneficiary(String accountNumber, BankEnum bank, Double amount) {
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.amount = amount;
    }


    public static List<RequestArifPayBeneficiary>  beneficiaries(double amount, GojjebneasAccountSettings accountSettings){
            return List.of(
                    new RequestArifPayBeneficiary(accountSettings.getCbe(), BankEnum.CBE, amount),
                    new RequestArifPayBeneficiary(accountSettings.getBoa(), BankEnum.BOA, amount),
                    new RequestArifPayBeneficiary(accountSettings.getTelebirr(), BankEnum.TELLEBIRR, amount)
            );
    }


}
