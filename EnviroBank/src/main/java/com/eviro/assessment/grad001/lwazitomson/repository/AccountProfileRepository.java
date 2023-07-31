package com.eviro.assessment.grad001.lwazitomson.repository;

import com.eviro.assessment.grad001.lwazitomson.entity.AccountProfile;
import com.eviro.assessment.grad001.lwazitomson.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountProfileRepository extends JpaRepository<AccountProfile, Long> {
    AccountProfile findByAccountHolderNameEqualsAndAccountHolderSurnameEqualsAndHttpImageLinkContaining(String accountHolderName,
                                                                                            String accountHolderSurname,
                                                                                            String httpImageLink);
}
