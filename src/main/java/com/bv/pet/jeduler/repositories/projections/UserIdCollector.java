package com.bv.pet.jeduler.repositories.projections;

import java.util.List;

/**
 * Interface to unite repositories which entity contains inner key user_id
 */
public interface UserIdCollector {
    List<UserId> findAllBy();
}
